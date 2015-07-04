package wang.james.attendance;

/**
 * Created by james on 7/3/15.
 */
public class Configuration {
    final int ID_LENGTH = 9;
    final String url = "https://stuypulse-attendance.appspot.com/";
    private String ADMIN_EMAIL = "";
    private String ADMIN_PASSWORD = "";

    private static final Configuration config = new Configuration();
    public static Configuration getInstance() {
        return config;
    }

    public void setAdminEmail(String ADMIN_EMAIL) {
        this.ADMIN_EMAIL = ADMIN_EMAIL;
    }

    public void setAdminPassword(String ADMIN_PASSWORD) {
        this.ADMIN_PASSWORD = ADMIN_PASSWORD;
    }

    public String getAdminEmail() {
        return ADMIN_EMAIL;
    }

    public String getAdminPassword() {
        return ADMIN_PASSWORD;
    }
}
