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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        final String url = "jdbc:postgresql://localhost:5432/leucine";
        final String user = "postgres";
        final String pass = "mohan";

        try {
			Class.forName("org.postgresql.Driver");
			
            Connection con = DriverManager.getConnection(url, user, pass);

            String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");

                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                session.setAttribute("role", role);

                if ("Employee".equalsIgnoreCase(role)) {
                    resp.sendRedirect("requestAccess.jsp");
                } else if ("Manager".equalsIgnoreCase(role)) {
                    resp.sendRedirect("pendingRequests.jsp");
                } else if ("Admin".equalsIgnoreCase(role)) {
                    resp.sendRedirect("createSoftware.jsp");
                }
            } else {
                resp.sendRedirect("login.jsp?error=invalid");
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error during login");
            e.printStackTrace();
        }
    }
}
