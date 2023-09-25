package com.shin.noticeboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRowSearch() {
        return rowSearch;
    }

    public void setRowSearch(String rowSearch) {
        this.rowSearch = rowSearch;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    // public int getLimit() {
    //     return limit;
    // }

    // public void setLimit(int limit) {
    //     this.limit = limit;
    // }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
