/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Compte est une classe qui représente un compte contenant une liste d'utilisateurs.
 */
package modeles;

import java.io.*;
import java.util.ArrayList;

public class Compte implements Serializable {
	ArrayList<Utilisateur> _utilisateurs; // Liste des utilisateurs du compte

	// Constructeur de la classe Compte.
	public Compte() {
		this._utilisateurs = new ArrayList<Utilisateur>();
	}

	// Méthode pour obtenir la liste des utilisateurs du compte.
	public ArrayList<Utilisateur> get_utilisateurs() {
		return _utilisateurs;
	}

	// Méthode pour ajouter un utilisateur à la liste des utilisateurs du compte.
	public void AjouterUser(Utilisateur utilisateur) {
		this._utilisateurs.add(utilisateur);
	}

	// Méthode pour sérialiser l'objet Compte en utilisant la sérialisation Java.
	public void serialiser(String fichier) {
		try {
			FileOutputStream fileOut = new FileOutputStream(fichier);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this); // Sérialisation de l'objet Compte
			out.close();
			fileOut.close();
			System.out.println("Serialisation réussie");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Échec de la sérialisation");
		}
	}
}
