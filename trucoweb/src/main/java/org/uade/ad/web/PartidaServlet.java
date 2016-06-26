package org.uade.ad.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.uade.ad.trucorepo.delegates.JuegoDelegate;
import org.uade.ad.trucorepo.dtos.CartasDisponiblesDTO;
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
		ALMAZO, 
		REPARTIR_CARTAS,
		DESCONOCIDO
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
			String[] path = request.getRequestURI().split("/");
			switch (path[path.length - 1]) {
				case "EnvitesDisponibles":
					//Consultar envites disponibles de una partida
					consultarEnvites(request, response);
					break;
				case "NuevaPartida":
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
					break;
				case "Partida":
					//Obtener la partida que solicita
					consultarPartida(request, response);
					break;
				case "Unirse":
					handleUnirsePartida(request, response);
					break;
				default: 
					throw new ServletException("No se identifica que operacion realizar");
			}
		} else if (request.getMethod().equalsIgnoreCase("post")) {
			String[] path = request.getRequestURI().split("/");
			switch (path[path.length-1]) {
			case "Jugar":
				handleJuego(request, response);
				break;
			case "Unirse":
				handleUnirsePartida(request, response);
				break;
			case "Verificar":
				break;
			}
		}
	}

	private void handleUnirsePartida(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idPartidaStr = request.getParameter("idPartida");
		if (idPartidaStr == null || idPartidaStr.length() == 0) {
			error("Falta idPartida", request, response);
			return;
		}
		HttpSession session = request.getSession();
		if (session == null) {
			error("No hay sesion", request, response);
			return;
		} else {
			JugadorDTO user = (JugadorDTO) session.getAttribute("user");
			if (user == null) {
				error("No hay usuario logueado", request, response);
				return;
			} else {
				try {
					PartidaDTO dto = delegate.getPartida(user, Integer.parseInt(idPartidaStr));
					agregarPartidaSesion(session, dto);
					response.sendRedirect("/trucoweb/juegoMain.jsp?idPartida=" + dto.getIdPartida());
				} catch (NumberFormatException | JuegoException e) {
					e.printStackTrace();
					error(e.getMessage(), request, response);
				}
			}
		}
	}

	private void consultarPartida(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idPartidaStr = request.getParameter("idPartida");
		if (idPartidaStr == null || idPartidaStr.length() == 0) {
			errorXML("No se proporciono idPartida", request, response);
		} else {
			HttpSession session = request.getSession();
			if (session == null) {
				error("No hay sesion", request, response);
				return;
			}
			JugadorDTO user = (JugadorDTO) session.getAttribute("user");
			if (user == null) {
				error("No hay usuario logueado", request, response);
				return;
			}
			try {
				PartidaDTO partida = delegate.getPartida(user, Integer.parseInt(idPartidaStr));
				xmlResponse(partida, request, response);
			} catch (JuegoException e) {
				e.printStackTrace();
				errorXML(e.getLocalizedMessage(), request, response);
			}
		}
	}

	private void consultarEnvites(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session == null) {
			error("No hay sesion", request, response);
			return;
		} else {
			JugadorDTO jugador = (JugadorDTO) session.getAttribute("user");
			if (jugador == null) {
				error("No hay jugador logueado", request, response);
				return;
			} else {
			}
		}
	}

	private void handleJuego(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idCartaStr = request.getParameter("idCarta");
		String idEnviteStr = request.getParameter("idEnvite");
		String alMazoStr = request.getParameter("alMazo");
		String repartirCartas = request.getParameter("repartirCartas");
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
			errorXML("Falta idPartida", request, response);
		} else {
			idPartida = Integer.parseInt(idPartidaStr);
		}
		PartidaDTO partida = null;
		
		try {
			switch (getTipoAccion(idCartaStr, idEnviteStr, alMazoStr, repartirCartas)) {
				case ALMAZO:
					System.out.println("Seleccionada accion de irse al mazo");
					partida = delegate.irAlMazo(jugador, idPartida);
					xmlResponse(partida, request, response);
					break;
				case ENVITE:
					partida = delegate.cantar(jugador, idPartida, Integer.parseInt(idEnviteStr));
					xmlResponse(partida, request, response);
					break;
				case JUEGACARTA:
					partida = delegate.jugarCarta(jugador, idPartida, Integer.parseInt(idCartaStr));
					xmlResponse(partida, request, response);
					break;
				case REPARTIR_CARTAS:
					partida = delegate.repartirCartas(jugador, idPartida);
					xmlResponse(partida, request, response);
					break;
				default:
					errorXML("error", request, response);
					break;
			}
		} catch (JuegoException e) {
			errorXML(e.getLocalizedMessage(), request, response);
		}
	}
	
	private void errorXML(String string, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(500);
		response.setContentType("text/xml");
		PrintWriter writer = response.getWriter();
		writer.write("<error>" + string + "</error>");
		writer.close();
	}

	private void xmlResponse(PartidaDTO partida, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PrintWriter writer = response.getWriter();
		response.setContentType("text/xml");
		eliminarDatosOtrosJugadores(partida, request);
		try {
			writer.write(XMLSerialize.serialize(partida));
			writer.close();
		} catch (JAXBException e) {
			error("Error en serializacion: " + e.getMessage(), request, response);
			e.printStackTrace();
		}
	}

	private void eliminarDatosOtrosJugadores(PartidaDTO partida, HttpServletRequest request) {
		HttpSession session = request.getSession();
		JugadorDTO jugador = (JugadorDTO) session.getAttribute("user");
		if (!jugador.equals(partida.getTurnoActual())) {
			partida.getTurnoActualCartasDisponibles().clear();
		}
		Iterator<CartasDisponiblesDTO> it = partida.getCartasDisponibles().iterator();
		CartasDisponiblesDTO dto = null;
		while (it.hasNext()) {
			dto = it.next();
			if (!jugador.equals(dto.getJugador())) {
				it.remove();
			}
		}
	}

	/**
	 * Obtiene la accion de juego que desea hacer el usuario de acuerdo a los parametros recibidos
	 * 
	 * @param idCartaStr
	 * @param idEnviteStr
	 * @param alMazoStr
	 * @return
	 */
	private AccionTipo getTipoAccion(String idCartaStr, String idEnviteStr, String alMazoStr, String repartirCartas) {
		if ((idCartaStr == null || idCartaStr.length() == 0) && (idEnviteStr == null || idEnviteStr.length() == 0)
				&& (alMazoStr == null || alMazoStr.length() == 0) && (repartirCartas == null || repartirCartas.length() == 0)) {
			return AccionTipo.DESCONOCIDO;
		} else if (idCartaStr != null && idCartaStr.length() > 0) {
			return AccionTipo.JUEGACARTA;
		} else if (idEnviteStr != null && idEnviteStr.length() > 0) {
			return AccionTipo.ENVITE;
		} else if (alMazoStr != null && alMazoStr.length() > 0 && Boolean.parseBoolean(alMazoStr)) {
			return AccionTipo.ALMAZO;
		} else if (repartirCartas != null && repartirCartas.length() > 0 && Boolean.parseBoolean(repartirCartas)) {
			return AccionTipo.REPARTIR_CARTAS;
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
						if (partida.esNull()) {
							request.setAttribute("mensaje", "Agregado a cola de espera partida abierta en pareja");
							request.getRequestDispatcher("/main.jsp").forward(request, response);
						} else {
							agregarPartidaSesion(session, partida);
							response.sendRedirect("/trucoweb/juegoMain.jsp?idPartida=" + partida.getIdPartida());
						}
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
							if (partida.esNull()) {
								error("No fue posible crear la partida en grupo. Contacte admin", request, response); 
							} else {
								agregarPartidaSesion(session, partida);
								response.sendRedirect("/trucoweb/juegoMain.jsp?idPartida=" + partida.getIdPartida());
							}
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
		//Le pasamos al delegate que el usuario quiere participar en nueva panuevaPartidaAbiertaParejasrtida abierta y forward a vista de juego principal con data de la partida
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
					if (dto.esNull()) {
						request.setAttribute("mensaje", "Agregado a cola de partida abierta");
						request.getRequestDispatcher("/main.jsp").forward(request, response);
					} else {
						agregarPartidaSesion(session, dto);
						response.sendRedirect("/trucoweb/juegoMain.jsp?idPartida=" + dto.getIdPartida());
					}
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
		request.getRequestDispatcher("/error.jsp").forward(request, response);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
