package cci.caos.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cci.caos.dao.AgentDao;
import cci.caos.dao.factory.AbstractDAOFactory;
import cci.caos.forms.creerAgentForm;
import cci.caos.repository.Agent;
import cci.caos.server.SapforServer;

public class CreationAgent extends HttpServlet {
    private static final long  serialVersionUID = 1L;
    public static final String ATT_AGENT        = "agent";
    public static final String ATT_FORM         = "form";
    public static final String VUE              = "/creerAgent.jsp";
    public static final String BONJOUR          = "/bonjour.jsp";

    private AgentDao           agentDao;

    /**
     * Servlet utilisee pour la creation d'un agent via le formulaire de saisie
     * 
     * @author PT (TheoPerrin)
     * 
     * 
     */
    public void init() throws ServletException {
        /* Récupération d'une instance de notre AgentDao */
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory( SapforServer.typeDao );
        this.agentDao = adf.getAgentDao();
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        /* Affichage de la page de création */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        /* Récupération de l'agent rendu par le formulaire */
        creerAgentForm form = new creerAgentForm( agentDao );

        /* Traitement de la requête et récupération du bean Agent en résultat */
        Agent agent = form.creerAgent( request );

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_AGENT, agent );

        this.getServletContext().getRequestDispatcher( BONJOUR ).forward( request, response );
    }

}
