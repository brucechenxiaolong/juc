package com.java.trycatch;

public class PdeException extends RuntimeException {

    private String code = "PDE-ERR-000";

    public PdeException(String message){
        super(message);
    }

    public PdeException(String message,String code){
        super(message);
        this.code = code;
    }

    public PdeException(PdeExceptionEnum err){
        super(err.getMsg());
        this.code = err.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
