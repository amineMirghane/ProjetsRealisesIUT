/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * ControleurListeProjetOuvrir est une classe qui sert de contrôleur pour l'ouverture d'un projet.
 * Elle implémente l'interface ActionListener pour gérer les événements des boutons.
 */
package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurListeProjetOuvrir extends JPanel implements ActionListener {
	// Action pour le bouton d'ouverture
	public static final String ACTION_OUVRIR = "OUVRIR";

	Utilisateur _modeleUtilisateur; // Modèle utilisateur
	VueListeProjetOuvrir _vueListeProjetOuvrir; // Vue de la liste des projets à ouvrir
	VueListeProjet _vueListeProjet; // Vue de la liste des projets
	JFrame _parentFrame; // Fenêtre parente
	JButton ouvrirProjetButton; // Bouton pour ouvrir un projet
	Favoris _modeleFavoris; // Modèle des favoris

	// Constructeur de la classe
	public ControleurListeProjetOuvrir(Utilisateur modeleUtilisateur, VueListeProjetOuvrir vueListeProjetOuvrir,
			VueListeProjet vueListeProjet, JFrame parentFrame, Favoris modeleFavoris) {
		this._modeleUtilisateur = modeleUtilisateur;
		this._vueListeProjetOuvrir = vueListeProjetOuvrir;
		this._vueListeProjet = vueListeProjet;
		this._parentFrame = parentFrame;
		this._modeleFavoris = modeleFavoris;

		// Création du bouton et ajout de l'action
		this.ouvrirProjetButton = new JButton("Ouvrir");
		this.ouvrirProjetButton.setActionCommand(ACTION_OUVRIR);
		this.ouvrirProjetButton.addActionListener(this);
		this.add(ouvrirProjetButton);
	}

	// Méthode appelée lorsqu'un bouton est cliqué
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_OUVRIR)) {
			this.afficherProjet();
			this._vueListeProjetOuvrir.clearFields();
			this._parentFrame.dispose();
		}
	}

	// Affiche les détails du projet sélectionné
	public void afficherProjet() {
		for (Projet p : this._modeleUtilisateur.get_projets()) {
			if (p.get_idProjet() == this._vueListeProjetOuvrir.getIdFieldText()) {
				VueProjet projetView = new VueProjet(p);
				ControleurProjet projetController = new ControleurProjet(p, projetView, this._modeleFavoris);
				JFrame frame = new JFrame("Détails du projet");
				frame.setResizable(false);
				frame.setLayout(new BorderLayout());
				frame.add(projetView, BorderLayout.CENTER);
				frame.add(projetController, BorderLayout.SOUTH);
				frame.pack();
				frame.setVisible(true);
				break;
			}
		}
	}
}
