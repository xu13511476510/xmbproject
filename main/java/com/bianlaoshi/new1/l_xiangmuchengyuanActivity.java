package com.bianlaoshi.new1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 2017/12/24.
 */

public class l_xiangmuchengyuanActivity extends AppCompatActivity {

    public static project p;
    private static List<student> studentList=new ArrayList<student>();
    private Handler handler=new Handler();
    private RecyclerView mRecycleView;
    private homeadapter ada;
    public static int pd=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_xiangmuchengyuan);
        l_xiangmuchengyuanActivity.p=l_xiugaixiangmuActivity.p;

        String url="http://112.74.176.171:8080/xmb/servlet/xiangmuchengyuanServlet?pid="+p.getPid();
        Thread thread=new l_3discovery_teacherActivityThread(url,handler,this,studentList);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(pd==0)
        {
            finish();
            Intent intent=new Intent(l_xiangmuchengyuanActivity.this,l_xiangmuchengyuanActivity.class);
            startActivity(intent);
            pd=1;
        }

        mRecycleView=(RecyclerView)findViewById(R.id.r1);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(ada=new homeadapter(this));


    }

    class homeadapter extends RecyclerView.Adapter<homeadapter.myholder>{
        Context context;
        public homeadapter(Context context){
            this.context=context;
        }
        class myholder extends RecyclerView.ViewHolder{
            TextView studentName;
            TextView studentAdvantage;
            TextView studentcontext;
            public myholder(View view){
                super(view);
                studentName = (TextView) view.findViewById(R.id.stu_name);
                studentAdvantage=(TextView)view.findViewById(R.id.stu_advantage);
                studentcontext=(TextView)view.findViewById(R.id.text_context);
            }

        }
        @Override
        public myholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.l_projmemitem,parent,false);
            myholder holder=new myholder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(myholder holder, int position) {
            student stu=studentList.get(position);
            holder.studentName.setText(stu.getSname());
            holder.studentAdvantage.setText(stu.getSadvantage());
            holder.studentcontext.setText(stu.getSid());
        }

        @Override
        public int getItemCount() {
            return studentList.size();
        }
    }
}
