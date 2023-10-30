package com.shin.noticeboard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class NoticeErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(NoticeErrorController.class);
    private final String VIEW_PATH = "/error";

    @GetMapping(VIEW_PATH)
    public String redirectRoot(){
        logger.info("NoticeErrorController- redirectRoot");

        return "index.html";
    }

    // @GetMapping("/boardMain")
    // public String boardMain(){
    //     logger.info("NoticeErrorController - boardMain");

    //     return "index.html";
    // }

    // @GetMapping("/boardRegister")
    // public String boardRegister(){
    //     logger.info("NoticeErrorController - boardRegister");

    //     return "index.html";
    // }

    // @GetMapping("/boardWrite")
    // public String boardWrite(){
    //     logger.info("NoticeErrorController - boardWrite");

    //     return "index.html";
    // }

    // @GetMapping("/boardDetail")
    // public String boardDetail(){
    //     logger.info("NoticeErrorController - boardDetail");

    //     return "index.html";
    // }

    // @GetMapping("/boardAccount")
    // public String boardAccount(){
    //     logger.info("NoticeErrorController - boardAccount");

    //     return "index.html";
    // }

    public String getErrorPath(){
        return null;
    }
}
