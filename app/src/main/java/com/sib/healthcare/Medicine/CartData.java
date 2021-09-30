package com.sib.healthcare.Medicine;

public class CartData {
    String mname,price,count,bname,ran,total,url;
public CartData()
{}

    public CartData(String mname, String price, String count, String bname, String ran, String total,String url) {
        this.mname = mname;
        this.price = price;
        this.count = count;
        this.bname = bname;
        this.ran = ran;
        this.total = total;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getRan() {
        return ran;
    }

    public void setRan(String ran) {
        this.ran = ran;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
