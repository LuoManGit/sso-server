package com.man.ssoserver.commons;

import com.man.ssoserver.controller.SSOController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author luoman
 * @createTime 2019 -05 -10 15:11
 * @description 设置cookie的通用类
 */
public class CookieUtil {
    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);
    public static void setCookie(String name, String value, int maxAge, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        //设置Maximum Age
        cookie.setMaxAge(maxAge);
        //设置cookie路径为当前项目路径
        String path = "/";
        logger.info("set cookie path：" + path);
        cookie.setPath(path);
        //添加cookie
        response.addCookie(cookie);
    }

}
