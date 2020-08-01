package com.example.harihara_medicals.Model;

import com.google.gson.annotations.SerializedName;

public class MRecords {

    @SerializedName("mfile")
    private String mfile;
    @SerializedName("mrecid")
    private String mrecid;
    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;

    public String getMfile() {
        return mfile;
    }

    public void setMfile(String mfile) {
        this.mfile = mfile;
    }

    public String getMrecid() {
        return mrecid;
    }

    public void setMrecid(String mrecid) {
        this.mrecid = mrecid;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
