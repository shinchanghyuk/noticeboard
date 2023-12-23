package com.shin.noticeboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class RestReturnVo {

    @JsonProperty("message")
    private String message;

    @JsonProperty("returnvalue")
    private String returnvalue;

    public RestReturnVo(String returnvalue, String message) {
        this.returnvalue = returnvalue;
        this.message = message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
