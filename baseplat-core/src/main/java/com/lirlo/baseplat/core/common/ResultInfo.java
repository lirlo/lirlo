package com.lirlo.baseplat.core.common;

import com.alibaba.druid.support.json.JSONUtils;
import lombok.Builder;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Data
@Builder
public class ResultInfo<T> {

    int code;

    String msg;

    T data;

    String token;

    public void response(HttpServletResponse httpServletResponse, ResultInfo resultInfo){
        httpServletResponse.setContentType("application/json;charset=utf-8");
        try {
            httpServletResponse.getWriter().write(JSONUtils.toJSONString(resultInfo.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){

        return "{\"code\":"+this.code+",\"msg\":"+this.msg+",\"token\":"+this.token+",\"data\":"+this.data+"}";
    }
}
