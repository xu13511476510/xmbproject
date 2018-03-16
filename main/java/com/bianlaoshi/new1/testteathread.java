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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by lenovo on 2017/12/18.
 */
//handler,context,uid2,uid1,u.getUidentity(),pname
public class testteathread extends Thread{
    private Handler handler;
    private EditText tid;
    private Context context;
    private String pname;
    private user u;
    private String url1;
    private String uid2;
    public static int a=0;
    public testteathread(String url1,Handler handler,Context context,String pname,EditText tid,user u,String uid2){
        this.handler=handler;
        this.u=u;
        this.context=context;
        this.tid=tid;
        this.pname=pname;
        this.url1=url1;
        this.uid2=uid2;
    }

    private void doGet() throws IOException, JSONException {
        URL httpUrl=new URL(this.url1);
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
                            .setMessage("该老师不存在")
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
                        final String uid1=tid.getText().toString();
                        String url= null;
                        try {
                            url = "http://112.74.176.171:8080/xmb/servlet/addMessageServlet?muid1="+uid2+"&muid2="+uid1+ "&mtext="+ URLEncoder.encode(pname,"utf-8")+"&msituation=4";
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Thread thread2=new sendMessageThread(url);
                        thread2.start();
                        try {
                            thread2.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //ceshi.setText("发表成功");
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

    //运行函数
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
