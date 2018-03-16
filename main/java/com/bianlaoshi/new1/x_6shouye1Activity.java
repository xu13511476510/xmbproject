package com.bianlaoshi.new1;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangyuan on 2017/11/19.
 */

public class x_6shouye1Activity extends AppCompatActivity {
    public static user u1;
    private Handler handler=new Handler();
    private TextView tishi;
    private List<studentcomment> datalist=new ArrayList<studentcomment>();
    private RecyclerView mRecyclerView;
    private MemberAdapter memada;
    private ImageView view1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_6shouye1);
        //分配控件
        tishi=(TextView)findViewById(R.id.textView4);
        mRecyclerView = (RecyclerView) findViewById(R.id.p1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(memada = new MemberAdapter(this));

        datalist=new ArrayList<studentcomment>();
        String url="http://112.74.176.171:8080/xmb/servlet/l_12Servlet?sid="+u1.getUid();
        l_12yaoqingxueshengThread thread=new l_12yaoqingxueshengThread(url,this,handler,datalist);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(datalist.size()==0)
            tishi.setText("还没有老师给你评价哦");




    }
    private class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{
        private LayoutInflater layoutInflater;
        private Context context;
        public MemberAdapter(Context context)
        {
            this.context=context;
            this.layoutInflater=LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView=layoutInflater.inflate(R.layout.x_6item,parent,false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.xmname.setText(datalist.get(position).getPname());
            holder.jobname.setText(datalist.get(position).getSctag());
            holder.stucomment.setText(datalist.get(position).getSccomment());
        }

        @Override
        public int getItemCount() {
            return datalist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView xmname;
            private TextView jobname;
            private TextView stucomment;

            public ViewHolder(View itemView){
                super(itemView);
                xmname=(TextView)itemView.findViewById(R.id.textView14);
                jobname=(TextView)itemView.findViewById(R.id.textView16);
                stucomment=(TextView)itemView.findViewById(R.id.textView18);
            }
        }
    }
}


