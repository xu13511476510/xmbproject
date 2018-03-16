package com.bianlaoshi.new1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by frank on 2017/11/27.
 */

public class l_5fabuxiangmuActivity extends AppCompatActivity {
    public static user u;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_5fabuxiangmu);

        u=l_1shouye.u1;
        final EditText pname=(EditText) findViewById(R.id.editText4);
        final EditText pnum=(EditText) findViewById(R.id.editText7);
        final EditText prequire=(EditText) findViewById(R.id.editText8);
        final EditText pintroduction=(EditText) findViewById(R.id.editText9);
        final EditText pelse=(EditText) findViewById(R.id.editText3);
        Button submit=(Button)findViewById(R.id.button13);
        Button cancel=(Button)findViewById(R.id.button6);

        cancel.setText("退出");

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Pattern pattern = Pattern.compile("^[1-9]\\d*$");
                Matcher matcher = pattern.matcher(pnum.getText());
                boolean bl = true;
                if(pname.getText().length()==0){
                    new AlertDialog.Builder(l_5fabuxiangmuActivity.this)
                            .setTitle("错误")
                            .setMessage("项目名不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(pname.getText().length()>20){
                    new AlertDialog.Builder(l_5fabuxiangmuActivity.this)
                            .setTitle("错误")
                            .setMessage("项目名不能超过20个汉字或字母")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(pnum.getText().length()==0){
                    new AlertDialog.Builder(l_5fabuxiangmuActivity.this)
                            .setTitle("错误")
                            .setMessage("招收人数不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(matcher.matches()!=bl){
                    new AlertDialog.Builder(l_5fabuxiangmuActivity.this)
                            .setTitle("错误")
                            .setMessage("应在此输入大于0的数字")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(pintroduction.getText().length()==0){
                    new AlertDialog.Builder(l_5fabuxiangmuActivity.this)
                            .setTitle("错误")
                            .setMessage("项目简介不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else {
                    if(pname.getText().toString().contains("'")){
                        String s =pname.getText().toString().replace("'","‘");
                        pname.setText(s);
                    }
                    if(pnum.getText().toString().contains("'")){
                        String s =pnum.getText().toString().replace("'","‘");
                        pnum.setText(s);
                    }
                    if(prequire.getText().toString().contains("'")){
                        String s =prequire.getText().toString().replace("'","‘");
                        prequire.setText(s);
                    }
                    if(pintroduction.getText().toString().contains("'")){
                        String s =pintroduction.getText().toString().replace("'","‘");
                        pintroduction.setText(s);
                    }
                    if(pelse.getText().toString().contains("'")){
                        String s =pelse.getText().toString().replace("'","‘");
                        pelse.setText(s);
                    }
                    pname.setText(x_13gerenziliao1.guolv(pname.getText().toString()));
                    prequire.setText(x_13gerenziliao1.guolv(prequire.getText().toString()));
                    pintroduction.setText(x_13gerenziliao1.guolv(pintroduction.getText().toString()));
                    pelse.setText(x_13gerenziliao1.guolv(pelse.getText().toString()));
                    new AlertDialog.Builder(l_5fabuxiangmuActivity.this)
                            .setTitle("确定")
                            .setMessage("确定要提交吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url="http://112.74.176.171:8080/xmb/servlet/l_5fabuxiangmuServlet";
                                    new l_5fabuxiangmuThread(url,u.getUid(),pname,pnum,prequire,pintroduction,pelse).start();
                                    new AlertDialog.Builder(l_5fabuxiangmuActivity.this)
                                            .setMessage("提交成功")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                    Intent intent=new Intent(l_5fabuxiangmuActivity.this,l_1shouye.class);
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

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(l_5fabuxiangmuActivity.this)
                        .setTitle("确定")
                        .setMessage("确定退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(l_5fabuxiangmuActivity.this)
                                        .setMessage("已退出")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                                Intent intent=new Intent(l_5fabuxiangmuActivity.this,l_1shouye.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }
        });

        Button chakan=(Button)findViewById(R.id.button28);
        chakan.getBackground().setAlpha(0);
        chakan.setText("");

        ImageButton tuichu=(ImageButton)findViewById(R.id.imageButton10);
        tuichu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(l_5fabuxiangmuActivity.this)
                        .setTitle("确定")
                        .setMessage("确定退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(l_5fabuxiangmuActivity.this)
                                        .setMessage("已退出")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                                Intent intent=new Intent(l_5fabuxiangmuActivity.this,l_1shouye.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }
        });


    }
}