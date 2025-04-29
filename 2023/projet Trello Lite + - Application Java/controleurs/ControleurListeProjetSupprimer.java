/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * ControleurListeProjetSupprimer est une classe qui sert de contrôleur pour la suppression d'un projet.
 * Elle implémente l'interface ActionListener pour gérer les événements des boutons.
 */
package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurListeProjetSupprimer extends JPanel implements ActionListener {
	// Action pour le bouton de suppression
	public static final String ACTION_SUPPRIMER = "SUPPRIMER";

	Utilisateur _modeleUtilisateur; // Modèle utilisateur
	VueListeProjetSupprimer _vueListeProjetSupprimer; // Vue de la liste des projets à supprimer
	VueListeProjet _vueListeProjet; // Vue de la liste des projets
	JFrame _parentFrame; // Fenêtre parente
	JButton supprimerProjetButton; // Bouton pour supprimer un projet

	// Constructeur de la classe
	public ControleurListeProjetSupprimer(Utilisateur modeleUtilisateur,
			VueListeProjetSupprimer vueListeProjetSupprimer, VueListeProjet vueListeProjet, JFrame parentFrame) {
		this._modeleUtilisateur = modeleUtilisateur;
		this._vueListeProjetSupprimer = vueListeProjetSupprimer;
		this._vueListeProjet = vueListeProjet;
		this._parentFrame = parentFrame;

		// Création du bouton et ajout de l'action
		this.supprimerProjetButton = new JButton("Supprimer");
		this.supprimerProjetButton.setActionCommand(ACTION_SUPPRIMER);
		this.supprimerProjetButton.addActionListener(this);
		this.add(supprimerProjetButton);
	}

	// Méthode appelée lorsqu'un bouton est cliqué
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
			this._modeleUtilisateur.SupprimerProjet(this._vueListeProjetSupprimer.getIdFieldText());
			this._vueListeProjetSupprimer.clearFields();
			this._vueListeProjet.majVueListeProjet();
			this._parentFrame.dispose();
		}
	}

}
