package com.shin.noticeboard.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shin.noticeboard.model.NoticeAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;
    @Autowired
    public LoginCheckInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("LoginCheckInterceptor - preHandle");

//        HttpSession session = request.getSession(false);
//        if(session == null || session.getAttribute("sessionId") == null) {
//            log.info("LoginCheckInterceptor - preHandle session null");
//            return false;
//        }
//
//        NoticeAccount noticeAccount = (NoticeAccount) session.getAttribute("sessionId");
//        return !noticeAccount.getUserid().isEmpty() && !noticeAccount.getPassword().isEmpty();

        return true;
    }
}
