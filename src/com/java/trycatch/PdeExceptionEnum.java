package com.java.trycatch;

public enum PdeExceptionEnum {

	/**
	 * 成功
	 */
	SUCCESS("PDE-200","成功"),
	/**
	 * 未知错误
	 */
	UNKNOW_ERROR("PDE-ERR-000","未知错误"),
	/**
	 * 用户不存在
	 */
    USER_NOT_FIND("PDE-ERR-001","用户不存在");
	/**
	 * 成功
	 */
	public static final String CODE_SUCCESS  = "PDE-200";
	/**
	 * 未知错误
	 */
	public static final String CODE_UNKNOW_ERROR  = "PDE-ERR-000";

	/**
	 * 用户不存在
	 */
	public static final String CODE_USER_NOT_FIND  = "PDE-ERR-001";

	private String code;
    private String msg;

    PdeExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
