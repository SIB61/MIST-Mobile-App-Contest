package com.sib.healthcare.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AppointmentModel implements Parcelable  {

    private String drName,drUid,type,clinicAddress;
    private String pUid,pName,age,gender,height,weight,description, date , serialNo , approximateTime;

    public AppointmentModel() {

    }

    protected AppointmentModel(Parcel in) {
        drName = in.readString();
        drUid = in.readString();
        type = in.readString();
        clinicAddress = in.readString();
        pUid = in.readString();
        pName = in.readString();
        age = in.readString();
        gender = in.readString();
        height = in.readString();
        weight = in.readString();
        description = in.readString();
        date = in.readString();
        serialNo = in.readString();
        approximateTime = in.readString();
    }

    public static final Creator<AppointmentModel> CREATOR = new Creator<AppointmentModel>() {
        @Override
        public AppointmentModel createFromParcel(Parcel in) {
            return new AppointmentModel(in);
        }

        @Override
        public AppointmentModel[] newArray(int size) {
            return new AppointmentModel[size];
        }
    };

    public String getApproximateTime() {
        return approximateTime;
    }

    public void setApproximateTime(String approximateTime) {
        this.approximateTime = approximateTime;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AppointmentModel(String drName, String drUid, String type, String clinicAddress, String pUid, String pName, String age, String gender, String height, String weight, String description, String date, String serialNo, String approximateTime) {
        this.drName = drName;
        this.drUid = drUid;
        this.type = type;
        this.clinicAddress = clinicAddress;
        this.pUid = pUid;
        this.pName = pName;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.description = description;
        this.date = date;
        this.serialNo = serialNo;
        this.approximateTime = approximateTime;
    }


    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrUid() {
        return drUid;
    }

    public void setDrUid(String drUid) {
        this.drUid = drUid;
    }


    public String getpUid() {
        return pUid;
    }

    public void setpUid(String pUid) {
        this.pUid = pUid;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(drName);
        dest.writeString(drUid);
        dest.writeString(type);
        dest.writeString(clinicAddress);
        dest.writeString(pUid);
        dest.writeString(pName);
        dest.writeString(age);
        dest.writeString(gender);
        dest.writeString(height);
        dest.writeString(weight);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(serialNo);
        dest.writeString(approximateTime);
    }
}
