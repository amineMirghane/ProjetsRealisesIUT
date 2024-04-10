/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Le contrôleur pour la fonctionnalité de suppression d'une liste de cartes dans les favoris.
 * Gère les interactions entre le modèle Favoris, la vue VueFavorisSupprimerListe et la vue VueFavorisCartesListes.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurFavorisSupprimerListe extends JPanel implements ActionListener {
    public static final String ACTION_SUPPRIMER = "SUPPRIMER";
    Favoris _modeleFavoris;
    VueFavorisSupprimerListe _vueFavorisSupprimerListe;
    VueFavorisCartesListes _vueFavorisCartesListes;
    JButton supprimerCarteButton;
    JFrame _parentFrame;

    public ControleurFavorisSupprimerListe(Favoris modeleFavoris, VueFavorisSupprimerListe vueFavorisSupprimerListe,
            VueFavorisCartesListes vueFavorisCartesListes, JFrame parentFrame) {
        this._modeleFavoris = modeleFavoris;
        this._vueFavorisSupprimerListe = vueFavorisSupprimerListe;
        this._vueFavorisCartesListes = vueFavorisCartesListes;
        this._parentFrame = parentFrame;

        // Crée le bouton "Supprimer"
        this.supprimerCarteButton = new JButton("Supprimer");
        this.supprimerCarteButton.setActionCommand(ACTION_SUPPRIMER);
        this.supprimerCarteButton.addActionListener(this);
        this.add(supprimerCarteButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
            // Parcourt les listes de cartes du modèle Favoris pour trouver la liste correspondante à l'ID
            for (ListeDeCarte liste : this._modeleFavoris.get_listeDeCarte()) {
                if (liste.get_idListeDeCarte() == this._vueFavorisSupprimerListe.getIdFieldText()) {
                    // Supprime la liste de cartes du modèle Favoris
                    this._modeleFavoris.supprimerListeDeCarte(this._vueFavorisSupprimerListe.getIdFieldText());
                    
                    // Efface les champs de saisie dans la vue VueFavorisSupprimerListe
                    this._vueFavorisSupprimerListe.clearFields();
                    
                    // Met à jour la vue VueFavorisCartesListes
                    this._vueFavorisCartesListes.majVueFavorisCartesListes();
                    
                    // Ferme la fenêtre parente
                    this._parentFrame.dispose();
                    
                    break;
                }
            }
        }
    }

}
