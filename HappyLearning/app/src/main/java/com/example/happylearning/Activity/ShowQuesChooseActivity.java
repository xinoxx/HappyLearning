package com.example.happylearning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ShowQuesChooseActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back ;
    private TextView title ;
    private TextView chooseText1 , chooseText2 , chooseText3 , chooseText4 ;
    private ImageView chooseA , chooseB , chooseC , chooseD ;
    private Button btn_submit ;
    private LinearLayout choose1 , choose2 , choose3 , choose4 , choose_layout ;

    private SQLiteDatabase myDatabase ;
    private Question question ;
    private String userChoose = "" ;
    private int userResult ;

    //动态设计多选按钮按键次数,来实现点击一次改变状态
    private int []clickTimes = {0,0,0,0} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ques_choose);

        initView();
        //获得路径
        String path = MySQLiteOpenHelper.FILE_DIR + "happyLearning_db" ;
        //打开数据库
        myDatabase = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY) ;

        //获得传值
        Intent intent = getIntent() ;
        String qid = intent.getStringExtra("qid") ;
        //从数据库获得内容
        SQLManagement management = new SQLManagement() ;
        question = management.findByQid(qid , myDatabase);

        showQuestion(question);

        //注册监听器
        back.setOnClickListener(this);
        choose1.setOnClickListener(this);
        choose2.setOnClickListener(this);
        choose3.setOnClickListener(this);
        choose4.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        //注册到application
        ApplicationUtil.getInstance().addActivity(this);

    }

    //设置内容
    private void showQuestion(Question question){

        String title = question.getTitle() ;
        String content = question.getContent() ;
        String []contentSplit = content.split("&") ;
        System.out.println("split长度：" + contentSplit.length );
        title = title + "\n" + contentSplit[0] ;
        this.title.setText(title) ;
        chooseText1.setText(contentSplit[1]);
        chooseText2.setText(contentSplit[2]);
        chooseText3.setText(contentSplit[3]);
        chooseText4.setText(contentSplit[4]);

    }

    //初始化控件
    private void initView(){
        back = (ImageView) findViewById(R.id.choose_img_back) ;
        title = (TextView) findViewById(R.id.title) ;
        //题目设置滚屏
        title.setMovementMethod(new ScrollingMovementMethod());

        chooseText1 = (TextView) findViewById(R.id.chooseText1) ;
        chooseText2 = (TextView) findViewById(R.id.chooseText2) ;
        chooseText3 = (TextView) findViewById(R.id.chooseText3) ;
        chooseText4 = (TextView) findViewById(R.id.chooseText4) ;

        chooseA = (ImageView) findViewById(R.id.chooseA) ;
        chooseB = (ImageView) findViewById(R.id.chooseB) ;
        chooseC = (ImageView) findViewById(R.id.chooseC) ;
        chooseD = (ImageView) findViewById(R.id.chooseD) ;

        btn_submit = (Button) findViewById(R.id.btn_submit) ;

        choose1 = (LinearLayout) findViewById(R.id.choose1) ;
        choose2 = (LinearLayout) findViewById(R.id.choose2) ;
        choose3 = (LinearLayout) findViewById(R.id.choose3) ;
        choose4 = (LinearLayout) findViewById(R.id.choose4) ;
        choose_layout = (LinearLayout) findViewById(R.id.choose_layout) ;
    }

    //点击事件
    @Override
    public void onClick(View v) {

        switch( v.getId()){
            case R.id.choose_img_back:{
                finish();
            }
            break ;
            case R.id.btn_submit:{
                //保存记录
                saveRecord() ;
                Intent intent = new Intent(ShowQuesChooseActivity.this, ShowResultActivity.class ) ;
                intent.putExtra("qid" , question.getQid() ) ;
                intent.putExtra("result" , String.valueOf(userResult) ) ;
                startActivity(intent) ;
                //该activity关闭
                finish() ;
            }
            break ;
            case R.id.choose1:{
                //单选按钮特效
                if( question.getType() == 1 ) {
                    modifyResource(choose1);
                    userChoose = "A";
                }
                //多选按钮特效
                else{
                    clickTimes[0]++ ;
                    modifyResourceMultiple();
                }
            }
            break ;
            case R.id.choose2:{
                if( question.getType() == 1 ) {
                    modifyResource(choose2);
                    userChoose = "B";
                }else{
                    clickTimes[1]++ ;
                    modifyResourceMultiple();
                }
            }
            break ;
            case R.id.choose3:{
                if( question.getType() == 1 ) {
                    modifyResource(choose3);
                    userChoose = "C";
                }else{
                    clickTimes[2]++ ;
                    modifyResourceMultiple();
                }
            }
            break ;
            case R.id.choose4:{
                if( question.getType() == 1 ) {
                    modifyResource(choose4);
                    userChoose = "D";
                }else{
                    clickTimes[3]++ ;
                    modifyResourceMultiple();
                }
            }
            break ;
            default:break ;
        }
    }

    //存储完成记录
    private void saveRecord(){

        //读取用户信息
        SharedPreferenceUtil spf = new SharedPreferenceUtil() ;
        User user = spf.sendUser(this) ;

        //多选结果要特判
        if( question.getType() == 2 ){

            for( int i = 0 ; i < 4 ; i++ ){
                if( clickTimes[i]%2 == 1 ){
                    if( i==0 ) userChoose += "A" ;
                    if( i==1 ) userChoose += "B" ;
                    if( i==2 ) userChoose += "C" ;
                    if( i==3 ) userChoose += "D" ;
                }
            }
        }

        //1正确，0失败
        if (question.getAnswer().equals(userChoose)) userResult = 1;
        else userResult = 0;
        System.out.println("用户答案：" + userResult );
        System.out.println("数据库答案：" + question.getAnswer() );

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
        String sql = "insert into Relation values('" + user.getUid() + "','" + question.getQid() + "','"+ userChoose + "', " + userResult + ",'" + nowDate+ "')" ;
        db.execSQL(sql);
        db.close();

    }

    //修改其他组件状态:单选
    private void modifyResource( LinearLayout choose ){

        Drawable drawable = getResources().getDrawable(R.drawable.shape_backgroud) ;
        if( choose == choose1 ){
            chooseA.setImageResource(R.drawable.a_selected);
            chooseB.setImageResource(R.drawable.b_default);
            chooseC.setImageResource(R.drawable.c_default);
            chooseD.setImageResource(R.drawable.d_default);

            choose1.setBackgroundResource(R.drawable.shape_backgroud);
            choose2.setBackgroundResource(R.drawable.shape_border);
            choose3.setBackgroundResource(R.drawable.shape_border);
            choose4.setBackgroundResource(R.drawable.shape_border);
        }else if( choose == choose2 ){
            chooseB.setImageResource(R.drawable.b_selected);
            chooseA.setImageResource(R.drawable.a_default);
            chooseC.setImageResource(R.drawable.c_default);
            chooseD.setImageResource(R.drawable.d_default);

            choose2.setBackgroundResource(R.drawable.shape_backgroud);
            choose1.setBackgroundResource(R.drawable.shape_border);
            choose3.setBackgroundResource(R.drawable.shape_border);
            choose4.setBackgroundResource(R.drawable.shape_border);
        }else if( choose == choose3 ){
            chooseC.setImageResource(R.drawable.c_selected);
            chooseA.setImageResource(R.drawable.a_default);
            chooseB.setImageResource(R.drawable.b_default);
            chooseD.setImageResource(R.drawable.d_default);

            choose3.setBackgroundResource(R.drawable.shape_backgroud);
            choose1.setBackgroundResource(R.drawable.shape_border);
            choose2.setBackgroundResource(R.drawable.shape_border);
            choose4.setBackgroundResource(R.drawable.shape_border);
        }else if( choose == choose4 ){
            chooseD.setImageResource(R.drawable.d_selected);
            chooseA.setImageResource(R.drawable.a_default);
            chooseB.setImageResource(R.drawable.b_default);
            chooseC.setImageResource(R.drawable.c_default);

            choose4.setBackgroundResource(R.drawable.shape_backgroud);
            choose1.setBackgroundResource(R.drawable.shape_border);
            choose2.setBackgroundResource(R.drawable.shape_border);
            choose3.setBackgroundResource(R.drawable.shape_border);
        }
    }

    //修改组件状态：多选
    private void modifyResourceMultiple(){

        if( clickTimes[0]%2 == 0 ){
            choose1.setBackgroundResource(R.drawable.shape_border);
            chooseA.setImageResource(R.drawable.a_default);
        }else{
            choose1.setBackgroundResource(R.drawable.shape_backgroud);
            chooseA.setImageResource(R.drawable.a_selected);
        }
        if( clickTimes[1]%2 == 0 ){
            choose2.setBackgroundResource(R.drawable.shape_border);
            chooseB.setImageResource(R.drawable.b_default);
        }else{
            choose2.setBackgroundResource(R.drawable.shape_backgroud);
            chooseB.setImageResource(R.drawable.b_selected);
        }
        if( clickTimes[2]%2 == 0 ){
            choose3.setBackgroundResource(R.drawable.shape_border);
            chooseC.setImageResource(R.drawable.c_default);
        }else{
            choose3.setBackgroundResource(R.drawable.shape_backgroud);
            chooseC.setImageResource(R.drawable.c_selected);
        }
        if( clickTimes[3]%2 == 0 ){
            choose4.setBackgroundResource(R.drawable.shape_border);
            chooseD.setImageResource(R.drawable.d_default);
        }else{
            choose4.setBackgroundResource(R.drawable.shape_backgroud);
            chooseD.setImageResource(R.drawable.d_selected);
        }
    }
}
