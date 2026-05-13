package com.donaton.donation.config;

public class AppConfig {

    private static AppConfig instance;

    private String appName;

    private AppConfig() {
        this.appName = "Donaton";
    }

    public static AppConfig getInstance() {

        if(instance == null) {
            instance = new AppConfig();
        }

        return instance;
    }

    public String getAppName() {
        return appName;
    }
}