package com.example.happylearning.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.List;

/**
 * 创建数据库
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    //数据库文件存储目录
    public static final String FILE_DIR = "/data/data/com.example.happylearning/databases/" ;

    private List<String> sqlList ;
    private static final int DATABASE_VERSION = 1 ;
    private Context mContext ;

    //建表语句
    private static final String table_user = "create table if not exists User(" +
            "uid varchar(20) primary key ," +
            "pwd varchar(20))" ;
    private static final String table_question = "create table if not exists Question(" +
            "qid varchar(20) primary key ," +
            "subject varchar(50) ," +
            "title text ," +
            "content text ," +
            "classification varchar(50) ," +
            "picture blob ," +
            "type integer ," +
            "answer text ," +
            "analysis text)" ;
    private static final String table_relation = "create table if not exists Relation(" +
            "uid varchar(20) ," +
            "qid varchar(20) ," +
            "stu_answer text ," +
            "result integer ," +
            "date varchar(30)," +
            "FOREIGN KEY (uid)" +
            "REFERENCES User(uid)," +
            "FOREIGN KEY (qid)" +
            "REFERENCES Question(qid))" ;

    //删表语句
    private static final String delete_table_user = "drop table if exists User" ;
    private static final String delete_table_question = "drop table if exists Question" ;
    private static final String delete_table_relation = "drop table if exists Relation" ;

    public MySQLiteOpenHelper(@Nullable Context context , @Nullable String name){
        super(context , name , null , DATABASE_VERSION) ;
        mContext = context ;
    }

    //onCreate方法在第一次打开数据库时执行，在此进行表结构创建的操作
    @Override
    public void onCreate(SQLiteDatabase db) {

        File dir = new File(FILE_DIR) ;
        if( !dir.exists()) dir.mkdir() ;

        db.execSQL(table_user);
        db.execSQL(table_question);
        db.execSQL(table_relation);
        //初始化数据库数据
        SQLManagement SQLManagement = new SQLManagement() ;
        sqlList = SQLManagement.Init() ;
        for( String sql : sqlList ){
            db.execSQL(sql);
        }
    }

    //onUpgrade方法在数据库版本升高时执行，在此根据旧版本号进行表结构变更处理
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if( newVersion > oldVersion ){
            db.execSQL(delete_table_user);
            db.execSQL(delete_table_question);
            db.execSQL(delete_table_relation);
            onCreate(db);
        }
    }
}
