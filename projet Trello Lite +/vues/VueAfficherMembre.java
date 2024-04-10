/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueAfficherMembre représente une vue pour afficher les détails d'un membre.
 * Elle contient un champ texte pour saisir l'ID du membre.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

import modeles.ListeDeCarte;

public class VueAfficherMembre extends JPanel {
	Carte _modeleCarte; // Instance de la classe Carte pour représenter les données du membre
	JLabel idLabel; // JLabel pour le texte "ID du membre : "
	JTextField idField; // JTextField pour saisir l'ID du membre

	public VueAfficherMembre(Carte modeleCarte) {
		this._modeleCarte = modeleCarte; // Initialise l'instance de Carte avec le modèle de données du membre

		this.idLabel = new JLabel("ID du membre : "); // Crée un JLabel avec le texte "ID du membre : "
		this.idField = new JTextField(5); // Crée un JTextField avec une largeur de 5 caractères
		this.add(idLabel); // Ajoute le JLabel au JPanel
		this.add(idField); // Ajoute le JTextField au JPanel
	}

	// Récupère le texte saisi dans le champ ID.
	public int getIdFieldText() {
		return Integer.parseInt(idField.getText()); // Récupère le texte du champ ID et le convertit en entier
	}

	// Efface le contenu du champ ID.
	public void clearFields() {
		idField.setText(""); // Efface le texte du champ ID
	}
}
