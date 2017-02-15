package cci.caos.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Session {
    private int               id;
    private String            nom;
    private Date              dateDebut, dateFin;
    private boolean           ouverteInscription;
    private Uv                uv;
    private List<Candidature> candidats;
   

    /* Constructeurs */
    public Session() {
    }

    public Session( int id, String n, Date dd, Date df, boolean b, Uv uv ) {
        this.id = id;
        this.nom = n;
        this.dateDebut = dd;
        this.dateFin = df;
        this.ouverteInscription = b;
        this.uv = uv;
        this.candidats = new ArrayList<Candidature>();
    }

    /* Accesseurs et Modificateurs */
    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom( String nom ) {
        this.nom = nom;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut( Date dateDebut ) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin( Date dateFin ) {
        this.dateFin = dateFin;
    }

    public boolean isOuverteInscription() {
        return ouverteInscription;
    }

    public void setOuverteInscription( boolean ouverteInscription ) {
        this.ouverteInscription = ouverteInscription;
    }

    public Uv getUv() {
        return uv;
    }

    public void setUv( Uv uv ) {
        this.uv = uv;
    }

    public List<Candidature> getCandidats() {
        return candidats;
    }

    public void setCandidats( List<Candidature> candidats ) {
        this.candidats = candidats;
    }

    /* Fonctions Specifiques */
    /**
     * Permet de cloturer la session aux candidatures
     * 
     * @return Vrai si la cloture s'est deroulée correctement
     */
    public boolean fermerCandidature() {
        this.ouverteInscription = false;
        return true;
    }

    /**
     * Permet de mettre à jour la liste des candidatures à la session
     * 
     * @return Vrai si la mise a jour s'est deroulée correctement
     */
    public boolean modifierListeCandidats( List<Candidature> candidatures ) {
        this.candidats = candidatures;
        return true;
    }
    
    /**
     * 
     * @param id: id d'un agent
     * @return True si la candidature a bien été retirée
     */
    public boolean retirerCandidature(int idAgent){
    	Iterator<Candidature> it = candidats.iterator();
    	while (it.hasNext()) {
    		Candidature elemCourant = it.next();
    		if (elemCourant.getAgent().getId()==idAgent) {
    			return candidats.remove(elemCourant);
    		}	
    	}
    	return false;
    }
	public boolean deposerCandidature(int idAgent) {
		Iterator<Candidature> it = candidats.iterator();
    	while (it.hasNext()) {
    		Candidature elemCourant = it.next();
    		if (elemCourant.getAgent().getId()==idAgent) {
    			return candidats.add(elemCourant);
    		}	
    	}
		

		return false;
	}
  
  
}
