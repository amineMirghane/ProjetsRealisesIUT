# Documentation : Lancement de l'Application

Cette documentation explique les étapes nécessaires pour lancer l'application via Docker et RStudio.

## Prérequis

- Une machine Linux (soit WSL sous Windows, soit directement un PC Linux)
- Docker installé sur votre système

## Fichier docker-compose.yml

Créez un fichier nommé `docker-compose.yml` avec le contenu suivant :

```yaml
version: "2.2"

services:
  ## Les ports 
  # localhost:8787 => Le RStudio
  # localhost:8788 => Le Phpmyadmin
  # localhost:8789 => Le MYSql
  # localhost:3838 => Shiny
  # Identifiant de la base de donnée db:db (toutes les valeurs c'est db dans l'url)

  # Base de données MySQL
  db:
    image: mysql:latest
    restart: unless-stopped
    environment:
      MYSQL_ROOT_PASSWORD: db
      MYSQL_DATABASE: db
      MYSQL_USER: db
      MYSQL_PASSWORD: db
    ports:
      - "8789:3306"
    volumes:
      - db_data:/var/lib/mysql
      - ./Projet-complet.sql:/docker-entrypoint-initdb.d/Projet-complet.sql

  # Interface PhpMyAdmin
  phpmyadmin:
    image: phpmyadmin/phpmyadmin:latest
    restart: unless-stopped
    depends_on:
      - db
    environment:
      PMA_HOST: db
      PMA_USER: db
      PMA_PASSWORD: db
      MYSQL_ROOT_PASSWORD: db
      UPLOAD_LIMIT: 5G
    ports:
      - "8788:80"

  # Environnement RStudio
  rstudio:
    image: rocker/rstudio
    restart: unless-stopped
    ports:
      - "8787:8787"
      - "3838:3838"
    volumes:
      - ./data/rstudio:/home/rstudio/
      - ./scripts_R:/home/rstudio/scripts_R
    depends_on:
      - db
    environment:
      DISABLE_AUTH: "true"
    command: >
      sh -c "apt-get update &&
            apt-get install -y zlib1g-dev libcurl4-openssl-dev libmysqlclient-dev python3-pip libicu-dev build-essential gfortran libblas-dev liblapack-dev &&
            pip3 install --break-system-packages 'numpy<2' &&
            pip3 install --break-system-packages mysql-connector-python scikit-learn scikit-surprise sqlalchemy pandas pymysql &&
            /init"

volumes:
  db_data:
    driver: local
```

## Étapes d'installation et de lancement

### 1. Installation de Docker

Si Docker n'est pas déjà installé sur votre système, vous devez l'installer :

```bash
# Pour Ubuntu/Debian
sudo apt update
sudo apt install docker.io docker-compose

# Pour vérifier l'installation
docker --version
docker-compose --version
```

### 2. Lancement de l'application via Docker Compose

```bash
# Naviguer vers le répertoire contenant votre fichier docker-compose.yml
cd chemin/vers/votre/projet

# Lancer les conteneurs avec docker-compose
docker-compose up -d
```

> **Note**: Assurez-vous que les fichiers suivants existent dans le répertoire:
> - `Projet-complet.sql` pour initialiser la base de données
> - Un dossier `scripts_R` contenant le fichier `App.R`
> - Un dossier `data` qui contiendra les données RStudio

### 3. Attente de l'initialisation

- Patientez entre 3 et 5 minutes
- Cette attente est nécessaire pour permettre au conteneur RStudio de s'initialiser correctement
- Pendant cette période, les packages R requis sont installés dans le conteneur

### 4. Connexion à RStudio

- Ouvrez votre navigateur web
- Accédez à l'adresse: `http://localhost:8787`
- Aucune authentification n'est requise (`DISABLE_AUTH: "true"` dans le docker-compose)

### 5. Lancement de l'application

Une fois connecté à RStudio:
1. Ouvrez le gestionnaire de fichiers dans l'interface RStudio
2. Naviguez vers `/home/rstudio/scripts_R/`
3. Ouvrez le fichier `App.R`
4. Exécutez l'application en cliquant sur le bouton "Run App" dans RStudio

## Résolution des problèmes courants

- Si l'application ne démarre pas après 5 minutes, vérifiez l'état des conteneurs avec `docker ps`
- Pour consulter les logs : `docker logs nom_du_conteneur_rstudio`

## Récapitulatif des services

| Service | URL d'accès | Identifiants |
|---------|-------------|--------------|
| RStudio | http://localhost:8787 | Authentification désactivée |
| PhpMyAdmin | http://localhost:8788 | user: db / password: db |
| MySQL | localhost:8789 | user: db / password: db / database: db |
| Shiny | http://localhost:3838 | N/A |

> Documentation Réalisé par **Laurent**.