package com.example.web;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AttendanceServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/webapp";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String studentId = request.getParameter("studentId");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Attendance(StudentID, Date, Status) VALUES (?, ?, ?)");
            ps.setString(1, studentId);
            ps.setString(2, date);
            ps.setString(3, status);

            int i = ps.executeUpdate();
            if (i > 0) {
                response.sendRedirect("success.jsp");
            } else {
                out.println("<h3>Failed to save attendance. Try again.</h3>");
            }
            con.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
