package com.tinkoff.tnews.data.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Dmitry Barkalov on 05.05.17.
 */

public class NetworkGetRequest {
    private final String mUrl;

    public NetworkGetRequest(String url) {
        this.mUrl = url;
    }

    public String execute() throws IOException {
        URL url = new URL(mUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream stream = null;
        String result = null;
        try {
            urlConnection.setReadTimeout(3000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            if (urlConnection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + urlConnection.getResponseCode());
            }
            result = readStream(urlConnection.getInputStream());
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    private String readStream(InputStream in) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }
}
