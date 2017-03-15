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
     * 
     * @param agent
     *            l'agent depositaire de la candidature
     * @param statutCandidature
     *            le statut de la candidature
     * @param estFormateur
     *            le role propose lors de la session de formation
     * @param session
     *            la session concernant par la candidature
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
     * 
     * @return agent
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     * Change l'agent de la candidature
     * 
     * @param agent
     *            l'agent de la candidature
     */
    public void setAgent( Agent agent ) {
        this.agent = agent;
    }

    /**
     * Renvoie le statut de la candidature
     * 
     * @return statutCandidature
     */
    public int getStatutCandidature() {
        return statutCandidature;
    }

    /**
     * Change le statut de la candidature
     * 
     * @param statutCandidature
     *            le statut de la candidature (-2 : Candidature non statuee ; -1
     *            : Candidature acceptee ; 0 : Candidature refusee ; 1..n :
     *            place dans la liste d'attente)
     */
    public void setStatutCandidature( int statutCandidature ) {
        this.statutCandidature = statutCandidature;
    }

    /**
     * Verifie si le candidat est un formateur ou non
     * 
     * @return Vrai si le candidat est formateur, faux sinon
     */
    public boolean isEstFormateur() {
        return estFormateur;
    }

    /**
     * Change la valeur du boolean formateur
     * 
     * @param estFormateur
     *            le role (Formateur/Stagiaire) du candidat
     */
    public void setEstFormateur( boolean estFormateur ) {
        this.estFormateur = estFormateur;
    }

    /**
     * Renvoie la session concernee par la candidature
     * 
     * @return session
     */
    public Session getSession() {
        return session;
    }

    /**
     * Change la session de la candidature
     * 
     * @param session
     *            la session concerne par la candidature
     */
    public void setSession( Session session ) {
        this.session = session;
    }
}