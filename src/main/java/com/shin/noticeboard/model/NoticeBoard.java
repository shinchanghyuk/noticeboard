package com.shin.noticeboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
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

    @JsonProperty("filePath")
    private String filePath;

    @JsonProperty("originalFileName")
    private String originalFileName;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
