/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueMembre représente une vue pour afficher les détails d'un membre.
 * Elle affiche les informations telles que l'ID, le nom, le prénom, la date de naissance,
 * l'adresse e-mail, le genre, le numéro de téléphone, l'adresse, le type et les permissions du membre.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueMembre extends JPanel {
	Membre _modeleMembre; // Instance de la classe Membre pour représenter les données du membre
	JLabel idLabel; // JLabel pour afficher l'ID du membre
	JLabel nomLabel; // JLabel pour afficher le nom du membre
	JLabel prenomLabel; // JLabel pour afficher le prénom du membre
	JLabel dateNaissanceLabel; // JLabel pour afficher la date de naissance du membre
	JLabel mailLabel; // JLabel pour afficher l'adresse e-mail du membre
	JLabel genreLabel; // JLabel pour afficher le genre du membre
	JLabel telLabel; // JLabel pour afficher le numéro de téléphone du membre
	JLabel adresseLabel; // JLabel pour afficher l'adresse du membre
	JLabel typeLabel; // JLabel pour afficher le type de membre
	JLabel permissionLabel; // JLabel pour afficher les permissions du membre

	public VueMembre(Membre modeleMembre) {
		this._modeleMembre = modeleMembre; // Initialise l'instance de Membre avec le modèle de données du membre

		// Crée les JLabel pour chaque information du membre
		idLabel = new JLabel();
		nomLabel = new JLabel();
		prenomLabel = new JLabel();
		dateNaissanceLabel = new JLabel();
		mailLabel = new JLabel();
		genreLabel = new JLabel();
		telLabel = new JLabel();
		adresseLabel = new JLabel();
		typeLabel = new JLabel();
		permissionLabel = new JLabel();

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(idLabel);
		infoPanel.add(nomLabel);
		infoPanel.add(prenomLabel);
		infoPanel.add(dateNaissanceLabel);
		infoPanel.add(mailLabel);
		infoPanel.add(genreLabel);
		infoPanel.add(telLabel);
		infoPanel.add(adresseLabel);
		infoPanel.add(typeLabel);
		infoPanel.add(permissionLabel);

		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(infoPanel);

		this.majVueMembre();
	}

	// Met à jour la vue avec les informations du membre.
	public void majVueMembre() {
		this.idLabel.setText("ID : " + this._modeleMembre.get_idUser());
		this.nomLabel.setText("Nom : " + this._modeleMembre.get_nomUser());
		this.prenomLabel.setText("Prénom : " + this._modeleMembre.get_prenomUser());
		this.dateNaissanceLabel.setText("Date de naissance : " + this._modeleMembre.get_dateDeNaissanceUser());
		this.mailLabel.setText("Mail : " + this._modeleMembre.get_mailUser());
		this.genreLabel.setText("Genre : " + this._modeleMembre.get_genreUser());
		this.telLabel.setText("Téléphone : +33" + this._modeleMembre.get_telUser());
		this.adresseLabel.setText("Adresse : " + this._modeleMembre.get_adresseUser());
		this.typeLabel.setText("Type : " + this._modeleMembre.get_typeMembre());
		this.permissionLabel.setText("Permission : " + this._modeleMembre.get_permissionMembre());
	}
}
