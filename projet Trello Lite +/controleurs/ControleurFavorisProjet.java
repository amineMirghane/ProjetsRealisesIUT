/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Le contrôleur pour la fonctionnalité de suppression de projet dans les favoris.
 * Gère les interactions entre le modèle Favoris, la vue VueFavorisProjets et la vue VueFavorisSupprimerProjet.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurFavorisProjet extends JPanel implements ActionListener {
    public static final String ACTION_SUPPRIMER = "SUPPRIMER";
    Favoris _modeleFavoris;
    VueFavorisProjets _vueFavorisProjets;
    JButton supprimerProjetButton;

    public ControleurFavorisProjet(Favoris modeleFavoris, VueFavorisProjets vueFavorisProjets) {
        this._modeleFavoris = modeleFavoris;
        this._vueFavorisProjets = vueFavorisProjets;

        // Crée le bouton "Supprimer un projet"
        this.supprimerProjetButton = new JButton("Supprimer un projet");
        this.supprimerProjetButton.setActionCommand(ACTION_SUPPRIMER);
        this.supprimerProjetButton.addActionListener(this);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(supprimerProjetButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
            // Affiche la fenêtre de suppression lorsqu'on clique sur le bouton
            this.afficherFenetreSuppression();
        }
    }

    public void afficherFenetreSuppression() {
        // Crée la vue de la fenêtre de suppression
        VueFavorisSupprimerProjet favorisSupprimerProjetView = new VueFavorisSupprimerProjet(this._modeleFavoris);
        
        // Crée la fenêtre principale
        JFrame frame = new JFrame("Supprimer un projet");
        
        // Crée le contrôleur pour la fenêtre de suppression
        ControleurFavorisSupprimerProjet favorisSupprimerProjetController = new ControleurFavorisSupprimerProjet(
                this._modeleFavoris, favorisSupprimerProjetView, this._vueFavorisProjets, frame);
        
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        
        // Ajoute la vue de suppression et le contrôleur à la fenêtre principale
        frame.add(favorisSupprimerProjetView);
        frame.add(favorisSupprimerProjetController);
        frame.pack();
        frame.setVisible(true);
    }
}
