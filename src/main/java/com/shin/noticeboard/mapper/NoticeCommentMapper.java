package com.shin.noticeboard.mapper;

import java.util.ArrayList;
import java.util.List;

import com.shin.noticeboard.model.NoticeComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeCommentMapper {

    int selectCommentCount(NoticeComment noticeComment);

    List<NoticeComment> select(NoticeComment oticeComment);

    void insert(NoticeComment oticeComment);

    void modify(NoticeComment oticeComment);

    void allDelete(List<String> id);

    void delete(NoticeComment oticeComment);
}
