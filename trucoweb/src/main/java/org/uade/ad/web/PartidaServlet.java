package org.uade.ad.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
import org.uade.ad.web.util.XMLSerialize;

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

	private enum AccionTipo {
		JUEGACARTA,
		ENVITE,
		ALMAZO, DESCONOCIDO
	}
	
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
			String[] path = request.getContextPath().split("/");
			String tipoPartida = request.getParameter("tipoPartida");
			if (PARTIDA_ABIERTA.equals(tipoPartida)) {
				nuevaPartidaAbierta(request, response);
			} else if (PARTIDA_CERRADA.equals(tipoPartida)) {
				nuevaPartidaCerrada(request, response);
			} else if (PARTIDA_ABIERTA_PAREJAS.equals(tipoPartida)) {
				nuevaPartidaAbiertaParejas(request, response);
			} else {
				error("No se identifica el tipo de partida " + tipoPartida, request, response);
			}
		} else if (request.getMethod().equalsIgnoreCase("post")) {
			String[] path = request.getContextPath().split("/");
			switch (path[path.length-1]) {
			case "Jugar":
				handleJuego(request, response);
				break;
			case "Verificar":
				break;
			}
		}
	}

	private void handleJuego(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idCartaStr = request.getParameter("idCarta");
		String idEnviteStr = request.getParameter("idEnvite");
		String alMazoStr = request.getParameter("alMazo");
		String idPartidaStr = request.getParameter("idPartida");
		
		HttpSession session = request.getSession();
		JugadorDTO jugador = null;
		if (session == null) { 
			error("No hay sesion", request, response);
			return;
		} else {
			jugador = (JugadorDTO) session.getAttribute("user");
			if (jugador == null) {
				error("no hay jugador logueado", request, response);
				return;
			}
		}
		
		int idPartida = 0;
		
		if (idPartidaStr == null || idPartidaStr.length() == 0) {
			//TODO Enviar xml error
		} else {
			idPartida = Integer.parseInt(idPartidaStr);
		}
		PartidaDTO partida = null; //TODO Obtener la partida de las guardadas en sesion
		
		try {
			switch (getTipoAccion(idCartaStr, idEnviteStr, alMazoStr)) {
				case ALMAZO:
					partida = delegate.irAlMazo(jugador, idPartida);
					xmlResponse(partida, response);
					break;
				case ENVITE:
					partida = delegate.cantar(jugador, idPartida, Integer.parseInt(idEnviteStr));
					xmlResponse(partida, response);
					break;
				case JUEGACARTA:
					partida = delegate.jugarCarta(jugador, idPartida, Integer.parseInt(idCartaStr));
					xmlResponse(partida, response);
					break;
				default:
					//TODO Enviar xml error
					break;
			}
		} catch (JuegoException e) {
			
		}
	}
	
	private void xmlResponse(PartidaDTO partida, HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		response.setContentType("text/xml");
		writer.write(XMLSerialize.serialize(partida));
		writer.close();
	}

	/**
	 * Obtiene la accion de juego que desea hacer el usuario de acuerdo a los parametros recibidos
	 * 
	 * @param idCartaStr
	 * @param idEnviteStr
	 * @param alMazoStr
	 * @return
	 */
	private AccionTipo getTipoAccion(String idCartaStr, String idEnviteStr, String alMazoStr) {
		if ((idCartaStr == null || idCartaStr.length() == 0) && (idEnviteStr == null || idEnviteStr.length() == 0)
				&& (alMazoStr == null || alMazoStr.length() == 0)) {
			return AccionTipo.DESCONOCIDO;
		} else if (idCartaStr != null && idCartaStr.length() > 0) {
			return AccionTipo.JUEGACARTA;
		} else if (idEnviteStr != null && idEnviteStr.length() > 0) {
			return AccionTipo.ENVITE;
		} else if (alMazoStr != null && alMazoStr.length() > 0) {
			return AccionTipo.ALMAZO;
		}
		return AccionTipo.DESCONOCIDO;
	}

	private void nuevaPartidaAbiertaParejas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParejaStr = request.getParameter("idPareja");
		if (idParejaStr == null || idParejaStr.length() == 0) {
			error("idPareja es requerido", request, response);
		} else {
			int idPareja = Integer.parseInt(idParejaStr);
			HttpSession session = request.getSession();
			if (session == null) {
				error("No hay sesion", request, response);
			} else {
				JugadorDTO user = (JugadorDTO) session.getAttribute("user");
				if (user == null) {
					error("No hay jugador activo", request, response);
				} else {
					try {
						PartidaDTO partida = delegate.crearNuevaPartidaAbiertaPareja(user, idPareja);
						agregarPartidaSesion(session, partida);
						request.setAttribute("partidaId", partida.getIdPartida());
						request.getRequestDispatcher("juegoMain.jsp").forward(request, response);
					} catch (JuegoException e) {
						error("Ocurrio un error iniciando la partida: " + e.getMessage(), request, response);
						e.printStackTrace();
					}
				}
			}
			
		}
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
							agregarPartidaSesion(session, partida);
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
					agregarPartidaSesion(session, dto);
					request.setAttribute("partidaId", dto.getIdPartida());
					request.getRequestDispatcher("juegoMain.jsp").forward(request, response);
				} catch (JuegoException e) {
					error("Ocurrio un problema en la creacion de partida: " + e.getMessage(), request, response);
				}
			}
		}
	}

	private void agregarPartidaSesion(HttpSession session, PartidaDTO dto) {
		@SuppressWarnings("unchecked")
		List<PartidaDTO> partidas = (List<PartidaDTO>) session.getAttribute("partidas");
		if (partidas == null) {
			partidas = new ArrayList<>();
			partidas.add(dto);
			session.setAttribute("partidas", partidas);
		} else {
			partidas.remove(dto);
			partidas.add(dto);
		}
	}

	private void error(String mensaje, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("error", true);
		request.setAttribute("errorMessage", mensaje);
		request.getRequestDispatcher("error.jsp").forward(request, response);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
