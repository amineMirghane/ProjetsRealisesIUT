/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Le contrôleur pour la fonctionnalité de suppression d'une carte dans les favoris.
 * Gère les interactions entre le modèle Favoris, la vue VueFavorisSupprimerCarte et la vue VueFavorisCarte.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurFavorisSupprimerCarte extends JPanel implements ActionListener {
	public static final String ACTION_SUPPRIMER = "SUPPRIMER";
	Favoris _modeleFavoris;
	VueFavorisSupprimerCarte _vueFavorisSupprimerCarte;
	VueFavorisCarte _vueFavorisCarte;
	JButton supprimerCarteButton;
	JFrame _parentFrame;

	public ControleurFavorisSupprimerCarte(Favoris modeleFavoris, VueFavorisSupprimerCarte vueFavorisSupprimerCarte,
			VueFavorisCarte vueFavorisCarte, JFrame parentFrame) {
		this._modeleFavoris = modeleFavoris;
		this._vueFavorisSupprimerCarte = vueFavorisSupprimerCarte;
		this._vueFavorisCarte = vueFavorisCarte;
		this._parentFrame = parentFrame;

		// Crée le bouton "Supprimer"
		this.supprimerCarteButton = new JButton("Supprimer");
		this.supprimerCarteButton.setActionCommand(ACTION_SUPPRIMER);
		this.supprimerCarteButton.addActionListener(this);
		this.add(supprimerCarteButton);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
			// Parcourt les cartes du modèle Favoris pour trouver la carte correspondante à
			// l'ID
			for (Carte carte : this._modeleFavoris.get_cartes()) {
				if (carte.get_idCarte() == this._vueFavorisSupprimerCarte.getIdFieldText()) {
					// Supprime la carte du modèle Favoris
					this._modeleFavoris.supprimerCarte(this._vueFavorisSupprimerCarte.getIdFieldText());

					// Met à jour la vue VueFavorisCarte
					this._vueFavorisCarte.majVueFavorisCartes();

					// Ferme la fenêtre parente
					this._parentFrame.dispose();

					break;
				}
			}
		}
	}
}
