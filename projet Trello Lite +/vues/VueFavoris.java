/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueFavoris représente une vue permettant d'afficher les informations d'un favori.
 * Elle contient un JLabel pour afficher l'ID du favori.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueFavoris extends JPanel {
	Favoris _modeleFavoris; // Instance de la classe Favoris pour représenter les données du favori
	JLabel idLabel; // JLabel pour afficher l'ID du favori

	public VueFavoris(Favoris modeleFavoris) {
		this._modeleFavoris = modeleFavoris; // Initialise l'instance de Favoris avec le modèle de données du favori

		this.idLabel = new JLabel(); // Crée un JLabel vide pour afficher l'ID du favori
		this.add(idLabel); // Ajoute le JLabel au JPanel

		this.majVueFavoris(); // Met à jour la vue du favori
	}

	// Met à jour la vue du favori en affichant l'ID du favori.
	public void majVueFavoris() {
		this.idLabel.setText("ID du favori : " + this._modeleFavoris.get_idFavoris()); // Affiche l'ID du favori dans le
																						// JLabel
	}
}
