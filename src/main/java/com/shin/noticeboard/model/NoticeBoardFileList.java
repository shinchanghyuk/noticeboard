package com.shin.noticeboard.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoticeBoardFileList {
    private String filepath;
    private String originalFileName;

    public NoticeBoardFileList() {
    }

    public NoticeBoardFileList(String filepath, String originalFileName) {
        this.filepath = filepath;
        this.originalFileName = originalFileName;
    }
}
