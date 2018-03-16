package com.bianlaoshi.new1;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created by frank on 2017/12/23.
 */

public class zhaohuimimaActivity extends AppCompatActivity {
    private Handler handler=new Handler();
    public static int[] a = new int[1];

    private String phoneNo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_3zhaohuimima);

        final EditText phonein=(EditText)findViewById(R.id.shoujihaoma);
        final EditText testin=(EditText)findViewById(R.id.yanzhengma);
        final EditText pass1=(EditText)findViewById(R.id.shezhimima);
        final EditText pass2=(EditText)findViewById(R.id.querenmima);
        ImageButton send=(ImageButton)findViewById(R.id.imageButton6);
        Button lssubmit=(Button)findViewById(R.id.lsbuttonzhuce);
        final TextView test=(TextView)findViewById(R.id.textView64);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        final Date[] start = {null};

        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(phonein.getText().length()==0){
                    new AlertDialog.Builder(zhaohuimimaActivity.this)
                            .setTitle("错误")
                            .setMessage("手机号码不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(phonein.getText().length()!=11){
                    new AlertDialog.Builder(zhaohuimimaActivity.this)
                            .setTitle("错误")
                            .setMessage("手机号码应是11位")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else{
                    //防止不停点击
                    Date now=new Date();

                    if(start[0] !=null && (now.getTime()- start[0].getTime())<=180000)
                    {
                        test.setText("验证码正在传输，请稍后");
                    }
                    else
                    {
                        //生成验证码
                        Random r=new Random();
                        a[0] = abs(r.nextInt())%100000;

                        String phone=phonein.getText().toString();
                        sendTextThread thread=new sendTextThread(a[0],phone);
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        phoneNo=phone;
                        test.setText("验证码已发送");
                        start[0] =now;
                    }
                }

            }
        });

        lssubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //老师注册
                String yzm=testin.getText().toString();
                String yzm1=Integer.toString(a[0]);
                if(yzm.contains(yzm1)&&yzm1.contains(yzm))
                {
                    String passA=pass1.getText().toString();
                    String passB=pass2.getText().toString();
                    if(phonein.getText().length()==0){
                        new AlertDialog.Builder(zhaohuimimaActivity.this)
                                .setTitle("错误")
                                .setMessage("手机号码不能为空")
                                .setPositiveButton("确定",null)
                                .show();
                    }
                    else if(phonein.getText().length()!=11){
                        new AlertDialog.Builder(zhaohuimimaActivity.this)
                                .setTitle("错误")
                                .setMessage("手机号码应是11位")
                                .setPositiveButton("确定",null)
                                .show();
                    }
                    else if(passA.length()==0){
                        new AlertDialog.Builder(zhaohuimimaActivity.this)
                                .setTitle("错误")
                                .setMessage("密码不能为空")
                                .setPositiveButton("确定",null)
                                .show();
                    }
                    else if(passA.length()<6){
                        new AlertDialog.Builder(zhaohuimimaActivity.this)
                                .setTitle("错误")
                                .setMessage("密码位数应大于等于6")
                                .setPositiveButton("确定",null)
                                .show();
                    }
                    else if(passA.length()>20){
                        new AlertDialog.Builder(zhaohuimimaActivity.this)
                                .setTitle("错误")
                                .setMessage("密码位数不能大于20")
                                .setPositiveButton("确定",null)
                                .show();
                    }
                    else {
                        if (passA.contains("'")) {
                            String s = pass1.getText().toString().replace("'", "‘");
                            pass1.setText(s);
                        }
                        if (passB.contains("'")) {
                            String s = pass2.getText().toString().replace("'", "‘");
                            pass2.setText(s);
                        }
                        if (testin.getText().toString().contains("'")) {
                            String s = testin.getText().toString().replace("'", "‘");
                            testin.setText(s);
                        }
                        if(passA.contains(passB)&&passB.contains(passA))
                        {
                            String phone=phonein.getText().toString();
                            //验证用户是否存在
                            String url1="http://112.74.176.171:8080/xmb/servlet/testUserServlet?uid="+phone;
                            Thread thread=new zhaohuimimaThread(url1,handler,phonein,pass1,pass2,testin,test,1,zhaohuimimaActivity.this,phoneNo);
                            thread.start();
                            try {
                                thread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            //弹窗：密码不一致
                            new AlertDialog.Builder(zhaohuimimaActivity.this)
                                    .setTitle("错误")
                                    .setMessage("密码不一致")
                                    .setPositiveButton("确定",null)
                                    .show();
                        }
                    }
                }
                else
                {
                    new AlertDialog.Builder(zhaohuimimaActivity.this)
                            .setTitle("错误")
                            .setMessage("验证码错误")
                            .setPositiveButton("确定",null)
                            .show();
                    //弹窗：验证码错误
                }
            }
        });
    }
}
