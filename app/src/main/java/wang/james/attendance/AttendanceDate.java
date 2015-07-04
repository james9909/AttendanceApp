package wang.james.attendance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by james on 7/2/15.
 */
public class AttendanceDate {
    private static final AttendanceDate attendanceDate = new AttendanceDate();
    private String day, month, year;
    private Calendar cal = Calendar.getInstance();

    public static AttendanceDate getInstance() {
        return attendanceDate;
    }

    public int getDay() {
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public int getMonth() {
        int month = cal.get(Calendar.MONTH);
        return month;
    }

    public int getYear() {
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

}