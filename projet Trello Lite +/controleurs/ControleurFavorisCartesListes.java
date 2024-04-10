/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Le contrôleur pour la fonctionnalité de suppression de liste de cartes dans les favoris.
 * Gère les interactions entre le modèle Favoris, la vue VueFavorisCartesListes et la vue VueFavorisSupprimerListe.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurFavorisCartesListes extends JPanel implements ActionListener {
	public static final String ACTION_SUPPRIMER = "SUPPRIMER";
	Favoris _modeleFavoris;
	VueFavorisCartesListes _vueFavorisCartesListes;
	JButton supprimerListeButton;

	public ControleurFavorisCartesListes(Favoris modeleFavoris, VueFavorisCartesListes vueFavorisCartesListes) {
		this._modeleFavoris = modeleFavoris;
		this._vueFavorisCartesListes = vueFavorisCartesListes;

		// Crée le bouton "Supprimer une liste"
		this.supprimerListeButton = new JButton("Supprimer une liste");
		this.supprimerListeButton.setActionCommand(ACTION_SUPPRIMER);
		this.supprimerListeButton.addActionListener(this);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(supprimerListeButton);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_SUPPRIMER)) {
			// Affiche la fenêtre de suppression lorsqu'on clique sur le bouton
			this.afficherFenetreSuppression();
		}
	}

	public void afficherFenetreSuppression() {
		// Crée la vue de la fenêtre de suppression
		VueFavorisSupprimerListe favorisSupprimerListeView = new VueFavorisSupprimerListe(this._modeleFavoris);

		// Crée la fenêtre principale
		JFrame frame = new JFrame("Supprimer une liste de carte");

		// Crée le contrôleur pour la fenêtre de suppression
		ControleurFavorisSupprimerListe favorisSupprimerListeController = new ControleurFavorisSupprimerListe(
				this._modeleFavoris, favorisSupprimerListeView, this._vueFavorisCartesListes, frame);

		frame.setResizable(false);
		frame.setLayout(new FlowLayout());

		// Ajoute la vue de suppression et le contrôleur à la fenêtre principale
		frame.add(favorisSupprimerListeView);
		frame.add(favorisSupprimerListeController);
		frame.pack();
		frame.setVisible(true);
	}
}
