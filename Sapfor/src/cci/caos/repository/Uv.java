package cci.caos.repository;
import java.util.*;

public class Uv {
	
	 public String nom;
	 public String duree;
	 public int nombrePlaceMin;
	 public int nombrePlaceMax;
	 public List listePrerequis;
	 //public String lieux;
	 public java.util.Collection<Uv> exige;
	
	 
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

	 
	 
	 /** @pdGenerated default getter 
	   * 
	   *   @return la liste d'UV exig�es
	   */
	   public java.util.Collection<Uv> getExige() {
	      if (exige == null)
	         exige = new java.util.HashSet<Uv>();
	      return exige;
	   }
	   
	   
	/** @pdGenerated default iterator getter 
	   * @return Un it�rateur sur la liste d'UV exig�es
	   * Si elle n'existe pas, cr�ation d'une liste vide
	   */
	   public java.util.Iterator getIteratorExige() {
	      if (exige == null)
	         exige = new java.util.HashSet<Uv>();
	      return exige.iterator();
	   }
	   
	   /** @pdGenerated default setter
	   	* Remplace la liste d'UV exig�es par une autre liste d'UV exig�es plac�e en param�tre
	    * @param newExige */
	   public void setExige(java.util.Collection<Uv> newExige) {
	      removeAllExige();
	      for (java.util.Iterator iter = newExige.iterator(); iter.hasNext();)
	         addExige((Uv)iter.next());
	   }
	   
	   /** @pdGenerated default add
	    * @param newUv 
	    * 
	    * Ajoute une UV dans la liste des UV exig�e
	    **/
	   public void addExige(Uv newUv) {
	      if (newUv == null)
	         return;
	      if (this.exige == null)
	         this.exige = new java.util.HashSet<Uv>();
	      if (!this.exige.contains(newUv))
	         this.exige.add(newUv);
	   }
	   
	   /** @pdGenerated default remove
	    * @param oldUv
	    * Enl�ve une UV � la liste des UV exig�es
	    */
	   public void removeExige(Uv oldUv) {
	      if (oldUv == null)
	         return;
	      if (this.exige != null)
	         if (this.exige.contains(oldUv))
	            this.exige.remove(oldUv);
	   }
	   
	   /** @pdGenerated default removeAll
	    * Enl�ve toutes les UV de la liste des UV exig�es
	  	* 
	   	*/
	   public void removeAllExige() {
	      if (exige != null)
	         exige.clear();
	   }
	
}
