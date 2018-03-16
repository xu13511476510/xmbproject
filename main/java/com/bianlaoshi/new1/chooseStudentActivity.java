package com.bianlaoshi.new1;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by frank on 2017/12/23.
 */

public class chooseStudentActivity extends AppCompatActivity {
    public static user u1;
    public static student s1;
    private RecyclerView mRecyclerView;
    private memberadapt adapter1;
    private Handler handler=new Handler();
    public static List<project> plist=new ArrayList<project>();
    public static Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_9review_student);

        String url="http://112.74.176.171:8080/xmb/servlet/l_1shouyeServlet?tid="+this.u1.getUid();
        Thread thread= new chooseProjectThread(url,handler,this);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.r1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter1 = new memberadapt(this));


    }

    private class memberadapt extends RecyclerView.Adapter<memberadapt.viewHolder>{
        private Context context;
        public memberadapt(Context context){
            this.context=context;
        }
        public class viewHolder extends RecyclerView.ViewHolder{
            TextView xiangmuname;
            public viewHolder(View view){
                super(view);
                xiangmuname=(TextView)view.findViewById(R.id.l_yaoqqingstuitem_pro);
                itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        //加确定弹窗
                        new AlertDialog.Builder(chooseStudentActivity.this)
                                .setTitle("确定")
                                .setMessage("确定要邀请吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String url = null;
                                        try {
                                            url = "http://112.74.176.171:8080/xmb/servlet/studentProjectServlet?order=2"+"&sid="+s1.getSid()+"&pid="+plist.get(getAdapterPosition()).getPid()+"&tid="+u1.getUid()+"&pname="+ URLEncoder.encode(plist.get(getAdapterPosition()).getPname(),"utf-8");
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                        Thread thread=new l_yaoqingxueshengThread(url,handler,chooseStudentActivity.this);
                                        thread.start();
                                        try {
                                            thread.join();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                                .setNegativeButton("取消",null)
                                .show();
                    }
                });
            }
        }
        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            viewHolder holder = new viewHolder(LayoutInflater.from(
                    chooseStudentActivity.this).inflate(R.layout.l_yaoqingstuitem, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(viewHolder holder, int position) {
            holder.xiangmuname.setText(plist.get(position).getPname());
        }

        @Override
        public int getItemCount() {
            return plist.size();
        }
    }
}
