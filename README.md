The User Access Management System (UAMS) is a web-based application designed to manage user roles and permissions within an organization.
It enables administrators, managers, and employees to perform specific actions based on their roles. The system handles user authentication,
role-based access control, and the approval of requests related to software access.

Key Features:

Role-based Access Control (RBAC): Different roles, such as Admin, Manager, and Employee, with permissions for software management and approval workflows.
Software Management: Admins can create new software applications.
Request Approval: Managers and Admins can approve or reject access requests for software.
User Authentication: Secure login and session management for all roles.

Technologies Used:

Frontend: JSP (JavaServer Pages), HTML, CSS
Backend: Java Servlets
Database: PostgreSQL
Other: JDBC (Java Database Connectivity)

Setup PostgreSQL Database:

Create a PostgreSQL database called leucine and set up the required tables.

You can use the following SQL script to create the necessary tables:

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE software (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    access_levels TEXT
);

CREATE TABLE requests (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    software_id INT REFERENCES software(id),
    status VARCHAR(20) DEFAULT 'Pending'
);

Features
1. Employee Authentication
Users can log in with their credentials (username and password).
Access levels are assigned based on the role (Admin, Manager,).
2. Admin Role
Can create new software applications.
Can approve or reject requests from employees.
3. Manager Role
Can approve or reject requests from employees but cannot create new software applications.
4. Employee Role
Can request access to software.
5. Pending Requests
Admins and Managers can view and manage pending requests for software access.
