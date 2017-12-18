package com.example.home.googleui;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    EditText name,phone,password,email;
    Button reg,clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

        }
        name=(EditText) findViewById(R.id.name);
        phone=(EditText) findViewById(R.id.phonen);
        password=(EditText) findViewById(R.id.password);
        email=(EditText) findViewById(R.id.mail);
        reg=(Button) findViewById(R.id.Register);
        clear=(Button) findViewById(R.id.Clear);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.1.100/woman/reg.php";

                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("t1",name.getText().toString()));
                params.add(new BasicNameValuePair("t2", password.getText().toString()));
                params.add(new BasicNameValuePair("t3", phone.getText().toString()));
                params.add(new BasicNameValuePair("t4", email.getText().toString()));


                String resultServer = NetConnect.getHttpPost(url, params);
                String msg =null;
                try {
                    JSONObject c = new JSONObject(resultServer);
                    msg=(String.valueOf(c.getString("muthu")));
                    if(msg == "ok"){
                        Intent i = new Intent(getApplication(),Main3Activity.class);
                        startActivity(i);
                    }else {
                        Toast.makeText(getApplicationContext(),""+msg,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");phone.setText("");password.setText("");email.setText("");
            }
        });
    }
}
