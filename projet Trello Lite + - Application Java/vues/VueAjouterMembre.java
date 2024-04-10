/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * La classe VueAjouterMembre représente une vue pour ajouter un membre à une carte.
 * Elle contient des champs de saisie pour l'ID de l'utilisateur, la permission de l'utilisateur et le type de l'utilisateur.
 */
package vues;

import javax.swing.*;
import java.awt.*;
import modeles.*;

public class VueAjouterMembre extends JPanel {
	Carte _modeleCarte; // Instance de la classe Carte pour représenter les données de la carte
	JLabel idLabel; // JLabel pour le texte "ID de l'utilisateur :"
	JLabel permissionLabel; // JLabel pour le texte "Permission de l'utilisateur :"
	JLabel typeLabel; // JLabel pour le texte "Type de l'utilisateur :"
	JTextField idField; // JTextField pour saisir l'ID de l'utilisateur
	JTextField permissionField; // JTextField pour saisir la permission de l'utilisateur
	JTextField typeField; // JTextField pour saisir le type de l'utilisateur

	public VueAjouterMembre(Carte modeleCarte) {
		this._modeleCarte = modeleCarte; // Initialise l'instance de Carte avec le modèle de données de la carte

		this.idLabel = new JLabel("ID de l'utilisateur :"); // Crée un JLabel avec le texte "ID de l'utilisateur :"
		this.permissionLabel = new JLabel("Permission de l'utilisateur :"); // Crée un JLabel avec le texte "Permission
																			// de l'utilisateur :"
		this.typeLabel = new JLabel("Type de l'utilisateur :"); // Crée un JLabel avec le texte "Type de l'utilisateur
																// :"
		this.idField = new JTextField(10); // Crée un JTextField avec une largeur de 10 caractères
		this.permissionField = new JTextField(10); // Crée un JTextField avec une largeur de 10 caractères
		this.typeField = new JTextField(10); // Crée un JTextField avec une largeur de 10 caractères
		this.setLayout(new GridLayout(3, 2)); // Définit le layout du JPanel comme un GridLayout avec 3 lignes et 2
												// colonnes
		this.add(idLabel); // Ajoute le JLabel de l'ID de l'utilisateur et le JTextField correspondant au
							// JPanel
		this.add(idField);
		this.add(permissionLabel); // Ajoute le JLabel de la permission de l'utilisateur et le JTextField
									// correspondant au JPanel
		this.add(permissionField);
		this.add(typeLabel); // Ajoute le JLabel du type de l'utilisateur et le JTextField correspondant au
								// JPanel
		this.add(typeField);
	}

	// Récupère l'ID de l'utilisateur saisi dans le champ.
	public int getIdFieldText() {
		return Integer.parseInt(idField.getText()); // Récupère le texte du champ de l'ID de l'utilisateur et le
													// convertit en entier
	}

	// Récupère la permission de l'utilisateur saisie dans le champ.
	public String getPermissionFieldText() {
		return permissionField.getText(); // Récupère le texte du champ de la permission de l'utilisateur
	}

	// Récupère le type de l'utilisateur saisi dans le champ.
	public String getTypeFieldText() {
		return typeField.getText(); // Récupère le texte du champ du type de l'utilisateur
	}

	// Efface le contenu de tous les champs de saisie.
	public void clearFields() {
		idField.setText(""); // Efface le texte du champ de l'ID de l'utilisateur
		permissionField.setText(""); // Efface le texte du champ de la permission de l'utilisateur
		typeField.setText(""); // Efface le texte du champ du type de l'utilisateur
	}
}
