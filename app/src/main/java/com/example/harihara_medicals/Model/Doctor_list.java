package com.example.harihara_medicals.Model;

public class Doctor_list {
    private String did,doctor_name,doctor_spc,doctor_num1,doctor_num2,doctor_address,doctor_fees,doctor_exprience;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

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

    public String getDoctor_num1() {
        return doctor_num1;
    }

    public void setDoctor_num1(String doctor_num1) {
        this.doctor_num1 = doctor_num1;
    }

    public String getDoctor_num2() {
        return doctor_num2;
    }

    public void setDoctor_num2(String doctor_num2) {
        this.doctor_num2 = doctor_num2;
    }
}
