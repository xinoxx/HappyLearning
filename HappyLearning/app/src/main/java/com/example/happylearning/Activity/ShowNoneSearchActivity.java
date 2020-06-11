package com.example.happylearning.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.happylearning.R;
import com.example.happylearning.Utils.ApplicationUtil;

@SuppressLint("Registered")
public class ShowNoneSearchActivity extends AppCompatActivity {

    private ImageView img_back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_none_search);

        img_back = (ImageView) findViewById(R.id.search_none_img_back) ;
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ApplicationUtil.getInstance().addActivity(this);
    }
}
