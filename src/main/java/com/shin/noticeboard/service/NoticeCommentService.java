package com.shin.noticeboard.service;

import com.shin.noticeboard.mapper.NoticeCommentMapper;
import com.shin.noticeboard.model.NoticeComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NoticeCommentService {

    private final NoticeCommentMapper noticeCommentMapper;

    @Autowired
    public NoticeCommentService(NoticeCommentMapper noticeCommentMapper) {
        this.noticeCommentMapper = noticeCommentMapper;
    }

    public int selectCommentCount(NoticeComment noticeComment) {
        return noticeCommentMapper.selectCommentCount(noticeComment);
    }
    public List<NoticeComment> search(NoticeComment noticeComment) {
        return noticeCommentMapper.select(noticeComment);
    }

    public void save(NoticeComment noticeComment) {
        noticeCommentMapper.insert(noticeComment);
    }

    public void modify(NoticeComment noticeComment) {
        noticeCommentMapper.modify(noticeComment);
    }

    public void allDelete(List<String> id) {
        log.info("NoticeCommentService - allDelete : {}", id.toString());
        noticeCommentMapper.allDelete(id);
    }

    public void delete(NoticeComment noticeComment) {
        noticeCommentMapper.delete(noticeComment);
    }
}
