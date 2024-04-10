/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Le contrôleur pour la fonctionnalité de suppression d'un projet dans les favoris.
 * Gère les interactions entre le modèle Favoris, la vue VueFavorisSupprimerProjet et la vue VueFavorisProjets.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurFavorisSupprimerProjet extends JPanel implements ActionListener {
    public static final String ACTION_SUPPRIMER = "SUPPRIMER";
    Favoris _modeleFavoris;
    VueFavorisSupprimerProjet _vueFavorisSupprimerProjet;
    VueFavorisProjets _vueFavorisProjets;
    JButton supprimerProjetButton;
    JFrame _parentFrame;

    public ControleurFavorisSupprimerProjet(Favoris modeleFavoris, VueFavorisSupprimerProjet vueFavorisSupprimerProjet,
            VueFavorisProjets vueFavorisProjets, JFrame parentFrame) {
        this._modeleFavoris = modeleFavoris;
        this._vueFavorisSupprimerProjet = vueFavorisSupprimerProjet;
        this._vueFavorisProjets = vueFavorisProjets;
        this._parentFrame = parentFrame;

        // Crée le bouton "Supprimer"
        this.supprimerProjetButton = new JButton("Supprimer");
        this.supprimerProjetButton.setActionCommand(ACTION_SUPPRIMER);
        this.supprimerProjetButton.addActionListener(this);
        this.add(supprimerProjetButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
            // Parcourt les projets du modèle Favoris pour trouver le projet correspondant à l'ID
            for (Projet projet : this._modeleFavoris.get_projet()) {
                if (projet.get_idProjet() == this._vueFavorisSupprimerProjet.getIdFieldText()) {
                    // Supprime le projet du modèle Favoris
                    this._modeleFavoris.supprimerProjet(this._vueFavorisSupprimerProjet.getIdFieldText());
                    
                    // Efface les champs de saisie dans la vue VueFavorisSupprimerProjet
                    this._vueFavorisSupprimerProjet.clearFields();
                    
                    // Met à jour la vue VueFavorisProjets
                    this._vueFavorisProjets.majVueFavorisProjets();
                    
                    // Ferme la fenêtre parente
                    this._parentFrame.dispose();
                    
                    break;
                }
            }
        }
    }

}
