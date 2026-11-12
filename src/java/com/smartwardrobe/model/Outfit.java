package com.smartwardrobe.model;
public class Outfit {
    private int outfitId,userId;
    private String outfitName,occasion,season,createdAt;
    public int getOutfitId() {
        return outfitId;
    }
    public void setOutfitId(int v) {
        outfitId=v;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int v) {
        userId=v;
    }
    public String getOutfitName() {
        return outfitName;
    }
    public void setOutfitName(String v) {
        outfitName=v;
    }
    public String getOccasion() {
        return occasion;
    }
    public void setOccasion(String v) {
        occasion=v;
    }
    public String getSeason() {
        return season;
    }
    public void setSeason(String v) {
        season=v;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String v) {
        createdAt=v;
    }

}
