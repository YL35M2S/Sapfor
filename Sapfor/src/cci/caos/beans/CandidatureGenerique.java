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
    public CandidatureGenerique() {
    }

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
    public int getId_Agent() {
        return id_Agent;
    }

    public void setId_Agent( int id ) {
        this.id_Agent = id;
    }

    public int getId_Session() {
        return id_Session;
    }

    public void setId_Session( int id ) {
        this.id_Session = id;
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

    public boolean isEstFormateur() {
        return estFormateur;
    }

    public void setEstFormateur( boolean estFormateur ) {
        this.estFormateur = estFormateur;
    }

    public int getStatutCandidature() {
        return statutCandidature;
    }

    public void setStatutCandidature( int statutCandidature ) {
        this.statutCandidature = statutCandidature;
    }
}