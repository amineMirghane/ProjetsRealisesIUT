/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Membre est une classe qui représente un membre héritant de la classe Utilisateur.
 * Elle contient des informations spécifiques au membre, telles que l'identifiant, le type et les permissions.
 */
package modeles;

import java.io.Serializable;
import java.util.Date;

public class Membre extends Utilisateur implements Serializable {
	// Compteur pour l'identifiant
	static int i = 1;

	// Attributs de classe
	private int _idMembre; // Identifiant du membre
	private String _typeMembre; // Type de membre
	private String _permissionMembre; // Permissions du membre

	// Constructeur de la classe Membre héritant de la classe Utilisateur.
	public Membre(String nomUser, String prenomUser, String dateDeNaissance, String mailUser, String genreUser,
			int telUser, String adresseUser, String password, int idMembre, String typeMembre,
			String permissionMembre) {
		super(nomUser, prenomUser, dateDeNaissance, mailUser, genreUser, telUser, adresseUser, password);
		this._idMembre = i; // Affectation d'un identifiant unique au membre
		this._typeMembre = typeMembre; // Affectation du type de membre
		this._permissionMembre = permissionMembre; // Affectation des permissions du membre
		// Incrémentation du compteur pour les identifiants uniques des membres
		i++;
	}

	// Getters et setters pour les différents attributs du membre

	public int get_idMembre() {
		return _idMembre;
	}

	public String get_typeMembre() {
		return _typeMembre;
	}

	public void set_typeMembre(String _typeMembre) {
		this._typeMembre = _typeMembre;
	}

	public String get_permissionMembre() {
		return _permissionMembre;
	}

	// Méthode pour changer les permissions du membre.
	public void changerPermissionMembre(String permission) {
		this._permissionMembre = permission;
	}
}
