/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Cette classe représente un contrôleur pour ajouter une carte à une liste de cartes.
 * Elle gère l'interaction de l'utilisateur avec le bouton d'ajout de carte.
 * Lorsque le bouton est cliqué, elle récupère les informations de la vue d'ajout de carte
 * et ajoute une nouvelle carte à la liste de cartes.
 * Elle met également à jour la vue de la liste de cartes pour refléter les modifications.
 */

// Import des classes nécessaires
package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurAjouterCarte extends JPanel implements ActionListener {
	// Constante pour l'action du bouton
	public static final String ACTION_AJOUTER = "AJOUTER";

	// Modèle de la liste de cartes
	ListeDeCarte _modeleListeDeCarte;

	// Vue d'ajout de carte
	VueAjouterCarte _vueAjouterCarte;

	// Vue de la liste de cartes
	VueListeDeCarte _vueListeDeCarte;

	// Bouton d'ajout de carte
	JButton ajouterButton;

	// Constructeur
	public ControleurAjouterCarte(ListeDeCarte modeleListeDeCarte, VueAjouterCarte vueAjouterCarte,
			VueListeDeCarte vueListeDeCarte) {
		// Initialisation des attributs
		this._modeleListeDeCarte = modeleListeDeCarte;
		this._vueAjouterCarte = vueAjouterCarte;
		this._vueListeDeCarte = vueListeDeCarte;

		// Création du bouton d'ajout de carte
		this.ajouterButton = new JButton("Ajouter");
		this.ajouterButton.setSize(50, 20);
		this.ajouterButton.setActionCommand(ACTION_AJOUTER);
		this.ajouterButton.addActionListener(this);

		// Configuration du layout du panneau
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		// Ajout du bouton au panneau
		this.add(ajouterButton);
	}

	// Méthode appelée lorsqu'un événement d'action se produit
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_AJOUTER)) {
			// Récupération des informations de la vue d'ajout de carte
			String nom = this._vueAjouterCarte.getNomFieldText();
			String description = this._vueAjouterCarte.getDescriptionFieldText();
			String dateDeDebut = this._vueAjouterCarte.getDateDeDebutFieldText();
			String dateLimite = this._vueAjouterCarte.getDateLimiteFieldText();
			String statut = this._vueAjouterCarte.getStatusFieldText();

			// Ajout d'une nouvelle carte à la liste de cartes
			this._modeleListeDeCarte.AjouterCarte(new Carte(nom, description, dateDeDebut, dateLimite, statut));

			// Effacement des champs de la vue d'ajout de carte
			this._vueAjouterCarte.clearFields();

			// Mise à jour de la vue de la liste de cartes
			this._vueListeDeCarte.majVueListeDeCarte();
		}
	}
}
