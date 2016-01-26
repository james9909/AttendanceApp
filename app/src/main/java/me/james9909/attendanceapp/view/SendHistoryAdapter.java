package me.james9909.attendanceapp.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import me.james9909.attendanceapp.R;

public class SendHistoryAdapter extends RecyclerView.Adapter<SendHistoryAdapter.SendHistoryViewHolder> {
    private ArrayList<History> mHistory;

    public SendHistoryAdapter(ArrayList<History> mHistory) {
        this.mHistory = mHistory;
    }

    public void add(History historyItem) {
        mHistory.add(historyItem);
        notifyItemInserted(mHistory.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public SendHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        SendHistoryViewHolder sendHistoryViewHolder = new SendHistoryViewHolder(view);
        return sendHistoryViewHolder;
    }

    @Override
    public void onBindViewHolder(SendHistoryViewHolder holder, int position) {
        TextView studentId = holder.studentId;
        TextView statusMessage = holder.statusMessage;

        studentId.setText(mHistory.get(position).getId());
        if (mHistory.get(position).isSent() && mHistory.get(position).isValid()) {
            statusMessage.setText("Sent");
            statusMessage.setTextColor(Color.GREEN);
        } else if (!mHistory.get(position).isSent()) {
            statusMessage.setText("Failed to send to server");
            statusMessage.setTextColor(Color.RED);
        } else {
            statusMessage.setText("Invalid barcode");
            statusMessage.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return mHistory.size();
    }

    public class SendHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView studentId;
        TextView statusMessage;
        Context context;

        public SendHistoryViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.studentId = (TextView) itemView.findViewById(R.id.student_id);
            this.statusMessage = (TextView) itemView.findViewById(R.id.status_message);
        }
    }
}
