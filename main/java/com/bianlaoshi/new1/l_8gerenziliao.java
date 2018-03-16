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

public class l_8gerenziliao extends AppCompatActivity {
    public static user u1;
    private EditText tname;
    private TextView tid;
    private TextView tpoint;
    private EditText tmail;
    private TextView tschool;
    private EditText tintroduction;
    private Handler handler=new Handler();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_8gerenziliao);
        //初始化控件
        tname=(EditText) findViewById(R.id.editText4);
        tid=(TextView) findViewById(R.id.textView50);
        tpoint=(TextView)findViewById(R.id.textView52);
        tmail=(EditText) findViewById(R.id.editText9);
        tschool=(TextView) findViewById(R.id.editTextSchool);
        tintroduction=(EditText) findViewById(R.id.editTextIntroduction);
        Button submit=(Button)findViewById(R.id.button24);
        final Spinner choose=(Spinner)findViewById(R.id.chooseTextSchool);

        //开启线程
        String url="http://112.74.176.171:8080/xmb/servlet/l_8gerenziliaoServlet?tid=" +u1.getUid().toString();
        l_8gerenziliaoThread thread=new l_8gerenziliaoThread(url,this,handler,tname, tid,  tpoint,tmail,  tschool, tintroduction);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        choose.setSelection(0,true);
        choose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] spingarr=getResources().getStringArray(R.array.spingarr);
                if(position!=0)
                tschool.setText(spingarr[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Pattern pattern = Pattern.compile("^[A-Za-z0-9]+([._\\-]*[A-Za-z0-9])*@([A-Za-z0-9]+[-a-z0-9A-Z]*[A-Za-z0-9]+.){1,63}[A-Za-z0-9]+$");
                Matcher matcher = pattern.matcher(tmail.getText());
                boolean b1 = true;
                int k1=0;
                if(tmail.getText().toString().equals("未填写"))
                    k1=1;
                if(tname.getText().length()==0){
                    new AlertDialog.Builder(l_8gerenziliao.this)
                            .setTitle("错误")
                            .setMessage("至少把姓名填上哦")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(tname.getText().length()>10){
                    new AlertDialog.Builder(l_8gerenziliao.this)
                            .setTitle("错误")
                            .setMessage("姓名长度不能超过10个汉字/字母")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(matcher.matches()!=b1&&tmail.getText().length()!=0&&k1==0){
                    new AlertDialog.Builder(l_8gerenziliao.this)
                            .setTitle("错误")
                            .setMessage("请输入正确邮箱格式")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(tschool.getText().length()>15){
                    new AlertDialog.Builder(l_8gerenziliao.this)
                            .setTitle("错误")
                            .setMessage("所属院校不能超过15个汉字/字母")

                            .setPositiveButton("确定",null)
                            .show();
                }
                else{
                    if(tname.getText().toString().contains("'")){
                        String s =tname.getText().toString().replace("'","‘");
                        tname.setText(s);
                    }
                    if(tmail.getText().toString().contains("'")){
                        String s =tmail.getText().toString().replace("'","‘");
                        tmail.setText(s);
                    }
                    if(tschool.getText().toString().contains("'")){
                        String s =tschool.getText().toString().replace("'","‘");
                        tschool.setText(s);
                    }
                    if(tintroduction.getText().toString().contains("'")){
                        String s =tintroduction.getText().toString().replace("'","‘");
                        tintroduction.setText(s);
                    }
                    tname.setText(x_13gerenziliao1.guolv(tname.getText().toString()));
                    tschool.setText(x_13gerenziliao1.guolv(tschool.getText().toString()));
                    tintroduction.setText(x_13gerenziliao1.guolv(tintroduction.getText().toString()));
                    new AlertDialog.Builder(l_8gerenziliao.this)
                            .setTitle("确定")
                            .setMessage("确定提交吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url="http://112.74.176.171:8080/xmb/servlet/updateZiliaoServlet?order=1";
                                    String uid=tid.getText().toString();
                                    String name=tname.getText().toString();
                                    try {
                                        name= URLEncoder.encode(name,"utf-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    String mail=tmail.getText().toString();
                                    try {
                                        mail= URLEncoder.encode(mail,"utf-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    String school=tschool.getText().toString();
                                    try {
                                        school= URLEncoder.encode(school,"utf-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    String introduction=tintroduction.getText().toString();
                                    try {
                                        introduction= URLEncoder.encode(introduction,"utf-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    String a=tschool.getText().toString();
                                    try {
                                        a=URLEncoder.encode(a,"utf-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }

                                    url=url+"&name="+name+"&mail="+mail+"&school="+a+"&introduction="+introduction+"&uid="+uid;
                                    new updateZiliaoThread(url).start();
                                    new AlertDialog.Builder(l_8gerenziliao.this)
                                            .setMessage("提交成功")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent=new Intent(l_8gerenziliao.this,L_Mine.class);
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
