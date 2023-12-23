package com.shin.noticeboard.service.impl;

import com.shin.noticeboard.model.NoticeBoard;
import com.shin.noticeboard.model.NoticeBoardSearchList;

import com.shin.noticeboard.service.NoticeBoardService;
import com.shin.noticeboard.mapper.NoticeBoardMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.shin.noticeboard.utils.FileDownload;
import com.shin.noticeboard.utils.FileUpload;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class NoticeBoardServiceimpl implements NoticeBoardService {

    private final NoticeBoardMapper noticeBoardMapper;
    private final FileUpload fileUpload;
    private final FileDownload fileDownload;

    @Autowired
    public NoticeBoardServiceimpl(NoticeBoardMapper noticeBoardMapper, FileUpload fileUpload, FileDownload fileDownload) {
        this.noticeBoardMapper = noticeBoardMapper;
        this.fileUpload = fileUpload;
        this.fileDownload = fileDownload;
    }

    public NoticeBoardSearchList search(NoticeBoard noticeBoard) {
        List<NoticeBoard> result = null;
        NoticeBoardSearchList noticeBoardSearchList = null;
        int count = 0;

        noticeBoardSearchList = new NoticeBoardSearchList();

        count = noticeBoardMapper.selectTotalCount(noticeBoard);

        // vue에서 가져온 currentPage, pageSize 값을 가지고 조합하여 xml에 접근함
        noticeBoard.setOffset(noticeBoard.getPageSize() * (noticeBoard.getCurrentPage() - 1));
        result = noticeBoardMapper.select(noticeBoard);

        noticeBoardSearchList.setTotcnt(count);
        noticeBoardSearchList.setSearchList(result);

        return noticeBoardSearchList;
    }

    public void save(MultipartFile file, NoticeBoard noticeBoard) {
        if(file != null && !file.isEmpty()) {
            // 서버에 파일 업로드
            try {
                fileUpload.fileUpload(file, noticeBoard);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.info("NoticeBoardController - save file is empty");
        }

        noticeBoardMapper.insert(noticeBoard);
    }

    public void modify(MultipartFile file, NoticeBoard noticeBoard) {
        if(file != null && !file.isEmpty()) {
            // 서버에 파일 업로드
            try {
                fileUpload.fileUpload(file, noticeBoard);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.info("NoticeBoardController - modify file is empty");
        }

        noticeBoardMapper.modify(noticeBoard);
    }

    public void delete(List<String> id) {
        noticeBoardMapper.delete(id);
    }

    public ResponseEntity<byte[]> fileDownload(NoticeBoard noticeBoard) {
        NoticeBoardSearchList noticeBoardSearchList = search(noticeBoard);

        return fileDownload.fileDownload(noticeBoardSearchList);
    }
}
