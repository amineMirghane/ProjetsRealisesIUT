/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueCarteChangerStatus représente une vue permettant de changer le statut d'une carte.
 * Elle contient un JLabel pour afficher le texte "Saisir le statut : " et un JTextField pour permettre à l'utilisateur de saisir le nouveau statut.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueCarteChangerStatus extends JPanel {
	Carte _modeleCarte; // Instance de la classe Carte pour représenter les données de la carte
	JLabel statusLabel; // JLabel pour afficher le texte "Saisir le statut : "
	JTextField statusField; // JTextField pour permettre à l'utilisateur de saisir le nouveau statut

	public VueCarteChangerStatus(Carte modeleCarte) {
		this._modeleCarte = modeleCarte; // Initialise l'instance de Carte avec le modèle de données de la carte

		this.statusLabel = new JLabel("Saisir le statut : "); // Crée un JLabel avec le texte "Saisir le statut : "
		this.statusField = new JTextField(5); // Crée un JTextField avec une largeur de 5 caractères

		this.add(statusLabel); // Ajoute le JLabel au JPanel
		this.add(statusField); // Ajoute le JTextField au JPanel
	}

	// Retourne le texte saisi dans le champ de saisie du statut.
	public String getStatusFieldText() {
		return statusField.getText(); // Retourne le texte du champ de saisie du statut
	}

	// Efface le contenu du champ de saisie du statut.
	public void clearFields() {
		statusField.setText(""); // Efface le texte du champ de saisie du statut
	}
}
