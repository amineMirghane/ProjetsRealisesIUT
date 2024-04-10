/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueSupprimerMembre représente une vue permettant de supprimer un membre d'une carte.
 * Elle affiche un champ de texte pour saisir l'ID du membre à supprimer.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueSupprimerMembre extends JPanel {
    Carte _modeleCarte; // Instance de la classe Carte pour représenter les données de la carte
    JLabel idLabel; // JLabel pour afficher le libellé du champ de saisie de l'ID du membre
    JTextField idField; // JTextField pour saisir l'ID du membre à supprimer

    public VueSupprimerMembre(Carte modeleCarte) {
        this._modeleCarte = modeleCarte; // Initialise l'instance de Carte avec le modèle de données de la carte

        idLabel = new JLabel("ID du membre : "); // Crée le JLabel pour afficher le libellé du champ de saisie de l'ID du membre
        idField = new JTextField(5); // Crée le JTextField pour saisir l'ID du membre à supprimer

        this.add(idLabel); // Ajoute le JLabel du libellé du champ de saisie de l'ID du membre au JPanel
        this.add(idField); // Ajoute le JTextField du champ de saisie de l'ID du membre au JPanel
    }

    // Récupère le texte saisi dans le champ de saisie de l'ID du membre.
    public int getIdFieldText() {
        return Integer.parseInt(idField.getText());
    }

    // Efface le contenu du champ de saisie de l'ID du membre.
    public void clearFields() {
        idField.setText("");
    }
}
