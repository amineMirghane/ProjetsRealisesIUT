/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Le contrôleur pour la suppression d'une carte dans une liste de cartes.
 * Il est responsable de gérer les actions et les interactions entre le modèle et la vue.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurSupprimerCarte extends JPanel implements ActionListener {
	// Définition de la constante pour l'action de suppression
	public static final String ACTION_SUPPRIMER = "SUPPRIMER";

	// Références vers le modèle de liste de cartes, la vue de suppression de carte,
	// la vue de liste de cartes et le frame parent
	ListeDeCarte _modeleListeDeCarte;
	VueSupprimerCarte _vueSupprimerCarte;
	VueListeDeCarte _vueListeDeCarte;
	JFrame _parentFrame;
	JButton supprimerCarteButton;

	public ControleurSupprimerCarte(ListeDeCarte modeleListeDeCarte, VueSupprimerCarte vueSupprimerCarte,
			VueListeDeCarte vueListeDeCarte, JFrame parentFrame) {
		this._modeleListeDeCarte = modeleListeDeCarte;
		this._vueSupprimerCarte = vueSupprimerCarte;
		this._vueListeDeCarte = vueListeDeCarte;
		this._parentFrame = parentFrame;

		// Création du bouton de suppression et association de l'action
		this.supprimerCarteButton = new JButton("Supprimer");
		this.supprimerCarteButton.setActionCommand(ACTION_SUPPRIMER);
		this.supprimerCarteButton.addActionListener(this);

		// Ajout du bouton au panneau
		this.add(supprimerCarteButton);
	}

	// Gestion de l'action du bouton
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
			// Suppression de la carte correspondante dans la liste de cartes du modèle
			this._modeleListeDeCarte.SupprimerCarte(this._vueSupprimerCarte.getIdFieldText());

			// Effacement des champs de saisie dans la vue de suppression de carte
			this._vueSupprimerCarte.clearFields();

			// Mise à jour de la vue de la liste de cartes
			this._vueListeDeCarte.majVueListeDeCarte();

			// Fermeture du frame parent
			this._parentFrame.dispose();
		}
	}
}