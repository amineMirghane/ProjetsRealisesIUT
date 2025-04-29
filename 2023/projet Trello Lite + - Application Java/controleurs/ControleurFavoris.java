/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Ce contrôleur gère les interactions avec la vue des favoris.
 * Il permet d'afficher différentes fenêtres en fonction des actions de l'utilisateur.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurFavoris extends JPanel implements ActionListener {
	// Constantes pour les actions
	public static final String ACTION_VCARTE = "CARTE";
	public static final String ACTION_VLISTE = "LISTE";
	public static final String ACTION_VPROJET = "PROJET";

	// Références aux modèles, vues et composants
	Favoris _modeleFavoris;
	VueFavoris _vueFavoris;
	JButton cartesButton;
	JButton listesDeCartesButton;
	JButton projetsButton;

	public ControleurFavoris(Favoris modeleFavoris, VueFavoris vueFavoris) {
		this._modeleFavoris = modeleFavoris;
		this._vueFavoris = vueFavoris;

		// Création des boutons et assignation des actions
		this.cartesButton = new JButton("Voir les cartes");
		this.listesDeCartesButton = new JButton("Voir les listes de cartes");
		this.projetsButton = new JButton("Voir les projets");
		this.cartesButton.setActionCommand(ACTION_VCARTE);
		this.listesDeCartesButton.setActionCommand(ACTION_VLISTE);
		this.projetsButton.setActionCommand(ACTION_VPROJET);
		this.cartesButton.addActionListener(this);
		this.listesDeCartesButton.addActionListener(this);
		this.projetsButton.addActionListener(this);

		// Ajout des boutons au panneau
		this.add(cartesButton);
		this.add(listesDeCartesButton);
		this.add(projetsButton);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_VCARTE)) {
			// Affichage de la fenêtre des cartes en favoris
			this.afficherFenetreCarte();
		} else if (e.getActionCommand().equals(ACTION_VLISTE)) {
			// Affichage de la fenêtre des listes de cartes en favoris
			this.afficherFenetreListe();
		} else if (e.getActionCommand().equals(ACTION_VPROJET)) {
			// Affichage de la fenêtre des projets en favoris
			this.afficherFenetreProjet();
		}
	}

	public void afficherFenetreCarte() {
		// Création de la vue et du contrôleur pour les cartes en favoris
		VueFavorisCarte favorisCarteView = new VueFavorisCarte(this._modeleFavoris);
		ControleurFavorisCarte favorisCarteController = new ControleurFavorisCarte(this._modeleFavoris,
				favorisCarteView);

		// Création et configuration de la fenêtre
		JFrame frame = new JFrame("Les cartes en favoris");
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(favorisCarteView, BorderLayout.CENTER);
		frame.add(favorisCarteController, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	public void afficherFenetreListe() {
		// Création de la vue et du contrôleur pour les listes de cartes en favoris
		VueFavorisCartesListes favorisCartesListesView = new VueFavorisCartesListes(this._modeleFavoris);
		ControleurFavorisCartesListes favorisCartesListesController = new ControleurFavorisCartesListes(
				this._modeleFavoris, favorisCartesListesView);
		// Création et configuration de la fenêtre
		JFrame frame = new JFrame("Les listes en favoris");
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(favorisCartesListesView, BorderLayout.CENTER);
		frame.add(favorisCartesListesController, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	public void afficherFenetreProjet() {
		// Création de la vue et du contrôleur pour les projets en favoris
		VueFavorisProjets favorisProjetsView = new VueFavorisProjets(this._modeleFavoris);
		ControleurFavorisProjet favorisProjetController = new ControleurFavorisProjet(this._modeleFavoris,
				favorisProjetsView);

		// Création et configuration de la fenêtre
		JFrame frame = new JFrame("Les projets en favoris");
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(favorisProjetsView, BorderLayout.CENTER);
		frame.add(favorisProjetController, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}
}
