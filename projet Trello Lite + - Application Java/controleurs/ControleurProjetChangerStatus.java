/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Le contrôleur pour la modification du status d'un projet.
 * Il est responsable de gérer les actions et les interactions entre le modèle et la vue.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurProjetChangerStatus extends JPanel implements ActionListener {
	// Définition de la constante pour l'action de changement de status
	public static final String ACTION_CHANGER = "CHANGER";

	// Références vers le modèle, la vue, le bouton et le frame parent
	Projet _modeleProjet;
	VueProjetChangerStatus _vueProjetChangerStatus;
	VueProjet _vueProjet;
	JButton changerStatusButton;
	JFrame _parentFrame;

	public ControleurProjetChangerStatus(Projet modeleProjet, VueProjetChangerStatus vueProjetChangerStatus, VueProjet vueProjet, JFrame parentFrame) {
		this._modeleProjet = modeleProjet;
		this._vueProjetChangerStatus = vueProjetChangerStatus;
		this._vueProjet = vueProjet;
		this._parentFrame = parentFrame;

		// Création du bouton et association de l'action
		this.changerStatusButton = new JButton("Changer");
		this.changerStatusButton.setActionCommand(ACTION_CHANGER);
		this.changerStatusButton.addActionListener(this);

		// Configuration du layout et ajout du bouton au panneau
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(changerStatusButton);
	}

	// Gestion de l'action du bouton
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_CHANGER)) {
			// Modification du status du projet avec la valeur saisie dans la vue
			this._modeleProjet.changerStatus(this._vueProjetChangerStatus.getStatusFieldText());
			
			// Mise à jour de la vue principale du projet
			this._vueProjet.majVueProjet();
			
			// Fermeture du frame parent
			this._parentFrame.dispose();
		}
	}
}