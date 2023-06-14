package com.example.kharcha_theexpensesmanagement.Model;

public class GroupModel {
    String group_id;
    String user_email;
    String  group_Name;
    String group_img_url;

    public GroupModel(String group_id,String user_email, String group_Name, String group_img_url) {
        this.group_id=group_id;
        this.user_email = user_email;
        this.group_Name = group_Name;
        this.group_img_url = group_img_url;
    }

    public GroupModel() {
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getGroup_Name() {
        return group_Name;
    }

    public void setGroup_Name(String group_Name) {
        this.group_Name = group_Name;
    }

    public String getGroup_img_url() {
        return group_img_url;
    }

    public void setGroup_img_url(String group_img_url) {
        this.group_img_url = group_img_url;
    }
}
