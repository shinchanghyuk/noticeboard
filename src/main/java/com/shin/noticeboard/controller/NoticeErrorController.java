package com.shin.noticeboard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
public class NoticeErrorController implements ErrorController {

    private final String VIEW_PATH = "/error";

    // 서버가 매핑하지 않은 URL이 들어왔을 때 index.html을 프론트로 보내주고
    // 프론트에서 라우팅을 통해 컴포넌트가 있는지 확인
    // 없으면 프론트에서 에러 페이지 처리
    @GetMapping(VIEW_PATH)
    public String redirectRoot(){
        log.info("NoticeErrorController- redirectRoot");

        return "index.html";
    }

    public String getErrorPath(){
        return null;
    }
}
