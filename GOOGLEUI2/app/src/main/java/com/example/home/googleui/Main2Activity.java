package com.example.home.googleui;

import android.content.Intent;
import android.net.ConnectivityManager;
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

public class Main2Activity extends AppCompatActivity {
    EditText userid,userpass;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

        }
        userid =(EditText) findViewById(R.id.Userid);
        userpass=(EditText) findViewById(R.id.Password);
        send=(Button) findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userid.getText().equals("") || userid.getText().length()==0 && userpass.getText().equals("") || userpass.getText().length()==0){
                    Toast.makeText(getApplicationContext(),"Enter the userid and password filed",Toast.LENGTH_SHORT).show();
                }else {
                    String url = "http://192.168.1.100/1/logins.php";
                    String name = userid.getText().toString();
                    String pass = userpass.getText().toString();

                    List<NameValuePair> params = new ArrayList<NameValuePair>();

                    params.add(new BasicNameValuePair("t1", name));
                    params.add(new BasicNameValuePair("t2", pass));
                    String server =NetConnect.getHttpPost(url, params);
                    JSONObject c;
                    String msg=null,user=null,session=null;
                    try{
                        c= new JSONObject(server);
                        msg =(String.valueOf(c.getString("muthu")));
                        user=(String.valueOf(c.getString("mss")));
                        session=(String.valueOf(c.getString("session")));

                        Toast.makeText(getApplicationContext(),""+msg.toString(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),""+user.toString(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),""+session.toString(),Toast.LENGTH_SHORT).show();

                        if(name.equals(""+user)){
                            Intent i = new Intent(Main2Activity.this,Main4Activity.class);
                            startActivity(i);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            }
        });

    }
}
