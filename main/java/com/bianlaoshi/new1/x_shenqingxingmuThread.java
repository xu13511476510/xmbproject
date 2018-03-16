package com.bianlaoshi.new1;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by frank on 2017/12/24.
 */

public class x_shenqingxingmuThread extends Thread {
    private String url;
    private Handler handler;
    private Context context;
    public x_shenqingxingmuThread(String url,Handler handler,Context context)
    {
        this.url=url;
        this.handler=handler;
        this.context=context;
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

        handler.post(new Runnable() {
            @Override
            public void run() {

                if(sb.toString().indexOf("0")!=-1)
                {
                    new AlertDialog.Builder(context)
                            .setMessage("提交成功")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                else if(sb.toString().indexOf("1")!=-1)
                {
                    new AlertDialog.Builder(context)
                            .setMessage("正在申请。老师还未同意")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                else if(sb.toString().indexOf("2")!=-1)
                {
                    new AlertDialog.Builder(context)
                            .setMessage("成功加入项目！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                else if(sb.toString().indexOf("3")!=-1)
                {
                    new AlertDialog.Builder(context)
                            .setMessage("您已经在这个项目中了！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }

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
