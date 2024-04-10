/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueConnexion représente une vue permettant à l'utilisateur de se connecter en saisissant son adresse email et son mot de passe.
 * Elle contient deux JLabels pour afficher les textes "Adresse Email : " et "Mot de passe : ", ainsi que deux JTextFields pour permettre à l'utilisateur de saisir son adresse email et son mot de passe.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueConnexion extends JPanel {
	Compte _modeleCompte; // Instance de la classe Compte pour représenter les données du compte
	JLabel mailLabel; // JLabel pour afficher le texte "Adresse Email : "
	JLabel passwordLabel; // JLabel pour afficher le texte "Mot de passe : "
	JTextField mailField; // JTextField pour permettre à l'utilisateur de saisir son adresse email
	JTextField passwordField; // JTextField pour permettre à l'utilisateur de saisir son mot de passe

	public VueConnexion(Compte modeleCompte) {
		this._modeleCompte = modeleCompte; // Initialise l'instance de Compte avec le modèle de données du compte

		this.mailLabel = new JLabel("Adresse Email : "); // Crée un JLabel avec le texte "Adresse Email : "
		this.passwordLabel = new JLabel("Mot de passe : "); // Crée un JLabel avec le texte "Mot de passe : "
		this.mailField = new JTextField(10); // Crée un JTextField avec une largeur de 10 caractères
		this.passwordField = new JTextField(10); // Crée un JTextField avec une largeur de 10 caractères

		JPanel inputPanel = new JPanel(); // Crée un nouveau JPanel pour contenir les composants d'entrée
		inputPanel.setLayout(new GridLayout(2, 2)); // Définit une disposition en grille de 2 lignes et 2 colonnes pour
													// le JPanel
		inputPanel.add(mailLabel); // Ajoute le JLabel de l'adresse email au JPanel
		inputPanel.add(mailField); // Ajoute le JTextField de l'adresse email au JPanel
		inputPanel.add(passwordLabel); // Ajoute le JLabel du mot de passe au JPanel
		inputPanel.add(passwordField); // Ajoute le JTextField du mot de passe au JPanel

		this.add(inputPanel); // Ajoute le JPanel contenant les composants d'entrée au JPanel principal
	}

	// Retourne le texte saisi dans le champ de saisie de l'adresse email.
	public String getMailFieldText() {
		return mailField.getText(); // Retourne le texte du champ de saisie de l'adresse email
	}

	// Retourne le texte saisi dans le champ de saisie du mot de passe.
	public String getPasswordFieldText() {
		return passwordField.getText(); // Retourne le texte du champ de saisie du mot de passe
	}

	// Efface le contenu des champs de saisie de l'adresse email et du mot de passe.
	public void clearFields() {
		mailField.setText(""); // Efface le texte du champ de saisie de l'adresse email
		passwordField.setText(""); // Efface le texte du champ de saisie du mot de passe
	}
}
