/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueProjet représente une vue pour afficher les détails d'un projet.
 * Elle affiche l'ID du projet, le nom du projet, la description du projet et le statut du projet.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueProjet extends JPanel {
	Projet _modeleProjet; // Instance de la classe Projet pour représenter les données du projet
	JLabel idLabel; // JLabel pour afficher l'ID du projet
	JLabel nomLabel; // JLabel pour afficher le nom du projet
	JLabel descriptionLabel; // JLabel pour afficher la description du projet
	JLabel statusLabel; // JLabel pour afficher le statut du projet

	public VueProjet(Projet modeleProjet) {
		this._modeleProjet = modeleProjet; // Initialise l'instance de Projet avec le modèle de données du projet

		idLabel = new JLabel(); // Crée le JLabel pour afficher l'ID du projet
		nomLabel = new JLabel(); // Crée le JLabel pour afficher le nom du projet
		descriptionLabel = new JLabel(); // Crée le JLabel pour afficher la description du projet
		statusLabel = new JLabel(); // Crée le JLabel pour afficher le statut du projet

		JPanel infoPanel = new JPanel(); // Crée un JPanel pour contenir les JLabels
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Configure le layout du JPanel en vertical
		infoPanel.add(idLabel); // Ajoute le JLabel de l'ID du projet au JPanel
		infoPanel.add(nomLabel); // Ajoute le JLabel du nom du projet au JPanel
		infoPanel.add(descriptionLabel); // Ajoute le JLabel de la description du projet au JPanel
		infoPanel.add(statusLabel); // Ajoute le JLabel du statut du projet au JPanel

		this.setLayout(new FlowLayout(FlowLayout.LEFT)); // Configure le layout de la vue en alignant les éléments à
															// gauche
		this.add(infoPanel); // Ajoute le JPanel à la vue

		this.majVueProjet(); // Met à jour la vue avec les données du projet
	}

	// Met à jour les JLabels avec les données du projet.
	public void majVueProjet() {
		idLabel.setText("ID du projet : " + _modeleProjet.get_idProjet()); // Met à jour le JLabel de l'ID du projet
		nomLabel.setText("Nom du projet : " + _modeleProjet.get_nomProjet()); // Met à jour le JLabel du nom du projet
		descriptionLabel.setText("Description : " + _modeleProjet.get_descriptionProjet()); // Met à jour le JLabel de
																							// la description du projet
		statusLabel.setText("Statut : " + _modeleProjet.get_statusProjet()); // Met à jour le JLabel du statut du projet
	}
}
