package com.example.kharcha_theexpensesmanagement.Model;


import android.os.Parcel;
import android.os.Parcelable;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;

public class MemberModel implements Parcelable {

    String group_Name;
    String group_id;
    String memberMno;
    String memberName;
    String Member_id;
    HashMap<String,Double> owns;

    public MemberModel(){}

    public MemberModel(String group_Name, String group_id, String memberMno, String memberName, String member_id, HashMap<String, Double> owns) {
        this.group_Name = group_Name;
        this.group_id = group_id;
        this.memberMno = memberMno;
        this.memberName = memberName;
        Member_id = member_id;
        this.owns = owns;
    }

    protected MemberModel(Parcel in) {
        group_Name = in.readString();
        group_id = in.readString();
        memberMno = in.readString();
        memberName = in.readString();
        Member_id = in.readString();
    }

    public static final Creator<MemberModel> CREATOR = new Creator<MemberModel>() {
        @Override
        public MemberModel createFromParcel(Parcel in) {
            return new MemberModel(in);
        }

        @Override
        public MemberModel[] newArray(int size) {
            return new MemberModel[size];
        }
    };

    public String getMember_id() {
        return Member_id;
    }

    public void setMember_id(String member_id) {
        Member_id = member_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_Name() {
        return group_Name;
    }

    public void setGroup_Name(String group_Name) {
        this.group_Name = group_Name;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public HashMap<String, Double> getOwns() {
        return owns;
    }

    public void setOwns(HashMap<String, Double> owns) {
        this.owns = owns;
    }

    public String getMemberMno() {
        return memberMno;
    }

    public void setMemberMno(String memberMno) {
        this.memberMno = memberMno;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(group_Name);
        parcel.writeString(
                group_id
        );
        parcel.writeString(memberMno);
        parcel.writeString(memberName);
        parcel.writeString(Member_id);

    }
}