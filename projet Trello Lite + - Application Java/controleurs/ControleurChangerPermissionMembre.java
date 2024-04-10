/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Ce contrôleur gère les interactions avec la vue de changement de permission d'un membre.
 * Il est responsable de la mise à jour de la permission du membre et de la fermeture de la fenêtre parente.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurChangerPermissionMembre extends JPanel implements ActionListener {
	// Constante pour l'action de changement
	public static final String ACTION_CHANGER = "CHANGER";

	// Références aux modèles, vues et composants
	Membre _modeleMembre;
	VueChangerPermissionMembre _vueChangerPermissionMembre;
	VueMembre _vueMembre;
	JButton changerStatusButton;
	JFrame _parentFrame;

	public ControleurChangerPermissionMembre(Membre modeleMembre, VueChangerPermissionMembre vueChangerPermissionMembre,
			VueMembre vueMembre, JFrame parentFrame) {
		this._modeleMembre = modeleMembre;
		this._vueChangerPermissionMembre = vueChangerPermissionMembre;
		this._vueMembre = vueMembre;
		this._parentFrame = parentFrame;

		// Création du bouton de changement et assignation de l'action
		this.changerStatusButton = new JButton("Changer");
		this.changerStatusButton.setActionCommand(ACTION_CHANGER);
		this.changerStatusButton.addActionListener(this);

		// Configuration du layout
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		// Ajout du bouton au panneau
		this.add(changerStatusButton);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_CHANGER)) {
			// Action : Changement de la permission du membre
			this._modeleMembre.changerPermissionMembre(this._vueChangerPermissionMembre.getPermissionFieldText());

			// Mise à jour de la vue du membre
			this._vueMembre.majVueMembre();

			// Fermeture de la fenêtre parente
			this._parentFrame.dispose();
		}
	}
}
