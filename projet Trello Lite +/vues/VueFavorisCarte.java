/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueFavorisCarte représente une vue permettant d'afficher les informations d'un favori contenant une liste de cartes.
 * Elle contient un JLabel pour afficher l'ID du favori et une JList pour afficher les cartes associées.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueFavorisCarte extends JPanel {
	Favoris _modeleFavoris; // Instance de la classe Favoris pour représenter les données du favori
	JLabel idLabel; // JLabel pour afficher l'ID du favori
	JList<String> carteList; // JList pour afficher les cartes associées au favori

	public VueFavorisCarte(Favoris modeleFavoris) {
		this._modeleFavoris = modeleFavoris; // Initialise l'instance de Favoris avec le modèle de données du favori

		this.idLabel = new JLabel(); // Crée un JLabel vide pour afficher l'ID du favori
		this.carteList = new JList<String>(); // Crée une JList vide pour afficher les cartes associées

		this.setLayout(new BorderLayout()); // Définit le layout du JPanel comme BorderLayout
		this.add(idLabel, BorderLayout.NORTH); // Ajoute le JLabel en position NORTH (en haut) du JPanel
		this.add(new JScrollPane(carteList), BorderLayout.CENTER); // Ajoute la JList avec un JScrollPane en position
																	// CENTER (au centre) du JPanel

		this.majVueFavorisCartes(); // Met à jour la vue des cartes associées au favori
	}

	// Met à jour la vue des cartes associées au favori en affichant l'ID du favori
	// et les noms des cartes dans la JList.
	public void majVueFavorisCartes() {
		this.idLabel.setText("ID du favori : " + this._modeleFavoris.get_idFavoris()); // Affiche l'ID du favori dans le
																						// JLabel

		DefaultListModel<String> listModel = new DefaultListModel<>(); // Crée un DefaultListModel pour stocker les noms
																		// des cartes
		listModel.clear(); // Efface le modèle de liste précédent

		for (Carte carte : _modeleFavoris.get_cartes()) { // Parcourt les cartes associées au favori
			listModel.addElement(carte.get_nomCarte() + " {" + carte.get_idCarte() + "}"); // Ajoute le nom de la carte
																							// avec son ID au modèle de
																							// liste
		}

		carteList.setModel(listModel); // Applique le modèle de liste à la JList pour afficher les noms des cartes
	}
}
