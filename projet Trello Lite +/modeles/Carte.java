package modeles;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Carte implements Serializable {
	static int i = 1; // Variable statique pour générer des identifiants de carte uniques
	LocalDate _currentDate; // Date actuelle
	private int _idCarte; // Identifiant de la carte
	private String _nomCarte; // Nom de la carte
	private String _descriptionCarte; // Description de la carte
	private LocalDate _dateDeDebutCarte; // Date de début de la carte
	private LocalDate _dateLimite; // Date limite de la carte
	private String _statusCarte; // Statut de la carte (en cours, terminée, etc.)

	ArrayList<Membre> _membres; // Liste des membres associés à la carte

	// Constructeur de la classe Carte.
	public Carte(String nomCarte, String descriptionCarte, String dateDeDebutCarte, String dateLimite,
			String statusCarte) {
		this._idCarte = i; // Affectation d'un identifiant unique à la carte
		this._nomCarte = nomCarte;
		this._descriptionCarte = descriptionCarte;
		this._dateDeDebutCarte = LocalDate.parse(dateDeDebutCarte); // Conversion de la date de début en LocalDate
		this._dateLimite = LocalDate.parse(dateLimite); // Conversion de la date limite en LocalDate
		this._statusCarte = statusCarte;
		this._currentDate = LocalDate.now(); // Affectation de la date actuelle
		this._membres = new ArrayList<Membre>(); // Initialisation de la liste des membres
		i++; // Incrémentation de la variable statique pour les identifiants uniques
	}

	// Getters et setters pour les différents attributs de la carte

	public LocalDate get_currentDate() {
		return _currentDate;
	}

	public void set_currentDate(LocalDate _currentDate) {
		this._currentDate = _currentDate;
	}

	public int get_idCarte() {
		return _idCarte;
	}

	public String get_nomCarte() {
		return _nomCarte;
	}

	public String get_descriptionCarte() {
		return _descriptionCarte;
	}

	public LocalDate get_dateDeDebutCarte() {
		return _dateDeDebutCarte;
	}

	public LocalDate get_dateLimite() {
		return _dateLimite;
	}

	public String get_statusCarte() {
		return _statusCarte;
	}

	public void ChangerStatusCarte(String statusCarte) {
		this._statusCarte = statusCarte;
	}

	public ArrayList<Membre> get_membres() {
		return _membres;
	}

	// Méthode pour ajouter un membre à la carte.
	public void AjouterMembre(Membre membre) {
		_membres.add(membre);
	}

	// Méthode pour supprimer un membre de la carte en utilisant son identifiant.
	public void SuppMembre(int idMembre) {
		for (int i = 0; i < this._membres.size(); i++) {
			if (this._membres.get(i).get_idMembre() == idMembre) {
				this._membres.remove(i);
				break; // Sortir de la boucle une fois que le membre est supprimé
			}
		}
	}
}
