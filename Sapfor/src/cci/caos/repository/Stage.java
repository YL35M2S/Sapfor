package cci.caos.repository;

public class Stage {

	private String nom;
	
	
	public java.util.Collection<Session> appartient;
	
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
	
	
	
	  /** @pdGenerated default getter */
	   public java.util.Collection<Session> getAppartient() {
	      if (appartient == null)
	         appartient = new java.util.HashSet<Session>();
	      return appartient;
	   }
	  

	/** @pdGenerated default iterator getter */
	   public java.util.Iterator getIteratorAppartient() {
	      if (appartient == null)
	         appartient = new java.util.HashSet<Session>();
	      return appartient.iterator();
	   }
	   
	   /** @pdGenerated default setter
	     * @param newAppartient */
	   public void setAppartient(java.util.Collection<Session> newAppartient) {
	      removeAllAppartient();
	      for (java.util.Iterator iter = newAppartient.iterator(); iter.hasNext();)
	         addAppartient((Session)iter.next());
	   }
	   
	   /** @pdGenerated default add
	     * @param newSession */
	   public void addAppartient(Session newSession) {
	      if (newSession == null)
	         return;
	      if (this.appartient == null)
	         this.appartient = new java.util.HashSet<Session>();
	      if (!this.appartient.contains(newSession))
	         this.appartient.add(newSession);
	   }
	   
	   /** @pdGenerated default remove
	     * @param oldSession */
	   public void removeAppartient(Session oldSession) {
	      if (oldSession == null)
	         return;
	      if (this.appartient != null)
	         if (this.appartient.contains(oldSession))
	            this.appartient.remove(oldSession);
	   }
	   
	   /** @pdGenerated default removeAll */
	   public void removeAllAppartient() {
	      if (appartient != null)
	         appartient.clear();
	   }
	
	
}
