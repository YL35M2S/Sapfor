package cci.caos.repository;
import java.util.*;


public class Aptitude {
	
	public String nom;
	public List<Uv> listeUV;	
	
	 /*Constructeurs*/
	
	public Aptitude(){}
	

	/* Accesseurs et Modificateurs */
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Uv> getListeUV() {
		return listeUV;
	}

	public void setListeUV(List<Uv> listeUV) {
		this.listeUV = listeUV;
	}
	
	/*Ajoute une Uv prérequise à l'aptitude*/
	public void ajouterUv(Uv u){
		this.listeUV.add(u); 
	}
}
