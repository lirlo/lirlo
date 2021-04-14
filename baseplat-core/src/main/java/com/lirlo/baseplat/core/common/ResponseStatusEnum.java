package com.lirlo.baseplat.core.common;

public enum ResponseStatusEnum {

    SUCCESS(200,"请求成功"),
    FAILED_404(404,"请求不存在"),
    FAILED_403(403,"禁止访问"),
    FAILED_500(500,"服务器内部错误");

    private int code;

    private String msg;

    private ResponseStatusEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
