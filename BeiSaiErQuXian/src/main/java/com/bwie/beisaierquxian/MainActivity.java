package com.bwie.beisaierquxian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CustomQuLinePath customQuLinePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //查找Path之贝塞尔曲线控件
        customQuLinePath = (CustomQuLinePath) findViewById(R.id.QuLinePath);
        //button点击事件
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customQuLinePath.setMode(!customQuLinePath.getMode());
            }
        });
    }
}
