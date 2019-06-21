package com.americanlistening.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.americanlistening.core.DataRequest;
import com.americanlistening.core.Instance;
import com.americanlistening.core.RegistrationException;
import com.americanlistening.core.User;
import com.americanlistening.core.UserCreateInfo;

/**
 * Servlet implementation class DataRequestServlet
 */
@WebServlet("/DataRequestServlet")
public class DataRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserCreateInfo inf = new UserCreateInfo();
		inf.username = "bob";
		inf.password = "pass";
		inf.email = "bob@hotmail.com";
		User user = null;
		try {
			user = Instance.currentInstance.registerUser(inf);
		} catch (RegistrationException e) {
			System.out.println("Failed to register user.");
			e.printStackTrace(System.out);
		}
		if (user != null)
			System.out.println(user.getUserID());
		
		String rtype = request.getParameter("request_type");
		int rtypeint = Integer.parseInt(rtype);
		DataRequest req = new DataRequest();
		req.requestType = rtypeint;
		req.params = request.getParameter("params").split(" ");
		response.getOutputStream().write(Instance.currentInstance.getData(req).getBytes());
		response.flushBuffer();
	}

}
