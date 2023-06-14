package com.example.kharcha_theexpensesmanagement.Model;

public class feedbackModel {
    String feedbackEmail;
    String feedbackDescription;
    String TimeStamp;

    public feedbackModel(String feedbackEmail, String feedbackDescription, String TimeStamp) {
        this.feedbackEmail = feedbackEmail;
        this.feedbackDescription = feedbackDescription;
        this.TimeStamp=TimeStamp;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getFeedbackEmail() {
        return feedbackEmail;
    }

    public void setFeedbackEmail(String feedbackEmail) {
        this.feedbackEmail = feedbackEmail;
    }

    public String getFeedbackDescription() {
        return feedbackDescription;
    }

    public void setFeedbackDescription(String feedbackDescription) {
        this.feedbackDescription = feedbackDescription;
    }
}
