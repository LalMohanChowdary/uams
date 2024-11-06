package org.luicme.uams;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/softwareservlet")
public class SoftwareServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		String role = (String) session.getAttribute("role");

		if (session == null || role == null || !role.equals("Admin")) {
			resp.sendRedirect("login.jsp");
			return;
		}

		String name = req.getParameter("name");
		String description = req.getParameter("description");
		String[] accessLevels = req.getParameterValues("accessLevels");

		StringBuilder accessLevelsStr = new StringBuilder();
		if (accessLevels != null) {
			for (String access : accessLevels) {
				accessLevelsStr.append(access).append(",");
			}
			if (accessLevelsStr.length() > 0) {
				accessLevelsStr.setLength(accessLevelsStr.length() - 1);
			}
		}

		final String url = "jdbc:postgresql://localhost:5432/leucine";
		final String user = "postgres";
		final String pass = "mohan";

		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);

			String sql = "INSERT INTO software (name, description, access_levels) VALUES (?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, description);
			ps.setString(3, accessLevelsStr.toString());
			ps.executeUpdate();

			resp.sendRedirect("createSoftwareSuccess.jsp");

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error during software creation");
			e.printStackTrace();
			resp.sendRedirect("createSoftware.jsp?error=creationFailed");
		}
	}
}
