package com.bianlaoshi.new1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by frank on 2017/12/6.
 */

public class x_11self_reviewActivity extends AppCompatActivity {

    private user u;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        u=x_6shouye1Activity.u1;
        setContentView(R.layout.x_11self_review);
        final EditText job=(EditText)findViewById(R.id.review);
        final EditText review=(EditText)findViewById(R.id.rerview_1);
        final EditText pname=(EditText)findViewById(R.id.editText16);
        Button submit=(Button)findViewById(R.id.button2);

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(job.getText().length()==0){
                    new AlertDialog.Builder(x_11self_reviewActivity.this)
                        .setTitle("错误")
                        .setMessage("主要职责不能为空")
                        .setPositiveButton("确定",null)
                        .show();
                }
                else if(review.getText().length()==0){
                    new AlertDialog.Builder(x_11self_reviewActivity.this)
                            .setTitle("错误")
                            .setMessage("对自己的评价不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else if(pname.getText().length()==0){
                    new AlertDialog.Builder(x_11self_reviewActivity.this)
                            .setTitle("错误")
                            .setMessage("项目名不能为空")
                            .setPositiveButton("确定",null)
                            .show();
                }
                else{
                    if(job.getText().toString().contains("'")){
                        String s = job.getText().toString().replace("'","‘");
                        job.setText(s);
                    }
                    if(review.getText().toString().contains("'")){
                        String s = review.getText().toString().replace("'","‘");
                        review.setText(s);
                    }
                    if(pname.getText().toString().contains("'")){
                        String s = pname.getText().toString().replace("'","‘");
                        pname.setText(s);
                    }
                    pname.setText(x_13gerenziliao1.guolv(pname.getText().toString()));
                    job.setText(x_13gerenziliao1.guolv(job.getText().toString()));
                    review.setText(x_13gerenziliao1.guolv(review.getText().toString()));
                    new AlertDialog.Builder(x_11self_reviewActivity.this)
                            .setTitle("确认")
                            .setMessage("确认发表吗？")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        String srjob= URLEncoder.encode(job.getText().toString(),"utf-8");
                                        String srreview= URLEncoder.encode(review.getText().toString(),"utf-8");
                                        String srpname= URLEncoder.encode(pname.getText().toString(),"utf-8");
                                        String url="http://112.74.176.171:8080/xmb/servlet/x_11reviewServlet?sid="+u.getUid()+"&srjob="+srjob+"&srreview="+srreview+"&srpname="+srpname;
                                        new sendMessageThread(url).start();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    new AlertDialog.Builder(x_11self_reviewActivity.this)
                                            .setMessage("发表成功")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(x_11self_reviewActivity.this,X_Mine.class);
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
