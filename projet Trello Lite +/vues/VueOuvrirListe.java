/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueOuvrirListe représente une vue pour ouvrir une liste de cartes.
 * Elle affiche un champ de texte pour entrer l'ID de la liste de cartes à ouvrir.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueOuvrirListe extends JPanel {
	Projet _modeleProjet; // Instance de la classe Projet pour représenter les données du projet
	JLabel idLabel; // JLabel pour afficher le label "ID de la liste de cartes"
	JTextField idField; // Champ de texte pour entrer l'ID de la liste de cartes

	public VueOuvrirListe(Projet modeleProjet) {
		this._modeleProjet = modeleProjet; // Initialise l'instance de Projet avec le modèle de données du projet

		idLabel = new JLabel("ID de la liste de cartes : "); // Crée le JLabel pour afficher le label "ID de la liste de
																// cartes"
		idField = new JTextField(5); // Crée le champ de texte pour entrer l'ID de la liste de cartes

		this.add(idLabel); // Ajoute le JLabel à la vue
		this.add(idField); // Ajoute le champ de texte à la vue
	}

	// Récupère le texte saisi dans le champ de texte et le convertit en entier.
	public int getIdFieldText() {
		return Integer.parseInt(idField.getText());
	}

	// Efface le contenu du champ de texte.
	public void clearFields() {
		idField.setText("");
	}
}
