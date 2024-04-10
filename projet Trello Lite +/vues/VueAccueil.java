/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueAcceuil représente une vue pour l'écran d'accueil.
 * Elle affiche une image de logo redimensionnée.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueAccueil extends JPanel {
	public static final String CHEMIN = "./logo.png"; // Chemin vers l'image du logo
	ImageIcon imageIcon; // ImageIcon pour afficher l'image
	JLabel imageLabel; // JLabel pour contenir l'image

	public VueAccueil() {
		this.imageIcon = new ImageIcon(CHEMIN); // Crée un ImageIcon avec l'image du logo
		this.resizeLogo(); // Redimensionne le logo
		this.imageLabel = new JLabel(); // Crée un JLabel pour l'image
		this.imageLabel.setIcon(imageIcon); // Définit l'icône de l'image pour le JLabel
		this.setLayout(new FlowLayout(FlowLayout.CENTER)); // Définit le gestionnaire de disposition du JPanel
		this.add(imageLabel); // Ajoute le JLabel à ce JPanel
	}

	// Redimensionne l'image du logo.
	public void resizeLogo() {
		Image image = this.imageIcon.getImage(); // Obtient l'image du logo de l'ImageIcon
		int nouvelleLargeur = 250; // Nouvelle largeur souhaitée pour le logo
		int nouvelleHauteur = 75; // Nouvelle hauteur souhaitée pour le logo
		Image nouvelleImage = image.getScaledInstance(nouvelleLargeur, nouvelleHauteur, Image.SCALE_SMOOTH); // Redimensionne
																												// l'image
		this.imageIcon = new ImageIcon(nouvelleImage); // Crée un nouveau ImageIcon avec l'image redimensionnée
	}
}
