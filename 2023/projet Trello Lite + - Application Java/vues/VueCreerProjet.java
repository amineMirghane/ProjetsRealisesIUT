/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueCreerProjet représente une vue permettant à l'utilisateur de créer un nouveau projet en saisissant le nom, la description et le statut du projet.
 * Elle contient trois JLabels pour afficher les textes "Nom du projet : ", "Description du projet :" et "Statut du projet :",
 * ainsi que trois JTextFields pour permettre à l'utilisateur de saisir le nom, la description et le statut du projet.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueCreerProjet extends JPanel {
	Utilisateur _modeleUtilisateur; // Instance de la classe Utilisateur pour représenter les données de
									// l'utilisateur
	JLabel nomLabel; // JLabel pour afficher le texte "Nom du projet : "
	JLabel descriptionLabel; // JLabel pour afficher le texte "Description du projet :"
	JLabel statusLabel; // JLabel pour afficher le texte "Statut du projet :"
	JTextField nomField; // JTextField pour permettre à l'utilisateur de saisir le nom du projet
	JTextField descriptionField; // JTextField pour permettre à l'utilisateur de saisir la description du projet
	JTextField statusField; // JTextField pour permettre à l'utilisateur de saisir le statut du projet

	public VueCreerProjet(Utilisateur modeleUtilisateur) {
		this._modeleUtilisateur = modeleUtilisateur; // Initialise l'instance de Utilisateur avec le modèle de données
														// de l'utilisateur

		this.nomLabel = new JLabel("Nom du projet : "); // Crée un JLabel avec le texte "Nom du projet : "
		this.descriptionLabel = new JLabel("Description du projet :"); // Crée un JLabel avec le texte "Description du
																		// projet :"
		this.statusLabel = new JLabel("Statut du projet :"); // Crée un JLabel avec le texte "Statut du projet :"
		this.nomField = new JTextField(20); // Crée un JTextField avec une largeur de 20 caractères
		this.descriptionField = new JTextField(20); // Crée un JTextField avec une largeur de 20 caractères
		this.statusField = new JTextField(20); // Crée un JTextField avec une largeur de 20 caractères

		JPanel inputPanel = new JPanel(new GridLayout(3, 2)); // Crée un nouveau JPanel pour contenir les composants
																// d'entrée avec une disposition en grille de 3 lignes
																// et 2 colonnes
		inputPanel.add(nomLabel); // Ajoute le JLabel du nom du projet au JPanel
		inputPanel.add(nomField); // Ajoute le JTextField du nom du projet au JPanel
		inputPanel.add(descriptionLabel); // Ajoute le JLabel de la description du projet au JPanel
		inputPanel.add(descriptionField); // Ajoute le JTextField de la description du projet au JPanel
		inputPanel.add(statusLabel); // Ajoute le JLabel du statut du projet au JPanel
		inputPanel.add(statusField); // Ajoute le JTextField du statut du projet au JPanel

		this.add(inputPanel); // Ajoute le JPanel contenant les composants d'entrée au JPanel principal
	}

	// Retourne le texte saisi dans le champ de saisie du nom du projet.
	public String getNomFieldText() {
		return nomField.getText(); // Retourne le texte du champ de saisie du nom du projet
	}

	// Retourne le texte saisi dans le champ de saisie de la description du projet.
	public String getDescriptionFieldText() {
		return descriptionField.getText(); // Retourne le texte du champ de saisie de la description du projet
	}

	// Retourne le texte saisi dans le champ de saisie du statut du projet.
	public String getStatusFieldText() {
		return statusField.getText(); // Retourne le texte du champ de saisie du statut du projet
	}

	// Efface le contenu des champs de saisie du nom du projet, de la description du
	// projet et du statut du projet.
	public void clearFields() {
		nomField.setText(""); // Efface le texte du champ de saisie du nom du projet
		descriptionField.setText(""); // Efface le texte du champ de saisie de la description du projet
		statusField.setText(""); // Efface le texte du champ de saisie du statut du projet
	}
}
