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
 * Created by frank on 2017/11/29.
 */

public class l_10Activity extends AppCompatActivity {
    private Handler handler=new Handler();
    public static Context context;
    public static message1 mess;
    public static int pd=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_10pingjiaxuesheng);
        //View view= LayoutInflater.from(this).inflate(R.layout.amountview, null);

        Button submit=(Button)findViewById(R.id.button23);
        final EditText escsid=(EditText) findViewById(R.id.editText10);
        final EditText escrank=(EditText) findViewById(R.id.etAmount);
        final EditText esctag=(EditText) findViewById(R.id.editText14);
        final EditText esccomment=(EditText) findViewById(R.id.editText11);
        final EditText epname=(EditText) findViewById(R.id.editText12);
        if(pd==1){
            escsid.setText(mess.getMuid1());
            epname.setText(mess.getMtext());
            pd=0;
        }

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String scsid=escsid.getText().toString();
                final String scrank=escrank.getText().toString();
                Pattern pattern = Pattern.compile("^[1-9]\\d*$");
                Matcher matcher = pattern.matcher(escsid.getText());
                boolean b1 = true;
                /*if(estuname.getText().length()==0){
                    new AlertDialog.Builder(l_10Activity.this)
                            .setTitle("错误")
                            .setMessage("学生名不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else */
                if(escsid.getText().length()==0) {
                    new AlertDialog.Builder(l_10Activity.this)
                            .setTitle("错误")
                            .setMessage("手机号不能为空")
                            .setPositiveButton("确定", null)
                            .show();
                }
                else if(matcher.matches()!=b1){
                    new AlertDialog.Builder(l_10Activity.this)
                            .setTitle("错误")
                            .setMessage("请输入正确的手机号")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(escsid.getText().length()!=11){
                    new AlertDialog.Builder(l_10Activity.this)
                            .setTitle("错误")
                            .setMessage("手机号位数应是11位")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(epname.getText().length()==0){
                    new AlertDialog.Builder(l_10Activity.this)
                            .setTitle("错误")
                            .setMessage("项目名不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(epname.getText().length()>50){
                    new AlertDialog.Builder(l_10Activity.this)
                            .setTitle("错误")
                            .setMessage("项目名不能超过50个汉字/字母")
                            .setPositiveButton("确定",null)
                            .show();
                }
                /*else if(scrank==null){
                    new AlertDialog.Builder(l_10Activity.this)
                            .setTitle("错误")
                            .setMessage("排名不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(matcher1.matches()!=b1){
                    new AlertDialog.Builder(l_10Activity.this)
                            .setTitle("错误")
                            .setMessage("排名请输入正整数")
                            .setPositiveButton("确定",null)
                            .show();
                    ceshi.setText(scrank);
                }*/
                else if(esctag.getText().length()==0){
                    new AlertDialog.Builder(l_10Activity.this)
                            .setTitle("错误")
                            .setMessage("职责不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(esccomment.getText().length()==0){
                    new AlertDialog.Builder(l_10Activity.this)
                            .setTitle("错误")
                            .setMessage("评价不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else{
                    if(epname.getText().toString().contains("'")){
                        String s =epname.getText().toString().replace("'","‘");
                        epname.setText(s);
                    }
                    if(esctag.getText().toString().contains("'")){
                        String s =esctag.getText().toString().replace("'","‘");
                        esctag.setText(s);
                    }
                    if(esccomment.getText().toString().contains("'")){
                        String s =esccomment.getText().toString().replace("'","‘");
                        esccomment.setText(s);
                    }
                    epname.setText(x_13gerenziliao1.guolv(epname.getText().toString()));
                    esctag.setText(x_13gerenziliao1.guolv(esctag.getText().toString()));
                    esccomment.setText(x_13gerenziliao1.guolv(esccomment.getText().toString()));
                    new AlertDialog.Builder(l_10Activity.this)
                            .setTitle("确定")
                            .setMessage("确定提交吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String id=escsid.getText().toString();
                                    String url1="http://112.74.176.171:8080/xmb/servlet/testUserServlet?uid="+id;
                                    teststuthread thread1=new teststuthread(url1,handler,esccomment,epname,scsid,escrank,esctag,l_10Activity.this);
                                    thread1.start();
                                    try {
                                        thread1.join();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    //ceshi.setText("启动线程后");
                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();
                }
            }
        });
    }
}
