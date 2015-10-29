package com.hieuvt.myexception;

/**
 * Created by hieu.vutrong on 5/21/2015.
 */
public class MyException extends Exception {

    private String message = null;
    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
        this.message = message;
    }

    public MyException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
