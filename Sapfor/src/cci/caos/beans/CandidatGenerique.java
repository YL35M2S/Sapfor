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
    
    /**
     * Constructeur vide
     */
    public CandidatGenerique() {
    }

    /**
     * Constructeur
     * @param id_Agent Id d'un agent
     * @param n Nom d'un agent
     * @param m Matricule d'un agent
     * @param role Role d'un agent
     * @param statutCandidature Statut de la candidature
     */
    public CandidatGenerique( int id_Agent, String n, String m, String role, int statutCandidature ) {
        this.id_Agent = id_Agent;
        this.nom = n;
        this.matricule = m;
        this.role = role;
        this.statutCandidature = statutCandidature;
    }

    /* Accesseurs et Modificateurs */
    
    /**
     * Renvoie le nom de l'agent
     * @return Nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Change le nom de l'agent
     * @param nom
     */
    public void setNom( String nom ) {
        this.nom = nom;
    }
    
    /**
     * Renvoie l'id de l'agent
     * @return Id
     */
    public int getId_Agent() {
        return id_Agent;
    }
    
    /**
     * Change l'id d'un agent
     * @param id_Agent
     */
    public void setId_Agent( int id_Agent ) {
        this.id_Agent = id_Agent;
    }
    /**
     * Renvoie le matricule d'un agent
     * @return matricule
     */
    public String getMatricule() {
        return matricule;
    }
    
    /**
     * Change le matricule
     * @param matricule
     */
    public void setMatricule( String matricule ) {
        this.matricule = matricule;
    }

    /**
     * Renvoie le role de l'agent
     * @return Role
     */
    public String getRole() {
        return role;
    }
    
    /**
     * Modifie le role de l'agent
     * @param role
     */
    public void setRole( String role ) {
        this.role = role;
    }
    
    /**
     * Renvoie le status de la candidature
     * @return ???????????
     */
    public int getStatutCandidature() {
        return statutCandidature;
    }
    
    /**
     * Modifie le status de la Candidature
     * @param statutCandidature
     */
    public void setStatutCandidature( int statutCandidature ) {
        this.statutCandidature = statutCandidature;
    }
}