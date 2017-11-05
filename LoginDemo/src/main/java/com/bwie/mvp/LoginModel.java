package com.bwie.mvp;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Model层 代表登录的类，进行数据的存取
 * Model – 数据的存取：Model 角色主要是提供数据的存取功能。Presenter 需要通过Model层存储、获取数据，
   Model就像一个数据仓库。更直白的说，Model是封装了数据库DAO或者网络获取数据的角色，或者两种数据方式获取的集合。
 */
public class LoginModel {
    //登录方法，网络获取数据，回调接口显示成功与否
    public void login(String name, String pwd, final ModelCallBack callBack) {
        //使用OKHttp请求网络数据，在当前项目Model添加依赖：compile 'com.squareup.okhttp3:okhttp:3.9.0'
        OkHttpClient client = new OkHttpClient();
        //使用post请求时，使用表单内容来进行数据请求
        RequestBody body = new FormBody.Builder()
            .add("pscid","39")
            .add("page","1")
            .build();
        Request request = new Request.Builder().url("http://120.27.23.105/product/getProducts")
            .post(body)
            .build();

        //使用call对象把请求内容添加进队列中进行获取
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败时，回传一个值 1
                callBack.failed(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功时，获取响应的内容，callBack 接口回调返回到presenter层
                String result = response.body().string();
                callBack.success(result);
            }
        });
    }
}
