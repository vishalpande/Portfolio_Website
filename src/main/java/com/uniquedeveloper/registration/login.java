package com.uniquedeveloper.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String uemail=request.getParameter("username");
		String up=request.getParameter("password");
		HttpSession session=request.getSession();
		Connection con=null;
		RequestDispatcher dispatcher=null;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
       	 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/signup1?useSSL=false","root","1234");
			PreparedStatement pst=con.prepareStatement("select* from users where uemail=? and up=?");
			
			pst.setString(1, uemail);
			pst.setString(2, up);
			
			ResultSet rs=pst.executeQuery();
			
			if(rs.next()) {
				session.setAttribute("name", rs.getString("uname"));
				
				dispatcher=request.getRequestDispatcher("index.jsp");
			}else {
				
				request.setAttribute("status", "faild");
				dispatcher=request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
