package wang.james.attendance.Utils;

import java.util.Calendar;

/**
 * Created by james on 7/3/15.
 */
public class Configuration {
    private static final Configuration config = new Configuration();
    public final static int ID_LENGTH = 9;
    public final static String url = "https://stuypulse-attendance.appspot.com/";
    private String ADMIN_EMAIL = "";
    private String ADMIN_PASSWORD = "";

    private Calendar calendar = Calendar.getInstance();
    private int DAY = calendar.get(Calendar.DAY_OF_MONTH);
    private int MONTH = calendar.get(Calendar.MONTH);
    private int YEAR = calendar.get(Calendar.YEAR);

    public static Configuration getInstance() {
        return config;
    }

    public String getAdminEmail() {
        return ADMIN_EMAIL;
    }

    public void setAdminEmail(String ADMIN_EMAIL) {
        this.ADMIN_EMAIL = ADMIN_EMAIL;
    }

    public String getAdminPassword() {
        return ADMIN_PASSWORD;
    }

    public void setAdminPassword(String ADMIN_PASSWORD) {
        this.ADMIN_PASSWORD = ADMIN_PASSWORD;
    }

    public int getDay() {
        return DAY;
    }

    public void setDay(int DAY) {
        this.DAY  = DAY;
    }

    public int getMonth() {
        return MONTH;
    }

    public void setMonth(int MONTH) {
        this.MONTH = MONTH;
    }

    public int getYear() {
        return YEAR;
    }

    public void setYear(int YEAR) {
        this.YEAR = YEAR;
    }
}