/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueListeDeCarte représente une vue affichant une liste de cartes.
 * Elle affiche l'ID de la liste de cartes, le nom de la liste de cartes, le nombre de cartes
 * et une liste des cartes contenues dans la liste.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueListeDeCarte extends JPanel {
	ListeDeCarte _modeleListeDeCarte; // Instance de la classe ListeDeCarte pour représenter les données de la liste
										// de cartes
	JLabel idLabel; // JLabel pour afficher l'ID de la liste de cartes
	JLabel nomLabel; // JLabel pour afficher le nom de la liste de cartes
	JLabel nbCartesLabel; // JLabel pour afficher le nombre de cartes dans la liste
	JList<String> carteList; // JList pour afficher la liste des cartes

	public VueListeDeCarte(ListeDeCarte modeleListeDeCarte) {
		this._modeleListeDeCarte = modeleListeDeCarte; // Initialise l'instance de ListeDeCarte avec le modèle de
														// données de la liste de cartes

		// Crée les JLabel et la JList pour afficher les informations de la liste de
		// cartes
		idLabel = new JLabel();
		nomLabel = new JLabel();
		nbCartesLabel = new JLabel();
		carteList = new JList<String>();
		carteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permet la sélection d'une seule carte dans
																			// la liste

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(idLabel);
		infoPanel.add(nomLabel);
		infoPanel.add(nbCartesLabel);

		this.setLayout(new BorderLayout());
		this.add(infoPanel, BorderLayout.NORTH);
		this.add(new JScrollPane(carteList), BorderLayout.CENTER);

		this.majVueListeDeCarte(); // Met à jour la vue de la liste de cartes
	}

	// Met à jour la vue de la liste de cartes avec les informations du modèle de
	// données.
	public void majVueListeDeCarte() {
		this.idLabel.setText("ID de la liste de cartes : " + this._modeleListeDeCarte.get_idListeDeCarte());
		this.nomLabel.setText("Nom de la liste de cartes : " + this._modeleListeDeCarte.get_nomListeDeCarte());
		this.nbCartesLabel.setText("Nombre de cartes : " + this._modeleListeDeCarte.get_nbCartes());

		DefaultListModel<String> listModel = new DefaultListModel<>();
		listModel.clear();

		for (Carte carte : _modeleListeDeCarte.get_cartes()) {
			listModel.addElement(carte.get_nomCarte() + " {" + carte.get_idCarte() + "}");
		}

		carteList.setModel(listModel);
	}
}
