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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangyuan on 2017/11/18.
 */

public class X_Discovery extends AppCompatActivity {
    private Handler handler=new Handler();
    public static List<project> datalist=new ArrayList<project>();
    private RecyclerView mRecyclerView;
    private TextView tishi;
    private memadapter mem;
    private List<project> datalist1=new ArrayList<project>();
    public static String search;
    public static int pd=0;
    public static String shuru="";
    public static int pd2=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_7discovery);
        tishi=(TextView)findViewById(R.id.textView4);

        if(pd2==1)
        {
            EditText sousuo = (EditText) findViewById(R.id.search);
            sousuo.setText(""+shuru);
            pd2=0;
        }




        //开启线程
        String url="http://112.74.176.171:8080/xmb/servlet/x_discoveryServlet";
        x_discoveryThread thread= new x_discoveryThread(url,this,handler,datalist);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if(pd==0)
        {
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_stu);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mem = new memadapter(this,datalist));
        }
        else
        {

            for(int i=0;i<datalist.size();i++)
            {
                if(datalist.get(i).getPname().indexOf(search)!=-1)
                {
                    datalist1.add(datalist.get(i));
                }
            }


            //判断search是否为一个整数

            int pd3=0;
            for(int j=0;j<search.length();j++)
            {
                if(search.charAt(j)<'0'||search.charAt(j)>'9')
                {
                    pd3=1;
                }
            }

            if(pd3==0&&search.length()>0)
            {
                for(int i=0;i<datalist.size();i++) {
                    if (datalist.get(i).getPid() == Integer.valueOf(search)&&(!datalist1.contains(datalist.get(i)))) {
                        datalist1.add(datalist.get(i));
                    }
                }
            }

            if(datalist1.size()==0)
                tishi.setText("没有搜索到任何一个项目哦");

            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_stu);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mem = new memadapter(this,datalist1));
            pd=0;
            search="";
        }

        TextView searchSubmit=(TextView)findViewById(R.id.jiedan);
        searchSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText searchInfo=(EditText)findViewById(R.id.search);
                X_Discovery.search=searchInfo.getText().toString();
                if(pd==0)
                {
                    pd=1;
                }

                finish();
                Intent intent = new Intent(X_Discovery.this, X_Discovery.class);
                startActivity(intent);
            }
        });


    }
    private class memadapter extends RecyclerView.Adapter<memadapter.ViewHolder>{
        private LayoutInflater layoutInflater;
        private Context context;
        public List<project> datalist2;
        public memadapter(Context context,List<project> datalist)
        {
            this.context=context;
            this.layoutInflater=LayoutInflater.from(context);
            this.datalist2=datalist;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView=layoutInflater.inflate(R.layout.x_item,parent,false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.xmname.setText(this.datalist2.get(position).getPname());
            holder.num.setText(""+this.datalist2.get(position).getPnum()+"人");
            holder.xmintroduction.setText(this.datalist2.get(position).getPintroduction());
            holder.tname.setText(this.datalist2.get(position).getPrequire());
        }

        @Override
        public int getItemCount() {
            return this.datalist2.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView xmname;
            private TextView tname;
            private TextView fbdate;
            private TextView num;
            private TextView xmintroduction;
            private TextView school;
            private TextView inum;
            public ViewHolder(View itemView){
                super(itemView);
                xmname=(TextView)itemView.findViewById(R.id.name_of_project);
                tname=(TextView)itemView.findViewById(R.id.name_of_teacher);

                num=(TextView)itemView.findViewById(R.id.number_of_member);
                xmintroduction=(TextView)itemView.findViewById(R.id.produce_of_project);

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
                        x_5xiangmuxiangqing1.p=datalist2.get(getAdapterPosition());
                        Intent intent = new Intent();
                        intent.setClass(X_Discovery.this, x_5xiangmuxiangqing1.class);
                        X_Discovery.this.startActivity(intent);
                    }
                });
            }
        }
    }
}
