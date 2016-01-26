package me.james9909.attendanceapp.utils;

public class Config {

    public static Config config;
    public static final String ATTENDANCE_ADDRESS = "https://stuypulse-attendance.appspot.com/";
    public String adminEmail = "";
    public String adminPassword = "";

    public static Config getInstance() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminEmail() {
        return this.adminEmail;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminPassword() {
        return this.adminPassword;
    }
}
