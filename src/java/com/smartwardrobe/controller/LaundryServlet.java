package com.smartwardrobe.controller;
import com.smartwardrobe.model.Laundry;
import com.smartwardrobe.service.*;
import com.smartwardrobe.util.WebUtil;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/laundry") public class LaundryServlet extends HttpServlet {
    private final LaundryService ls=new LaundryService();
    private final ClothingService cs=new ClothingService();
    protected void doGet(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException {
        Integer u=WebUtil.userId(r);
        if(u==null) {
            s.sendRedirect(r.getContextPath()+"/login");
            return;
        }
        try {
            r.setAttribute("laundry",ls.list(u));
            r.setAttribute("clothes",cs.list(u,"","","In Laundry"));
            r.getRequestDispatcher("/laundry.jsp").forward(r,s);
        }
        catch(Exception e) {
            throw new ServletException(e);
        }

    }
    protected void doPost(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException {
        Integer u=WebUtil.userId(r);
        if(u==null) {
            s.sendRedirect(r.getContextPath()+"/login");
            return;
        }
        try {
            if("complete".equals(r.getParameter("action")))ls.complete(parseInt(r.getParameter("id")),u);
            else {
                Laundry x=new Laundry();
                x.setClothingId(parseInt(r.getParameter("clothingId")));
                if(cs.find(x.getClothingId(),u)==null) {
                    throw new ServletException("The selected item does not belong to this account.");
                }
                x.setLaundryDate(LocalDate.now().toString());
                x.setReturnDate("");
                x.setStatus("In Laundry");
                ls.add(x);
                cs.laundry(x.getClothingId(),u,"In Laundry");
            }
            s.sendRedirect(r.getContextPath()+"/laundry");
        }
        catch(Exception e) {
            throw new ServletException(e);
        }

    }
    private int parseInt(String value) {
        if(value==null||value.trim().isEmpty()) {
            throw new IllegalArgumentException("Required number is missing.");
        }
        return Integer.parseInt(value.trim());
    }

}
