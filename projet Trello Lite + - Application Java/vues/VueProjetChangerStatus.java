/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueProjetChangerStatus représente une vue permettant de changer le statut d'un projet.
 * Elle affiche un champ de texte pour saisir le nouveau statut.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueProjetChangerStatus extends JPanel {
	Projet _modeleProjet; // Instance de la classe Projet pour représenter les données du projet
	JLabel statusLabel; // JLabel pour afficher le libellé du champ de saisie du statut
	JTextField statusField; // JTextField pour saisir le nouveau statut

	public VueProjetChangerStatus(Projet modeleProjet) {
		this._modeleProjet = modeleProjet; // Initialise l'instance de Projet avec le modèle de données du projet

		statusLabel = new JLabel("Saisir le statut : "); // Crée le JLabel pour afficher le libellé du champ de saisie
															// du statut
		statusField = new JTextField(5); // Crée le JTextField pour saisir le nouveau statut

		this.add(statusLabel); // Ajoute le JLabel du libellé du champ de saisie au JPanel
		this.add(statusField); // Ajoute le JTextField du champ de saisie au JPanel
	}

	// Récupère le texte saisi dans le champ de saisie du statut.
	public String getStatusFieldText() {
		return statusField.getText();
	}

	// Efface le contenu du champ de saisie du statut.
	public void clearFields() {
		statusField.setText("");
	}
}
