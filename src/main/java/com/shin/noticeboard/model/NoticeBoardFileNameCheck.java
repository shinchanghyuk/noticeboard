package com.shin.noticeboard.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoticeBoardFileNameCheck {
    private int boardid;
    private String originalFileName;


    public NoticeBoardFileNameCheck(int boardid, String originalFileName) {
        this.boardid = boardid;
        this.originalFileName = originalFileName;
    }
}
