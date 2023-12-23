package com.shin.noticeboard.controller;

import javax.servlet.http.HttpServletRequest;

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

// 검증 , 쿠키 , 세션 ,
@RestController
@RequestMapping("/noticeboard")
@Slf4j
public class NoticeAccountController {
    
    private final ObjectMapper objectMapper;
    private final NoticeAccountService noticeAccountService;

    @Autowired
    public NoticeAccountController(ObjectMapper objectMapper, NoticeAccountService noticeAccountService) {
        this.objectMapper = objectMapper;
        this.noticeAccountService = noticeAccountService;
    }

    // 아이디 로그인
    @PostMapping(value = "/login")
    @ResponseBody
    public RestReturnVo login(@RequestBody NoticeAccount noticeAccount) {
        RestReturnVo result = null;

        log.info("NoticeAccountController - login noticeAccount : {}", noticeAccount.toString());

        result = noticeAccountService.login(noticeAccount);
        log.info("NoticeAccountController - login result : {}", result.toString());

        // 프론트에 데이터 전달 로직추가
        return result;
    }

    // 아이디 생성
    @PostMapping(value = "/create")
    @ResponseBody
    public void create(@RequestBody NoticeAccount noticeAccount) {

        log.info("NoticeAccountController - create noticeAccount : {}", noticeAccount.toString());

        // 에러를 던지면 response status가 200이 아님
        noticeAccountService.create(noticeAccount);
    }

    // 사용자 수정
    @PostMapping(value = "/accountModify")
    public void modify(@RequestBody NoticeAccount noticeAccount) {
        log.info("NoticeAccountController - modify noticeAccount : {}", noticeAccount);

        noticeAccountService.modify(noticeAccount);
    }

    // 사용자 삭제
    @PostMapping(value = "/accountDelete")
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
