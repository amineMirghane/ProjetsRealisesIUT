/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Ce contrôleur gère les interactions avec la vue de connexion.
 * Il est responsable de la vérification des informations de connexion, de l'affichage de la fenêtre utilisateur
 * et de la fermeture de la fenêtre parente.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurConnexion extends JPanel implements ActionListener {
	// Constante pour l'action de connexion
	public static final String ACTION_CO = "SECONNECTER";

	// Références aux modèles, vues et composants
	Compte _modeleCompte;
	VueConnexion _vueConnexion;
	JButton connectButton;
	JFrame _parentFrame;

	public ControleurConnexion(Compte modeleCompte, VueConnexion vueConnexion, JFrame parentFrame) {
		this._modeleCompte = modeleCompte;
		this._vueConnexion = vueConnexion;
		this._parentFrame = parentFrame;

		// Création du bouton de connexion et assignation de l'action
		this.connectButton = new JButton("Se connecter");
		this.connectButton.setActionCommand(ACTION_CO);
		this.connectButton.addActionListener(this);

		// Configuration du layout
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		// Ajout du bouton au panneau
		this.add(connectButton);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_CO)) {
			// Vérification des informations de connexion
			for (Utilisateur UnUtilisateur : this._modeleCompte.get_utilisateurs()) {
				if (UnUtilisateur.get_mailUser().equals(this._vueConnexion.getMailFieldText())) {
					if (UnUtilisateur.get_password().equals(this._vueConnexion.getPasswordFieldText())) {
						// Affichage de la fenêtre utilisateur
						this.afficherFenetreUtilisateur(UnUtilisateur);

						// Fermeture de la fenêtre parente
						this._parentFrame.dispose();
						break;
					}
				}
			}
		}
	}

	public void afficherFenetreUtilisateur(Utilisateur UnUtilisateur) {
		// Création de la vue utilisateur et du contrôleur utilisateur
		VueUtilisateur utilisateurView = new VueUtilisateur(UnUtilisateur);
		ControleurUtilisateur utilisateurController = new ControleurUtilisateur(UnUtilisateur, utilisateurView,
				this._modeleCompte);

		// Création de la fenêtre utilisateur
		JFrame frame = new JFrame("Bienvenue " + UnUtilisateur.get_nomUser() + " " + UnUtilisateur.get_prenomUser());
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(utilisateurView, BorderLayout.CENTER);
		frame.add(utilisateurController, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}
}
