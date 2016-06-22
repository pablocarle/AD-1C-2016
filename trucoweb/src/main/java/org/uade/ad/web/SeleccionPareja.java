package org.uade.ad.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.uade.ad.trucorepo.delegates.JuegoDelegate;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;

/**
 * Servlet implementation class SeleccionPareja
 */
public class SeleccionPareja extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private JuegoDelegate delegate;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeleccionPareja() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	try {
    		delegate = new JuegoDelegate();
    	} catch (JuegoException e) {
    		throw new ServletException(e);
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<JugadorDTO> jugadoresDisponibles;
		try {
			jugadoresDisponibles = delegate.getJugadoresDisponibles();
			request.setAttribute("jugadoresDisponibles", jugadoresDisponibles);
		} catch (JuegoException e) {
			request.setAttribute("error", true);
			request.setAttribute("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		request.getRequestDispatcher("seleccionPareja.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}

}
