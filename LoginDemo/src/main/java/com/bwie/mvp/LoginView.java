package com.bwie.mvp;
/**
 * View 层 接口类
 * View – 用户界面：View通常是指Activity、Fragment或者某个View控件，它含有一个Presenter成员变量。
   通常View需要实现一个逻辑接口，将View上的操作转交给Presenter进行实现，最后，Presenter 调用View逻辑接口将结果返回给View元素。
 */
public interface LoginView {
    //姓名为空时调用的方法
    public void nameEmpty();

    //密码为空时调用的方法
    public void pwdEmpty();

    //登录成功时调用的方法,返回一个String值表示登陆成功
    public void loginSuccess(Object object);

    //登录失败时调用的方法，返回一个代表失败的int值
    public void loginFailed(int code);
}
