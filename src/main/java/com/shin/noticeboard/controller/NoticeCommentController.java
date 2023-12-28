package com.shin.noticeboard.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.shin.noticeboard.model.NoticeComment;
import com.shin.noticeboard.service.NoticeCommentService;

import java.util.Date;

@Slf4j
@RestController
public class NoticeCommentController {
    
    private final ObjectMapper objectMapper;
    private final NoticeCommentService noticeCommentService;

    @Autowired
    public NoticeCommentController(ObjectMapper objectMapper, NoticeCommentService noticeCommentService) {
        this.objectMapper = objectMapper;
        this.noticeCommentService = noticeCommentService;
    }

    // 게시글에 포함된 댓글검색
    @PostMapping(value = "/comment/search")
    public List<NoticeComment> search(@RequestBody NoticeComment noticeComment) {
        List<NoticeComment> noticeCommentList = null;

        log.info("NoticeCommentController - search NoticeComment : {}", noticeComment);

        noticeCommentList = noticeCommentService.search(noticeComment);

        // 프론트에 데이터 전달 로직추가
        return noticeCommentList;
    }

    // 댓글 생성
    @PostMapping(value = "/comment/save")
    public void save(@RequestBody NoticeComment noticeComment) {

        log.info("NoticeCommentController - save noticeComment : {}", noticeComment);

        // commentid가 0이면 새로 생성되는 게시글임
        if(noticeComment.getCommentid() == null || noticeComment.getCommentid().isEmpty()) {
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
    @PostMapping(value = "/comment/delete")
    public void delete(@RequestBody NoticeComment noticeComment) {
        log.info("NoticeCommentController - delete noticeComment : {}", noticeComment);
        noticeCommentService.delete(noticeComment);
    }
}
