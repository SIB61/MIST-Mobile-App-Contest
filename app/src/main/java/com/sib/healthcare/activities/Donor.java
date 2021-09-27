package com.sib.healthcare.activities;

public class Donor {
    String district, division,phone,blood,email,url,name,token;
public Donor()
{

}
    public Donor(String district, String division, String phone, String blood, String email, String url,String name,String token) {
        this.district = district;
        this.division = division;
        this.phone = phone;
        this.blood = blood;
        this.email = email;
        this.url = url;
        this.name = name;
        this.name = name;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
