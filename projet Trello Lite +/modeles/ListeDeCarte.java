/**
 * @author MIRGHANE Amine
 * ListeDeCarte est une classe qui représente une liste de cartes.
 */
package modeles;

import java.io.Serializable;
import java.util.*;

public class ListeDeCarte implements Serializable {
	static int i = 1; // Variable statique pour générer des identifiants de liste de cartes uniques
	ArrayList<Carte> _cartes; // Liste des cartes de la liste
	private int _idListeDeCarte; // Identifiant de la liste de cartes
	private String _nomListeDeCarte; // Nom de la liste de cartes
	private int _nbCartes; // Nombre de cartes dans la liste

	// Constructeur de la classe ListeDeCarte.
	public ListeDeCarte(String nomListe) {
		this._nomListeDeCarte = nomListe;
		this._idListeDeCarte = i; // Affectation d'un identifiant unique à la liste de cartes
		this._cartes = new ArrayList<Carte>(); // Initialisation de la liste des cartes
		this._nbCartes = this._cartes.size(); // Initialisation du nombre de cartes à 0
		i++; // Incrémentation de la variable statique pour les identifiants uniques
	}

	// Getters et setters pour les différents attributs de la liste de cartes

	public int get_idListeDeCarte() {
		return _idListeDeCarte;
	}

	public ArrayList<Carte> get_cartes() {
		return _cartes;
	}

	// Méthode pour ajouter une carte à la liste de cartes.
	public void AjouterCarte(Carte carte) {
		this._cartes.add(carte);
	}

	// Méthode pour supprimer une carte de la liste de cartes en utilisant son
	// identifiant.
	public void SupprimerCarte(int idCarte) {
		for (int i = 0; i < this._cartes.size(); i++) {
			if (this._cartes.get(i).get_idCarte() == idCarte) {
				this._cartes.remove(i);
				break; // Sortir de la boucle une fois que la carte est supprimée
			}
		}
	}

	public String get_nomListeDeCarte() {
		return _nomListeDeCarte;
	}

	public int get_nbCartes() {
		return _cartes.size();
	}
}
