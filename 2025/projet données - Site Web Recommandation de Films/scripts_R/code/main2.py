#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Hybrid recommender: CF (Surprise SVD) + CB (TF-IDF) blending
Requirements:
  - Python ≥3.7
  - pandas, numpy
  - sqlalchemy, pymysql
  - scikit-learn
  - scikit-surprise
Installation:
  python3 -m venv /venv
  . /venv/bin/activate
  pip install pandas numpy sqlalchemy pymysql scikit-learn scikit-surprise
"""

import sys
import argparse
import json
import decimal
import pandas as pd
import numpy as np
from sqlalchemy import create_engine, text
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.preprocessing import MinMaxScaler

# ----- Tentative d'import de Surprise pour CF -----
try:
    from surprise import Dataset, Reader, SVD
    CF_AVAILABLE = True
except ImportError:
    CF_AVAILABLE = False
    print(
        "WARNING: 'scikit-surprise' n'est pas installé. "
        "Collaborative Filtering désactivé.",
        file=sys.stderr,
        flush=True
    )

# ----- Configuration BDD -----
DB_USER = "db"
DB_PASS = "db"
DB_NAME = "db"
DB_HOST = "db"
DB_PORT = 3306

def get_engine():
    uri = f"mysql+pymysql://{DB_USER}:{DB_PASS}@{DB_HOST}:{DB_PORT}/{DB_NAME}?charset=utf8mb4"
    return create_engine(uri, pool_pre_ping=True, pool_recycle=3600)

# ----- Nettoyage des features textuelles -----
def clean_text(text):
    if pd.isna(text):
        return ""
    return ' '.join(text.lower().split())

# ----- Chargement des films likés (CB) -----
def load_liked_movies(engine, user_ids):
    ids_str = ','.join(str(u) for u in user_ids)
    q = text(f"""
        SELECT m.tconst, m.titre, a.note, m.nbVote,
               GROUP_CONCAT(DISTINCT g.nom SEPARATOR ' ') AS genres,
               GROUP_CONCAT(DISTINCT CASE WHEN pa.role='acteur' THEN p.nom END SEPARATOR ' ') AS acteurs,
               GROUP_CONCAT(DISTINCT CASE WHEN pa.role='director' THEN p.nom END SEPARATOR ' ') AS directeurs,
               m.dateSortie
        FROM aime a
        JOIN media m ON a.tconst = m.tconst
        LEFT JOIN esttype et ON m.tconst = et.tconst
        LEFT JOIN genre g ON et.idG = g.idG
        LEFT JOIN participe pa ON m.tconst = pa.tconst
        LEFT JOIN personne p ON pa.idP = p.idP
        WHERE a.note > 0 AND a.idUser IN ({ids_str})
        GROUP BY m.tconst, m.titre, a.note, m.nbVote, m.dateSortie
    """)
    df = pd.read_sql_query(q, engine).fillna('')
    df['genres'] = df['genres'].apply(clean_text)
    df['acteurs'] = df['acteurs'].apply(clean_text)
    df['directeurs'] = df['directeurs'].apply(clean_text)
    df['features'] = (
        (df['genres'] + ' ') * 3 +  # Poids 2x pour les genres
        (df['acteurs'] + ' ') * 2 +  # Poids 3x pour les acteurs
        df['directeurs'] + ' ' +     # Poids 1x pour les réalisateurs
        df['dateSortie'].astype(str)  # Poids 1x (ou ajuster si besoin)
    ).str.strip()
    return df

# ----- Chargement de tous les films (candidats CB) -----
def load_all_movies(engine):
    q = text("""
        SELECT m.tconst, m.titre, m.note, m.nbVote,
               GROUP_CONCAT(DISTINCT g.nom SEPARATOR ' ') AS genres,
               GROUP_CONCAT(DISTINCT CASE WHEN pa.role='acteur' THEN p.nom END SEPARATOR ' ') AS acteurs,
               GROUP_CONCAT(DISTINCT CASE WHEN pa.role='director' THEN p.nom END SEPARATOR ' ') AS directeurs,
               m.dateSortie
        FROM media m
        LEFT JOIN esttype et ON m.tconst = et.tconst
        LEFT JOIN genre g ON et.idG = g.idG
        LEFT JOIN participe pa ON m.tconst = pa.tconst
        LEFT JOIN personne p ON pa.idP = p.idP
        WHERE m.nbVote >= 100 AND m.note >= 5.0
        GROUP BY m.tconst, m.titre, m.note, m.nbVote, m.dateSortie
    """)
    df = pd.read_sql_query(q, engine).fillna('')
    df['genres'] = df['genres'].apply(clean_text)
    df['acteurs'] = df['acteurs'].apply(clean_text)
    df['directeurs'] = df['directeurs'].apply(clean_text)
    df['features'] = (
        (df['genres'] + ' ') * 2 +  # Poids 2x pour les genres
        (df['acteurs'] + ' ') * 3 +  # Poids 3x pour les acteurs
        df['directeurs'] + ' ' +     # Poids 1x pour les réalisateurs
        df['dateSortie'].astype(str)  # Poids 1x (ou ajuster si besoin)
    ).str.strip()
    return df

# ----- Chargement des notes (CF) -----
def load_ratings(engine):
    return pd.read_sql_query(
        "SELECT idUser AS user_id, tconst AS item_id, note AS rating FROM aime",
        engine
    )

# ----- Entraînement CF (SVD) -----
def train_cf_model(ratings_df):
    if not CF_AVAILABLE or ratings_df.empty:
        return None
    reader = Reader(rating_scale=(ratings_df.rating.min(), ratings_df.rating.max()))
    data = Dataset.load_from_df(ratings_df[['user_id', 'item_id', 'rating']], reader)
    train = data.build_full_trainset()
    algo = SVD(n_factors=50, n_epochs=20, lr_all=0.005, reg_all=0.02, verbose=False)
    algo.fit(train)
    return algo

# ----- Recommandation hybride -----
def recommend_movies_for_users(engine, user_ids, top_n=15, base_alpha=0.7):
    # 1) CB part
    liked_df = load_liked_movies(engine, user_ids)
    if liked_df.empty:
        print("Aucun film liké par ces utilisateurs.")
        return []
    all_df = load_all_movies(engine)
    seen = set(liked_df['tconst'])
    candidates = all_df[~all_df['tconst'].isin(seen)].copy()
    if candidates.empty:
        print("Tous les films sont déjà likés !")
        return []

    # TF-IDF + cosinus => cb_score
    combo = pd.concat([candidates['features'], liked_df['features']])
    tfidf = TfidfVectorizer(max_features=1000, stop_words='english', ngram_range=(1, 2))
    mat = tfidf.fit_transform(combo)
    c_mat = mat[:len(candidates)]
    l_mat = mat[len(candidates):]
    cb_scores = cosine_similarity(c_mat, l_mat, dense_output=False).mean(axis=1)
    candidates['cb_score'] = np.array(cb_scores).ravel()

    # 2) CF part
    ratings_df = load_ratings(engine)
    cf_model = train_cf_model(ratings_df)
    if cf_model and not ratings_df.empty:
        preds = [
            np.mean([cf_model.predict(u, row['tconst']).est for u in user_ids])
            for _, row in candidates.iterrows()
        ]
        candidates['cf_score'] = preds
    else:
        candidates['cf_score'] = 0.0
        print("Aucune note disponible, passage en mode CB pur.")
        base_alpha = 0.0

    # 3) Ajustement dynamique d'alpha
    ratings_count = ratings_df[ratings_df['user_id'].isin(user_ids)].shape[0]
    alpha = min(base_alpha, ratings_count / 50.0)  # Plus de notes = plus de CF

    # 4) Normalisation
    scaler = MinMaxScaler()
    candidates[['cb_score', 'cf_score']] = scaler.fit_transform(
        candidates[['cb_score', 'cf_score']]
    )

    # 5) Pondération par note et popularité
    candidates['cb_score'] = candidates['cb_score'] * (candidates['note'] / 10.0)
    candidates['cf_score'] = candidates['cf_score'] * (candidates['note'] / 10.0)
    candidates['popularity'] = np.log1p(candidates['nbVote']) / np.log1p(candidates['nbVote'].max())

    # 6) Score blendé
    candidates['final_score'] = (
        alpha * candidates['cf_score'] +
        (1.0 - alpha) * candidates['cb_score']
    ) * candidates['popularity']

    # 7) Top N
    top = candidates.nlargest(top_n, 'final_score')
    out = top[[
        'tconst', 'titre', 'genres', 'note', 'nbVote',
        'cb_score', 'cf_score', 'final_score'
    ]].to_dict(orient='records')

    # 8) Arrondi
    for film in out:
        for col in ['note', 'nbVote', 'cb_score', 'cf_score', 'final_score']:
            film[col] = round(float(film[col]), 2)
    return out

# ----- Main -----
def main():
    parser = argparse.ArgumentParser(description="Recommander des films")
    parser.add_argument(
        "--user_ids", help="Chemin vers un JSON contenant la liste des IDs utilisateurs"
    )
    parser.add_argument(
        "--format", choices=["json", "text"], default="text",
        help="Format de sortie : json ou text"
    )
    args = parser.parse_args()

    engine = get_engine()
    if args.user_ids:
        with open(args.user_ids, 'r') as f:
            users = json.load(f)
    else:
        users = [1001, 1002]

    resultats = recommend_movies_for_users(engine, users)

    if args.format == 'json':
        simplified = [{
            'titre': f['titre'],
            'genres': f['genres'],
            'note': f['note'],
            'similarity': f['cb_score'],
            'final_score': f['final_score']
        } for f in resultats]
        print(json.dumps(
            simplified,
            default=lambda o: float(o) if isinstance(o, decimal.Decimal) else o
        ))
    else:
        print(f"\n🎯 Recommandations pour les utilisateurs {users} :\n")
        for f in resultats:
            print(f" - {f['titre']} (note: {f['note']:.1f}, score: {f['final_score']:.3f})")

if __name__ == "__main__":
    main()