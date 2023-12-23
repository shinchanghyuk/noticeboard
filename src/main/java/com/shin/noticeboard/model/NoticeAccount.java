package com.shin.noticeboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@Getter @Setter
public class NoticeAccount {
    @JsonProperty("userid")
    private String userid;

    @JsonProperty("password")
    private String password;

    @JsonProperty("status")
    private int status;

    @JsonProperty("usertype")
    private int usertype;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
