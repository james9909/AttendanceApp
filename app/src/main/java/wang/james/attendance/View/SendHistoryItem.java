package wang.james.attendance.View;

/**
 * Created by james on 8/4/15.
 */
public class SendHistoryItem {

    private String id;
    private boolean valid;

    public SendHistoryItem(String id, boolean valid) {
        this.id = id;
        this.valid = valid;
    }

    public String getId() {
        return id;
    }

    public boolean getValid() {
        return valid;
    }
}
