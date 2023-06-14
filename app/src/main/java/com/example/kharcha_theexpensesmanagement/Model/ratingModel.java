package com.example.kharcha_theexpensesmanagement.Model;

public class ratingModel {
    String ratingEmail;
    Float ratingData;
    String TimeStamp;


    public ratingModel(String ratingEmail,Float ratingData,String TimeStamp) {
        this.ratingEmail = ratingEmail;
        this.ratingData = ratingData;
        this.TimeStamp=TimeStamp;

    }

    public ratingModel() {
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getRatingEmail() {
        return ratingEmail;
    }

    public void setRatingEmail(String ratingEmail) {
        this.ratingEmail = ratingEmail;
    }

    public Float getRatingData() {
        return ratingData;
    }

    public void setRatingData(Float ratingData) {
        this.ratingData = ratingData;
    }
}
