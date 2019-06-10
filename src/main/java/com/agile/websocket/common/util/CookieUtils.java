package com.agile.websocket.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyi
 * @date 2019/6/4
 */
public class CookieUtils {

    /**
     * 根据名字获取cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }


    /**
     * 将cookie封装到map
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for(Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 保存cookies
     * @param response
     * @param name
     * @param value
     * @param time 秒
     * @return
     */
    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value, int time) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        try {
            URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        cookie.setMaxAge(time);
        response.addCookie(cookie);
        return response;
    }

    /**
     * 删除无效cookie
     * @param request
     * @param response
     * @param deleteKey
     * @throws NullPointerException
     */
    public static void deleteCookieByName(HttpServletRequest request, HttpServletResponse response, String deleteKey) throws NullPointerException {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        for (String key : cookieMap.keySet()) {
            if (key.equals(deleteKey)) {
                Cookie cookie = cookieMap.get(key);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
    }

}
