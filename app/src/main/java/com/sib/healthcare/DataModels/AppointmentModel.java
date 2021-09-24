package com.sib.healthcare.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

public class AppointmentModel implements Parcelable {

    private String drName,drUid;
    private String pUid,pName,age,gender,height,weight,description,date;

    public AppointmentModel() {

    }

    public AppointmentModel(String drName, String drUid, String uid, String name, String age, String gender, String height, String weight, String description, String date) {
        this.drName = drName;
        this.drUid = drUid;
        this.pUid = uid;
        this.pName = name;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.description = description;
        this.date = date;
    }

    @Override
    public String toString() {
        return "AppointmentModel{" +
                "drName='" + drName + '\'' +
                ", drUid='" + drUid + '\'' +
                ", pUid='" + pUid + '\'' +
                ", pName='" + pName + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    protected AppointmentModel(Parcel in) {
        drName = in.readString();
        drUid = in.readString();
        pUid = in.readString();
        pName = in.readString();
        age = in.readString();
        gender = in.readString();
        height = in.readString();
        weight = in.readString();
        description = in.readString();
        date = in.readString();
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
        dest.writeString(pUid);
        dest.writeString(pName);
        dest.writeString(age);
        dest.writeString(gender);
        dest.writeString(height);
        dest.writeString(weight);
        dest.writeString(description);
        dest.writeString(date);
    }
}
