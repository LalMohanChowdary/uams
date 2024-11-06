<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, javax.servlet.*, javax.servlet.http.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pending Requests</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #e9e9e9;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h2>Pending Access Requests</h2>
    <table>
        <tr>
            <th>Employee Name</th>
            <th>Software Name</th>
            <th>Access Type</th>
            <th>Reason for Request</th>
            <th>Actions</th>
        </tr>
        <%
            try {
                Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/leucine", "postgres", "mohan");
                String query = "SELECT requests.id, users.username, software.name AS software_name, " +
                               "requests.access_type, requests.reason " +
                               "FROM requests " +
                               "JOIN users ON requests.user_id = users.id " +
                               "JOIN software ON requests.software_id = software.id " +
                               "WHERE requests.status = 'Pending'";
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int requestId = rs.getInt("id");
                    String employeeName = rs.getString("username");
                    String softwareName = rs.getString("software_name");
                    String accessType = rs.getString("access_type");
                    String reason = rs.getString("reason");
        %>
        <tr>
            <td><%= employeeName %></td>
            <td><%= softwareName %></td>
            <td><%= accessType %></td>
            <td><%= reason %></td>
            <td>
                <form action="approvalservlet" method="post" style="display:inline;">
                    <input type="hidden" name="requestId" value="<%= requestId %>">
                    <input type="hidden" name="status" value="Approved">
                    <input type="submit" value="Approve">
                </form>
                <form action="approvalservlet" method="post" style="display:inline;">
                    <input type="hidden" name="requestId" value="<%= requestId %>">
                    <input type="hidden" name="status" value="Rejected">
                    <input type="submit" value="Reject">
                </form>
            </td>
        </tr>
        <%
                }
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        %>
    </table>
</body>
</html>
