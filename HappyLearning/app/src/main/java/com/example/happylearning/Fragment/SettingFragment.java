package com.example.happylearning.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.happylearning.Bean.User;
import com.example.happylearning.R;
import com.example.happylearning.Utils.ApplicationUtil;
import com.example.happylearning.Utils.SharedPreferenceUtil;

public class SettingFragment extends Fragment {

    private View view ;
    private Button btn_button ;
    private TextView space ;

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_setting , container , false) ;

        btn_button = view.findViewById(R.id.exit) ;
        space = view.findViewById(R.id.space) ;

        //读取用户信息
        final SharedPreferenceUtil[] spf = {new SharedPreferenceUtil()};
        User user = spf[0].sendUser(getContext()) ;
        String str = user.getUid() + "的个人空间" ;
        space.setText(str);

        btn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferenceUtil spf = new SharedPreferenceUtil() ;
                spf.clearSharedPrf(getContext());
                //退出登录关闭所有activity
                ApplicationUtil.getInstance().exit();

            }
        });
        return view ;
    }
}
