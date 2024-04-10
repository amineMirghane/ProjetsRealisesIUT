/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Utilisateur est une classe qui représente un utilisateur du système.
 * Elle contient des informations telles que l'identifiant, le nom, le prénom, la date de naissance,
 * l'adresse email, le genre, le numéro de téléphone et l'adresse de l'utilisateur.
 * Elle gère également les favoris de l'utilisateur et les projets auxquels il participe.
 */
package modeles;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Utilisateur implements Serializable {
	static int i = 1; // Compteur pour l'identifiant des utilisateurs

	private int _idUser; // Identifiant de l'utilisateur
	private String _nomUser; // Nom de l'utilisateur
	private String _prenomUser; // Prénom de l'utilisateur
	private LocalDate _dateDeNaissanceUser; // Date de naissance de l'utilisateur
	private String _mailUser; // Adresse email de l'utilisateur
	private String _genreUser; // Genre de l'utilisateur
	private int _telUser; // Numéro de téléphone de l'utilisateur
	private String _adresseUser; // Adresse de l'utilisateur
	private String _password; // Mot de passe de l'utilisateur
	private Favoris _favoris; // Favoris de l'utilisateur
	private ArrayList<Projet> _projets; // Liste des projets auxquels l'utilisateur participe

	/**
	 * Constructeur de la classe Utilisateur.
	 *
	 * @param nomUser         Le nom de l'utilisateur
	 * @param prenomUser      Le prénom de l'utilisateur
	 * @param dateDeNaissance La date de naissance de l'utilisateur
	 * @param mailUser        L'adresse email de l'utilisateur
	 * @param genreUser       Le genre de l'utilisateur
	 * @param telUser         Le numéro de téléphone de l'utilisateur
	 * @param adresseUser     L'adresse de l'utilisateur
	 * @param password        Le mot de passe de l'utilisateur
	 */
	public Utilisateur(String nomUser, String prenomUser, String dateDeNaissance, String mailUser, String genreUser,
			int telUser, String adresseUser, String password) {
		this._idUser = i; // Affectation d'un identifiant unique à l'utilisateur
		this._nomUser = nomUser; // Affectation du nom de l'utilisateur
		this._prenomUser = prenomUser; // Affectation du prénom de l'utilisateur
		this._dateDeNaissanceUser = LocalDate.parse(dateDeNaissance); // Affectation de la date de naissance de
																		// l'utilisateur
		this._mailUser = mailUser; // Affectation de l'adresse email de l'utilisateur
		this._genreUser = genreUser; // Affectation du genre de l'utilisateur
		this._telUser = telUser; // Affectation du numéro de téléphone de l'utilisateur
		this._adresseUser = adresseUser; // Affectation de l'adresse de l'utilisateur
		this._password = password; // Affectation du mot de passe de l'utilisateur
		this._favoris = new Favoris(); // Initialisation des favoris de l'utilisateur
		this._projets = new ArrayList<Projet>(); // Initialisation de la liste des projets
		i++; // Incrémentation du compteur pour les identifiants uniques des utilisateurs
	}

	// Getters et setters pour les différents attributs de l'utilisateur

	public int get_idUser() {
		return _idUser;
	}

	public String get_nomUser() {
		return _nomUser;
	}

	public String get_prenomUser() {
		return _prenomUser;
	}

	public LocalDate get_dateDeNaissanceUser() {
		return _dateDeNaissanceUser;
	}

	public String get_mailUser() {
		return _mailUser;
	}

	public String get_genreUser() {
		return _genreUser;
	}

	public int get_telUser() {
		return _telUser;
	}

	public String get_adresseUser() {
		return _adresseUser;
	}

	public String get_password() {
		return _password;
	}

	public Favoris get_favoris() {
		return _favoris;
	}

	public int get_nbProjets() {
		return _projets.size();
	}

	public ArrayList<Projet> get_projets() {
		return _projets;
	}

	public void AjouterProjet(Projet projet) {
		this._projets.add(projet);
	}

	public void SupprimerProjet(int idProjet) {
		for (int i = 0; i < this._projets.size(); i++) {
			if (this._projets.get(i).get_idProjet() == idProjet) {
				this._projets.remove(i);
				break; // Ajout de break pour sortir de la boucle après avoir supprimé le projet
			}
		}
	}
}