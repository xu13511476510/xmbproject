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

public class teststuthread extends Thread{
    private String url1;
    private Handler handler;
    private EditText esctag;
    private EditText esccomment;
    private EditText epname;
    private String scsid;
    private EditText scrank;
    private Context context;
    private user user;
    public static int a=0;
    public teststuthread(String url1, Handler handler, EditText esccomment, EditText epname, String scsid, EditText scrank,EditText esctag,Context context){
        this.url1=url1;
        this.handler=handler;
        this.esccomment=esccomment;
        this.epname=epname;
        this.scsid=scsid;
        this.scrank=scrank;
        this.esctag=esctag;
        this.context=context;
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
                    //ceshi.setText("该学生不存在");
                    new AlertDialog.Builder(context)
                            .setMessage("该学生不存在")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else{
                    try {
                        user=parseJson(sb.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final String s=String.valueOf(user.getUidentity());
                    if(s.contains("1")){
                        new AlertDialog.Builder(context)
                                .setMessage("该用户是老师，不能评价")
                                .setPositiveButton("确定",null)
                                .show();
                    }
                    else{
                        String sctag=esctag.getText().toString();
                        String sccomment=esccomment.getText().toString();
                        String pname=epname.getText().toString();
                        String srank=scrank.getText().toString();
                        String identity;
                        try {
                            sctag= URLEncoder.encode(sctag,"utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        try {
                            srank= URLEncoder.encode(srank,"utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        try {
                            sccomment= URLEncoder.encode(sccomment,"utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        try {
                            pname=URLEncoder.encode(pname,"utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        String url="http://112.74.176.171:8080/xmb/servlet/l_10Servlet?scsid="+scsid+"&scrank="+srank+"&sctag="+sctag+"&sccomment="+sccomment+"&pname="+pname;
                        Thread thread2=new l_10Thread(url);
                        //ceshi.setText("发表成功");
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
                                        Intent intent=new Intent(context,L_Mine.class);
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
