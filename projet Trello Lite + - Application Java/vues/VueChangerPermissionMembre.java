/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueChangerPermissionMembre représente une vue permettant de changer la permission d'un membre.
 * Elle contient un JLabel pour afficher le texte "Saisir la permission : " et un JTextField pour permettre à l'utilisateur de saisir la nouvelle permission.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueChangerPermissionMembre extends JPanel {
	Membre _modeleMembre; // Instance de la classe Membre pour représenter les données du membre
	JLabel permissionLabel; // JLabel pour afficher le texte "Saisir la permission : "
	JTextField permissionField; // JTextField pour permettre à l'utilisateur de saisir la nouvelle permission

	public VueChangerPermissionMembre(Membre modeleMembre) {
		this._modeleMembre = modeleMembre; // Initialise l'instance de Membre avec le modèle de données du membre

		this.permissionLabel = new JLabel("Saisir la permission : "); // Crée un JLabel avec le texte "Saisir la
																		// permission : "
		this.permissionField = new JTextField(5); // Crée un JTextField avec une largeur de 5 caractères

		this.add(permissionLabel); // Ajoute le JLabel au JPanel
		this.add(permissionField); // Ajoute le JTextField au JPanel
	}

	// Retourne le texte saisi dans le champ de saisie de la permission.
	public String getPermissionFieldText() {
		return permissionField.getText(); // Retourne le texte du champ de saisie de la permission
	}

	// Efface le contenu du champ de saisie de la permission.
	public void clearFields() {
		permissionField.setText(""); // Efface le texte du champ de saisie de la permission
	}
}
