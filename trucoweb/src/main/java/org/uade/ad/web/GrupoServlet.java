package org.uade.ad.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.uade.ad.trucorepo.delegates.JugadoresDelegate;
import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.exceptions.GrupoException;
import org.uade.ad.trucorepo.exceptions.JugadorException;

/**
 * Servlet implementation class GrupoServlet
 */
public class GrupoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	private JugadoresDelegate delegate;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GrupoServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
    	super.init();
    	try {
			delegate = new JugadoresDelegate();
		} catch (JugadorException | GrupoException e) {
			throw new ServletException(e);
		}
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processGetRequest(request, response);
	}

	private void processGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processPostRequest(request, response);
	}

	private void processPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Nuevo grupo
		HttpSession session = request.getSession();
		JugadorDTO jugador = (JugadorDTO) session.getAttribute("user"); //El admin
		String nombreGrupo = request.getParameter("nombreGrupo");
		String apodoJugador1 = request.getParameter("jugador1");
		String apodoJugador2 = request.getParameter("jugador2");
		String apodoJugador3 = request.getParameter("jugador3");
		
		try {
			GrupoDTO grupo = delegate.crearGrupo(nombreGrupo, jugador.getApodo(), apodoJugador1, apodoJugador2, apodoJugador3);
		} catch (GrupoException e) {
			//Devolver a webclient mensaje
		}
	}
}
