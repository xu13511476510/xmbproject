package com.bianlaoshi.new1;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 2017/11/20.
 */

public class TeacherMessageThread extends Thread {
    private String url;
    private Handler handler;
    private Context context;
    private List<message1> messageList=new ArrayList<message1>();
    private List<message1> datalist;
    public TeacherMessageThread(String url,Handler handler,Context context,List<message1> datalist)
    {
        this.url=url;
        this.handler=handler;
        this.context=context;
        this.datalist=datalist;
    }

    private void doGet() throws IOException, JSONException {
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
        messageList=parseJson(sb.toString());
        for(int i=0;i<messageList.size();i++){
            message1 m1=new message1();
            m1.setMid(messageList.get(i).getMid());
            m1.setMtext( messageList.get(i).getMtext());
            m1.setMuid1( messageList.get(i).getMuid1());
            m1.setMuname(messageList.get(i).getMuname());
            m1.setMsituation(messageList.get(i).getMsituation());
            m1.setMuid2(messageList.get(i).getMuid2());
            datalist.add(m1);
        }
        /*
        handler.post(new Runnable() {
            @Override
            public void run() {
                //修改activity


            }
        });*/
    }

    private List<message1> parseJson(String json) throws JSONException {
        JSONObject obj=new JSONObject(json);
        int result=obj.getInt("result");
        List<message1> messages=new ArrayList<message1>();
        if(result==1)
        {
            JSONArray messageData=obj.getJSONArray("messageList");
            for(int i=0;i<messageData.length();i++) {
                JSONObject messageObj = messageData.getJSONObject(i);
                message1 m1=new message1();
                m1.setMid( messageObj.getInt("mid"));
                m1.setMsituation( messageObj.getString("msituation").toCharArray()[0]);
                m1.setMtext( messageObj.getString("mtext"));
                m1.setMuid1( messageObj.getString("muid1"));
                m1.setMuid2( messageObj.getString("muid2"));
                m1.setMuname(messageObj.getString("muname"));
                messages.add(m1);
            }
        }
        else
        {
            Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
        }
        return messages;
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
