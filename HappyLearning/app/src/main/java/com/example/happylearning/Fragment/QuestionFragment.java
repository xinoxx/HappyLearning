package com.example.happylearning.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.happylearning.Activity.ShowQuesChooseActivity;
import com.example.happylearning.Activity.ShowQuesObjectiveActivity;
import com.example.happylearning.Adpater.QuestionAdapter;
import com.example.happylearning.Bean.Question;
import com.example.happylearning.R;
import com.example.happylearning.Utils.MySQLiteOpenHelper;
import com.example.happylearning.Utils.SQLManagement;

import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View view ;
    private ListView listView ;
    private List<Question> questionList = new ArrayList<>() ;
    private QuestionAdapter questionAdapter ;
    private SwipeRefreshLayout swipe ;
    private SQLiteDatabase myDatabase ;
    private String subject ;

    public QuestionFragment(){}

    public QuestionFragment(String subject ){
        this.subject = subject ;
    }

    //动态加载fragment布局
    //SwipeRefreshLayout下拉刷新布局
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(final LayoutInflater inflater , ViewGroup container , Bundle saveInstanceState ){

        view = inflater.inflate(R.layout.fragment_question , container , false ) ;
        initQuestion() ;
        initView() ;

        //为SwipeRefreshLayout设置监听事件
        swipe.setOnRefreshListener(this);
        //为SwipeRefreshLayout设置刷新时的颜色变化
        swipe.setColorSchemeColors(R.color.colorAccent);

        questionAdapter = new QuestionAdapter(getContext() , R.layout.question_item , questionList ) ;
        //设置适配器
        listView.setAdapter(questionAdapter);
        //添加监听事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //三种问题类型跳转
                int type = questionList.get(position).getType() ;
                String str = questionList.get(position).getTitle() ;
                Intent intent ;
                if( ( type == 1 || type == 2 ) && !str.equals("下拉刷新-测试标题") && !str.equals("测试标题")){
                    intent = new Intent(getContext(), ShowQuesChooseActivity.class);
                    //传值
                    intent.putExtra("qid" , questionList.get(position).getQid()) ;
                    startActivity(intent);
                }
                else if( type == 3 && !str.equals("下拉刷新-测试标题") && !str.equals("测试标题") ){
                    intent = new Intent(getContext() , ShowQuesObjectiveActivity.class) ;
                    //传值
                    intent.putExtra("qid" , questionList.get(position).getQid()) ;
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"测试标题，无具体内容！",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view ;
    }

    private void initView() {
        listView = view.findViewById(R.id.listView_question) ;
        swipe = view.findViewById(R.id.swipe_refresh) ;
    }

    private void initQuestion() {

        questionList.clear();

        SQLManagement management = new SQLManagement() ;

        //获得路径
        String path = MySQLiteOpenHelper.FILE_DIR + "happyLearning_db" ;
        //打开数据库
        myDatabase = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY) ;
        //获得游标
        Cursor cursor = myDatabase.rawQuery( "select * from Question " , new String[]{}) ;
        System.out.println("cursor数量：" + cursor.getCount());
        //移到光标第一行
        cursor.moveToFirst() ;
        //isAfterLast游标是否指向最后一行的位置
        while( !cursor.isAfterLast()){
            Question question = new Question() ;
            question = management.setQuestion(cursor) ;
            if( question.getSubject().equals(subject))
            questionList.add(question);
            cursor.moveToNext();
        }
        cursor.close();
        myDatabase.close();

        //提供的测试题目，可删可修改
        for( int i = 0 ; i < 6 ; i++ ){
            Question question1 = new Question("", "", "测试标题", "", "",null, 1, "", "") ;
            questionList.add(question1) ;
        }
    }

    @Override
    public void onRefresh() {
        //模拟耗时操作
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //刷新
                refreshData() ;
                //取消刷新
                swipe.setRefreshing(false);
            }
        },2000) ;
    }

    private void refreshData(){
        //提供测试数据，可删除，未存入数据库
        Question question = new Question("", "", "下拉刷新-测试标题", "", "",null, 1, "", "") ;
        //在列表首加载数据
        questionList.add(0,question);
        //通知listView，数据刷新了
        questionAdapter.notifyDataSetChanged();
    }
}
