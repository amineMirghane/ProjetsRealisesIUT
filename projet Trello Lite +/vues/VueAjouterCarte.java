/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueAjouterCarte représente une vue pour ajouter une carte.
 * Elle contient plusieurs champs de saisie pour le nom, la description, les dates et le statut de la carte.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueAjouterCarte extends JPanel {
	ListeDeCarte _modeleListeDeCarte; // Instance de la classe ListeDeCarte pour représenter les données de la liste
										// de cartes
	JLabel nomLabel; // JLabel pour le texte "Nom de la carte :"
	JLabel descriptionLabel; // JLabel pour le texte "Description de la carte :"
	JLabel dateDeDebutLabel; // JLabel pour le texte "Date de début :"
	JLabel dateLimiteLabel; // JLabel pour le texte "Date limite :"
	JLabel statusLabel; // JLabel pour le texte "Statut de la carte :"
	JTextField nomField; // JTextField pour saisir le nom de la carte
	JTextField descriptionField; // JTextField pour saisir la description de la carte
	JTextField dateDeDebutField; // JTextField pour saisir la date de début de la carte
	JTextField dateLimiteField; // JTextField pour saisir la date limite de la carte
	JTextField statusField; // JTextField pour saisir le statut de la carte

	public VueAjouterCarte(ListeDeCarte modeleListeDeCarte) {
		this._modeleListeDeCarte = modeleListeDeCarte; // Initialise l'instance de ListeDeCarte avec le modèle de
														// données de la liste de cartes

		this.nomLabel = new JLabel("Nom de la carte :"); // Crée un JLabel avec le texte "Nom de la carte :"
		this.descriptionLabel = new JLabel("Description de la carte :"); // Crée un JLabel avec le texte "Description de
																			// la carte :"
		this.dateDeDebutLabel = new JLabel("Date de début :"); // Crée un JLabel avec le texte "Date de début :"
		this.dateLimiteLabel = new JLabel("Date limite :"); // Crée un JLabel avec le texte "Date limite :"
		this.statusLabel = new JLabel("Statut de la carte :"); // Crée un JLabel avec le texte "Statut de la carte :"
		this.nomField = new JTextField(20); // Crée un JTextField avec une largeur de 20 caractères
		this.descriptionField = new JTextField(20); // Crée un JTextField avec une largeur de 20 caractères
		this.dateDeDebutField = new JTextField(20); // Crée un JTextField avec une largeur de 20 caractères
		this.dateLimiteField = new JTextField(20); // Crée un JTextField avec une largeur de 20 caractères
		this.statusField = new JTextField(20); // Crée un JTextField avec une largeur de 20 caractères
		JPanel inputPanel = new JPanel(new GridLayout(5, 2)); // Crée un JPanel avec une disposition en grille de 5
																// lignes et 2 colonnes
		inputPanel.add(nomLabel); // Ajoute le JLabel du nom et le JTextField correspondant au JPanel
		inputPanel.add(nomField);
		inputPanel.add(descriptionLabel); // Ajoute le JLabel de la description et le JTextField correspondant au JPanel
		inputPanel.add(descriptionField);
		inputPanel.add(dateDeDebutLabel); // Ajoute le JLabel de la date de début et le JTextField correspondant au
											// JPanel
		inputPanel.add(dateDeDebutField);
		inputPanel.add(dateLimiteLabel); // Ajoute le JLabel de la date limite et le JTextField correspondant au JPanel
		inputPanel.add(dateLimiteField);
		inputPanel.add(statusLabel); // Ajoute le JLabel du statut et le JTextField correspondant au JPanel
		inputPanel.add(statusField);
		this.add(inputPanel); // Ajoute le JPanel contenant les champs de saisie au JPanel principal
	}

	// Récupère le texte saisi dans le champ du nom de la carte.
	public String getNomFieldText() {
		return nomField.getText(); // Récupère le texte du champ du nom de la carte
	}

	// Récupère le texte saisi dans le champ de la description de la carte.
	public String getDescriptionFieldText() {
		return descriptionField.getText(); // Récupère le texte du champ de la description de la carte
	}

	// Récupère le texte saisi dans le champ de la date de début de la carte.
	public String getDateDeDebutFieldText() {
		return dateDeDebutField.getText(); // Récupère le texte du champ de la date de début de la carte
	}

	// Récupère le texte saisi dans le champ de la date limite de la carte.
	public String getDateLimiteFieldText() {
		return dateLimiteField.getText(); // Récupère le texte du champ de la date limite de la carte
	}

	// Récupère le texte saisi dans le champ du statut de la carte.
	public String getStatusFieldText() {
		return statusField.getText(); // Récupère le texte du champ du statut de la carte
	}

	// Efface le contenu de tous les champs de saisie.
	public void clearFields() {
		nomField.setText(""); // Efface le texte du champ du nom de la carte
		descriptionField.setText(""); // Efface le texte du champ de la description de la carte
		dateDeDebutField.setText(""); // Efface le texte du champ de la date de début de la carte
		dateLimiteField.setText(""); // Efface le texte du champ de la date limite de la carte
		statusField.setText(""); // Efface le texte du champ du statut de la carte
	}
}
