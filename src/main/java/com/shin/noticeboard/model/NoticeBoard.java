package com.shin.noticeboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class NoticeBoard {
    @JsonProperty("id")
    private int id;

    @JsonProperty("userid")
    private String userid;

    @JsonProperty("usertype")
    private int usertype;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("createtime")
    private String createtime;

    @JsonProperty("modifytime")
    private String modifytime;

    @JsonProperty("pageSize")
    private int pageSize;

    @JsonProperty("currentPage")
    private int currentPage;

    // @JsonProperty("limit")
    // private int limit;

    @JsonProperty("offset")
    private int offset;

    @JsonProperty("deletetime")
    private String deletetime;

    @JsonProperty("rowSearch")
    private String rowSearch;

    @JsonProperty("fileList")
    private List<NoticeBoardFileList> fileList;

    @JsonProperty("uploadedFileName")
    private List<String> uploadedFileName;


//    @JsonProperty("filePath")
//    private List<String> filePath = new ArrayList<>();
//
//    @JsonProperty("originalFileName")
//    private List<String> originalFileName = new ArrayList<>();
//
//    public void setFilePath(String filePath) {
//        this.filePath.add(filePath);
//    }
//
//    public void setOriginalFileName(String originalFileName) {
//        this.originalFileName.add(originalFileName);
//    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
