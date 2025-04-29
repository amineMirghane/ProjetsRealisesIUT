/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueGestionMembres représente une vue permettant de gérer les membres d'une carte.
 * Elle affiche l'ID et le nom de la carte, ainsi qu'une liste des membres associés à la carte.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueGestionMembres extends JPanel {
	Carte _modeleCarte; // Instance de la classe Carte pour représenter les données de la carte
	JLabel idLabel; // JLabel pour afficher l'ID de la carte
	JLabel nomLabel; // JLabel pour afficher le nom de la carte
	JList<String> membresJList; // JList pour afficher la liste des membres de la carte

	public VueGestionMembres(Carte modeleCarte) {
		this._modeleCarte = modeleCarte; // Initialise l'instance de Carte avec le modèle de données de la carte

		this.idLabel = new JLabel(); // Crée un JLabel pour afficher l'ID de la carte
		this.nomLabel = new JLabel(); // Crée un JLabel pour afficher le nom de la carte
		this.membresJList = new JList<String>(); // Crée une JList pour afficher la liste des membres de la carte

		JPanel infoPanel = new JPanel(); // Crée un JPanel pour afficher les informations de la carte
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Utilise un gestionnaire de disposition
																			// vertical pour les composants du JPanel
		infoPanel.add(idLabel); // Ajoute le JLabel de l'ID de la carte dans le JPanel
		infoPanel.add(nomLabel); // Ajoute le JLabel du nom de la carte dans le JPanel

		JPanel buttonPanel = new JPanel(); // Crée un JPanel pour les boutons de gestion des membres

		this.setLayout(new BorderLayout()); // Utilise un gestionnaire de disposition border pour le JPanel principal
		this.add(infoPanel, BorderLayout.NORTH); // Ajoute le JPanel des informations de la carte dans la partie nord du
													// JPanel principal
		this.add(new JScrollPane(membresJList), BorderLayout.CENTER); // Ajoute la JList des membres de la carte avec un
																		// JScrollPane dans la partie centrale du JPanel
																		// principal

		this.majVueGestionMembres(); // Met à jour la vue des membres de la carte
	}

	// Met à jour la vue des membres de la carte en affichant l'ID de la carte, le
	// nom de la carte et la liste des membres associés à la carte.
	public void majVueGestionMembres() {
		this.idLabel.setText("ID de la carte : " + this._modeleCarte.get_idCarte()); // Affiche l'ID de la carte dans le
																						// JLabel correspondant
		this.nomLabel.setText("Nom de la carte : " + this._modeleCarte.get_nomCarte()); // Affiche le nom de la carte
																						// dans le JLabel correspondant

		DefaultListModel<String> listModel = new DefaultListModel<>(); // Crée un DefaultListModel pour la liste des
																		// membres
		listModel.clear(); // Efface les données existantes dans le modèle de liste

		for (Membre membre : _modeleCarte.get_membres()) {
			// Ajoute chaque membre à la liste en affichant le nom, prénom et ID du membre
			listModel.addElement(membre.get_nomUser().toUpperCase() + " " + membre.get_prenomUser() + " {"
					+ membre.get_idMembre() + "}");
		}

		membresJList.setModel(listModel); // Applique le modèle de liste à la JList des membres
	}
}
