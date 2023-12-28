package com.shin.noticeboard.controller;

import java.util.ArrayList;
import java.util.List;

import com.shin.noticeboard.model.NoticeBoardFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shin.noticeboard.model.NoticeBoard;
import com.shin.noticeboard.model.NoticeBoardSearchList;
import com.shin.noticeboard.service.NoticeBoardService;
import com.shin.noticeboard.service.NoticeCommentService;

@Slf4j
@RestController
public class NoticeBoardController {
    
    private final ObjectMapper objectMapper;
    private final NoticeBoardService noticeBoardService;
    private final NoticeCommentService noticeCommentService;

    @Autowired
    public NoticeBoardController(ObjectMapper objectMapper, NoticeBoardService noticeBoardService, NoticeCommentService noticeCommentService) {
        this.objectMapper = objectMapper;
        this.noticeBoardService = noticeBoardService;
        this.noticeCommentService = noticeCommentService;
    }

    // 게시물 전체 검색
    @PostMapping("/board/search")
    public NoticeBoardSearchList search(@RequestBody NoticeBoard noticeBoard) {
        NoticeBoardSearchList noticeBoardSearchList = null;

        log.info("NoticeBoardController - search noticeBoard : {}", noticeBoard.toString());
        noticeBoardSearchList = noticeBoardService.search(noticeBoard);
        log.info("NoticeBoardController - search result totcnt : {}", noticeBoardSearchList.getTotcnt());

        // 프론트에 데이터 전달 로직추가
        return noticeBoardSearchList;
    }

    // 특정 게시글 검색
    @GetMapping("/board/detailSearch/{id}")
    public NoticeBoard detailSearch(@PathVariable("id") int id) {
        log.info("NoticeBoardController - detailSearch id : {}", id);
        return noticeBoardService.detailSearch(id);
    }

    // 게시물 생성
    @RequestMapping(value = "/board/save", method = RequestMethod.POST)
    public void save(@ModelAttribute NoticeBoard noticeBoard,
                     @RequestParam(required = false, name = "files") MultipartFile[] files) {
        log.info("NoticeBoardController - save noticeBoard : {}", noticeBoard.toString());

        // Id가 0이면 새로 생성되는 게시글임
        if(noticeBoard.getId() == 0) {
            noticeBoardService.save(files, noticeBoard);
        } else {
            noticeBoardService.modify(files, noticeBoard);
        }     
    }

    // 게시물 삭제
    @RequestMapping(value = "/board/delete", method = RequestMethod.POST)
    public void delete(@RequestBody String requestPayload) {
        List<String> id = new ArrayList<>();

        log.info("NoticeBoardController - delete requestPayload : {}", requestPayload);

        try {
            id = objectMapper.readValue(requestPayload, new TypeReference<List<String>>() {});

        } catch (Exception e) {
            log.error("NoticeBoardController - delete Exception : ", e);
        }

        noticeBoardService.delete(id);

        // 게시글에 달린 댓글 또한 삭제되어야 함
        noticeCommentService.allDelete(id);
    }

    @PostMapping(value = "/fileDownload")
    public ResponseEntity<byte[]> fileDownload(@RequestBody NoticeBoardFile noticeBoardFile) {
        log.info("NoticeBoardController - fileDownload noticeBoardFile : {}", noticeBoardFile.toString());
        return noticeBoardService.fileDownload(noticeBoardFile);
    }
}
