package com.bianlaoshi.new1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
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
 * Created by frank on 2017/12/16.
 */

public class testUserThread extends Thread {
    private String url;
    private Handler handler;
    private EditText phonein;
    private EditText pass;
    private TextView test;
    private Context context;
    private int id;
    private EditText pass1;
    private EditText yzm;

    private String phoneNo;

    public testUserThread(String url,Handler handler,EditText phonein,EditText pass,EditText pass1,EditText yzm,TextView test,int id,Context context,String phoneNo)
    {
        this.url=url;
        this.handler=handler;
        this.phonein=phonein;
        this.pass=pass;
        this.test=test;
        this.id=id;
        this.context=context;
        this.pass1=pass1;
        this.yzm=yzm;
        this.phoneNo=phoneNo;
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
                if(sb.toString().contains("No"))
                {
                    String phone=phonein.getText().toString();
                    String passA=pass.getText().toString();
                    String url="http://112.74.176.171:8080/xmb/servlet/createUserServlet?uid="+phoneNo+"&pass="+passA+"&identity="+id;
                    new sendMessageThread(url).start();

                    a_4zhuceActivity.a[0]=-1;
                    phonein.setText("");
                    pass.setText("");
                    pass1.setText("");
                    yzm.setText("");
                    test.setText("");
                    new AlertDialog.Builder(context)
                            .setMessage("注册成功")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setClass(context,Login.class);
                                    context.startActivity(intent);
                                }
                            })
                            .show();

                }
                else
                {
                    new AlertDialog.Builder(context)
                            .setTitle("错误")
                            .setMessage("用户已存在")
                            .setPositiveButton("确定",null)
                            .show();
                }
            }
        });
    }
    //解析json字符串
    private user parseJson(String json) throws JSONException {
        JSONObject obj=new JSONObject(json);
        user u1=new user();
        u1.setUid(obj.getString("uid"));
        u1.setUpassword(obj.getString("upassword"));
        u1.setUidentity(obj.getString("uidentity").charAt(0));
        u1.setUcondition(obj.getString("ucondition").charAt(0));
        return u1;
    }
    //运行函数
    public void run()
    {
        try {
            doGet();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
