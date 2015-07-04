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

    private int DAY_OVERRIDE = -1;
    private int MONTH_OVERRIDE = -1;
    private int YEAR_OVERRIDE = -1;

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

    public int getDayOverride() {
        return DAY_OVERRIDE;
    }

    public void setDayOverride(int DAY_OVERRIDE) {
        this.DAY_OVERRIDE = DAY_OVERRIDE;
    }

    public int getMonthOverride() {
        return MONTH_OVERRIDE;
    }

    public void setMonthOverride(int MONTH_OVERRIDE) {
        this.MONTH_OVERRIDE = MONTH_OVERRIDE;
    }

    public int getYearOverride() {
        return YEAR_OVERRIDE;
    }

    public void setYearOverride(int YEAR_OVERRIDE) {
        this.YEAR_OVERRIDE = YEAR_OVERRIDE;
    }
}