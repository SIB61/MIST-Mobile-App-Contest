package com.sib.healthcare.models;


import com.google.firebase.Timestamp;

public class QuestionModel {
   private String uId,questionId,name,age,gender,height,weight,question;
   private Timestamp timestamp;

   public QuestionModel() {
   }

   public QuestionModel(String uId, String questionId, String name, String age, String gender, String height, String weight, String question, Timestamp timestamp) {
      this.uId = uId;
      this.questionId = questionId;
      this.name = name;
      this.age = age;
      this.gender = gender;
      this.height = height;
      this.weight = weight;
      this.question = question;
      this.timestamp = timestamp;
   }

   public String getuId() {
      return uId;
   }

   public void setuId(String uId) {
      this.uId = uId;
   }

   public String getQuestionId() {
      return questionId;
   }

   public void setQuestionId(String questionId) {
      this.questionId = questionId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
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

   public String getQuestion() {
      return question;
   }

   public void setQuestion(String question) {
      this.question = question;
   }

   public Timestamp getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(Timestamp date) {
      this.timestamp = timestamp;
   }
}
