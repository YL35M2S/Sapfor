package cci.caos.repository;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Candidature {

    private Agent   agent;
    private int     statutCandidature;
    private boolean estFormateur;
    private Session session;

    
    /**
     * Constructeur vide
     */
    public Candidature() {
    }
    /**
     * Constructeur qui initialise les variables d'instance
     * @param agent
     * @param statutCandidature
     * @param estFormateur
     * @param session
     */
    public Candidature( Agent agent, int statutCandidature, boolean estFormateur, Session session ) {
        this.agent = agent;
        this.statutCandidature = statutCandidature;
        this.estFormateur = estFormateur;
        this.session = session;
    }

    /* Accesseurs et Modificateurs */
    /**
     * Renvoie l'agent de la candidature
     * @return agent
     */
    public Agent getAgent() {
        return agent;
    }
    /**
     * Change l'agent de la candidature
     * @param agent
     */
    public void setAgent( Agent agent ) {
        this.agent = agent;
    }
    /**
     * Renvoie le statut de la candidature
     * @return statutCandidature
     */
    public int getStatutCandidature() {
        return statutCandidature;
    }
    /**
     * Change le statut de la candidature
     * @param statutCandidature
     */
    public void setStatutCandidature( int statutCandidature ) {
        this.statutCandidature = statutCandidature;
    }
    /**
     * Verifie si le candidat est un formateur ou non
     * @return Vrai si le candidat est formateur, faux sinon
     */
    public boolean isEstFormateur() {
        return estFormateur;
    }
    /**
     * Change la valeur du boolean formateur
     * @param estFormateur
     */
    public void setEstFormateur( boolean estFormateur ) {
        this.estFormateur = estFormateur;
    }
    /**
     * Renvoie la session concernee par la candidature
     * @return session
     */
    public Session getSession() {
        return session;
    }
    /**
     * Change la session de la candidature
     * @param session
     */
    public void setSession( Session session ) {
        this.session = session;
    }

}
