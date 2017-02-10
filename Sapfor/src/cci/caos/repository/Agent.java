package cci.caos.repository;
import java.util.List;


public class Agent {

	   public String nom;
	   public String mdp;
	   public String matricule;
	   public List listeUV;
	   public List listeAptitude;
	   public Boolean gestionnaire = false;
	   
	   public java.util.Collection<Session> contient;
	   /** @pdRoleInfo migr=no name=Session assc=estFormateur mult=0..1 */
	   public Session estDirigee;
	   /** @pdRoleInfo migr=no name=Session assc=participe mult=0..1 */
	   public Session accueil;
	   /** @pdRoleInfo migr=no name=Session assc=estGestionnaire coll=java.util.Collection impl=java.util.HashSet mult=0..* */
	   public java.util.Collection<Session> estGeree;
	   /** @pdRoleInfo migr=no name=Uv assc=estDetenteur coll=java.util.Collection impl=java.util.HashSet mult=0..* */
	   public java.util.Collection<Uv> estDetenue;
	   /** @pdRoleInfo migr=no name=Aptitude assc=estTitulaire coll=java.util.Collection impl=java.util.HashSet mult=0..* */
	   public java.util.Collection<Aptitude> estPossedee;
	   
	   /*Constructeurs*/
	   
	    public Agent(){}
	   
	    public Agent(String nom, String motdepasse, String matricule, List listeUV, List listeAptitude, Boolean gestionnaire) {
		super();
		this.nom = nom;
		this.mdp = motdepasse;
		this.matricule = matricule;
		this.listeUV = listeUV;
		this.listeAptitude = listeAptitude;
		this.gestionnaire = gestionnaire;
	}

	   /*Accesseurs et Modificateurs  */
	   
	   
	   public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getMdp() {
			return mdp;
		}

		public void setMdp(String mdp) {
			this.mdp = mdp;
		}

		public String getMatricule() {
			return matricule;
		}

		public void setMatricule(String matricule) {
			this.matricule = matricule;
		}

		public List getListeUV() {
			return listeUV;
		}

		public void setListeUV(List listeUV) {
			this.listeUV = listeUV;
		}

		public List getListeAptitude() {
			return listeAptitude;
		}

		public void setListeAptitude(List listeAptitude) {
			this.listeAptitude = listeAptitude;
		}

		public Boolean getGestionnaire() {
			return gestionnaire;
		}

		public void setGestionnaire(Boolean gestionnaire) {
			this.gestionnaire = gestionnaire;
		}

		public Session getEstDirigee() {
			return estDirigee;
		}

		public void setEstDirigee(Session estDirigee) {
			this.estDirigee = estDirigee;
		}

		public Session getAccueil() {
			return accueil;
		}

		public void setAccueil(Session accueil) {
			this.accueil = accueil;
		}
		
		/*Accesseur et Modificateurs pour les listes */
	   
	/** @pdGenerated default getter 
	 * 
	 * 
	 * */
	   public java.util.Collection<Session> getContient() {
	      if (contient == null)
	         contient = new java.util.HashSet<Session>();
	      return contient;
	   }
	   
	   /** @pdGenerated default iterator getter */
	   public java.util.Iterator getIteratorContient() {
	      if (contient == null)
	         contient = new java.util.HashSet<Session>();
	      return contient.iterator();
	   }
	   
	   /** @pdGenerated default setter
	     * @param newContient */
	   public void setContient(java.util.Collection<Session> newContient) {
	      removeAllContient();
	      for (java.util.Iterator iter = newContient.iterator(); iter.hasNext();)
	         addContient((Session)iter.next());
	   }
	   
	   /** @pdGenerated default add
	     * @param newSession */
	   public void addContient(Session newSession) {
	      if (newSession == null)
	         return;
	      if (this.contient == null)
	         this.contient = new java.util.HashSet<Session>();
	      if (!this.contient.contains(newSession))
	         this.contient.add(newSession);
	   }
	   
	   /** @pdGenerated default remove
	     * @param oldSession */
	   public void removeContient(Session oldSession) {
	      if (oldSession == null)
	         return;
	      if (this.contient != null)
	         if (this.contient.contains(oldSession))
	            this.contient.remove(oldSession);
	   }
	   
	   /** @pdGenerated default removeAll */
	   public void removeAllContient() {
	      if (contient != null)
	         contient.clear();
	   }
	   /** @pdGenerated default getter */
	   public java.util.Collection<Session> getEstGeree() {
	      if (estGeree == null)
	         estGeree = new java.util.HashSet<Session>();
	      return estGeree;
	   }
	   
	   /** @pdGenerated default iterator getter */
	   public java.util.Iterator getIteratorEstGeree() {
	      if (estGeree == null)
	         estGeree = new java.util.HashSet<Session>();
	      return estGeree.iterator();
	   }
	   
