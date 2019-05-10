package com.man.ssoserver.controller;

import com.man.ssoserver.Service.UserService;
import com.man.ssoserver.commons.JWTUtils;
import com.man.ssoserver.config.Constants;
import com.man.ssoserver.dao.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.io.IOException;

@RestController
@RequestMapping("/sso")
public class SSOController {
    private static final Logger logger = LoggerFactory.getLogger(SSOController.class);

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public ModelAndView login(String service, @CookieValue(value = Constants.SSOCOOKIE, defaultValue = "") String castgc, HttpServletResponse response, HttpServletRequest request){

        logger.info(service);
        if(castgc.equals("")) {//没有cookie
            logger.info("没有cookie，返回登录页面");
        }
        else{
            String userid = JWTUtils.getString(castgc,Constants.SCRETE_KEY);
            if(userid ==null||userService.getUserById(userid)==null){
                logger.info("cookie验证失败，返回登录页面");
            }
            else{
                try {
                    response.sendRedirect(service + "?ticket=" + castgc);
                }
                catch (IOException e){
                    logger.error("重定向失败");
                }
            }
        }
        ModelAndView modelAndView = new ModelAndView("/login");
        modelAndView.addObject("service", service);
        return modelAndView;
    }
    @PostMapping("/doLogin")
    public ModelAndView dologin(String service,HttpServletRequest request,HttpServletResponse response)throws Exception {
        String userName = request.getParameter("name");
        String password = request.getParameter("password");
        if (userName.equals("luoman") && password.equals("luoman")) {
            User user = userService.getUserByName(userName);
            String csatgc = JWTUtils.createAccessToken(user.getId(), Constants.SCRETE_KEY, 3, 1);
            setCookie(Constants.SSOCOOKIE, csatgc, 100000, request, response);
            String redirctUrl = service + "?ticket=" + csatgc;
            logger.info("重定向到" + redirctUrl);

            response.sendRedirect(redirctUrl);
        }

        ModelAndView modelAndView = new ModelAndView("/login");
        modelAndView.addObject("service", service);
        modelAndView.addObject("msg", "用户名或者密码错误");
        return modelAndView;
    }

    @GetMapping(value = "/validate")
    @ResponseBody
    public String validate(String ticket, HttpServletRequest request) {
        try {
            logger.info("validate,:{},:{}", ticket);

            String userId = JWTUtils.getString(ticket, Constants.SCRETE_KEY);
            if(userId==null){
                return "no\n\n";
            }
            User user = userService.getUserById(userId);
            if(user==null){
                return "no\n\n";
            }
            String userCName = user.getName();
            return "yes\n"+userCName+"\n";// Cas10TicketValidator.parseResponseFromServer 方法读取 Validate返回值的第二行 作为验证用户
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage(), e);
            return "no\n\n";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "no\n\n";
        }
    }
    private void setCookie(String name,String value,int maxAge,HttpServletRequest request,HttpServletResponse response){
        Cookie cookie=new Cookie(name,value);
        //设置Maximum Age
        cookie.setMaxAge(maxAge);
        //设置cookie路径为当前项目路径
        String path = request.getServletPath();
        logger.info("cookie Path"+path);
        cookie.setPath(path);
        //添加cookie
        response.addCookie(cookie);

    }






}
