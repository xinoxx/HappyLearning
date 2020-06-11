package com.example.happylearning.Utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

import com.example.happylearning.Bean.Question;
import com.example.happylearning.Bean.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLManagement {

    private List<String> sqlList ;

    public SQLManagement(){}

    public List<String> Init(){

        sqlList = new ArrayList<>() ;
        //写入数据
        String sql = "" ;
        sql= "insert into Question values('0001' , '语文' , '下列对一首五律颔联和颈联的补充，选出最恰当的一项(  )'," +
                "'____汉阳渡，初日郢门山。（颔联）\n江上几人在，天涯孤棹__。（颈联）&风高  还&风高  回&高风  还&高风  回' , '古诗篇' , null , 1, 'C' , '无')" ;
        sqlList.add(sql) ;
        sql = "insert into Question values('0002' , '语文' , '请运用所积累的知识，完成（1）~（4）题。'," +
                "'阿基姆明白，这个直到不久前还生龙活虎的年轻人，此刻内心激荡着怎样的情感。他了解保尔的悲剧……\n" +
                "“阿基姆，难道你真的以为生活能把我逼进死角，把我压成一张薄饼吗？只要我的心脏还在跳动，”他突然使劲抓住阿基姆的手紧压着他的胸脯，“只要它还在跳动，就别想叫我离开党。只有死，才能让我离开战斗行列。老兄，请你千万记住这一点。”" +
                "&（1）以上文段选自《__________________》，作者是____________。\n（2）“生龙活虎”在句子中的意思是__________________________________________。" +
                "\n（3）请结合原著回答：文段中“悲剧”指什么？“呐喊”表达了保尔怎样的心声？' , '阅读篇' , null , 3, '（1）钢铁是怎样炼成的；奥斯特洛夫斯基\n" +
                "       （2）形容很有生气和活力\n       （3）“悲剧”指完全瘫痪，双目失明；" +
                "“呐喊”是指为解放全人类而斗争。' , '见原文后面解析')" ;
        sqlList.add(sql) ;
        sql = "insert into Question values('0003' , '语文' , '下列各句中有语病的句子是（）'," +
                "'&我的书包里还缺乏一个像样的铅笔盒。&" +
                "他穿着一件灰大衣和一顶红帽子。&她想念她的幼子，而不便说出来。&" +
                "战士的英勇顽强，奋不顾身的优秀品质' , '句子篇' , null , 2, 'BD' , 'B:他穿着一件灰大衣和戴着一顶红帽子" +
                "\n      D:战士有英勇顽强、奋不顾身的优秀品质')" ;
        sqlList.add(sql) ;
        return sqlList ;

    }

    public Question findByQid(String qid , SQLiteDatabase myDatabase ){

        Question question = new Question();
        //获得游标
        Cursor cursor = myDatabase.rawQuery( "select * from Question where qid=?" , new String[]{qid}) ;
        System.out.println("cursor数量：" + cursor.getCount());
        //移到光标第一行
        cursor.moveToFirst() ;
        question = setQuestion(cursor) ;

        cursor.close();
        myDatabase.close();

        return question ;
    }

    public List<Question> findByTitle( String search_text , SQLiteDatabase myDatabase ){

        List<Question> questionList = new ArrayList<>() ;
        Cursor cursor = myDatabase.rawQuery("select * from Question where title like '%" + search_text + "%'" , new String[]{}) ;
        System.out.println("cursor数量：" + cursor.getCount());
        cursor.moveToFirst() ;
        while( !cursor.isAfterLast() ){
            Question question = setQuestion(cursor) ;
            questionList.add(question) ;
            cursor.moveToNext() ;
        }
        return questionList ;
    }

    public Question setQuestion( Cursor cursor ){

        Question question = new Question() ;
        question.setQid(cursor.getString(cursor.getColumnIndex("qid")));
        question.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
        question.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        question.setContent(cursor.getString(cursor.getColumnIndex("content")));
        question.setClassification(cursor.getString(cursor.getColumnIndex("classification")));
        //图片格式从数据库中取出的blob转换成bitmap格式
        byte []picture ;
        picture = cursor.getBlob(cursor.getColumnIndex("picture")) ;
        if( picture!=null)
            question.setPicture(BitmapFactory.decodeByteArray( picture ,0, picture.length));
        else
            question.setPicture(null);
        question.setType(cursor.getInt(cursor.getColumnIndex("type")));
        question.setAnswer(cursor.getString(cursor.getColumnIndex("answer")));
        question.setAnalysis(cursor.getString(cursor.getColumnIndex("analysis")));
        return question ;
    }

}
