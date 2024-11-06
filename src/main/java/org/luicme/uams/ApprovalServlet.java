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

@WebServlet("/approvalservlet")
public class ApprovalServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int requestId = Integer.parseInt(req.getParameter("requestId"));
		String status = req.getParameter("status");

		final String url = "jdbc:postgresql://localhost:5432/leucine";
		final String user = "postgres";
		final String pass = "mohan";

		try {
			Class.forName("org.postgresql.Driver");
			
			Connection con = DriverManager.getConnection(url, user, pass);
			
			String sql = "UPDATE requests SET status = ? WHERE id = ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setInt(2, requestId);
			
			ps.executeUpdate();

			resp.sendRedirect("pendingRequests.jsp");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			resp.sendRedirect("error.jsp");
		}
	}
}
