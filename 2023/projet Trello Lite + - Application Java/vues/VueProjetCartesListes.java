/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueProjetCartesListes représente une vue pour afficher les cartes et les listes d'un projet.
 * Elle affiche l'ID du projet, le nom du projet et une liste de cartes.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueProjetCartesListes extends JPanel {
	Projet _modeleProjet; // Instance de la classe Projet pour représenter les données du projet
	JLabel idLabel; // JLabel pour afficher l'ID du projet
	JLabel nomLabel; // JLabel pour afficher le nom du projet
	JList<String> listeDeCartesJList; // JList pour afficher la liste de cartes du projet

	public VueProjetCartesListes(Projet modeleProjet) {
		this._modeleProjet = modeleProjet; // Initialise l'instance de Projet avec le modèle de données du projet

		idLabel = new JLabel(); // Crée le JLabel pour afficher l'ID du projet
		nomLabel = new JLabel(); // Crée le JLabel pour afficher le nom du projet
		listeDeCartesJList = new JList<String>(); // Crée la JList pour afficher la liste de cartes

		JPanel infoPanel = new JPanel(); // Crée un JPanel pour contenir les JLabels
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Configure le layout du JPanel en vertical
		infoPanel.add(idLabel); // Ajoute le JLabel de l'ID du projet au JPanel
		infoPanel.add(nomLabel); // Ajoute le JLabel du nom du projet au JPanel

		this.setLayout(new BorderLayout()); // Configure le layout de la vue en utilisant le BorderLayout
		this.add(infoPanel, BorderLayout.NORTH); // Ajoute le JPanel en haut de la vue
		this.add(new JScrollPane(listeDeCartesJList), BorderLayout.CENTER); // Ajoute la JList avec un JScrollPane au
																			// centre de la vue

		this.majVueProjetCartesListes(); // Met à jour la vue avec les données du projet
	}

	// Met à jour les JLabels et la JList avec les données du projet.
	public void majVueProjetCartesListes() {
		idLabel.setText("ID du projet : " + _modeleProjet.get_idProjet()); // Met à jour le JLabel de l'ID du projet
		nomLabel.setText("Nom du projet : " + _modeleProjet.get_nomProjet()); // Met à jour le JLabel du nom du projet

		DefaultListModel<String> listModel = new DefaultListModel<>(); // Crée un DefaultListModel pour la JList
		listModel.clear(); // Efface le contenu actuel du DefaultListModel

		for (ListeDeCarte uneListeDeCarte : _modeleProjet.get_listeDeCarte()) {
			// Parcourt les listes de cartes du projet
			listModel.addElement(
					uneListeDeCarte.get_nomListeDeCarte() + " {" + uneListeDeCarte.get_idListeDeCarte() + "}");
			// Ajoute chaque liste de cartes au DefaultListModel
		}

		listeDeCartesJList.setModel(listModel); // Met à jour la JList avec le DefaultListModel
	}
}
