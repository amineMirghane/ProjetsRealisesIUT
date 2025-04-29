# Documentation de l'Application NetFlop

## Table des matières

1. [Introduction](#introduction)
2. [Architecture de l'application](#architecture-de-lapplication)
3. [Fonctionnalités principales](#fonctionnalités-principales)
   - [Système d'authentification](#système-dauthentification)
   - [Découverte de films](#découverte-de-films)
   - [Système de notation et de favoris](#système-de-notation-et-de-favoris)
   - [Recommandations de groupe](#recommandations-de-groupe)
   - [Paramètres de l'application](#paramètres-de-lapplication)
4. [Visualisations et graphiques](#visualisations-et-graphiques)
5. [Implémentation de Shiny](#implémentation-de-shiny)
6. [Algorithme de recommandation](#algorithme-de-recommandation)
7. [Intégration d'APIs externes](#intégration-dapis-externes)
8. [Structure du code](#structure-du-code)

## Introduction

NetFlop est une application web interactive de recommandation de films, développée en R avec Shiny. Elle offre une interface utilisateur moderne inspirée de YouTube et permet aux utilisateurs de découvrir, noter et obtenir des recommandations de films personnalisées. L'application intègre également un système de recommandation de groupe innovant.

## Architecture de l'application

L'application est construite selon une architecture à trois niveaux :

1. **Interface utilisateur** : Développée avec Shiny et stylisée avec CSS personnalisé
2. **Serveur d'application** : Logique métier gérée par R/Shiny
3. **Base de données** : MySQL pour stocker les informations sur les films, utilisateurs et leurs préférences

## Fonctionnalités principales

### Système d'authentification

L'application propose un système d'authentification simple mais efficace :

- **Connexion** : Les utilisateurs peuvent se connecter via un panneau modal
- **Création de compte** : Nouveaux utilisateurs peuvent créer un compte directement depuis l'interface
- **Gestion de session** : Session utilisateur maintenue avec des valeurs réactives

```r
observeEvent(input$login_button, {
  if (nchar(input$user_login) > 0) {
    user_data <- execute_query(paste0("SELECT idUser, nom FROM user WHERE nom = '", 
                                    input$user_login, "'"))
    
    if (!is.null(user_data) && nrow(user_data) > 0) {
      values$loggedIn <- TRUE
      values$currentUser <- user_data$nom
      values$userId <- user_data$idUser
      # ...
    }
  }
})
```

### Découverte de films

L'interface principale permet aux utilisateurs de découvrir des films :

- **Affichage en grille** : Présentation visuelle des films avec affiches
- **Filtrage** : Options pour trier par note, date de sortie ou titre
- **Recherche** : Barre de recherche pour trouver des films spécifiques
- **Pagination** : Navigation facilitée à travers le catalogue de films

La découverte de films est implémentée avec un rendu UI dynamique :

```r
output$films_grid <- renderUI({
  films_data <- filtered_films_data()
  
  # Pagination et affichage en grille
  page <- input$page_num
  films_per_page <- 20
  
  # Création des cartes de films avec posters et informations
  films_cards <- lapply(1:nrow(page_films), function(i) {
    # Construction des cartes de films
  })
  
  # ...
})
```

### Système de notation et de favoris

Le système de notation permet aux utilisateurs d'exprimer leurs préférences :

- **Notation sur 10** : Interface intuitive avec des étoiles
- **Ajout aux favoris** : Sauvegarde automatique lors d'une notation
- **Tableau de favoris** : Vue détaillée des films notés
- **Visualisation graphique** : Graphique montrant les préférences de l'utilisateur

La fonctionnalité de notation est implémentée via un modal interactif :

```r
observeEvent(input$like_action, {
  # Affichage du modal de notation
  showModal(modalDialog(
    title = "Noter ce film",
    div(class = "rating-modal-content",
        # Interface de notation
    )
  ))
})

observeEvent(input$save_like, {
  # Sauvegarde de la notation dans la base de données
  query <- paste0(
    "INSERT INTO aime (tconst, idUser, note) VALUES ('",
    values$selectedFilmId, "', ", values$userId, ", ", rating, ")"
  )
})
```

### Recommandations de groupe

Une fonctionnalité innovante permet de générer des recommandations pour un groupe d'utilisateurs :

- **Sélection d'utilisateurs** : Interface pour choisir plusieurs utilisateurs
- **Visualisation 3D** : Graphique représentant les films recommandés selon plusieurs dimensions
- **Tableau détaillé** : Liste des films recommandés avec leurs caractéristiques

L'implémentation utilise une intégration avec Python pour l'algorithme de recommandation :

```r
observeEvent(input$validate_users, {
  # Récupération des utilisateurs sélectionnés
  selected_user_ids <- c()
  
  # Appel à l'algorithme de recommandation Python
  values$pythonRecommendations <- call_python_recommender(selected_user_ids)
})

call_python_recommender <- function(user_ids) {
  # Communication avec le script Python via JSON
  python_cmd <- paste0("python3 /home/rstudio/scripts_R/code/main2.py --user_ids=", temp_file, " --format=json")
  # ...
}
```

### Paramètres de l'application

L'application offre des options de configuration :

- **Paramètres de base de données** : Configuration de la connexion
- **Paramètres d'algorithme** : Options pour personnaliser les recommandations

## Visualisations et graphiques

L'application utilise plusieurs types de visualisations interactives :

1. **Graphique à barres** pour les films favoris (bibliothèque plotly) :
   - Axe X : Titres des films
   - Axe Y : Notes attribuées (1-10)
   - Interactivité : Survol pour voir les détails

```r
output$liked_films_plot <- renderPlotly({
  # Création d'un graphique à barres avec plotly
  p <- plot_ly(liked_movies_data, x = ~titre_court, y = ~note, 
               type = "bar", 
               text = ~titre,
               hoverinfo = "text+y")
  # ...
})
```

2. **Graphique 3D** pour les recommandations de groupe :
   - Axes : Note IMDb, Similarité, Score final
   - Coloration basée sur le score final
   - Interactivité : Rotation 3D, zoom, survol

```r
output$group_genres_plot <- renderPlotly({
  # Création d'un nuage de points 3D
  p <- plot_ly(
    recommended,
    x = ~Note,
    y = ~Similarity,
    z = ~FinalScore,
    text = ~paste("Titre:", Titre, "<br>Genres:", Genres),
    type = "scatter3d",
    mode = "markers"
  )
  # ...
})
```

## Implémentation de Shiny

L'application exploite plusieurs fonctionnalités avancées de Shiny :

1. **Interface utilisateur modulaire** :
   - Structure basée sur des div et conditionalPanel
   - Gestion de navigation sans rechargement de page
   - Composants réactifs pour l'affichage dynamique

2. **Réactivité** :
   - Utilisation de reactiveValues pour maintenir l'état
   - observeEvent pour les interactions utilisateur
   - Isolation réactive avec reactive() et req()

3. **Interfaces dynamiques** :
   - renderUI pour générer l'interface selon le contexte
   - Mise à jour dynamique des entrées avec updateTextInput, etc.
   - Gestion avancée des modals

4. **Personnalisation UI** :
   - CSS personnalisé pour une interface style YouTube
   - JavaScript personnalisé via insertUI et shinyjs
   - Animations et transitions CSS

```r
# Gestion de la navigation
observeEvent(input$active_tab, {
  shinyjs::hide("page_home")
  shinyjs::hide("page_favorites")
  shinyjs::hide("page_admin")
  shinyjs::hide("page_settings")
  
  # Affichage de la page appropriée
  if(input$active_tab == "home") {
    shinyjs::show("page_home")
    shinyjs::addClass(id = "nav_home", class = "active")
  } else if(input$active_tab == "favorites") {
    # ...
  }
})
```

## Algorithme de recommandation

L'algorithme de recommandation est implémenté en Python et intégré à l'application via des appels système :

1. **Filtrage collaboratif** : Compare les préférences entre utilisateurs
2. **Content-based** : Suggère des films similaires basés sur les caractéristiques
3. **Hybride** : Combinaison des deux approches

La communication avec Python se fait via des fichiers JSON temporaires :

```r
call_python_recommender <- function(user_ids) {
  temp_file <- tempfile(fileext = ".json")
  writeLines(jsonlite::toJSON(user_ids), temp_file)
  
  python_cmd <- paste0("python3 /home/rstudio/scripts_R/code/main2.py --user_ids=", temp_file, " --format=json")
  
  result <- system(python_cmd, intern = TRUE)
  
  # Traitement des résultats
  recommendations <- jsonlite::fromJSON(result)
  return(recommendations)
}
```

Le script Python (`main2.py`) utilise les bibliothèques scikit-learn, scikit-surprise et pandas pour calculer les recommandations personnalisées.

## Intégration d'APIs externes

L'application intègre l'API TMDB (The Movie Database) pour récupérer les affiches des films :

```r
get_movie_poster <- function(movie_title) {
  base_url <- "https://api.themoviedb.org/3/search/movie"
  
  response <- GET(
    url = base_url,
    add_headers(Authorization = paste("Bearer", BEARER_TOKEN)),
    query = list(query = movie_title, language = "fr-FR")
  )
  
  # Traitement de la réponse et récupération de l'URL de l'affiche
}
```

## Structure du code

Le code de l'application est organisé en plusieurs sections logiques :

1. **Configuration et initialisation** : Chargement des packages, configuration de la base de données
2. **Interface utilisateur (UI)** : Définition de la structure HTML/CSS
3. **Serveur (server)** :
   - Fonctions utilitaires (connexion BD, récupération d'affiches)
   - Gestion de l'authentification
   - Fonctionnalités de découverte de films
   - Système de notation et favoris
   - Recommandations de groupe
   - Paramètres

Le code utilise une approche modulaire avec des fonctions distinctes pour chaque fonctionnalité, facilitant la maintenance et les évolutions futures.

> Documentation Réalisé par **Laurent**.