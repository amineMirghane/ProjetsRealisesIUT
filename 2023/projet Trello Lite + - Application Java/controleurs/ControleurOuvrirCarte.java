/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * ControleurOuvrirCarte est une classe qui sert de contrôleur pour l'ouverture d'une carte.
 * Elle implémente l'interface ActionListener pour gérer les événements des boutons.
 */
package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurOuvrirCarte extends JPanel implements ActionListener {
	// Action pour le bouton d'ouverture de carte
	public static final String ACTION_OUVRIR = "OUVRIR";

	ListeDeCarte _modeleListeDeCarte; // Modèle de la liste de cartes
	VueOuvrirCarte _vueOuvrirCarte; // Vue pour ouvrir une carte
	JButton ouvrirCarteButton; // Bouton pour ouvrir une carte
	JFrame _parentFrame; // Fenêtre parente
	Favoris _modeleFavoris; // Modèle des favoris

	// Constructeur de la classe
	public ControleurOuvrirCarte(ListeDeCarte modeleListeDeCarte, VueOuvrirCarte vueOuvrirCarte, JFrame parentFrame,
			Favoris modeleFavoris) {
		this._modeleListeDeCarte = modeleListeDeCarte;
		this._vueOuvrirCarte = vueOuvrirCarte;
		this._parentFrame = parentFrame;
		this._modeleFavoris = modeleFavoris;

		// Création du bouton et ajout de l'action
		this.ouvrirCarteButton = new JButton("Ouvrir");
		this.ouvrirCarteButton.setActionCommand(ACTION_OUVRIR);
		this.ouvrirCarteButton.addActionListener(this);
		this.add(ouvrirCarteButton);
	}

	// Méthode appelée lorsqu'un bouton est cliqué
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_OUVRIR)) {
			this.afficherCarte();
			this._vueOuvrirCarte.clearFields();
			this._parentFrame.dispose();
		}
	}

	// Méthode pour afficher la carte sélectionnée
	public void afficherCarte() {
		for (Carte carte : this._modeleListeDeCarte.get_cartes()) {
			if (carte.get_idCarte() == this._vueOuvrirCarte.getIdFieldText()) {
				VueCarte carteView = new VueCarte(carte);
				ControleurCarte carteController = new ControleurCarte(carte, carteView, this._modeleFavoris);
				JFrame frame = new JFrame("Détails de la carte");
				frame.setResizable(false);
				frame.setLayout(new BorderLayout());
				frame.add(carteView, BorderLayout.CENTER);
				frame.add(carteController, BorderLayout.SOUTH);
				frame.pack();
				frame.setVisible(true);
				break;
			}
		}
	}
}
