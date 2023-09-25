package com.shin.noticeboard.mapper;

import com.shin.noticeboard.model.NoticeAccount;

import java.util.List;

public interface NoticeAccountMapper {

    NoticeAccount login(NoticeAccount noticeAccount);

    void create(NoticeAccount noticeAccount);

    void modify(NoticeAccount noticeAccount);

    void delete(List<String> id);
}
