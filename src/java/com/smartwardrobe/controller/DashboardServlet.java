package com.smartwardrobe.controller;
import com.smartwardrobe.service.ClothingService;
import com.smartwardrobe.service.LaundryService;
import com.smartwardrobe.util.WebUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final ClothingService clothingService = new ClothingService();
    private final LaundryService laundryService = new LaundryService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Integer uid = WebUtil.userId(request);
        // Dashboard is a protected page. If the user is not logged in,
        // redirect to login instead of throwing a NullPointerException.
        if (uid == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try {
            request.setAttribute("clothes", clothingService.list(uid, "", "", ""));
            request.setAttribute("laundry", laundryService.list(uid));
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        }
        catch (Exception e) {
            throw new ServletException("Unable to load dashboard", e);
        }

    }

}
