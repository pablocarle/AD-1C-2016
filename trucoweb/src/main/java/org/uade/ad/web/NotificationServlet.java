package org.uade.ad.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.uade.ad.trucorepo.delegates.JuegoDelegate;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.dtos.NotificacionDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;

/**
 * Servlet implementation class NotificationServlet
 */
public class NotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private JuegoDelegate delegate = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotificationServlet() {
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session == null) {
			error("No hay sesion", request, response);
		} else {
			JugadorDTO jugador = (JugadorDTO) session.getAttribute("user");
			if (jugador == null) {
				error("No hay jugador en curso", request, response);
			} else {
				try {
					List<NotificacionDTO> dtos = delegate.getNotificaciones(jugador);
				} catch (JuegoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//TODO Enviar como XML
			}
		}
	}

	private void error(String mensaje, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("error", true);
		request.setAttribute("errorMessage", mensaje);
		request.getRequestDispatcher("error.jsp").forward(request, response);
	}
}
