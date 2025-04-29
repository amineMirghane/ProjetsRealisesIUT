angular.module('KRRclass', ['chart.js']).controller('MainCtrl', ['$scope', '$http', mainCtrl]);

function mainCtrl($scope, $http) {
  // Requête SPARQL prédéfinie
  $scope.mySparqlQuery = `
PREFIX iut: <https://cours.iut-orsay.fr/npbd/projet/MIRGHANE/>
SELECT ?nomGenre (COUNT(?genre) AS ?nbAwardsReçus)
WHERE {
  ?genre a iut:Genre;
         iut:nom ?nomGenre.
  ?film a iut:Film;
        iut:nom ?nomFilm;
        iut:aPourGenre ?genre;
        iut:award ?medailleAward.
}
GROUP BY ?genre ?nomGenre
ORDER BY DESC(?nbAwardsReçus)
LIMIT 100
`;

  $scope.startMyAwesomeApp = function () {
    // Vérifie que le SPARQL endpoint est défini
    if (!$scope.myInputEndPoint) {
      $scope.myDisplayMessage = "SPARQL endpoint is not set!";
      console.error("SPARQL endpoint is not set!");
      return;
    }

    // Affiche un message d'accueil
    $scope.myDisplayMessage = "Welcome to my awesome Web Application called: " + ($scope.myInputAppName || "Unnamed Application");

    // Encode la requête SPARQL avant l'envoi
    const encodedQuery = encodeURIComponent($scope.mySparqlQuery);

    // Requête HTTP vers le SPARQL endpoint
    $http({
      method: "GET",
      url: $scope.myInputEndPoint + "?query=" + encodedQuery,
      headers: {
        'Accept': 'application/sparql-results+json',
        'Content-Type': 'application/sparql-results+json'
      }
    })
    .then(function (response) {
      // Réinitialisation des données
      $scope.myDynamicLabels = [];
      $scope.myDynamicData = [];

      // Parcourir les résultats et mettre à jour les données du graphique
      angular.forEach(response.data.results.bindings, function (val) {
        $scope.myDynamicLabels.push(val.nomGenre.value); // Nom des genres
        $scope.myDynamicData.push(parseInt(val.nbAwardsReçus.value)); // Nombre d'awards
      });

      // Message de succès
      $scope.myDisplayMessage = "Visualisation des résultats: ";
    })
    .catch(function (error) {
      // Gestion des erreurs
      console.error('Error running the SPARQL query:', error);
      $scope.myDisplayMessage = "Error running the SPARQL query! Please check your endpoint and query.";
    });
  };

  // Options du graphique
  $scope.chartOptions = {
    responsive: true,
    plugins: {
      legend: {
        display: true, // Active la légende
        position: 'bottom', // Position de la légende
        labels: {
          font: {
            size: 14 // Taille des textes de la légende
          },
          color: '#333', // Couleur des textes
          padding: 10 // Espacement entre les éléments
        }
      },
      tooltip: {
        callbacks: {
          label: function (tooltipItem) {
            const label = tooltipItem.label || '';
            const value = tooltipItem.raw || 0;
            return `${label}: ${value} awards`;
          }
        }
      }
    }
  };
}
