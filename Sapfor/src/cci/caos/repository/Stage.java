package cci.caos.repository;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Stage {

    private int    id;
    private String nom;

    /**
     * Constructeur vide
     */
    public Stage() {
    }

    /**
     * Constructeur qui initialise la variable d'instance : nom
     * 
     * @param nom
     *            le nom du stage
     */
    public Stage( String nom ) {
        this.nom = nom;
    }

    /* Accesseurs et Modificateurs */
    /**
     * Renvoie l'id du stage
     * 
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Change l'id du stage
     * 
     * @param id
     *            l'id du stage
     */
    public void setId( int id ) {
        this.id = id;
    }

    /**
     * Renvoie le nom du stage
     * 
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Change le nom du stage
     * 
     * @param nom
     *            le nom du stage
     * 
     */
    public void setNom( String nom ) {
        this.nom = nom;
    }
}