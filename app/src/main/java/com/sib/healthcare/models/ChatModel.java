package com.sib.healthcare.models;


import com.google.firebase.Timestamp;

public class ChatModel {
    private String msg , fUid , fName;
    private int type;
    private Timestamp timestamp;
    public static  final  int MY_MSG=1;
    public static final int FRIEND_MSG=2;

    public ChatModel() {
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public ChatModel(String msg, int type, Timestamp timestamp, String fUid) {
        this.msg = msg;
        this.type = type;
        this.timestamp = timestamp;
        this.fUid=fUid;
    }

    public String getfUid() {
        return fUid;
    }

    public void setfUid(String fUid) {
        this.fUid = fUid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
