/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Ce contrôleur gère la liste de cartes et les actions associées.
 */
package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurListeDeCarte extends JPanel implements ActionListener {
	public static final String ACTION_AJOUTER = "AJOUTER";
	public static final String ACTION_OUVRIR = "OUVRIR";
	public static final String ACTION_SUPPRIMER = "SUPPRIMER";
	public static final String ACTION_FAV = "FAVORIS";
	ListeDeCarte _modeleListeDeCarte;
	VueListeDeCarte _vueListeDeCarte;
	JButton ajouterCarteButton;
	JButton ouvrirCarteButton;
	JButton supprimerCarteButton;
	JButton favorisButton;
	Favoris _modeleFavoris;

	public ControleurListeDeCarte(ListeDeCarte modeleListeDeCarte, VueListeDeCarte vueListeDeCarte,
			Favoris modeleFavoris) {
		this._modeleListeDeCarte = modeleListeDeCarte;
		this._vueListeDeCarte = vueListeDeCarte;
		this._modeleFavoris = modeleFavoris;

		// Création des boutons et configuration de leurs actions
		this.ajouterCarteButton = new JButton("Ajouter une carte");
		this.ouvrirCarteButton = new JButton("Ouvrir une carte");
		this.supprimerCarteButton = new JButton("Supprimer une carte");
		this.favorisButton = new JButton("Ajouter aux favoris");
		this.ajouterCarteButton.setActionCommand(ACTION_AJOUTER);
		this.ouvrirCarteButton.setActionCommand(ACTION_OUVRIR);
		this.supprimerCarteButton.setActionCommand(ACTION_SUPPRIMER);
		this.favorisButton.setActionCommand(ACTION_FAV);
		this.ajouterCarteButton.addActionListener(this);
		this.ouvrirCarteButton.addActionListener(this);
		this.supprimerCarteButton.addActionListener(this);
		this.favorisButton.addActionListener(this);

		// Configuration du layout et ajout des boutons au panneau
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(ajouterCarteButton);
		this.add(ouvrirCarteButton);
		this.add(supprimerCarteButton);
		this.add(favorisButton);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_AJOUTER)) {
			// Affichage de la fenêtre d'ajout d'une carte
			this.afficherFenetreAjout();
		} else if (e.getActionCommand().equals(ACTION_OUVRIR)) {
			// Affichage de la fenêtre d'ouverture d'une carte
			this.afficherFenetreOuverture();
		} else if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
			// Affichage de la fenêtre de suppression d'une carte
			this.afficherFenetreSuppression();
		} else if (e.getActionCommand().equals(ACTION_FAV)) {
			// Ajout de la carte aux favoris
			this._modeleFavoris.AjouterFavoris(_modeleListeDeCarte);
		}
	}

	public void afficherFenetreAjout() {
		// Création de la vue d'ajout de carte
		VueAjouterCarte ajouterCarteView = new VueAjouterCarte(this._modeleListeDeCarte);
		ControleurAjouterCarte ajouterCarteController = new ControleurAjouterCarte(this._modeleListeDeCarte,
				ajouterCarteView, this._vueListeDeCarte);
		JFrame frame = new JFrame("Ajouter une carte");
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(ajouterCarteView, BorderLayout.CENTER);
		frame.add(ajouterCarteController, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	public void afficherFenetreOuverture() {
		// Création de la vue d'ouverture de carte
		VueOuvrirCarte ouvrirCarteView = new VueOuvrirCarte(this._modeleListeDeCarte);
		JFrame frame = new JFrame("Ouvrir une carte");
		ControleurOuvrirCarte ouvrirCarteController = new ControleurOuvrirCarte(this._modeleListeDeCarte,
				ouvrirCarteView, frame, this._modeleFavoris);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		frame.add(ouvrirCarteView);
		frame.add(ouvrirCarteController);
		frame.pack();
		frame.setVisible(true);
	}

	public void afficherFenetreSuppression() {
		// Création de la vue de suppression de carte
		VueSupprimerCarte supprimerCarteView = new VueSupprimerCarte(this._modeleListeDeCarte);
		JFrame frame = new JFrame("Supprimer une carte");
		ControleurSupprimerCarte supprimerCarteController = new ControleurSupprimerCarte(this._modeleListeDeCarte,
				supprimerCarteView, this._vueListeDeCarte, frame);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		frame.add(supprimerCarteView);
		frame.add(supprimerCarteController);
		frame.pack();
		frame.setVisible(true);
	}
}
