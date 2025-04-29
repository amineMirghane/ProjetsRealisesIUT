/***
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Le contrôleur pour la fonctionnalité de gestion des membres d'une carte.
 * Gère les interactions entre le modèle Carte et les vues associées.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurGestionMembres extends JPanel implements ActionListener {
	public static final String ACTION_AJOUTER = "AJOUTER";
	public static final String ACTION_AFFICHER = "AFFICHER";
	public static final String ACTION_SUPPRIMER = "SUPPRIMER";
	Carte _modeleCarte;
	VueGestionMembres _vueGestionMembres;
	JButton ajouterMembreButton;
	JButton afficherMembreButton;
	JButton supprimerMembreButton;

	public ControleurGestionMembres(Carte modeleCarte, VueGestionMembres vueGestionMembres) {
		this._modeleCarte = modeleCarte;
		this._vueGestionMembres = vueGestionMembres;

		// Crée les boutons "Ajouter un membre", "Afficher un membre" et "Supprimer un
		// membre"
		this.ajouterMembreButton = new JButton("Ajouter un membre");
		this.afficherMembreButton = new JButton("Afficher un membre");
		this.supprimerMembreButton = new JButton("Supprimer un membre");
		this.ajouterMembreButton.setActionCommand(ACTION_AJOUTER);
		this.afficherMembreButton.setActionCommand(ACTION_AFFICHER);
		this.supprimerMembreButton.setActionCommand(ACTION_SUPPRIMER);
		this.ajouterMembreButton.addActionListener(this);
		this.afficherMembreButton.addActionListener(this);
		this.supprimerMembreButton.addActionListener(this);

		// Définit la disposition des boutons
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(ajouterMembreButton);
		this.add(afficherMembreButton);
		this.add(supprimerMembreButton);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_AJOUTER)) {
			this.afficherFenetreAjout();
		} else if (e.getActionCommand().equals(ACTION_AFFICHER)) {
			this.afficherFenetreOuverture();
		} else if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
			this.afficherFenetreSuppression();
		}
	}

	public void afficherFenetreAjout() {
		// Crée la vue pour ajouter un membre
		VueAjouterMembre ajouterMembreView = new VueAjouterMembre(this._modeleCarte);

		// Crée le contrôleur pour ajouter un membre
		ControleurAjouterMembre ajouterMembreController = new ControleurAjouterMembre(this._modeleCarte,
				ajouterMembreView, this._vueGestionMembres);

		// Crée la fenêtre pour ajouter un membre
		JFrame frame = new JFrame("Ajouter un Membre");
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(ajouterMembreView, BorderLayout.CENTER);
		frame.add(ajouterMembreController, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	public void afficherFenetreSuppression() {
		// Crée la vue pour supprimer un membre
		VueSupprimerMembre supprimerMembreView = new VueSupprimerMembre(this._modeleCarte);

		// Crée la fenêtre pour supprimer un membre
		JFrame frame = new JFrame("Supprimer un membre");

		// Crée le contrôleur pour supprimer un membre
		ControleurSupprimerMembre supprimerMembreController = new ControleurSupprimerMembre(this._modeleCarte,
				supprimerMembreView, this._vueGestionMembres, frame);

		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		frame.add(supprimerMembreView);
		frame.add(supprimerMembreController);
		frame.pack();
		frame.setVisible(true);
	}

	public void afficherFenetreOuverture() {
		// Crée la vue pour afficher un membre
		VueAfficherMembre afficherMembreView = new VueAfficherMembre(this._modeleCarte);

		// Crée la fenêtre pour afficher un membre
		JFrame frame = new JFrame("Afficher un membre");

		// Crée le contrôleur pour afficher un membre
		ControleurAfficherMembre afficherMembreController = new ControleurAfficherMembre(this._modeleCarte,
				afficherMembreView, frame);

		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		frame.add(afficherMembreView);
		frame.add(afficherMembreController);
		frame.pack();
		frame.setVisible(true);
	}
}