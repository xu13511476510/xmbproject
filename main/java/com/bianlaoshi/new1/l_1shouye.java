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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhuangyuan on 2017/11/14.
 */

public class l_1shouye extends AppCompatActivity {
    public List<project> mDatas=new ArrayList<project>();
    private List<student> studentList=new ArrayList<>();
    public static user u1;
    private Handler handler=new Handler();//初始化handler
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView_stu;
    private HomeAdapter mAdapter;
    private ImageView button1;
    private ImageView view1;
    private TextView tishi;
    private int s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_1shouye);
        tishi=(TextView)findViewById(R.id.textView4);
        s=0;
        String url="http://112.74.176.171:8080/xmb/servlet/l_1shouyeServlet?tid="+this.u1.getUid();
        l_1shouyeActivityThread thread= new l_1shouyeActivityThread(url,handler,this,mDatas,s,tishi);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //tishi.setText(s+"");
        /*if(mDatas.size()==0)
            tishi.setText("还没有发布项目哦");*/
        mRecyclerView = (RecyclerView) findViewById(R.id.project1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter(this));

        //点击加号发布项目
        button1=(ImageView)findViewById(R.id.imageButton2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                l_5fabuxiangmuActivity.u=u1;
                finish();
                Intent intent=new Intent(l_1shouye.this,l_5fabuxiangmuActivity.class);
                startActivity(intent);
            }
        });


    }
    class HomeAdapter extends  RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
        private Context context;

        public HomeAdapter(Context context)
        {
            this.context=context;
        }
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    l_1shouye.this).inflate(R.layout.l_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position)
        {

            holder.t1.setText(mDatas.get(position).getPname());
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView t1;
            public MyViewHolder(View view)
            {
                super(view);
                t1 = (TextView) view.findViewById(R.id.textView30);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getAdapterPosition() == RecyclerView.NO_POSITION) {
                            Toast.makeText(
                                    context,
                                    R.string.msg_ClickAgain,
                                    Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                        l_xiugaixiangmuActivity.p=mDatas.get(getAdapterPosition());
                        finish();
                        Intent intent = new Intent();
                        intent.setClass(l_1shouye.this,l_xiugaixiangmuActivity.class);
                        l_1shouye.this.startActivity(intent);
                    }
                });
            }
        }
    }
}
