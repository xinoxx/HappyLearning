package com.example.happylearning.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.happylearning.Fragment.MainFragment;
import com.example.happylearning.Fragment.SettingFragment;
import com.example.happylearning.R;
import com.example.happylearning.Utils.ApplicationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout ll_main , ll_setting ;
    private TextView text_main , text_setting ;
    private ImageView img_main , img_setting ;

    private MainFragment mainFragment ;
    private SettingFragment settingFragment ;
    private List<Fragment> fragmentList = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        ll_main = (LinearLayout)findViewById(R.id.layout_main) ;
        ll_setting = (LinearLayout)findViewById(R.id.layout_setting) ;
        text_main = (TextView)findViewById(R.id.text_main) ;
        text_setting = (TextView)findViewById(R.id.text_setting) ;
        img_main = (ImageView)findViewById(R.id.img_main) ;
        img_setting = (ImageView)findViewById(R.id.img_setting) ;

        //初始默认选中main
        img_main.setImageResource(R.drawable.main_selected);
        text_main.setTextColor(Color.BLUE);

        //初始化fragment
        mainFragment = new MainFragment() ;
        addFragment(mainFragment);
        showFragment(mainFragment);

        //注册监听事件
        ll_main.setOnClickListener(this);
        ll_setting.setOnClickListener(this);

        //注册到application中
        ApplicationUtil.getInstance().addActivity(this);
    }

    //添加fragment
    private void addFragment( Fragment fragment ){
        //开启事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction() ;
        if( !fragment.isAdded() ){
            //设置简单的过度动画
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN) ;
            //添加fragment
            transaction.add(R.id.main_content , fragment ).commit() ;
            fragmentList.add(fragment) ;
        }
    }

    //显示fragment
    private void showFragment(Fragment fragment){
        //将不需要显示的fragment隐藏起来
        for(Fragment fragment1 : fragmentList ){
            if( fragment1 != fragment ){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction() ;
                transaction.hide(fragment1).commit() ;
            }
        }
        //显示fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction() ;
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN) ;
        transaction.show(fragment).commit() ;
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ){
            //选择主页时
            case R.id.layout_main:{
                if( mainFragment == null ){
                    mainFragment = new MainFragment() ;
                }
                addFragment(mainFragment);
                showFragment(mainFragment);

                //设置选中颜色
                text_main.setTextColor(Color.BLUE);
                img_main.setImageResource(R.drawable.main_selected);

                //设置未选择颜色
                text_setting.setTextColor(Color.BLACK);
                img_setting.setImageResource(R.drawable.setting_default);
            }
            break ;
            //选择设置
            case R.id.layout_setting:{
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                }
                addFragment(settingFragment);
                showFragment(settingFragment);
                //设置选中颜色
                text_setting.setTextColor(Color.BLUE);
                img_setting.setImageResource(R.drawable.setting_selected);

                //设置未选择的颜色
                text_main.setTextColor(Color.BLACK);
                img_main.setImageResource(R.drawable.main_default);
            }
            break ;
            default:
                break;
        }
    }
}
