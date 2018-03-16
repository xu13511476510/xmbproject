package com.bianlaoshi.new1;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by frank on 2017/11/27.
 */

public class x_5xiangmuxiangqing1 extends AppCompatActivity {
    public static project p;
    public static user u;
    private Handler handler=new Handler();
    public Context context;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_5xiangmuxiangqing1);

        TextView pname=(TextView) findViewById(R.id.pname);
        TextView pteacher=(TextView) findViewById(R.id.pteacher);
        TextView pnum=(TextView) findViewById(R.id.textView41);
        TextView prequire=(TextView) findViewById(R.id.prequi);
        TextView pintroduction=(TextView) findViewById(R.id.jianjie1);
        TextView pelse=(TextView) findViewById(R.id.text3211);

        //生成url
        String url="http://112.74.176.171:8080/xmb/servlet/returnTeacherNameServlet?tid="+p.getPtid();
        new getTeacherNameThread(handler,url,pteacher).start();

        pname.setText(p.getPname());
        pnum.setText(""+p.getPnum());
        prequire.setText(p.getPrequire());
        pintroduction.setText(p.getPintroduction());
        pelse.setText(p.getPelse());

        Button apply=(Button)findViewById(R.id.button10);
        ImageButton jubao=(ImageButton)findViewById(R.id.Buttonjubao);

        u=x_6shouye1Activity.u1;

        apply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(x_5xiangmuxiangqing1.this)
                        .setTitle("确定")
                        .setMessage("确定提交申请吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url= null;
                                try {
                                    url = "http://112.74.176.171:8080/xmb/servlet/studentProjectServlet?sid="+u.getUid()+"&pid="+p.getPid()+"&tid="+p.getPtid()+"&order=1"+"&pname="+ URLEncoder.encode(p.getPname(),"utf-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                Thread thread=new x_shenqingxingmuThread(url,handler,x_5xiangmuxiangqing1.this);
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
        });

        jubao.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(x_5xiangmuxiangqing1.this)
                        .setTitle("确定")
                        .setMessage("确定举报吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url="http://112.74.176.171:8080/xmb/servlet/jubaoServlet?order=1&pid="+p.getPid();
                                //0 user 1 project
                                new jubaoThread(url).start();
                                new AlertDialog.Builder(x_5xiangmuxiangqing1.this)
                                        .setMessage("举报成功")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

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
