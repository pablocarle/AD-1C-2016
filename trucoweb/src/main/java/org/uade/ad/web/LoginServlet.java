package org.uade.ad.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.uade.ad.trucorepo.delegates.JugadoresDelegate;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.exceptions.JugadorException;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private JugadoresDelegate jugadoresDelegate;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
    	super.init();
    	try {
			jugadoresDelegate = new JugadoresDelegate();
		} catch (JugadorException e) {
			throw new ServletException(e);
		}
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String apodo = request.getParameter("apodo");
		String password = request.getParameter("pass");
		System.out.println("Solicitud de acceso de " + apodo);
		
		try {
			JugadorDTO dto = jugadoresDelegate.validarUsuario(apodo, password);
			HttpSession session = request.getSession(true);
			session.setAttribute("user", dto);
			response.sendRedirect("main.jsp");
		} catch (JugadorException e) {
			PrintWriter writer = response.getWriter();
			writer.write("<html><body>Login incorrecto</body></html>");
			writer.close();
		}
	}
}
