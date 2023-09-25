package com.shin.noticeboard.mapper;

import java.util.ArrayList;
import java.util.List;

import com.shin.noticeboard.model.NoticeBoard;

public interface NoticeBoardMapper {

    int selectTotalCount(NoticeBoard noticeBoard);

    List<NoticeBoard> select(NoticeBoard noticeBoard);

    void insert(NoticeBoard noticeBoard);

    void modify(NoticeBoard noticeBoard);

    void delete(List<String> id);
}
