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
    public SessionGenerique() {
    }

    public SessionGenerique( int id, String n, String dd, String df, String uv, String stage ) {
        this.id = id;
        this.nom = n;
        this.dateDebut = dd;
        this.dateFin = df;
        this.uv = uv;
        this.stage = stage;
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

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut( String dateDebut ) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin( String dateFin ) {
        this.dateFin = dateFin;
    }

    public String getUv() {
        return uv;
    }

    public void setUv( String uv ) {
        this.uv = uv;
    }

    public String getStage() {
        return stage;
    }

    public void setStage( String stage ) {
        this.stage = stage;
    }
}