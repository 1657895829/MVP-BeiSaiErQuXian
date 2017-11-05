package com.bwie.mvp;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 1. MVP介绍
       MVP模式是MVC模式的一个演化版本，MVP全称Model-View-Presenter。目前MVP在Android应用开发中越来越重要了。
       在Android中，业务逻辑和数据存取是紧紧耦合的，很多缺乏经验的开发者很可能会将各种各样的业务逻辑塞进某个Activity、Fragment或者自定义View中，这样会使得这些组件的单个类型臃肿不堪。
       如果不将具体的业务逻辑抽离出来，当UI变化时，你就需要去原来的View中抽离具体业务逻辑，这必然会很麻烦并且易出错。
   2. 使用MVP的好处
     （1）MVP模式会解除View与Model的耦合，有效的降低View的复杂性。同时又带来了良好的可扩展性、可测试性，保证系统的整洁性和灵活性。
     （2）MVP模式可以分离显示层与逻辑层，它们之间通过接口进行通信，降低耦合。理想化的MVP模式可以实现同一份逻辑代码搭配不同的显示界面，因为它们之间并不依赖与具体，而是依赖于抽象。
      这使得Presenter可以运用于任何实现了View逻辑接口的UI，使之具有更广泛的适用性，保证了灵活度。
 * 3. MVP模式的三个角色
 （1）Presenter – 交互中间人：Presenter主要作为沟通View与Model的桥梁，它从Model层检索数据后，返回给View层，
      使得View与Model之间没有耦合，也将业务逻辑从View角色上抽离出来。
 （2）View – 用户界面：View通常是指Activity、Fragment或者某个View控件，它含有一个Presenter成员变量。
      通常View需要实现一个逻辑接口，将View上的操作转交给Presenter进行实现，最后，Presenter 调用View逻辑接口将结果返回给View元素。
 （3）Model – 数据的存取：Model 角色主要是提供数据的存取功能。Presenter 需要通过Model层存储、获取数据，
      Model就像一个数据仓库。更直白的说，Model是封装了数据库DAO或者网络获取数据的角色，或者两种数据方式获取的集合。
 */
public class MainActivity extends AppCompatActivity implements LoginView{
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.pwd)
    EditText pwd;
    @Bind(R.id.btn)
    Button btn;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //this 代表implements实现的LoginView类
        presenter = new LoginPresenter(this);
    }

    // 登陆按钮的点击事件
    @OnClick(R.id.btn)
    public void onViewClicked() {
        //点击按钮时调用Presenter层的login登录方法判断输入内容是否为空
        presenter.login(name.getText().toString(),pwd.getText().toString());
    }

    @Override
    public void nameEmpty() {
        Toast.makeText(this,"姓名不得为空！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void pwdEmpty() {
        Toast.makeText(this,"密码不得为空！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess(Object object) {
        //当前为主线程，需建立一个子线程，执行更新UI操作
        /**
         * android Toast提示异常：java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
         错误原因：原来是在子线程弹Toast了，切记，Toast只能在UI线程弹出，如果一定要在子线程弹，那么就通过 new Handler(Looper.getMainLooper()) 来弹出。
         */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,"数据获取成功！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void loginFailed(int code) {
        Toast.makeText(this,"数据获取失败！",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当前页面关闭，销毁 Presenter层 中所持有的LoginView 对象
        presenter.detach();
    }
}
