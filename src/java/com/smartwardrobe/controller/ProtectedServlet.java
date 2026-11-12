package com.smartwardrobe.controller;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/protected-check") public class ProtectedServlet extends HttpServlet {
    protected void doGet(HttpServletRequest r,HttpServletResponse s)throws IOException {
        if(r.getSession(false)==null||r.getSession(false).getAttribute("userId")==null)s.sendRedirect(r.getContextPath()+"/login");
        else s.sendRedirect(r.getContextPath()+"/dashboard");
    }

}
