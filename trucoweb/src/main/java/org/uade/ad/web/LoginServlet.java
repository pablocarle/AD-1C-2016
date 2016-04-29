package org.uade.ad.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
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
		System.out.println("Solicitud de acceso de " + apodo);
		
		if (loginValido(apodo, password)) {
			response.sendRedirect("main.jsp");
		} else {
			PrintWriter writer = response.getWriter();
			writer.write("<html><body>Login invalido</body></html>");
		}
	}

	private boolean loginValido(String apodo, String password) {
		//TODO Debe usar business delegate para solicitar login en server rmi
		return true;
	}

}
