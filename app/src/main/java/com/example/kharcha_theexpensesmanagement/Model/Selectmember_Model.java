package com.example.kharcha_theexpensesmanagement.Model;

import java.util.List;

public class Selectmember_Model {
    private String name;
    private List<String> checkedItems;

    public Selectmember_Model(String name, List<String> checkedItems) {
        this.name = name;
        this.checkedItems = checkedItems;
    }

    public Selectmember_Model() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  List<String> getCheckedItems() {
        return checkedItems;
    }

    public void setCheckedItems(List<String> checkedItems) {
        this.checkedItems = checkedItems;
    }
}
