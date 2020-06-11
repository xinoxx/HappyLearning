package com.example.happylearning.Adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.happylearning.Bean.Question;
import com.example.happylearning.R;

import java.util.List;

public class QuestionAdapter extends ArrayAdapter<Question> {

    private int resourceId ;

    public QuestionAdapter(Context context , int textViewResourceId , List<Question> objects ){
        super(context , textViewResourceId , objects ) ;
        resourceId = textViewResourceId ;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent){

        Question question = getItem(position) ;
        View view ;
        //使用ViewHolder，让ListView滚动时能快速设置值
        ViewHolder viewHolder ;
        //若无可重用的View ， 则进行加载
        if( convertView == null ){
            view = LayoutInflater.from(getContext()).inflate(resourceId , parent , false) ;
            //初始化viewHolder，方便重用
            viewHolder = new ViewHolder() ;
            viewHolder.questionTitle = view.findViewById(R.id.question_item_title) ;
            viewHolder.questionClassification = view.findViewById(R.id.question_item_classification) ;
            viewHolder.questionType = view.findViewById(R.id.question_item_type) ;
            view.setTag(viewHolder);
        }
        //否则重用
        else{
            view = convertView ;
            viewHolder = (ViewHolder)view.getTag() ;
        }

        //设置显示内容
        viewHolder.questionTitle.setText(question.getTitle());
        viewHolder.questionClassification.setText(question.getClassification());
        int type = question.getType() ;
        if( type == 1 )
            viewHolder.questionType.setText("单选题");
        else if ( type == 2 )
            viewHolder.questionType.setText("多选题");
        else if( type == 3 )
            viewHolder.questionType.setText("客观题");

        return view ;
    }

    //自定义一个ViewHolder
    static class ViewHolder{

        TextView questionTitle ;
        TextView questionClassification ;
        TextView questionType ;
    }



}
