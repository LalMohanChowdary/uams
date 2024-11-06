<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.*, javax.servlet.*, java.sql.*, java.util.*" %>
<%

    String url = "jdbc:postgresql://localhost:5432/leucine";
    String user = "postgres";
    String pass = "mohan";
    List<String> softwareList = new ArrayList<>();

    try {
		Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, user, pass);
        String sql = "SELECT name FROM software";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            softwareList.add(rs.getString("name"));
        }

        rs.close();
        stmt.close();
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Request Access</title>
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
        form {
            max-width: 600px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }
        select, textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
            width: 100%;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h2>Request Access to Software</h2>
    <form action="requestservlet" method="post">
        <label for="software">Software Name:</label>
        <select id="software" name="software" required>
            <%
                for (String software : softwareList) {
                    out.print("<option value='" + software + "'>" + software + "</option>");
                }
            %>
        </select>

        <label for="accessType">Access Type:</label>
        <select id="accessType" name="accessType" required>
            <option value="Read">Read</option>
            <option value="Write">Write</option>
            <option value="Admin">Admin</option>
        </select>

        <label for="reason">Reason for Request:</label>
        <textarea id="reason" name="reason" required></textarea>

        <input type="submit" value="Submit Request">
    </form>
</body>
</html>
