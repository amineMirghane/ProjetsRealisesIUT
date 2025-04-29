/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Le contrôleur pour la suppression d'une liste de cartes dans un projet.
 * Il est responsable de gérer les actions et les interactions entre le modèle et la vue.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurSupprimerListe extends JPanel implements ActionListener {
	// Définition de la constante pour l'action de suppression
	public static final String ACTION_SUPPRIMER = "SUPPRIMER";

	// Références vers le modèle de projet, la vue de suppression de liste, la vue du projet (avec les cartes et les listes) et le frame parent
	Projet _modeleProjet;
	VueSupprimerListe _vueSupprimerListe;
	VueProjetCartesListes _vueProjetCartesListes;
	JButton supprimerListeButton;
	JFrame _parentFrame;

	public ControleurSupprimerListe(Projet modeleProjet, VueSupprimerListe vueProjetSupprimerCartesListes,
			VueProjetCartesListes vueProjetCartesListes, JFrame parentFrame) {
		this._modeleProjet = modeleProjet;
		this._vueSupprimerListe = vueProjetSupprimerCartesListes;
		this._vueProjetCartesListes = vueProjetCartesListes;
		this._parentFrame = parentFrame;

		// Création du bouton de suppression et association de l'action
		this.supprimerListeButton = new JButton("Supprimer");
		this.supprimerListeButton.setActionCommand(ACTION_SUPPRIMER);
		this.supprimerListeButton.addActionListener(this);

		// Ajout du bouton au panneau
		this.add(supprimerListeButton);
	}

	// Gestion de l'action du bouton
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
			// Suppression de la liste de cartes correspondante dans le modèle de projet
			this._modeleProjet.SuppListeDeCarte(this._vueSupprimerListe.getIdFieldText());
			
			// Effacement des champs de saisie dans la vue de suppression de liste
			this._vueSupprimerListe.clearFields();
			
			// Mise à jour de la vue du projet (avec les cartes et les listes)
			this._vueProjetCartesListes.majVueProjetCartesListes();
			
			// Fermeture du frame parent
			this._parentFrame.dispose();
		}
	}
}
