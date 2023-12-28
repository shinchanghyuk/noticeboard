package com.shin.noticeboard.service;

import com.shin.noticeboard.mapper.NoticeBoardMapper;
import com.shin.noticeboard.model.NoticeBoard;
import com.shin.noticeboard.model.NoticeBoardFile;
import com.shin.noticeboard.model.NoticeBoardSearchList;
import com.shin.noticeboard.utils.FileDownload;
import com.shin.noticeboard.utils.FileUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@Transactional
public class NoticeBoardService {

    private final NoticeBoardMapper noticeBoardMapper;
    private final FileUpload fileUpload;
    private final FileDownload fileDownload;

    @Autowired
    public NoticeBoardService(NoticeBoardMapper noticeBoardMapper, FileUpload fileUpload, FileDownload fileDownload) {
        this.noticeBoardMapper = noticeBoardMapper;
        this.fileUpload = fileUpload;
        this.fileDownload = fileDownload;
    }

    public NoticeBoardSearchList search(NoticeBoard noticeBoard) {
        List<NoticeBoard> result = null;
        int count = 0;

        NoticeBoardSearchList noticeBoardSearchList = new NoticeBoardSearchList();

        count = noticeBoardMapper.selectTotalCount(noticeBoard);

        // vue에서 가져온 currentPage, pageSize 값을 가지고 조합하여 xml에 접근함
        noticeBoard.setOffset(noticeBoard.getPageSize() * (noticeBoard.getCurrentPage() - 1));
        result = noticeBoardMapper.select(noticeBoard);

        noticeBoardSearchList.setTotcnt(count);
        noticeBoardSearchList.setSearchList(result);

        return noticeBoardSearchList;
    }

    public NoticeBoard detailSearch(int id) {
        return noticeBoardMapper.detailSelect(id);
    }

    public void save(MultipartFile[] files, NoticeBoard noticeBoard) {
        // 게시글 저장
        noticeBoardMapper.insert(noticeBoard);

        try {
            if(files != null && files.length != 0) {
                fileUpload.fileUpload(files, noticeBoard);

                for (int i = 0; i < noticeBoard.getFileList().size(); i++) {
                    NoticeBoardFile noticeBoardFile = new NoticeBoardFile(noticeBoard.getId(), noticeBoard.getUserid(),
                            noticeBoard.getFileList().get(i).getFilepath(), noticeBoard.getFileList().get(i).getOriginalFileName());

                    noticeBoardMapper.fileInsert(noticeBoardFile);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void modify(MultipartFile[] files, NoticeBoard noticeBoard) {
        noticeBoardMapper.modify(noticeBoard);

        // 클라이언트에서 지워진 파일이 있을 때 DB에서 삭제함
        noticeBoardMapper.deleteFile(noticeBoard);

        if(files != null && files.length != 0) {
            try {
                // 서버에 파일 업로드
                fileUpload.fileUpload(files, noticeBoard);

                for (int i = 0; i < noticeBoard.getFileList().size(); i++) {
                    NoticeBoardFile noticeBoardFile = new NoticeBoardFile(noticeBoard.getId(), noticeBoard.getUserid(),
                            noticeBoard.getFileList().get(i).getFilepath(), noticeBoard.getFileList().get(i).getOriginalFileName());

                    noticeBoardMapper.fileInsert(noticeBoardFile);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void delete(List<String> id) {
        noticeBoardMapper.delete(id);
    }

    public ResponseEntity<byte[]> fileDownload(NoticeBoardFile PnoticeBoardFile) {
        NoticeBoardFile noticeBoardFile = noticeBoardMapper.fileSelect(PnoticeBoardFile);

        return fileDownload.fileDownload(noticeBoardFile);
    }
}
