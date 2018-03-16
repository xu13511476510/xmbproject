package com.bianlaoshi.new1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangyuan on 2017/11/18.
 */

public class L_Discovery extends AppCompatActivity {

    public static List<student> studentList=new ArrayList(); //一定要是静态的
    private Handler handler=new Handler();//初始化handler
    private List<student> studentList1=new ArrayList();
    public static String search="";
    public static String shuru="";
    public static int pd=0;
    public static int pd2=0;
    private TextView tishi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* super.onCreate(savedInstanceState);
        setContentView(R.layout.l_3discovery_teacher);
        initStudent();
        RecyclerView recyclerview=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        StudentAdapter adapter=new StudentAdapter(studentList);
        recyclerview.setAdapter(adapter);*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_3discovery_teacher);
        //分配控件
        tishi=(TextView)findViewById(R.id.textView4);


        if(pd2==1)
        {
            EditText sousuo=(EditText)findViewById(R.id.search);
            sousuo.setText(shuru);
            pd2=0;
        }


        //页面显示数据的获取
        String url="http://112.74.176.171:8080/xmb/servlet/l_3discovery_teacherServlet";
        l_3discovery_teacherActivityThread thread=new l_3discovery_teacherActivityThread(url,handler,this,studentList);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if(pd==0)
        {
            RecyclerView recyclerview=(RecyclerView)findViewById(R.id.recycler_stu);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerview.setLayoutManager(layoutManager);
            StudentAdapter adapter=new StudentAdapter(studentList,this);
            recyclerview.setAdapter(adapter);
        }
        else {
            for (int i = 0; i < studentList.size(); i++) {
                if (studentList.get(i).getSid().indexOf(search)!=-1 || studentList.get(i).getSadvantage().indexOf(search)!=-1) {
                    studentList1.add(studentList.get(i));
                }
            }
            if(studentList1.size()==0)
                tishi.setText("没有搜索到任何一个学生哦");
            RecyclerView recyclerview=(RecyclerView)findViewById(R.id.recycler_stu);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerview.setLayoutManager(layoutManager);
            StudentAdapter adapter=new StudentAdapter(studentList1,this);
            recyclerview.setAdapter(adapter);
        }





        TextView doSearch=(TextView)findViewById(R.id.jiedan);
        doSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText searchInfo=(EditText)findViewById(R.id.search);
                L_Discovery.search=searchInfo.getText().toString();
                if(pd==0)
                {
                    pd=1;
                }
                finish();
                Intent intent = new Intent(L_Discovery.this, L_Discovery.class);
                startActivity(intent);

            }
        });
    }
}

