package com.java.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yusheng ZENG
 * @version 1.0
 */
public abstract class CookieUtil {


    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, -1, null, null, false);
    }

    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(response, name, value, maxAge, null, null, false);
    }

    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge, String domain, String path, boolean secure) {

        if (name == null || value == null) {
            return;
        }

        try {
            Cookie cookie = new Cookie(name, value);

            if (domain != null) {
                cookie.setDomain(domain);
            }
//            cookie.setHttpOnly(true);
            cookie.setPath(path == null ? "/" : path);
            cookie.setMaxAge(maxAge);
            cookie.setSecure(secure);
            response.addCookie(cookie);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Cookie[] getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return cookies;
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || name == null) {
            return null;
        }

        Cookie cookie = null;
        for (Cookie c : cookies) {
            if (c.getName().equals(name)) {
                cookie = c;
                break;
            }
        }
        return cookie;
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        return cookie == null ? null : cookie.getValue();
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        deleteCookie(request, response, name, null, null);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain, String path) {

        Cookie cookie = getCookie(request, name);

        if (cookie == null) {
            return;
        }

        int maxAge = 0; // Delete by setting its maxage to zero
        setCookie(response, name, cookie.getValue(), maxAge, domain, path, false);
    }
}


