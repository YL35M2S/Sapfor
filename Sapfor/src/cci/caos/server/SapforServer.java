package cci.caos.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import cci.caos.beans.AgentConnection;
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

    private Map<String, Agent>        connexions;
    private Map<Integer, Session>     sessions;
    private Map<Integer, Uv>          uvs;
    private Map<Integer, Agent>       agents;
    private Map<Integer, Stage>       stages;
    private Map<Integer, Aptitude>    aptitudes;
    private Map<Integer, Candidature> candidatures;

    private Map<String, Agent>        uuid_agents;
    private static SapforServer       sessionServer;

    public SapforServer() {
        connexions = new HashMap<String, Agent>();
        sessions = new HashMap<Integer, Session>();
        uvs = new HashMap<Integer, Uv>();
        agents = new HashMap<Integer, Agent>();
        stages = new HashMap<Integer, Stage>();
        aptitudes = new HashMap<Integer, Aptitude>();
        candidatures = new HashMap<Integer, Candidature>();

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
     * Permet d'obtenir la session identifiée par l'identifiant "id"
     * 
     * @Param id Identifiant de la session recherchée
     * 
     * @return Retourne la session recherchée
     */
    public Session getSessionById( int id ) {
        Session s = null;
        for ( Map.Entry<Integer, Session> entry : sessions.entrySet() ) {
            if ( (int) ( entry.getKey() ) == id ) {
                s = entry.getValue();
            }
        }
        return s;
    }

    /*
     * Permet d'obtenir l'UV identifiée par l'identifiant "id"
     * 
     * @Param id Identifiant de l'uv recherchée
     * 
     * @return Retourne l'uv recherchée
     */
    public Uv getUvById( int id ) {
        Uv u = null;
        for ( Map.Entry<Integer, Uv> entry : uvs.entrySet() ) {
            if ( (int) ( entry.getKey() ) == id ) {
                u = entry.getValue();
            }
        }
        return u;
    }

    /*
     * Permet de vérifier que l'agent identifié par "uuid" est connecté
     * 
     * @Param uuid Identifiant unique de l'agent
     * 
     * @return Retourne true si l'agent est identifié
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
        Agent a = null;
        for ( Map.Entry<String, Agent> entry : connexions.entrySet() ) {
            if ( entry.getKey().compareTo( uuid ) == 0 ) {
                a = entry.getValue();
            }
        }
        return a;
    }

    /*
     * Permet d'obtenir l'agent identifié par "id"
     * 
     * @Param id Identifiant de l'agent
     * 
     * @return Retourne l'agent identifié par "id"
     */
    public Agent getAgentById( int id ) {
        Agent a = null;
        for ( Map.Entry<Integer, Agent> entry : agents.entrySet() ) {
            if ( entry.getKey() == id ) {
                a = entry.getValue();
            }
        }
        return a;
    }

    /*
     * Permet d'obtenir le stage identifié par "id"
     * 
     * @Param id Identifiant du stage
     * 
     * @return Retourne le stage identifié par "id"
     */
    public Stage getStageById( int id ) {
        Stage s = null;
        for ( Map.Entry<Integer, Stage> entry : stages.entrySet() ) {
            if ( entry.getKey() == id ) {
                s = entry.getValue();
            }
        }
        return s;
    }

    public List<Uv> getListeUvFormateur() {
        List<Uv> ListeUvFormateur = new ArrayList<Uv>();
        ListeUvFormateur.add( uvs.get( 7 ) );
        ListeUvFormateur.add( uvs.get( 8 ) );
        return ListeUvFormateur;
    }

    public List<Candidature> getSessionsAccessibles( String uuid ) {
        Agent agent = connexions.get( uuid );
        List<Candidature> CandidaturesSessionsAccessibles = new ArrayList<Candidature>();
        List<Uv> listeUvRequiseFormateur = getListeUvFormateur();

        // Pour chaque session existante sur le serveur
        for ( Integer mapKey : sessions.keySet() ) {
            List<Uv> listeUvRequiseStagiaire = sessions.get( mapKey ).getUv().getListePrerequis();
            // nombre d'UV requises en tant que Candidat pour cette session
            int nombreUvRequisesStagiaire = listeUvRequiseStagiaire.size();
            // futur nombre de ces UV en tant que Candidat possÃ©dÃ©es par
            // l'agent
            int nombreUvPossedeStagiaire = 0;

            int nombreUvRequiseFormateur = listeUvRequiseFormateur.size();
            int nombreUvPossedeFormateur = 0;
            boolean AgentPossedeUvSession = false;

            Iterator<Uv> itListeUvRequisesStagiaire = listeUvRequiseStagiaire.iterator();
            Iterator<Uv> itListeUvRequisesFormateur = listeUvRequiseFormateur.iterator();

            // Regarde si l'agent a les UV nÃ©cessaires pour Ãªtre stagiaire
            while ( itListeUvRequisesStagiaire.hasNext() ) {
                Uv UvRequiseStagiaire = itListeUvRequisesStagiaire.next();
                Iterator<Uv> itListeUvAgent = agent.getListeUV().iterator();

                while ( itListeUvAgent.hasNext() ) {
                    Uv UvAgent = itListeUvAgent.next();

                    if ( UvRequiseStagiaire.getNom().compareTo( UvAgent.getNom() ) == 0 ) {
                        nombreUvPossedeStagiaire++; // Il possÃ¨de l'UV
                        break;
                    }
                }
            }
            // Regarde si l'agent a les UV nÃ©cessaires pour Ãªtre formateur
            while ( itListeUvRequisesFormateur.hasNext() ) {
                Uv UvRequiseFormateur = itListeUvRequisesFormateur.next();
                Iterator<Uv> itListeUvAgent = agent.getListeUV().iterator();
                while ( itListeUvAgent.hasNext() ) {
                    Uv UvAgent = itListeUvAgent.next();

                    if ( UvAgent.getNom().compareTo( sessions.get( mapKey ).getUv().getNom() ) == 0 ) {
                        AgentPossedeUvSession = true;
                    }

                    if ( UvRequiseFormateur.getNom().compareTo( UvAgent.getNom() ) == 0 ) {
                        nombreUvPossedeFormateur++;
                    }
                }
            }

            // Ajout candidature en tant que stagiaire
            if ( nombreUvRequisesStagiaire == nombreUvPossedeStagiaire && !AgentPossedeUvSession ) {
                Candidature candidature = new Candidature( agent, -2, false, sessions.get( mapKey ) );
                CandidaturesSessionsAccessibles.add( candidature );
            }

            // Ajout candidature en tant que formateur
            if ( nombreUvRequiseFormateur == nombreUvPossedeFormateur & AgentPossedeUvSession ) {
                Candidature candidature = new Candidature( agent, -2, true, sessions.get( mapKey ) );
                CandidaturesSessionsAccessibles.add( candidature );
            }
        }
        return CandidaturesSessionsAccessibles;
    }

    public List<Session> getListeSessionsFermees() {
        List<Session> listeFermees = new ArrayList<Session>();
        for ( Map.Entry<Integer, Session> entry : sessions.entrySet() ) {
            if ( entry.getValue().isOuverteInscription() == false ) {
                listeFermees.add( entry.getValue() );
            }
        }
        return listeFermees;
    }

    public List<Session> getListeSessionsOuvertes() {
        List<Session> listeOuvertes = new ArrayList<Session>();
        for ( Map.Entry<Integer, Session> entry : sessions.entrySet() ) {
            if ( entry.getValue().isOuverteInscription() == true ) {
                listeOuvertes.add( entry.getValue() );
            }
        }
        return listeOuvertes;
    }

    public AgentConnection getConnexionAgent( String matricule, String password ) {
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( "SHA-256" );
        passwordEncryptor.setPlainDigest( true );

        for ( Map.Entry<Integer, Agent> entry : agents.entrySet() ) {
            if ( entry.getValue().getMatricule().compareTo( matricule ) == 0 ) {
                if ( passwordEncryptor.checkPassword( password, entry.getValue().getMdp() ) ) {
                    String uuid = UUID.randomUUID().toString();
                    connexions.put( uuid, entry.getValue() );
                    AgentConnection ag = new AgentConnection();
                    ag.setMatricule( matricule );
                    ag.setNom( entry.getValue().getNom() );
                    ag.setUuid( uuid );
                    return ag; // Si la connexion est réussie
                }
            }
        }
        return null; // Si la connexion a échouée
    }

    public List<Candidature> getListeCandidatures( int idSession ) {
        List<Candidature> listeCandidatures = new ArrayList<Candidature>();
        for ( Map.Entry<Integer, Candidature> entry : candidatures.entrySet() ) {
            if ( entry.getValue().getSession().getId() == idSession ) {
                listeCandidatures.add( entry.getValue() );
            }
        }
        return listeCandidatures;
    }

    /*
     * @param uuid l'uuid de l'agent
     * 
     * @return la liste des sessions dans lesquelles l'agent Ã  candidater
     * 
     * Attention Agent compareTo(a) compare uniquement les id des agents.
     */
    public List<Session> getListeSession( String uuid ) {
        List<Session> listeSessionsCandidat = new ArrayList<Session>();
        Agent agentCherche = getAgentByUUID( uuid );

        for ( Map.Entry<Integer, Candidature> entry : candidatures.entrySet() ) {
            if ( entry.getValue().getAgent().compareTo( agentCherche ) == 0 ) {
                listeSessionsCandidat.add( entry.getValue().getSession() );
            }
        }
        return listeSessionsCandidat;
    }

    public boolean modifierListeCandidats( int idSession, List<Candidature> listeCandidature ) {
        Iterator<Candidature> itCandidature = listeCandidature.iterator();

        for ( Map.Entry<Integer, Candidature> entry : candidatures.entrySet() ) {
            while ( itCandidature.hasNext() ) {
                Candidature CandidatureCourante = itCandidature.next();
                if ( CandidatureCourante.getAgent().compareTo( entry.getValue().getAgent() ) == 0 &&
                        CandidatureCourante.getSession().getId() == entry.getValue().getSession().getId() ) {
                    candidatures.put( entry.getKey(), CandidatureCourante ); // Remplacement
                                                                             // de
                                                                             // la
                                                                             // candidature
                }
            }
        }
        // Temporaire
        return true;
    }

    public boolean retirerCandidature( int idAgent, int idSession ) {
        for ( Map.Entry<Integer, Candidature> entry : candidatures.entrySet() ) {
            if ( entry.getValue().getAgent().getId() == idAgent ) {
                if ( entry.getValue().getSession().getId() == idSession ) {
                    candidatures.remove( entry.getKey() );
                    return true;
                }
            }
        }
        return false;
    }

    public List<SessionGenerique> getListeSessionsGeneriques() {
        List<SessionGenerique> listeSessionsGeneriques = new ArrayList<SessionGenerique>();

        for ( Map.Entry<Integer, Session> entry : sessions.entrySet() ) {
            if ( entry.getValue().isOuverteInscription() ) {
                listeSessionsGeneriques.add( sessionToSessionGenerique( entry.getValue() ) );
            }
        }
        return listeSessionsGeneriques;
    }

    public boolean deposerCandidature( int idAgent, int idSession, boolean estFormateur ) {
        boolean alreadyExist = false;
        for ( Map.Entry<Integer, Candidature> entry : candidatures.entrySet() ) {
            if ( entry.getValue().getAgent().getId() == idAgent ) {
                if ( entry.getValue().getSession().getId() == idSession ) {
                    alreadyExist = true;
                }
            }
        }
        if ( !alreadyExist ) {
            int cle = ThreadLocalRandom.current().nextInt( 1, 100000 + 1 );
            // Assure l'unicitÃ© de la clÃ© dans la hashMap
            while ( candidatures.containsKey( cle ) ) {
                cle = ThreadLocalRandom.current().nextInt( 1, 100000 + 1 );
            }
            candidatures.put( cle,
                    new Candidature( getAgentById( idAgent ), -2, estFormateur, getSessionById( idSession ) ) );
            return true;
        } else {
            return false;
        }
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
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( AbstractDAOFactory.DAO_FACTORY );

        /* Creation des instances d'UV Simple + DAO */
        Uv uv1 = new Uv( "UV_INC1", 5, 3, 12, "Rennes" );
        Uv uv2 = new Uv( "UV_FDF1", 10, 3, 12, "Rennes" );
        Uv uv3 = new Uv( "UV_SAR1", 5, 3, 12, "Rennes" );
        uvs.put( 1, uv1 );
        uvs.put( 2, uv2 );
        uvs.put( 3, uv3 );
        UvDao uvDao = adf.getUvDao();
        uv1.setId( uvDao.creer( uv1 ) );
        uv2.setId( uvDao.creer( uv2 ) );
        uv3.setId( uvDao.creer( uv3 ) );

        /* Mise a jour d'instances d'UV avec prerequis + DAO */
        uv1.ajouterUv( uv2 );
        uv1.ajouterUv( uv3 );
        uv2.ajouterUv( uv3 );
        uvDao.mettreAJour( uv1 );
        uvDao.mettreAJour( uv2 );

        /* Creation des instances d'UV avec prerequis + DAO */
        Uv uv4 = new Uv( "UV_WJK2", 5, 3, 12, "Rennes" );
        Uv uv5 = new Uv( "UV_WJK1", 5, 3, 12, "Rennes" );
        Uv uv6 = new Uv( "UV_AJH", 5, 3, 12, "Rennes" );
        Uv uv7 = new Uv( "UV_FORM", 5, 3, 12, "Rennes" ); // Uv requise pour
                                                          // etre formateur
        Uv uv8 = new Uv( "UV_FORM", 5, 3, 12, "Rennes" ); // Uv requise pour
                                                          // etre formateur
        uvs.put( 4, uv4 );
        uvs.put( 5, uv5 );
        uvs.put( 6, uv6 );
        uvs.put( 7, uv7 );
        uvs.put( 8, uv8 );
        uv3.ajouterUv( uv6 );
        uv4.ajouterUv( uv5 );
        uv4.setId( uvDao.creer( uv4 ) );
        uv5.setId( uvDao.creer( uv5 ) );
        uv6.setId( uvDao.creer( uv6 ) );
        uv7.setId( uvDao.creer( uv7 ) );
        uv8.setId( uvDao.creer( uv8 ) );

        // Creation d'aptitude simple + DAO
        Aptitude apt1 = new Aptitude( "APT1" );
        Aptitude apt2 = new Aptitude( "APT2" );
        aptitudes.put( 1, apt1 );
        aptitudes.put( 2, apt2 );
        AptitudeDao aptitudeDao = adf.getAptitudeDao();
        apt1.setId( aptitudeDao.creer( apt1 ) );
        apt2.setId( aptitudeDao.creer( apt2 ) );

        // Mise a jour d'aptitude avec prerequis + DAO
        apt1.ajouterUv( uv1 );
        apt1.ajouterUv( uv2 );
        apt2.ajouterUv( uv1 );
        aptitudeDao.mettreAJour( apt1 );

        // Creation d'aptitude avec prerequis + DAO
        Aptitude apt3 = new Aptitude( "APT3" );
        apt3.ajouterUv( uv4 );
        apt3.ajouterUv( uv5 );
        aptitudes.put( 3, apt3 );
        apt3.setId( aptitudeDao.creer( apt3 ) );

        // Creation d'agents simple + DAO
        Agent a1 = new Agent( "ATREUILLIER", "mdp", "19041975", true );
        Agent a2 = new Agent( "LTREUILLIER", "mdp", "15122010", false );
        agents.put( 1, a1 );
        agents.put( 2, a2 );
        /* Sauvegarde des Agents */
        AgentDao agentDao = adf.getAgentDao();
        a1.setId( agentDao.creer( a1 ) );
        a2.setId( agentDao.creer( a2 ) );

        // Mise a jour d'agents avec Uv + DAO
        a1.ajouterUv( uv6 );
        a2.ajouterUv( uv3 );
        agentDao.mettreAJour( a1 );
        agentDao.mettreAJour( a2 );

        // Creation d'agents avec Uv + DAO
        Agent a3 = new Agent( "NTREUILLIER", "mdp", "09102013", false );
        Agent a4 = new Agent( "FDESCAVES", "mdp", "06091991", false );
        Agent a5 = new Agent( "MDESCAVES", "max", "06091990", false );
        agents.put( 3, a3 );
        agents.put( 4, a4 );
        agents.put( 5, a5 );
        a3.ajouterUv( uv2 );
        a3.ajouterUv( uv3 );
        a3.setId( agentDao.creer( a3 ) );
        a4.setId( agentDao.creer( a4 ) );
        a5.setId( agentDao.creer( a5 ) );

        // Creation de Stage Simple + DAO
        Stage stg1 = new Stage( "fevrier1" );
        Stage stg2 = new Stage( "fevrier2" );
        Stage stg3 = new Stage( "mars1" );
        stages.put( 1, stg1 );
        stages.put( 2, stg2 );
        stages.put( 3, stg3 );
        StageDao stageDao = adf.getStageDao();
        stg1.setId( stageDao.creer( stg1 ) );
        stg2.setId( stageDao.creer( stg2 ) );
        stg3.setId( stageDao.creer( stg3 ) );

        // Mise à jour de Stage Simple + DAO
        stg1.setNom( "fevrier3" );
        stageDao.mettreAJour( stg1 );

        // Creation de session simple + DAO
        Session s1 = new Session( "INC1", new java.sql.Date( 2017, 02, 06 ), new java.sql.Date( 2017, 02, 10 ), true,
                uv1, stg1 );
        Session s2 = new Session( "FDF1", new java.sql.Date( 2017, 02, 06 ), new java.sql.Date( 2017, 02, 17 ), true,
                uv2, stg2 );
        Session s3 = new Session( "SAR1", new java.sql.Date( 2017, 01, 30 ), new java.sql.Date( 2017, 02, 03 ), true,
                uv3, stg3 );

        sessions.put( 1, s1 );
        sessions.put( 2, s2 );
        sessions.put( 3, s3 );
        /* Sauvegarde des Sessions */
        SessionDao sessionDao = adf.getSessionDao();
        s1.setId( sessionDao.creer( s1 ) );
        s2.setId( sessionDao.creer( s2 ) );
        s3.setId( sessionDao.creer( s3 ) );

        // Mise a jour de session simple + DAO
        s1.setStage( stg2 );
        sessionDao.mettreAJour( s1 );

        // Creation de candidatures simples + DAO
        Candidature cand1 = new Candidature( a2, -2, false, s1 );
        Candidature cand2 = new Candidature( a3, -2, false, s1 );
        candidatures.put( 1, cand1 );
        candidatures.put( 2, cand2 );
        /* Sauvegarde des Candidatures */
        CandidatureDao candidatureDao = adf.getCandidatureDao();
        candidatureDao.creer( cand1 );
        candidatureDao.creer( cand2 );

        // Mise a jour de candidature simple + DAO
        cand1.setSession( s2 );
        sessionDao.mettreAJour( s2 );

        // Ajout des tokens de connexions
        connexions.put( "19041975", a1 );
        connexions.put( "15122010", a2 );
        connexions.put( "09102013", a3 );
        connexions.put( "06091991", a4 );
    }
}
