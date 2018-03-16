package com.bianlaoshi.new1;


import android.os.Handler;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by frank on 2017/11/27.
 */

public class getTeacherNameThread extends Thread {
    private Handler handler;
    private String url;
    private TextView tname;

    public getTeacherNameThread(Handler handler,String url,TextView tname)
    {
        this.handler=handler;
        this.url=url;
        this.tname=tname;
    }

    private void doGet() throws IOException {
        URL httpUrl=new URL(this.url);
        HttpURLConnection conn= (HttpURLConnection) httpUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(5000);
        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String str;
        final StringBuffer sb=new StringBuffer();
        while((str=br.readLine())!=null)
        {
            sb.append(str);
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                tname.setText(sb.toString());
            }
        });
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
