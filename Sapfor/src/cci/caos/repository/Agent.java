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

    /**
     * Constructeur vide
     */
    public Agent() {
    }
    /**
     * Constructeur initialisant les variables d'instance avec les paramètres d'entree
     * @param nom
     * @param motdepasse
     * @param matricule
     * @param gestionnaire
     */
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
    /**
     * Renvoie l'id de l'agent
     * @return id de l'agent
     */
    public int getId() {
        return id;
    }
    /**
     * Change l'id d'un agent
     * @param id
     */
    public void setId( int id ) {
        this.id = id;
    }
    /**
     * Renvoie le nom de l'agent
     * @return nom
     */
    public String getNom() {
        return nom;
    }
    /**
     * Change le nom d'un agent
     * @param nom
     */
    public void setNom( String nom ) {
        this.nom = nom;
    }
    /**
     * Renvoie le mot de passe d'un agent
     * @return mdp
     */
    public String getMdp() {
        return mdp;
    }
    /**
     * Permet de changer le mot de passe d'un agent
     * @param mdp
     */
    public void setMdp( String mdp ) {
        this.mdp = mdp;
    }
    /**
     * Donne le matricule de l'agent
     * @return matricule
     */
    public String getMatricule() {
        return matricule;
    }
    /**
     * Permet de changer le matricule d'un agent
     * @param matricule
     */
    public void setMatricule( String matricule ) {
        this.matricule = matricule;
    }
    /**
     * Retourne la liste des uv possedees par un agent
     * @return listeUV
     */
    public List<Uv> getListeUV() {
        return listeUV;
    }
    /**
     * Change la liste des uv possedees par un agent
     * @param listeUV
     */
    public void setListeUV( List<Uv> listeUV ) {
        this.listeUV = listeUV;
    }

    /**
     * ajout une uv à un agent
     * @param u
     */
    public void ajouterUv( Uv u ) {
        this.listeUV.add( u );
    }
    /**
     * Renvoie la liste des aptitudes d'un agent
     * @return
     */
    public List<Aptitude> getListeAptitude() {
        return listeAptitude;
    }
    /**
     * Change la liste des aptitudes
     * @param listeAptitude
     */
    public void setListeAptitude( List<Aptitude> listeAptitude ) {
        this.listeAptitude = listeAptitude;
    }

    /**
     * Ajout une aptitude a un agent
     * @param ap
     */
    public void ajouterAptitude( Aptitude ap ) {
        this.listeAptitude.add( ap );
    }
    /**
     * Permet de savoir si l'agent est un gestionnaire
     * @return Vrai si l'agent est gestionnaire, Faux sinon
     */
    public Boolean getGestionnaire() {
        return gestionnaire;
    }
    /**
     * Change la valeur booleenne du gestionnaire
     * @param gestionnaire
     */
    public void setGestionnaire( Boolean gestionnaire ) {
        this.gestionnaire = gestionnaire;
    }
    /**
     * redefinition de la methode compareTo qui prend un objet de type Agent en parametre
     * @param o
     * @return 0 si l'id de l'agent correspond a celle de l'instance, -1 sinon
     */
    @Override
    public int compareTo( Object o ) {
        if ( ( (Agent) o ).getId() == this.getId() ) {
            return 0;
        } else {
            return -1;
        }
    }

}
