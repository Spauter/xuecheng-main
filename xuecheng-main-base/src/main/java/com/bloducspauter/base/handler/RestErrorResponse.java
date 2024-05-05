package com.bloducspauter.base.handler;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestErrorResponse {

    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }
}
