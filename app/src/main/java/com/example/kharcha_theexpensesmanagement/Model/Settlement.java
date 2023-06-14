package com.example.kharcha_theexpensesmanagement.Model;

import java.util.HashMap;
import java.util.List;

public class Settlement {
    String settleid;
    HashMap<String, Double> paidFor;
    List<String> member_name;
    String Total_Amt;
    String payee_id;
    String payee_name;
    String group_id;
    String expense_id;

    public Settlement(String settleid, HashMap<String, Double> paidFor, List<String> member_name, String payee_id, String payee_name, String group_id, String expense_id, String total_Amt) {
        this.settleid = settleid;
        this.paidFor = paidFor;
        this.member_name = member_name;
        Total_Amt = total_Amt;
        this.payee_id = payee_id;
        this.payee_name = payee_name;
        this.group_id = group_id;
        this.expense_id = expense_id;

    }

    public Settlement() {
    }

    public String getSettleid() {
        return settleid;
    }

    public void setSettleid(String settleid) {
        this.settleid = settleid;
    }

    public HashMap<String, Double> getPaidFor() {
        return paidFor;
    }

    public void setPaidFor(HashMap<String, Double> paidFor) {
        this.paidFor = paidFor;
    }

    public List<String> getMember_name() {
        return member_name;
    }

    public void setMember_name(List<String> member_name) {
        this.member_name = member_name;
    }

    public String getTotal_Amt() {
        return Total_Amt;
    }

    public void setTotal_Amt(String total_Amt) {
        Total_Amt = total_Amt;
    }

    public String getPayee_id() {
        return payee_id;
    }

    public void setPayee_id(String payee_id) {
        this.payee_id = payee_id;
    }

    public String getPayee_name() {
        return payee_name;
    }

    public void setPayee_name(String payee_name) {
        this.payee_name = payee_name;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(String expense_id) {
        this.expense_id = expense_id;
    }

}
