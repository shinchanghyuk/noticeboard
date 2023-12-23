package com.shin.noticeboard.mapper;

import com.shin.noticeboard.model.NoticeAccount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeAccountMapper {
    NoticeAccount login(NoticeAccount noticeAccount);

    void create(NoticeAccount noticeAccount);

    void modify(NoticeAccount noticeAccount);

    void delete(List<String> id);
}
