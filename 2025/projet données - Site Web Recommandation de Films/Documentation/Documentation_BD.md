# Rapport du projet - Base de données FILM IMDB (Travail Data)


**Le MCD a été réalisé par Clément et le fichier des notes authentique par Mallory.**  
**La partie Data a été réalisé par Abdoullah : traitement, création des inserts, mise en place de la BD optimisée et validation.**

---
## 1. Introduction

Après la soutenance de mi-parcours, une refonte complète de la base de données a été décidée pour optimiser la structure et la rendre plus adaptée aux besoins du projet.  
J'ai donc dû reprendre tout le travail effectué auparavant : retraitement des données, réadaptation de la base, et nouvelle génération des insertions.  
Ensuite, j'ai conçu et mis en place la nouvelle base de données sur phpMyAdmin, adaptée aux nouvelles contraintes, en veillant toujours à l'intégrité et à la cohérence des données.

---


Dans ce projet, la partie **Data** a consisté à réaliser les tâches suivantes :

- **Nettoyer** les fichiers **TSV** bruts issus de la base de données **IMDb non commerciale**.
- **Combiner** ces différents fichiers pour **fabriquer un CSV** global.
- **Filtrer** les données pour conserver uniquement les **films** (pas de séries, pas de films adultes, et exclusion des films avec **moins de 100 votes**).
- **Normaliser** les données (suppression des erreurs d'encodage, valeurs manquantes corrigées).
- **Créer la base de données** MySQL sur **phpMyAdmin** avec une architecture optimisée.
- **Automatiser les INSERT** à l'aide de **scripts Python**, car le volume des données était important (plus de 200 Mo).

---

## 2. Description de la Base de Données

La base finale contient plusieurs tables soigneusement structurées pour assurer la cohérence, l'intégrité et l'efficacité de l'exploitation.

Chaque table a été construite avec des **clés primaires** et **clés étrangères** pour garantir des liens solides entre les données.

### 2.1 Table `Media`

Contient les informations principales des films.

| Attribut      | Type          | Description                |
| ------------- | ------------- | -------------------------- |
| tconst        | VARCHAR(50)   | Identifiant du film        |
| titre         | VARCHAR(50)   | Titre du film              |
| titreOriginal | VARCHAR(50)   | Titre original             |
| temps         | INT           | Durée du film (en minutes) |
| dateSortie    | YEAR          | Année de sortie            |
| nbVote        | INT           | Nombre de votes IMDb       |
| note          | DECIMAL(15,2) | Note moyenne IMDb          |

**Exemple d'INSERT :**
```sql
INSERT INTO Media (tconst, titre, titreOriginal, temps, dateSortie, nbVote, note)
VALUES ('tt0000009', 'Miss Jerry', 'Miss Jerry', 45, 1900, 215, 5.40);
```

---

### 2.2 Table `Utilisateur`

Liste des utilisateurs qui notent les films.

| Attribut | Type        | Description             |
| -------- | ----------- | ----------------------- |
| idUser   | INT         | Identifiant utilisateur |
| nom      | VARCHAR(50) | Nom de l'utilisateur    |

**Exemple d'INSERT :**
```sql
INSERT INTO Utilisateur (idUser, nom)
VALUES (1, 'User_1');
```

---

### 2.3 Table `Genre`

Liste tous les genres de films.

| Attribut | Type        | Description          |
| -------- | ----------- | -------------------- |
| idG      | VARCHAR(50) | Identifiant du genre |
| nom      | VARCHAR(50) | Nom du genre         |

**Exemple d'INSERT :**
```sql
INSERT INTO Genre (idG, nom)
VALUES ('G1', 'Action');
```

---

### 2.4 Table `Personne`

Contient toutes les personnes référencées dans les films (acteurs, réalisateurs).

| Attribut | Type        | Description                |
| -------- | ----------- | -------------------------- |
| idP      | VARCHAR(50) | Identifiant de la personne |
| nom      | VARCHAR(50) | Nom de la personne         |

**Exemple d'INSERT :**
```sql
INSERT INTO Personne (idP, nom)
VALUES ('Anm0000001', 'Fred Astaire');
```

---

### 2.5 Table `Participe`

Associe les personnes aux films avec leur rôle.

| Attribut | Type        | Description                |
| -------- | ----------- | -------------------------- |
| tconst   | VARCHAR(50) | Identifiant du film        |
| idP      | VARCHAR(50) | Identifiant de la personne |
| role     | VARCHAR(50) | Rôle dans le film          |

**Exemple d'INSERT :**
```sql
INSERT INTO Participe (tconst, idP, role)
VALUES ('tt0000009', 'Anm0183823', 'acteur');
```

---

### 2.6 Table `EstType`

Relie un film à ses genres.

| Attribut | Type        | Description          |
| -------- | ----------- | -------------------- |
| tconst   | VARCHAR(50) | Identifiant du film  |
| idG      | VARCHAR(50) | Identifiant du genre |

**Exemple d'INSERT :**
```sql
INSERT INTO EstType (idG, tconst)
VALUES ('G1', 'tt0000009');
```

---

### 2.7 Table `Aime`

Contient les notes attribuées par les utilisateurs aux films.

| Attribut | Type        | Description                  |
| -------- | ----------- | ---------------------------- |
| tconst   | VARCHAR(50) | Identifiant du film          |
| idUser   | INT         | Identifiant de l'utilisateur |
| note     | FLOAT       | Note attribuée au film       |

**Exemple d'INSERT :**
```sql
INSERT INTO Aime (tconst, idUser, note)
VALUES ('tt0000009', 1, 7.5);
```

---

### 2.8 Table `GroupeUser`

Liste les groupes d'utilisateurs.

| Attribut  | Type        | Description           |
| --------- | ----------- | --------------------- |
| idGroupe  | INT         | Identifiant du groupe |
| nomGroupe | VARCHAR(50) | Nom du groupe         |

**Exemple d'INSERT :**
```sql
INSERT INTO GroupeUser (idGroupe, nomGroupe)
VALUES (1, 'Cinéphiles');
```

---

### 2.9 Table `Appartient`

Indique l'appartenance des utilisateurs à des groupes.

| Attribut | Type | Description                  |
| -------- | ---- | ---------------------------- |
| idGroupe | INT  | Identifiant du groupe        |
| idUser   | INT  | Identifiant de l'utilisateur |

**Exemple d'INSERT :**
```sql
INSERT INTO Appartient (idGroupe, idUser)
VALUES (1, 1);
```

---

## 3. Points Techniques et Optimisations

- **Normalisation maximale** : Toutes les données sont séparées dans des tables respectant les bonnes pratiques de modélisation relationnelle.
- **Dates standardisées** : Le format **YEAR** est utilisé pour simplifier la gestion des dates de sortie.
- **Génération automatique** :
  - Tous les fichiers d'insertion (`INSERT`) ont été **générés par des scripts Python**.
  - Gestion automatique des volumes pour éviter les erreurs d'import en divisant les gros fichiers.
- **Fiabilité assurée** :
  - Suppression des doublons.
  - Contrôle de l'unicité des relations (`PRIMARY KEY`, `FOREIGN KEY`).

## 4. Validation de la Base

Des **requêtes SQL** ont été réalisées pour :

- Vérifier le bon peuplement de toutes les tables.
- Comparer les moyennes des notes des films avec les vraies valeurs d'IMDb.
- Vérifier l'absence de doublons dans les participations, notes, et appartenances.

---
## 5. Gestion Spécifique des Notes Utilisateurs

Puisqu'aucune base officielle ne fournit les notes détaillées des utilisateurs IMDb, nous avons dans un premier temps généré un échantillon simulé de 300 utilisateurs fictifs attribuant des notes aux films, en respectant la moyenne IMDb de chaque film.

Cependant, cet échantillon simulé **n'a finalement pas été utilisé** dans la base de données finale.

Pour garantir des données plus authentiques, nous avons choisi d'ajouter des **notes réelles** : chaque membre du groupe projet a noté **10 films** à partir de son expérience personnelle, afin d'enrichir la base avec de véritables notations humaines.

Le fichier contenant les notations authentiques a été préparé par **Mallory**, permettant de centraliser et intégrer efficacement les vraies notes dans la base.

Ce choix a permis de conserver une base **équilibrée**, **réaliste** et **directement exploitable** pour les tests de l'algorithme.

---

## 6. Conclusion


La base est **propre**, **fiable**, et **optimisée** pour être utilisée efficacement par l'équipe **algorithme** et **front-end**. Elle est désormais prête pour supporter des projets de recommandations, d'analyses statistiques ou d'exploration de films.


> Documentation Réalisé par **Abdoullah**.
