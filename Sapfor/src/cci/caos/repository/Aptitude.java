package cci.caos.repository;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Aptitude {

    private int      id;
    private String   nom;
    private List<Uv> listeUV;

    /**
     * Constructeur vide
     */
    public Aptitude() {
    }

    /**
     * Constructeur avec 2 parametres
     * 
     * @param nom
     *            le nom de l'aptitude
     * @param listeUV
     *            la liste des UV necessaire a l'obtention de l'aptitude
     */
    public Aptitude( String nom, List<Uv> listeUV ) {
        this.nom = nom;
        this.listeUV = listeUV;
    }

    /**
     * Constructeur avec un parametre
     * 
     * @param nom
     *            le nom de l'aptitude
     */
    public Aptitude( String nom ) {
        this.nom = nom;
        this.listeUV = new ArrayList<Uv>();
    }

    /* Accesseurs et Modificateurs */
    /**
     * Renvoie l'id d'une aptitude
     * 
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Change l'id d'une aptitude
     * 
     * @param id
     *            l'id de l'aptitude
     */
    public void setId( int id ) {
        this.id = id;
    }

    /**
     * Renvoie le nom d'une aptitude
     * 
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Change le nom d'une aptitude
     * 
     * @param nom
     *            le nom de l'aptitude
     */
    public void setNom( String nom ) {
        this.nom = nom;
    }

    /**
     * Renvoie la liste des Uv prerequise a une aptitude donnee
     * 
     * @return listeUV
     */
    public List<Uv> getListeUV() {
        return listeUV;
    }

    /**
     * Change la liste des Uv concernee par une aptitude donnee
     * 
     * @param listeUV
     *            la liste des UV requise pour une aptitude
     */
    public void setListeUV( List<Uv> listeUV ) {
        this.listeUV = listeUV;
    }

    /**
     * Ajoute une Uv prerequise a l'aptitude
     * 
     * @param u
     *            l'UV a ajouter
     */
    public void ajouterUv( Uv u ) {
        this.listeUV.add( u );
    }
}
