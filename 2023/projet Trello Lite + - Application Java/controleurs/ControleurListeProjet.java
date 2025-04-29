/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * ControleurListeProjet est une classe qui sert de contrôleur pour la liste des projets.
 * Elle implémente l'interface ActionListener pour gérer les événements des boutons.
 */
package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurListeProjet extends JPanel implements ActionListener {
	// Actions possibles pour les boutons
	public static final String ACTION_CREER = "CREER";
	public static final String ACTION_OUVRIR = "OUVRIR";
	public static final String ACTION_SUPPRIMER = "SUPPRIMER";

	Utilisateur _modeleUtilisateur; // Modèle utilisateur
	VueListeProjet _vueListeProjet; // Vue de la liste des projets
	JButton creerProjetButton; // Bouton pour créer un projet
	JButton ouvrirProjetButton; // Bouton pour ouvrir un projet
	JButton supprimerProjetButton; // Bouton pour supprimer un projet
	Favoris _modeleFavoris; // Modèle des favoris

	// Constructeur de la classe
	public ControleurListeProjet(Utilisateur modeleUtilisateur, VueListeProjet vueListeProjet, Favoris modeleFavoris) {
		this._modeleUtilisateur = modeleUtilisateur;
		this._vueListeProjet = vueListeProjet;
		this._modeleFavoris = modeleFavoris;

		// Création des boutons et ajout des actions
		this.creerProjetButton = new JButton("Créer un projet");
		this.ouvrirProjetButton = new JButton("Ouvrir un projet");
		this.supprimerProjetButton = new JButton("Supprimer un projet");
		this.creerProjetButton.setActionCommand(ACTION_CREER);
		this.ouvrirProjetButton.setActionCommand(ACTION_OUVRIR);
		this.supprimerProjetButton.setActionCommand(ACTION_SUPPRIMER);
		this.creerProjetButton.addActionListener(this);
		this.ouvrirProjetButton.addActionListener(this);
		this.supprimerProjetButton.addActionListener(this);

		// Configuration du layout
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		// Ajout des boutons au panneau
		this.add(creerProjetButton);
		this.add(ouvrirProjetButton);
		this.add(supprimerProjetButton);
	}

	// Méthode appelée lorsqu'un bouton est cliqué
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_CREER)) {
			this.afficherFenetreCreation();
		} else if (e.getActionCommand().equals(ACTION_OUVRIR)) {
			this.afficherFenetreOuverture();
		} else if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
			this.afficherFenetreSuppression();
		}
	}

	// Affiche la fenêtre de création de projet
	public void afficherFenetreCreation() {
		VueCreerProjet creerProjetView = new VueCreerProjet(this._modeleUtilisateur);
		ControleurCreerProjet creerProjetController = new ControleurCreerProjet(this._modeleUtilisateur,
				creerProjetView, this._vueListeProjet);
		JFrame frame = new JFrame("Créer un projet");
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(creerProjetView, BorderLayout.CENTER);
		frame.add(creerProjetController, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	// Affiche la fenêtre d'ouverture de projet
	public void afficherFenetreOuverture() {
		VueListeProjetOuvrir listeProjetOuvrirView = new VueListeProjetOuvrir(this._modeleUtilisateur);
		JFrame frame = new JFrame("Ouvrir un projet");
		ControleurListeProjetOuvrir listeProjetOuvrirController = new ControleurListeProjetOuvrir(
				this._modeleUtilisateur, listeProjetOuvrirView, this._vueListeProjet, frame, this._modeleFavoris);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		frame.add(listeProjetOuvrirView);
		frame.add(listeProjetOuvrirController);
		frame.pack();
		frame.setVisible(true);
	}

	// Affiche la fenêtre de suppression de projet
	public void afficherFenetreSuppression() {
		VueListeProjetSupprimer listeProjetSupprimerView = new VueListeProjetSupprimer(this._modeleUtilisateur);
		JFrame frame = new JFrame("Supprimer un projet");
		ControleurListeProjetSupprimer listeProjetSupprimerController = new ControleurListeProjetSupprimer(
				this._modeleUtilisateur, listeProjetSupprimerView, this._vueListeProjet, frame);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		frame.add(listeProjetSupprimerView);
		frame.add(listeProjetSupprimerController);
		frame.pack();
		frame.setVisible(true);
	}
}
