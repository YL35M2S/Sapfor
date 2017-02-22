package cci.caos.dao;

import java.sql.Connection;

public class DaoFactory extends AbstractDAOFactory {
    protected static final Connection conn = CaosConnection.getInstance();

    /*
     * R�cup�ration de l'impl�mentation des diff�rentes DAO
     */
    public AgentDao getAgentDao() {
        return new AgentDaoImpl( conn );
    }

    public AptitudeDao getAptitudeDao() {
        return new AptitudeDaoImpl( conn );
    }

    public StageDao getStageDao() {
        return new StageDaoImpl( conn );
    }

    public SessionDao getSessionDao() {
        return new SessionDaoImpl( conn );
    }

    public CandidatureDao getCandidatureDao() {
        return new CandidatureDaoImpl( conn );
    }

    public UvDao getUvDao() {
        return new UvDaoImpl( conn );
    }
}