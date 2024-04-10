/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * ControleurAjouterListe est une classe qui représente le contrôleur pour ajouter une liste dans un projet.
 * Elle implémente l'interface ActionListener pour gérer les actions des boutons.
 */
package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurAjouterListe extends JPanel implements ActionListener {
	public static final String ACTION_AJOUTER = "AJOUTER"; // Constante pour l'action "Ajouter"

	Projet _modeleProjet; // Référence vers le modèle de projet
	VueAjouterListe _vueAjouterListe; // Référence vers la vue pour ajouter une liste
	VueProjetCartesListes _vueProjetCartesListes; // Référence vers la vue du projet avec les cartes et listes
	JFrame _parentFrame; // Référence vers la fenêtre parent

	JButton ajouterListeButton; // Bouton pour ajouter la liste

	// Constructeur de la classe ControleurAjouterListe.
	public ControleurAjouterListe(Projet modeleProjet, VueAjouterListe vueAjouterListe,
			VueProjetCartesListes vueProjetCartesListes, JFrame parentFrame) {
		this._modeleProjet = modeleProjet;
		this._vueAjouterListe = vueAjouterListe;
		this._vueProjetCartesListes = vueProjetCartesListes;
		this._parentFrame = parentFrame;

		// Création du bouton "Ajouter"
		this.ajouterListeButton = new JButton("Ajouter");
		this.ajouterListeButton.setActionCommand(ACTION_AJOUTER); // Définition de l'action du bouton
		this.ajouterListeButton.addActionListener(this); // Ajout du contrôleur d'événements au bouton
		this.add(ajouterListeButton); // Ajout du bouton au panneau
	}

	// Méthode qui gère les actions des boutons.
	public void actionPerformed(ActionEvent e) {
		// Ajout d'une nouvelle liste de cartes avec le nom spécifié dans la vue d'ajout
		this._modeleProjet.AjouterListeDeCarte(new ListeDeCarte(this._vueAjouterListe.getNomFieldText()));

		// Nettoyage des champs de la vue d'ajout
		this._vueAjouterListe.clearFields();

		// Mise à jour de la vue du projet avec les nouvelles cartes et listes
		this._vueProjetCartesListes.majVueProjetCartesListes();

		// Fermeture de la fenêtre parent
		this._parentFrame.dispose();
	}
}
