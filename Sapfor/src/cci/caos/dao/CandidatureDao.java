package cci.caos.dao;

import cci.caos.repository.Candidature;
import cci.caos.repository.Session;

public interface CandidatureDao {

	void creer (Candidature candidature) throws DAOException;
	
	Candidature trouver(int idCandidat, int idSession) throws DAOException;
}
