package com.example.happylearning.Fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.happylearning.Activity.ShowNoneSearchActivity;
import com.example.happylearning.Activity.ShowSearchActivity;
import com.example.happylearning.Adpater.FragmentAdapter;
import com.example.happylearning.Bean.Question;
import com.example.happylearning.R;
import com.example.happylearning.Utils.MySQLiteOpenHelper;
import com.example.happylearning.Utils.SQLManagement;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private Button btn_search ;
    private EditText search_text ;
    private View view ;
    private TabLayout top_tag ;
    private ViewPager viewPager ;
    private List<String> titleList ;
    private List<Fragment> fragmentsList ;
    private FragmentAdapter fragmentAdapter ;

    private QuestionFragment chineseFragment ;
    private QuestionFragment mathFragment ;
    private QuestionFragment englishFragment ;
    private QuestionFragment chemicalFragment ;
    private QuestionFragment physicalFragment ;
    private QuestionFragment biologyFragment ;

    private SQLiteDatabase myDatabase ;

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_main , container , false) ;
        initView() ;
        fragmentChange();
        return view ;
    }

    private void initView(){
        top_tag = view.findViewById(R.id.top_tab) ;
        viewPager = view.findViewById(R.id.view_pager) ;

        btn_search = view.findViewById(R.id.btn_search) ;
        search_text = view.findViewById(R.id.search_text) ;

        search_text.setText("");

        //设置监听器
        btn_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if( search_text.getText().toString().equals("") ){
                    Toast.makeText(getContext() , "请输入有效信息" , Toast.LENGTH_SHORT).show();
                }
                else {

                    //获得路径
                    String path = MySQLiteOpenHelper.FILE_DIR + "happyLearning_db" ;
                    //打开数据库
                    myDatabase = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY) ;
                    SQLManagement management = new SQLManagement() ;
                    List<Question> questionList = new ArrayList<>() ;
                    questionList = management.findByTitle(search_text.getText().toString(),myDatabase) ;

                    Intent intent ;
                    if( questionList.isEmpty() ){
                        intent = new Intent(getContext() , ShowNoneSearchActivity.class) ;
                    }else{
                        intent = new Intent(getContext(), ShowSearchActivity.class);
                        intent.putExtra("searchText", search_text.getText().toString());
                    }
                    startActivity(intent);
                }
            }
        });

    }

    private void fragmentChange(){

        fragmentsList = new ArrayList<>() ;

        chineseFragment = new QuestionFragment("语文") ;
        mathFragment = new QuestionFragment("数学") ;
        englishFragment = new QuestionFragment("英语") ;
        chemicalFragment = new QuestionFragment("化学") ;
        physicalFragment = new QuestionFragment("物理") ;
        biologyFragment = new QuestionFragment("生物") ;

        fragmentsList.add(chineseFragment) ;
        fragmentsList.add(mathFragment) ;
        fragmentsList.add(englishFragment) ;
        fragmentsList.add(chemicalFragment) ;
        fragmentsList.add(physicalFragment) ;
        fragmentsList.add(biologyFragment);

        titleList = new ArrayList<>() ;
        titleList.add("语文") ;
        titleList.add("数学") ;
        titleList.add("英语") ;
        titleList.add("化学") ;
        titleList.add("物理") ;
        titleList.add("生物") ;

        fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager() , fragmentsList , titleList );
        //设置适配器
        viewPager.setAdapter(fragmentAdapter);

        //将tabLayout与viewPager连起来
        top_tag.setupWithViewPager(viewPager);
    }


}
