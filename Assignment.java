package com.gymapp;

public class Assignment {

    private String assignment_id;
    private String member_id;
    private String coach_id;

    public Assignment() {}

    public Assignment(String assignment_id, String member_id, String coach_id) {
        this.assignment_id = assignment_id;
        this.member_id = member_id;
        this.coach_id = coach_id;
    }

    public String getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(String assignment_id) {
        this.assignment_id = assignment_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getCoach_id() {
        return coach_id;
    }

    public void setCoach_id(String coach_id) {
        this.coach_id = coach_id;
    }
}
