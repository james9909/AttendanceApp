package wang.james.attendance;

import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

public class TakeAttendanceActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private EditText getId;
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        getId = (EditText) findViewById(R.id.get_id);
        send = (Button) findViewById(R.id.send_attendance);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getId.getText().toString();
                if (isValid(id)) {
                    send(id);
                } else {
                    AttendanceToast.show(getApplicationContext(), "Invalid barcode");
                }
            }
        });
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_take_attendance, menu);
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

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showResponse(String response) {
        if (response == null) {
            AttendanceToast.show(getApplicationContext(), "Could not contact server");
        } else if (response.contains("SUCCESS")) {
            AttendanceToast.show(getApplicationContext(), "Success!");
            getId.getText().clear();
        } else {
            AttendanceToast.show(getApplicationContext(), response);
        }
    }

    private boolean isValid(String id) {
        return id.length() == Configuration.ID_LENGTH;
    }

    private void send(final String id) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(Configuration.url);

                // Use this to set data
                BasicNameValuePair idBasicNameValuePair = new BasicNameValuePair("id", id);
                BasicNameValuePair emailBasicNameValuePair = new BasicNameValuePair("email", Configuration.getInstance().getAdminEmail());
                BasicNameValuePair passwordBasicNameValuePair = new BasicNameValuePair("pass", Configuration.getInstance().getAdminPassword());
                BasicNameValuePair dayBasicNameValuePair = new BasicNameValuePair("day", Configuration.getInstance().getDay() + "");
                BasicNameValuePair monthBasicNameValuePair = new BasicNameValuePair("month", Configuration.getInstance().getMonth() + "");
                BasicNameValuePair yearBasicNameValuePair = new BasicNameValuePair("year", Configuration.getInstance().getYear() + "");

                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(idBasicNameValuePair);
                nameValuePairList.add(emailBasicNameValuePair);
                nameValuePairList.add(passwordBasicNameValuePair);
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
                showResponse(result);
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(id);

    }

}