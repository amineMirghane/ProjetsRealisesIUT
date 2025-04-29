/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueUtilisateur représente une vue permettant d'afficher les informations d'un utilisateur.
 * Elle affiche les informations telles que l'ID, le nom, le prénom, la date de naissance, l'e-mail,
 * le genre, le numéro de téléphone et l'adresse de l'utilisateur.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueUtilisateur extends JPanel {
    Utilisateur _modeleUtilisateur; // Instance de la classe Utilisateur pour représenter les données de l'utilisateur
    JLabel idLabel; // JLabel pour afficher l'ID de l'utilisateur
    JLabel nomLabel; // JLabel pour afficher le nom de l'utilisateur
    JLabel prenomLabel; // JLabel pour afficher le prénom de l'utilisateur
    JLabel dateNaissanceLabel; // JLabel pour afficher la date de naissance de l'utilisateur
    JLabel mailLabel; // JLabel pour afficher l'e-mail de l'utilisateur
    JLabel genreLabel; // JLabel pour afficher le genre de l'utilisateur
    JLabel telLabel; // JLabel pour afficher le numéro de téléphone de l'utilisateur
    JLabel adresseLabel; // JLabel pour afficher l'adresse de l'utilisateur

    public VueUtilisateur(Utilisateur modeleUtilisateur) {
        this._modeleUtilisateur = modeleUtilisateur; // Initialise l'instance de Utilisateur avec le modèle de données de l'utilisateur

        idLabel = new JLabel(); // Crée le JLabel pour afficher l'ID de l'utilisateur
        nomLabel = new JLabel(); // Crée le JLabel pour afficher le nom de l'utilisateur
        prenomLabel = new JLabel(); // Crée le JLabel pour afficher le prénom de l'utilisateur
        dateNaissanceLabel = new JLabel(); // Crée le JLabel pour afficher la date de naissance de l'utilisateur
        mailLabel = new JLabel(); // Crée le JLabel pour afficher l'e-mail de l'utilisateur
        genreLabel = new JLabel(); // Crée le JLabel pour afficher le genre de l'utilisateur
        telLabel = new JLabel(); // Crée le JLabel pour afficher le numéro de téléphone de l'utilisateur
        adresseLabel = new JLabel(); // Crée le JLabel pour afficher l'adresse de l'utilisateur

        JPanel infoPanel = new JPanel(); // Crée un JPanel pour contenir les labels d'information
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Définit le gestionnaire de disposition du JPanel comme une disposition en colonne

        // Ajoute les JLabels d'information au JPanel
        infoPanel.add(idLabel);
        infoPanel.add(nomLabel);
        infoPanel.add(prenomLabel);
        infoPanel.add(dateNaissanceLabel);
        infoPanel.add(mailLabel);
        infoPanel.add(genreLabel);
        infoPanel.add(telLabel);
        infoPanel.add(adresseLabel);

        this.setLayout(new FlowLayout(FlowLayout.LEFT)); // Définit le gestionnaire de disposition du JPanel courant comme un alignement à gauche
        this.add(infoPanel); // Ajoute le JPanel d'informations au JPanel courant
        this.majVueUtilisateur(); // Met à jour la vue de l'utilisateur avec les données actuelles
    }

    // Met à jour la vue de l'utilisateur avec les données actuelles de l'utilisateur.
    public void majVueUtilisateur() {
        idLabel.setText("ID : " + this._modeleUtilisateur.get_idUser()); // Met à jour le texte de l'ID de l'utilisateur
        nomLabel.setText("Nom : " + this._modeleUtilisateur.get_nomUser()); // Met à jour le texte du nom de l'utilisateur
        prenomLabel.setText("Prénom : " + this._modeleUtilisateur.get_prenomUser()); // Met à jour le texte du prénom de l'utilisateur
        dateNaissanceLabel.setText("Date de naissance : " + this._modeleUtilisateur.get_dateDeNaissanceUser()); // Met à jour le texte de la date de naissance de l'utilisateur
        mailLabel.setText("Mail : " + this._modeleUtilisateur.get_mailUser()); // Met à jour le texte de l'e-mail de l'utilisateur
        genreLabel.setText("Genre : " + this._modeleUtilisateur.get_genreUser()); // Met à jour le texte du genre de l'utilisateur
        telLabel.setText("Téléphone : +33" + this._modeleUtilisateur.get_telUser()); // Met à jour le texte du numéro de téléphone de l'utilisateur
        adresseLabel.setText("Adresse : " + this._modeleUtilisateur.get_adresseUser()); // Met à jour le texte de l'adresse de l'utilisateur
    }
}
