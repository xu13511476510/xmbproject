package com.bianlaoshi.new1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
/**
 * Created by zhuangyuan on 2017/11/1.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{
    private static List<student> mStudentList;
    private static Context context;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView studentImage;
        TextView studentName;
        TextView studentSchool;
        TextView studentAdvantage;

        public ViewHolder(View view) {
            super(view);
            studentImage = (ImageView) view.findViewById(R.id.stu_image);
            studentName = (TextView) view.findViewById(R.id.stu_name);
            studentSchool=(TextView)view.findViewById(R.id.stu_school);
            studentAdvantage=(TextView)view.findViewById(R.id.text_advantage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, l_12yaoqingxuesheng.class);
                    context.startActivity(intent);
                    l_12yaoqingxuesheng.s=mStudentList.get(getAdapterPosition());
                }
            });
        }
    }

    public StudentAdapter(List<student> studentlist,Context context){
        mStudentList=studentlist;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.stu_item_of_find,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        student student=mStudentList.get(position);
        holder.studentName.setText(student.getSname());
        holder.studentAdvantage.setText(student.getSadvantage());
        holder.studentSchool.setText(student.getSschool());
    }
    @Override
    public int getItemCount()
    {
        return mStudentList.size();
    }
}

