package cci.caos.repository;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Stage {

    private int    id;
    private String nom;

    /* Constructeurs */
    public Stage() {
    }

    public Stage( String nom ) {
        this.nom = nom;
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
}
