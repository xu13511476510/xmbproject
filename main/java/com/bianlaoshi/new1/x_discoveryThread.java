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

public class x_discoveryThread extends Thread {
    private Handler handler;
    private Context context;
    private String url;
    private  List<project> projectList;
    private List<project> datalist;
    public x_discoveryThread(String url, Context context,Handler handler,List<project> datalist)
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
        projectList=jsonParse(sb.toString());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //修改acitivity
                X_Discovery.datalist.clear();
                for(int i=0;i<projectList.size();i++){
                    project p1=new project();
                    p1.setPid(projectList.get(i).getPid());
                    p1.setPtid(projectList.get(i).getPtid());
                    p1.setPname(projectList.get(i).getPname());
                    p1.setPnum(projectList.get(i).getPnum());
                    p1.setPcondition(projectList.get(i).getPcondition());
                    p1.setPrequire(projectList.get(i).getPrequire());
                    p1.setPintroduction(projectList.get(i).getPintroduction());
                    p1.setPelse(projectList.get(i).getPelse());
                    X_Discovery.datalist.add(p1);
                }
            }
        });
    }
    //解析函数
    private List<project> jsonParse(String json) throws JSONException {
        JSONObject obj=new JSONObject(json);
        int result=obj.getInt("result");
        List<project> projects=new ArrayList<project>();
        if(result==1)
        {
            JSONArray projectData=obj.getJSONArray("projectList");
            for(int i=0;i<projectData.length();i++) {
                JSONObject projectObj = projectData.getJSONObject(i);
                project p1=new project();
                p1.setPid(projectObj.getInt("pid"));
                p1.setPtid(projectObj.getString("ptid"));
                p1.setPname(projectObj.getString("pname"));
                p1.setPnum(projectObj.getInt("pnum"));
                p1.setPcondition(projectObj.getString("pcondition").charAt(0));
                p1.setPrequire(projectObj.getString("prequire"));
                p1.setPintroduction(projectObj.getString("pintroduction"));
                p1.setPelse(projectObj.getString("pelse"));
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

