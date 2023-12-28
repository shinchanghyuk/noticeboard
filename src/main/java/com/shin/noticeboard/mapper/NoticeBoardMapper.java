package com.shin.noticeboard.mapper;

import java.util.ArrayList;
import java.util.List;

import com.shin.noticeboard.model.NoticeBoard;
import com.shin.noticeboard.model.NoticeBoardFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeBoardMapper {

    int selectTotalCount(NoticeBoard noticeBoard);

    List<NoticeBoard> select(NoticeBoard noticeBoard);

    NoticeBoard detailSelect(int id);

    NoticeBoardFile fileSelect(NoticeBoardFile noticeBoardFile);

    int insert(NoticeBoard noticeBoard);

    void fileInsert(NoticeBoardFile noticeBoardFile);

    void modify(NoticeBoard noticeBoard);

    void delete(List<String> id);

    void deleteFile(NoticeBoard noticeBoard);
}
