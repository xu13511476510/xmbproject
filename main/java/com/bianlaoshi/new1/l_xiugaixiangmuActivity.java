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
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by frank on 2017/11/30.
 */

public class l_xiugaixiangmuActivity extends AppCompatActivity {
    public static user u;
    public static project p;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_5fabuxiangmu);

        u=l_1shouye.u1;
        final TextView title=(TextView) findViewById(R.id.textView26);
        final EditText pname=(EditText) findViewById(R.id.editText4);
        final EditText pnum=(EditText) findViewById(R.id.editText7);
        final EditText prequire=(EditText) findViewById(R.id.editText8);
        final EditText pintroduction=(EditText) findViewById(R.id.editText9);
        final EditText pelse=(EditText) findViewById(R.id.editText3);
        Button submit=(Button)findViewById(R.id.button13);
        Button cancel=(Button)findViewById(R.id.button6);

        title.setText("修改项目资料");
        pname.setText(p.getPname());
        pnum.setText(""+p.getPnum());
        prequire.setText(p.getPrequire());
        pintroduction.setText(p.getPintroduction());
        pelse.setText(p.getPelse());
        submit.setText("确认修改");

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Pattern pattern = Pattern.compile("^[1-9]\\d*$");
                Matcher matcher = pattern.matcher(pnum.getText());
                boolean bl = true;
                if(pname.getText().length()==0){
                    new AlertDialog.Builder(l_xiugaixiangmuActivity.this)
                            .setTitle("错误")
                            .setMessage("项目名不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(pname.getText().length()>20){
                    new AlertDialog.Builder(l_xiugaixiangmuActivity.this)
                            .setTitle("错误")
                            .setMessage("项目名不能超过20个汉字/字母")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(pnum.getText().length()==0){
                    new AlertDialog.Builder(l_xiugaixiangmuActivity.this)
                            .setTitle("错误")
                            .setMessage("招收人数不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(matcher.matches()!=bl){
                    new AlertDialog.Builder(l_xiugaixiangmuActivity.this)
                            .setTitle("错误")
                            .setMessage("应在此输入大于0的数字")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(pintroduction.getText().length()==0){
                    new AlertDialog.Builder(l_xiugaixiangmuActivity.this)
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
                    new AlertDialog.Builder(l_xiugaixiangmuActivity.this)
                            .setTitle("确定")
                            .setMessage("确定要修改吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url="http://112.74.176.171:8080/xmb/servlet/l_xiugaixiangmuServlet";
                                    new l_xiugaixiangmuThread(url,p.getPid(),pname,pnum,prequire,pintroduction,pelse).start();
                                    new AlertDialog.Builder(l_xiugaixiangmuActivity.this)
                                            .setMessage("修改成功")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                    Intent intent=new Intent(l_xiugaixiangmuActivity.this,l_1shouye.class);
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
                new AlertDialog.Builder(l_xiugaixiangmuActivity.this)
                        .setTitle("确定")
                        .setMessage("确定删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url="http://112.74.176.171:8080/xmb/servlet/shanchuxiangmuServlet?pid="+p.getPid();
                                new sendMessageThread(url).start();
                                new AlertDialog.Builder(l_xiugaixiangmuActivity.this)
                                        .setMessage("已删除")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Intent intent=new Intent(l_xiugaixiangmuActivity.this,l_1shouye.class);
                                                startActivity(intent);
                                                finish();
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
        chakan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                l_xiangmuchengyuanActivity.p=p;
                l_xiangmuchengyuanActivity.pd=0;
                Intent intent=new Intent(l_xiugaixiangmuActivity.this,l_xiangmuchengyuanActivity.class);
                startActivity(intent);
            }
        });


        ImageButton tuichu=(ImageButton)findViewById(R.id.imageButton10);
        tuichu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(l_xiugaixiangmuActivity.this)
                        .setTitle("确定")
                        .setMessage("确定退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(l_xiugaixiangmuActivity.this)
                                        .setMessage("已退出")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                                Intent intent=new Intent(l_xiugaixiangmuActivity.this,l_1shouye.class);
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
