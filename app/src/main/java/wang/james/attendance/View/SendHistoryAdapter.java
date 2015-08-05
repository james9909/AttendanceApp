package wang.james.attendance.View;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import wang.james.attendance.R;

/**
 * Created by james on 8/4/15.
 */
public class SendHistoryAdapter extends RecyclerView.Adapter<SendHistoryAdapter.SendHistoryViewHolder> {

    private ArrayList<SendHistoryItem> mHistory;

    public SendHistoryAdapter(ArrayList<SendHistoryItem> mHistory) {
        this.mHistory = mHistory;
    }

    public void add(SendHistoryItem historyItem) {
        mHistory.add(historyItem);
        notifyItemInserted(mHistory.size() - 1);
    }

    @Override
    public SendHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.send_history_row, parent, false);
        SendHistoryViewHolder sendHistoryViewHolder = new SendHistoryViewHolder(view);
        return sendHistoryViewHolder;
    }

    @Override
    public void onBindViewHolder(SendHistoryViewHolder holder, int position) {
        TextView sendHistoryId = holder.sendHistoryId;
        TextView sendHistoryStatus = holder.sendHistoryStatus;

        sendHistoryId.setText(mHistory.get(position).getId());
        if (mHistory.get(position).getValid()) {
            sendHistoryStatus.setText("Validation successful");
            sendHistoryStatus.setTextColor(Color.GREEN);
        } else {
            sendHistoryStatus.setText("Invalid barcode");
            sendHistoryStatus.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return mHistory.size();
    }

    public class SendHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView sendHistoryId;
        TextView sendHistoryStatus;
        Context context;

        public SendHistoryViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.sendHistoryId = (TextView) itemView.findViewById(R.id.send_history_id);
            this.sendHistoryStatus = (TextView) itemView.findViewById(R.id.send_history_status);
        }
    }
}
