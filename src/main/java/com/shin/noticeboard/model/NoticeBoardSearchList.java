
package com.shin.noticeboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NoticeBoardSearchList {

    private List<NoticeBoard> searchList;
    private int totcnt;
  

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public List<NoticeBoard> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<NoticeBoard> searchList) {
        this.searchList = searchList;
    }

    public int getTotcnt() {
        return totcnt;
    }

    public void setTotcnt(int totcnt) {
        this.totcnt = totcnt;
    }
}
