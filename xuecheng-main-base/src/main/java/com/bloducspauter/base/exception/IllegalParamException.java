package com.bloducspauter.base.exception;

import com.bloducspauter.base.enums.CommonError;

public class IllegalParamException extends IllegalArgumentException {
    private static final long serialVersionUID = 192099489L;

    public IllegalParamException(String message) {
        super(message);
    }

    public static void cast(CommonError error) {
        String message = error.getErrMessage();
        throw new IllegalParamException(message);
    }

    public static void cast(CommonError error,Object param) {
        String message = error.getErrMessage();
        throw new IllegalParamException(message+":"+param.toString());
    }
}
