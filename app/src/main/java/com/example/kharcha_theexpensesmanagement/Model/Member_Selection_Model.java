package com.example.kharcha_theexpensesmanagement.Model;

public class Member_Selection_Model {
    String Group_id;
    String Member_id;
    String Expense_id;
    String member_selection_id;
    String Selection;
    String Member_Amount;

    public Member_Selection_Model(String member_selection_id,String group_id, String member_id, String expense_id, String selection, String member_Amount) {
      this.member_selection_id=member_selection_id;
        Group_id = group_id;
        Member_id = member_id;
        Expense_id = expense_id;
        Selection = selection;
        Member_Amount = member_Amount;
    }

    public Member_Selection_Model() {
    }

    public String getMember_selection_id() {
        return member_selection_id;
    }

    public void setMember_selection_id(String member_selection_id) {
        this.member_selection_id = member_selection_id;
    }

    public String getGroup_id() {
        return Group_id;
    }

    public void setGroup_id(String group_id) {
        Group_id = group_id;
    }

    public String getMember_id() {
        return Member_id;
    }

    public void setMember_id(String member_id) {
        Member_id = member_id;
    }

    public String getExpense_id() {
        return Expense_id;
    }

    public void setExpense_id(String expense_id) {
        Expense_id = expense_id;
    }

    public String getSelection() {
        return Selection;
    }

    public void setSelection(String selection) {
        Selection = selection;
    }

    public String getMember_Amount() {
        return Member_Amount;
    }

    public void setMember_Amount(String member_Amount) {
        Member_Amount = member_Amount;
    }
}
