package com.smartwardrobe.model;
public class Laundry {
    private int laundryId,clothingId;
    private String clothingName,laundryDate,returnDate,status;
    public int getLaundryId() {
        return laundryId;
    }
    public void setLaundryId(int v) {
        laundryId=v;
    }
    public int getClothingId() {
        return clothingId;
    }
    public void setClothingId(int v) {
        clothingId=v;
    }
    public String getClothingName() {
        return clothingName;
    }
    public void setClothingName(String v) {
        clothingName=v;
    }
    public String getLaundryDate() {
        return laundryDate;
    }
    public void setLaundryDate(String v) {
        laundryDate=v;
    }
    public String getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(String v) {
        returnDate=v;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String v) {
        status=v;
    }

}
