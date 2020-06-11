package com.example.happylearning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happylearning.R;
import com.example.happylearning.Utils.ApplicationUtil;
import com.example.happylearning.Utils.MySQLiteOpenHelper;
import com.example.happylearning.Utils.SharedPreferenceUtil;

/**
 * 登录界面
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView login_uid ;
    private TextView login_pwd ;
    private Button btn_login ;
    private MySQLiteOpenHelper myDatabase ;

    private static final String DATABASE = "happyLearning_db" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化控件
        login_uid = (TextView)findViewById(R.id.login_uid) ;
        login_pwd = (TextView)findViewById(R.id.login_pwd) ;
        btn_login = (Button)findViewById(R.id.btn_login) ;

        //实例化数据库
        myDatabase = new MySQLiteOpenHelper(this, DATABASE ) ;

        //注册监听事件
        btn_login.setOnClickListener((View.OnClickListener) this);

        //注册到Application
        ApplicationUtil.getInstance().addActivity(this);
    }


    @Override
    public void onClick(View v) {

        //打开数据库
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase() ;

        //获取界面信息
        String uid = login_uid.getText().toString() ;
        String pwd = login_pwd.getText().toString() ;

        //获得游标
        Cursor cursor = sqLiteDatabase.rawQuery( "select * from User where uid = ?" , new String[]{uid}) ;
        if( cursor.getCount() == 0 ){
            Toast.makeText(LoginActivity.this,"您是新用户，自动注册",Toast.LENGTH_SHORT).show();
            //向数据库中添加数据
            String addUser = "insert into User(uid,pwd) values('" + uid +
                    "' , '" + pwd + "')" ;
            sqLiteDatabase.execSQL(addUser);

            //存至SharedPrf
            SharedPreferenceUtil spu = new SharedPreferenceUtil() ;
            spu.saveUser(this,uid,pwd);

            //页面跳转
            Intent intent = new Intent(LoginActivity.this, MainActivity.class) ;
            startActivity(intent);
        }
        else{
            if( cursor.moveToFirst() ){
                String db_pwd = cursor.getString(cursor.getColumnIndex("pwd")) ;
                if( pwd.equals(db_pwd)){
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();

                    //存至SharedPrf
                    SharedPreferenceUtil spu = new SharedPreferenceUtil() ;
                    spu.saveUser(this,uid,pwd);

                    //页面跳转
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class) ;
                    startActivity(intent);

                }
                else{
                    Toast.makeText(LoginActivity.this,"密码错误！",Toast.LENGTH_SHORT).show();
                    //清空文本
                    login_pwd.setText("");
                }
            }
        }
        cursor.close();
        sqLiteDatabase.close();
    }

}
