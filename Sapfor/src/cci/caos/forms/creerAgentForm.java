package cci.caos.forms;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import cci.caos.dao.AgentDao;
import cci.caos.repository.Agent;

public final class creerAgentForm {
    private static final String CHAMP_ID           = "id";
    private static final String CHAMP_NOM          = "nom";
    private static final String CHAMP_MDP          = "motdepasse";
    private static final String CHAMP_MATRICULE    = "matricule";
    private static final String CHAMP_GESTIONNAIRE = "gestionnaire";

    private AgentDao            agentDao;

    /**
     * Constructeur
     * @param agentDao
     */
    public creerAgentForm( AgentDao agentDao ) {
        this.agentDao = agentDao;
    }
    /**
     * Recupere les donnees de la jsp creerAgent pour creer un nouvel agent
     * @param request
     * @return l'agent cree
     */
    public Agent creerAgent( HttpServletRequest request ) {
        String id = getValeurChamp( request, CHAMP_ID );
        String nom = getValeurChamp( request, CHAMP_NOM );
        String motDePasse = getValeurChamp( request, CHAMP_MDP );
        String matricule = getValeurChamp( request, CHAMP_MATRICULE );
        String gestionnaire = getValeurChamp( request, CHAMP_GESTIONNAIRE );

        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( "SHA-256" );
        passwordEncryptor.setPlainDigest( true );
        String HashMotDePasse = passwordEncryptor.encryptPassword( motDePasse );

        Agent agent = new Agent();

        agent.setId( Integer.parseInt( id ) );
        agent.setNom( nom );
        agent.setMdp( HashMotDePasse );
        agent.setMatricule( matricule );

        // méthode qui doit renvoyer true ou false depuis une String
        // Attention Méthode qui ne marche pas !!!!
        if ( gestionnaire == "1" ) {
            agent.setGestionnaire( true );
        } else {
            agent.setGestionnaire( false );
        }

        // Vérification de la méthode agentDao existe(id)
        if ( !agentDao.existe( agent ) ) {
            // Vérification de la méthode agentDao creer(agent)
            agentDao.creer( agent );
            return agent;
        }
        // Verification de la méthode agentDao trouver(id)
        else {
            return agentDao.trouver( Integer.parseInt( id ) );
        }
    }

   
    /**
     * Verifie si les champs remplis ne sont pas nuls
     * @param request
     * @param nomChamp
     * @return null si champs non renseigne ou la valeur du champ
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }
    }

}
