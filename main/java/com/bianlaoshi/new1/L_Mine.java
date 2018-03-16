package com.bianlaoshi.new1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by zhuangyuan on 2017/11/18.
 */

public class L_Mine extends AppCompatActivity {
    private LinearLayout gerenziliao;
    private LinearLayout pingjiaxuesheng;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_6mine_teacher);

        gerenziliao=(LinearLayout) findViewById(R.id.mine_information1);
        gerenziliao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(L_Mine.this, l_8gerenziliao.class);
                startActivity(intent);
            }
        });
        pingjiaxuesheng=(LinearLayout) findViewById(R.id.myreview1);
        pingjiaxuesheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(L_Mine.this, l_10Activity.class);
                startActivity(intent);
            }
        });

        //点击消息按钮
        ImageView view1=(ImageView)findViewById(R.id.imageView20);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent=new Intent(L_Mine.this,TeacherMessage.class);
                startActivity(intent);
            }
        });
    }
}
