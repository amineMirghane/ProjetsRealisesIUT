/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueSupprimerCarte représente une vue permettant de supprimer une carte d'une liste.
 * Elle affiche un champ de texte pour saisir l'ID de la carte à supprimer.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

import modeles.ListeDeCarte;

public class VueSupprimerCarte extends JPanel {
	ListeDeCarte _modeleListeDeCarte; // Instance de la classe ListeDeCarte pour représenter les données de la liste
										// de cartes
	JLabel idLabel; // JLabel pour afficher le libellé du champ de saisie de l'ID de la carte
	JTextField idField; // JTextField pour saisir l'ID de la carte à supprimer

	public VueSupprimerCarte(ListeDeCarte modeleListeDeCarte) {
		this._modeleListeDeCarte = modeleListeDeCarte; // Initialise l'instance de ListeDeCarte avec le modèle de
														// données de la liste de cartes

		idLabel = new JLabel("ID de la carte : "); // Crée le JLabel pour afficher le libellé du champ de saisie de l'ID
													// de la carte
		idField = new JTextField(5); // Crée le JTextField pour saisir l'ID de la carte à supprimer

		this.add(idLabel); // Ajoute le JLabel du libellé du champ de saisie de l'ID de la carte au JPanel
		this.add(idField); // Ajoute le JTextField du champ de saisie de l'ID de la carte au JPanel
	}

	// Récupère le texte saisi dans le champ de saisie de l'ID de la carte.
	public int getIdFieldText() {
		return Integer.parseInt(idField.getText());
	}

	// Efface le contenu du champ de saisie de l'ID de la carte.
	public void clearFields() {
		idField.setText("");
	}
}