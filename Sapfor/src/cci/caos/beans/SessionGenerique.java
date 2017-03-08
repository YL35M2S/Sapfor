package cci.caos.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SessionGenerique {
    private int    id;
    private String nom;
    private String dateDebut;
    private String dateFin;
    private String uv;
    private String stage;

    /* Constructeurs */
    
    /**
     * Constructeur vide
     */
    public SessionGenerique() {
    }

    /**
     * Constructeur
     * @param id Id de la session
     * @param n Nom de la session
     * @param dd Date début de la session
     * @param df Date fin de la session
     * @param uv Nom de l'UV concernee
     * @param stage Nom du stage auquel appartient la session
     */
    public SessionGenerique( int id, String n, String dd, String df, String uv, String stage ) {
        this.id = id;
        this.nom = n;
        this.dateDebut = dd;
        this.dateFin = df;
        this.uv = uv;
        this.stage = stage;
    }

    /* Accesseurs et Modificateurs */
    
    /**
     * Renvoie l'id de la session
     * @return id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Change l'id de la session
     * @param id
     */
    public void setId( int id ) {
        this.id = id;
    }
    
    /**
     * Renvoie le nom de la session
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Change le nom de la session
     * @param nom
     */
    public void setNom( String nom ) {
        this.nom = nom;
    }

    /**
     * Renvoie la date de debut de la session
     * @return Date (format String)
     */
    public String getDateDebut() {
        return dateDebut;
    }

    /**
     * Modifie la date de début de la session
     * @param dateDebut
     */
    public void setDateDebut( String dateDebut ) {
        this.dateDebut = dateDebut;
    }

    /**
     * Renvoie la date de fin de la session
     * @return Date (format String)
     */
    public String getDateFin() {
        return dateFin;
    }

    /**
     * Modifie la date de fin de la session
     * @param dateFin
     */
    public void setDateFin( String dateFin ) {
        this.dateFin = dateFin;
    }

    /**
     * Renvoie le nom de l'UV concernee par la session
     * @return nom UV
     */
    public String getUv() {
        return uv;
    }

    /**
     * Modifie le nom de l'UV concernee par la session
     * @param uv
     */
    public void setUv( String uv ) {
        this.uv = uv;
    }

    /**
     * Renvoie le stage auquel appartient la session
     * @return nom Stage
     */
    public String getStage() {
        return stage;
    }

    /**
     * Modifie le nom du stage auquel appartient la session
     * @param stage
     */
    public void setStage( String stage ) {
        this.stage = stage;
    }
}