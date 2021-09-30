package com.sib.healthcare.Medicine;

public class Med {
    String mname,price,type,disease,ran,name,address,URL,des,qua;
public Med()
{}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getQua() {
        return qua;
    }

    public void setQua(String qua) {
        this.qua = qua;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Med(String mname, String price, String type, String disease, String ran, String name, String address,String URL,String des,String qua) {
        this.mname = mname;
        this.price = price;
        this.type = type;
        this.disease = disease;
        this.ran = ran;
        this.name = name;
        this.address = address;
        this.URL = URL;
        this.des = des;
        this.qua = qua;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getRan() {
        return ran;
    }

    public void setRan(String ran) {
        this.ran = ran;
    }
}
