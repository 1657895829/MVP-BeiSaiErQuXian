package com.bwie.mvp;
/**
 * Model层 回调接口类
 */
public interface ModelCallBack {
    //登陆成功时的方法，返回一个String值表示登陆成功
    public void success(String data);

    //登陆失败时的方法，返回一个int值响应码表示登陆失败
    public void failed(int code);
}
