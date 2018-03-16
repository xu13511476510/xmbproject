package com.bianlaoshi.new1;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

/**
 * Created by frank on 2017/12/23.
 */

public class x_10review_teacherActivity extends AppCompatActivity {
    private Handler handler=new Handler();
    private static Context context;
    public static user u;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_10review_teacher);
        u=x_6shouye1Activity.u1;
        final RatingBar rb=(RatingBar)findViewById(R.id.star);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

           @Override
           public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
               Toast.makeText(x_10review_teacherActivity.this,"" + arg1, Toast.LENGTH_SHORT).show();
            }
            });

        Button sub=(Button)findViewById(R.id.button13);
        final EditText phone=(EditText)findViewById(R.id.editText13);
        sub.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(phone.getText().length()!=11){
                    new AlertDialog.Builder(x_10review_teacherActivity.this)
                            .setTitle("错误")
                            .setMessage("手机号位数应是11位")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else{
                    new AlertDialog.Builder(x_10review_teacherActivity.this)
                            .setTitle("确定")
                            .setMessage("确定提交吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    float f=rb.getRating();
                                    String phonein=phone.getText().toString();
                                    String url1="http://112.74.176.171:8080/xmb/servlet/testUserServlet?uid="+phonein;
                                    Thread thread=new testteathread1(url1,handler,f,phone,u,x_10review_teacherActivity.this);
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
            }
        });

    }
}
