package cci.caos.repository;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Uv {

    private int      id;
    private String   nom;
    private int      duree;
    private int      nombrePlaceMin;
    private int      nombrePlaceMax;
    private List<Uv> listePrerequis;
    private String   lieu;

    /**
     * Constructeur vide
     */
    public Uv() {
    }

    /**
     * Constructeur qui initialise les variables d'instance (nom, duree,
     * nombrePlaceMin, nombrePlaceMax et lieu)
     * 
     * @param nom
     *            le nom de l'uv
     * @param duree
     *            la duree de l'uv
     * @param nombrePlaceMin
     *            le nombre de place minimale pour organiser une session de
     *            formation
     * @param nombrePlaceMax
     *            le nombre de place maximale pour organiser une session de
     *            formation
     * @param lieu
     *            le lieu de la session de formation
     */
    public Uv( String nom, int duree, int nombrePlaceMin, int nombrePlaceMax, String lieu ) {
        this.nom = nom;
        this.duree = duree;
        this.nombrePlaceMin = nombrePlaceMin;
        this.nombrePlaceMax = nombrePlaceMax;
        listePrerequis = new ArrayList<Uv>();
        this.lieu = lieu;
    }

    /* Accesseurs et Modificateurs */
    /**
     * Renvoie le nom de l'uv
     * 
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Change le nom de l'uv
     * 
     * @param nom
     *            le nom de l'uv
     */
    public void setNom( String nom ) {
        this.nom = nom;
    }

    /**
     * Renvoie la duree de l'uv
     * 
     * @return uv
     */
    public int getDuree() {
        return duree;
    }

    /**
     * Change la duree de l'uv
     * 
     * @param duree
     *            la duree de l'uv
     */
    public void setDuree( int duree ) {
        this.duree = duree;
    }

    /**
     * Renvoie le nombre de places minimum a une uv
     * 
     * @return nombrePlaceMin
     */
    public int getNombrePlaceMin() {
        return nombrePlaceMin;
    }

    /**
     * Change le nombre de places minimum a une uv
     * 
     * @param nombrePlaceMin
     *            le nombre de place minimale pour organiser une session de
     *            formation
     */
    public void setNombrePlaceMin( int nombrePlaceMin ) {
        this.nombrePlaceMin = nombrePlaceMin;
    }

    /**
     * Renvoie le nombre de places maximum a une uv
     * 
     * @return nombrePlaceMax
     */
    public int getNombrePlaceMax() {
        return nombrePlaceMax;
    }

    /**
     * Change le nombre de places maximum a une uv
     * 
     * @param nombrePlaceMax
     *            le nombre de place maximale pour organiser une session de
     *            formation
     */
    public void setNombrePlaceMax( int nombrePlaceMax ) {
        this.nombrePlaceMax = nombrePlaceMax;
    }

    /**
     * Renvoie le lieu de l'uv
     * 
     * @return lieu
     */
    public String getLieu() {
        return lieu;
    }

    /**
     * Change le lieu de l'uv
     * 
     * @param lieu
     *            le lieu pour organiser une session de formation
     */
    public void setLieu( String lieu ) {
        this.lieu = lieu;
    }

    /**
     * Renvoie la liste des uv prerequises de l'UV
     * 
     * @return listePrerequis
     */
    public List<Uv> getListePrerequis() {
        return listePrerequis;
    }

    /**
     * Change la liste des uv prerequises
     * 
     * @param listePrerequis
     *            la liste des prerequis a cette uv
     */
    public void setListePrerequis( List<Uv> listePrerequis ) {
        this.listePrerequis = listePrerequis;
    }

    /**
     * Renvoie l'id d'une uv
     * 
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Change l'id d'une uv
     * 
     * @param id
     *            l'id de l'uv
     */
    public void setId( int id ) {
        this.id = id;
    }

    /**
     * Ajout d'une Uv prerequise pour avoir l'UV
     * 
     * @param prerequis
     *            l'UV a ajouter dans la liste des prerequis
     */
    public void ajouterUv( Uv prerequis ) {
        this.listePrerequis.add( prerequis );
    }

    /**
     * Retrait d'une Uv prerequise pour avoir l'UV
     * 
     * @param u
     *            l'UV a retirer de la liste des prerequis
     */
    public void retirerUv( Uv u ) {
        this.listePrerequis.remove( u );
    }
}
