package com.example.assignment3;

public class RecycleBinItem {

    private int id;
    private int userId;
    private String siteName;
    private String siteUrl;
    private String login;
    private String sitePassword;
    private String deletedTime;

    public RecycleBinItem(int id, int userId, String siteName, String siteUrl, String login, String sitePassword, String deletedTime) {
        this.id = id;
        this.userId = userId;
        this.siteName = siteName;
        this.siteUrl = siteUrl;
        this.login = login;
        this.sitePassword = sitePassword;
        this.deletedTime = deletedTime;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public String getLogin() {
        return login;
    }

    public String getSitePassword() {
        return sitePassword;
    }

    public String getDeletedTime() {
        return deletedTime;
    }
}