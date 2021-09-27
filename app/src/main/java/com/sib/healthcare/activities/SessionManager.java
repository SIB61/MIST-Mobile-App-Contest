package com.sib.healthcare.activities;

import android.content.Context;
import android.content.SharedPreferences;

import com.sib.healthcare.models.UserDataModel;

import java.util.HashMap;

public class SessionManager {
    private UserDataModel userDataModel;
    SharedPreferences sh;
    SharedPreferences.Editor ed;
    Context c;
    public static final String USERSESSION = "userLogIn";
    public static final String REMEMBERME = "userRemember";


    public static final String ISLOGGED = "ISLOGGEDIN";

    public static final String FULLNAME = "FULLNAME";
    public static final String EMAIL = "EMAIL";
    public static final String PHONE = "PHONE";
    public static final String PASS = "PASS";
    public static final String URL = "URL";
    public static final String DONOR = "DONOR";
    public static final String TOKEN = "TOKEN";
    public static final String DIVISION = "DIVISION";
    public static final String DISTRICT = "DISTRICT";


    public static final String ISREMEMBERME = "REMEMBERME";
    public static final String REMEMBERMEPHONE = "PHONE";
    public static final String REMEMBERMEPASS = "PASS";


    public SessionManager(Context c, String session) {
        this.c = c;
        sh = c.getSharedPreferences(session, Context.MODE_PRIVATE);
        ed = sh.edit();
    }



    public void loginSession(String fullname, String email, String phone, String pass,String url,String donor,String token,String division,String district) {

        ed.putBoolean(ISLOGGED, true);
        ed.putString(FULLNAME, fullname);
        ed.putString(EMAIL, email);
        ed.putString(PHONE, phone);
        ed.putString(PASS, pass);
        ed.putString(URL, url);
        ed.putString(DONOR, donor);
        ed.putString(TOKEN, token);
        ed.putString(DIVISION, division);
        ed.putString(DISTRICT, district);
        ed.commit();
    }

    public HashMap<String, String> returnData() {
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put(FULLNAME, sh.getString(FULLNAME, null));
        hm.put(EMAIL, sh.getString(EMAIL, null));
        hm.put(PHONE, sh.getString(PHONE, null));
        hm.put(PASS, sh.getString(PASS, null));
        hm.put(URL, sh.getString(URL, null));
        hm.put(DONOR, sh.getString(DONOR, null));
        hm.put(TOKEN, sh.getString(TOKEN, null));
        hm.put(DISTRICT, sh.getString(DISTRICT, null));
        hm.put(DIVISION, sh.getString(DIVISION, null));

        return hm;
    }

    public boolean checkLogin() {
        if (sh.getBoolean(ISLOGGED, true)) {
            return true;
        } else
            return false;
    }

    public void logOut() {
        ed.clear();
        ed.commit();
    }

    public void rememberMeSession(String phone, String pass) {

        ed.putBoolean(ISREMEMBERME, true);


        ed.putString(REMEMBERMEPHONE, phone);
        ed.putString(REMEMBERMEPASS, pass);

        ed.commit();
    }

    public HashMap<String, String> returnDataRememberMe() {
        HashMap<String, String> hm = new HashMap<String, String>();

        hm.put(REMEMBERMEPHONE, sh.getString(REMEMBERMEPHONE, null));

        hm.put(REMEMBERMEPASS, sh.getString(REMEMBERMEPASS, null));

        return hm;
    }

    public boolean checkRememberMe() {
        if (sh.getBoolean(ISREMEMBERME, true)) {
            return true;
        } else
            return false;
    }


}
