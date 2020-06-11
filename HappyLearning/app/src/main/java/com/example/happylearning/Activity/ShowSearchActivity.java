package com.example.happylearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.happylearning.Fragment.MainFragment;
import com.example.happylearning.Fragment.QuestionFragment;
import com.example.happylearning.Fragment.SearchFragment;
import com.example.happylearning.R;
import com.example.happylearning.Utils.ApplicationUtil;

public class ShowSearchActivity extends AppCompatActivity {

    private ImageView img_back ;
    private SearchFragment searchFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_search);

        //初始化控件
        img_back = (ImageView) findViewById(R.id.search_img_back) ;

        //开启事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction() ;
        searchFragment = new SearchFragment() ;
        if( !searchFragment.isAdded() ){
            //设置简单的过度动画
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN) ;
            //添加fragment
            transaction.add(R.id.search_content , searchFragment ).commit() ;
        }

        img_back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish() ;
            }
        });

        //注册到application中
        ApplicationUtil.getInstance().addActivity(this);

    }
}
