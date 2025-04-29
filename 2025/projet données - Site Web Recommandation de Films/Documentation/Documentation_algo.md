# Documentation du système de recommandation hybride

## Introduction

Ce système implémente un algorithme de recommandation hybride combinant deux approches complémentaires :
- **Filtrage collaboratif (CF)** : Utilise le comportement passé des utilisateurs (notes) avec l'algorithme SVD de Surprise
- **Recommandation basée sur le contenu (CB)** : Analyse les caractéristiques des films (genres, acteurs, réalisateurs) avec TF-IDF

L'objectif est de fournir des recommandations personnalisées en exploitant à la fois les similarités entre utilisateurs/items et les caractéristiques intrinsèques des films.

## Prérequis

- Python 3.7 ou supérieur
- Bibliothèques :
  - pandas, numpy
  - sqlalchemy, pymysql
  - scikit-learn
  - scikit-surprise

Installation des dépendances :
```bash
python3 -m venv /venv
. /venv/bin/activate
pip install pandas numpy sqlalchemy pymysql scikit-learn scikit-surprise
```

## Architecture du système

### 1. Configuration de la base de données
Le système se connecte à une base de données MySQL contenant les informations sur les films, utilisateurs et évaluations.

### 2. Extraction des données
- **Films likés** : Extraction des films appréciés par les utilisateurs cibles
- **Catalogue de films** : Extraction des films candidats pour les recommandations
- **Évaluations** : Récupération de l'historique des notes pour le filtrage collaboratif

### 3. Prétraitement
- Nettoyage et formatage des caractéristiques textuelles
- Construction de features combinées avec pondération :
  - Genres (poids ×2)
  - Acteurs (poids ×3)
  - Réalisateurs (poids ×1)
  - Année de sortie (poids ×1)

### 4. Moteur de recommandation basé sur le contenu (CB)
- Vectorisation TF-IDF des caractéristiques textuelles
- Calcul de similarité cosinus entre les films appréciés et les candidats

### 5. Moteur de filtrage collaboratif (CF)
- Entraînement d'un modèle SVD avec Surprise
- Prédiction des notes pour les films candidats

### 6. Hybridation
- Normalisation des scores CB et CF
- Pondération dynamique basée sur le volume de données disponibles
- Ajustement selon la popularité et la qualité des films
- Calcul du score final hybride

## Algorithme détaillé

1. **Chargement des données**
   - Extraction des films appréciés par les utilisateurs cibles
   - Extraction des films candidats (non vus par les utilisateurs)
   - Chargement de l'historique des évaluations

2. **Recommandation basée sur le contenu**
   - Construction des vecteurs TF-IDF pour les caractéristiques des films
   - Calcul de la similarité entre les films appréciés et les candidats
   - Attribution d'un score CB à chaque film candidat

3. **Filtrage collaboratif**
   - Entraînement du modèle SVD sur l'historique des notes
   - Prédiction des notes pour les films candidats
   - Attribution d'un score CF à chaque film candidat

4. **Fusion des scores**
   - Normalisation des scores CB et CF sur une échelle commune
   - Ajustement dynamique du coefficient d'hybridation (α) :
     - Plus de notes disponibles = plus de poids pour CF
     - Moins de notes = plus de poids pour CB
   - Pondération par la qualité (note moyenne) et popularité (nombre de votes)
   - Calcul du score final : `score_final = (α × score_CF + (1-α) × score_CB) × popularité`

5. **Sélection des recommandations**
   - Classement des candidats selon le score final
   - Retour des N films les mieux classés

## Utilisation

### En ligne de commande

```bash
# Recommandation pour les utilisateurs spécifiés dans un fichier JSON
python recommender.py --user_ids chemin/vers/users.json --format json

# Recommandation pour les utilisateurs par défaut (1001, 1002)
python recommender.py --format text
```

### Options

- `--user_ids` : Chemin vers un fichier JSON contenant la liste des IDs utilisateurs
- `--format` : Format de sortie (json ou text)

## Points forts

1. **Hybridation adaptative** : Équilibre dynamique entre CF et CB selon le volume de données
2. **Pondération intelligente des caractéristiques** : Importance relative des genres, acteurs, etc.
3. **Prise en compte de la qualité et popularité** : Favorise les films bien notés et populaires
4. **Fallback automatique** : Bascule vers CB pur en l'absence de données suffisantes pour CF

## Limitations et perspectives d'amélioration

1. **Performance** : Optimiser le traitement pour de grands catalogues (vectorisation sparse, etc.)
2. **Cold start** : Améliorer les recommandations pour nouveaux utilisateurs/films
3. **Diversité** : Implémenter des mécanismes pour favoriser la diversité des recommandations
4. **Actualisation** : Ajouter des facteurs temporels pour favoriser les contenus récents
5. **Évaluation** : Implémenter des métriques pour évaluer la qualité des recommandations

> Documentation Réalisé par **Yusuf**.