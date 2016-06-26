package org.uade.ad.web;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
		String message = "";
		
		String nombreGrupo = request.getParameter("nombreGrupo");
		String apodoJugador1 = request.getParameter("jugador1");
		String apodoJugador2 = request.getParameter("jugador2");
		String apodoJugador3 = request.getParameter("jugador3");

		if (nombreGrupo == null || nombreGrupo.length() == 0
				|| apodoJugador1 == null || apodoJugador1.length() == 0
				|| apodoJugador2 == null || apodoJugador2.length() == 0
				|| apodoJugador3 == null || apodoJugador3.length() == 0) {
			message += "Faltan parametros obligatorios";
		} else if (!sonUnicos(jugador.getApodo(), apodoJugador1, apodoJugador2, apodoJugador3)) {
			message += "Los 4 jugadores deben ser distintos\n";
		}
		
		session.removeAttribute("regResult");
		if (message.length() == 0) {
			try {
				GrupoDTO grupo = delegate.crearGrupo(nombreGrupo, jugador.getApodo(), apodoJugador1, apodoJugador2, apodoJugador3);
				jugador.agregarGrupo(grupo);
				session.setAttribute("regResult", "Grupo " + grupo.getNombre() + " generado correctamente");
			} catch (GrupoException e) {
				session.setAttribute("regResult", e.getMessage());
			}
		} else {
			session.setAttribute("regResult", message);
		}
		request.getRequestDispatcher("grupoReg.jsp").forward(request, response);
	}

	private boolean sonUnicos(String apodo, String apodoJugador1, String apodoJugador2, String apodoJugador3) {
		Set<String> set = new HashSet<>(4);
		set.add(apodoJugador3);
		set.add(apodoJugador2);
		set.add(apodoJugador1);
		set.add(apodo);
		return set.size() == 4;
	}
}
