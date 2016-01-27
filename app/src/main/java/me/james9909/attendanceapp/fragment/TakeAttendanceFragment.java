package me.james9909.attendanceapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.james9909.attendanceapp.R;
import me.james9909.attendanceapp.utils.Config;
import me.james9909.attendanceapp.utils.Utils;
import me.james9909.attendanceapp.view.History;
import me.james9909.attendanceapp.view.SendHistoryAdapter;

public class TakeAttendanceFragment extends Fragment {

    private RecyclerView recyclerView;
    private SendHistoryAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<History> history;
    private Button submit;
    private EditText id;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType currentLayoutManagerType;

    public TakeAttendanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_take_attendance, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.id_send_history);
        layoutManager = new LinearLayoutManager(getActivity());

        currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        setRecyclerViewLayoutManager(currentLayoutManagerType);

        history = new ArrayList<>();
        adapter = new SendHistoryAdapter(history);

        recyclerView.setAdapter(adapter);

        submit = (Button) rootView.findViewById(R.id.submit_id);
        id = (EditText) rootView.findViewById(R.id.id_input);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendId();
           }
        });

        // On enter
        id.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction()) == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    sendId();
                    return true;
                }
                return false;
            }
        });

        return rootView;
    }


    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                layoutManager = new GridLayoutManager(getActivity(), 2);
                currentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                layoutManager = new LinearLayoutManager(getActivity());
                currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                layoutManager = new LinearLayoutManager(getActivity());
                currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(scrollPosition);
    }

    public void sendId() {
        String studentId = id.getText().toString();
        if (studentId.equals("")) {
            Toast.makeText(getActivity(), "Please input something", Toast.LENGTH_SHORT).show();
            return;
        }

        History historyItem;
        if (studentId.matches("[0-9]{9}")) {
            historyItem = new History(studentId, true);
            Map<String, String> parameters = new HashMap<>();
            parameters.put("email", Config.getInstance().getAdminEmail());
            parameters.put("pass", Config.getInstance().getAdminPassword());
            parameters.put("day", Utils.getDay());
            parameters.put("month", Utils.getMonth());
            parameters.put("year", Utils.getYear());
            parameters.put("id", studentId);

            try {
                String response = Utils.makeRequest(Config.ATTENDANCE_ADDRESS, "POST", parameters);
                if (response.contains("ERROR")) {
                    historyItem.setSent(false);
                } else {
                    historyItem.setSent(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                historyItem.setSent(false);
            }
        } else {
            historyItem = new History(studentId, false);
            historyItem.setSent(false);
        }
        adapter.add(historyItem);
        recyclerView.smoothScrollToPosition(history.size() - 1);
    }
}
