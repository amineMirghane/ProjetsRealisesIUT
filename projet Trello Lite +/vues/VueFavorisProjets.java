/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueFavorisProjets représente une vue permettant d'afficher les informations d'un favori contenant une liste de projets.
 * Elle contient un JLabel pour afficher l'ID du favori et une JList pour afficher les projets associés.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueFavorisProjets extends JPanel {
	Favoris _modeleFavoris; // Instance de la classe Favoris pour représenter les données du favori
	JLabel idLabel; // JLabel pour afficher l'ID du favori
	JList<String> projetsJList; // JList pour afficher les projets associés au favori

	public VueFavorisProjets(Favoris modeleFavoris) {
		this._modeleFavoris = modeleFavoris; // Initialise l'instance de Favoris avec le modèle de données du favori

		this.idLabel = new JLabel(); // Crée un JLabel vide pour afficher l'ID du favori
		this.projetsJList = new JList<String>(); // Crée une JList vide pour afficher les projets associés

		JPanel buttonPanel = new JPanel(); // Crée un JPanel pour les boutons (non utilisé dans le code fourni)

		this.setLayout(new BorderLayout()); // Définit le layout du JPanel comme BorderLayout
		this.add(idLabel, BorderLayout.NORTH); // Ajoute le JLabel en position NORTH (en haut) du JPanel
		this.add(new JScrollPane(projetsJList), BorderLayout.CENTER); // Ajoute la JList avec un JScrollPane en position
																		// CENTER (au centre) du JPanel

		this.majVueFavorisProjets(); // Met à jour la vue des projets associés au favori
	}

	// Met à jour la vue des projets associés au favori en affichant l'ID du favori
	// et les noms des projets dans la JList.
	public void majVueFavorisProjets() {
		this.idLabel.setText("ID du favori : " + this._modeleFavoris.get_idFavoris()); // Affiche l'ID du favori dans le
																						// JLabel

		DefaultListModel<String> listModel = new DefaultListModel<>(); // Crée un DefaultListModel pour stocker les noms
																		// des projets
		listModel.clear(); // Efface le modèle de liste précédent

		for (Projet projet : _modeleFavoris.get_projet()) { // Parcourt les projets associés au favori
			listModel.addElement(projet.get_nomProjet() + " {" + projet.get_idProjet() + "}"); // Ajoute le nom du
																								// projet avec son ID au
																								// modèle de liste
		}

		projetsJList.setModel(listModel); // Applique le modèle de liste à la JList pour afficher les noms des projets
	}
}
