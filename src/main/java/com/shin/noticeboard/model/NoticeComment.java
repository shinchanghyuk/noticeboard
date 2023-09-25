package com.shin.noticeboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public int getBoardid() {
        return boardid;
    }

    public void setBoardid(int boardid) {
        this.boardid = boardid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    // public String getBoarduserid() {
    //     return boarduserid;
    // }

    // public void setBoarduserid(String boarduserid) {
    //     this.boarduserid = boarduserid;
    // }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public String getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(String deletetime) {
        this.deletetime = deletetime;
    }
}
