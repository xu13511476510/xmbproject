package com.bianlaoshi.new1;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by frank on 2017/11/18.
 */

public class mainActivityThread extends Thread {
    private String url;
    private Handler handler;
    private TextView textViewResult;
    private Context context;
    public mainActivityThread(String url,Handler handler,TextView textViewResult,Context context)
    {
        this.url=url;
        this.handler=handler;
        this.textViewResult=textViewResult;
        this.context=context;
    }
    //传输数据函数
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
                    textViewResult.setText("用户或密码错误，或者账号被封。请联系客服");
                }
                else
                {
                    try {
                        user u1=parseJson(sb.toString());
                        if(u1.getUidentity()=='1')
                        {
                            //跳转到老师页面

                            String url="http://112.74.176.171:8080/xmb/servlet/dateNumberServlet";
                            new sendMessageThread(url).start();

                            l_1shouye.u1=u1;
                            l_8gerenziliao.u1=u1;
                            TeacherMessage.u1=u1;
                            textViewResult.setText("");
                            Intent intent=new Intent(context,L_Mine.class);
                            context.startActivity(intent);
                        }
                        else
                        {
                            if(u1.getUidentity()=='2')
                            {
                                //跳转到学生页面

                                String url="http://112.74.176.171:8080/xmb/servlet/dateNumberServlet";
                                new sendMessageThread(url).start();

                                x_6shouye1Activity.u1=u1;
                                x_13gerenziliao1.u1=u1;
                                TeacherMessage.u1=u1;
                                textViewResult.setText("");
                                Intent intent=new Intent(context,X_Mine.class);
                                context.startActivity(intent);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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
