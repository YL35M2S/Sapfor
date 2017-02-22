package cci.caos.repository;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Aptitude {

    private int      id;
    private String   nom;
    private List<Uv> listeUV;

    /* Constructeurs */

    public Aptitude() {
    }

    public Aptitude( String nom, List<Uv> listeUV ) {
        this.nom = nom;
        this.listeUV = listeUV;
    }

    public Aptitude( String nom ) {
        this.nom = nom;
        this.listeUV = new ArrayList<Uv>();
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

    public List<Uv> getListeUV() {
        return listeUV;
    }

    public void setListeUV( List<Uv> listeUV ) {
        this.listeUV = listeUV;
    }

    /* Ajoute une Uv prérequise à l'aptitude */
    public void ajouterUv( Uv u ) {
        this.listeUV.add( u );
    }
}
