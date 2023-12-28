package com.shin.noticeboard.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shin.noticeboard.model.NoticeAccount;
import com.shin.noticeboard.model.RestReturnVo;
import com.shin.noticeboard.service.NoticeAccountService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// 검증 , 쿠키 , 세션 ,
@RestController
@Slf4j
public class NoticeAccountController {
    
    private final ObjectMapper objectMapper;
    private final NoticeAccountService noticeAccountService;

    @Autowired
    public NoticeAccountController(ObjectMapper objectMapper, NoticeAccountService noticeAccountService) {
        this.objectMapper = objectMapper;
        this.noticeAccountService = noticeAccountService;
    }

    @GetMapping("/logout")
    public void logout(HttpSession session) {
        noticeAccountService.logout(session);
    }

    // 아이디 로그인
    @PostMapping("/login")
    public RestReturnVo login(@RequestBody NoticeAccount noticeAccount, HttpSession session) {
        RestReturnVo result = null;

        log.info("NoticeAccountController - login noticeAccount : {}", noticeAccount.toString());

        result = noticeAccountService.login(noticeAccount, session);
        log.info("NoticeAccountController - login result : {}", result.toString());

        // 프론트에 데이터 전달 로직추가
        return result;
    }

    // 아이디 생성
    @PostMapping("/account/register")
    public void create(@RequestBody NoticeAccount noticeAccount) {

        log.info("NoticeAccountController - register noticeAccount : {}", noticeAccount.toString());

        // 에러를 던지면 response status가 200이 아님
        noticeAccountService.register(noticeAccount);
    }

    // 사용자 수정
    @PostMapping("/account/modify")
    public void modify(@RequestBody NoticeAccount noticeAccount) {
        log.info("NoticeAccountController - modify noticeAccount : {}", noticeAccount);

        noticeAccountService.modify(noticeAccount);
    }

    // 사용자 삭제
    @PostMapping(value = "/account/delete")
    public void delete(@RequestBody String requestPayload) {
        List<String> id = new ArrayList<>();

        log.info("NoticeAccountController - delete requestPayload : {}", requestPayload);

        try {
            id = objectMapper.readValue(requestPayload, new TypeReference<List<String>>(){});

        } catch (Exception e) {
            log.error("NoticeAccountController - delete Exception : ", e);
        }

        noticeAccountService.delete(id);
    }
}
