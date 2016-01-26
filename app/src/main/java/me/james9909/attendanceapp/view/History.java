package me.james9909.attendanceapp.view;

public class History {

    String id;
    boolean sent;
    boolean valid;

    public History(String id) {
        this.id = id;
    }

    public History(String id, boolean valid) {
        this(id);
        this.valid = valid;
    }

    public String getId() {
        return this.id;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isSent() {
        return this.sent;
    }

    public boolean isValid() {
        return this.valid;
    }
}
