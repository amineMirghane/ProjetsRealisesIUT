/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueCarte représente une vue pour afficher les informations d'une carte.
 * Elle contient des JLabel pour afficher l'ID, le nom, la description, la date de début,
 * la date limite et le statut de la carte.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueCarte extends JPanel {
    Carte _modeleCarte; // Instance de la classe Carte pour représenter les données de la carte
    JLabel idLabel; // JLabel pour afficher l'ID de la carte
    JLabel nomLabel; // JLabel pour afficher le nom de la carte
    JLabel descriptionLabel; // JLabel pour afficher la description de la carte
    JLabel dateDeDebutLabel; // JLabel pour afficher la date de début de la carte
    JLabel dateLimiteLabel; // JLabel pour afficher la date limite de la carte
    JLabel statusLabel; // JLabel pour afficher le statut de la carte

    public VueCarte(Carte modeleCarte) {
        this._modeleCarte = modeleCarte; // Initialise l'instance de Carte avec le modèle de données de la carte

        // Crée des JLabel vides pour afficher les informations de la carte
        this.idLabel = new JLabel();
        this.nomLabel = new JLabel();
        this.descriptionLabel = new JLabel();
        this.dateDeDebutLabel = new JLabel();
        this.dateLimiteLabel = new JLabel();
        this.statusLabel = new JLabel();

        JPanel infoPanel = new JPanel(); // Crée un JPanel pour regrouper les JLabel
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Définit le layout du JPanel comme un BoxLayout vertical
        infoPanel.add(idLabel); // Ajoute les JLabel au JPanel
        infoPanel.add(nomLabel);
        infoPanel.add(descriptionLabel);
        infoPanel.add(dateDeDebutLabel);
        infoPanel.add(dateLimiteLabel);
        infoPanel.add(statusLabel);

        this.setLayout(new FlowLayout(FlowLayout.LEFT)); // Définit le layout du JPanel principal comme un FlowLayout aligné à gauche
        this.add(infoPanel); // Ajoute le JPanel contenant les JLabel au JPanel principal
        this.majVueCarte(); // Met à jour l'affichage des informations de la carte
    }

    // Met à jour l'affichage des informations de la carte en utilisant les données du modèle de carte.
    public void majVueCarte() {
        this.idLabel.setText("ID de la carte : " + this._modeleCarte.get_idCarte()); // Affiche l'ID de la carte avec le texte correspondant
        this.nomLabel.setText("Nom de la carte : " + this._modeleCarte.get_nomCarte()); // Affiche le nom de la carte avec le texte correspondant
        this.descriptionLabel.setText("Description : " + this._modeleCarte.get_descriptionCarte()); // Affiche la description de la carte avec le texte correspondant
        this.dateDeDebutLabel.setText("Date de début : " + this._modeleCarte.get_dateDeDebutCarte().toString()); // Affiche la date de début de la carte avec le texte correspondant
        this.dateLimiteLabel.setText("Date limite : " + this._modeleCarte.get_dateLimite().toString()); // Affiche la date limite de la carte avec le texte correspondant
        this.statusLabel.setText("Status : " + this._modeleCarte.get_statusCarte()); // Affiche le statut de la carte avec le texte correspondant
    }
}
