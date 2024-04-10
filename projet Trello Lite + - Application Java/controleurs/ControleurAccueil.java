/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Cette classe représente un contrôleur pour une application d'accueil.
 * Elle gère les interactions de l'utilisateur avec les boutons d'inscription et de connexion.
 * Lorsque ces boutons sont cliqués, elle affiche les fenêtres correspondantes.
 * Elle utilise également la sérialisation pour stocker et récupérer les comptes des utilisateurs.
 */

package controleurs;

//Import des classes nécessaires
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;
import java.io.*;

public class ControleurAccueil extends JPanel implements ActionListener {
	// Constantes pour les actions des boutons
	public static final String ACTION_INSCRIPTION = "SINSCRIRE";
	public static final String ACTION_CONNEXION = "SECONNECTER";

	// Nom du fichier de sérialisation
	public static final String FICHIER = "compte.dat";

	// Modèle de compte
	Compte _modeleCompte;

	// Boutons d'inscription et de connexion
	JButton inscriptionButton;
	JButton connexionButton;

	// Constructeur
	public ControleurAccueil() {
		// Désérialisation du compte à partir du fichier
		this.deserialiserCompte(FICHIER);

		// Initialisation des boutons d'inscription et de connexion
		this.inscriptionButton = new JButton("S'inscrire");
		this.connexionButton = new JButton("Se connecter");

		// Définition des actions associées aux boutons
		this.inscriptionButton.setActionCommand(ACTION_INSCRIPTION);
		this.connexionButton.setActionCommand(ACTION_CONNEXION);

		// Ajout des écouteurs d'événements aux boutons
		this.inscriptionButton.addActionListener(this);
		this.connexionButton.addActionListener(this);

		// Configuration du layout du panneau
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		// Ajout des boutons au panneau
		this.add(connexionButton);
		this.add(inscriptionButton);
	}

	// Méthode appelée lorsqu'un événement d'action se produit
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_INSCRIPTION)) {
			// Affichage de la fenêtre d'inscription
			this.afficherFenetreInscription();
		} else if (e.getActionCommand().equals(ACTION_CONNEXION)) {
			// Affichage de la fenêtre de connexion
			this.afficherFenetreConnexion();
		}
	}

	// Affichage de la fenêtre d'inscription
	public void afficherFenetreInscription() {
		// Création de la vue et du contrôleur de l'inscription
		VueInscription inscriptionView = new VueInscription(this._modeleCompte);
		ControleurInscription inscriptionController = new ControleurInscription(this._modeleCompte, inscriptionView);

		// Création de la fenêtre d'inscription
		JFrame frame = new JFrame("Inscription");
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		// Ajout de la vue et du contrôleur à la fenêtre
		frame.add(inscriptionView, BorderLayout.CENTER);
		frame.add(inscriptionController, BorderLayout.SOUTH);

		// Configuration et affichage de la fenêtre
		frame.pack();
		frame.setVisible(true);
	}

	// Affichage de la fenêtre de connexion
	public void afficherFenetreConnexion() {
		// Création de la vue de connexion
		VueConnexion connexionView = new VueConnexion(this._modeleCompte);

		// Création de la fenêtre de connexion
		JFrame frame = new JFrame("Connexion");
		ControleurConnexion connexionController = new ControleurConnexion(this._modeleCompte, connexionView, frame);

		// Configuration de la fenêtre de connexion
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		// Ajout de la vue et du contrôleur à la fenêtre
		frame.add(connexionView, BorderLayout.CENTER);
		frame.add(connexionController, BorderLayout.SOUTH);

		// Configuration et affichage de la fenêtre
		frame.pack();
		frame.setVisible(true);
	}

	// Désérialisation du compte à partir du fichier
	public void deserialiserCompte(String fichier) {
		File file = new File(fichier);

		if (file.exists()) {
			try {
				FileInputStream fileIn = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				this._modeleCompte = (Compte) in.readObject();
				in.close();
				fileIn.close();
				System.out.println("Désérialisation réussie");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Échec de la désérialisation");
			}
		} else {
			this._modeleCompte = new Compte();
			this._modeleCompte.serialiser(fichier);
			System.out.println("Le fichier n'existe pas. Le modèle Compte a été sérialisé.");
		}
	}
}