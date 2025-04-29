/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Le contrôleur pour la gestion de l'utilisateur.
 * Il est responsable de gérer les actions et les interactions entre le modèle et la vue.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import modeles.*;
import vues.*;

public class ControleurUtilisateur extends JPanel implements ActionListener {
	// Définition des constantes pour les actions
	public static final String ACTION_PRO = "PROJET";
	public static final String ACTION_FAV = "FAVORIS";
	public static final String ACTION_ENR = "ENREGISTRER";
	public static final String FICHIER = "compte.dat";

	// Références vers le modèle utilisateur, la vue utilisateur et le modèle de
	// compte
	Utilisateur _modeleUtilisateur;
	VueUtilisateur _vueUtilisateur;
	Compte _modeleCompte;

	// Boutons pour les actions
	JButton listeProjetsButton;
	JButton favorisButton;
	JButton saveButton;

	public ControleurUtilisateur(Utilisateur modeleUtilisateur, VueUtilisateur vueUtilisateur, Compte modeleCompte) {
		this._modeleUtilisateur = modeleUtilisateur;
		this._vueUtilisateur = vueUtilisateur;
		this._modeleCompte = modeleCompte;

		// Création des boutons pour les actions et association des actions
		this.listeProjetsButton = new JButton("Voir la liste des projets");
		this.favorisButton = new JButton("Voir les favoris");
		this.saveButton = new JButton("Enregistrer les modifications");
		this.listeProjetsButton.setActionCommand(ACTION_PRO);
		this.favorisButton.setActionCommand(ACTION_FAV);
		this.saveButton.setActionCommand(ACTION_ENR);
		this.listeProjetsButton.addActionListener(this);
		this.favorisButton.addActionListener(this);
		this.saveButton.addActionListener(this);

		// Ajout des boutons au panneau
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(listeProjetsButton);
		this.add(favorisButton);
		this.add(saveButton);
	}

	// Gestion des actions des boutons
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_PRO)) {
			this.afficherFenetreProjet();
		} else if (e.getActionCommand().equals(ACTION_FAV)) {
			this.afficherFenetreFavoris();
		} else if (e.getActionCommand().equals(ACTION_ENR)) {
			this.enregistrer(FICHIER);
		}
	}

	// Affichage de la fenêtre de la liste des projets
	public void afficherFenetreProjet() {
		VueListeProjet listeProjetView = new VueListeProjet(this._modeleUtilisateur);
		ControleurListeProjet listeProjetController = new ControleurListeProjet(this._modeleUtilisateur,
				listeProjetView, this._modeleUtilisateur.get_favoris());
		JFrame frame = new JFrame("Liste des projets");
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(listeProjetView, BorderLayout.CENTER);
		frame.add(listeProjetController, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	// Affichage de la fenêtre des favoris
	public void afficherFenetreFavoris() {
		VueFavoris favorisView = new VueFavoris(this._modeleUtilisateur.get_favoris());
		ControleurFavoris favorisController = new ControleurFavoris(this._modeleUtilisateur.get_favoris(), favorisView);
		JFrame frame = new JFrame("Liste des favoris");
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		frame.add(favorisView);
		frame.add(favorisController);
		frame.pack();
		frame.setVisible(true);
	}

	// Enregistrement des modifications dans un fichier
	public void enregistrer(String fichier) {
		try {
			FileOutputStream fileOut = new FileOutputStream(fichier);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this._modeleCompte);
			out.close();
			fileOut.close();
			System.out.println("Serialization réussie");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Échec de la serialization");
		}
	}
}
