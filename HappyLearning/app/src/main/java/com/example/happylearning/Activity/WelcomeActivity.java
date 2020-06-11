package com.example.happylearning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.happylearning.R;
import com.example.happylearning.Utils.ApplicationUtil;

/**
 * 欢迎界面
 */

public class WelcomeActivity extends AppCompatActivity {

    private Button btn_jump ;

    //线程实现该页面显示3秒  3秒后自动跳转
    final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                //页面跳转
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class) ;
                startActivity(intent) ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        thread.start();

        btn_jump = (Button)findViewById(R.id.btn_jump) ;

        //手动点击跳转入登录界面
        btn_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //页面跳转
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class) ;
            startActivity(intent) ;
            thread.interrupt();
            //activity动作完成
            finish();
            }
        });

        //添加activity
        ApplicationUtil.getInstance().addActivity(this);
    }



}
