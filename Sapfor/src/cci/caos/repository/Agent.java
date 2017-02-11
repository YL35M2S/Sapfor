package cci.caos.repository;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Agent {

    private String         nom;
    private String         mdp;
    private String         matricule;
    private List<Uv>       listeUV;
    private List<Aptitude> listeAptitude;
    private Boolean        gestionnaire;

    /* Constructeurs */
    public Agent() {
    }

    public Agent( String nom, String motdepasse, String matricule, Boolean gestionnaire ) {
        this.nom = nom;
        this.mdp = motdepasse;
        this.matricule = matricule;
        this.gestionnaire = gestionnaire;
        listeUV = new ArrayList<Uv>();
        listeAptitude = new ArrayList<Aptitude>();
    }

    /* Accesseurs et Modificateurs */

    public String getNom() {
        return nom;
    }

    public void setNom( String nom ) {
        this.nom = nom;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp( String mdp ) {
        this.mdp = mdp;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule( String matricule ) {
        this.matricule = matricule;
    }

    public List<Uv> getListeUV() {
        return listeUV;
    }

    public void setListeUV( List<Uv> listeUV ) {
        this.listeUV = listeUV;
    }

    /* Ajouter une Uv à un agent */
    public void ajouterUv( Uv u ) {
        this.listeUV.add( u );
    }

    public List<Aptitude> getListeAptitude() {
        return listeAptitude;
    }

    public void setListeAptitude( List<Aptitude> listeAptitude ) {
        this.listeAptitude = listeAptitude;
    }

    /* Ajoute une aptitude à un agent */
    public void ajouterAptitude( Aptitude ap ) {
        this.listeAptitude.add( ap );
    }

    public Boolean getGestionnaire() {
        return gestionnaire;
    }

    public void setGestionnaire( Boolean gestionnaire ) {
        this.gestionnaire = gestionnaire;
    }

}
