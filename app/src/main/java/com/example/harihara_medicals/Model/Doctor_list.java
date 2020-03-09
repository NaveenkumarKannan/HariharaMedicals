package com.example.harihara_medicals.Model;

public class Doctor_list {
    private String doctor_name,doctor_spc,doctor_num,doctor_address,doctor_fees,doctor_exprience;

    public String getDoctor_fees() {
        return doctor_fees;
    }

    public void setDoctor_fees(String doctor_fees) {
        this.doctor_fees = doctor_fees;
    }

    public String getDoctor_exprience() {
        return doctor_exprience;
    }

    public void setDoctor_exprience(String doctor_exprience) {
        this.doctor_exprience = doctor_exprience;
    }

    public String getDoctor_address() {
        return doctor_address;
    }

    public void setDoctor_address(String doctor_address) {
        this.doctor_address = doctor_address;
    }

    public Doctor_list() {
        this.doctor_name = doctor_name;
        this.doctor_spc = doctor_spc;
        this.doctor_num = doctor_num;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_spc() {
        return doctor_spc;
    }

    public void setDoctor_spc(String doctor_spc) {
        this.doctor_spc = doctor_spc;
    }

    public String getDoctor_num() {
        return doctor_num;
    }

    public void setDoctor_num(String doctor_num) {
        this.doctor_num = doctor_num;
    }
}
