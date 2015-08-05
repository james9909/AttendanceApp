package wang.james.attendance.View;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by james on 8/4/15.
 */
public class SendHistoryItem implements Parcelable {

    private String id;
    private boolean valid;

    public SendHistoryItem(String id, boolean valid) {
        this.id = id;
        this.valid = valid;
    }

    protected SendHistoryItem(Parcel in) {
        id = in.readString();
        valid = (boolean) in.readValue(null);
    }

    public static final Creator<SendHistoryItem> CREATOR = new Creator<SendHistoryItem>() {
        @Override
        public SendHistoryItem createFromParcel(Parcel in) {
            return new SendHistoryItem(in);
        }

        @Override
        public SendHistoryItem[] newArray(int size) {
            return new SendHistoryItem[size];
        }
    };

    public String getId() {
        return id;
    }

    public boolean getValid() {
        return valid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeValue(valid);
    }
}
