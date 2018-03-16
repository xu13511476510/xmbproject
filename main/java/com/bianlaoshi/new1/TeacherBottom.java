package com.bianlaoshi.new1;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;


/**
 * Created by zhuangyuan on 2017/11/18.
 */

public class TeacherBottom extends RelativeLayout {
    public RelativeLayout zhuye,faxian,wode;
    public TeacherBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.bottomtitle,this);
        zhuye=(RelativeLayout)findViewById(R.id.homepage);
        faxian=(RelativeLayout)findViewById(R.id.find);
        wode=(RelativeLayout) findViewById(R.id.mine);
        zhuye.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                turnClearTile();
//                zhuye.setTextColor(Color.rgb(209,208,208));
                Intent intent=new Intent(getContext(),l_1shouye.class);
                getContext().startActivity(intent);
            }
        });
        wode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),L_Mine.class);
                getContext().startActivity(intent);
            }
        });
        faxian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String data="";
                L_Discovery.pd=0;
                Intent intent=new Intent(getContext(),L_Discovery.class);
                //intent.putExtra("dd",data);
                getContext().startActivity(intent);
            }
        });

    }
//    private void turnClearTile(){
//        zhuye.setTextColor(Color.rgb(45,45,45));
//        wode.setTextColor(Color.rgb(45,45,45));
//        cailan.setTextColor(Color.rgb(45,45,45));
//        shequ.setTextColor(Color.rgb(45,45,45));
//    }
}

