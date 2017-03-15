package cci.caos.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CandidatureGenerique {
    private int     id_Agent;
    private boolean estFormateur;
    private int     statutCandidature;
    private int     id_Session;
    private String  nom;
    private String  dateDebut;
    private String  dateFin;
    private String  uv;
    private String  stage;

    /* Constructeurs */

    /**
     * Constructeur vide
     */
    public CandidatureGenerique() {
    }

    /**
     * Constructeur
     * 
     * @param id_Agent
     *            l'id d'un agent
     * @param estFormateur
     *            le role de la candidature (True si candidature pour etre
     *            formateur)
     * @param statutCandidature
     *            le statut de la candidature
     * @param id_Session
     *            l'id de la session
     * @param n
     *            le nom de la session
     * @param dd
     *            Date la date de début de la session
     * @param df
     *            Date la date de fin de la session
     * @param uv
     *            l'UV concernee par la session
     * @param stage
     *            le stage auquel appartient la session
     */
    public CandidatureGenerique( int id_Agent, boolean estFormateur, int statutCandidature, int id_Session, String n,
            String dd, String df, String uv, String stage ) {
        this.id_Agent = id_Agent;
        this.id_Session = id_Session;
        this.estFormateur = estFormateur;
        this.statutCandidature = statutCandidature;
        this.nom = n;
        this.dateDebut = dd;
        this.dateFin = df;
        this.uv = uv;
        this.stage = stage;
    }

    /* Accesseurs et Modificateurs */

    /**
     * Renvoie l'id de l'agent
     * 
     * @return id d'agent
     */
    public int getId_Agent() {
        return id_Agent;
    }

    /**
     * Change l'id de l'agent
     * 
     * @param id
     *            l'id de l'agent candidat
     */
    public void setId_Agent( int id ) {
        this.id_Agent = id;
    }

    /**
     * Retourne l'id de la session
     * 
     * @return id de session
     */
    public int getId_Session() {
        return id_Session;
    }

    /**
     * Change l'id de la session
     * 
     * @param id
     *            l'id de la session concernee par la candidature
     */
    public void setId_Session( int id ) {
        this.id_Session = id;
    }

    /**
     * Renvoie le nom d'un agent
     * 
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Change le nom d'un agent
     * 
     * @param nom
     *            le nom de l'agent candidat
     */
    public void setNom( String nom ) {
        this.nom = nom;
    }

    /**
     * Renvoie la date de début de la session candidate
     * 
     * @return date (format String)
     */
    public String getDateDebut() {
        return dateDebut;
    }

    /**
     * Modifie la date de début de la session candidate
     * 
     * @param dateDebut
     *            la date de debut de la session concernee par la candidature
     * 
     */
    public void setDateDebut( String dateDebut ) {
        this.dateDebut = dateDebut;
    }

    /**
     * Renvoie la date de fin de la session candidate
     * 
     * @return date (format String)
     */
    public String getDateFin() {
        return dateFin;
    }

    /**
     * Change la date de fin de la session candidate
     * 
     * @param dateFin
     *            la date de fin de la session concernee par la candidature
     */
    public void setDateFin( String dateFin ) {
        this.dateFin = dateFin;
    }

    /**
     * Renvoie le nom de l'UV concernee
     * 
     * @return UV
     */
    public String getUv() {
        return uv;
    }

    /**
     * Modifie le nom de l'UV concernee
     * 
     * @param uv
     *            le nom de l'UV concernee par la candidature
     */
    public void setUv( String uv ) {
        this.uv = uv;
    }

    /**
     * Renvoie le nom stage concerne
     * 
     * @return nom du stage
     */
    public String getStage() {
        return stage;
    }

    /**
     * Change le nom du stage concerne
     * 
     * @param stage
     *            le nom du stage concerne par la candidature
     */
    public void setStage( String stage ) {
        this.stage = stage;
    }

    /**
     * Renvoie si le candidat candidate en tant que formateur
     * 
     * @return true si l'agent candidate en tant que formateur, sinon false
     */
    public boolean isEstFormateur() {
        return estFormateur;
    }

    /**
     * Change l'etat de formateur du candidat pour cette session
     * 
     * @param estFormateur
     *            le role (Formateur/Stagiaire) de l'agent candidat (True si
     *            candidature pour etre formateur)
     */
    public void setEstFormateur( boolean estFormateur ) {
        this.estFormateur = estFormateur;
    }

    /**
     * Renvoie le statut de la candidature
     * 
     * @return statut le statut de la candidature (-2 : Candidature non statuee
     *         ; -1 : Candidature acceptee ; 0 : Candidature refusee ; 1..n :
     *         place dans la liste d'attente)
     */
    public int getStatutCandidature() {
        return statutCandidature;
    }

    /**
     * Modifie le statut de la candidature
     * 
     * @param statutCandidature
     *            le statut de la candidature (-2 : Candidature non statuee ; -1
     *            : Candidature acceptee ; 0 : Candidature refusee ; 1..n :
     *            place dans la liste d'attente)
     */
    public void setStatutCandidature( int statutCandidature ) {
        this.statutCandidature = statutCandidature;
    }
}