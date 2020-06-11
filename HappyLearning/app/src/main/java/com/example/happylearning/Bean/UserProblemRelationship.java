package com.example.happylearning.Bean;

public class UserProblemRelationship {

    private String qid ;
    private String uid ;
    private String stu_answer ;
    private int result ;
    private String date ;

    public UserProblemRelationship(){}

    public UserProblemRelationship(String qid, String uid, String stu_answer, int result, String date) {
        this.qid = qid;
        this.uid = uid;
        this.stu_answer = stu_answer;
        this.result = result;
        this.date = date;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStu_answer() {
        return stu_answer;
    }

    public void setStu_answer(String stu_answer) {
        this.stu_answer = stu_answer;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
