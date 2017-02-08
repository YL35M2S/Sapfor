package cci.caos.repository;

import java.util.Date;

public class Session {
    private int     id;
    private String  nom;
    private Date    dateDebut, dateFin;
    private boolean ouverteInscription;

    /* Constructeurs */
    public Session() {
    }

    public Session( int id, String n, Date dd, Date df, boolean b ) {
        this.id = id;
        this.nom = n;
        this.dateDebut = dd;
        this.dateFin = df;
        this.ouverteInscription = b;
    }

    /* Accesseurs et Modificateurs */
    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
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

    public String getNom() {
        return nom;
    }

    public void setNom( String nom ) {
        this.nom = nom;
    }

    public String presenteToi() {
        return this.nom;
    }

    public boolean fermer() {
        return this.ouverteInscription = false;
    }

}
