package cci.caos.repository;
import java.util.List;


public class Agent {

	   public String nom;
	   public String mdp;
	   public String matricule;
	   public List<Uv> listeUV;
	   public List<Aptitude> listeAptitude;
	   public Boolean gestionnaire;

	   
	   /*Constructeurs*/
	   
	    public Agent(){}
	   
	    public Agent(String nom, String motdepasse, String matricule, List<Uv> listeUV, List<Aptitude> listeAptitude, Boolean gestionnaire) {
		this.nom = nom;
		this.mdp = motdepasse;
		this.matricule = matricule;
		this.listeUV = listeUV;
		this.listeAptitude = listeAptitude;
		this.gestionnaire = gestionnaire;
	}

	   /*Accesseurs et Modificateurs  */

	   public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getMdp() {
			return mdp;
		}

		public void setMdp(String mdp) {
			this.mdp = mdp;
		}

		public String getMatricule() {
			return matricule;
		}

		public void setMatricule(String matricule) {
			this.matricule = matricule;
		}

		public List<Uv> getListeUV() {
			return listeUV;
		}

		public void setListeUV(List<Uv> listeUV) {
			this.listeUV = listeUV;
		}

		public List<Aptitude> getListeAptitude() {
			return listeAptitude;
		}

		public void setListeAptitude(List<Aptitude> listeAptitude) {
			this.listeAptitude = listeAptitude;
		}

		public Boolean getGestionnaire() {
			return gestionnaire;
		}

		public void setGestionnaire(Boolean gestionnaire) {
			this.gestionnaire = gestionnaire;
		}

}
