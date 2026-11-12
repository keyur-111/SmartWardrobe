package com.smartwardrobe.model;
public class Clothing {
    private int clothingId,userId,categoryId,usageCount;
    private String name,color,fabric,season,occasion,image,status,categoryName;
    public int getClothingId() {
        return clothingId;
    }
    public void setClothingId(int v) {
        clothingId=v;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int v) {
        userId=v;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int v) {
        categoryId=v;
    }
    public int getUsageCount() {
        return usageCount;
    }
    public void setUsageCount(int v) {
        usageCount=v;
    }
    public String getName() {
        return name;
    }
    public void setName(String v) {
        name=v;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String v) {
        color=v;
    }
    public String getFabric() {
        return fabric;
    }
    public void setFabric(String v) {
        fabric=v;
    }
    public String getSeason() {
        return season;
    }
    public void setSeason(String v) {
        season=v;
    }
    public String getOccasion() {
        return occasion;
    }
    public void setOccasion(String v) {
        occasion=v;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String v) {
        image=v;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String v) {
        status=v;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String v) {
        categoryName=v;
    }

}
