//package com.example.mighty.airtelapp;
//
//import android.arch.persistence.room.ColumnInfo;
//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;
//
//@Entity
//public class User {
//
//    public User(String recipientNumber, String dataBundleName, String dataBundleValue, String dataBundleCost, String spinnerRow) {
//        this.recipientNumber = recipientNumber;
//        this.dataBundleName = dataBundleName;
//        this.dataBundleValue = dataBundleValue;
//        this.dataBundleCost = dataBundleCost;
//        this.spinnerRow = spinnerRow;
//    }
//
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//
//    @ColumnInfo(name = "recipient_number")
//    private String recipientNumber;
//
//    @ColumnInfo(name = "data_bundle_name")
//    private String dataBundleName;
//
//    @ColumnInfo(name = "data_bundle_value")
//    private String dataBundleValue;
//
//    @ColumnInfo(name = "data_bundle_cost")
//    private String dataBundleCost;
//
//    @ColumnInfo(name = "spinner_row")
//    private String spinnerRow;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getRecipientNumber() {
//        return recipientNumber;
//    }
//
//    public void setRecipientNumber(String recipientNumber) {
//        this.recipientNumber = recipientNumber;
//    }
//
//    public String getDataBundleName() {
//        return dataBundleName;
//    }
//
//    public void setDataBundleName(String dataBundleName) {
//        this.dataBundleName = dataBundleName;
//    }
//
//    public String getDataBundleValue() {
//        return dataBundleValue;
//    }
//
//    public void setDataBundleValue(String dataBundleValue) {
//        this.dataBundleValue = dataBundleValue;
//    }
//
//    public String getDataBundleCost() {
//        return dataBundleCost;
//    }
//
//    public void setDataBundleCost(String dataBundleCost) {
//        this.dataBundleCost = dataBundleCost;
//    }
//
//    public String getSpinnerRow() {
//        return spinnerRow;
//    }
//
//    public void setSpinnerRow(String spinnerRow) {
//        this.spinnerRow = spinnerRow;
//    }
//}
