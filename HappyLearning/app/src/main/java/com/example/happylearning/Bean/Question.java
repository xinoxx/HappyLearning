package com.example.happylearning.Bean;

import android.graphics.Bitmap;

public class Question {

    private String qid ;
    private String subject ;
    private String title ;
    private String content;
    private String classification ;
    private Bitmap picture ;
    private int type ;
    private String answer ;
    private String analysis ;

    public Question(){}

    public Question(String qid, String subject, String title, String content, String classification, Bitmap picture, int type, String answer, String analysis) {
        this.qid = qid;
        this.subject = subject;
        this.title = title;
        this.content = content;
        this.classification = classification;
        this.picture = picture;
        this.type = type;
        this.answer = answer;
        this.analysis = analysis;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
}
