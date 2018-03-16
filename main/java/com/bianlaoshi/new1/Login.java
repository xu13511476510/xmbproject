package com.bianlaoshi.new1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by zhuangyuan on 2017/11/13.
 */

public class Login extends AppCompatActivity {
    private EditText editTextUid;
    private EditText editTextPassword;
    private Handler handler=new Handler();
    private TextView textViewResult;
    private Button submit;
    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_2denglu);
        //分配控件
        editTextUid=(EditText) findViewById(R.id.inputphone);
        editTextPassword=(EditText) findViewById(R.id.inputpassword);
        textViewResult=(TextView) findViewById(R.id.textView28);
        submit=(Button)findViewById(R.id.denglu);


        //按钮监听
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //构建url，开启线程,都使用get方法传输
                if(editTextUid.getText().toString().contains("'")){
                    String s =editTextUid.getText().toString().replace("'","‘");
                    editTextUid.setText(s);
                }
                if(editTextPassword.getText().toString().contains("'")){
                    String s =editTextPassword.getText().toString().replace("'","‘");
                    editTextPassword.setText(s);
                }
                else {
                    String uid = editTextUid.getText().toString();
                    String upassword = editTextPassword.getText().toString();
                    String url = "http://112.74.176.171:8080/xmb/servlet/mainActivityServlet?uid=" + uid + "&upassword=" + upassword;
                    new mainActivityThread(url, handler, textViewResult, Login.this).start();
                }
            }
        });

        button1=(Button)findViewById(R.id.newuser_zhuce);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent=new Intent(Login.this,a_4zhuceActivity.class);
                startActivity(intent);
            }
        });

        button2=(Button)findViewById(R.id.forgetpassword);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,zhaohuimimaActivity.class);
                startActivity(intent);
            }
        });

    }
}





