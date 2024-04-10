/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueFavorisSupprimerListe représente une vue permettant de supprimer une liste de cartes d'un favori.
 * Elle contient un JLabel pour afficher le libellé de l'ID de la liste de cartes à supprimer et un JTextField pour saisir l'ID de la liste de cartes.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueFavorisSupprimerListe extends JPanel {
	Favoris _modeleFavoris; // Instance de la classe Favoris pour représenter les données du favori
	JLabel idLabel; // JLabel pour afficher le libellé de l'ID de la liste de cartes à supprimer
	JTextField idField; // JTextField pour saisir l'ID de la liste de cartes à supprimer

	public VueFavorisSupprimerListe(Favoris modeleFavoris) {
		this._modeleFavoris = modeleFavoris; // Initialise l'instance de Favoris avec le modèle de données du favori

		this.idLabel = new JLabel("ID de la liste de cartes : "); // Crée un JLabel avec le libellé de l'ID de la liste
																	// de cartes à supprimer
		this.idField = new JTextField(5); // Crée un JTextField pour saisir l'ID de la liste de cartes

		this.add(idLabel); // Ajoute le JLabel dans le JPanel
		this.add(idField); // Ajoute le JTextField dans le JPanel
	}

	// Récupère l'ID de la liste de cartes saisi dans le champ de texte.
	public int getIdFieldText() {
		return Integer.parseInt(idField.getText()); // Récupère le texte saisi dans le champ de texte et le convertit en
													// entier
	}

	// Efface le contenu du champ de texte.
	public void clearFields() {
		idField.setText(""); // Efface le contenu du champ de texte
	}
}
