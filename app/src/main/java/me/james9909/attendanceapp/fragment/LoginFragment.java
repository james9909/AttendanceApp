package me.james9909.attendanceapp.fragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import me.james9909.attendanceapp.R;
import me.james9909.attendanceapp.utils.Config;
import me.james9909.attendanceapp.utils.Utils;

public class LoginFragment extends Fragment {

    private EditText emailInput;
    private EditText passwordInput;
    private Button submitButton;

    private String email;
    private String password;
    private String serverResponse;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Allow us to send web requests
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_login, container, false);

        emailInput = (EditText) rootView.findViewById(R.id.login_email_input);
        passwordInput = (EditText) rootView.findViewById(R.id.login_password_input);
        submitButton = (Button) rootView.findViewById(R.id.login_submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();

                Map<String, String> parameters= new HashMap<String, String>();
                parameters.put("email", email);
                parameters.put("pass", password);
                parameters.put("month", Utils.getMonth());
                parameters.put("day", Utils.getDay());
                parameters.put("year", Utils.getYear());

                try {
                    serverResponse = Utils.makeRequest(Config.ATTENDANCE_ADDRESS, "POST", parameters);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Could not contact server", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (serverResponse.contains("SUCCESS")) {
                    Toast.makeText(getContext(), "Validation successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), serverResponse, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}
