package cci.caos.repository;
import java.util.*;

public class Uv {
	
	 public String nom;
	 public String duree;
	 public int nombrePlaceMin;
	 public int nombrePlaceMax;
	 public List<Uv> listePrerequis;
	 //public String lieux;
	 
	/*Constructeurs*/
	
	 public Uv(){}
	 
	 public Uv(String nom, String duree, int nombrePlaceMin, int nombrePlaceMax) {
			super();
			this.nom = nom;
			this.duree = duree;
			this.nombrePlaceMin = nombrePlaceMin;
			this.nombrePlaceMax = nombrePlaceMax;
		}
	

	/* Accesseurs et Modificateurs */

	 public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getDuree() {
			return duree;
		}

		public void setDuree(String duree) {
			this.duree = duree;
		}

		public int getNombrePlaceMin() {
			return nombrePlaceMin;
		}

		public void setNombrePlaceMin(int nombrePlaceMin) {
			this.nombrePlaceMin = nombrePlaceMin;
		}

		public int getNombrePlaceMax() {
			return nombrePlaceMax;
		}

		public void setNombrePlaceMax(int nombrePlaceMax) {
			this.nombrePlaceMax = nombrePlaceMax;
		}

		public List<Uv> getListePrerequis() {
			return listePrerequis;
		}

		public void setListePrerequis(List<Uv> listePrerequis) {
			this.listePrerequis = listePrerequis;
		}

		
}
