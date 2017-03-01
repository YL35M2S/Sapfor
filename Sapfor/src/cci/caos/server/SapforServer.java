package cci.caos.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import cci.caos.beans.AgentConnection;
import cci.caos.beans.CandidatureGenerique;
import cci.caos.beans.SessionGenerique;
import cci.caos.dao.AbstractDAOFactory;
import cci.caos.dao.AgentDao;
import cci.caos.dao.AptitudeDao;
import cci.caos.dao.CandidatureDao;
import cci.caos.dao.SessionDao;
import cci.caos.dao.StageDao;
import cci.caos.dao.UvDao;
import cci.caos.repository.Agent;
import cci.caos.repository.Aptitude;
import cci.caos.repository.Candidature;
import cci.caos.repository.Session;
import cci.caos.repository.Stage;
import cci.caos.repository.Uv;

public class SapforServer {

    public static final int     typeDao = AbstractDAOFactory.DAO_FACTORY;
    private Map<String, Agent>  connexions;
    private static SapforServer sessionServer;

    public SapforServer() {
        connexions = new HashMap<String, Agent>();
        initializeServer();
    }

    /*
     * Permet d'obtenir la session actuelle du serveur
     * 
     * @return Retourne la session SapforServer actuelle
     */
    public static SapforServer getSessionServer() {
        if ( sessionServer == null ) {
            sessionServer = new SapforServer();
        }
        return sessionServer;
    }

    /*
     * Permet d'obtenir la session identifi�e par l'identifiant "id"
     * 
     * @Param id Identifiant de la session recherch�e
     * 
     * @return Retourne la session recherch�e
     */
    public Session getSessionById( int id ) {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        SessionDao sessionDao = adf.getSessionDao();
        return sessionDao.trouver( id );
    }

    /*
     * Permet d'obtenir l'UV identifi�e par l'identifiant "id"
     * 
     * @Param id Identifiant de l'uv recherch�e
     * 
     * @return Retourne l'uv recherch�e
     */
    public Uv getUvById( int id ) {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        UvDao uvDao = adf.getUvDao();
        return uvDao.trouver( id );
    }

    /*
     * Permet de v�rifier que l'agent identifi� par "uuid" est connect�
     * 
     * @Param uuid Identifiant unique de l'agent
     * 
     * @return Retourne true si l'agent est identifi�
     */
    public boolean isConnectedByUUID( String uuid ) {
        boolean estConnecte = false;
        for ( Map.Entry<String, Agent> entry : connexions.entrySet() ) {
            if ( entry.getKey().compareTo( uuid ) == 0 ) {
                estConnecte = true;
                break;
            }
        }
        return estConnecte;
    }

    /*
     * Permet d'obtenir l'agent identifié par "uuid"
     * 
     * @Param uuid Identifiant unique de l'agent
     * 
     * @return Retourne l'agent identifié par uuid
     */
    public Agent getAgentByUUID( String uuid ) {
        return connexions.get( uuid );
    }

    /*
     * Permet d'obtenir l'agent identifié par "id"
     * 
     * @Param id Identifiant de l'agent
     * 
     * @return Retourne l'agent identifié par "id"
     */
    public Agent getAgentById( int id ) {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        AgentDao agentDao = adf.getAgentDao();
        return agentDao.trouver( id );
    }

    /*
     * Permet d'obtenir le stage identifi� par "id"
     * 
     * @Param id Identifiant du stage
     * 
     * @return Retourne le stage identifi� par "id"
     */
    public Stage getStageById( int id ) {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        StageDao stageDao = adf.getStageDao();
        return stageDao.trouver( id );
    }

    public Aptitude getAptitudeById( int id ) {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        AptitudeDao aptitudeDao = adf.getAptitudeDao();
        return aptitudeDao.trouver( id );

    }

    public List<Uv> getListeUvFormateur() {
        List<Uv> ListeUvFormateur = new ArrayList<Uv>();
        // ListeUvFormateur.add( uvs.get( 7 ) );
        // ListeUvFormateur.add( uvs.get( 8 ) );
        return ListeUvFormateur;
    }

