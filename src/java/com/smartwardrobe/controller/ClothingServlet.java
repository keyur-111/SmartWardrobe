package com.smartwardrobe.controller;

import com.smartwardrobe.model.Clothing;
import com.smartwardrobe.service.CategoryService;
import com.smartwardrobe.service.ClothingService;
import com.smartwardrobe.util.WebUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/wardrobe")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 7 * 1024 * 1024
)
public class ClothingServlet extends HttpServlet {

    private final ClothingService clothingService = new ClothingService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = WebUtil.userId(request);

        if (userId == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return;
        }

        try {
            String action = request.getParameter("action");

            if ("add".equals(action)) {
                request.setAttribute(
                        "categories",
                        categoryService.all()
                );

                request.getRequestDispatcher(
                        "/clothing-form.jsp"
                ).forward(request, response);

                return;
            }

            if ("edit".equals(action)) {
                int clothingId = parseInt(
                        request.getParameter("id")
                );

                request.setAttribute(
                        "item",
                        clothingService.find(
                                clothingId,
                                userId
                        )
                );

                request.setAttribute(
                        "categories",
                        categoryService.all()
                );

                request.getRequestDispatcher(
                        "/clothing-form.jsp"
                ).forward(request, response);

                return;
            }

            request.setAttribute(
                    "items",
                    clothingService.list(
                            userId,
                            request.getParameter("search"),
                            request.getParameter("category"),
                            request.getParameter("status")
                    )
            );

            request.setAttribute(
                    "categories",
                    categoryService.all()
            );

            request.getRequestDispatcher(
                    "/wardrobe.jsp"
            ).forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                    "Unable to load wardrobe",
                    e
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = WebUtil.userId(request);

        if (userId == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return;
        }

        String action = request.getParameter("action");

        try {

            if ("delete".equals(action)) {

                int clothingId = parseInt(
                        request.getParameter("id")
                );

                clothingService.delete(
                        clothingId,
                        userId
                );
            }

            else if ("laundry".equals(action)) {

                int clothingId = parseInt(
                        request.getParameter("id")
                );

                clothingService.laundry(
                        clothingId,
                        userId,
                        request.getParameter("status")
                );
            }

            else if ("usage".equals(action)) {

                int clothingId = parseInt(
                        request.getParameter("id")
                );

                clothingService.usage(
                        clothingId,
                        userId
                );
            }

            else {

                Clothing clothing = new Clothing();

                clothing.setUserId(userId);

                clothing.setCategoryId(
                        parseInt(
                                request.getParameter("categoryId")
                        )
                );

                clothing.setName(
                        WebUtil.safe(
                                request.getParameter("name")
                        )
                );

                clothing.setColor(
                        WebUtil.safe(
                                request.getParameter("color")
                        )
                );

                clothing.setFabric(
                        WebUtil.safe(
                                request.getParameter("fabric")
                        )
                );

                clothing.setSeason(
                        WebUtil.safe(
                                request.getParameter("season")
                        )
                );

                clothing.setOccasion(
                        WebUtil.safe(
                                request.getParameter("occasion")
                        )
                );

                clothing.setStatus(
                        WebUtil.safe(
                                request.getParameter("status")
                        )
                );

                String existingImage =
                        WebUtil.safe(
                                request.getParameter("existingImage")
                        );

                String imageName =
                        saveUploadedImage(request);

                if (imageName == null
                        || imageName.isEmpty()) {

                    imageName = existingImage;
                }

                clothing.setImage(imageName);

                if ("update".equals(action)) {

                    clothing.setClothingId(
                            parseInt(
                                    request.getParameter("id")
                            )
                    );

                    clothingService.update(clothing);

                } else {

                    clothingService.add(clothing);
                }
            }

            response.sendRedirect(
                    request.getContextPath() + "/wardrobe"
            );

        } catch (Exception e) {

            throw new ServletException(
                    "Unable to save wardrobe item",
                    e
            );
        }
    }

    private int parseInt(String value) {

        if (value == null) {
            throw new IllegalArgumentException(
                    "Required number is missing."
            );
        }

        String cleaned = value.trim();

        if (cleaned.isEmpty()) {
            throw new IllegalArgumentException(
                    "Required number is empty."
            );
        }

        return Integer.parseInt(cleaned);
    }

    private String saveUploadedImage(
            HttpServletRequest request)
            throws IOException, ServletException {

        Part part = request.getPart("imageFile");

        if (part == null || part.getSize() == 0) {
            return "";
        }

        String submittedFileName =
                part.getSubmittedFileName();

        if (submittedFileName == null
                || submittedFileName.trim().isEmpty()) {
            return "";
        }

        String originalName =
                new File(submittedFileName).getName();

        int dot =
                originalName.lastIndexOf('.');

        String extension =
                dot > 0
                        ? originalName.substring(dot).toLowerCase()
                        : "";

        if (!".jpg".equals(extension)
                && !".jpeg".equals(extension)
                && !".png".equals(extension)
                && !".webp".equals(extension)) {

            throw new ServletException(
                    "Only JPG, JPEG, PNG and WEBP images are allowed."
            );
        }

        String fileName =
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        + extension;

        String uploadPath =
                getServletContext().getRealPath("/uploads");

        if (uploadPath == null) {
            throw new ServletException(
                    "Upload folder is unavailable."
            );
        }

        Path uploadDirectory =
                Paths.get(uploadPath);

        Files.createDirectories(
                uploadDirectory
        );

        Path target =
                uploadDirectory.resolve(fileName);

        try (InputStream input =
                     part.getInputStream()) {

            Files.copy(
                    input,
                    target,
                    StandardCopyOption.REPLACE_EXISTING
            );
        }

        return fileName;
    }
}