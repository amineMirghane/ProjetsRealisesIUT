/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Le controleur pour le module Projet.
 * Il est responsable de gérer les actions et les interactions entre le modèle et la vue.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurProjet extends JPanel implements ActionListener {
	// Définition des constantes pour les actions
	public static final String ACTION_VOIR = "VOIR";
	public static final String ACTION_CHANGER = "CHANGER";
	public static final String ACTION_FAV = "FAVORIS";

	// Références vers le modèle, la vue et les boutons
	Projet _modeleProjet;
	VueProjet _vueProjet;
	JButton listeCartesButton;
	JButton changerStatusButton;
	JButton favorisButton;
	Favoris _modeleFavoris;

	public ControleurProjet(Projet modeleProjet, VueProjet vueProjet, Favoris modeleFavoris) {
		this._modeleProjet = modeleProjet;
		this._vueProjet = vueProjet;
		this._modeleFavoris = modeleFavoris;

		// Création des boutons et association des actions
		this.listeCartesButton = new JButton("Voir les listes de cartes");
		this.changerStatusButton = new JButton("Changer le status du projet");
		this.favorisButton = new JButton("Ajouter aux favoris");
		this.listeCartesButton.setActionCommand(ACTION_VOIR);
		this.changerStatusButton.setActionCommand(ACTION_CHANGER);
		this.favorisButton.setActionCommand(ACTION_FAV);
		this.listeCartesButton.addActionListener(this);
		this.changerStatusButton.addActionListener(this);
		this.favorisButton.addActionListener(this);

		// Configuration du layout et ajout des boutons au panneau
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(listeCartesButton);
		this.add(changerStatusButton);
		this.add(favorisButton);
	}

	// Gestion des actions des boutons
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_VOIR)) {
			this.afficherListesDeCarte();
		} else if (e.getActionCommand().equals(ACTION_CHANGER)) {
			this.afficherFenetreChangerStatut();
		} else if (e.getActionCommand().equals(ACTION_FAV)) {
			this._modeleFavoris.AjouterFavoris(_modeleProjet);
		}
	}

	// Affiche la fenêtre des listes de cartes
	public void afficherListesDeCarte() {
		VueProjetCartesListes projetCartesListesView = new VueProjetCartesListes(this._modeleProjet);
		ControleurProjetCartesListes projetCartesListesController = new ControleurProjetCartesListes(this._modeleProjet,
				projetCartesListesView, this._modeleFavoris);
		JFrame frame = new JFrame("Liste des listes de cartes");
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(projetCartesListesView, BorderLayout.CENTER);
		frame.add(projetCartesListesController, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	// Affiche la fenêtre de changement de statut du projet
	public void afficherFenetreChangerStatut() {
		VueProjetChangerStatus projetChangerStatusView = new VueProjetChangerStatus(this._modeleProjet);
		JFrame frame = new JFrame("Changer le statut du projet");
		ControleurProjetChangerStatus projetChangerStatusController = new ControleurProjetChangerStatus(
				this._modeleProjet, projetChangerStatusView, this._vueProjet, frame);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		frame.add(projetChangerStatusView);
		frame.add(projetChangerStatusController);
		frame.pack();
		frame.setVisible(true);
	}
}