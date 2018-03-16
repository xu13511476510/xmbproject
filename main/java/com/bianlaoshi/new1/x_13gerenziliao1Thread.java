package com.bianlaoshi.new1;

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

public class x_13gerenziliao1Thread extends Thread {
    private String url;
    private Handler handler;
    private student s;
    private EditText sname;
    private TextView sid;
    private EditText smail;
    private TextView sschool;
    private EditText sintroduction;
    private EditText sadvantage;


    public x_13gerenziliao1Thread(String url,Handler handler,EditText sname,
            TextView sid,
            EditText smail,
            TextView sschool,
            EditText sintroduction,
            EditText sadvantage)
    {
        this.url=url;
        this.handler=handler;
        this.sname=sname;
        this.sid=sid;
        this.smail=smail;
        this.sschool=sschool;
        this.sintroduction=sintroduction;
        this.sadvantage=sadvantage;
    }

    private void doGet() throws IOException, JSONException {
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
        s=parseJson(sb.toString());
        handler.post(new Runnable() {
            @Override
            public void run() {
                sname.setText(s.getSname());
                sid.setText(s.getSid());
                smail.setText(s.getSmail());
                sschool.setText(s.getSschool());
                sintroduction.setText(s.getSintroduction());
                sadvantage.setText(s.getSadvantage());
            }
        });
    }

    private student parseJson(String json) throws JSONException {
        JSONObject obj=new JSONObject(json);
        student stu=new student();
        stu.setSid(obj.getString("sid"));
        stu.setSname(obj.getString("sname"));
        stu.setSadvantage(obj.getString("sadvantage"));
        stu.setSdepartment(obj.getString("sdepartment"));
        stu.setSgrade(obj.getString("sgrade"));
        //stu.setSimage(obj.getString("simage"));
        stu.setSintroduction(obj.getString("sintroduction"));
        stu.setSmail(obj.getString("smail"));
        stu.setSmajor(obj.getString("smajor"));
        stu.setSpoint(obj.getInt("spoint"));
        stu.setSschool(obj.getString("sschool"));
        return stu;
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
