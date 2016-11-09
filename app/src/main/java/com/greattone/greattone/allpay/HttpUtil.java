package com.greattone.greattone.allpay;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import android.content.Context;
import android.util.Log;


class HttpUtil {
    private static final String TAG = Utility.LOGTAG;
    private static int TIMEOUT_ReadTimeout = 15000;
    private static int TIMEOUT_ConnectTimeout = 15000;

    public static String post(String url, Map<String, String> params, Context cxt) throws Exception {
        String urlParameters = "";
        if (params != null) {
                Log.d(TAG, "post data : \n");
                for (String key : params.keySet()) {
                    Log.d(TAG, key + " = " + params.get(key) + "\n");
                    urlParameters += (key + "=" + URLEncoder.encode(params.get(key), "UTF-8") + "&");
                }

        }

        return doPost(url, urlParameters);
    }

    public static String doPost(String url, String param) throws Exception {
        Log.d(TAG, "doPost -> url : " + url);
        Log.d(TAG, "doPost -> param : " + param);

        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer(0);
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_ReadTimeout);
            conn.setConnectTimeout(TIMEOUT_ConnectTimeout);

            if (param != null && !param.trim().equals("")) {
                out = new PrintWriter(conn.getOutputStream());
                out.print(param);
                out.flush();
            }

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append("\r\n");
                result.append(line);
            }
        } catch (Exception e) {
            if(e.getMessage() != null)
                Log.d(TAG, "doPost -> Error: " + e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                if(e.getMessage() != null)
                    Log.d(TAG, "doPost -> out.close() or in.close() Error: " + e.getMessage());
            }
        }

        Log.d(TAG, "doPost -> result: " + result.toString());
        return result.toString();
    }


}
