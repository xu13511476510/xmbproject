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
 * Created by frank on 2017/12/23.
 */

public class chooseProjectThread extends Thread {
    private String url;
    private Handler handler;
    private Context context;
    private List<project> projectList;

    public chooseProjectThread(String url, Handler handler, Context context)
    {
        this.url=url;
        this.handler=handler;
        this.context=context;
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

        this.projectList=parseJson(sb.toString());
        chooseStudentActivity.plist.clear();
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < projectList.size(); i++) {
                    chooseStudentActivity.plist.add(projectList.get(i));
                }
            }
        });

    }

    //解析函数
    private List<project> parseJson(String json) throws JSONException {
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
            this.doGet();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
