package com.bianlaoshi.new1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by frank on 2017/11/28.
 */

public class sendMessageThread extends Thread {
    private String url;
    public sendMessageThread(String url)
    {
        this.url=url;
    }
    private void doGet() throws IOException {
        URL httpUrl=new URL(this.url);
        HttpURLConnection conn= (HttpURLConnection) httpUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(5000);
        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
        String str;
        final StringBuffer sb=new StringBuffer();
        while((str=br.readLine())!=null)
        {
            sb.append(str);
        }
    }

    public void run()
    {
        try {
            doGet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
