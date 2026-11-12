package com.smartwardrobe.util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
public final class WebUtil {
    private WebUtil() {
    }
    public static Integer userId(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("userId") == null) return null;
        return (Integer) s.getAttribute("userId");
    }
    public static boolean admin(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s != null && "ADMIN".equals(s.getAttribute("role"));
    }
    public static String safe(String value) {
        return value == null ? "" : value.trim();
    }

}
