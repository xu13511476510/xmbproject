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

public class X_Mine extends AppCompatActivity {
    private LinearLayout gerenziliao;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_8wode);

        gerenziliao=(LinearLayout) findViewById(R.id.mine_information);
        gerenziliao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(X_Mine.this, x_13gerenziliao1.class);
                startActivity(intent);
            }
        });
        LinearLayout yaoqingpingjia=(LinearLayout)findViewById(R.id.yaoqingpingjia);
        yaoqingpingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(X_Mine.this, x_14yaoqingpingjiaActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout ziwopingjia=(LinearLayout)findViewById(R.id.ziwopingjia);
        ziwopingjia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(X_Mine.this, x_11self_reviewActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout pingjialaoshi=(LinearLayout)findViewById(R.id.pjls);
        pingjialaoshi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(X_Mine.this, x_10review_teacherActivity.class);
                startActivity(intent);
            }
        });

        ImageView view1=(ImageView)findViewById(R.id.imageView34);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent=new Intent(X_Mine.this,TeacherMessage.class);
                startActivity(intent);
            }
        });
    }

}