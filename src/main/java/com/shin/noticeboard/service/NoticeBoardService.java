package com.shin.noticeboard.service;

import java.util.ArrayList;
import java.util.List;

import com.shin.noticeboard.model.NoticeBoard;
import com.shin.noticeboard.model.NoticeBoardSearchList;

public interface NoticeBoardService {

    NoticeBoardSearchList search(NoticeBoard noticeBoard);

    void save(NoticeBoard noticeBoard);

    void modify(NoticeBoard noticeBoard);

    void delete(List<String> id);
}
