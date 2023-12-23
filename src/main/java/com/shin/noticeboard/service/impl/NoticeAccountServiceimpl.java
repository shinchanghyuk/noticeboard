package com.shin.noticeboard.service.impl;

import com.shin.noticeboard.model.NoticeAccount;
import com.shin.noticeboard.model.RestReturnVo;
import com.shin.noticeboard.service.NoticeAccountService;
import com.shin.noticeboard.mapper.NoticeAccountMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeAccountServiceimpl implements NoticeAccountService {

    private NoticeAccountMapper noticeAccountMapper;
    private static final Logger logger = LoggerFactory.getLogger(NoticeAccountService.class);

    @Autowired
    public void setNoticeAccountMapper(NoticeAccountMapper noticeAccountMapper) {
        this.noticeAccountMapper = noticeAccountMapper;
    }

    public RestReturnVo login(NoticeAccount noticeAccount) {
        NoticeAccount resultNoticeAccount = null;
        try {
            resultNoticeAccount = noticeAccountMapper.login(noticeAccount);
        } catch (Exception e) {
            logger.error("NoticeAccountServiceimpl - login Exception : ", e);
            throw new IllegalStateException(e);
        }

        // 사용자가 있는지 체크
        if (resultNoticeAccount == null || resultNoticeAccount.getUserid() == null) {
            return new RestReturnVo("9999", "user not exist");
        }

        // 유효한 사용자인지 체크
        if (resultNoticeAccount.getStatus() == 0) {
            return new RestReturnVo("9999", "user invaild");
        }

        // 일반사용자인지 체크
        if (resultNoticeAccount.getUsertype() == 0) {
            return new RestReturnVo("1000", "login success(user)");
        } else {
            return new RestReturnVo("1001", "login success(admin)");
        }
    }

    public void create(NoticeAccount noticeAccount) {
        noticeAccountMapper.create(noticeAccount);
    }

    public void modify(NoticeAccount noticeAccount) {
        noticeAccountMapper.modify(noticeAccount);
    }

    public void delete(List<String> id) {
        noticeAccountMapper.delete(id);
    }
}
