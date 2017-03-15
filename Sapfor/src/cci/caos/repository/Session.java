package cci.caos.repository;

import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Session {
    private int     id;
    private String  nom;
    private Date    dateDebut, dateFin;
    private boolean ouverteInscription;
    private Uv      uv;
    private Stage   stage;

    /**
     * Constructeur vide
     */
    public Session() {
    }

    /**
     * Constructeur qui initialise les variables d'instance
     * 
     * @param n
     *            le nom de la session
     * @param dd
     *            la date de debut de la session
     * @param df
     *            la date de fin de la session
     * @param b
     *            le statut ouverte aux inscriptions ou fermee
     * @param uv
     *            l'uv concerne par la session de formation
     * @param stage
     *            le stage contenant la session
     */
    public Session( String n, Date dd, Date df, boolean b, Uv uv, Stage stage ) {
        this.nom = n;
        this.dateDebut = dd;
        this.dateFin = df;
        this.ouverteInscription = b;
        this.uv = uv;
        this.stage = stage;
    }

    /* Accesseurs et Modificateurs */
    /**
     * Renvoie l'id de la session
     * 
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Change l'id de la session
     * 
     * @param id
     *            l'id de la session
     */
    public void setId( int id ) {
        this.id = id;
    }

    /**
     * Renvoie le nom de la sesion
     * 
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Change le nom de la session
     * 
     * @param nom
     *            le nom de la session
     */
    public void setNom( String nom ) {
        this.nom = nom;
    }

    /**
     * Renvoie la date de debut de session
     * 
     * @return dateDebut
     */
    public Date getDateDebut() {
        return dateDebut;
    }

    /**
     * Change la date de debut de session
     * 
     * @param dateDebut
     *            date de debut de la session
     */
    public void setDateDebut( Date dateDebut ) {
        this.dateDebut = dateDebut;
    }

    /**
     * Renvoie la date de fin de session
     * 
     * @return dateFin
     */
    public Date getDateFin() {
        return dateFin;
    }

    /**
     * Change la date de fin de session
     * 
     * @param dateFin
     *            date de fin de la session
     */
    public void setDateFin( Date dateFin ) {
        this.dateFin = dateFin;
    }

    /**
     * Verifie si la session est ouverte aux inscriptions
     * 
     * @return Vrai si ouvert, faux sinon
     */
    public boolean isOuverteInscription() {
        return ouverteInscription;
    }

    /**
     * Change la valeur booleenne de l'ouverture de l'inscription a la session
     * 
     * @param ouverteInscription
     *            statut de la session de formation (ouverte aux inscriptions ou
     *            fermee)
     */
    public void setOuverteInscription( boolean ouverteInscription ) {
        this.ouverteInscription = ouverteInscription;
    }

    /**
     * Renvoie une uv de la session
     * 
     * @return uv
     */
    public Uv getUv() {
        return uv;
    }

    /**
     * Change l'uv de la session
     * 
     * @param uv
     *            l'UV de la session
     */
    public void setUv( Uv uv ) {
        this.uv = uv;
    }

    /**
     * Renvoie le stage qui propose la session
     * 
     * @return stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Change le stage de la session
     * 
     * @param stage
     *            la stage integrant la session
     */
    public void setStage( Stage stage ) {
        this.stage = stage;
    }

    /* Fonctions Specifiques */
    /**
     * Permet de cloturer la session aux candidatures
     * 
     * @return Vrai si la cloture s'est deroulee correctement
     */
    public boolean fermerCandidature() {
        this.ouverteInscription = false;
        return true;
    }

    /**
     * Permet d'ouvrir la session aux candidatures
     * 
     * @return Vrai si l'ouverture s'est deroulee correctement
     */
    public boolean ouvrirCandidature() {
        this.ouverteInscription = true;
        return true;
    }
}
