# Installation et chargement des packages nécessaires
packages <- c("shiny", "shinydashboard", "shinythemes", "DBI", "RMySQL", "DT", 
              "plotly", "shinyjs", "recommenderlab", "Matrix", "httr", "jsonlite")

install_if_missing <- function(pkg) {
  if (!require(pkg, character.only = TRUE)) {
    install.packages(pkg, dependencies = TRUE)
    library(pkg, character.only = TRUE)
  }
}

# Clé API TMDB
BEARER_TOKEN <- "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyYjc4YTdhOGU4OTVkNTkyYzViZjhjODNiODA2MDQ3OSIsIm5iZiI6MTczMzc0Mjk5Ny44NzM5OTk4LCJzdWIiOiI2NzU2ZDE5NTQzMTEwOGFhMjNkMTJhMTUiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.rhEh9vpDvgtJzA-girtATATozP3RZnN2Q9AYjsHX9pY"

get_movie_poster <- function(movie_title) {
  base_url <- "https://api.themoviedb.org/3/search/movie"
  
  # Utiliser language "en" pour de meilleurs résultats avec les titres originaux
  response <- GET(
    url = base_url,
    add_headers(Authorization = paste("Bearer", BEARER_TOKEN)),
    query = list(query = movie_title, language = "en-US", include_adult = "false")
  )
  
  if (status_code(response) == 200) {
    result <- fromJSON(content(response, "text"), flatten = TRUE)
    if (length(result$results) > 0 && !is.null(result$results$poster_path[1])) {
      poster_path <- result$results$poster_path[1]
      return(paste0("https://image.tmdb.org/t/p/w500", poster_path))
    }
  }
  # Image par défaut pour les films sans poster
  return("interog.jpg")
}

# Vérifier et installer les packages manquants
sapply(packages, install_if_missing)

# Structure de l'application Shiny
ui <- fluidPage(
  useShinyjs(),
  
  # Inputs cachés pour la pagination et la notation
  tags$div(
    style = "display: none;",
    numericInput("page_num", "Current Page", 1, min = 1),
    numericInput("selected_rating", "Selected Rating", 0, min = 0, max = 10)
  ),
  
  # En-tête style YouTube
  div(class = "youtube-header",
      div(class = "yt-logo-container",
          tags$img(src = "image.png", class = "yt-title-image", style = "height: 80px;")
      ),
      div(class = "yt-search-container",
          textInput("search_input", NULL, placeholder = "Rechercher un film...", width = "100%")
      ),
      div(class = "yt-user-container", id = "user_header",
          conditionalPanel(
            condition = "!output.is_logged_in",
            actionButton("display_login", tags$span(tags$i(class = "fas fa-sign-in-alt"), "Se connecter"), 
                         class = "btn-youtube-login")
          ),
          conditionalPanel(
            condition = "output.is_logged_in",
            div(class = "yt-user-info",
                tags$img(src = "https://cdn-icons-png.flaticon.com/512/847/847969.png", class = "yt-avatar"),
                textOutput("header_username"),
                actionButton("logout_button", tags$span(tags$i(class = "fas fa-sign-out-alt"), " Déconnexion"), 
                             class = "btn-yt-logout")
            )
          )
      )
  ),
  
  # Panneau de connexion
  hidden(
    div(id = "login_panel", class = "yt-login-panel",
        div(class = "yt-login-content",
            h3("Connexion", class = "text-center"),
            textInput("user_login", "Nom d'utilisateur"),
            div(class = "yt-login-buttons",
                actionButton("login_button", "Se connecter", class = "btn-yt-primary"),
                actionButton("new_user_button", "Créer un compte", class = "btn-yt-secondary")
            ),
            actionButton("close_login", "×", class = "btn-close-login")
        )
    )
  ),
  
  # Structure principale
  div(class = "yt-content",
      div(class = "yt-sidebar", id = "yt_sidebar",
          div(class = "nav-item active", id = "nav_home", onclick = "Shiny.setInputValue('active_tab', 'home')", 
              tags$i(class = "fas fa-home"), "Accueil"),
          div(class = "nav-item", id = "nav_favorites", onclick = "Shiny.setInputValue('active_tab', 'favorites')",
              tags$i(class = "fas fa-heart"), "Mes favoris"),
          div(class = "nav-item", id = "nav_admin", onclick = "Shiny.setInputValue('active_tab', 'admin')",
              tags$i(class = "fas fa-users"), "Groupes"),
          div(class = "nav-item", id = "nav_settings", onclick = "Shiny.setInputValue('active_tab', 'settings')",
              tags$i(class = "fas fa-cog"), "Paramètres"),
          hr(),
          conditionalPanel(
            condition = "output.is_logged_in",
            h4("Mes favoris récents", class = "sidebar-subtitle"),
            uiOutput("sidebar_favorites")
          )
      ),
      
      div(class = "yt-main-content",
          div(id = "page_home", class = "content-page",
              conditionalPanel(
                condition = "!output.is_logged_in",
                div(class = "welcome-message",
                    h2("Bienvenue sur NetFlop"),
                    p("Connectez-vous pour découvrir des films et recevoir des recommandations personnalisées."),
                    actionButton("welcome_login", "Se connecter", class = "btn-yt-primary")
                )
              ),
              conditionalPanel(
                condition = "output.is_logged_in",
                h2("Films à découvrir"),
                div(class = "yt-filter-bar",
                    selectInput("sort_by", "Trier par:", 
                                choices = c("Les mieux notés" = "note", 
                                            "Les plus récents" = "date",
                                            "Titre" = "titre"),
                                selected = "note"),
                    checkboxInput("show_liked", "Afficher ceux que j'ai déjà aimés", value = FALSE)
                ),
                uiOutput("films_grid")
              )
          ),
          
          # Page Favoris
          div(id = "page_favorites", class = "content-page", style = "display: none;",
              conditionalPanel(
                condition = "output.is_logged_in",
                h2("Mes films préférés"),
                div(class = "favorites-container",
                    div(class = "favorites-chart",
                        plotlyOutput("liked_films_plot")
                    ),
                    div(class = "favorites-list",
                        DT::dataTableOutput("liked_films_table")
                    )
                )
              ),
              conditionalPanel(
                condition = "!output.is_logged_in",
                div(class = "welcome-message",
                    h2("Vous n'êtes pas connecté"),
                    p("Connectez-vous pour voir vos films préférés."),
                    actionButton("welcome_login_favorites", "Se connecter", class = "btn-yt-primary",
                                 onclick = "Shiny.setInputValue('welcome_login', true, {priority: 'event'})")
                )
              )
          ),
          
          # Page Admin
          div(id = "page_admin", class = "content-page", style = "display: none;",
              h2("Recommandations de groupe"),
              p("Sélectionnez les utilisateurs pour former un groupe et obtenez des recommandations communes."),
              div(class = "admin-container",
                  div(class = "users-panel",
                      div(id = "users_select_panel", class = "users-grid",
                          uiOutput("admin_users_list")
                      ),
                      actionButton("validate_users", "Générer des recommandations", 
                                   class = "btn-yt-primary", style = "width: 100%; margin-top: 15px;")
                  ),
                  div(class = "recommendations-panel", id = "recommendation_panel",
                      h3("Films recommandés pour ce groupe"),
                      div(class = "recommendations-chart",
                          plotlyOutput("group_genres_plot")
                      ),
                      div(class = "recommendations-list",
                          DT::dataTableOutput("recommended_films")
                      )
                  )
              )
          ),
          
          # Page Paramètres
          div(id = "page_settings", class = "content-page", style = "display: none;",
              h2("Paramètres de l'application"),
              div(class = "settings-section",
                  h3("Base de données"),
                  div(class = "settings-grid",
                      div(
                        textInput("db_host", "Hôte", value = "db")
                      ),
                      div(
                        textInput("db_name", "Nom de la base", value = "db")
                      ),
                      div(
                        textInput("db_user", "Utilisateur", value = "db")
                      ),
                      div(
                        passwordInput("db_password", "Mot de passe", value = "db")
                      )
                  ),
                  actionButton("save_db_settings", "Sauvegarder", class = "btn-yt-primary")
              ),
              div(class = "settings-section",
                  h3("Algorithme de recommandation"),
                  div(class = "settings-grid",
                      div(
                        selectInput("algo_type", "Type d'algorithme",
                                    choices = c("Filtrage collaboratif", 
                                                "Content-based", 
                                                "Hybride"),
                                    selected = "Filtrage collaboratif")
                      ),
                      div(
                        sliderInput("min_rating", "Note minimale pour recommandation", 
                                    min = 1, max = 10, value = 7, step = 0.5)
                      )
                  ),
                  div(
                    sliderInput("max_recommendations", "Nombre max de recommandations", 
                                min = 5, max = 50, value = 10, step = 5, width = "100%")
                  ),
                  actionButton("save_algo_settings", "Appliquer", class = "btn-yt-primary")
              )
          )
      )
  )
)

