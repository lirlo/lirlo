package com.lirlo.baseplat.auth.security.common;

import lombok.Data;

public enum HttpStatusEnum {

    SUCCESS(200,"成功"),
    BAD_REQUEST(400,""),
    UNAUTHORIZED(401,"无权限"),
    FORBIDDEN(403,"禁止访问"),
    NOT_FOUND(404,"方法未找到"),
    METHOD_NOT_ALLOWED(405,"方法不允许"),
    INTERNAL_SERVER_ERROR(500,"服务器内部错误"),
    BAD_GATEWAY(502,"");


    int code;

    String message;


    private HttpStatusEnum(int code,String message){
        this.code = code;
        this.message = message;
    }

}
