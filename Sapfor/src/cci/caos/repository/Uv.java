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

    /* Constructeurs */

    public Uv() {
    }

    public Uv( String nom, int duree, int nombrePlaceMin, int nombrePlaceMax, String lieu ) {
        this.nom = nom;
        this.duree = duree;
        this.nombrePlaceMin = nombrePlaceMin;
        this.nombrePlaceMax = nombrePlaceMax;
        listePrerequis = new ArrayList<Uv>();
        this.lieu = lieu;
    }

    /* Accesseurs et Modificateurs */

    public String getNom() {
        return nom;
    }

    public void setNom( String nom ) {
        this.nom = nom;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree( int duree ) {
        this.duree = duree;
    }

    public int getNombrePlaceMin() {
        return nombrePlaceMin;
    }

    public void setNombrePlaceMin( int nombrePlaceMin ) {
        this.nombrePlaceMin = nombrePlaceMin;
    }

    public int getNombrePlaceMax() {
        return nombrePlaceMax;
    }

    public void setNombrePlaceMax( int nombrePlaceMax ) {
        this.nombrePlaceMax = nombrePlaceMax;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu( String lieu ) {
        this.lieu = lieu;
    }

    public List<Uv> getListePrerequis() {
        return listePrerequis;
    }

    public void setListePrerequis( List<Uv> listePrerequis ) {
        this.listePrerequis = listePrerequis;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    /* ajout d'une Uv prérequise pour avoir l'UV */
    public void ajouterUv( Uv prerequis ) {
        this.listePrerequis.add( prerequis );
    }

    /* retrait d'une Uv prérequise pour avoir l'Uv */
    public void retirerUv( Uv u ) {
        this.listePrerequis.remove( u );
    }
}
