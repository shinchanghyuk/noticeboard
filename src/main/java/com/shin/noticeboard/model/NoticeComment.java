package com.shin.noticeboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
@Getter
@Setter
public class NoticeComment {

    @JsonProperty("commentid")
    private String commentid;

    @JsonProperty("boardid")
    private int boardid;

    @JsonProperty("userid")
    private String userid;

    // @JsonProperty("boarduserid")
    // private String boarduserid;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("createtime")
    private String createtime;

    @JsonProperty("modifytime")
    private String modifytime;

    @JsonProperty("deletetime")
    private String deletetime;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
