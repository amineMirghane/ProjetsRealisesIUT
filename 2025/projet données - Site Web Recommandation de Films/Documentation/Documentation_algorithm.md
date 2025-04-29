
# Documentation technique de l’algorithme

## Introduction

Cet algorithme de recommandation de films a été développé pour des groupes d’utilisateurs en utilisant :

- Une base de données phpMyAdmin contenant les films, les genres, les utilisateurs, leurs notes
- Une méthode de filtrage collaboratif (Collaborative Filtering) basé sur les prédictions de notes générées par un modèle SVD (Singular Value Decomposition)
- Une méthode de filtrage par contenu (Content-Based Filtering), basée sur les genres des films, ainsi que sur les acteurs principaux et les réalisateurs
- Une pondération par les notes attribuées aux films par les utilisateurs (notes individuelles)

## Connexion à la BD

Le fichier `db.py` établit la connexion avec la base de données PhpMyAdmin en utilisant XAMPP.  
La connexion est établie via le module `mysql` en se connectant à la base `bd` sur `localhost` et le port `3306`.

![Connexion à la bd](scripts_R/www/connexion_bd.png)

## Extraction des genres, des films et des réalisateurs de la base

On extrait à l’aide des requêtes SQL les informations sur lesquelles on va se baser pour construire nos vecteurs utilisateurs.  
Les informations sur les réalisateurs, acteurs et genres sont celles sur lesquelles les vecteurs se baseront.

À l’aide de requêtes SQL, on récupère :

- les genres (`obtenir_liste_genres`)
- les acteurs (`obtenir_liste_acteurs`)
- les réalisateurs (`obtenir_liste_realisateurs`)

![Fonction obtenir liste des genres](scripts_R/www/obtenir_liste.png)

Pour chaque film, on obtient également :

- Ses genres (`obtenir_liste_genres_associes_film`)
- Ses acteurs principaux (`obtenir_acteurs_associes_film`)
- Son ou ses réalisateurs (`obtenir_realisateurs_associes_film`)

![Fonction obtenir liste des genres associés aux films](scripts_R/www/obtenir_liste_genres_associes_film.png)

## Construction d’un vecteur pour un utilisateur

Pour chaque utilisateur, l'algorithme commence par récupérer la liste des films qu'il a aimés, ainsi que la note associée à chaque film, à partir de la table `aime` de la base de données.

Pour chaque film aimé, un vecteur binaire est construit à partir :

- des genres associés au film (`obtenir_liste_genres_associes_film`)
- des acteurs principaux (`obtenir_acteurs_associes_film`)
- des réalisateurs (`obtenir_realisateurs_associes_film`)

Chaque dimension du vecteur vaut 1 si l'élément est présent pour le film, 0 sinon (`vecteur_genres`, `vecteur_acteurs`, `vecteur_realisateurs`).  
Ces trois vecteurs sont concaténés pour obtenir un **vecteur total** (`vecteur_total`).

Chaque vecteur est ensuite pondéré par la note que l'utilisateur a donnée au film :

```python
vecteurs_pondérés.append([val * note for val in v])
```

Enfin, l'algorithme calcule la **moyenne pondérée** de tous les vecteurs films aimés pour obtenir un vecteur moyen représentant les préférences globales de l'utilisateur :

```python
vecteur_moyen = [sum(col) / somme_poids for col in zip(*vecteurs_pondérés)]
```

![Vecteur Utilisateur](scripts_R/www/vecteur_utilisateur.png)

## Construction d’un vecteur pour une liste d’utilisateurs

Le vecteur moyen d'une liste d'utilisateurs est obtenu en faisant la moyenne des vecteurs individuels.  
Cela permet de représenter l'ensemble des goûts du groupe en prenant en compte les genres, acteurs et réalisateurs appréciés.

## Le fonctionnement de l’algorithme

L'algorithme repose sur une **approche hybride**, combinant :

- **Filtrage par contenu** :
  - Chaque utilisateur est représenté par un vecteur moyen basé sur les genres, acteurs principaux et réalisateurs des films qu'il a aimés (`vecteur_utilisateur` dans `utilisateur.py`).
  - Chaque film est représenté par son vecteur de caractéristiques (`vecteur_complet_film` dans `utilisateur.py`).
  - La **similarité cosinus** est calculée entre le vecteur du groupe d'utilisateurs et le vecteur de chaque film (voir fonction `similarite_cosinus` dans `recommandation.py`).

- **Filtrage collaboratif** :
  - Un modèle **SVD** est entraîné sur les notes des utilisateurs (`entrainer_modele_svd` dans `collaboratif.py`).
  - Pour chaque film non vu, la note prédite par le modèle est obtenue via `predire_note_svd` (`collaboratif.py`).

Ensuite, dans la fonction `recommander_films` (`recommandation.py`), les deux scores sont fusionnés :

- Score contenu (similarité cosinus) pondéré par un facteur α = 0.60
- Score collaboratif (note prédite SVD, normalisée entre [0,1]) pondéré par (1−α) = 0.40

Cela donne le **score final du film**, permettant de le classer parmi les films recommandés.

### Remarque

Cette approche hybride permet :

- d’exploiter la description des films aimés par les utilisateurs (contenu)
- d’étudier les comportements d’utilisateurs similaires (collaboratif via SVD)

![Score](scripts_R/www/score.png)


## Optimisation de l'algorithme

Pour accélérer l'exécution :

- Les vecteurs films sont **pré-construits une seule fois** au début.
- Ils sont stockés en mémoire dans un **dictionnaire Python**, indexé par l'identifiant du film (`tconst`).
- Cela évite les appels répétitifs à la base de données et réduit le temps de calcul, notamment lors de l'analyse de plus de 1000 films.

![Optimisation](scripts_R/www/dictionnaire.png)

## Les résultats de l’algorithme

Pour valider la cohérence de l'algorithme, une comparaison a été faite entre les films aimés par les utilisateurs et les films recommandés.

Observations :

- Forte correspondance au niveau :
  - des genres (Action, Animation, Super-héros)
  - des acteurs (présence d'acteurs liés aux univers **Fast & Furious** et **Marvel**)
  - des univers cinématographiques (**MCU**, **Fast Saga**)

  ![Résultat de la Base de donnée](scripts_R/www/res_bd.png)
  ![Résultat via l'invite de commande](scripts_R/www/res_bash.png)

> Documentation Réalisé par **Amine**.