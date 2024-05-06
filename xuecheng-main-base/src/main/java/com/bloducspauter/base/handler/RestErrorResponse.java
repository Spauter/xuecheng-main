package com.bloducspauter.base.handler;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Bloduc Spauter
 */
@Setter
@Getter
public class RestErrorResponse {

    private String errMessage;

    private List<Object> errors;

    public RestErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }
}
