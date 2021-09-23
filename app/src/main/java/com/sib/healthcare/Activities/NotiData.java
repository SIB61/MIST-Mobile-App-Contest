package com.sib.healthcare.Activities;

public class NotiData {
    String blood,patientName,disease,location,phone,date, url,name,email,district,division,time,name1,gh;
    public NotiData()
    {}

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public NotiData(String blood, String patientName, String disease, String location, String phone, String date, String url, String name, String email, String district, String division, String time,String name1,String gh) {
        this.blood = blood;
        this.patientName = patientName;
        this.disease = disease;
        this.location = location;
        this.phone = phone;
        this.date = date;
        this.url = url;
        this.email = email;
        this.name = name;
        this.district = district;
        this.division = division;
        this.time = time;
        this.name1 = name1;
        this.gh = gh;
    }

    public String getName1() {
        return name1;
    }

    public String getGh() {
        return gh;
    }

    public void setGh(String gh) {
        this.gh = gh;
    }

    public void setName1(String name1) {
        this.name1 = name1;
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

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
