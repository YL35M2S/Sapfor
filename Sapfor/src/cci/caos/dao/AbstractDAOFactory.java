package cci.caos.dao;

public abstract class AbstractDAOFactory {

    public static final int DAO_FACTORY     = 0;
    public static final int XML_DAO_FACTORY = 1;

    public abstract AgentDao getAgentDao();

    public abstract AptitudeDao getAptitudeDao();

    public abstract CandidatureDao getCandidatureDao();

    public abstract SessionDao getSessionDao();

    public abstract StageDao getStageDao();

    public abstract UvDao getUvDao();

    public static AbstractDAOFactory getFactory( int type ) {
        switch ( type ) {
        case DAO_FACTORY:
            return new DaoFactory();
        case XML_DAO_FACTORY:
            return new XMLDaoFactory();
        default:
            return null;
        }
    }
}
