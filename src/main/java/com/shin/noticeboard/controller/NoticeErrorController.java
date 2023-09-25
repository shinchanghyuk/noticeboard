package com.shin.noticeboard.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NoticeErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(NoticeBoardController.class);
    private String VIEW_PATH = "/error/";

    // 에러발생
    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());

            logger.info("NoticeErrorController - handleError statusCode : " + statusCode);

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return VIEW_PATH + "404.html";
            }
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return VIEW_PATH + "500.html";
            }

            if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return VIEW_PATH + "401.html";
            }
        }

        return "error";
    }

    public String getErrorPath() {
        return null;
    }
}
