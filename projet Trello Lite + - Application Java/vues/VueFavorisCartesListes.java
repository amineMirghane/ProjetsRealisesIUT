/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueFavorisCartesListes représente une vue permettant d'afficher les informations d'un favori contenant une liste de listes de cartes.
 * Elle contient un JLabel pour afficher l'ID du favori et une JList pour afficher les listes de cartes associées.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueFavorisCartesListes extends JPanel {
    Favoris _modeleFavoris; // Instance de la classe Favoris pour représenter les données du favori
    JLabel idLabel; // JLabel pour afficher l'ID du favori
    JList<String> listeDeCartesJList; // JList pour afficher les listes de cartes associées au favori

    public VueFavorisCartesListes(Favoris modeleFavoris) {
        this._modeleFavoris = modeleFavoris; // Initialise l'instance de Favoris avec le modèle de données du favori

        this.idLabel = new JLabel(); // Crée un JLabel vide pour afficher l'ID du favori
        this.listeDeCartesJList = new JList<String>(); // Crée une JList vide pour afficher les listes de cartes associées

        this.setLayout(new BorderLayout()); // Définit le layout du JPanel comme BorderLayout
        this.add(idLabel, BorderLayout.NORTH); // Ajoute le JLabel en position NORTH (en haut) du JPanel
        this.add(new JScrollPane(listeDeCartesJList), BorderLayout.CENTER); // Ajoute la JList avec un JScrollPane en position CENTER (au centre) du JPanel

        this.majVueFavorisCartesListes(); // Met à jour la vue des listes de cartes associées au favori
    }

    /**
     * Met à jour la vue des listes de cartes associées au favori en affichant l'ID du favori et les noms des listes de cartes dans la JList.
     */
    public void majVueFavorisCartesListes() {
        this.idLabel.setText("ID du favori : " + this._modeleFavoris.get_idFavoris()); // Affiche l'ID du favori dans le JLabel

        DefaultListModel<String> listModel = new DefaultListModel<>(); // Crée un DefaultListModel pour stocker les noms des listes de cartes
        listModel.clear(); // Efface le modèle de liste précédent

        for (ListeDeCarte uneListeDeCarte : _modeleFavoris.get_listeDeCarte()) { // Parcourt les listes de cartes associées au favori
            listModel.addElement(
                    uneListeDeCarte.get_nomListeDeCarte() + " {" + uneListeDeCarte.get_idListeDeCarte() + "}"); // Ajoute le nom de la liste de cartes avec son ID au modèle de liste
        }

        listeDeCartesJList.setModel(listModel); // Applique le modèle de liste à la JList pour afficher les noms des listes de cartes
    }
}
