package org.uade.ad.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.uade.ad.trucorepo.delegates.JuegoDelegate;
import org.uade.ad.trucorepo.dtos.JugadorDTO;
import org.uade.ad.trucorepo.dtos.NotificacionDTO;
import org.uade.ad.trucorepo.exceptions.JuegoException;
import org.uade.ad.web.util.XMLSerialize;

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
					xmlResponse(dtos, request, response);
				} catch (JuegoException e) {
					e.printStackTrace();
					error(e.getLocalizedMessage(), request, response);
				}
			}
		}
	}

	private void error(String mensaje, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("error", true);
		request.setAttribute("errorMessage", mensaje);
		request.getRequestDispatcher("error.jsp").forward(request, response);
	}
	
	private void xmlResponse(List<NotificacionDTO> notificaciones, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PrintWriter writer = response.getWriter();
		response.setContentType("text/xml");
		StringBuilder str = new StringBuilder();
		try {
			str.append("<Notificaciones>");
			for (NotificacionDTO n : notificaciones) {
				str.append(XMLSerialize.serialize(n));
			}
			str.append("</Notificaciones>");
			writer.write(str.toString());
		} catch (JAXBException e) {
			error("Error en serializacion de notificacion: " + e.getMessage(), request, response);
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
}
