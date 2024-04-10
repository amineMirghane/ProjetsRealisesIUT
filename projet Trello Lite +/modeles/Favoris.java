/**
 * @author MIRGHANE Amine TP2G
 */
package modeles;

import java.io.Serializable;
import java.util.*;

public class Favoris implements Serializable{
	// -----------------------------------------
	// ATTRIBUTS
	// -----------------------------------------
	public static int i = 1;
	private int _idFavoris; // Un favori a un identifiant
	ArrayList<Carte> _cartes; // Tableau contenant les cartes favorites de l'utilisateur
	ArrayList<Projet> _projet; // Tableau contenant les projets favoris de l'utilisateur
	ArrayList<ListeDeCarte> _listeDeCarte; // Tableau contenant les listes de cartes favoris de l'utilisateur

	// -----------------------------------------
	// CONSTRUCTEUR
	// -----------------------------------------

	public Favoris() {
		_idFavoris = i;
		_cartes = new ArrayList<Carte>();
		_projet = new ArrayList<Projet>();
		_listeDeCarte = new ArrayList<ListeDeCarte>();
		i++;
	}

	// -----------------------------------------
	// METHODES
	// -----------------------------------------
	public int get_idFavoris() {
		return _idFavoris;
	}

	public void AjouterFavoris(Carte carte) {
		_cartes.add(carte);
	}

	public void supprimerCarte(int idCarte) {
		for (int i = 0; i < this._cartes.size(); i++) {
			if (this._cartes.get(i).get_idCarte() == idCarte) {
				this._cartes.remove(i);
			}
		}
	}

	public void AjouterFavoris(ListeDeCarte listeDeCarte) {
		_listeDeCarte.add(listeDeCarte);
	}

	public void supprimerListeDeCarte(int idListeDeCarte) {
		for (int i = 0; i < this._listeDeCarte.size(); i++) {
			if (this._listeDeCarte.get(i).get_idListeDeCarte() == idListeDeCarte) {
				this._listeDeCarte.remove(i);
			}
		}
	}

	public void AjouterFavoris(Projet projet) {
		_projet.add(projet);
	}

	public void supprimerProjet(int idProjet) {
		for (int i = 0; i < this._projet.size(); i++) {
			if (this._projet.get(i).get_idProjet() == idProjet) {
				this._projet.remove(i);
			}
		}
	}

	public ArrayList<Carte> get_cartes() {
		return _cartes;
	}

	public ArrayList<ListeDeCarte> get_listeDeCarte() {
		return _listeDeCarte;
	}

	public ArrayList<Projet> get_projet() {
		return _projet;
	}
}
