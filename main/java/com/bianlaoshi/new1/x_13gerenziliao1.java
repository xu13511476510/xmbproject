package com.bianlaoshi.new1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhuangyuan on 2017/11/19.
 */

public class x_13gerenziliao1 extends AppCompatActivity {
    private EditText sname;
    private TextView sid;
    private EditText smail;
    private TextView sschool;
    private EditText sintroduction;
    private EditText sadvantage;
    private Spinner choose;
    private Handler handler=new Handler();
    private int p=0;
    public static user u1;

    public static String guolv(String s){
        if(s.contains("中共"))
            s=s.replace("中共","**");
        if(s.contains("民主"))
            s=s.replace("民主","**");
        if(s.contains("共产党"))
            s=s.replace("共产党","***");
        if(s.contains("民权"))
            s=s.replace("民权","**");
        if(s.contains("中国共产党"))
            s=s.replace("中国共产党","*****");
        if(s.contains("胡锦涛"))
            s=s.replace("胡锦涛","***");
        if(s.contains("温家宝"))
            s=s.replace("温家宝","***");
        if(s.contains("习近平"))
            s=s.replace("习近平","***");
        if(s.contains("李克强"))
            s=s.replace("李克强","***");
        if(s.contains("邓小平"))
            s=s.replace("邓小平","***");
        if(s.contains("毛泽东"))
            s=s.replace("毛泽东","***");
        if(s.contains("反共"))
            s=s.replace("反共","**");
        if(s.contains("反党"))
            s=s.replace("反党","**");
        if(s.contains("反国家"))
            s=s.replace("反国家","***");
        if(s.contains("游行"))
            s=s.replace("游行","**");
        if(s.contains("集会"))
            s=s.replace("集会","**");
        if(s.contains("强奸"))
            s=s.replace("强奸","**");
        if(s.contains("走私"))
            s=s.replace("走私","**");
        if(s.contains("强暴"))
            s=s.replace("强暴","**");
        if(s.contains("轮奸"))
            s=s.replace("轮奸","**");
        if(s.contains("奸杀"))
            s=s.replace("奸杀","**");
        if(s.contains("杀死"))
            s=s.replace("杀死","**");
        if(s.contains("套套"))
            s=s.replace("套套","**");
        if(s.contains("日"))
            s=s.replace("日","*");
        if(s.contains("操"))
            s=s.replace("操","*");
        if(s.contains("草泥马"))
            s=s.replace("草泥马","***");
        if(s.contains("草你妈"))
            s=s.replace("草你妈","***");
        if(s.contains("人民币"))
            s=s.replace("人民币","***");
        if(s.contains("fuck"))
            s=s.replace("fuck","****");
        if(s.contains("高潮"))
            s=s.replace("高潮","**");
        if(s.contains("傻逼"))
            s=s.replace("傻逼","**");
        if(s.contains("妈逼"))
            s=s.replace("妈逼","**");
        return s;
    }
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_13gerenziliao1);
        //配置控件
        sname=(EditText) findViewById(R.id.editText4);
        sid=(TextView) findViewById(R.id.textView50);
        smail=(EditText) findViewById(R.id.editText9);
        sschool=(TextView) findViewById(R.id.editTextSchool);
        sintroduction=(EditText) findViewById(R.id.editTextIntroduction);
        sadvantage=(EditText)findViewById(R.id.editText15);
        Button submit=(Button)findViewById(R.id.button25);
        choose=(Spinner)findViewById(R.id.chooseTextSchool);
        choose.setSelection(0, true);
        choose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] spingarr = getResources().getStringArray(R.array.spingarr);
                if(position!=0)
                sschool.setText(spingarr[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //开启线程
        String url="http://112.74.176.171:8080/xmb/servlet/x_13gerenziliao1Servlet?sid=" +u1.getUid().toString();
        x_13gerenziliao1Thread thread=new x_13gerenziliao1Thread(url,handler, sname,
                sid,
                smail,
                sschool,
                sintroduction,
                sadvantage
        );
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Pattern pattern = Pattern.compile("^[A-Za-z0-9]+([._\\-]*[A-Za-z0-9])*@([A-Za-z0-9]+[-a-z0-9A-Z]*[A-Za-z0-9]+.){1,63}[A-Za-z0-9]+$");
                Matcher matcher = pattern.matcher(smail.getText());
                boolean b1 = true;
                int k=0,k1=0;
                String[] aa=sadvantage.getText().toString().split(",");
                if(smail.getText().toString().equals("未填写"))
                    k1=1;
                for(int i=0;i<aa.length-1;i++){
                    for(int j=i+1;j<aa.length;j++){
                        if(aa[i].equals(aa[j]))
                            k=1;
                    }
                }
                if(sname.getText().length()==0){
                    new AlertDialog.Builder(x_13gerenziliao1.this)
                            .setTitle("错误")
                            .setMessage("至少把姓名填上哦")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(sname.getText().length()>10){
                    new AlertDialog.Builder(x_13gerenziliao1.this)
                            .setTitle("错误")
                            .setMessage("姓名长度不能超过10个汉字/字母")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(matcher.matches()!=b1&&smail.getText().length()!=0&&k1==0){
                        new AlertDialog.Builder(x_13gerenziliao1.this)
                            .setTitle("错误")
                            .setMessage("请填写正确的邮箱地址")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(sschool.getText().length()>15){
                    new AlertDialog.Builder(x_13gerenziliao1.this)
                            .setTitle("错误")
                            .setMessage("所属院校不能超过15个英文字母")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(sadvantage.getText().length()>100){
                    new AlertDialog.Builder(x_13gerenziliao1.this)
                            .setTitle("错误")
                            .setMessage("专长部分不能超过100个汉字/字母")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(k==1){
                    new AlertDialog.Builder(x_13gerenziliao1.this)
                            .setTitle("错误")
                            .setMessage("专长部分每个项不能输入相同内容")
                            .setPositiveButton("确定",null)
                            .show();
                    k=0;
                }
                else{
                    if(sname.getText().toString().contains("'")){
                        String s = sname.getText().toString().replace("'","‘");
                        sname.setText(s);
                    }
                    if(sadvantage.getText().toString().contains("'")){
                        String s =sadvantage.getText().toString().replace("'","‘");
                        sadvantage.setText(s);
                    }
                    if(sintroduction.getText().toString().contains("'")){
                        String s =sintroduction.getText().toString().replace("'","‘");
                        sintroduction.setText(s);
                    }
                    sname.setText(guolv(sname.getText().toString()));
                    sadvantage.setText(guolv(sadvantage.getText().toString()));
                    sintroduction.setText(guolv(sintroduction.getText().toString()));
                    new AlertDialog.Builder(x_13gerenziliao1.this)
                            .setTitle("确定")
                            .setMessage("确定提交吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url="http://112.74.176.171:8080/xmb/servlet/updateZiliaoServlet?order=0";
                                    // 0 学生，1 老师
                                    try {
                                        String uid=sid.getText().toString();

                                        String name=sname.getText().toString();
                                        name= URLEncoder.encode(name,"utf-8");

                                        String mail=smail.getText().toString();
                                        mail= URLEncoder.encode(mail,"utf-8");

                                        String introduction=sintroduction.getText().toString();
                                        introduction= URLEncoder.encode(introduction,"utf-8");

                                        String advantage=sadvantage.getText().toString();
                                        advantage=URLEncoder.encode(advantage,"utf-8");

                                        String a=sschool.getText().toString();
                                        a=URLEncoder.encode(a,"utf-8");
                                        url=url+"&name="+name+"&mail="+mail+"&school="+a+"&introduction="+introduction+"&uid="+uid+"&advantage="+advantage;
                                        new updateZiliaoThread(url).start();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    new AlertDialog.Builder(x_13gerenziliao1.this)
                                            .setMessage("提交成功")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent=new Intent(x_13gerenziliao1.this,X_Mine.class);
                                                    startActivity(intent);
                                                }
                                            })
                                            .show();
                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();
                }
            }
        });
    }
}
