package com.shin.noticeboard.service;

import com.shin.noticeboard.mapper.NoticeAccountMapper;
import com.shin.noticeboard.model.NoticeAccount;
import com.shin.noticeboard.model.RestReturnVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Slf4j
public class NoticeAccountService {

    private final NoticeAccountMapper noticeAccountMapper;

    @Autowired
    public NoticeAccountService(NoticeAccountMapper noticeAccountMapper) {
        this.noticeAccountMapper = noticeAccountMapper;
    }

    public RestReturnVo login(NoticeAccount noticeAccount, HttpSession session) {
        NoticeAccount resultNoticeAccount = null;
        RestReturnVo restReturnVo = null;
        try {
            resultNoticeAccount = noticeAccountMapper.login(noticeAccount);
        } catch (Exception e) {
            log.error("NoticeAccountServiceimpl - login Exception : ", e);
            throw new IllegalStateException(e);
        }

        if (resultNoticeAccount == null || resultNoticeAccount.getUserid() == null) { // 사용자가 있는지 체크
            restReturnVo = new RestReturnVo("9999", "user not exist");
        } else if (resultNoticeAccount.getStatus() == 0) { // 유효한 사용자 인지 체크
            restReturnVo = new RestReturnVo("9999", "user invaild");
        } else if (resultNoticeAccount.getUsertype() == 0) { // 일반 사용자 인지 체크
            restReturnVo = new RestReturnVo("1000", "login success(user)");
        } else {
            restReturnVo = new RestReturnVo("1001", "login success(admin)");
        }

        session.setAttribute("sessionId", noticeAccount);

        return restReturnVo;
    }

    public void logout(HttpSession session) {
        // 세션제거
        session.invalidate();
    }

    public void register(NoticeAccount noticeAccount) {
        noticeAccountMapper.create(noticeAccount);
    }

    public void modify(NoticeAccount noticeAccount) {
        noticeAccountMapper.modify(noticeAccount);
    }

    public void delete(List<String> id) {
        noticeAccountMapper.delete(id);
    }
}
