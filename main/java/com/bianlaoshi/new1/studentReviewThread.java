package com.bianlaoshi.new1;

import android.content.Context;
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
 * Created by frank on 2017/12/6.
 */

public class studentReviewThread extends Thread {
    private String url;
    private Context context;
    private List<studentreview> srList;

    public studentReviewThread(String url, Context context,List<studentreview> srList)
    {
        this.url=url;
        this.context=context;
        this.srList=srList;
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
        final List<studentreview> studentreviews=parseJson(sb.toString());
        for(int i=0;i<studentreviews.size();i++)
        {
            srList.add(studentreviews.get(i));
        }

    }

    private List<studentreview> parseJson(String json) throws JSONException {
        JSONObject obj=new JSONObject(json);
        int result=obj.getInt("result");
        List<studentreview> srList=new ArrayList<studentreview>();
        if(result==1)
        {
            JSONArray srData=obj.getJSONArray("srList");
            for(int i=0;i<srData.length();i++) {
                JSONObject srObj = srData.getJSONObject(i);
                studentreview sr=new studentreview();
                sr.setSrjob(srObj.getString("srjob"));
                sr.setSrpname(srObj.getString("srpname"));
                sr.setSrreview(srObj.getString("srreview"));
                sr.setSrsid(srObj.getString("srsid"));
                srList.add(sr);
            }
        }
        else
        {
            Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
        }
        return srList;
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
