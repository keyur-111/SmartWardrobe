package com.smartwardrobe.controller;

import com.smartwardrobe.model.Clothing;
import com.smartwardrobe.model.Outfit;
import com.smartwardrobe.service.ClothingService;
import com.smartwardrobe.service.OutfitService;
import com.smartwardrobe.util.WebUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/outfits")
public class OutfitServlet extends HttpServlet {

    private final ClothingService cs =
            new ClothingService();

    private final OutfitService os =
            new OutfitService();

    @Override
    protected void doGet(
            HttpServletRequest r,
            HttpServletResponse s)
            throws ServletException, IOException {

        Integer u = WebUtil.userId(r);

        if (u == null) {
            s.sendRedirect(
                    r.getContextPath() + "/login"
            );
            return;
        }

        try {

            String occasion =
                    r.getParameter("occasion");

            String season =
                    r.getParameter("season");

            if (occasion != null && season != null) {

                List<Clothing> pool =
                        cs.pool(u, occasion, season);

                Map<String, Clothing> chosen =
                        new LinkedHashMap<>();

                for (Clothing x : pool) {

                    String category =
                            x.getCategoryName();

                    if ("Tops".equals(category)
                            || "Bottoms".equals(category)
                            || "Footwear".equals(category)) {

                        chosen.putIfAbsent(
                                category,
                                x
                        );
                    }
                }

                for (Clothing x : pool) {

                    if ("Outerwear".equals(
                            x.getCategoryName())
                            && !chosen.containsKey(
                                    "Outerwear")) {

                        chosen.put(
                                "Outerwear",
                                x
                        );

                        break;
                    }
                }

                r.setAttribute(
                        "generated",
                        new ArrayList<>(
                                chosen.values()
                        )
                );

                r.setAttribute(
                        "occasion",
                        occasion
                );

                r.setAttribute(
                        "season",
                        season
                );
            }

            r.setAttribute(
                    "outfits",
                    os.list(u)
            );

            r.getRequestDispatcher(
                    "/outfit-generator.jsp"
            ).forward(r, s);

        } catch (Exception e) {

            throw new ServletException(
                    "Unable to generate outfit",
                    e
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest r,
            HttpServletResponse s)
            throws ServletException, IOException {

        Integer u = WebUtil.userId(r);

        if (u == null) {
            s.sendRedirect(
                    r.getContextPath() + "/login"
            );
            return;
        }

        try {

            String a =
                    r.getParameter("action");

            if ("delete".equals(a)) {

                String idValue =
                        r.getParameter("id");

                if (idValue == null
                        || idValue.trim().isEmpty()) {

                    throw new IllegalArgumentException(
                            "Outfit ID is missing."
                    );
                }

                int id =
                        Integer.parseInt(
                                idValue.trim()
                        );

                os.delete(id, u);

            } else {

                String[] vals =
                        r.getParameterValues(
                                "clothingId"
                        );

                List<Integer> ids =
                        new ArrayList<>();

                if (vals != null) {

                    for (String v : vals) {

                        if (v == null) {
                            continue;
                        }

                        String cleaned =
                                v.trim();

                        if (cleaned.isEmpty()) {
                            continue;
                        }

                        ids.add(
                                Integer.parseInt(
                                        cleaned
                                )
                        );
                    }
                }

                if (ids.size() >= 3) {

                    Outfit o =
                            new Outfit();

                    o.setUserId(u);

                    o.setOutfitName(
                            WebUtil.safe(
                                    r.getParameter(
                                            "outfitName"
                                    )
                            )
                    );

                    o.setOccasion(
                            WebUtil.safe(
                                    r.getParameter(
                                            "occasion"
                                    )
                            )
                    );

                    o.setSeason(
                            WebUtil.safe(
                                    r.getParameter(
                                            "season"
                                    )
                            )
                    );

                    os.save(o, ids);
                }
            }

            s.sendRedirect(
                    r.getContextPath() + "/outfits"
            );

        } catch (Exception e) {

            throw new ServletException(
                    "Unable to save outfit",
                    e
            );
        }
    }
}