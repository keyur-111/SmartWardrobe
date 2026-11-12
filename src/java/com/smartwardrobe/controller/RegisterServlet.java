package com.smartwardrobe.controller;
import com.smartwardrobe.model.User;
import com.smartwardrobe.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/register") public class RegisterServlet extends HttpServlet {
    private final UserService service=new UserService();
    protected void doGet(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException {
        r.getRequestDispatcher("/register.jsp").forward(r,s);
    }
    protected void doPost(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException {
        String n=r.getParameter("name"),e=r.getParameter("email"),p=r.getParameter("password"),cp=r.getParameter("confirmPassword");
        if(n==null||n.trim().length()<2||e==null||!e.contains("@")||p==null||p.length()<6||!p.equals(cp)) {
            r.setAttribute("error","Please enter valid details. Password must be at least 6 characters and match confirmation.");
            doGet(r,s);
            return;
        }
        try {
            if(service.exists(e)) {
                r.setAttribute("error","An account with this email already exists.");
                doGet(r,s);
                return;
            }
            User u=new User();
            u.setName(n.trim());
            u.setEmail(e.trim().toLowerCase());
            u.setPassword(p);
            service.register(u);
            s.sendRedirect(r.getContextPath()+"/login?registered=1");
        }
        catch(Exception ex) {
            throw new ServletException(ex);
        }

    }

}
