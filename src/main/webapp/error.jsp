<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error - Something Went Wrong</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8d7da;
            color: #721c24;
            margin: 0;
            padding: 20px;
            text-align: center;
        }
        h1 {
            color: #c7254e;
        }
        p {
            font-size: 18px;
        }
        a {
            color: #0056b3;
            text-decoration: none;
            font-weight: bold;
        }
        a:hover {
            text-decoration: underline;
        }
        .container {
            max-width: 600px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Oops! Something Went Wrong.</h1>
        <p>We're sorry, but an unexpected error has occurred. Please try again later.</p>
        <p>If the problem persists, contact support.</p>
        <p>
            <a href="index.jsp">Return to Home</a> |
            <a href="pendingRequests.jsp">Go Back</a>
        </p>
    </div>
</body>
</html>
