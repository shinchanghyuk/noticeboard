package com.shin.noticeboard.controller;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shin.noticeboard.model.NoticeAccount;
import com.shin.noticeboard.model.RestReturnVo;
import com.shin.noticeboard.service.NoticeAccountService;

@RestController
@RequestMapping("/noticeboard")
public class NoticeAccountController {

    private static final Logger logger = LoggerFactory.getLogger(NoticeAccountController.class);

    private ObjectMapper objectMapper;

    // 서비스 주입
    @Autowired
    private NoticeAccountService noticeAccountService;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // 아이디 로그인
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<RestReturnVo> login(@RequestBody String requestPayload, HttpServletRequest request) {
        RestReturnVo result = null;
        NoticeAccount noticeAccount = null;

        logger.info("NoticeAccountController - login requestPayload : " + requestPayload);

        try {
            noticeAccount = objectMapper.readValue(requestPayload, NoticeAccount.class);

            logger.info("NoticeAccountController - login noticeAccount : " + noticeAccount.toString());

            result = noticeAccountService.login(noticeAccount);

            logger.info("NoticeAccountController - login result : " + result.toString());

        } catch (Exception e) {
            logger.error("NoticeAccountController - login Exception : ", e);
        }

        // 프론트에 데이터 전달 로직추가
        return ResponseEntity.ok(result);
    }

    // 아이디 생성
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody String requestPayload) {
        NoticeAccount noticeAccount = null;

        logger.info("NoticeAccountController - create requestPayload : " + requestPayload);

        try {
            noticeAccount = objectMapper.readValue(requestPayload, NoticeAccount.class);
        } catch (Exception e) {
            logger.error("NoticeAccountController - create Exception : ", e);
        }

        noticeAccountService.create(noticeAccount);
    }

    // 사용자 수정
    @RequestMapping(value = "/accountModify", method = RequestMethod.POST)
    public void modify(@RequestBody String requestPayload) {
    NoticeAccount noticeAccount = null;

    logger.info("NoticeAccountController - modify requestPayload : " + requestPayload);

    try {
        noticeAccount = objectMapper.readValue(requestPayload, NoticeAccount.class);
    } catch (Exception e) {
        logger.error("NoticeAccountController - modify Exception : ", e);
    }

        noticeAccountService.modify(noticeAccount);
    }

    // 사용자 삭제
    @RequestMapping(value = "/accountDelete", method = RequestMethod.POST)
    public void delete(@RequestBody String requestPayload) {
    List<String> id = new ArrayList<String>();

    logger.info("NoticeAccountController - delete requestPayload : " + requestPayload);

    try {
        id = objectMapper.readValue(requestPayload, new TypeReference<List<String>>(){});

    } catch (Exception e) {
        logger.error("NoticeAccountController - delete Exception : ", e);
    }

        noticeAccountService.delete(id);
    }
}
