package cci.caos.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AgentConnection {

    private String  uuid;
    private String  nom;
    private String  matricule;
    private boolean gestionnaire;

    /**
     * Constructeur vide
     */
    public AgentConnection() {
    }

    /* Accesseurs et Modificateurs */
    
    /**
     * Renvoie l'UUID d'un agent
     * @return UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Change l'UUID d'un agent
     * @param uuid
     */
    public void setUuid( String uuid ) {
        this.uuid = uuid;
    }

    /**
     * Renvoie le Nom de l'agent
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
     * Renvoie le matricule de l'agent
     * @return matricule
     */
    public String getMatricule() {
        return matricule;
    }
    
    /**
     * Change le matricule de l'agent
     * @param matricule
     */
    public void setMatricule( String matricule ) {
        this.matricule = matricule;
    }

    /**
     * Renvoie si l'agent est gestionnaire
     * @return true si l'agent est gestionnaire, sinon false
     */
    public boolean getGestionnaire() {
        return gestionnaire;
    }

    /**
     * Change le status gestionnaire de l'agent
     * @param gestionnaire
     */
    public void setGestionnaire( boolean gestionnaire ) {
        this.gestionnaire = gestionnaire;
    }
}