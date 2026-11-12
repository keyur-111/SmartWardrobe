package com.smartwardrobe.controller;
import com.smartwardrobe.model.User;
import com.smartwardrobe.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/login") public class LoginServlet extends HttpServlet {
    private final UserService service=new UserService();
    protected void doGet(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException {
        r.getRequestDispatcher("/login.jsp").forward(r,s);
    }
    protected void doPost(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException {
        try {
            User u=service.login(r.getParameter("email"),r.getParameter("password"));
            if(u==null) {
                r.setAttribute("error","Invalid email or password.");
                doGet(r,s);
                return;
            }
            HttpSession ss=r.getSession();
            ss.setAttribute("userId",u.getUserId());
            ss.setAttribute("userName",u.getName());
            ss.setAttribute("email",u.getEmail());
            ss.setAttribute("role",u.getRole());
            s.sendRedirect(r.getContextPath()+("ADMIN".equals(u.getRole())?"/admin":"/dashboard"));
        }
        catch(Exception e) {
            throw new ServletException(e);
        }

    }

}
