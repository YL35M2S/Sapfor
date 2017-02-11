package cci.caos.repository;
import java.util.*;

public class Uv {
	
	 public String nom;
	 public String duree;
	 public int nombrePlaceMin;
	 public int nombrePlaceMax;
	 public List<Uv> listePrerequis;
	 public String lieu;
 
	/*Constructeurs*/
	
	 public Uv(){}
	 
	 public Uv(String nom, String duree, int nombrePlaceMin, int nombrePlaceMax, String lieu) {
			super();
			this.nom = nom;
			this.duree = duree;
			this.nombrePlaceMin = nombrePlaceMin;
			this.nombrePlaceMax = nombrePlaceMax;
			this.lieu = lieu; 
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

		public String getLieu() {
			return lieu;
		}

		public void setLieu(String lieu) {
			this.lieu = lieu;
		}
		
		public List<Uv> getListePrerequis() {
			return listePrerequis;
		}

		public void setListePrerequis(List<Uv> listePrerequis) {
			this.listePrerequis = listePrerequis;
		}

		/*ajout d'une Uv prérequise pour avoir l'UV*/ 
		public void ajouterUv(Uv prerequis){
			this.listePrerequis.add(prerequis); 
		}
		
		/*retrait d'une Uv prérequise pour avoir l'Uv*/
		public void retirerUv(Uv u){
			this.listePrerequis.remove(u); 
		}
}
