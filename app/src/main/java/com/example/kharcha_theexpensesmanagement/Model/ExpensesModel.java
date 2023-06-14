package com.example.kharcha_theexpensesmanagement.Model;

public class ExpensesModel {
    String group_id;
    String Member_id;
    String Expenses_id;
    String Expenses_Name;
    String Who_paid;
    String Amount_expense;
    String TimeStamp;


    public ExpensesModel(String group_id,String member_id, String expenses_id, String expenses_Name, String who_paid, String amount_expense, String timeStamp) {
        this.group_id = group_id;
        Member_id = member_id;
        Expenses_id = expenses_id;
        Expenses_Name = expenses_Name;
        Who_paid = who_paid;
        Amount_expense = amount_expense;
        TimeStamp = timeStamp;
    }

    public ExpensesModel(){}

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getMember_id() {
        return Member_id;
    }

    public void setMember_id(String member_id) {
        Member_id = member_id;
    }

    public String getExpenses_id() {
        return Expenses_id;
    }

    public void setExpenses_id(String expenses_id) {
        Expenses_id = expenses_id;
    }

    public String getExpenses_Name() {
        return Expenses_Name;
    }

    public void setExpenses_Name(String expenses_Name) {
        Expenses_Name = expenses_Name;
    }

    public String getWho_paid() {
        return Who_paid;
    }

    public void setWho_paid(String who_paid) {
        Who_paid = who_paid;
    }

    public String getAmount_expense() {
        return Amount_expense;
    }

    public void setAmount_expense(String amount_expense) {
        Amount_expense = amount_expense;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }
}
