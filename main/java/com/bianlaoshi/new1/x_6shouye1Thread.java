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
 * Created by frank on 2017/11/19.
 */

public class x_6shouye1Thread extends Thread {
    private String url;
    private Context context;
    private List<studentcomment> projectList=new ArrayList<studentcomment>();
    private List<studentcomment> datalist;
    private Handler handler;
    public x_6shouye1Thread(String url,Context context,Handler handler,List<studentcomment> datalist)
    {
        this.url=url;
        this.context=context;
        this.handler=handler;
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
        projectList=parseJson(sb.toString());
        for (int i = 0; i < projectList.size(); i++) {
            studentcomment p1=new studentcomment();
            p1.setScsid(projectList.get(i).getScsid());
            p1.setSctag(projectList.get(i).getSctag());
            p1.setSccomment(projectList.get(i).getSccomment());
            datalist.add(p1);

         /*
        handler.post(new Runnable() {
            @Override
            public void run() {
                //修改activity

                }
            }
        });
        */
        }
    }
    //解析函数
    private List<studentcomment> parseJson(String json) throws JSONException {
        JSONObject obj=new JSONObject(json);
        int result=obj.getInt("result");
        List<studentcomment> projects=new ArrayList<studentcomment>();
        if(result==1)
        {
            JSONArray projectData=obj.getJSONArray("projectList");
            for(int i=0;i<projectData.length();i++) {
                JSONObject projectObj = projectData.getJSONObject(i);
                studentcomment p1=new studentcomment();
                p1.setPname(projectObj.getString("pname"));
                p1.setSccomment(projectObj.getString("sccomment"));
                p1.setScprojectstar(projectObj.getInt("scprojectstar"));
                p1.setScrank(projectObj.getInt("scrank"));
                p1.setScsid(projectObj.getString("scsid"));
                p1.setScstar(projectObj.getInt("scstar"));
                p1.setSctag(projectObj.getString("sctag"));
                projects.add(p1);
            }
        }
        else
        {
            Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
        }
        return projects;
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