    public List<CandidatureGenerique> getSessionsAccessibles( String uuid ) {
        SimpleDateFormat simpDate = new SimpleDateFormat( "dd/MM/yyyy" );

        Agent agent = connexions.get( uuid );
        List<CandidatureGenerique> CandidaturesSessionsAccessibles = new ArrayList<CandidatureGenerique>();
        List<Uv> listeUvRequiseFormateur = getListeUvFormateur();

        // Pour chaque session existante sur la BDD
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        SessionDao sessionDao = adf.getSessionDao();
        CandidatureDao candidatureDao = adf.getCandidatureDao();
        for ( Session s : sessionDao.listerToutes() ) {
            List<Uv> listeUvRequiseStagiaire = s.getUv().getListePrerequis();
            // nombre d'UV requises en tant que Candidat pour cette session
            int nombreUvRequisesStagiaire = listeUvRequiseStagiaire.size();
            // futur nombre de ces UV en tant que Candidat possédées par
            // l'agent
            int nombreUvPossedeStagiaire = 0;

            int nombreUvRequiseFormateur = listeUvRequiseFormateur.size();
            int nombreUvPossedeFormateur = 0;
            boolean AgentPossedeUvSession = false;

            Iterator<Uv> itListeUvRequisesStagiaire = listeUvRequiseStagiaire.iterator();
            Iterator<Uv> itListeUvRequisesFormateur = listeUvRequiseFormateur.iterator();

            // Regarde si l'agent a les UV nécessaires pour être stagiaire
            while ( itListeUvRequisesStagiaire.hasNext() ) {
                Uv UvRequiseStagiaire = itListeUvRequisesStagiaire.next();
                Iterator<Uv> itListeUvAgent = agent.getListeUV().iterator();

                while ( itListeUvAgent.hasNext() ) {
                    Uv UvAgent = itListeUvAgent.next();

                    if ( UvRequiseStagiaire.getNom().compareTo( UvAgent.getNom() ) == 0 ) {
                        nombreUvPossedeStagiaire++; // Il possède l'UV
                        break;
                    }
                }
            }
            // Regarde si l'agent a les UV nécessaires pour être formateur
            while ( itListeUvRequisesFormateur.hasNext() ) {
                Uv UvRequiseFormateur = itListeUvRequisesFormateur.next();
                Iterator<Uv> itListeUvAgent = agent.getListeUV().iterator();
                while ( itListeUvAgent.hasNext() ) {
                    Uv UvAgent = itListeUvAgent.next();

                    if ( UvAgent.getNom().compareTo( s.getUv().getNom() ) == 0 ) {
                        AgentPossedeUvSession = true;
                    }

                    if ( UvRequiseFormateur.getNom().compareTo( UvAgent.getNom() ) == 0 ) {
                        nombreUvPossedeFormateur++;
                    }
                }
            }

            if ( !candidatureDao.existe( agent.getId(), s.getId() ) ) {

                // Ajout candidature en tant que stagiaire
                if ( nombreUvRequisesStagiaire == nombreUvPossedeStagiaire && !AgentPossedeUvSession ) {

                    CandidatureGenerique candidature = new CandidatureGenerique( agent.getId(), false, -2, s.getId(),
                            s.getNom(), simpDate.format( s.getDateDebut() ), simpDate.format( s.getDateFin() ),
                            s.getUv().getNom(), s.getStage().getNom() );
                    CandidaturesSessionsAccessibles.add( candidature );
                }

                // Ajout candidature en tant que formateur
                if ( nombreUvRequiseFormateur == nombreUvPossedeFormateur & AgentPossedeUvSession )

                {
                    CandidatureGenerique candidature = new CandidatureGenerique( agent.getId(), true, -2, s.getId(),
                            s.getNom(), simpDate.format( s.getDateDebut() ), simpDate.format( s.getDateFin() ),
                            s.getUv().getNom(), s.getStage().getNom() );
                    CandidaturesSessionsAccessibles.add( candidature );
                }
            }
        }
        return CandidaturesSessionsAccessibles;
    }

    public List<Session> getListeSessionsFermees() {
        List<Session> listeFermees = new ArrayList<Session>();

        // Pour chaque session existante sur la BDD
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        SessionDao sessionDao = adf.getSessionDao();
        for ( Session s : sessionDao.listerToutes() ) {
            if ( !s.isOuverteInscription() ) {
                listeFermees.add( s );
            }
        }
        return listeFermees;
    }

    public List<Session> getListeSessionsOuvertes() {
        List<Session> listeOuvertes = new ArrayList<Session>();

        // Pour chaque session existante sur la BDD
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        SessionDao sessionDao = adf.getSessionDao();
        for ( Session s : sessionDao.listerToutes() ) {
            if ( s.isOuverteInscription() ) {
                listeOuvertes.add( s );
            }
        }
        return listeOuvertes;
    }

