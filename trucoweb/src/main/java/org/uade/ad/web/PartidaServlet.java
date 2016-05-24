package org.uade.ad.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.uade.ad.trucorepo.delegates.JuegoDelegate;
import org.uade.ad.trucorepo.dtos.GrupoDTO;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.dtos.PartidaDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;

/**
 * 
 * Servlet para gestion de partida
 * 
 */
public class PartidaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String PARTIDA_ABIERTA = "abierta";
	private static final String PARTIDA_ABIERTA_PAREJAS = "abiertaParejas";
	private static final String PARTIDA_CERRADA = "cerrada";

	private JuegoDelegate delegate;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PartidaServlet() {
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
		if (request.getMethod().equalsIgnoreCase("get")) {
			String tipoPartida = request.getParameter("tipo");
			if (PARTIDA_ABIERTA.equals(tipoPartida)) {
				nuevaPartidaAbierta(request, response);
			} else if (PARTIDA_CERRADA.equals(tipoPartida)) {
				nuevaPartidaCerrada(request, response);
			} else if (PARTIDA_ABIERTA_PAREJAS.equals(tipoPartida)) {
				nuevaPartidaAbiertaParejas(request, response);
			} else {
				error("No se identifica el tipo de partida " + tipoPartida, request, response);
			}
		}
	}

	private void nuevaPartidaAbiertaParejas(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void nuevaPartidaCerrada(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Necesito conocer el grupo con el que usuario quiere crear una partida cerrada
		String idGrupo = request.getParameter("idGrupo");
		if (idGrupo == null || idGrupo.length() == 0) {
			error("", request, response);
		} else {
			int idGrupoInt = Integer.parseInt(idGrupo);
			//Verifico que el grupo efectivamente pertenece al usuario
			HttpSession session = request.getSession();
			if (session == null) {
				error("No hay sesion", request, response);
			} else {
				JugadorDTO user = (JugadorDTO) session.getAttribute("user");
				if (user == null) {
					error("No hay jugador activo", request, response);
				} else {
					if (user.tieneGrupo(idGrupoInt)) {
						GrupoDTO grupo = user.getGrupo(idGrupoInt);
						try {
							PartidaDTO partida = delegate.crearNuevaPartidaCerrada(user, grupo);
							request.setAttribute("partidaId", partida.getIdPartida());
							request.getRequestDispatcher("juegoMain.jsp").forward(request, response);
						} catch (JuegoException e) {
							error("Ocurrio un error al crear la partida cerrada: " + e.getMessage(), request, response);
							e.printStackTrace();
						}
					} else {
						error("El grupo no pertenece al usuario", request, response);
					}
				}
			}
		}
	}

	private void nuevaPartidaAbierta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Le pasamos al delegate que el usuario quiere participar en nueva partida abierta y forward a vista de juego principal con data de la partida
		HttpSession session = request.getSession();
		if (session == null) {
			error("No hay sesion", request, response);
		} else {
			if (session.getAttribute("user") == null) {
				error("No hay usuario logueado", request, response);
			} else {
				JugadorDTO jugador = (JugadorDTO) session.getAttribute("user");
				try {
					PartidaDTO dto = delegate.crearNuevaPartidaAbierta(jugador);
					request.setAttribute("partidaId", dto.getIdPartida());
					request.getRequestDispatcher("juegoMain.jsp").forward(request, response);
				} catch (JuegoException e) {
					error("Ocurrio un problema en la creacion de partida: " + e.getMessage(), request, response);
				}
			}
		}
	}

	private void error(String mensaje, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("error", true);
		request.setAttribute("errorMessage", mensaje);
		request.getRequestDispatcher("error.jsp").forward(request, response);;
		//TODO Crear error.jsp
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
