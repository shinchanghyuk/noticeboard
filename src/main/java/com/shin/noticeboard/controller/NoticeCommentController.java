package com.shin.noticeboard.controller;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shin.noticeboard.model.NoticeBoard;
import com.shin.noticeboard.model.NoticeBoardSearchList;
import com.shin.noticeboard.model.NoticeComment;
import com.shin.noticeboard.service.NoticeCommentService;

import java.util.Date;

@RestController
@RequestMapping("/noticeboard")
public class NoticeCommentController {

    private static final Logger logger = LoggerFactory.getLogger(NoticeCommentController.class);

    private ObjectMapper objectMapper;

    // 서비스 주입
    @Autowired
    private NoticeCommentService noticeCommentService;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // 게시글에 포함된 댓글검색
    @RequestMapping(value = "/commentSearch", method = RequestMethod.POST)
    public List<NoticeComment> search(@RequestBody String requestPayload, HttpServletRequest request) {
        NoticeComment noticeComment= null;
        List<NoticeComment> noticeCommentList = null;

        logger.info("NoticeCommentController - search requestPayload : " + requestPayload);

        try {
            noticeComment = objectMapper.readValue(requestPayload, NoticeComment.class);

            logger.info("NoticeCommentController - search noticeComment : " + noticeComment.toString());

            noticeCommentList = noticeCommentService.search(noticeComment);

            logger.info("NoticeCommentController - search result : " + noticeCommentList);

        } catch (Exception e) {
            logger.error("NoticeCommentController - search Exception : ", e);
        }

        // 프론트에 데이터 전달 로직추가
        return noticeCommentList;

    }

    // 댓글 생성
    @RequestMapping(value = "/commentSave", method = RequestMethod.POST)
    public void save(@RequestBody String requestPayload) {
        NoticeComment noticeComment = null;

        logger.info("NoticeCommentController - save requestPayload : " + requestPayload);

        try {
            noticeComment = objectMapper.readValue(requestPayload, NoticeComment.class);
        } catch (Exception e) {
            logger.error("NoticeCommentController - save Exception : ", e);
        }
        
        // commentid가 0이면 새로 생성되는 게시글임
        if(noticeComment.getCommentid() == null || noticeComment.getCommentid() == "" || noticeComment.getCommentid().length() == 0) {
            // 해당 게시글에 댓글 수 파악한 후 commentid 넣어두기

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String formattedDate = dateFormat.format(currentDate);
            
            noticeComment.setCommentid(noticeComment.getBoardid() + "-" + formattedDate);
            noticeCommentService.save(noticeComment);
        } else {
            noticeCommentService.modify(noticeComment);
        }
    }

    // 댓글 삭제
    @RequestMapping(value = "/commentDelete", method = RequestMethod.POST)
    public void delete(@RequestBody String requestPayload) {
        NoticeComment noticeComment = null;

        logger.info("NoticeCommentController - delete requestPayload : " + requestPayload);

        try {
            noticeComment = objectMapper.readValue(requestPayload, NoticeComment.class);
        } catch (Exception e) {
            logger.error("NoticeCommentController - delete Exception : ", e);
        }

        noticeCommentService.delete(noticeComment);
    }
}
