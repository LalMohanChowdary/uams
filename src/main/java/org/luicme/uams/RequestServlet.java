package org.luicme.uams;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/requestservlet")
public class RequestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String role = (String) session.getAttribute("role");
        String username = (String) session.getAttribute("username");

        if (session == null || role == null || !role.equals("Employee")) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String softwareName = req.getParameter("software");
        String accessType = req.getParameter("accessType");
        String reason = req.getParameter("reason");

        final String url = "jdbc:postgresql://localhost:5432/leucine";
        final String user = "postgres";
        final String pass = "mohan";

        try {
			Class.forName("org.postgresql.Driver");
			
            Connection con = DriverManager.getConnection(url, user, pass);

            String userIdSql = "SELECT id FROM users WHERE username = ?";
            
            PreparedStatement userIdStmt = con.prepareStatement(userIdSql);
            
            userIdStmt.setString(1, username);
            
            ResultSet rs = userIdStmt.executeQuery();
            
            int userId = rs.next() ? rs.getInt("id") : -1;
            
            rs.close();
            userIdStmt.close();

            String sql = "INSERT INTO requests (user_id, software_id, access_type, reason, status) VALUES (?, (SELECT id FROM software WHERE name = ?), ?, ?, 'Pending')";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, userId);
            ps.setString(2, softwareName);
            ps.setString(3, accessType);
            ps.setString(4, reason);
            ps.executeUpdate();

            resp.sendRedirect("requestAccessSuccess.jsp");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error during access request submission");
            e.printStackTrace();
            resp.sendRedirect("requestAccess.jsp?error=submissionFailed");
        }
    }
}
