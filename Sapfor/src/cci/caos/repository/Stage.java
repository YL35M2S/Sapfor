package cci.caos.repository;
import java.util.*;

public class Stage {

	private String nom;
	private List<Session> session; 
		
	/*Constructeurs*/
	   public Stage() {}
	
	   public Stage(String nom) {
			this.nom = nom;
		}
	
	
	/* Accesseurs et Modificateurs */
		
		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}
	
	    public List<Session> getSession() {
			return session;
		}

		public void setSession(List<Session> session) {
			this.session = session;
		}

		
		/*Ajout d'une session à un stage*/
		public void ajouterSession(Session session){
			this.session.add(session); 
		}
	
	
}
