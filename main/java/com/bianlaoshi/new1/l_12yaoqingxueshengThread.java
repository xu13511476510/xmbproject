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
 * Created by frank on 2017/11/29.
 */

public class l_12yaoqingxueshengThread extends Thread {
    private String url;
    private Context context;
    private Handler handler;
    private List<studentcomment> scList;

    public l_12yaoqingxueshengThread(String url, Context context,Handler handler, List<studentcomment> scList)
    {
        this.url=url;
        this.context=context;
        this.handler=handler;
        this.scList=scList;
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
        final List<studentcomment> studentcomments=parseJson(sb.toString());
        for(int i=0;i<studentcomments.size();i++)
        {
            scList.add(studentcomments.get(i));
        }

    }

    private List<studentcomment> parseJson(String json) throws JSONException {
        JSONObject obj=new JSONObject(json);
        int result=obj.getInt("result");
        List<studentcomment> scList=new ArrayList<studentcomment>();
        if(result==1)
        {
            JSONArray scData=obj.getJSONArray("scList");
            for(int i=0;i<scData.length();i++) {
                JSONObject studentObj = scData.getJSONObject(i);
                studentcomment sc=new studentcomment();
                sc.setPname(studentObj.getString("pname"));
                sc.setSccomment(studentObj.getString("sccomment"));
                sc.setScprojectstar(studentObj.getInt("scprojectstar"));
                sc.setScrank(studentObj.getInt("scrank"));
                sc.setScsid(studentObj.getString("scsid"));
                sc.setScstar(studentObj.getInt("scstar"));
                sc.setSctag(studentObj.getString("sctag"));
                scList.add(sc);
            }
        }
        else
        {
            Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
        }
        return scList;
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
