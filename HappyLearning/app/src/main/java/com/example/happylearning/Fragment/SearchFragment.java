package com.example.happylearning.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.happylearning.Activity.ShowNoneSearchActivity;
import com.example.happylearning.Activity.ShowQuesChooseActivity;
import com.example.happylearning.Activity.ShowQuesObjectiveActivity;
import com.example.happylearning.Adpater.QuestionAdapter;
import com.example.happylearning.Bean.Question;
import com.example.happylearning.R;
import com.example.happylearning.Utils.MySQLiteOpenHelper;
import com.example.happylearning.Utils.SQLManagement;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private View view ;
    private ListView listView ;
    private List<Question> questionList = new ArrayList<>() ;
    private QuestionAdapter questionAdapter ;
    private SQLiteDatabase myDatabase ;
    private String searchText ;
    private SwipeRefreshLayout swipe ;

    public SearchFragment(){}

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(final LayoutInflater inflater , ViewGroup container , Bundle saveInstanceState){

        view = inflater.inflate(R.layout.fragment_question , container , false ) ;

        //初始化控件
        listView = view.findViewById(R.id.listView_question) ;
        swipe = view.findViewById(R.id.swipe_refresh) ;

        //为SwipeRefreshLayout设置监听事件
        swipe.setOnRefreshListener(this);
        //为SwipeRefreshLayout设置刷新时的颜色变化
        swipe.setColorSchemeColors(R.color.colorAccent);

        //通过宿主activity拿到Intent里的值
        searchText = getActivity().getIntent().getStringExtra("searchText") ;
        System.out.println("获得的搜索内容：" + searchText );

        //获得路径
        String path = MySQLiteOpenHelper.FILE_DIR + "happyLearning_db" ;
        //打开数据库
        myDatabase = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY) ;
        SQLManagement management = new SQLManagement() ;
        questionList = management.findByTitle(searchText,myDatabase) ;

        questionAdapter = new QuestionAdapter(getContext(), R.layout.question_item, questionList);
        listView.setAdapter(questionAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //三种问题类型跳转
                int type = questionList.get(position).getType();
                Intent intent;
                if (type == 1 || type == 2) {
                    intent = new Intent(getContext(), ShowQuesChooseActivity.class);
                } else {
                    intent = new Intent(getContext(), ShowQuesObjectiveActivity.class);
                }
                //传值
                intent.putExtra("qid", questionList.get(position).getQid());
                startActivity(intent);
            }
        });

        return view ;
    }

    @Override
    public void onRefresh() {
        //模拟耗时操作
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext() ,"无新内容了",Toast.LENGTH_SHORT).show();
                //取消刷新
                swipe.setRefreshing(false);
            }
        },2000) ;
    }
}
