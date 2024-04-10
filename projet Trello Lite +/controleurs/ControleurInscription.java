/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Ce contrôleur gère le processus d'inscription d'un utilisateur.
 */
package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import modeles.*;
import vues.*;

public class ControleurInscription extends JPanel implements ActionListener {
	public static final String ACTION_CREER = "CREER";
	public static final String FICHIER = "compte.dat";
	Compte _modeleCompte;
	VueInscription _vueInscription;
	JButton creerButton;

	public ControleurInscription(Compte modeleCompte, VueInscription vueInscription) {
		this._modeleCompte = modeleCompte;
		this._vueInscription = vueInscription;

		this.creerButton = new JButton("Creer");
		this.creerButton.setSize(50, 20);
		this.creerButton.setActionCommand(ACTION_CREER);
		this.creerButton.addActionListener(this);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(creerButton);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_CREER)) {
			// Récupération des informations saisies dans la vue d'inscription
			String nom = _vueInscription.getNomFieldText();
			String prenom = _vueInscription.getPrenomFieldText();
			String dateNaissance = _vueInscription.getDateNaissanceFieldText();
			String mail = _vueInscription.getMailFieldText();
			String genre = _vueInscription.getGenreFieldText();
			int tel = _vueInscription.getTelFieldText();
			String adresse = _vueInscription.getAdresseFieldText();
			String password = _vueInscription.getMDPFieldText();

			// Création d'un nouvel utilisateur avec les informations saisies
			Utilisateur utilisateur = new Utilisateur(nom, prenom, dateNaissance, mail, genre, tel, adresse, password);

			// Ajout de l'utilisateur au modèle de compte
			_modeleCompte.AjouterUser(utilisateur);

			// Sérialisation du modèle de compte dans un fichier
			this.serialiser(FICHIER);

			// Effacement des champs de la vue d'inscription
			_vueInscription.clearFields();
		}
	}

	public void serialiser(String fichier) {
		try {
			// Ouverture du fichier de sortie
			FileOutputStream fileOut = new FileOutputStream(fichier);

			// Création d'un flux d'objets pour écrire les données dans le fichier
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			// Écriture du modèle de compte dans le fichier
			out.writeObject(this._modeleCompte);

			// Fermeture du flux d'objets et du fichier
			out.close();
			fileOut.close();

			System.out.println("Serealisation réussie");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Echec de la serealisation");
		}
	}
}
