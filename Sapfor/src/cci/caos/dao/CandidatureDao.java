package cci.caos.dao;

import cci.caos.repository.Candidature;

public interface CandidatureDao {

	void creer (Candidature candidature) throws DAOException;
	
	Candidature trouver(int idCandidat, int idSession) throws DAOException;
}
