package com.shin.noticeboard.service;

import com.shin.noticeboard.model.NoticeAccount;
import com.shin.noticeboard.model.RestReturnVo;

import java.util.List;

public interface NoticeAccountService {

    RestReturnVo login(NoticeAccount noticeAccount);

    void create(NoticeAccount noticeAccount);

    void modify(NoticeAccount noticeAccount);

    void delete(List<String> id);
}
