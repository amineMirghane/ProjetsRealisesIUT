/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * ControleurOuvrirListe est une classe qui sert de contrôleur pour l'ouverture d'une liste de cartes.
 * Elle implémente l'interface ActionListener pour gérer les événements des boutons.
 */
package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurOuvrirListe extends JPanel implements ActionListener {
	// Action pour le bouton d'ouverture de liste
	public static final String ACTION_OUVRIR = "OUVRIR";

	Projet _modeleProjet; // Modèle du projet
	VueOuvrirListe _vueOuvrirListe; // Vue pour ouvrir une liste
	JButton ouvrirListeButton; // Bouton pour ouvrir une liste
	JFrame _parentFrame; // Fenêtre parente
	Favoris _modeleFavoris; // Modèle des favoris

	// Constructeur de la classe
	public ControleurOuvrirListe(Projet modeleProjet, VueOuvrirListe vueOuvrirListe, JFrame parentFrame,
			Favoris modeleFavoris) {
		this._modeleProjet = modeleProjet;
		this._vueOuvrirListe = vueOuvrirListe;
		this._parentFrame = parentFrame;
		this._modeleFavoris = modeleFavoris;

		// Création du bouton et ajout de l'action
		this.ouvrirListeButton = new JButton("Ouvrir");
		this.ouvrirListeButton.setActionCommand(ACTION_OUVRIR);
		this.ouvrirListeButton.addActionListener(this);
		this.add(ouvrirListeButton);
	}

	// Méthode appelée lorsqu'un bouton est cliqué
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_OUVRIR)) {
			this.afficherListe();
			this._vueOuvrirListe.clearFields();
			this._parentFrame.dispose();
		}
	}

	// Méthode pour afficher la liste de cartes sélectionnée
	public void afficherListe() {
		for (ListeDeCarte uneListeDeCarte : this._modeleProjet.get_listeDeCarte()) {
			if (uneListeDeCarte.get_idListeDeCarte() == this._vueOuvrirListe.getIdFieldText()) {
				VueListeDeCarte listeDeCarteView = new VueListeDeCarte(uneListeDeCarte);
				ControleurListeDeCarte listeDeCarteController = new ControleurListeDeCarte(uneListeDeCarte,
						listeDeCarteView, this._modeleFavoris);
				JFrame frame = new JFrame("Détails de la liste de cartes");
				frame.setResizable(false);
				frame.setLayout(new BorderLayout());
				frame.add(listeDeCarteView, BorderLayout.CENTER);
				frame.add(listeDeCarteController, BorderLayout.SOUTH);
				frame.pack();
				frame.setVisible(true);
				break;
			}
		}
	}
}
