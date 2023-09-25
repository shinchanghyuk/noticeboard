package com.shin.noticeboard.service.impl;

import com.shin.noticeboard.model.NoticeBoard;
import com.shin.noticeboard.model.NoticeBoardSearchList;

import com.shin.noticeboard.service.NoticeBoardService;
import com.shin.noticeboard.mapper.NoticeBoardMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeBoardServiceimpl implements NoticeBoardService {

    private NoticeBoardMapper noticeBoardMapper;
    private static final Logger logger = LoggerFactory.getLogger(NoticeBoardService.class);

    @Autowired
    public void setNoticeBoardMapper(NoticeBoardMapper noticeBoardMapper) {
        this.noticeBoardMapper = noticeBoardMapper;
    }

    public NoticeBoardSearchList search(NoticeBoard noticeBoard) {
        List<NoticeBoard> result = null;
        NoticeBoardSearchList noticeBoardSearchList = null;
        int count = 0;

        try {
            noticeBoardSearchList = new NoticeBoardSearchList();
            
            count = noticeBoardMapper.selectTotalCount(noticeBoard);

            // vue에서 가져온 currentPage, pageSize 값을 가지고 조합하여 xml에 접근함
            noticeBoard.setOffset(noticeBoard.getPageSize() * (noticeBoard.getCurrentPage() - 1));
            result = noticeBoardMapper.select(noticeBoard);

            noticeBoardSearchList.setTotcnt(count);
            noticeBoardSearchList.setSearchList(result);
        } catch (Exception e) {
            logger.error("NoticeBoardServiceimpl - search Exception : ", e);
        }

        return noticeBoardSearchList;
    }

    public void save(NoticeBoard noticeBoard) {
        noticeBoardMapper.insert(noticeBoard);
    }

    public void modify(NoticeBoard noticeBoard) {
        noticeBoardMapper.modify(noticeBoard);
    }

    public void delete(List<String> id) {
        noticeBoardMapper.delete(id);
    }
}
