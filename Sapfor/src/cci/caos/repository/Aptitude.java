package cci.caos.repository;
import java.util.*;


public class Aptitude {
	
	public String nom;
	public List listeUV;
	
	
	public java.util.Collection<Uv> estRequise;
	
	
	 /*Constructeurs*/
	
	public Aptitude(){}
	
	public Aptitude(String nom, List listeUV) {
		super();
		this.nom = nom;
		this.listeUV = listeUV;
	}

	/* Accesseurs et Modificateurs */
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List getListeUV() {
		return listeUV;
	}

	public void setListeUV(List listeUV) {
		this.listeUV = listeUV;
	}
	
	/** @pdGenerated default getter */
	   public java.util.Collection<Uv> getEstRequise() {
	      if (estRequise == null)
	         estRequise = new java.util.HashSet<Uv>();
	      return estRequise;
	   }
	   
	   /** @pdGenerated default iterator getter */
	   public java.util.Iterator getIteratorEstRequise() {
	      if (estRequise == null)
	         estRequise = new java.util.HashSet<Uv>();
	      return estRequise.iterator();
	   }
	   
	   /** @pdGenerated default setter
	     * @param newEstRequise */
	   public void setEstRequise(java.util.Collection<Uv> newEstRequise) {
	      removeAllEstRequise();
	      for (java.util.Iterator iter = newEstRequise.iterator(); iter.hasNext();)
	         addEstRequise((Uv)iter.next());
	   }
	   
	   /** @pdGenerated default add
	     * @param newUv */
	   public void addEstRequise(Uv newUv) {
	      if (newUv == null)
	         return;
	      if (this.estRequise == null)
	         this.estRequise = new java.util.HashSet<Uv>();
	      if (!this.estRequise.contains(newUv))
	         this.estRequise.add(newUv);
	   }
	   
	   /** @pdGenerated default remove
	     * @param oldUv */
	   public void removeEstRequise(Uv oldUv) {
	      if (oldUv == null)
	         return;
	      if (this.estRequise != null)
	         if (this.estRequise.contains(oldUv))
	            this.estRequise.remove(oldUv);
	   }
	   
	   /** @pdGenerated default removeAll */
	   public void removeAllEstRequise() {
	      if (estRequise != null)
	         estRequise.clear();
	   }
	
	
	
}
