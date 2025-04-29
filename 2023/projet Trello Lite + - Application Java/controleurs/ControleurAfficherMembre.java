/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Cette classe représente un contrôleur pour afficher un membre.
 * Elle gère l'interaction de l'utilisateur avec le bouton d'affichage du membre.
 * Lorsque le bouton est cliqué, elle affiche les informations du membre correspondant.
 */

// Import des classes nécessaires
package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurAfficherMembre extends JPanel implements ActionListener {
	// Constante pour l'action du bouton
	public static final String ACTION_OUVRIR = "OUVRIR";

	// Modèle de carte
	Carte _modeleCarte;

	// Vue pour afficher le membre
	VueAfficherMembre _vueAfficherMembre;

	// Bouton d'affichage du membre
	JButton afficherMembreButton;

	// Fenêtre parente
	JFrame _parentFrame;

	// Constructeur
	public ControleurAfficherMembre(Carte modeleCarte, VueAfficherMembre vueAfficherMembre, JFrame parentFrame) {
		// Initialisation des attributs
		this._modeleCarte = modeleCarte;
		this._vueAfficherMembre = vueAfficherMembre;
		this._parentFrame = parentFrame;

		// Création du bouton d'affichage du membre
		this.afficherMembreButton = new JButton("Afficher");
		this.afficherMembreButton.setActionCommand(ACTION_OUVRIR);
		this.afficherMembreButton.addActionListener(this);

		// Ajout du bouton au panneau
		this.add(afficherMembreButton);
	}

	// Méthode appelée lorsqu'un événement d'action se produit
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_OUVRIR)) {
			// Affichage du membre sélectionné
			this.afficherMembre();

			// Effacement des champs de la vue et fermeture de la fenêtre parente
			this._vueAfficherMembre.clearFields();
			this._parentFrame.dispose();
		}
	}

	// Affichage des informations du membre sélectionné
	public void afficherMembre() {
		for (Membre membre : this._modeleCarte.get_membres()) {
			if (membre.get_idMembre() == this._vueAfficherMembre.getIdFieldText()) {
				// Création de la vue et du contrôleur du membre
				VueMembre membreView = new VueMembre(membre);
				ControleurMembre membreController = new ControleurMembre(membre, membreView);

				// Création de la fenêtre pour afficher le membre
				JFrame frame = new JFrame("Information sur le membre");
				frame.setResizable(false);
				frame.setLayout(new BorderLayout());

				// Ajout de la vue et du contrôleur à la fenêtre
				frame.add(membreView, BorderLayout.CENTER);
				frame.add(membreController, BorderLayout.SOUTH);

				// Configuration et affichage de la fenêtre
				frame.pack();
				frame.setVisible(true);

				// Sortie de la boucle pour afficher un seul membre correspondant
				break;
			}
		}
	}
}
