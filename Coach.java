package com.gymapp;


public  class Coach{

    private String coach_id;
    private String coach_name;
    private String coach_address;
    private String coach_gender;
    private String coach_phone_num;
    private String coach_status;

    public Coach(){}

    public Coach(String coach_id, String coach_name, String coach_address, String coach_gender, String coach_phone_num, String coach_status) {
        this.coach_id = coach_id;
        this.coach_name = coach_name;
        this.coach_address = coach_address;
        this.coach_gender = coach_gender;
        this.coach_phone_num = coach_phone_num;
        this.coach_status = coach_status;
    }

    public void setCoach_id(String coach_id) {
        this.coach_id = coach_id;
    }

    public void setCoach_name(String coach_name) {
        this.coach_name = coach_name;
    }

    public void setCoach_address(String coach_address) {
        this.coach_address = coach_address;
    }

    public void setCoach_gender(String coach_gender) {
        this.coach_gender = coach_gender;
    }

    public void setCoach_phone_num(String coach_phone_num) {
        this.coach_phone_num = coach_phone_num;
    }

    public void setCoach_status(String coach_status) {
        this.coach_status = coach_status;
    }

    public String getCoach_id() {
        return coach_id;
    }

    public String getCoach_name() {
        return coach_name;
    }

    public String getCoach_address() {
        return coach_address;
    }

    public String getCoach_gender() {
        return coach_gender;
    }

    public String getCoach_phone_num() {
        return coach_phone_num;
    }

    public String getCoach_status() {
        return coach_status;
    }
}
