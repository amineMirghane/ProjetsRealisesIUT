/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueInscription représente une vue permettant de s'inscrire en créant un compte.
 * Elle affiche différents champs tels que le nom, prénom, date de naissance, mail, genre, téléphone, adresse et mot de passe.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueInscription extends JPanel {
	Compte _modeleCompte; // Instance de la classe Compte pour représenter les données du compte
	JLabel nomLabel; // JLabel pour afficher le label "Nom"
	JTextField nomField; // JTextField pour entrer le nom
	JLabel prenomLabel; // JLabel pour afficher le label "Prénom"
	JTextField prenomField; // JTextField pour entrer le prénom
	JLabel dateNaissanceLabel; // JLabel pour afficher le label "Date de naissance"
	JTextField dateNaissanceField; // JTextField pour entrer la date de naissance
	JLabel mailLabel; // JLabel pour afficher le label "Mail"
	JTextField mailField; // JTextField pour entrer le mail
	JLabel genreLabel; // JLabel pour afficher le label "Genre"
	JTextField genreField; // JTextField pour entrer le genre
	JLabel telLabel; // JLabel pour afficher le label "Téléphone"
	JTextField telField; // JTextField pour entrer le numéro de téléphone
	JLabel adresseLabel; // JLabel pour afficher le label "Adresse"
	JTextField adresseField; // JTextField pour entrer l'adresse
	JLabel MDPLabel; // JLabel pour afficher le label "Password"
	JTextField MDPField; // JTextField pour entrer le mot de passe

	public VueInscription(Compte modeleCompte) {
		this._modeleCompte = modeleCompte; // Initialise l'instance de Compte avec le modèle de données du compte

		// Crée les JLabel et JTextField pour les différents champs
		nomLabel = new JLabel("Nom :");
		prenomLabel = new JLabel("Prénom :");
		dateNaissanceLabel = new JLabel("Date de naissance :");
		mailLabel = new JLabel("Mail :");
		genreLabel = new JLabel("Genre :");
		telLabel = new JLabel("Téléphone :");
		adresseLabel = new JLabel("Adresse :");
		MDPLabel = new JLabel("Password :");
		nomField = new JTextField(10);
		prenomField = new JTextField(10);
		dateNaissanceField = new JTextField(10);
		mailField = new JTextField(10);
		genreField = new JTextField(10);
		telField = new JTextField(10);
		adresseField = new JTextField(10);
		MDPField = new JTextField(10);

		this.setLayout(new GridLayout(8, 2)); // Utilise un gestionnaire de disposition de grille pour le JPanel
		// Ajoute les JLabel et JTextField dans le JPanel selon une grille de 8 lignes
		// et 2 colonnes
		this.add(nomLabel);
		this.add(nomField);
		this.add(prenomLabel);
		this.add(prenomField);
		this.add(dateNaissanceLabel);
		this.add(dateNaissanceField);
		this.add(mailLabel);
		this.add(mailField);
		this.add(genreLabel);
		this.add(genreField);
		this.add(telLabel);
		this.add(telField);
		this.add(adresseLabel);
		this.add(adresseField);
		this.add(MDPLabel);
		this.add(MDPField);
	}

	// Efface les champs de saisie en réinitialisant leur contenu.
	public void clearFields() {
		nomField.setText("");
		prenomField.setText("");
		dateNaissanceField.setText("");
		mailField.setText("");
		genreField.setText("");
		telField.setText("");
		adresseField.setText("");
		MDPField.setText("");
	}

	// Retourne le texte saisi dans le champ "Nom".
	public String getNomFieldText() {
		return nomField.getText();
	}

	// Retourne le texte saisi dans le champ "Prénom".
	public String getPrenomFieldText() {
		return prenomField.getText();
	}

	// Retourne le texte saisi dans le champ "Date de naissance".
	public String getDateNaissanceFieldText() {
		return dateNaissanceField.getText();
	}

	// Retourne le texte saisi dans le champ "Mail".
	public String getMailFieldText() {
		return mailField.getText();
	}

	// Retourne le texte saisi dans le champ "Genre".
	public String getGenreFieldText() {
		return genreField.getText();
	}

	// Retourne le texte saisi dans le champ "Téléphone".
	public int getTelFieldText() {
		return Integer.parseInt(telField.getText());
	}

	// Retourne le texte saisi dans le champ "Adresse".
	public String getAdresseFieldText() {
		return adresseField.getText();
	}

	// Retourne le texte saisi dans le champ "Password".
	public String getMDPFieldText() {
		return MDPField.getText();
	}
}
