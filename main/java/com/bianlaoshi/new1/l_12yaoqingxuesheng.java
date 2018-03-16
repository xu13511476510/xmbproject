package com.bianlaoshi.new1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by frank on 2017/11/27.
 */

public class l_12yaoqingxuesheng extends AppCompatActivity {
    public static student s;
    public static user u1;
    private Handler handler=new Handler();
    private List<studentcomment> scList=new ArrayList<studentcomment>();
    private List<studentreview> srList=new ArrayList<studentreview>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_12yaoqingxuesheng);


        ImageButton  invite=(ImageButton) findViewById(R.id.imageButtonyaoqingx);
        ImageButton jubao=(ImageButton) findViewById(R.id.imageButtonjubaox);
        this.u1=l_1shouye.u1;

        RecyclerView r1=(RecyclerView)findViewById(R.id.r1);
        r1.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        r1.setAdapter(new MemberAdapter(this));


        String url="http://112.74.176.171:8080/xmb/servlet/l_12Servlet?sid="+s.getSid();
        l_12yaoqingxueshengThread thread=new l_12yaoqingxueshengThread(url,this,handler,scList);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String url1="http://112.74.176.171:8080/xmb/servlet/returnReviewServlet?sid="+s.getSid();
        studentReviewThread srt=new studentReviewThread(url1,this,srList);
        srt.start();
        try {
            srt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        invite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(l_12yaoqingxuesheng.this)
                        .setTitle("确认")
                        .setMessage("确定邀请该名学生吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                chooseStudentActivity.s1=s;
                                chooseStudentActivity.u1=u1;
                                Intent intent=new Intent(l_12yaoqingxuesheng.this,chooseStudentActivity.class);
                                l_12yaoqingxuesheng.this.startActivity(intent);

                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }
        });

        jubao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(l_12yaoqingxuesheng.this)
                        .setTitle("确认")
                        .setMessage("确定举报该名学生吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url="http://112.74.176.171:8080/xmb/servlet/jubaoServlet?order=0&uid="+s.getSid();
                                //order: 0 user 1 project
                                new jubaoThread(url).start();
                                new AlertDialog.Builder(l_12yaoqingxuesheng.this)
                                        .setMessage("举报成功")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent=new Intent(l_12yaoqingxuesheng.this,L_Discovery.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }
        });



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
            if(viewType==0)
            {
                View itemView = layoutInflater.inflate(R.layout.header, parent, false);
                return new ViewHolder(itemView);
            }
            else if(viewType==1)
            {
                View itemView = layoutInflater.inflate(R.layout.l_12item, parent, false);
                return new SecondViewHolder(itemView);
            }
            else if(viewType==2)
            {
                View itemView = layoutInflater.inflate(R.layout.l_12item, parent, false);
                return new ThirdViewHolder(itemView);
            }

            else if(viewType==3)
            {
                View itemView = layoutInflater.inflate(R.layout.divider1, parent, false);
                return new FourthViewHolder(itemView);
            }
            else if(viewType==5)
            {
                View itemView = layoutInflater.inflate(R.layout.divider3, parent, false);
                return new FourthViewHolder(itemView);
            }
            else if(viewType==6)
            {
                View itemView = layoutInflater.inflate(R.layout.divider4, parent, false);
                return new FourthViewHolder(itemView);
            }
            else
            {
                View itemView = layoutInflater.inflate(R.layout.divider, parent, false);
                return new FourthViewHolder(itemView);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (getItemViewType(position))
            {
                case 0:  //姓名学校特长等
                    ViewHolder viewHolder = (ViewHolder) holder;
                    viewHolder.mname.setText(s.getSname());
                    viewHolder.school.setText(s.getSschool());
                    viewHolder.strong.setText(s.getSadvantage());
                    viewHolder.else1.setText(s.getSintroduction());
                    break;
                case 1:    //老师评价
                    SecondViewHolder secondViewHolder = (SecondViewHolder) holder;
                    secondViewHolder.mname.setText(scList.get(position-2).getPname());
                    secondViewHolder.zhize.setText(scList.get(position-2).getSctag());
                    secondViewHolder.pingjia.setText(scList.get(position-2).getSccomment());
                    break;
                case 2:    //自我评价
                    ThirdViewHolder holder2 = (ThirdViewHolder) holder;
                    holder2.mname.setText(srList.get(position-scList.size()-3).getSrpname());
                    holder2.zhize.setText(srList.get(position-scList.size()-3).getSrjob());
                    holder2.pingjia.setText(srList.get(position-scList.size()-3).getSrreview());
                    break;
                case 3: //老师评价分割线
                    break;

                case 4: //自我评价分割线
                    break;
                case 5: //老师评价暂无分割线
                    break;

                case 6:
                    break;

            }
        }

        @Override
        public int getItemCount() {
            return scList.size()+srList.size()+3;
        }

        public int getItemViewType(int position)
        {
            if(position==0)//姓名学校特长等
            {
                return 0;
            }
            else if(position==1&&scList.size()!=0) //老师评价分割线
            {
                return 3;
            }
            else if(position==1&&scList.size()==0) //老师评价分割线
            {
                return 5;
            }


            else if(position<=scList.size()+1&&position>1)//老师评价
            {
                return 1;
            }
            else  if(position==scList.size()+2&&srList.size()!=0)//自我评价分割线
            {
                return 4;
            }
            else  if(position==scList.size()+2&&srList.size()==0)//自我评价暂无分割线
            {
                return 6;
            }
            else   //自我评价
            {
                return 2;
            }

        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView mname;
            private TextView school;
            private TextView strong;
            private TextView else1;
            public ViewHolder(View itemView){
                super(itemView);
                mname=(TextView)itemView.findViewById(R.id.textView11);
                school=(TextView)itemView.findViewById(R.id.textView12);
                strong=(TextView)itemView.findViewById(R.id.textView58);
                else1=(TextView)itemView.findViewById(R.id.textView13);
            }
        }

        private class SecondViewHolder extends ViewHolder {
            private TextView mname;
            private TextView zhize;
            private TextView pingjia;

            public SecondViewHolder(View itemView){
                super(itemView);
                mname=(TextView)itemView.findViewById(R.id.textView141);
                zhize=(TextView)itemView.findViewById(R.id.textView161);
                pingjia=(TextView)itemView.findViewById(R.id.textView181);
            }
        }
        private class ThirdViewHolder extends ViewHolder {
            private TextView mname;
            private TextView zhize;
            private TextView pingjia;

            public ThirdViewHolder(View itemView){
                super(itemView);
                mname=(TextView)itemView.findViewById(R.id.textView141);
                zhize=(TextView)itemView.findViewById(R.id.textView161);
                pingjia=(TextView)itemView.findViewById(R.id.textView181);
            }
        }

        private class FourthViewHolder extends ViewHolder {
            private Button b1;

            public FourthViewHolder(View itemView){
                super(itemView);
               b1= (Button) findViewById(R.id.button8);
            }
        }

       /* private class FifthViewHolder extends ViewHolder {
            private Button b2;

            public FifthViewHolder(View itemView){
                super(itemView);
                b2= (Button) findViewById(R.id.button8);
            }
        }*/
    }


}
