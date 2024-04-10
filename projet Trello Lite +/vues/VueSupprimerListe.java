/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueSupprimerListe représente une vue permettant de supprimer une liste de cartes d'un projet.
 * Elle affiche un champ de texte pour saisir l'ID de la liste de cartes à supprimer.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueSupprimerListe extends JPanel {
	Projet _modeleProjet; // Instance de la classe Projet pour représenter les données du projet
	JLabel idLabel; // JLabel pour afficher le libellé du champ de saisie de l'ID de la liste de
					// cartes
	JTextField idField; // JTextField pour saisir l'ID de la liste de cartes à supprimer

	public VueSupprimerListe(Projet modeleProjet) {
		this._modeleProjet = modeleProjet; // Initialise l'instance de Projet avec le modèle de données du projet

		idLabel = new JLabel("ID de la liste de cartes : "); // Crée le JLabel pour afficher le libellé du champ de
																// saisie de l'ID de la liste de cartes
		idField = new JTextField(5); // Crée le JTextField pour saisir l'ID de la liste de cartes à supprimer

		this.add(idLabel); // Ajoute le JLabel du libellé du champ de saisie de l'ID de la liste de cartes
							// au JPanel
		this.add(idField); // Ajoute le JTextField du champ de saisie de l'ID de la liste de cartes au
							// JPanel
	}

	// Récupère le texte saisi dans le champ de saisie de l'ID de la liste de
	// cartes.
	public int getIdFieldText() {
		return Integer.parseInt(idField.getText());
	}

	// Efface le contenu du champ de saisie de l'ID de la liste de cartes.
	public void clearFields() {
		idField.setText("");
	}
}