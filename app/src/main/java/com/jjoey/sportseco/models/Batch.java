package com.jjoey.sportseco.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "batches")
public class Batch extends Model{

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("batch_name")
    @Column(name = "batch_name")
    public String batchName;

    @Expose
    @SerializedName("batch_id")
    @Column(name = "batch_id")
    public String batchId;

    public Batch() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
}
