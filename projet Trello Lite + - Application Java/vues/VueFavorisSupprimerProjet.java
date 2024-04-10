/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueFavorisSupprimerProjet représente une vue permettant de supprimer un projet d'un favori.
 * Elle contient un JLabel pour afficher le libellé de l'ID du projet à supprimer et un JTextField pour saisir l'ID du projet.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueFavorisSupprimerProjet extends JPanel {
	Favoris _modeleFavoris; // Instance de la classe Favoris pour représenter les données du favori
	JLabel idLabel; // JLabel pour afficher le libellé de l'ID du projet à supprimer
	JTextField idField; // JTextField pour saisir l'ID du projet à supprimer

	public VueFavorisSupprimerProjet(Favoris modeleFavoris) {
		this._modeleFavoris = modeleFavoris; // Initialise l'instance de Favoris avec le modèle de données du favori

		this.idLabel = new JLabel("ID du projet : "); // Crée un JLabel avec le libellé de l'ID du projet à supprimer
		this.idField = new JTextField(5); // Crée un JTextField pour saisir l'ID du projet

		this.add(idLabel); // Ajoute le JLabel dans le JPanel
		this.add(idField); // Ajoute le JTextField dans le JPanel
	}

	// Récupère l'ID du projet saisi dans le champ de texte.
	public int getIdFieldText() {
		return Integer.parseInt(idField.getText()); // Récupère le texte saisi dans le champ de texte et le convertit en
													// entier
	}

	// Efface le contenu du champ de texte.
	public void clearFields() {
		idField.setText(""); // Efface le contenu du champ de texte
	}
}
