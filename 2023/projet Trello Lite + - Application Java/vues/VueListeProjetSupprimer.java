/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueListeProjetSupprimer représente une vue permettant de supprimer un projet.
 * Elle affiche un champ de texte pour entrer l'ID du projet à supprimer.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueListeProjetSupprimer extends JPanel {
	Utilisateur _modeleUtilisateur; // Instance de la classe Utilisateur pour représenter les données de
									// l'utilisateur
	JLabel idLabel; // JLabel pour afficher le texte "ID du projet :"
	JTextField idField; // JTextField pour entrer l'ID du projet

	public VueListeProjetSupprimer(Utilisateur modeleUtilisateur) {
		this._modeleUtilisateur = modeleUtilisateur; // Initialise l'instance de Utilisateur avec le modèle de données
														// de l'utilisateur

		// Crée le JLabel et le JTextField pour entrer l'ID du projet
		idLabel = new JLabel("ID du projet :");
		idField = new JTextField(5);

		this.add(idLabel);
		this.add(idField);
	}

	// Retourne l'ID du projet entré dans le champ de texte.
	public int getIdFieldText() {
		return Integer.parseInt(idField.getText());
	}

	// Efface le contenu du champ de texte.
	public void clearFields() {
		idField.setText("");
	}
}
