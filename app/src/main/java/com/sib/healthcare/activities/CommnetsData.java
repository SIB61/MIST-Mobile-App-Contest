package com.sib.healthcare.activities;

public class CommnetsData {
    String name,email,main,mention,url,timed;
public CommnetsData()
{}

    public CommnetsData(String name, String email, String main, String mention, String url, String timed) {
        this.name = name;
        this.email = email;
        this.main = main;
        this.mention = mention;
        this.url = url;
        this.timed = timed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTimed() {
        return timed;
    }

    public void setTimed(String timed) {
        this.timed = timed;
    }
}
