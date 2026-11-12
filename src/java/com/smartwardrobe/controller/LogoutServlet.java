package com.smartwardrobe.controller;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/logout") public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest r,HttpServletResponse s)throws IOException {
        HttpSession x=r.getSession(false);
        if(x!=null)x.invalidate();
        s.sendRedirect(r.getContextPath()+"/login");
    }

}
