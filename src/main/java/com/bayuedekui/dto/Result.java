package com.bayuedekui.dto;

public class Result<T> {
    private boolean success;    //是否成功标志
    private T data;     //成功时返回的数据
    private String errMsg;  //错误信息
    private int errCode;    //错误码

    //无参构造
    public Result(){}

    //成功时的构造器
    public Result(boolean success,T data){
        this.success=success;
        this.data=data;
    }

    //错误时的构造器
    public Result(boolean success,int errCode,String errMsg){
        this.success=success;
        this.errCode=errCode;
        this.errMsg=errMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
