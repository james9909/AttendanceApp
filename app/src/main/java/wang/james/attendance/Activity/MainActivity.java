package wang.james.attendance.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import wang.james.attendance.View.AttendanceToast;
import wang.james.attendance.Utils.Configuration;
import wang.james.attendance.Fragment.NavigationDrawerFragment;
import wang.james.attendance.R;

public class MainActivity extends ActionBarActivity {

    Button login;
    EditText email, password;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Configuration config = new Configuration();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationDrawerFragment navigationDrawer = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.drawer);
        navigationDrawer.setUp(R.id.not_logged_in, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        if (!isLoggedIn()) {
            showLogin();
        } else {
            hideLogin();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPostData(email.getText().toString(), password.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isLoggedIn() {
        return Configuration.getInstance().getAdminEmail() != "";
    }

    public void showLogin() {
        View navigationDrawerItems = findViewById(R.id.drawer_fragment);
        navigationDrawerItems.setVisibility(View.GONE);

        View not_logged_in = findViewById(R.id.not_logged_in);
        not_logged_in.setVisibility(View.VISIBLE);

        View login_text = findViewById(R.id.login_text);
        login_text.setVisibility(View.VISIBLE);

        email.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);
    }

    public void hideLogin() {
        View navigationDrawerItems = findViewById(R.id.drawer_fragment);
        navigationDrawerItems.setVisibility(View.VISIBLE);

        View not_logged_in = findViewById(R.id.not_logged_in);
        not_logged_in.setVisibility(View.GONE);

        View login_text = findViewById(R.id.login_text);
        login_text.setVisibility(View.GONE);

        email.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        login.setVisibility(View.GONE);
    }

    private void login(String response, String givenEmail, String givenPassword) {
        if (response == null) {
            AttendanceToast.show(getApplicationContext(), "Could not contact server");
        }
        if (response.contains("SUCCESS")) {
            hideLogin();
            AttendanceToast.show(getApplicationContext(), "Validation successful");
            Configuration.getInstance().setAdminEmail(givenEmail);
            Configuration.getInstance().setAdminPassword(givenPassword);
        } else {
            AttendanceToast.show(getApplicationContext(), response);
        }
    }

    private void sendPostData(final String givenEmail, final String givenPassword) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(Configuration.url);

                // Use this to set data
                BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("email", email.getText().toString());
                BasicNameValuePair passwordBasicNameValuePAir = new BasicNameValuePair("pass", password.getText().toString());
                BasicNameValuePair dayBasicNameValuePair = new BasicNameValuePair("day", Configuration.getInstance().getDay() + "");
                BasicNameValuePair monthBasicNameValuePair = new BasicNameValuePair("month", Configuration.getInstance().getMonth() + "");
                BasicNameValuePair yearBasicNameValuePair = new BasicNameValuePair("year", Configuration.getInstance().getYear() + "");

                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(usernameBasicNameValuePair);
                nameValuePairList.add(passwordBasicNameValuePAir);
                nameValuePairList.add(dayBasicNameValuePair);
                nameValuePairList.add(monthBasicNameValuePair);
                nameValuePairList.add(yearBasicNameValuePair);

                try {
                    // UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs.
                    //This is typically useful while sending an HTTP POST request.
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

                    // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
                    httpPost.setEntity(urlEncodedFormEntity);

                    try {
                        // HttpResponse is an interface just like HttpPost.
                        //Therefore we can't initialize them
                        HttpResponse httpResponse = httpClient.execute(httpPost);

                        // According to the JAVA API, InputStream constructor do nothing.
                        //So we can't initialize InputStream although it is not an interface
                        InputStream inputStream = httpResponse.getEntity().getContent();

                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        StringBuilder stringBuilder = new StringBuilder();

                        String bufferedStrChunk;

                        while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                            stringBuilder.append(bufferedStrChunk);
                        }

                        return stringBuilder.toString();

                    } catch (ClientProtocolException cpe) {
                        cpe.printStackTrace();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }

                } catch (UnsupportedEncodingException uee) {
                    uee.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                login(result, givenEmail, givenPassword);
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(givenEmail, givenPassword);

    }
}