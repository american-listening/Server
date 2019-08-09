package com.americanlistening.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.americanlistening.core.youtube.YouTubeVideo;
import com.americanlistening.dao.DAO;
import com.americanlistening.dao.YouTubeVideoDAO;
import com.google.gdata.client.Query;
import com.google.gdata.client.youtube.YouTubeQuery;

/**
 * Servlet implementation class YouTubeSearch
 */
@WebServlet("/YouTubeSearchServlet")
public class YouTubeSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public YouTubeSearchServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = request.getParameter("search");
		DAO<YouTubeVideo, String> dao = new YouTubeVideoDAO();
		YouTubeQuery q;
		Optional<YouTubeVideo> video = dao.get(search);
		if (video.isPresent()) {
			response.getWriter().append("Found top result: ").append(video.get().getWebPlayerURL());
		} else {
			response.getWriter().append("No video found.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
