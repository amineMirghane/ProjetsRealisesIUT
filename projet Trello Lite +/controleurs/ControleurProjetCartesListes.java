/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Le controleur pour le module des listes de cartes d'un projet.
 * Il est responsable de gérer les actions et les interactions entre le modèle et la vue.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurProjetCartesListes extends JPanel implements ActionListener {
	// Définition des constantes pour les actions
	public static final String ACTION_AJOUTER = "AJOUTER";
	public static final String ACTION_OUVRIR = "OUVRIR";
	public static final String ACTION_SUPPRIMER = "SUPPRIMER";

	// Références vers le modèle, la vue et les boutons
	Projet _modeleProjet;
	VueProjetCartesListes _vueProjetCartesListes;
	JButton ajouterListeButton;
	JButton ouvrirListeButton;
	JButton supprimerListeButton;
	Favoris _modeleFavoris;

	public ControleurProjetCartesListes(Projet modeleProjet, VueProjetCartesListes vueProjetCartesListes,
			Favoris modeleFavoris) {
		this._modeleProjet = modeleProjet;
		this._vueProjetCartesListes = vueProjetCartesListes;
		this._modeleFavoris = modeleFavoris;

		// Création des boutons et association des actions
		this.ajouterListeButton = new JButton("Ajouter une liste");
		this.ouvrirListeButton = new JButton("Ouvrir une liste");
		this.supprimerListeButton = new JButton("Supprimer une liste");
		this.ajouterListeButton.setActionCommand(ACTION_AJOUTER);
		this.ouvrirListeButton.setActionCommand(ACTION_OUVRIR);
		this.supprimerListeButton.setActionCommand(ACTION_SUPPRIMER);
		this.ajouterListeButton.addActionListener(this);
		this.ouvrirListeButton.addActionListener(this);
		this.supprimerListeButton.addActionListener(this);

		// Configuration du layout et ajout des boutons au panneau
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(ajouterListeButton);
		this.add(ouvrirListeButton);
		this.add(supprimerListeButton);
	}

	// Gestion des actions des boutons
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_AJOUTER)) {
			this.afficherFenetreAjout();
		} else if (e.getActionCommand().equals(ACTION_OUVRIR)) {
			this.afficherFenetreOuverture();
		} else if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
			this.afficherFenetreSuppression();
		}
	}

	// Affiche la fenêtre d'ajout d'une liste de cartes
	public void afficherFenetreAjout() {
		VueAjouterListe ajouterListeView = new VueAjouterListe(this._modeleProjet);
		JFrame frame = new JFrame("Ajouter une liste");
		ControleurAjouterListe ajouterListeController = new ControleurAjouterListe(this._modeleProjet, ajouterListeView,
				this._vueProjetCartesListes, frame);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		frame.add(ajouterListeView);
		frame.add(ajouterListeController);
		frame.pack();
		frame.setVisible(true);
	}

	// Affiche la fenêtre d'ouverture d'une liste de cartes
	public void afficherFenetreOuverture() {
		VueOuvrirListe ouvrirListeView = new VueOuvrirListe(this._modeleProjet);
		JFrame frame = new JFrame("Ouvrir une liste");
		ControleurOuvrirListe ouvrirListeController = new ControleurOuvrirListe(this._modeleProjet, ouvrirListeView,
				frame, this._modeleFavoris);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		frame.add(ouvrirListeView);
		frame.add(ouvrirListeController);
		frame.pack();
		frame.setVisible(true);
	}

	// Affiche la fenêtre de suppression d'une liste de cartes
	public void afficherFenetreSuppression() {
		VueSupprimerListe supprimerListeView = new VueSupprimerListe(this._modeleProjet);
		JFrame frame = new JFrame("Supprimer une liste");
		ControleurSupprimerListe supprimerListeController = new ControleurSupprimerListe(this._modeleProjet,
				supprimerListeView, this._vueProjetCartesListes, frame);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		frame.add(supprimerListeView);
		frame.add(supprimerListeController);
		frame.pack();
		frame.setVisible(true);
	}
}