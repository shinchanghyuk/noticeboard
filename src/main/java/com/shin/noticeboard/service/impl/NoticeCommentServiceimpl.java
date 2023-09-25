package com.shin.noticeboard.service.impl;

import com.shin.noticeboard.model.NoticeComment;
import com.shin.noticeboard.service.NoticeCommentService;
import com.shin.noticeboard.mapper.NoticeCommentMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeCommentServiceimpl implements NoticeCommentService {

    private NoticeCommentMapper noticeCommentMapper;
    private static final Logger logger = LoggerFactory.getLogger(NoticeCommentService.class);

    @Autowired
    public void setNoticeCommentMapper(NoticeCommentMapper noticeCommentMapper) {
        this.noticeCommentMapper = noticeCommentMapper;
    }

    public int selectCommentCount(NoticeComment noticeComment) {
        int count = 0;
        try {
            count = noticeCommentMapper.selectCommentCount(noticeComment);
        } catch(Exception e){
            logger.error("NoticeCommentServiceimpl - selectCommentCount Exception : ", e);
        }

        return count;
    }
    public List<NoticeComment> search(NoticeComment noticeComment) {
        List<NoticeComment> noticeCommentList = null;
        int count = 0;

        try {
        //    count = noticeBoardMapper.selectTotalCount(noticeBoard);
            noticeCommentList = noticeCommentMapper.select(noticeComment);

        } catch (Exception e) {
            logger.error("NoticeCommentServiceimpl - search Exception : ", e);
        }

        return noticeCommentList;
    }

    public void save(NoticeComment noticeComment) {
        noticeCommentMapper.insert(noticeComment);
    }

    public void modify(NoticeComment noticeComment) {
        noticeCommentMapper.modify(noticeComment);
    }

    public void allDelete(List<String> id) {
        logger.info("NoticeCommentService - allDelete : ", id.toString());
        noticeCommentMapper.allDelete(id);
    }

    public void delete(NoticeComment noticeComment) {
        noticeCommentMapper.delete(noticeComment);
    }
}
