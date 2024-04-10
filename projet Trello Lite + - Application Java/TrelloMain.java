import javax.swing.*;
import java.awt.*;
import vues.*;
import controleurs.*;

public class TrelloMain extends JFrame {

	public TrelloMain(String s) {
		this.setTitle(s); // Définit le titre de la fenêtre principale
		this.setResizable(false); // Désactive le redimensionnement de la fenêtre

		VueAccueil acceuilView = new VueAccueil(); // Crée une instance de la vue d'accueil
		ControleurAccueil acceuilController = new ControleurAccueil(); // Crée une instance du contrôleur d'accueil

		this.setLayout(new BorderLayout()); // Définit le gestionnaire de disposition de la fenêtre principale comme un
											// BorderLayout
		this.add(acceuilView, BorderLayout.CENTER); // Ajoute la vue d'accueil au centre de la fenêtre principale
		this.add(acceuilController, BorderLayout.SOUTH); // Ajoute le contrôleur d'accueil en bas de la fenêtre
															// principale

		this.pack(); // Ajuste automatiquement la taille de la fenêtre pour s'adapter au contenu
		this.setVisible(true); // Rend la fenêtre principale visible
	}

	public static void main(String[] args) {
		TrelloMain test = new TrelloMain("Page d'accueil"); // Crée une instance de la classe TrelloMain avec le titre
															// "Page d'accueil"
	}
}
