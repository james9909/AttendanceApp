package wang.james.attendance.Utils;

/**
 * Created by james on 8/4/15.
 */
public class Utils {

    public static boolean isValidId(String id) {
        return id.length() == Configuration.ID_LENGTH;
    }
}
