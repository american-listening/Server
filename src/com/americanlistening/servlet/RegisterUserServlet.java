package com.americanlistening.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.americanlistening.core.CreationParameters;
import com.americanlistening.core.Instance;
import com.americanlistening.core.RegistrationException;
import com.americanlistening.core.User;
import com.americanlistening.core.UserCreateInfo;

/**
 * Servlet implementation class RegisterUserServlet
 */
@WebServlet("/RegisterUserServlet")
public class RegisterUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		UserCreateInfo inf = new UserCreateInfo();
		inf.username = username;
		inf.password = password;
		inf.email = email;
		PrintWriter out = new PrintWriter(response.getOutputStream());
		try {
			User user = Instance.currentInstance.registerUser(inf);
			out.println("0 " + user.getUserID());
		} catch (RegistrationException e) {
			CreationParameters.InvalidityType ty = e.invalidityType();
			if (ty == null)
				out.println("-2 " + e.getMessage());
			else
				out.println(ty.code());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
