/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Cette classe représente un contrôleur pour ajouter un membre à une carte.
 * Elle gère l'interaction de l'utilisateur avec le bouton d'ajout de membre.
 * Lorsque le bouton est cliqué, elle récupère les informations de la vue d'ajout de membre
 * et ajoute un nouveau membre à la carte.
 * Elle met également à jour la vue de gestion des membres pour refléter les modifications.
 */

// Import des classes nécessaires
package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;
import java.io.*;

public class ControleurAjouterMembre extends JPanel implements ActionListener {
	// Constante pour l'action du bouton
	public static final String ACTION_AJOUTER = "AJOUTER";

	// Constante pour le fichier de sauvegarde
	public static final String FICHIER = "compte.dat";

	// Modèle de la carte
	Carte _modeleCarte;

	// Vue d'ajout de membre
	VueAjouterMembre _vueAjouterMembre;

	// Vue de gestion des membres
	VueGestionMembres _vueGestionMembres;

	// Bouton d'ajout de membre
	JButton ajouterMembreButton;

	// Modèle de compte
	Compte _modeleCompte;

	// Constructeur
	public ControleurAjouterMembre(Carte modeleCarte, VueAjouterMembre vueAjouterMembre,
			VueGestionMembres vueGestionMembres) {
		// Initialisation des attributs
		this._modeleCarte = modeleCarte;
		this._vueAjouterMembre = vueAjouterMembre;
		this._vueGestionMembres = vueGestionMembres;
		this.deserialiserCompte(FICHIER);

		// Création du bouton d'ajout de membre
		ajouterMembreButton = new JButton("Ajouter");
		ajouterMembreButton.setActionCommand(ACTION_AJOUTER);
		ajouterMembreButton.addActionListener(this);

		// Configuration du layout du panneau
		setLayout(new FlowLayout(FlowLayout.CENTER));

		// Ajout du bouton au panneau
		add(ajouterMembreButton);
	}

	// Méthode appelée lorsqu'un événement d'action se produit
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_AJOUTER)) {
			// Recherche de l'utilisateur correspondant à l'ID spécifié dans la vue d'ajout
			// de membre
			for (Utilisateur utilisateur : this._modeleCompte.get_utilisateurs()) {
				if (utilisateur.get_idUser() == this._vueAjouterMembre.getIdFieldText()) {
					// Création d'un nouveau membre avec les informations de l'utilisateur
					Membre membre = new Membre(utilisateur.get_nomUser(), utilisateur.get_prenomUser(),
							utilisateur.get_dateDeNaissanceUser().toString(), utilisateur.get_mailUser(),
							utilisateur.get_genreUser(), utilisateur.get_telUser(), utilisateur.get_adresseUser(),
							utilisateur.get_password(), utilisateur.get_idUser(),
							this._vueAjouterMembre.getTypeFieldText(), this._vueAjouterMembre.getPermissionFieldText());

					// Ajout du membre à la carte
					this._modeleCarte.AjouterMembre(membre);

					// Mise à jour de la vue de gestion des membres

					this._vueGestionMembres.majVueGestionMembres();
					break;
				}
			}
		}
	}

	// Méthode pour désérialiser le modèle de compte à partir d'un fichier
	public void deserialiserCompte(String fichier) {
		File file = new File(fichier);

		if (file.exists()) {
			try {
				FileInputStream fileIn = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fileIn);

				// Désérialisation du modèle de compte
				this._modeleCompte = (Compte) in.readObject();
				in.close();
				fileIn.close();
				System.out.println("Désérialisation réussie");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Échec de la désérialisation");
			}
		} else {
			System.out.println("Le fichier n'existe pas. Le modèle Compte a été sérialisé.");
		}
	}
}
