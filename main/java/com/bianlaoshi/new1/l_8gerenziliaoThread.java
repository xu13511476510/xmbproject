package com.bianlaoshi.new1;

import android.content.Context;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by frank on 2017/11/19.
 */

public class l_8gerenziliaoThread extends Thread {
    private String url;
    private Context context;
    private Handler handler;
    private teacher t;

    private EditText tname;
    private TextView tid;
    private TextView tpoint;
    private EditText tmail;
    private TextView tschool;
    private EditText tintroduction;

    public l_8gerenziliaoThread(String url, Context context, Handler handler,EditText tname, TextView tid, TextView tpoint,EditText tmail, TextView tschool, EditText tintroduction)
    {
        this.url=url;
        this.handler=handler;
        this.context=context;
        this.tname=tname;
        this.tid=tid;
        this.tpoint=tpoint;
        this.tmail=tmail;
        this.tschool=tschool;
        this.tintroduction=tintroduction;
    }

    public void doGet() throws IOException, JSONException {
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
        t=parseJson(sb.toString());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //修改acitivity
                tname.setText(t.getTname());
                tid.setText(t.getTid());
                tpoint.setText(""+t.getTpoint());//注意类型转换
                tmail.setText(t.getTmail());
                tschool.setText(t.getTschool());
                tintroduction.setText(t.getTintroduction());
            }
        });
    }
    //解析函数
    public teacher parseJson(String json) throws JSONException {
        JSONObject obj=new JSONObject(json);
        teacher t=new teacher();
        t.setTname(obj.getString("tname"));
        t.setTid(obj.getString("tid"));
        t.setTmail(obj.getString("tmail"));
        t.setTpoint(obj.getInt("tpoint"));
        t.setTdepartment(obj.getString("tdepartment"));
        //t.setTimage(obj.getString("timage"));
        t.setTintroduction(obj.getString("tintroduction"));
        t.setTschool(obj.getString("tschool"));
        return t;
    }

    public void run()
    {
        try {
            doGet();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
