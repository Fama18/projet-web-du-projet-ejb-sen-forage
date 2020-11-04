package controller;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import metier.IUser;
import metier.UserImpl;
import metier.entities.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/User")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@EJB
	private IUser user_dao;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public void init(ServletConfig config) throws ServletException {
		
		user_dao = new UserImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("view/user/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        User ok = user_dao.getUserByEmail(email,password);
        
        if(ok!=null) {
        	session.setAttribute("nomF", ok.getNom());
        	RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        	rd.forward(request,response);
        	// forward permet depuis une servlet de rediriger la paire requête/réponse HTTP 
        	//vers une autre servlet ou vers une page JSP.
        }
        else {
        	response.sendRedirect("view/user/login.jsp");
        }
	}

}
