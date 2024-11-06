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

@WebServlet("/signup")
public class SignUpServelet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String name = req.getParameter("name");
		String password = req.getParameter("password");

		String role = "Employee"; //Manager,Admin

		final String url = "jdbc:postgresql://localhost:5432/leucine";
		final String user = "postgres";
		final String pass = "mohan";

		try {
			Class.forName("org.postgresql.Driver");

			Connection con = DriverManager.getConnection(url, user, pass);

			String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, name);
			ps.setString(2, password);
			ps.setString(3, role);

			ps.executeUpdate();

			resp.sendRedirect("login.jsp");
		} catch (SQLException e) {
			System.out.println("Exception Raised");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
