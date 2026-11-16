package com.smartwardrobe.controller;
import com.smartwardrobe.service.*;
import com.smartwardrobe.util.WebUtil;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/admin") public class AdminServlet extends HttpServlet {
    private final UserService us=new UserService();
    private final ClothingService cs=new ClothingService();
    private final CategoryService gs=new CategoryService();
    protected void doGet(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException {
        if(!WebUtil.admin(r)) {
            s.sendRedirect(r.getContextPath()+"/login");
            return;
        }
        try {
            HttpSession session=r.getSession(false);
            String csrfToken=(String)session.getAttribute("adminCsrfToken");
            if(csrfToken==null) {
                csrfToken=UUID.randomUUID().toString();
                session.setAttribute("adminCsrfToken",csrfToken);
            }
            r.setAttribute("csrfToken",csrfToken);
            r.setAttribute("users",us.all());
            r.setAttribute("items",cs.all());
            r.setAttribute("categories",gs.all());
            r.getRequestDispatcher("/admin/dashboard.jsp").forward(r,s);
        }
        catch(Exception e) {
            throw new ServletException(e);
        }

    }
    protected void doPost(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException {
        HttpSession session=r.getSession(false);
        if(session==null||!WebUtil.admin(r)) {
            s.sendRedirect(r.getContextPath()+"/login");
            return;
        }
        String expectedToken=(String)session.getAttribute("adminCsrfToken");
        String suppliedToken=r.getParameter("csrfToken");
        if(expectedToken==null||!expectedToken.equals(suppliedToken)) {
            s.sendError(HttpServletResponse.SC_FORBIDDEN,"Invalid session token.");
            return;
        }
        try {
            String action=r.getParameter("action");
            if("deleteUser".equals(action)) {
                if(!us.delete(parseId(r.getParameter("id")))) {
                    throw new ServletException("User not found.");
                }
            } else if("updateUser".equals(action)) {
                String name=WebUtil.safe(r.getParameter("name"));
                String email=WebUtil.safe(r.getParameter("email")).toLowerCase(Locale.ROOT);
                if(name.length()<2||name.length()>100||email.length()>100||!email.contains("@")) {
                    throw new ServletException("Enter a valid name and email address.");
                }
                if(!us.updateIdentity(parseId(r.getParameter("id")),name,email)) {
                    throw new ServletException("User not found or cannot be updated.");
                }
            } else if("changeAdminPassword".equals(action)) {
                String password=r.getParameter("newPassword");
                String confirmation=r.getParameter("confirmPassword");
                Integer userId=WebUtil.userId(r);
                if(password==null||password.length()<16||!password.equals(confirmation)) {
                    throw new ServletException("Use a password of at least 16 characters and confirm it.");
                }
                if(userId==null||!us.updateAdminPassword(userId,password)) {
                    throw new ServletException("The signed-in admin account could not be updated.");
                }
            } else {
                throw new ServletException("Unknown admin action.");
            }
            s.sendRedirect(r.getContextPath()+"/admin?updated=1");
        }
        catch(Exception e) {
            throw new ServletException(e);
        }

    }
    private int parseId(String value)throws ServletException {
        if(value==null||value.trim().isEmpty()) {
            throw new ServletException("User ID is missing.");
        }
        try {
            return Integer.parseInt(value.trim());
        } catch(NumberFormatException e) {
            throw new ServletException("Invalid user ID.",e);
        }
    }

}
