/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueOuvrirCarte représente une vue pour ouvrir une carte.
 * Elle affiche un champ de texte pour entrer l'ID de la carte à ouvrir.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueOuvrirCarte extends JPanel {
	ListeDeCarte _modeleListeDeCarte; // Instance de la classe ListeDeCarte pour représenter les données de la liste
										// de cartes
	JLabel idLabel; // JLabel pour afficher le label "ID de la carte"
	JTextField idField; // Champ de texte pour entrer l'ID de la carte

	public VueOuvrirCarte(ListeDeCarte modeleListeDeCarte) {
		this._modeleListeDeCarte = modeleListeDeCarte; // Initialise l'instance de ListeDeCarte avec le modèle de
														// données de la liste de cartes

		idLabel = new JLabel("ID de la carte : "); // Crée le JLabel pour afficher le label "ID de la carte"
		idField = new JTextField(5); // Crée le champ de texte pour entrer l'ID de la carte

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
