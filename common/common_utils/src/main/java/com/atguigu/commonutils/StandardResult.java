package com.atguigu.commonutils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

//统一返回结果
@Data
public class StandardResult {

    private Boolean success;

    private Integer code;

    private String message;

    private Map<String,Object> data = new HashMap<>();

    private StandardResult(){}

    //调用成功的方法
    public static StandardResult ok(){
        StandardResult r = new StandardResult();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    //调用失败的方法
    public static StandardResult error(){
        StandardResult r = new StandardResult();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public StandardResult success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public StandardResult message(String message){
        this.setMessage(message);
        return this;
    }

    public StandardResult code(Integer code){
        this.setCode(code);
        return this;
    }

    public StandardResult data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public StandardResult data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
