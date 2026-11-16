package com.smartwardrobe.controller;
import com.smartwardrobe.service.*;
import com.smartwardrobe.util.WebUtil;
import java.io.IOException;
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
        if(!WebUtil.admin(r)) {
            s.sendRedirect(r.getContextPath()+"/login");
            return;
        }
        try {
            if("deleteUser".equals(r.getParameter("action"))) {
                String id=r.getParameter("id");
                if(id==null||id.trim().isEmpty()) {
                    throw new ServletException("User ID is missing.");
                }
                us.delete(Integer.parseInt(id.trim()));
            }
            s.sendRedirect(r.getContextPath()+"/admin");
        }
        catch(Exception e) {
            throw new ServletException(e);
        }

    }

}
