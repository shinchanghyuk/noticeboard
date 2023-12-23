package com.shin.noticeboard.service;

import java.util.ArrayList;
import java.util.List;

import com.shin.noticeboard.model.NoticeBoard;
import com.shin.noticeboard.model.NoticeBoardSearchList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface NoticeBoardService {

    NoticeBoardSearchList search(NoticeBoard noticeBoard);

    void save(MultipartFile file, NoticeBoard noticeBoard);

    void modify(MultipartFile file, NoticeBoard noticeBoard);

    void delete(List<String> id);

    ResponseEntity<byte[]> fileDownload(NoticeBoard noticeBoard);
}
