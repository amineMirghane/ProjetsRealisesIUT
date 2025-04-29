/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Ce contrôleur gère les interactions avec la vue de création de projet.
 * Il est responsable de la création d'un nouveau projet, de la mise à jour de la liste des projets
 * et de la réinitialisation des champs de saisie.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurCreerProjet extends JPanel implements ActionListener {
	// Constante pour l'action de création
	public static final String ACTION_CREER = "CREER";

	// Références aux modèles, vues et composants
	Utilisateur _modeleUtilisateur;
	VueCreerProjet _vueCreerProjet;
	VueListeProjet _vueListeProjet;
	JButton creerButton;

	public ControleurCreerProjet(Utilisateur modeleUtilisateur, VueCreerProjet vueCreerProjet,
			VueListeProjet vueListeProjet) {
		this._modeleUtilisateur = modeleUtilisateur;
		this._vueCreerProjet = vueCreerProjet;
		this._vueListeProjet = vueListeProjet;

		// Création du bouton de création et assignation de l'action
		this.creerButton = new JButton("Créer");
		creerButton.setSize(50, 20);
		this.creerButton.setActionCommand(ACTION_CREER);
		this.creerButton.addActionListener(this);

		// Configuration du layout
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		// Ajout du bouton au panneau
		this.add(creerButton);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_CREER)) {
			// Création d'un nouveau projet et ajout à la liste des projets de l'utilisateur
			this._modeleUtilisateur.AjouterProjet(new Projet(this._vueCreerProjet.getNomFieldText(),
					this._vueCreerProjet.getDescriptionFieldText(), this._vueCreerProjet.getStatusFieldText()));

			// Réinitialisation des champs de saisie
			this._vueCreerProjet.clearFields();

			// Mise à jour de la vue liste des projets
			this._vueListeProjet.majVueListeProjet();
		}
	}
}
