package com.shin.noticeboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Getter
@Setter
public class NoticeBoardFile {
    private int id;
    private int boardid;
    private String userid;

    @JsonProperty("filePath")
    private String filePath;

    @JsonProperty("originalFileName")
    private String originalFileName;

    private String createtime;
    private String deletetime;

    public NoticeBoardFile(int boardid, String userid, String filePath, String originalFileName) {
        this.boardid = boardid;
        this.userid = userid;
        this.filePath = filePath;
        this.originalFileName = originalFileName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
