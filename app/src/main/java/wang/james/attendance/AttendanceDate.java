package wang.james.attendance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by james on 7/2/15.
 */
public class AttendanceDate {
    String day, month, year;
    Date date = new Date();
    Calendar cal = Calendar.getInstance();


    public String getDay() {
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day + "";
    }

    public String getMonth() {
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month + "";
    }

    public String getYear() {
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year + "";
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