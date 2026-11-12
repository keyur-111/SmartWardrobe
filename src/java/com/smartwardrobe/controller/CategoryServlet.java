package com.smartwardrobe.controller;

import com.smartwardrobe.model.Category;
import com.smartwardrobe.service.CategoryService;
import com.smartwardrobe.util.WebUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/categories")
public class CategoryServlet extends HttpServlet {

    private final CategoryService cs = new CategoryService();

    @Override
    protected void doGet(
            HttpServletRequest r,
            HttpServletResponse s)
            throws ServletException, IOException {

        if (!WebUtil.admin(r)) {
            s.sendRedirect(
                    r.getContextPath() + "/login"
            );
            return;
        }

        try {
            r.setAttribute(
                    "categories",
                    cs.all()
            );

            r.getRequestDispatcher(
                    "/admin/categories.jsp"
            ).forward(r, s);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest r,
            HttpServletResponse s)
            throws ServletException, IOException {

        try {
            String a = r.getParameter("action");

            if ("delete".equals(a)) {

                String idValue = r.getParameter("id");

                if (idValue == null || idValue.trim().isEmpty()) {
                    throw new IllegalArgumentException(
                            "Category ID is missing."
                    );
                }

                int id = Integer.parseInt(
                        idValue.trim()
                );

                cs.delete(id);

            } else {

                Category c = new Category();

                c.setCategoryName(
                        WebUtil.safe(
                                r.getParameter("name")
                        )
                );

                c.setDescription(
                        WebUtil.safe(
                                r.getParameter("description")
                        )
                );

                cs.add(c);
            }

            s.sendRedirect(
                    r.getContextPath()
                            + "/admin/categories"
            );

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}