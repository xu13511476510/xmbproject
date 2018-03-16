package com.bianlaoshi.new1;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lenovo on 2017/12/30.
 */

public class testteathread1 extends Thread{
    private String url;
    private Handler handler;
    private float f;
    private EditText phone;
    private user u;
    private Context context;
    public testteathread1(String url, Handler handler, float f, EditText phone, user u, Context context){
        this.url=url;
        this.handler=handler;
        this.f=f;
        this.phone=phone;
        this.u=u;
        this.context=context;
    }

    private void doGet() throws IOException, JSONException {
        URL httpUrl=new URL(this.url);
        HttpURLConnection conn=(HttpURLConnection)httpUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(5000);
        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String str;
        final StringBuffer sb=new StringBuffer();
        while((str=br.readLine())!=null){
            sb.append(str);
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                if(sb.toString().contains("No")){
                    new AlertDialog.Builder(context)
                            .setMessage("该用户不存在")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else{
                    try {
                        u=parseJson(sb.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final String s=String.valueOf(u.getUidentity());
                    if(s.contains("2")){
                        new AlertDialog.Builder(context)
                                .setMessage("该用户是学生，不能评价")
                                .setPositiveButton("确定",null)
                                .show();
                    }
                    else{
                        if(f>3)
                        {
                            String phonein=phone.getText().toString();
                            String url="http://112.74.176.171:8080/xmb/servlet/x_10reviewTeacherServlet?tid="+phonein;
                            Thread thread2=new sendMessageThread(url);
                            thread2.start();
                            try {
                                thread2.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                            new AlertDialog.Builder(context)
                                    .setMessage("提交成功")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent=new Intent(context,X_Mine.class);
                                            context.startActivity(intent);
                                        }
                                    })
                                    .show();
                    }
                }
            }
        });
    }

    private user parseJson(String json) throws JSONException {
        JSONObject obj=new JSONObject(json);
        user u=new user();
        u.setUidentity(obj.getString("uidentity").charAt(0));//.toCharArray()[0]
        return u;
    }

    public void run(){
        try {
            doGet();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
