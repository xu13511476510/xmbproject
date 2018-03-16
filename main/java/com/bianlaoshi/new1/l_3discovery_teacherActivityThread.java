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
 * Created by frank on 2017/11/18.
 */

public class l_3discovery_teacherActivityThread extends Thread {
    private String url;
    private List<student> studentList;
    private Handler handler;
    private Context context;
    private List<student> studentList1;

    public l_3discovery_teacherActivityThread(String url,Handler handler,Context context,List<student> studentList1)
    {
        this.url=url;
        this.handler=handler;
        this.context=context;
        this.studentList1=studentList1;
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
        studentList=parseJson(sb.toString());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //修改activity的内容
                studentList1.clear();
                for(int i=0;i<studentList.size();i++)
                {
                    studentList1.add(studentList.get(i));
                }
            }
        });
    }
    //解析函数
    private List<student> parseJson(String json) throws JSONException {
        JSONObject obj=new JSONObject(json);
        int result=obj.getInt("result");
        List<student> students=new ArrayList<student>();
        if(result==1)
        {
            JSONArray studentData=obj.getJSONArray("studentList");
            for(int i=0;i<studentData.length();i++) {
                JSONObject studentObj = studentData.getJSONObject(i);
                student stu=new student();
                stu.setSid(studentObj.getString("sid"));
                stu.setSname(studentObj.getString("sname"));
                stu.setSadvantage(studentObj.getString("sadvantage"));
                stu.setSdepartment(studentObj.getString("sdepartment"));
                stu.setSgrade(studentObj.getString("sgrade"));
                //stu.setSimage(studentObj.getString("simage"));
                stu.setSintroduction(studentObj.getString("sintroduction"));
                stu.setSmail(studentObj.getString("smail"));
                stu.setSmajor(studentObj.getString("smajor"));
                stu.setSpoint(studentObj.getInt("spoint"));
                stu.setSschool(studentObj.getString("sschool"));
                students.add(stu);
            }
        }
        else
        {
            Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
        }
        return students;
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
