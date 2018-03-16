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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by frank on 2017/11/28.
 */

public class x_14yaoqingpingjiaActivity extends AppCompatActivity {
    public static user u;
    private Handler handler=new Handler();
    public static Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_14yaoqingpingjia);
        u=x_6shouye1Activity.u1;
        final EditText pname1=(EditText) findViewById(R.id.name);
        final EditText tid=(EditText) findViewById(R.id.editText5);
        final String uid2=u.getUid();

        Button submit=(Button)findViewById(R.id.button22);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Pattern pattern = Pattern.compile("^[1-9]\\d*$");
                Matcher matcher = pattern.matcher(tid.getText());
                boolean bl = true;

                if(pname1.getText().length()==0){
                    new AlertDialog.Builder(x_14yaoqingpingjiaActivity.this)
                            .setTitle("错误")
                            .setMessage("项目名不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(pname1.getText().length()>20){
                    new AlertDialog.Builder(x_14yaoqingpingjiaActivity.this)
                            .setTitle("错误")
                            .setMessage("项目名不能大于20个字")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(tid.getText().length()==0){
                    new AlertDialog.Builder(x_14yaoqingpingjiaActivity.this)
                            .setTitle("错误")
                            .setMessage("老师手机号不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(tid.getText().length()!=11){
                    new AlertDialog.Builder(x_14yaoqingpingjiaActivity.this)
                            .setTitle("错误")
                            .setMessage("老师手机号应为11位数")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(matcher.matches()!=bl){
                    new AlertDialog.Builder(x_14yaoqingpingjiaActivity.this)
                            .setTitle("错误")
                            .setMessage("应在此输入大于0的正整数")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else{
                    if(pname1.getText().toString().contains("'")){
                        String s =pname1.getText().toString().replace("'","‘");
                        pname1.setText(s);
                    }
                    if(tid.getText().toString().contains("'")){
                        String s =tid.getText().toString().replace("'","‘");
                        tid.setText(s);
                    }
                    pname1.setText(x_13gerenziliao1.guolv(pname1.getText().toString()));
                    new AlertDialog.Builder(x_14yaoqingpingjiaActivity.this)
                            .setTitle("确认")
                            .setMessage("确定要提交吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String pname=pname1.getText().toString();
                                    String uid1=tid.getText().toString();
                                    String url1="http://112.74.176.171:8080/xmb/servlet/testUserServlet?uid="+uid1;
                                    testteathread thread=new testteathread(url1,handler,x_14yaoqingpingjiaActivity.this,pname,tid,u,uid2);
                                    thread.start();
                                    try {
                                        thread.join();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    //order=0，邀请评价，order=1,申请项目
                                }

                            })
                            .setNegativeButton("取消",null)
                            .show();
                }
            }
        });
    }
}
