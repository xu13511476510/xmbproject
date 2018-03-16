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

public class StudentBottom extends RelativeLayout {
    public RelativeLayout zhuye, faxian, wode;

    public StudentBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.x_bottom, this);
        zhuye = (RelativeLayout) findViewById(R.id.homepage);
        faxian = (RelativeLayout) findViewById(R.id.find);
        wode = (RelativeLayout) findViewById(R.id.mine);
        zhuye.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                turnClearTile();
//                zhuye.setTextColor(Color.rgb(209,208,208));
                Intent intent = new Intent(getContext(), x_6shouye1Activity.class);
                getContext().startActivity(intent);
            }
        });
        wode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), X_Mine.class);
                getContext().startActivity(intent);
            }
        });
        faxian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "";
                X_Discovery.pd=0;
                Intent intent = new Intent(getContext(), X_Discovery.class);
                //intent.putExtra("dd",data);
                getContext().startActivity(intent);
            }
        });

    }
}
