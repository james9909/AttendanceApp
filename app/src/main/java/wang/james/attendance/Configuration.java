package wang.james.attendance;

/**
 * Created by james on 7/3/15.
 */
public class Configuration {
    private static final Configuration config = new Configuration();
    final int ID_LENGTH = 9;
    final String url = "https://stuypulse-attendance.appspot.com/";
    private String ADMIN_EMAIL = "";
    private String ADMIN_PASSWORD = "";
    private int DAY = AttendanceDate.getInstance().getDay();
    private int MONTH = AttendanceDate.getInstance().getMonth();
    private int YEAR = AttendanceDate.getInstance().getYear();

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