package com.example.happylearning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happylearning.Bean.Question;
import com.example.happylearning.R;
import com.example.happylearning.Utils.ApplicationUtil;
import com.example.happylearning.Utils.MySQLiteOpenHelper;
import com.example.happylearning.Utils.SQLManagement;

public class ShowResultActivity extends AppCompatActivity {

    private ImageView btn_back , img_result ;
    private TextView showResult ;

    private SQLiteDatabase myDatabase ;
    private Question question ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct);

        btn_back = (ImageView) findViewById(R.id.btn_back1) ;
        showResult = (TextView) findViewById(R.id.show_result) ;
        img_result = (ImageView) findViewById(R.id.img_result) ;

        //获得传值
        Intent intent = getIntent() ;
        String qid = intent.getStringExtra("qid") ;
        String userResult = intent.getStringExtra("result") ;

        if(userResult.equals("0")){
            img_result.setImageResource(R.drawable.error);
        }else if(userResult.equals("2")){
            img_result.setImageResource(R.drawable.objective);
        }

        //获得路径
        String path = MySQLiteOpenHelper.FILE_DIR + "happyLearning_db" ;
        //打开数据库
        myDatabase = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY) ;

        //获取question
        SQLManagement management = new SQLManagement() ;
        question = management.findByQid(qid , myDatabase ) ;

        String result = "正确答案：\n      " + question.getAnswer() ;
        result += ("\n解析：\n      " + question.getAnalysis()) ;
        showResult.setText(result);

        //注册监听事件
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //注册到application
        ApplicationUtil.getInstance().addActivity(this);
    }
}
