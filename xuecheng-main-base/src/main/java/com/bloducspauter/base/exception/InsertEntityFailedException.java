package com.bloducspauter.base.exception;

public class InsertEntityFailedException extends Exception{
    private static final long serialVersionUID = 185483881L;
    public InsertEntityFailedException() {
        super("Insert entity failed");
    }

    public InsertEntityFailedException(String message) {
        super(message);
    }

    public InsertEntityFailedException(Class<?> entity) {
        super("Insert Entity Failed: " + entity.getSimpleName());
    }
}