server <- function(input, output, session) {
  values <- reactiveValues(
    loggedIn = FALSE,
    currentUser = NULL,
    userId = NULL,
    selectedUsers = NULL,
    selectedFilmId = NULL,
    filmsData = NULL,
    likedFilmsData = NULL,
    updateCounter = 0,
    activeTab = "home",
    adminRefreshCounter = 0,
    dbSettings = list(
      host = "db",
      name = "db",
      user = "db",
      password = "db",
      port = 3306
    )
  )
  
  # Fonction pour gérer les onglets actifs et la navigation
  observeEvent(input$active_tab, {
    shinyjs::hide("page_home")
    shinyjs::hide("page_favorites")
    shinyjs::hide("page_admin")
    shinyjs::hide("page_settings")
    
    shinyjs::removeClass(selector = ".nav-item", class = "active")
    
    if(input$active_tab == "home") {
      shinyjs::show("page_home")
      shinyjs::addClass(id = "nav_home", class = "active")
    } else if(input$active_tab == "favorites") {
      shinyjs::show("page_favorites")
      shinyjs::addClass(id = "nav_favorites", class = "active")
    } else if(input$active_tab == "admin") {
      shinyjs::show("page_admin")
      shinyjs::addClass(id = "nav_admin", class = "active")
    } else if(input$active_tab == "settings") {
      shinyjs::show("page_settings")
      shinyjs::addClass(id = "nav_settings", class = "active")
    }
    if(input$active_tab == "admin") {
      values$adminRefreshCounter <- values$adminRefreshCounter + 1
    }
  })
  
  get_db_connection <- function() {
    tryCatch({
      dbConnect(
        RMySQL::MySQL(),
        dbname = values$dbSettings$name,
        host = values$dbSettings$host,
        port = values$dbSettings$port,
        user = values$dbSettings$user,
        password = values$dbSettings$password,
        bigint = "numeric"
      )
    }, error = function(e) {
      showNotification(paste("Erreur de connexion à la base de données:", e$message), 
                       type = "error", duration = 10)
      return(NULL)
    })
  }
  
  execute_query <- function(query, params = NULL) {
    con <- get_db_connection()
    if (is.null(con)) return(NULL)
    
    result <- tryCatch({
      if (is.null(params)) {
        dbGetQuery(con, query)
      } else {
        dbGetQuery(con, query, params)
      }
    }, error = function(e) {
      showNotification(paste("Erreur d'exécution de la requête:", e$message), 
                       type = "error", duration = 10)
      return(NULL)
    })
    
    dbDisconnect(con)
    return(result)
  }
  
  is_film_liked <- function(film_id, user_id) {
    result <- execute_query(paste0(
      "SELECT COUNT(*) as count FROM aime WHERE tconst = '", 
      film_id, "' AND idUser = ", user_id
    ))
    
    return(!is.null(result) && result$count > 0)
  }
  
  load_films_data <- reactive({
    values$updateCounter
    
    if (!values$loggedIn) {
      return(NULL)
    }
    
    query <- paste0(
      "SELECT m.tconst, m.titre, m.titreOriginal, m.dateSortie, m.temps, m.note,
       CASE WHEN a.idUser IS NOT NULL THEN 1 ELSE 0 END as is_liked
       FROM media m 
       LEFT JOIN aime a ON m.tconst = a.tconst AND a.idUser = ", values$userId
    )
    
    if (input$sort_by == "note") {
      query <- paste0(query, " ORDER BY m.note DESC")
    } else if (input$sort_by == "date") {
      query <- paste0(query, " ORDER BY m.dateSortie DESC")
    } else {
      query <- paste0(query, " ORDER BY m.titre ASC")
    }
    
    films_data <- execute_query(query)
    
    if (!input$show_liked && !is.null(films_data)) {
      films_data <- films_data[films_data$is_liked == 0, ]
    }
    
    return(films_data)
  })
  
  filtered_films_data <- reactive({
    search_text <- tolower(input$search_input)
    films_data <- load_films_data()
    
    if (!is.null(films_data) && nrow(films_data) > 0 && !is.null(search_text) && nchar(search_text) > 0) {
      films_data[grepl(search_text, tolower(films_data$titre)), ]
    } else {
      films_data
    }
  })
  
  observeEvent(input$search_input, {
    updateNumericInput(session, "page_num", value = 1)
  })
  
  load_liked_films_data <- reactive({
    values$updateCounter
    
    if (!values$loggedIn) {
      return(NULL)
    }
    
    liked_films <- execute_query(paste0(
      "SELECT m.tconst, m.titre, m.titreOriginal, m.dateSortie, 
              COALESCE(a.note, 'Non noté') as note 
       FROM aime a 
       JOIN media m ON a.tconst = m.tconst 
       WHERE a.idUser = ", values$userId, " 
       ORDER BY a.note DESC, m.titre"
    ))
    
    return(liked_films)
  })
  
  output$is_logged_in <- reactive({
    return(values$loggedIn)
  })
  outputOptions(output, "is_logged_in", suspendWhenHidden = FALSE)
  
  output$header_username <- renderText({
    if (values$loggedIn) {
      return(values$currentUser)
    }
    return("")
  })
  
  output$sidebar_favorites <- renderUI({
    req(values$loggedIn)
    liked_films <- load_liked_films_data()
    
    if (!is.null(liked_films) && nrow(liked_films) > 0) {
      sidebar_films <- liked_films
      
      tagList(
        lapply(1:nrow(sidebar_films), function(i) {
          div(class = "sidebar-favorite-item",
              sidebar_films$titre[i],
              tags$small(class = "sidebar-favorite-rating", 
                         paste("Note:", sidebar_films$note[i]))
          )
        })
      )
    } else {
      div("Aucun film favori", class = "sidebar-no-favorites")
    }
  })
  
  # Fonction pour gérer les erreurs d'image et afficher l'image par défaut
  safe_movie_poster <- function(movie_title) {
    tryCatch({
      poster_url <- get_movie_poster(movie_title)
      return(poster_url)
    }, error = function(e) {
      return("https://via.placeholder.com/300x450/303030/FFFFFF?text=?")
    })
  }
  
output$films_grid <- renderUI({
  films_data <- filtered_films_data()
  
  if (is.null(films_data) || nrow(films_data) == 0) {
    return(div(class = "no-films",
               "Aucun film à afficher pour le moment."
    ))
  }
  
  page <- input$page_num
  if (is.null(page)) page <- 1
  
  films_per_page <- 20
  total_pages <- ceiling(nrow(films_data) / films_per_page)
  
  start_idx <- (page - 1) * films_per_page + 1
  end_idx <- min(page * films_per_page, nrow(films_data))
  
  if (start_idx > nrow(films_data)) {
    start_idx <- 1
    updateNumericInput(session, "page_num", value = 1)
  }
  
  page_films <- films_data[start_idx:end_idx, ]
  
  films_cards <- lapply(1:nrow(page_films), function(i) {
    film <- page_films[i, ]
    film_id <- film$tconst
    is_liked <- film$is_liked == 1
    
    poster_url <- safe_movie_poster(film$titreOriginal)
    
    div(class = "film-card",
        onclick = sprintf("Shiny.setInputValue('film_clicked', '%s', {priority: 'event'})", film_id),
        div(class = "film-poster-container",
            tags$img(src = poster_url, 
                     class = "film-poster", 
                     onerror = "this.onerror=null; this.src='interog.jpg';"),
            div(class = "film-info-overlay",
                if (is_liked) {
                  actionButton(paste0("unlike_", film_id), 
                              HTML("❤️"), 
                              class = "btn-film-like liked",
                              onclick = sprintf("event.stopPropagation(); Shiny.setInputValue('unlike_action', '%s', {priority: 'event'})", film_id))
                } else {
                  actionButton(paste0("like_", film_id), 
                              HTML("🤍"), 
                              class = "btn-film-like",
                              onclick = sprintf("event.stopPropagation(); Shiny.setInputValue('like_action', '%s', {priority: 'event'})", film_id))
                }
            )
        ),
        div(class = "film-info",
            div(class = "film-title", film$titre),
            div(class = "film-meta",
                span(class = "film-year", substr(film$dateSortie, 1, 4)),
                span(" | "),
                span(class = "film-duration", paste0(film$temps, " min"))
            )
        )
    )
  })
  
  tagList(
    div(class = "films-grid", films_cards),
    div(class = "pagination-controls",
        if (page > 1) {
          actionButton("prev_page", "Précédent", class = "btn-yt-secondary")
        },
        span(class = "page-indicator", paste("Page", page, "sur", total_pages)),
        if (page < total_pages) {
          actionButton("next_page", "Suivant", class = "btn-yt-primary")
        }
    )
  )
})
  
  # Gestion du clic sur un film pour afficher les détails
  observeEvent(input$film_clicked, {
    req(values$loggedIn)
    film_id <- input$film_clicked
    
    if (!is.null(film_id)) {
      # Récupérer les détails du film
      film_info <- execute_query(paste0(
        "SELECT titre, titreOriginal, temps, dateSortie, nbVote, note 
        FROM media 
        WHERE tconst = '", film_id, "'"
      ))
      
      # Récupérer les participants
      participants <- execute_query(paste0(
        "SELECT p.nom 
         FROM participe pa 
         INNER JOIN personne p ON pa.idP = p.idP 
         WHERE pa.tconst = '", film_id, "'"
      ))
      
      if (!is.null(film_info) && nrow(film_info) > 0) {
        poster_url <- safe_movie_poster(film_info$titreOriginal)
        
        # Préparer la liste des participants
        participants_list <- if (!is.null(participants) && nrow(participants) > 0) {
          paste(participants$nom, collapse = ", ")
        } else {
          "Aucun participant trouvé"
        }
        
        showModal(modalDialog(
          title = film_info$titre,
          div(class = "film-details-modal",
              div(class = "film-details-poster",
                  tags$img(src = poster_url, 
                           class = "film-details-poster-img", 
                           onerror = "this.onerror=null; this.src='interog.jpg';")
              ),
              div(class = "film-details-info",
                  p(strong("Titre : "), film_info$titre),
                  p(strong("Durée : "), paste(film_info$temps, "minutes")),
                  p(strong("Date de sortie : "), film_info$dateSortie),
                  p(strong("Nombre de votes : "), format(film_info$nbVote, big.mark = ",")),
                  p(strong("Note : "), sprintf("%.1f/10", film_info$note)),
                  p(strong("Participants : "), participants_list)
              )
          ),
          footer = modalButton("Fermer"),
          easyClose = TRUE,
          size = "m"
        ))
      } else {
        showNotification("Impossible de récupérer les détails du film", type = "error")
      }
    }
  })
  
  observeEvent(input$like_action, {
    req(values$loggedIn)
    film_id <- input$like_action
    
    if (!is.null(film_id)) {
      film_info <- execute_query(paste0(
        "SELECT titre, dateSortie FROM media WHERE tconst = '", film_id, "'"
      ))
      
      if (!is.null(film_info) && nrow(film_info) > 0) {
        values$selectedFilmId <- film_id
        
        showModal(modalDialog(
          title = "Noter ce film",
          div(class = "rating-modal-content",
              h4(paste(film_info$titre, "(", substr(film_info$dateSortie, 1, 4), ")")),
              p("Choisissez votre note:"),
              div(class = "rating-stars-container",
                  div(class = "rating-stars",
                      lapply(1:10, function(i) {
                        actionButton(paste0("rate_", i), HTML("★"), 
                                    class = "btn-star", 
                                    onclick = sprintf("Shiny.setInputValue('selected_rating', %d, {priority: 'event'})", i))
                      })
                  ),
                  textOutput("rating_value")
              )
          ),
          footer = div(class = "modal-footer-youtube",
                      actionButton("save_like", "Enregistrer", class = "btn-yt-primary"),
                      modalButton("Annuler")
          ),
          easyClose = TRUE,
          size = "s"
        ))
      }
    }
  })
  
  observeEvent(input$unlike_action, {
    req(values$loggedIn)
    film_id <- input$unlike_action
    
    if (!is.null(film_id)) {
      query <- paste0(
        "DELETE FROM aime WHERE tconst = '", film_id, "' AND idUser = ", values$userId
      )
      
      result <- execute_query(query)
      
      if (!is.null(result)) {
        showNotification("Film retiré de vos favoris", type = "message")
        values$updateCounter <- values$updateCounter + 1
      } else {
        showNotification("Erreur lors de la suppression", type = "error")
      }
    }
  })
  
  observeEvent(input$next_page, {
    current_page <- input$page_num
    if (is.null(current_page)) current_page <- 1
    updateNumericInput(session, "page_num", value = current_page + 1)
  })
  
  observeEvent(input$prev_page, {
    current_page <- input$page_num
    if (is.null(current_page)) current_page <- 1
    if (current_page > 1) {
      updateNumericInput(session, "page_num", value = current_page - 1)
    }
  })
  
  output$rating_value <- renderText({
    if (!is.null(input$selected_rating) && input$selected_rating > 0) {
      paste("Votre note :", input$selected_rating, "/10")
    } else {
      "Cliquez sur une étoile pour noter"
    }
  })
  
  output$liked_films_table <- DT::renderDataTable({
    req(values$loggedIn)
    liked_films <- load_liked_films_data()
    
    if (is.null(liked_films) || nrow(liked_films) == 0) {
      return(data.frame(Message = "Vous n'avez pas encore liké de films"))
    }
    
    liked_films$Actions <- sapply(seq_len(nrow(liked_films)), function(i) {
      film_id <- liked_films$tconst[i]
      sprintf('<button class="btn btn-sm action-button unlike-btn" onclick="Shiny.setInputValue(\'unlike_fav_button\', \'%s\', {priority: \'event\'})">❌︎</button>', film_id)
    })
    
    liked_films$Poster <- sapply(seq_len(nrow(liked_films)), function(i) {
      poster_url <- safe_movie_poster(liked_films$titreOriginal[i])
      sprintf("<img src='%s' height='80' class='recommendation-poster' onerror=\"this.onerror=null; this.src='https://via.placeholder.com/80x120/303030/FFFFFF?text=N/A';\"/>", poster_url)
    })
    
    liked_films_display <- liked_films[, c("Poster", "titre", "titreOriginal", "dateSortie", "note", "Actions")]
    names(liked_films_display) <- c("Poster", "Titre", "Titre Original", "Date de sortie", "Ma note", "Actions")
    
    DT::datatable(
      liked_films_display,
      options = list(
        pageLength = 5,
        lengthMenu = c(5, 10, 15),
        dom = 'tp',
        columnDefs = list(
          list(targets = c(0, 5), orderable = FALSE),
          list(targets = 0, width = "100px"),
          list(targets = 5, width = "50px"),
          list(targets = 2, visible = FALSE)
        )
      ),
      escape = FALSE,
      rownames = FALSE,
      class = "youtube-table"
    )
  })
  
  output$liked_films_plot <- renderPlotly({
    req(values$loggedIn)
    values$updateCounter
    
    liked_movies_data <- execute_query(paste0(
      "SELECT m.titre, a.note 
       FROM aime a 
       JOIN media m ON a.tconst = m.tconst 
       WHERE a.idUser = ", values$userId, " 
       AND a.note IS NOT NULL
       ORDER BY a.note DESC, m.titre"
    ))
    
    if (!is.null(liked_movies_data) && nrow(liked_movies_data) > 0) {
      liked_movies_data$titre_court <- sapply(liked_movies_data$titre, function(t) {
        if(nchar(t) > 20) paste0(substr(t, 1, 17), "...") else t
      })
      
      plot_height <- max(400, nrow(liked_movies_data) * 30)
      
      p <- plot_ly(liked_movies_data, x = ~titre_court, y = ~note, 
                   type = "bar", 
                   text = ~titre,
                   hoverinfo = "text+y",
                   height = plot_height,
                   marker = list(color = "#FF0000", 
                                 line = list(color = "#CC0000", width = 1.5)))
      
      p <- p %>% layout(
        title = list(
          text = "",
          font = list(family = "Roboto", size = 20, color = "#FFFFFF")
        ),
        xaxis = list(
          title = "", 
          tickangle = 45,
          tickfont = list(family = "Roboto", size = 10, color = "#DDDDDD")
        ),
        yaxis = list(
          title = "Note", 
          range = c(0, 10),
          tickfont = list(family = "Roboto", size = 12, color = "#DDDDDD")
        ),
        margin = list(b = 120),
        plot_bgcolor = "#2A2A2A",
        paper_bgcolor = "#2A2A2A"
      )
      
      return(p)
    } else {
      plot_ly(height = 400) %>% layout(
        title = "Notez des films pour voir vos préférences",
        plot_bgcolor = "#2A2A2A",
        paper_bgcolor = "#2A2A2A",
        font = list(color = "#FFFFFF")
      )
    }
  })
  
  output$admin_users_list <- renderUI({
    values$adminRefreshCounter
    users <- execute_query("SELECT idUser, nom FROM user ORDER BY nom")
    
    if (is.null(users) || nrow(users) == 0) {
      return(div("Aucun utilisateur trouvé.", 
                 class = "no-users"))
    }
    
    user_cards <- lapply(seq_along(users$nom), function(i) {
      user_id <- paste0("admin_user_", users$idUser[i])
      
      div(class = "user-card-modern",
          div(class = "user-card-content",
              div(class = "user-avatar-container",
                  img(src = "https://cdn-icons-png.flaticon.com/512/847/847969.png", 
                      class = "user-avatar-modern")
              ),
              div(class = "user-info",
                  div(class = "user-name-modern", users$nom[i]),
                  div(class = "user-status", "Membre actif")
              ),
              div(class = "user-select-modern",
                  checkboxInput(user_id, label = NULL, value = FALSE)
              )
          )
      )
    })
    
    div(class = "users-grid-modern", user_cards)
  })
  
  output$group_genres_plot <- renderPlotly({
    req(values$selectedUsers)
    
    if (is.null(values$pythonRecommendations) || !is.data.frame(values$pythonRecommendations)) {
      return(plot_ly(height = 400) %>% layout(
        title = "Aucune recommandation disponible",
        plot_bgcolor = "#2A2A2A",
        paper_bgcolor = "#2A2A2A",
        font = list(color = "#FFFFFF")
      ))
    }
    
    recommended <- values$pythonRecommendations
    
    if (!is.null(recommended) && nrow(recommended) > 0) {
      colnames(recommended) <- c("Titre", "Genres", "Note", "Similarity", "FinalScore")
      
      p <- plot_ly(
        recommended,
        x = ~Note,
        y = ~Similarity,
        z = ~FinalScore,
        text = ~paste("Titre:", Titre, "<br>Genres:", Genres),
        type = "scatter3d",
        mode = "markers",
        height = 400,
        marker = list(
          size = 8,
          color = ~FinalScore,
          colorscale = list(
            c(0, "#000000"),
            c(0.25, "#FFFF00"),
            c(0.5, "#FF9900"),
            c(0.75, "#FF69B4"),
            c(1, "#FF0000")
          ),
          showscale = TRUE,
          colorbar = list(
            title = "Score",
            titlefont = list(family = "Roboto", color = "#FFFFFF"),
            tickfont = list(family = "Roboto", color = "#DDDDDD")
          )
        )
      )
      
      p <- p %>% layout(
        title = list(
          text = "Films recommandés pour ce groupe",
          font = list(family = "Roboto", size = 20, color = "#FFFFFF")
        ),
        scene = list(
          xaxis = list(title = "Note IMDb", titlefont = list(family = "Roboto", color = "#DDDDDD")),
          yaxis = list(title = "Similarité", titlefont = list(family = "Roboto", color = "#DDDDDD")),
          zaxis = list(title = "Score final", titlefont = list(family = "Roboto", color = "#DDDDDD")),
          bgcolor = "#1A1A1A"
        ),
        margin = list(l = 0, r = 0, b = 0, t = 50),
        paper_bgcolor = "#2A2A2A"
      )
      
      return(p)
    } else {
      plot_ly(height = 400) %>% layout(
        title = "Aucune recommandation disponible",
        plot_bgcolor = "#2A2A2A",
        paper_bgcolor = "#2A2A2A",
        font = list(color = "#FFFFFF")
      )
    }
  })
  
  output$recommended_films <- DT::renderDataTable({
    req(values$selectedUsers)
    req(values$pythonRecommendations)
    
    recommended <- values$pythonRecommendations
    
    if (!is.null(recommended) && nrow(recommended) > 0) {
      recommended_display <- recommended
      colnames(recommended_display) <- c("Titre", "Genres", "Note", "Similarity", "Final Score")
      
      recommended_display$Poster <- sapply(recommended_display$Titre, function(titre) {
        poster_url <- safe_movie_poster(titre)
        sprintf("<img src='%s' height='80' class='recommendation-poster' onerror=\"this.onerror=null; this.src='https://via.placeholder.com/80x120/303030/FFFFFF?text=N/A';\"/>", poster_url)
      })
      
      recommended_display <- recommended_display[, c("Poster","Titre", "Genres", "Note", "Final Score")]
      
      DT::datatable(
        recommended_display,
        options = list(
          pageLength = 5,
          dom = 'tp',
          columnDefs = list(
            list(targets = 0, width = "100px", orderable = FALSE)
          )
        ),
        rownames = FALSE,
        escape = FALSE,
        class = "youtube-table recommendations-table"
      )
    } else {
      return(data.frame(Message = "Aucune recommandation disponible"))
    }
  })
  
  observeEvent(input$display_login, {
    shinyjs::show("login_panel")
  })
  
  observeEvent(input$close_login, {
    shinyjs::hide("login_panel")
  })
  
  observeEvent(input$welcome_login, {
    shinyjs::show("login_panel")
  })
  
  observeEvent(input$login_button, {
    if (nchar(input$user_login) > 0) {
      user_data <- execute_query(paste0("SELECT idUser, nom FROM user WHERE nom = '", 
                                        input$user_login, "'"))
      
      if (!is.null(user_data) && nrow(user_data) > 0) {
        values$loggedIn <- TRUE
        values$currentUser <- user_data$nom
        values$userId <- user_data$idUser
        values$updateCounter <- 0
        
        shinyjs::hide("login_panel")
        
        showNotification(paste("Bienvenue,", values$currentUser, "!"), 
                         type = "message", duration = 3)
      } else {
        showNotification("Utilisateur non trouvé", type = "error")
      }
    } else {
      showNotification("Veuillez entrer un nom d'utilisateur", type = "warning")
    }
  })
  
  observeEvent(input$logout_button, {
    temp_user <- values$currentUser
    
    values$loggedIn <- FALSE
    values$currentUser <- NULL
    values$userId <- NULL
    values$selectedFilmId <- NULL
    values$selectedUsers <- NULL
    values$updateCounter <- 0
    
    updateTextInput(session, "user_login", value = "")
    updateTextInput(session, "search_input", value = "")
    updateNumericInput(session, "page_num", value = 1)
    
    shinyjs::runjs("Shiny.setInputValue('active_tab', 'home')")
    
    shinyjs::show("login_panel")
    
    showNotification(paste("Au revoir", temp_user, "! Vous avez été déconnecté."), type = "message")
  })
  
  observeEvent(input$new_user_button, {
    if (nchar(input$user_login) > 0) {
      user_check <- execute_query(paste0("SELECT idUser FROM user WHERE nom = '", 
                                         input$user_login, "'"))
      
      if (!is.null(user_check) && nrow(user_check) == 0) {
        result <- execute_query(paste0("INSERT INTO user (nom) VALUES ('", 
                                       input$user_login, "')"))
        
        if (!is.null(result)) {
          new_user <- execute_query(paste0("SELECT idUser, nom FROM user WHERE nom = '", 
                                           input$user_login, "'"))
          
          values$loggedIn <- TRUE
          values$currentUser <- new_user$nom
          values$userId <- new_user$idUser
          values$updateCounter <- 0
          
          shinyjs::hide("login_panel")
          
          showNotification("Compte créé avec succès !", type = "message")
        }
      } else {
        showNotification("Ce nom d'utilisateur existe déjà", type = "error")
      }
    } else {
      showNotification("Veuillez entrer un nom d'utilisateur", type = "warning")
    }
  })
  
  observeEvent(input$unlike_fav_button, {
    req(values$loggedIn, input$unlike_fav_button)
    
    film_id <- input$unlike_fav_button
    
    query <- paste0(
      "DELETE FROM aime WHERE tconst = '", film_id, "' AND idUser = ", values$userId
    )
    
    result <- execute_query(query)
    
    if (!is.null(result)) {
      showNotification("Film retiré de vos favoris", type = "message")
      values$updateCounter <- values$updateCounter + 1
    } else {
      showNotification("Erreur lors de la suppression", type = "error")
    }
  })
  
  observeEvent(input$save_like, {
    req(values$loggedIn, values$selectedFilmId)
    
    rating <- input$selected_rating
    if (is.null(rating) || rating == 0) rating <- 5
    
    exists_query <- paste0(
      "SELECT COUNT(*) as count FROM aime WHERE tconst = '", 
      values$selectedFilmId, "' AND idUser = ", values$userId
    )
    
    exists_result <- execute_query(exists_query)
    
    if (!is.null(exists_result) && exists_result$count > 0) {
      query <- paste0(
        "UPDATE aime SET note = ", rating, " WHERE tconst = '",
        values$selectedFilmId, "' AND idUser = ", values$userId
      )
    } else {
      query <- paste0(
        "INSERT INTO aime (tconst, idUser, note) VALUES ('",
        values$selectedFilmId, "', ", values$userId, ", ", rating, ")"
      )
    }
    
    result <- execute_query(query)
    
    if (!is.null(result)) {
      message <- paste0("Film ajouté à vos favoris ! Note: ", rating, "/10")
      showNotification(message, type = "message")
      removeModal()
      values$updateCounter <- values$updateCounter + 1
    } else {
      showNotification("Erreur lors de l'enregistrement", type = "error")
    }
  })
  
  observeEvent(input$validate_users, {
    selected_user_ids <- c()
    
    for (name in names(input)) {
      if (grepl("^admin_user_", name) && input[[name]]) {
        user_id <- as.numeric(sub("admin_user_", "", name))
        selected_user_ids <- c(selected_user_ids, user_id)
      }
    }
    
    if (length(selected_user_ids) > 0) {
      values$selectedUsers <- selected_user_ids
      shinyjs::show("recommendation_panel")
      
      selected_names <- execute_query(paste0(
        "SELECT nom FROM user WHERE idUser IN (", 
        paste(selected_user_ids, collapse = ","), ")"
      ))
      
      showNotification(paste("Utilisateurs sélectionnés :", 
                            paste(selected_names$nom, collapse = ", ")), 
                       type = "message")
      
      values$pythonRecommendations <- call_python_recommender(selected_user_ids)
    } else {
      showNotification("Veuillez sélectionner au moins un utilisateur", 
                       type = "warning")
      shinyjs::hide("recommendation_panel")
    }
  })
  
  call_python_recommender <- function(user_ids) {
    temp_file <- tempfile(fileext = ".json")
    writeLines(jsonlite::toJSON(user_ids), temp_file)
    
    python_cmd <- paste0("python3 /home/rstudio/scripts_R/code/main2.py --user_ids=", temp_file, " --format=json")
    
    result <- tryCatch({
      system(python_cmd, intern = TRUE)
    }, error = function(e) {
      showNotification(paste("Erreur lors de l'exécution du script Python:", e$message), 
                       type = "error", duration = 10)
      return(NULL)
    })
    
    unlink(temp_file)
    
    if (!is.null(result) && length(result) > 0) {
      recommendations <- tryCatch({
        jsonlite::fromJSON(result)
      }, error = function(e) {
        showNotification(paste("Erreur lors de la lecture des recommandations:", e$message), 
                         type = "error", duration = 10)
        return(data.frame())
      })
      return(recommendations)
    } else {
      return(data.frame())
    }
  }
  
  observeEvent(input$save_db_settings, {
    values$dbSettings$host <- input$db_host
    values$dbSettings$name <- input$db_name
    values$dbSettings$user <- input$db_user
    values$dbSettings$password <- input$db_password
    
    con <- get_db_connection()
    if (!is.null(con)) {
      dbDisconnect(con)
      showNotification("Connexion à la base de données réussie", type = "message")
    }
  })
  
  observeEvent(input$save_algo_settings, {
    showNotification("Paramètres de l'algorithme sauvegardés", type = "message")
  })
  
  session$sendCustomMessage(type = "jsCode", list(
    code = "
      $(document).on('click', '.btn-star', function() {
        var rating = $(this).attr('id').split('_')[1];
        $('.btn-star').css('color', '#d0d0d0');
        for (var i = 1; i <= rating; i++) {
          $('#rate_' + i).css('color', '#FF0000');
        }
      });
    "
  ))
  
  insertUI(
    selector = "head",
    ui = tags$style(HTML("
      @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap');
      
      body {
        font-family: 'Roboto', sans-serif;
        background-color: #121212;
        color: #FFFFFF;
        margin: 0;
        padding: 0;
      }
      
      h1, h2, h3, h4 {
        font-weight: 500;
        color: #FFFFFF;
      }
      
      .youtube-header {
        background-color: #202020;
        display: flex;
        align-items: center;
        padding: 0 16px;
        height: 56px;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        z-index: 100;
        box-shadow: 0 1px 5px rgba(0, 0, 0, 0.3);
      }
      
      .yt-logo-container {
        display: flex;
        align-items: center;
        width: 200px;
      }
      
      .yt-title-image {
        margin: 0;
        height: 30px;
      }
      
      .yt-search-container {
        flex-grow: 1;
        margin: 0 20px;
        display: flex;
        align-items: center;
      }
      
      .yt-search-container .form-control {
        height: 38px;
        border-radius: 40px;
        padding-left: 20px;
        border: 1px solid #303030;
        background-color: #121212;
        color: #FFFFFF;
        width: 100%;
        max-width: none;
      }
      
      .yt-user-container {
        display: flex;
        align-items: center;
        min-width: 150px;
      }
      
      .yt-user-info {
        display: flex;
        align-items: center;
      }
      
      .yt-avatar {
        width: 32px;
        height: 32px;
        border-radius: 50%;
        margin-right: 10px;
      }
      
      .btn-youtube-login {
        background-color: #202020;
        color: #3ea6ff;
        border: 1px solid #3ea6ff;
        border-radius: 3px;
        font-size: 14px;
        padding: 8px 16px;
      }
      
      .btn-yt-logout {
        background: transparent;
        border: none;
        color: #FFFFFF;
        margin-left: 15px;
        padding: 6px 12px;
        border-radius: 2px;
        font-size: 14px;
      }
      
      .btn-yt-logout:hover {
        background-color: rgba(255, 0, 0, 0.2);
      }
      
      .yt-login-panel {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: rgba(0, 0, 0, 0.7);
        z-index: 1000;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      
      .yt-login-content {
        background-color: #202020;
        border-radius: 8px;
        padding: 24px;
        width: 350px;
        box-shadow: 0 4px 24px rgba(0, 0, 0, 0.5);
        position: relative;
      }
      
      .yt-login-content .form-control {
        background-color: #121212;
        border: 1px solid #303030;
        color: #FFFFFF;
      }
      
      .yt-login-buttons {
        display: flex;
        justify-content: space-between;
        margin-top: 24px;
      }
      
      .btn-close-login {
        position: absolute;
        top: 8px;
        right: 8px;
        background: transparent;
        border: none;
        font-size: 20px;
        cursor: pointer;
        color: #FFFFFF;
      }
      
      .yt-content {
        display: flex;
        margin-top: 56px;
        height: calc(100vh - 56px);
      }
      
      .yt-sidebar {
        width: 240px;
        background-color: #202020;
        padding: 12px 0;
        overflow-y: auto;
        height: 100%;
        position: fixed;
        top: 56px;
        left: 0;
      }
      
      .nav-item {
        padding: 12px 24px;
        display: flex;
        align-items: center;
        cursor: pointer;
        font-size: 14px;
        color: #AAAAAA;
      }
      
      .nav-item:hover {
        background-color: #303030;
        color: #FFFFFF;
      }
      
      .nav-item.active {
        background-color: #383838;
        color: #FFFFFF;
      }
      
      .nav-item i {
        margin-right: 16px;
        width: 24px;
        text-align: center;
      }
      
      .sidebar-subtitle {
        padding: 12px 24px;
        font-size: 14px;
        color: #AAAAAA;
        margin: 0;
      }
      
      .sidebar-favorite-item {
        padding: 8px 24px;
        font-size: 13px;
        color: #FFFFFF;
        cursor: pointer;
      }
      
      .sidebar-favorite-item:hover {
        background-color: #303030;
      }
      
      .sidebar-favorite-rating {
        display: block;
        color: #AAAAAA;
        font-size: 12px;
      }
      
      .sidebar-no-favorites {
        padding: 12px 24px;
        font-size: 13px;
        color: #AAAAAA;
      }
      
      .yt-main-content {
        margin-left: 240px;
        padding: 24px;
        width: calc(100% - 240px);
        overflow-y: auto;
        height: calc(100vh - 56px);
      }
      
      .content-page {
        display: block;
      }
      
      .welcome-message {
        text-align: center;
        margin-top: 50px;
      }
      
      .welcome-message h2 {
        font-size: 28px;
        margin-bottom: 16px;
      }
      
      .welcome-message p {
        font-size: 16px;
        color: #AAAAAA;
        margin-bottom: 24px;
      }
      
      .yt-filter-bar {
        display: flex;
        align-items: center;
        margin-bottom: 24px;
        gap: 20px;
      }
      
      .yt-filter-bar select, .yt-filter-bar input[type='checkbox'] {
        background-color: #121212;
        border: 1px solid #303030;
        color: #FFFFFF;
      }
      
      .films-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
        gap: 24px;
      }
      
      .film-card {
        background-color: #202020;
        border-radius: 8px;
        overflow: hidden;
        cursor: pointer;
        transition: transform 0.2s;
      }
      
      .film-card:hover {
        transform: translateY(-5px);
      }
      
      .film-poster-container {
        position: relative;
        width: 100%;
        padding-top: 150%;
      }
      
      .film-poster {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
      
      .film-info-overlay {
        position: absolute;
        top: 8px;
        right: 8px;
      }
      
      .btn-film-like {
        background: transparent;
        border: none;
        font-size: 24px;
        cursor: pointer;
      }
      
      .btn-film-like.liked {
        color: #FF0000;
      }
      
      .film-info {
        padding: 12px;
      }
      
      .film-title {
        font-size: 14px;
        font-weight: 500;
        margin-bottom: 8px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
      
      .film-meta {
        font-size: 12px;
        color: #AAAAAA;
      }
      
      .no-films {
        text-align: center;
        color: #AAAAAA;
        font-size: 16px;
        margin-top: 50px;
      }
      
      .pagination-controls {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 24px;
        gap: 16px;
      }
      
      .page-indicator {
        font-size: 14px;
        color: #AAAAAA;
      }
      
      .btn-yt-primary {
        background-color: #FF0000;
        border: none;
        color: #FFFFFF;
        padding: 8px 16px;
        border-radius: 2px;
        font-size: 14px;
      }
      
      .btn-yt-primary:hover {
        background-color: #CC0000;
      }
      
      .btn-yt-secondary {
        background-color: #303030;
        border: 1px solid #606060;
        color: #FFFFFF;
        padding: 8px 16px;
        border-radius: 2px;
        font-size: 14px;
      }
      
      .btn-yt-secondary:hover {
        background-color: #383838;
      }
      
/* Améliorations pour le conteneur de notation */
.rating-modal-content {
  text-align: center;
  padding: 20px;
  max-width: 100%;
}

/* Amélioration du conteneur des étoiles */
.rating-stars-container {
  margin: 16px auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

      /* Amélioration de l'affichage des étoiles */
      .rating-stars {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        gap: 10px;
        margin: 0 auto;
        padding: 10px 0;
        width: 100%;
        max-width: 300px;
      }

      /* Style amélioré pour chaque bouton étoile */
      .btn-star {
        background: transparent;
        border: none;
        font-size: 24px;
        color: #d0d0d0;
        cursor: pointer;
        padding: 5px;
        line-height: 1;
        transition: color 0.2s, transform 0.1s;
      }

      /* Animation au survol */
      .btn-star:hover {
        color: #FF0000;
        transform: scale(1.2);
      }

      /* Style pour les étoiles sélectionnées */
      .btn-star.selected {
        color: #FF0000;
      }

      /* Affichage de la note */
      #rating_value {
        margin-top: 10px;
        font-size: 16px;
        font-weight: 500;
      }

      /* Adaptation pour mobile */
      @media (max-width: 480px) {
        .rating-stars {
          max-width: 250px;
        }
        
        .btn-star {
          font-size: 20px;
          padding: 4px;
        }
      }
      
      .modal-footer-youtube {
        display: flex;
        justify-content: space-between;
        width: 100%;
      }
      
      .favorites-container {
        display: flex;
        gap: 24px;
        margin-top: 24px;
      }
      
      .favorites-chart, .favorites-list {
        flex: 1;
      }
      
      .youtube-table {
        background-color: #202020;
        color: #FFFFFF;
      }
      
      .youtube-table th {
        background-color: #303030;
        color: #FFFFFF;
        font-weight: 500;
      }
      
      .youtube-table td {
        border-bottom: 1px solid #303030;
      }
      
      .youtube-table tr:hover {
        background-color: #303030;
      }
      
      .unlike-btn {
        background-color: #FF0000;
        color: #FFFFFF;
      }
      
      .unlike-btn:hover {
        background-color: #CC0000;
      }
      
        .admin-container {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 24px;
        }

        .admin-container {
            grid-template-columns: 1fr;
          }
      
      .users-panel, .recommendations-panel {
        flex: 1;
      }
      
      .users-grid-modern {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
        gap: 16px;
      }
      
      .user-card-modern {
        background-color: #202020;
        border-radius: 8px;
        padding: 12px;
        cursor: pointer;
      }
      
      .user-card-content {
        display: flex;
        align-items: center;
        gap: 12px;
      }
      
      .user-avatar-modern {
        width: 40px;
        height: 40px;
        border-radius: 50%;
      }
      
      .user-info {
        flex-grow: 1;
      }
      
      .user-name-modern {
        font-size: 14px;
        font-weight: 500;
      }
      
      .user-status {
        font-size: 12px;
        color: #AAAAAA;
      }
      
      .user-select-modern {
        display: flex;
        align-items: center;
      }
      
      .recommendations-chart, .recommendations-list {
        margin-top: 24px;
      }
      
      .recommendation-poster {
        border-radius: 4px;
      }
      
      .recommendations-table th:nth-child(1), 
      .recommendations-table td:nth-child(1) {
        width: 100px;
      }
      
      .no-users {
        text-align: center;
        color: #AAAAAA;
        font-size: 16px;
        margin-top: 50px;
      }
      
      .settings-section {
        margin-bottom: 32px;
      }
      
      .settings-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 16px;
        margin-bottom: 16px;
      }
      
      .settings-grid .form-control {
        background-color: #121212;
        border: 1px solid #303030;
        color: #FFFFFF;
      }
      
      .film-details-modal {
        display: flex;
        gap: 24px;
      }
      
      .film-details-poster {
        flex: 0 0 auto;
      }
      
      .film-details-poster-img {
        width: 200px;
        height: 300px;
        object-fit: cover;
        border-radius: 8px;
      }
      
      .film-details-info {
        flex: 1;
      }
      
      .film-details-info p {
        margin: 8px 0;
        font-size: 14px;
      }
      
      .film-details-info strong {
        color: #FFFFFF;
      }
      
      .modal-dialog {
        background-color: #202020;
        color: #FFFFFF;
      }
      
      .modal-header, .modal-footer {
        background-color: #303030;
        border: none;
      }
      
      .modal-title {
        color: #FFFFFF;
      }
      
      .modal-body {
        background-color: #202020;
      }
    "))
  )
}

# Lancer l'application Shiny
shinyApp(ui = ui, server = server)