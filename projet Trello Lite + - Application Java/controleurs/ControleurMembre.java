/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * ControleurMembre est une classe qui sert de contrôleur pour un membre.
 * Elle implémente l'interface ActionListener pour gérer les événements des boutons.
 */
package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurMembre extends JPanel implements ActionListener {
	// Action pour le bouton de changement de permission
	public static final String ACTION_CHANGER = "CHANGER";

	Membre _modeleMembre; // Modèle du membre
	VueMembre _vueMembre; // Vue du membre
	JButton permissionButton; // Bouton pour changer la permission du membre

	// Constructeur de la classe
	public ControleurMembre(Membre modeleMembre, VueMembre vueMembre) {
		this._modeleMembre = modeleMembre;
		this._vueMembre = vueMembre;

		// Création du bouton et ajout de l'action
		this.permissionButton = new JButton("Changer la permission du membre");
		this.permissionButton.setActionCommand(ACTION_CHANGER);
		this.permissionButton.addActionListener(this);
		this.add(permissionButton);
	}

	// Méthode appelée lorsqu'un bouton est cliqué
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_CHANGER)) {
			// Création de la vue pour changer la permission du membre
			VueChangerPermissionMembre changerPermissionMembreView = new VueChangerPermissionMembre(this._modeleMembre);
			JFrame frame = new JFrame(
					"Membre " + this._modeleMembre.get_nomUser() + " " + this._modeleMembre.get_prenomUser());
			// Création du contrôleur pour changer la permission du membre
			ControleurChangerPermissionMembre changerPermissionMembreController = new ControleurChangerPermissionMembre(
					this._modeleMembre, changerPermissionMembreView, this._vueMembre, frame);
			frame.setResizable(false);
			frame.setLayout(new BorderLayout());
			frame.add(changerPermissionMembreView, BorderLayout.CENTER);
			frame.add(changerPermissionMembreController, BorderLayout.SOUTH);
			frame.pack();
			frame.setVisible(true);
		}
	}

}
