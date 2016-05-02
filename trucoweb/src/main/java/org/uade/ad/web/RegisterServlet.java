package org.uade.ad.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;

import org.uade.ad.trucorepo.delegates.JugadoresDelegate;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.exceptions.JugadorException;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private JugadoresDelegate jugadoresDelegate;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
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
		String email = request.getParameter("email");
		
		StringBuilder userMessage = new StringBuilder();
		
		if (apodo == null || apodo.length() == 0) {
			userMessage.append("apodo es requerido<br>");
		}
		if (password == null || password.length() == 0) {
			userMessage.append("password es requerido<br>");
		}
		if (email == null || email.length() == 0) {
			userMessage.append("email es requerido<br>");
		}
		if (userMessage.length() > 0) {
			PrintWriter writer = response.getWriter();
			//TODO Html mensaje error? o todo ajax?
			writer.write("");
			writer.close();
		} else {
			try {
				JugadorDTO dto = jugadoresDelegate.registrarJugador(apodo, email, password);
				response.setStatus(200);
				PrintWriter writer = response.getWriter();
				//TODO Respuesta a usuario con success de creacion de jugador
			} catch (JugadorException e) {
				PrintWriter writer = response.getWriter();
				//TODO Html mensaje error? o todo ajax?
				writer.write("");
				writer.close();
			}
		}
	}
}
