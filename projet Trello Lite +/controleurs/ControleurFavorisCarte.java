/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Le contrôleur pour la fonctionnalité de suppression de carte dans les favoris.
 * Gère les interactions entre le modèle Favoris, la vue VueFavorisCarte et la vue VueFavorisSupprimerCarte.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurFavorisCarte extends JPanel implements ActionListener {
    public static final String ACTION_SUPPRIMER = "SUPPRIMER";
    Favoris _modeleFavoris;
    VueFavorisCarte _vueFavorisCarte;
    JButton supprimerCarteButton;

    public ControleurFavorisCarte(Favoris modeleFavoris, VueFavorisCarte vueFavorisCarte) {
        this._modeleFavoris = modeleFavoris;
        this._vueFavorisCarte = vueFavorisCarte;

        // Crée le bouton "Supprimer une carte"
        this.supprimerCarteButton = new JButton("Supprimer une carte");
        this.supprimerCarteButton.setActionCommand(ACTION_SUPPRIMER);
        this.supprimerCarteButton.addActionListener(this);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(supprimerCarteButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
            // Affiche la fenêtre de suppression lorsqu'on clique sur le bouton
            this.afficherFenetreSuppression();
        }
    }

    public void afficherFenetreSuppression() {
        // Crée la vue de la fenêtre de suppression
        VueFavorisSupprimerCarte favorisSupprimerCarteView = new VueFavorisSupprimerCarte(this._modeleFavoris);
        
        // Crée la fenêtre principale
        JFrame frame = new JFrame("Supprimer une carte");
        
        // Crée le contrôleur pour la fenêtre de suppression
        ControleurFavorisSupprimerCarte favorisSupprimerCarteController = new ControleurFavorisSupprimerCarte(
                this._modeleFavoris, favorisSupprimerCarteView, this._vueFavorisCarte, frame);
        
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        
        // Ajoute la vue de suppression et le contrôleur à la fenêtre principale
        frame.add(favorisSupprimerCarteView);
        frame.add(favorisSupprimerCarteController);
        frame.pack();
        frame.setVisible(true);
    }
}
