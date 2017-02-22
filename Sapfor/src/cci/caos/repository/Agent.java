package cci.caos.repository;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

@XmlRootElement
public class Agent implements Comparable {

    private int            id;
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
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( "SHA-256" );
        passwordEncryptor.setPlainDigest( true );
        String HashMotDePasse = passwordEncryptor.encryptPassword( motdepasse );
        this.nom = nom;
        this.mdp = HashMotDePasse;
        this.matricule = matricule;
        this.gestionnaire = gestionnaire;
        this.listeUV = new ArrayList<Uv>();
        this.listeAptitude = new ArrayList<Aptitude>();
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

    /* Ajouter une Uv � un agent */
    public void ajouterUv( Uv u ) {
        this.listeUV.add( u );
    }

    public List<Aptitude> getListeAptitude() {
        return listeAptitude;
    }

    public void setListeAptitude( List<Aptitude> listeAptitude ) {
        this.listeAptitude = listeAptitude;
    }

    /* Ajoute une aptitude � un agent */
    public void ajouterAptitude( Aptitude ap ) {
        this.listeAptitude.add( ap );
    }

    public Boolean getGestionnaire() {
        return gestionnaire;
    }

    public void setGestionnaire( Boolean gestionnaire ) {
        this.gestionnaire = gestionnaire;
    }

    @Override
    public int compareTo( Object o ) {
        if ( ( (Agent) o ).getId() == this.getId() ) {
            return 0;
        } else {
            return -1;
        }
    }

}
