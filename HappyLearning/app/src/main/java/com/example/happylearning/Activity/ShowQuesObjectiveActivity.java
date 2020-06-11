package com.example.happylearning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.happylearning.Bean.Question;
import com.example.happylearning.Bean.User;
import com.example.happylearning.R;
import com.example.happylearning.Utils.ApplicationUtil;
import com.example.happylearning.Utils.MySQLiteOpenHelper;
import com.example.happylearning.Utils.SQLManagement;
import com.example.happylearning.Utils.SharedPreferenceUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowQuesObjectiveActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView img_back ;
    private TextView title, stu_answer;
    private Button btn_submit ;
    private SQLiteDatabase myDatabase ;
    private Question question ;
    private String userResult ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ques_objective);

        //初始化控件
        img_back = (ImageView) findViewById(R.id.objective_img_back) ;
        title = (TextView) findViewById(R.id.objective_title) ;
        title.setMovementMethod(new ScrollingMovementMethod());
        stu_answer = (TextView) findViewById(R.id.stu_answer) ;
        btn_submit = (Button) findViewById(R.id.objective_btn_submit) ;

        //获得Intent传值
        Intent intent = getIntent() ;
        String qid = intent.getStringExtra("qid") ;

        //获得路径
        String path = MySQLiteOpenHelper.FILE_DIR + "happyLearning_db" ;
        //打开数据库
        myDatabase = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY) ;

        //从数据库获得内容
        SQLManagement management = new SQLManagement() ;
        question = management.findByQid(qid , myDatabase);

        //输出数据到界面
        String content = question.getTitle() + "\n" + question.getContent() ;
        title.setText(content);

        //注册点击事件
        img_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        //注册至ApplicationUtil中
        ApplicationUtil.getInstance().addActivity(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.objective_img_back:{
                finish() ;
            }
            break ;
            case R.id.objective_btn_submit:{
                //获取用户输入
                userResult = stu_answer.getText().toString() ;
                saveRecord();
                Intent intent = new Intent(ShowQuesObjectiveActivity.this , ShowResultActivity.class) ;
                intent.putExtra("qid" , question.getQid() ) ;
                intent.putExtra("result" , "2" ) ;
                startActivity(intent) ;
                finish();
            }
            break ;
            default: break ;
        }
    }

    //存储用户回答记录
    private void saveRecord(){

        //读取用户信息
        SharedPreferenceUtil spf = new SharedPreferenceUtil() ;
        User user = spf.sendUser(this) ;

        //获取答题时间
        String nowDate ;
        Date date = new Date() ;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat() ;
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        nowDate = sdf.format(date) ;

        //存入数据库
        //获得路径
        String path = MySQLiteOpenHelper.FILE_DIR + "happyLearning_db" ;
        //打开数据库
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE) ;
        String sql = "insert into Relation values('" + user.getUid() + "','" + question.getQid() + "','"+ userResult + "', 2 ,'" + nowDate+ "')" ;
        db.execSQL(sql);
        db.close();

    }
}
