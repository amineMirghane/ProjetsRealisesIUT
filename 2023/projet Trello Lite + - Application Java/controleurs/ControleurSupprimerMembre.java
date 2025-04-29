/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Le contrôleur pour la suppression d'un membre dans une carte.
 * Il est responsable de gérer les actions et les interactions entre le modèle et la vue.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurSupprimerMembre extends JPanel implements ActionListener {
	// Définition de la constante pour l'action de suppression
	public static final String ACTION_SUPPRIMER = "SUPPRIMER";

	// Références vers le modèle de carte, la vue de suppression de membre, la vue
	// de gestion des membres et le frame parent
	Carte _modeleCarte;
	VueSupprimerMembre _vueSupprimerMembre;
	VueGestionMembres _vueGestionMembres;
	JButton supprimerMembreButton;
	JFrame _parentFrame;

	public ControleurSupprimerMembre(Carte modeleCarte, VueSupprimerMembre vueSupprimerMembre,
			VueGestionMembres vueGestionMembres, JFrame parentFrame) {
		this._modeleCarte = modeleCarte;
		this._vueSupprimerMembre = vueSupprimerMembre;
		this._vueGestionMembres = vueGestionMembres;
		this._parentFrame = parentFrame;

		// Création du bouton de suppression et association de l'action
		this.supprimerMembreButton = new JButton("Supprimer un membre");
		this.supprimerMembreButton.setActionCommand(ACTION_SUPPRIMER);
		this.supprimerMembreButton.addActionListener(this);

		// Ajout du bouton au panneau
		this.add(supprimerMembreButton);
	}

	// Gestion de l'action du bouton
	public void actionPerformed(ActionEvent e) {
		// Suppression du membre correspondant dans le modèle de carte
		this._modeleCarte.SuppMembre(this._vueSupprimerMembre.getIdFieldText());

		// Effacement des champs de saisie dans la vue de suppression de membre
		this._vueSupprimerMembre.clearFields();

		// Mise à jour de la vue de gestion des membres
		this._vueGestionMembres.majVueGestionMembres();

		// Fermeture du frame parent
		this._parentFrame.dispose();
	}
}