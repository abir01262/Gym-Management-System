package com.gymapp;

public class Member {

    private String member_id;
    private String member_name;
    private String member_address;
    private String member_gender;
    private String member_phone_num;
    private String member_schedule;
    private String member_start;
    private String member_end;
    private String member_status;
    private String member_coach;

    public Member(){}

    public Member(String member_id, String member_name, String member_address, String member_gender, String member_phone_num, String member_schedule, String member_start, String member_end, String member_status) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_address = member_address;
        this.member_gender = member_gender;
        this.member_phone_num = member_phone_num;
        this.member_schedule = member_schedule;
        this.member_start = member_start;
        this.member_end = member_end;
        this.member_status = member_status;
        this.member_coach ="";
    }

    public String getMember_coach() {
        return member_coach;
    }

    public void setMember_coach(String member_coach) {
        this.member_coach = member_coach;
    }

    public String getMember_id() {
        return member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public String getMember_address() {
        return member_address;
    }

    public String getMember_gender() {
        return member_gender;
    }

    public String getMember_phone_num() {
        return member_phone_num;
    }

    public String getMember_schedule() {
        return member_schedule;
    }

    public String getMember_start() {
        return member_start;
    }

    public String getMember_end() {
        return member_end;
    }

    public String getMember_status() {
        return member_status;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public void setMember_address(String member_address) {
        this.member_address = member_address;
    }

    public void setMember_gender(String member_gender) {
        this.member_gender = member_gender;
    }

    public void setMember_phone_num(String member_phone_num) {
        this.member_phone_num = member_phone_num;
    }

    public void setMember_schedule(String member_schedule) {
        this.member_schedule = member_schedule;
    }

    public void setMember_start(String member_start) {
        this.member_start = member_start;
    }

    public void setMember_end(String member_end) {
        this.member_end = member_end;
    }

    public void setMember_status(String member_status) {
        this.member_status = member_status;
    }

}
