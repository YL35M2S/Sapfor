package cci.caos.dao.factory;

import cci.caos.dao.AgentDao;
import cci.caos.dao.AptitudeDao;
import cci.caos.dao.CandidatureDao;
import cci.caos.dao.SessionDao;
import cci.caos.dao.StageDao;
import cci.caos.dao.UvDao;

public abstract class AbstractDAOFactory {

    public static final int DAO_FACTORY     = 0;
    public static final int XML_DAO_FACTORY = 1;

    /**
     * Récupération de l'implémentation de AgentDao
     * @return Un objet de la classe AgentDao
     */
    public abstract AgentDao getAgentDao();
    
    /**
     * Récupération de l'implémentation de AptitudeDao
     * @return Un objet de la classe AptitudeDao
     */
    public abstract AptitudeDao getAptitudeDao();
    
    /**
     * Récupération de l'implémentation de CandidatureDao
     * @return Un objet de la classe CandidatureDao
     */
    public abstract CandidatureDao getCandidatureDao();
    
    /**
     * Récupération de l'implémentation de SessionDao
     * @return Un objet de la classe SessionDao
     */
    public abstract SessionDao getSessionDao();
    
    /**
     * Récupération de l'implémentation de StageDao
     * @return Un objet de la classe StageDao
     */
    public abstract StageDao getStageDao();
    
    /**
     * Récupération de l'implémentation de UvDao
     * @return Un objet de la classe UvDao
     */
    public abstract UvDao getUvDao();

    /**
     * Renvoie un objet de type DaoFactory ou XMLDaoFactory selon la méthode de stockage souhaitée
     * @param type - le type de Factory que la méthode va retourner (0 pour DaoFactory, 1 pour XMLDaoFactory)
     * @return Un objet la classe DaoFactory ou XMLDaoFactory
     */
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
