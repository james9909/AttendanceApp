package me.james9909.attendanceapp.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Map;

public class Utils {

    private static Calendar calendar = Calendar.getInstance();

    public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36";

    public static String getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH) + "";
    }

    public static String getMonth() {
        // Correct month offset where January = 0
        return calendar.get(Calendar.MONTH) + 1 + "";
    }

    public static String getYear() {
        return calendar.get(Calendar.YEAR) + "";
    }

    public static String urlEncode(String s) throws UnsupportedEncodingException {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedEncodingException(e.toString());
        }
    }

    public static String parameterize(Map<String, String> parameters) throws UnsupportedEncodingException {
        if (parameters.size() == 0) {
            return "";
        }

        String params = "";
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            params += String.format("%s=%s",
                    urlEncode(parameter.getKey().toString()),
                    urlEncode(parameter.getValue().toString()));
            params += "&";
        }
        return params.substring(0, params.length()-1);
    }

    public static String makeRequest(String url, String method, Map<String, String> parameters) throws IOException {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        if (parameters != null) {
            conn.getOutputStream().write(parameterize(parameters).toString().getBytes("UTF-8"));
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String response = "";
        String input;
        while ((input = br.readLine()) != null) {
            response += input;
        }
        return response;
    }
}