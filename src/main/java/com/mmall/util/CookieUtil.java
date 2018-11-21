package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {

    //private static final String COOKIE_DOMAIN = ".happy.com";
    private static final String COOKIE_DOMAIN = "47.104.135.45"; // 只针对阿里云的环境
    public static final String COOKIE_NAME = "mmall_login_token";

    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("read cookieName {}, cookieValue {}", cookie.getName(), cookie.getValue());
                if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
                    log.info("return cookieName{}, cookieValue{}", cookie.getName(), cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // x:domain = ".happymmall.com"  一级域名
    // a:A.happymmall.com               cookie:domain = A.happymmall.com; path = "/";
    // b:B.happymmall.com               cookie:domain = B.happymmall.com; path = "/";
    // c:A.happymmall.com/test/cc       cookie:domain = A.happymmall.com; path = "/test/cc";
    // d:A.happymmall.com/test/dd       cookie:domain = A.happymmall.com; path = "/test/dd";
    // e:A.happymmall.com/test          cookie:domain = A.happymmall.com; path = "/test";
    // 5个都能共享x的cookie， a 和 b 互不共享， c、d 和 e 能共享 a 的cookie；
    // c 和 d 互不共享， e 可以共享 c he d 的cookie

    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setDomain(COOKIE_DOMAIN);
        // 表示设置在根目录
        cookie.setPath("/");
        cookie.setHttpOnly(true); // 设置为true，无法通过脚本访问cookie

        // 如果不设置，则cookie不会写入硬盘，而是写入内存，只在当前页面有效.
        cookie.setMaxAge(60 * 60 * 24 * 365); // 单位是秒，如果是-1，表示永久有效
        log.info("write cookieName {}, cookieValue {}", cookie.getName(), cookie.getValue());
        response.addCookie(cookie);
    }

    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    cookie.setMaxAge(0); // 设置成0，表示删除此cookie
                    log.info("delete cookieName {}, cookieValue {}", cookie.getName(), cookie.getValue());
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }
}
