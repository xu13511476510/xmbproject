package com.bianlaoshi.new1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangyuan on 2017/11/14.
 */

public class TeacherMessage extends AppCompatActivity {

    public static user u1;
    private Handler handler=new Handler();
    private TextView tishi;
    private List<message1> datalist=new ArrayList<message1>();
    private RecyclerView mRecyclerView;
    private Memberadapter memada;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_13xitongxiaoxi);
        //分配控件
        tishi=(TextView)findViewById(R.id.textView4);
        mRecyclerView = (RecyclerView) findViewById(R.id.p3);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(memada = new Memberadapter(this));
        //开始线程

        String url="http://112.74.176.171:8080/xmb/servlet/messageServlet?uid="+this.u1.getUid();
        TeacherMessageThread thread=new TeacherMessageThread(url,handler,this,datalist);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(datalist.size()==0)
            tishi.setText("没有新消息哦");
    }
    private class Memberadapter extends RecyclerView.Adapter<Memberadapter.ViewHolder>{
        private Context context;
        private LayoutInflater layoutInflater;
        public Memberadapter(Context context){
            this.context=context;
            this.layoutInflater=LayoutInflater.from(context);
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView mid,messa,muname;
            private ImageView jump;

            public ViewHolder(View itemView){
                super(itemView);
                mid=(TextView)itemView.findViewById(R.id.textView25);
                messa=(TextView)itemView.findViewById(R.id.textView24);
                muname=(TextView)itemView.findViewById(R.id.textView27);
                final TextView ceshi=(TextView) findViewById(R.id.textView26);
                jump=(ImageView)itemView.findViewById(R.id.imageView2);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*if (getAdapterPosition() == RecyclerView.NO_POSITION) {
                            Toast.makeText(
                                    context,
                                    R.string.msg_ClickAgain,
                                    Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                        l_xiugaixiangmuActivity.p=datalist.get(getAdapterPosition());*/
                        String phoneNo=String.valueOf(datalist.get(getAdapterPosition()).getMsituation());
                        if(phoneNo.contains("3")){
                            L_Discovery.shuru=datalist.get(getAdapterPosition()).getMuid1();
                            L_Discovery.pd2=1;

                            Intent intent = new Intent();
                            intent.setClass(TeacherMessage.this,L_Discovery.class);
                            TeacherMessage.this.startActivity(intent);
                        }
                        else if(phoneNo.contains("4")) {
                            l_10Activity.pd=1;
                            l_10Activity.mess = datalist.get(getAdapterPosition());
                            finish();
                            Intent intent = new Intent();
                            intent.setClass(TeacherMessage.this, l_10Activity.class);
                            TeacherMessage.this.startActivity(intent);
                        }
                        else if(phoneNo.contains("2")) {
                            X_Discovery.shuru=datalist.get(getAdapterPosition()).getMtext();
                            X_Discovery.pd2=1;

                            Intent intent = new Intent();
                            intent.setClass(TeacherMessage.this, X_Discovery.class);
                            TeacherMessage.this.startActivity(intent);

                        }
                    }
                });

                ImageButton del=(ImageButton)itemView.findViewById(R.id.imageButton);
                del.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(TeacherMessage.this)
                                .setTitle("确定")
                                .setMessage("确定删除吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String url="http://112.74.176.171:8080/xmb/servlet/deleteMessageServlet?mid="+datalist.get(getAdapterPosition()).getMid();
                                        Thread thread= new sendMessageThread(url);
                                        thread.start();
                                        try {
                                            thread.join();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        finish();
                                        Intent intent = new Intent();
                                        intent.setClass(TeacherMessage.this,TeacherMessage.class);
                                        TeacherMessage.this.startActivity(intent);
                                    }
                                })
                                .setNegativeButton("取消",null)
                                .show();
                    }
                });

            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView=layoutInflater.inflate(R.layout.m_item,parent,false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(datalist.get(position).getMsituation()=='4') {
                holder.mid.setText("" + datalist.get(position).getMuid1());
                holder.messa.setText("邀请您评价：" + datalist.get(position).getMtext());
                holder.muname.setText(datalist.get(position).getMuname());
            }
            else if(datalist.get(position).getMsituation()=='2')
            {
                holder.mid.setText("" + datalist.get(position).getMuid1());
                holder.messa.setText("邀请您加入项目" );
                holder.muname.setText(datalist.get(position).getMuname());
            }
            else
            {
                holder.mid.setText("" + datalist.get(position).getMuid1());
                holder.messa.setText("" + datalist.get(position).getMtext());
                holder.muname.setText(datalist.get(position).getMuname());
            }

            if(datalist.get(position).getMsituation()=='2'||datalist.get(position).getMsituation()=='3'||datalist.get(position).getMsituation()=='4')
            {
                holder.messa.setTextColor(0xffFFFF66);
                holder.jump.setAlpha(1f);
            }

        }

        @Override
        public int getItemCount() {
            return datalist.size();
        }

    }
}


