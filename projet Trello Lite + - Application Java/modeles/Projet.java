/**
 * Projet est une classe qui représente un projet.
 * Elle contient des informations telles que l'identifiant, le nom, la description et le statut du projet.
 * Elle gère également une liste de cartes associées au projet.
 */
package modeles;

import java.io.Serializable;
import java.util.*;

public class Projet implements Serializable {

	private int _idProjet; // Identifiant du projet
	private String _nomProjet; // Nom du projet
	private String _descriptionProjet; // Description du projet
	private String _statusProjet; // Statut du projet
	ArrayList<ListeDeCarte> _listeDeCarte; // Liste de cartes associées au projet
	static int j = 1; // Compteur pour l'identifiant des projets

	// Constructeur de la classe Projet.
	public Projet(String nomProjet, String descriptionProjet, String statusProjet) {
		this._idProjet = j; // Affectation d'un identifiant unique au projet
		this._nomProjet = nomProjet; // Affectation du nom du projet
		this._descriptionProjet = descriptionProjet; // Affectation de la description du projet
		this._statusProjet = statusProjet; // Affectation du statut du projet
		this._listeDeCarte = new ArrayList<ListeDeCarte>(); // Initialisation de la liste de cartes
		j++; // Incrémentation du compteur pour les identifiants uniques des projets
	}

	// Getters et setters pour les différents attributs du projet

	public int get_idProjet() {
		return _idProjet;
	}

	public String get_nomProjet() {
		return _nomProjet;
	}

	public String get_descriptionProjet() {
		return _descriptionProjet;
	}

	public String get_statusProjet() {
		return _statusProjet;
	}

	// Méthode pour changer le statut du projet.
	public void changerStatus(String status) {
		this._statusProjet = status;
	}

	public ArrayList<ListeDeCarte> get_listeDeCarte() {
		return _listeDeCarte;
	}

	// Méthode pour ajouter une liste de cartes au projet.
	public void AjouterListeDeCarte(ListeDeCarte listeDeCarte) {
		_listeDeCarte.add(listeDeCarte);
	}

	// Méthode pour supprimer une liste de cartes du projet.
	public void SuppListeDeCarte(int idListe) {
		for (int i = 0; i < this._listeDeCarte.size(); i++) {
			if (this._listeDeCarte.get(i).get_idListeDeCarte() == idListe) {
				this._listeDeCarte.remove(i);
			}
		}
	}
}
