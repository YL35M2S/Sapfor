package cci.caos.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CandidatGenerique {
    private int    id_Agent;
    private String nom;
    private String matricule;
    private String role;
    private int    statutCandidature;

    /* Constructeurs */
    public CandidatGenerique() {
    }

    public CandidatGenerique( int id_Agent, String n, String m, String role, int statutCandidature ) {
        this.id_Agent = id_Agent;
        this.nom = n;
        this.matricule = m;
        this.role = role;
        this.statutCandidature = statutCandidature;
    }

    /* Accesseurs et Modificateurs */
    public String getNom() {
        return nom;
    }

    public void setNom( String nom ) {
        this.nom = nom;
    }

    public int getId_Agent() {
        return id_Agent;
    }

    public void setId_Agent( int id_Agent ) {
        this.id_Agent = id_Agent;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule( String matricule ) {
        this.matricule = matricule;
    }

    public String getRole() {
        return role;
    }

    public void setRole( String role ) {
        this.role = role;
    }

    public int getStatutCandidature() {
        return statutCandidature;
    }

    public void setStatutCandidature( int statutCandidature ) {
        this.statutCandidature = statutCandidature;
    }
}