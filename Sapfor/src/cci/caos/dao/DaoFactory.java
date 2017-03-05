package cci.caos.dao;

import java.sql.Connection;

public class DaoFactory extends AbstractDAOFactory {
    protected static final Connection conn = CaosConnection.getInstance();

    /**
     * Recupere l'implementation de AgenDao
     * @return Un objet de la classe AgentDao connecte e la base de donnees
     */
    public AgentDao getAgentDao() {
        return new AgentDaoImpl( conn );
    }
    /**
     * Recupere l'implementation de AptitudeDao
     * @return Un objet de la classe AptitudeDao via la connection a la base
     */
    public AptitudeDao getAptitudeDao() {
        return new AptitudeDaoImpl( conn );
    }
    /**
     * Recupere l'implementation de StageDao
     * @return Un objet de la classe StageDao via la connection a la base
     */
    public StageDao getStageDao() {
        return new StageDaoImpl( conn );
    }
    /**
     * Recupere l'implementation de SessionDao
     * @return Un objet de la classe SessionDao via la connection a la base
     */
    public SessionDao getSessionDao() {
        return new SessionDaoImpl( conn );
    }
    /**
     * Recupere l'implementation de CandidatureDao
     * @return Un objet de la classe CandidatureDao via la connection a la base 
     */
    public CandidatureDao getCandidatureDao() {
        return new CandidatureDaoImpl( conn );
    }
    /**
     * Recupere l'implementation de UvDao
     * @return Un objet de la classe UvDao via la connection a la base 
     */
    public UvDao getUvDao() {
        return new UvDaoImpl( conn );
    }
}