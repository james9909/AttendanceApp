package wang.james.attendance;

import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewDate extends Fragment {

    private DatePicker datePicker;
    private TextView selected;
    private Button changeDate;

    private int day, month, year;
    static final int DATE_PICKER_ID = 1111;

    public ViewDate() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_date, container, false);

        selected = (TextView) view.findViewById(R.id.selected_date);
        changeDate = (Button) view.findViewById(R.id.change_date);

        day = Configuration.getInstance().getDay();
        month = Configuration.getInstance().getMonth();
        year = Configuration.getInstance().getYear();

        selected.setText(new StringBuilder().append(month + 1).append("/").append(day).append("/").append(year));

        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        return view;
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();

        Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        date.setArguments(args);

        date.setCallBack(ondate);
        date.show(getActivity().getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            selected.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(year));
        }
    };
}