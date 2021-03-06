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
     * 
     * @param id_Agent
     *            l'Id d'un agent
     * @param n
     *            le nom d'un agent
     * @param m
     *            le matricule d'un agent
     * @param role
     *            le role d'un agent
     * @param statutCandidature
     *            le statut de la candidature
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
     * 
     * @return Nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Change le nom de l'agent candidat
     * 
     * @param nom
     *            le nom du candidat a modifier
     */
    public void setNom( String nom ) {
        this.nom = nom;
    }

    /**
     * Renvoie l'id de l'agent
     * 
     * @return Id
     */
    public int getId_Agent() {
        return id_Agent;
    }

    /**
     * Change l'id d'un agent
     * 
     * @param id_Agent
     *            l'id de l'agent candidat
     */
    public void setId_Agent( int id_Agent ) {
        this.id_Agent = id_Agent;
    }

    /**
     * Renvoie le matricule d'un agent
     * 
     * @return matricule
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * Change le matricule
     * 
     * @param matricule
     *            le matricule de l'agent candidat
     */
    public void setMatricule( String matricule ) {
        this.matricule = matricule;
    }

    /**
     * Renvoie le role de l'agent
     * 
     * @return Role
     */
    public String getRole() {
        return role;
    }

    /**
     * Modifie le role de l'agent
     * 
     * @param role
     *            le role de l'agent candidat
     */
    public void setRole( String role ) {
        this.role = role;
    }

    /**
     * Renvoie le status de la candidature
     * 
     * @return le statut de la candidature (-2 : Candidature non statuee ; -1 :
     *         Candidature acceptee ; 0 : Candidature refusee ; 1..n : place
     *         dans la liste d'attente)
     */
    public int getStatutCandidature() {
        return statutCandidature;
    }

    /**
     * Modifie le status de la Candidature
     * 
     * @param statutCandidature
     *            le statut de la candidature
     */
    public void setStatutCandidature( int statutCandidature ) {
        this.statutCandidature = statutCandidature;
    }
}