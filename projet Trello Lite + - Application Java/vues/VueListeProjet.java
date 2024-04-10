/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueListeProjet représente une vue affichant une liste de projets.
 * Elle affiche l'ID de l'utilisateur, le nom de l'utilisateur, le nombre de projets
 * et une liste des projets associés à l'utilisateur.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueListeProjet extends JPanel {
	Utilisateur _modeleUtilisateur; // Instance de la classe Utilisateur pour représenter les données de
									// l'utilisateur
	JLabel idLabel; // JLabel pour afficher l'ID de l'utilisateur
	JLabel nomLabel; // JLabel pour afficher le nom de l'utilisateur
	JLabel nbProjetsLabel; // JLabel pour afficher le nombre de projets associés à l'utilisateur
	JList<String> projetList; // JList pour afficher la liste des projets

	public VueListeProjet(Utilisateur modeleUtilisateur) {
		this._modeleUtilisateur = modeleUtilisateur; // Initialise l'instance de Utilisateur avec le modèle de données
														// de l'utilisateur

		// Crée les JLabel et la JList pour afficher les informations de l'utilisateur
		// et les projets associés
		idLabel = new JLabel();
		nomLabel = new JLabel();
		nbProjetsLabel = new JLabel();
		projetList = new JList<String>();
		projetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permet la sélection d'un seul projet dans
																			// la liste

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(idLabel);
		infoPanel.add(nomLabel);
		infoPanel.add(nbProjetsLabel);

		this.setLayout(new BorderLayout());
		this.add(infoPanel, BorderLayout.NORTH);
		this.add(new JScrollPane(projetList), BorderLayout.CENTER);

		this.majVueListeProjet(); // Met à jour la vue de la liste de projets
	}

	// Met à jour la vue de la liste de projets avec les informations du modèle de
	// données.
	public void majVueListeProjet() {
		this.idLabel.setText("ID de l'utilisateur : " + this._modeleUtilisateur.get_idUser());
		this.nomLabel.setText("Nom de l'utilisateur : " + this._modeleUtilisateur.get_nomUser() + " "
				+ this._modeleUtilisateur.get_prenomUser());
		this.nbProjetsLabel.setText("Nombre de projets : " + this._modeleUtilisateur.get_nbProjets());

		DefaultListModel<String> listModel = new DefaultListModel<>();
		listModel.clear();

		for (Projet p : this._modeleUtilisateur.get_projets()) {
			listModel.addElement(p.get_nomProjet() + " {" + p.get_idProjet() + "}");
		}

		projetList.setModel(listModel);
	}
}
