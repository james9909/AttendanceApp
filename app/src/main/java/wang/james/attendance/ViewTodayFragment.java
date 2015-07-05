package wang.james.attendance;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wang.james.attendance.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewTodayFragment extends Fragment {

    public ViewTodayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_today, container, false);
    }

}
