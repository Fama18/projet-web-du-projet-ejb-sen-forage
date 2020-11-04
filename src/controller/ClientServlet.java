package controller;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import metier.IClient;
import metier.IVillage;
import metier.VillageImpl;
import metier.entities.Client;
import metier.entities.Village;
import metier.ClientImpl;

/**
 * Servlet implementation class ClientServlet
 */
@WebServlet("/Client")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	private IClient client_dao;
	private IVillage village_dao;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public void init(ServletConfig config) throws ServletException {
		client_dao = new ClientImpl();
		village_dao = new VillageImpl();
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listV", village_dao.list());
		request.setAttribute("listc", client_dao.list());	
		request.getRequestDispatcher("view/client/add.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String nomFamille = request.getParameter("nomFamille").toString();
	        String adresse = request.getParameter("adresse").toString();
	        String telephone = request.getParameter("telephone").toString();
	        int id = Integer.parseInt(request.getParameter("village_id").toString());
	        Village v1 = new Village();
	        v1 = village_dao.getVillageById(id);
	        Client client = new Client();
	        
	        client.setNomFamille(nomFamille);
	        client.setAdresse(adresse);
	        client.setTelephone(telephone);
	        client.setVillage(v1);
	        
	        int ok = client_dao.add(client);
			
	        
	        doGet(request,response);
	}

}
