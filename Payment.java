package com.gymapp;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Payment {

    private String payment_id;
    private String payment_name;
    private String payment_start;
    private String payment_end;
    private String payment_status;
    private String payment_totalDue;


    public Payment(){}

    public Payment(String payment_id, String payment_name, String payment_start, String payment_end, String payment_status, String payment_totalDue) {
        this.payment_id = payment_id;
        this.payment_name = payment_name;
        this.payment_start = payment_start;
        this.payment_end = payment_end;
        this.payment_status = payment_status;
        this.payment_totalDue = payment_totalDue;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public String getPayment_start() {
        return payment_start;
    }

    public String getPayment_end() {
        return payment_end;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public String getPayment_totalDue() {
        return payment_totalDue;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    public void setPayment_start(String payment_start) {
        this.payment_start = payment_start;
    }

    public void setPayment_end(String payment_end) {
        this.payment_end = payment_end;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public void setPayment_totalDue(String payment_totalDue) {
        this.payment_totalDue = payment_totalDue;
    }
}