    public AgentConnection getConnexionAgent( String matricule, String password ) {
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( "SHA-256" );
        passwordEncryptor.setPlainDigest( true );

        // Pour chaque agent existant sur la BDD
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        AgentDao agentDao = adf.getAgentDao();
        for ( Agent a : agentDao.listerTous() ) {
            if ( a.getMatricule().compareTo( matricule ) == 0 ) {
                if ( passwordEncryptor.checkPassword( password, a.getMdp() ) ) {
                    String uuid = UUID.randomUUID().toString();
                    connexions.put( uuid, a );
                    AgentConnection ag = new AgentConnection();
                    ag.setMatricule( matricule );
                    ag.setNom( a.getNom() );
                    ag.setUuid( uuid );
                    ag.setGestionnaire( a.getGestionnaire() );
                    return ag; // Si la connexion est r�ussie
                }
            }
        }
        return null; // Si la connexion a echouee
    }

    /**
     * Retourne la liste des candidatures pour une session donnee
     * 
     * @param idSession
     *            id de la session recherchée
     * @return Liste des candidatures pour une session donnée
     */
    public List<CandidatureGenerique> getListeCandidatures( int idSession ) {
        List<CandidatureGenerique> listeCandidaturesGeneriques = new ArrayList<CandidatureGenerique>();
        List<Candidature> listeCandidatures;

        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        CandidatureDao candidatureDao = adf.getCandidatureDao();
        listeCandidatures = candidatureDao.listerCandidaturesParSession( idSession );
        for ( Candidature c : listeCandidatures ) {
            listeCandidaturesGeneriques.add( candidatureToCandidatureGenerique( c ) );
        }
        return listeCandidaturesGeneriques;
    }

    /**
     * Retourne la liste des candidatures pour un agent donne
     * 
     * @param idAgent
     *            id de l'agent recherche
     * @return Liste des candidatures pour un agent donne
     */
    public List<CandidatureGenerique> getListeSession( String uuid ) {
        List<CandidatureGenerique> listeCandidaturesGeneriques = new ArrayList<CandidatureGenerique>();
        List<Candidature> listeCandidatures;
        Agent agent = getAgentByUUID( uuid );
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        CandidatureDao candidatureDao = adf.getCandidatureDao();
        listeCandidatures = candidatureDao.listerCandidaturesParAgent( agent.getId() );
        for ( Candidature c : listeCandidatures ) {
            listeCandidaturesGeneriques.add( candidatureToCandidatureGenerique( c ) );
        }
        return listeCandidaturesGeneriques;
    }
    
    /**
     * Retourne la liste des agents pour une session donnée
     * 
     * @param idAgent
     *            id de la session recherchée
     * @return Liste des agents pour une session donnée
     */
    public List<Candidature> getListeCandidats( int idSession ) {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        CandidatureDao candidatureDao = adf.getCandidatureDao();
        return candidatureDao.listerCandidaturesParSession( idSession );
    }

    // CONSERVER AU CAS OU
    /*
     * List<Session> listeSessionsCandidat = new ArrayList<Session>(); Agent
     * agentCherche = getAgentByUUID( uuid );
     * 
     * for ( Map.Entry<Integer, Candidature> entry : candidatures.entrySet() ) {
     * if ( entry.getValue().getAgent().compareTo( agentCherche ) == 0 ) {
     * listeSessionsCandidat.add( entry.getValue().getSession() ); } } return
     * listeSessionsCandidat; }
     */

    public boolean modifierListeCandidats( int idSession, List<Candidature> listeCandidature ) {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        CandidatureDao candidatureDao = adf.getCandidatureDao();
        candidatureDao.mettreAJourCandidatureASession( idSession, listeCandidature );
        return true;
    }

    public boolean retirerCandidature( int idAgent, int idSession ) {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        CandidatureDao candidatureDao = adf.getCandidatureDao();
        candidatureDao.supprimerCandidature( idAgent, idSession );
        return true;
    }

    public List<SessionGenerique> getListeSessionsGeneriques() {
        List<SessionGenerique> listeSessionsGeneriques = new ArrayList<SessionGenerique>();

        // Pour chaque session existante sur la BDD
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        SessionDao sessionDao = adf.getSessionDao();
        for ( Session s : sessionDao.listerToutes() ) {
            if ( s.isOuverteInscription() ) {
                listeSessionsGeneriques.add( sessionToSessionGenerique( s ) );
            }
        }
        return listeSessionsGeneriques;
    }

