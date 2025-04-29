/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Ce contrôleur gère les interactions avec la carte dans l'application.
 * Il est responsable de la création des vues associées et de la gestion des événements.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurCarte extends JPanel implements ActionListener {
	// Constantes pour les actions
	public static final String ACTION_VOIR = "VOIR";
	public static final String ACTION_CHANGER = "CHANGER";
	public static final String ACTION_FAV = "FAVORIS";

	// Références aux modèles et vues
	Carte _modeleCarte;
	VueCarte _vueCarte;
	JButton listeMembresButton;
	JButton changerStatusButton;
	JButton favorisButton;
	Favoris _modeleFavoris;

	public ControleurCarte(Carte modeleCarte, VueCarte vueCarte, Favoris modeleFavoris) {
		this._modeleCarte = modeleCarte;
		this._vueCarte = vueCarte;
		this._modeleFavoris = modeleFavoris;

		// Création des boutons et assignation des actions
		this.listeMembresButton = new JButton("Voir les membres de la carte");
		this.changerStatusButton = new JButton("Changer le status de la carte");
		this.favorisButton = new JButton("Ajouter aux favoris");
		this.listeMembresButton.setActionCommand(ACTION_VOIR);
		this.changerStatusButton.setActionCommand(ACTION_CHANGER);
		this.favorisButton.setActionCommand(ACTION_FAV);
		this.listeMembresButton.addActionListener(this);
		this.changerStatusButton.addActionListener(this);
		this.favorisButton.addActionListener(this);

		// Configuration du layout
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		// Ajout des boutons au panneau
		this.add(listeMembresButton);
		this.add(changerStatusButton);
		this.add(favorisButton);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_VOIR)) {
			// Action : Afficher les membres de la carte
			this.afficherMembresCarte();
		} else if (e.getActionCommand().equals(ACTION_CHANGER)) {
			// Action : Afficher la fenêtre pour changer le statut de la carte
			this.afficherFenetreChangerStatus();
		} else if (e.getActionCommand().equals(ACTION_FAV)) {
			// Action : Ajouter la carte aux favoris
			this._modeleFavoris.AjouterFavoris(_modeleCarte);
		}
	}

	public void afficherMembresCarte() {
		// Création de la vue et du contrôleur pour la gestion des membres de la carte
		VueGestionMembres gestionMembresView = new VueGestionMembres(this._modeleCarte);
		ControleurGestionMembres gestionMembresController = new ControleurGestionMembres(this._modeleCarte,
				gestionMembresView);

		// Création de la fenêtre
		JFrame frame = new JFrame("Liste des membres");
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		// Ajout de la vue et du contrôleur à la fenêtre
		frame.add(gestionMembresView, BorderLayout.CENTER);
		frame.add(gestionMembresController, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	public void afficherFenetreChangerStatus() {
		// Création de la vue pour changer le statut de la carte
		VueCarteChangerStatus carteChangerStatusView = new VueCarteChangerStatus(this._modeleCarte);

		// Création de la fenêtre
		JFrame frame = new JFrame("Changer le statut de la carte");

		// Création du contrôleur pour changer le statut de la carte
		ControleurCarteChangerStatus carteChangerStatusController = new ControleurCarteChangerStatus(this._modeleCarte,
				carteChangerStatusView, this._vueCarte, frame);

		frame.setResizable(false);
		frame.setLayout(new FlowLayout());

		// Ajout de la vue et du contrôleur à la fenêtre
		frame.add(carteChangerStatusView);
		frame.add(carteChangerStatusController);
		frame.pack();
		frame.setVisible(true);
	}
}