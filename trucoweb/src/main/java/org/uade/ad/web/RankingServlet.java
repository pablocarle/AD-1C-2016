package org.uade.ad.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.uade.ad.trucorepo.delegates.RankingDelegate;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.dtos.RankingDTO;
import org.uade.ad.trucorepo.exceptions.RankingException;

/**
 * Servlet implementation class RankingServlet
 */
public class RankingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	private RankingDelegate delegate = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RankingServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	try {
    		delegate = new RankingDelegate();
    	} catch (RankingException e) {
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
		String idGrupo = request.getParameter("idGrupo");
		HttpSession session = request.getSession();
		if (session == null) {
			error("No hay sesion", request, response);
			return;
		}
		JugadorDTO jugador = (JugadorDTO) session.getAttribute("user");
		if (jugador == null) {
			error("No hay jugador logueado", request, response);
			return;
		}
		if (idGrupo != null && idGrupo.length() > 0) {
			generarRankingGrupo(idGrupo, jugador, request, response);
		} else {
			generarRankingGeneral(request, response);
		}
	}

	private void error(String mensaje, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("error", true);
		request.setAttribute("errorMessage", mensaje);
		request.getRequestDispatcher("/error.jsp").forward(request, response);;
	}

	private void generarRankingGeneral(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RankingDTO ranking = delegate.getRankingGeneral();
			request.setAttribute("ranking", ranking);
			request.getRequestDispatcher("/trucoweb/ranking.jsp").forward(request, response);
		} catch (RankingException e) {
			error(e.getLocalizedMessage(), request, response);
			e.printStackTrace();
		}
	}

	private void generarRankingGrupo(String idGrupo, JugadorDTO jugador, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!jugador.perteneceAGrupo(Integer.parseInt(idGrupo))) {
			error("El jugador no pertenece al grupo", request, response);
			return;
		} else {
			RankingDTO ranking;
			try {
				ranking = delegate.getRankingGrupo(Integer.parseInt(idGrupo), jugador);
				request.setAttribute("ranking", ranking);
				request.getRequestDispatcher("/trucoweb/ranking.jsp").forward(request, response);
			} catch (NumberFormatException | RankingException e) {
				error(e.getLocalizedMessage(), request, response);
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
