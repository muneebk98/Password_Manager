package com.example.assignment3;

public class Password {
    private int userId;
    private String siteName;
    private String siteUrl;
    private String login;
    private String sitePassword;

    public Password(int userId, String siteName, String siteUrl, String login, String sitePassword) {
        this.userId = userId;
        this.siteName = siteName;
        this.siteUrl = siteUrl;
        this.login = login;
        this.sitePassword = sitePassword;
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
}