    public boolean deposerCandidature( int idAgent, int idSession, boolean estFormateur ) {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );
        CandidatureDao candidatureDao = adf.getCandidatureDao();
        /* Test si la candidature existe deja */
        if ( !candidatureDao.existe( idAgent, idSession ) ) {
            candidatureDao.creer( new Candidature( SapforServer.getSessionServer().getAgentById( idAgent ), -2,
                    estFormateur, SapforServer.getSessionServer().getSessionById( idSession ) ) );
        }
        return true;
    }

    @SuppressWarnings( "deprecation" )
    public CandidatureGenerique candidatureToCandidatureGenerique( Candidature candidature ) {
        SimpleDateFormat simpDate = new SimpleDateFormat( "dd/MM/yyyy" );
        return new CandidatureGenerique(
                candidature.getAgent().getId(),
                candidature.isEstFormateur(),
                candidature.getStatutCandidature(),
                candidature.getSession().getId(),
                candidature.getSession().getNom(),
                simpDate.format( candidature.getSession().getDateDebut() ),
                simpDate.format( candidature.getSession().getDateFin() ),
                candidature.getSession().getUv().getNom(),
                candidature.getSession().getStage().getNom() );
    }

    @SuppressWarnings( "deprecation" )
    public SessionGenerique sessionToSessionGenerique( Session session ) {
        SimpleDateFormat simpDate = new SimpleDateFormat( "dd/MM/yyyy" );
        return new SessionGenerique(
                session.getId(),
                session.getNom(),
                simpDate.format( session.getDateDebut() ),
                simpDate.format( session.getDateFin() ),
                session.getUv().getNom(),
                session.getStage().getNom() );
    }

    @SuppressWarnings( "deprecation" )
    public void initializeServer() {
        // AbstractDAOFactory adf = AbstractDAOFactory.getFactory( typeDao );

        /* Creation des instances d'UV Simple + DAO */
        Uv uv1 = new Uv( "UV_INC1", 5, 3, 12, "Rennes" );
        Uv uv2 = new Uv( "UV_FDF1", 10, 3, 12, "Rennes" );
        Uv uv3 = new Uv( "UV_SAR1", 5, 3, 12, "Rennes" );
        /*
         * UvDao uvDao = adf.getUvDao(); uv1.setId( uvDao.creer( uv1 ) );
         * uv2.setId( uvDao.creer( uv2 ) ); uv3.setId( uvDao.creer( uv3 ) );
         */
        uv1.setId( 1 );
        uv2.setId( 2 );
        uv3.setId( 2 );

        /* Mise a jour d'instances d'UV avec prerequis + DAO */
        uv1.ajouterUv( uv2 );
        uv1.ajouterUv( uv3 );
        uv2.ajouterUv( uv3 );
        // uvDao.mettreAJour( uv1 );
        // uvDao.mettreAJour( uv2 );

        /* Creation des instances d'UV avec prerequis + DAO */
        Uv uv4 = new Uv( "UV_WJK2", 5, 3, 12, "Rennes" );
        Uv uv5 = new Uv( "UV_WJK1", 5, 3, 12, "Rennes" );
        Uv uv6 = new Uv( "UV_AJH", 5, 3, 12, "Rennes" );
        Uv uv7 = new Uv( "UV_FORM", 5, 3, 12, "Rennes" ); // Uv requise pour
                                                          // etre formateur
        Uv uv8 = new Uv( "UV_FORM", 5, 3, 12, "Rennes" ); // Uv requise pour
                                                          // etre formateur
        uv3.ajouterUv( uv6 );
        uv4.ajouterUv( uv5 );
        /*
         * uv4.setId( uvDao.creer( uv4 ) ); uv5.setId( uvDao.creer( uv5 ) );
         * uv6.setId( uvDao.creer( uv6 ) ); uv7.setId( uvDao.creer( uv7 ) );
         * uv8.setId( uvDao.creer( uv8 ) );
         */
        uv4.setId( 4 );
        uv5.setId( 5 );
        uv6.setId( 6 );
        uv7.setId( 7 );
        uv8.setId( 8 );

        // Creation d'aptitude simple + DAO
        Aptitude apt1 = new Aptitude( "APT1" );
        Aptitude apt2 = new Aptitude( "APT2" );
        /*
         * AptitudeDao aptitudeDao = adf.getAptitudeDao(); apt1.setId(
         * aptitudeDao.creer( apt1 ) ); apt2.setId( aptitudeDao.creer( apt2 ) );
         */
        apt1.setId( 1 );
        apt2.setId( 2 );

        // Mise a jour d'aptitude avec prerequis + DAO
        apt1.ajouterUv( uv1 );
        apt1.ajouterUv( uv2 );
        apt2.ajouterUv( uv1 );
        // aptitudeDao.mettreAJour( apt1 );

        // Creation d'aptitude avec prerequis + DAO
        Aptitude apt3 = new Aptitude( "APT3" );
        apt3.ajouterUv( uv4 );
        apt3.ajouterUv( uv5 );
        // apt3.setId( aptitudeDao.creer( apt3 ) );
        apt3.setId( 3 );

        // Creation d'agents simple + DAO
        Agent a1 = new Agent( "ATREUILLIER", "mdp", "19041975", true );
        Agent a2 = new Agent( "LTREUILLIER", "mdp", "15122010", false );
        /* Sauvegarde des Agents */
        /*
         * AgentDao agentDao = adf.getAgentDao(); a1.setId( agentDao.creer( a1 )
         * ); a2.setId( agentDao.creer( a2 ) );
         */
        a1.setId( 1 );
        a2.setId( 2 );

        // Mise a jour d'agents avec Uv + DAO
        a1.ajouterUv( uv6 );
        a2.ajouterUv( uv3 );
        // agentDao.mettreAJour( a1 );
        // agentDao.mettreAJour( a2 );

        // Creation d'agents avec Uv + DAO
        Agent a3 = new Agent( "NTREUILLIER", "mdp", "09102013", false );
        Agent a4 = new Agent( "FDESCAVES", "mdp", "06091991", false );
        Agent a5 = new Agent( "MDESCAVES", "max", "06091990", false );
        a3.ajouterUv( uv2 );
        a3.ajouterUv( uv3 );
        /*
         * a3.setId( agentDao.creer( a3 ) ); a4.setId( agentDao.creer( a4 ) );
         * a5.setId( agentDao.creer( a5 ) );
         */
        a3.setId( 3 );
        a4.setId( 4 );
        a5.setId( 5 );

        // Creation de Stage Simple + DAO
        Stage stg1 = new Stage( "fevrier1" );
        Stage stg2 = new Stage( "fevrier2" );
        Stage stg3 = new Stage( "mars1" );
        /*
         * StageDao stageDao = adf.getStageDao(); stg1.setId( stageDao.creer(
         * stg1 ) ); stg2.setId( stageDao.creer( stg2 ) ); stg3.setId(
         * stageDao.creer( stg3 ) );
         */
        stg1.setId( 1 );
        stg2.setId( 2 );
        stg3.setId( 3 );

        // Mise à jour de Stage Simple + DAO
        stg1.setNom( "fevrier3" );
        // stageDao.mettreAJour( stg1 );

        // Creation de session simple + DAO
        Session s1 = new Session( "INC1", new java.sql.Date( 2017, 02, 06 ), new java.sql.Date( 2017, 02, 10 ), true,
                uv1, stg1 );
        Session s2 = new Session( "FDF1", new java.sql.Date( 2017, 02, 06 ), new java.sql.Date( 2017, 02, 17 ), true,
                uv2, stg2 );
        Session s3 = new Session( "SAR1", new java.sql.Date( 2017, 01, 30 ), new java.sql.Date( 2017, 02, 03 ), true,
                uv3, stg3 );
        Session s4 = new Session( "SAR2", new java.sql.Date( 2017, 01, 30 ), new java.sql.Date( 2017, 02, 03 ), true,
                uv3, stg3 );

        /* Sauvegarde des Sessions */
        /*
         * SessionDao sessionDao = adf.getSessionDao(); s1.setId(
         * sessionDao.creer( s1 ) ); s2.setId( sessionDao.creer( s2 ) );
         * s3.setId( sessionDao.creer( s3 ) );
         */
        s1.setId( 1 );
        s2.setId( 2 );
        s3.setId( 3 );

        // Mise a jour de session simple + DAO
        s1.setStage( stg2 );
        // sessionDao.mettreAJour( s1 );

        // Creation de candidatures simples + DAO
        Candidature cand1 = new Candidature( a2, -2, false, s1 );
        Candidature cand2 = new Candidature( a3, -2, false, s1 );
        /* Sauvegarde des Candidatures */
        /*
         * CandidatureDao candidatureDao = adf.getCandidatureDao();
         * candidatureDao.creer( cand1 ); candidatureDao.creer( cand2 );
         */
        // Mise a jour de candidature simple + DAO
        cand1.setSession( s2 );
        // sessionDao.mettreAJour( s2 );

        // Ajout des tokens de connexions
        connexions.put( "19041975", a1 );
        connexions.put( "15122010", a2 );
        connexions.put( "09102013", a3 );
        connexions.put( "06091991", a4 );
    }
}
