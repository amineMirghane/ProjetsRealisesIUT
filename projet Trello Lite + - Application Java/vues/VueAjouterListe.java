/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueAjouterListe représente une vue pour ajouter une liste de cartes.
 * Elle contient un champ de saisie pour le nom de la liste.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueAjouterListe extends JPanel {
	Projet _modeleProjet; // Instance de la classe Projet pour représenter les données du projet
	JLabel nomLabel; // JLabel pour le texte "Nom de la liste de carte :"
	JTextField nomField; // JTextField pour saisir le nom de la liste

	public VueAjouterListe(Projet modeleProjet) {
		this._modeleProjet = modeleProjet; // Initialise l'instance de Projet avec le modèle de données du projet

		this.nomLabel = new JLabel("Nom de la liste de carte :"); // Crée un JLabel avec le texte "Nom de la liste de
																	// carte :"
		this.nomField = new JTextField(10); // Crée un JTextField avec une largeur de 10 caractères
		this.add(nomLabel); // Ajoute le JLabel du nom de la liste et le JTextField correspondant au JPanel
		this.add(nomField);
	}

	// Récupère le texte saisi dans le champ du nom de la liste de cartes.
	public String getNomFieldText() {
		return nomField.getText(); // Récupère le texte du champ du nom de la liste de cartes
	}

	// Efface le contenu du champ de saisie du nom de la liste de cartes.
	public void clearFields() {
		nomField.setText(""); // Efface le texte du champ du nom de la liste de cartes
	}
}