	   /** @pdGenerated default setter
	     * @param newEstGeree */
	   public void setEstGeree(java.util.Collection<Session> newEstGeree) {
	      removeAllEstGeree();
	      for (java.util.Iterator iter = newEstGeree.iterator(); iter.hasNext();)
	         addEstGeree((Session)iter.next());
	   }
	   
	   /** @pdGenerated default add
	     * @param newSession */
	   public void addEstGeree(Session newSession) {
	      if (newSession == null)
	         return;
	      if (this.estGeree == null)
	         this.estGeree = new java.util.HashSet<Session>();
	      if (!this.estGeree.contains(newSession))
	         this.estGeree.add(newSession);
	   }
	   
	   /** @pdGenerated default remove
	     * @param oldSession */
	   public void removeEstGeree(Session oldSession) {
	      if (oldSession == null)
	         return;
	      if (this.estGeree != null)
	         if (this.estGeree.contains(oldSession))
	            this.estGeree.remove(oldSession);
	   }
	   
	   /** @pdGenerated default removeAll */
	   public void removeAllEstGeree() {
	      if (estGeree != null)
	         estGeree.clear();
	   }
	   /** @pdGenerated default getter */
	   public java.util.Collection<Uv> getEstDetenue() {
	      if (estDetenue == null)
	         estDetenue = new java.util.HashSet<Uv>();
	      return estDetenue;
	   }
	   
	   /** @pdGenerated default iterator getter */
	   public java.util.Iterator getIteratorEstDetenue() {
	      if (estDetenue == null)
	         estDetenue = new java.util.HashSet<Uv>();
	      return estDetenue.iterator();
	   }
	   
	   /** @pdGenerated default setter
	     * @param newEstDetenue */
	   public void setEstDetenue(java.util.Collection<Uv> newEstDetenue) {
	      removeAllEstDetenue();
	      for (java.util.Iterator iter = newEstDetenue.iterator(); iter.hasNext();)
	         addEstDetenue((Uv)iter.next());
	   }
	   
	   /** @pdGenerated default add
	     * @param newUv */
	   public void addEstDetenue(Uv newUv) {
	      if (newUv == null)
	         return;
	      if (this.estDetenue == null)
	         this.estDetenue = new java.util.HashSet<Uv>();
	      if (!this.estDetenue.contains(newUv))
	         this.estDetenue.add(newUv);
	   }
	   
	   /** @pdGenerated default remove
	     * @param oldUv */
	   public void removeEstDetenue(Uv oldUv) {
	      if (oldUv == null)
	         return;
	      if (this.estDetenue != null)
	         if (this.estDetenue.contains(oldUv))
	            this.estDetenue.remove(oldUv);
	   }
	   
	   /** @pdGenerated default removeAll */
	   public void removeAllEstDetenue() {
	      if (estDetenue != null)
	         estDetenue.clear();
	   }
	   /** @pdGenerated default getter */
	   public java.util.Collection<Aptitude> getEstPossedee() {
	      if (estPossedee == null)
	         estPossedee = new java.util.HashSet<Aptitude>();
	      return estPossedee;
	   }
	   
	   /** @pdGenerated default iterator getter */
	   public java.util.Iterator getIteratorEstPossedee() {
	      if (estPossedee == null)
	         estPossedee = new java.util.HashSet<Aptitude>();
	      return estPossedee.iterator();
	   }
	   
	   /** @pdGenerated default setter
	     * @param newEstPossedee */
	   public void setEstPossedee(java.util.Collection<Aptitude> newEstPossedee) {
	      removeAllEstPossedee();
	      for (java.util.Iterator iter = newEstPossedee.iterator(); iter.hasNext();)
	         addEstPossedee((Aptitude)iter.next());
	   }
	   
	   /** @pdGenerated default add
	     * @param newAptitude */
	   public void addEstPossedee(Aptitude newAptitude) {
	      if (newAptitude == null)
	         return;
	      if (this.estPossedee == null)
	         this.estPossedee = new java.util.HashSet<Aptitude>();
	      if (!this.estPossedee.contains(newAptitude))
	         this.estPossedee.add(newAptitude);
	   }
	   
	   /** @pdGenerated default remove
	     * @param oldAptitude */
	   public void removeEstPossedee(Aptitude oldAptitude) {
	      if (oldAptitude == null)
	         return;
	      if (this.estPossedee != null)
	         if (this.estPossedee.contains(oldAptitude))
	            this.estPossedee.remove(oldAptitude);
	   }
	   
	   /** @pdGenerated default removeAll */
	   public void removeAllEstPossedee() {
	      if (estPossedee != null)
	         estPossedee.clear();
	   }
	
	
	
	
	
}
