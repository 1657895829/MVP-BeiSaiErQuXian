package com.bwie.mvp;
import android.text.TextUtils;
/**
 * Presenter层 代表登录的类
 * Presenter – 交互中间人：Presenter主要作为沟通View与Model的桥梁，它从Model层检索数据后，返回给View层，
   使得View与Model之间没有耦合，也将业务逻辑从View角色上抽离出来。
 */
public class LoginPresenter {
    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        //在构造方法中声明Model层 代表登录的类，进行数据的存取
        loginModel = new LoginModel();
    }

    /**
     * 因为Presenter – 交互中间人：Presenter主要作为沟通View与Model的桥梁，如果Model数据存取完毕，页面销毁，
     * 而此时Presenter层还在源源不断从Model层接收传递的数据，传递给view层接，如果不及时回收，就会造成内存溢出（out of memory），所以可以把
     * view层页面销毁，Presenter层数据也就随之销毁移除了
     */
    public void detach() {
        loginView = null;
    }

    //login登录方法判断输入内容是否为空
    public void login(String name,String pwd){
        if (TextUtils.isEmpty(name)){
            loginView.nameEmpty();
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            loginView.pwdEmpty();
            return;
        }

        //调用Model层 代表登录的类，进行数据的存取，把从Model层类是否获取到数据的结果传到View 层 接口类进行调用显示
        loginModel.login(name, pwd, new ModelCallBack() {
            @Override
            public void success(String data) {
                loginView.loginSuccess(data);
            }

            @Override
            public void failed(int code) {
                loginView.loginFailed(code);
            }
        });
    }
}
