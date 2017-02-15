package cci.caos.repository;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Candidature {

    private Agent   agent;
    private int     statutCandidature;
    private boolean estFormateur;
    private Session session;

	/* Contructeurs */
    public Candidature() {
    }

    public Candidature( Agent agent, int statutCandidature, boolean estFormateur, Session session ) {
        this.agent = agent;
        this.statutCandidature = statutCandidature;
        this.estFormateur = estFormateur;
        this.session = session;
    }

    /* Accesseurs et Modificateurs */
    public Agent getAgent() {
        return agent;
    }

    public void setAgent( Agent agent ) {
        this.agent = agent;
    }

    public int getStatutCandidature() {
        return statutCandidature;
    }

    public void setStatutCandidature( int statutCandidature ) {
        this.statutCandidature = statutCandidature;
    }

    public boolean isEstFormateur() {
        return estFormateur;
    }

    public void setEstFormateur( boolean estFormateur ) {
        this.estFormateur = estFormateur;
    }
    
    public Session getSession() {
  		return session;
  	}

  	public void setSession(Session session) {
  		this.session = session;
  	}


}
