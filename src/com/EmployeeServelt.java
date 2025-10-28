package com.example.web;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmployeeServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/webapp";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "password";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String empid = request.getParameter("empid");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            Statement st = con.createStatement();

            String query;
            if (empid != null && !empid.isEmpty()) {
                query = "SELECT * FROM Employee WHERE EmpID=" + empid;
            } else {
                query = "SELECT * FROM Employee";
            }

            ResultSet rs = st.executeQuery(query);
            out.println("<h2>Employee Records</h2>");
            out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Salary</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("EmpID") + "</td><td>" +
                        rs.getString("Name") + "</td><td>" + rs.getDouble("Salary") + "</td></tr>");
            }
            out.println("</table>");

            con.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
