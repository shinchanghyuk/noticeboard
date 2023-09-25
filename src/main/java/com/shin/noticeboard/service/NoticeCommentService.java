package com.shin.noticeboard.service;

import java.util.ArrayList;
import java.util.List;

import com.shin.noticeboard.model.NoticeComment;

public interface NoticeCommentService {

    int selectCommentCount(NoticeComment noticeComment);

    List<NoticeComment> search(NoticeComment noticeComment);

    void save(NoticeComment noticeComment);

    void modify(NoticeComment noticeComment);

    void allDelete(List<String> id);

    void delete(NoticeComment noticeComment);
}
