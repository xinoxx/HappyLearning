package com.example.happylearning.Adpater;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titleList ;

    public FragmentAdapter(FragmentManager fm , List<Fragment> fragmentList, List<String> titleList ){
        super(fm);

        this.fragmentList = fragmentList;
        this.titleList = titleList ;
    }

    //获取fragmentList中的对象
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    //获取fragmentList的数量
    @Override
    public int getCount() {
        return fragmentList.size();
    }


    //显示名称
    @Override
    public CharSequence getPageTitle( int position ){
        return titleList.get(position % titleList.size()) ;
    }
}
