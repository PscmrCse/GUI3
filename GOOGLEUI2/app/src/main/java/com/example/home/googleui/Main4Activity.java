package com.example.home.googleui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

public class Main4Activity extends AppCompatActivity {
    private TextView txtSpeechInput;
    private Button btnSpeak,logout;
    private final int REQ_CODE_SPEECH_INPUT = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        txtSpeechInput = (TextView) findViewById(R.id.textSpeech);
        btnSpeak = (Button) findViewById(R.id.btnSpeech);


        // hide the action bar


        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    private class BackTask extends AsyncTask<Void,Void,Void> {
        ArrayList<String> list,list2;

        protected void onPreExecute(){
            super.onPreExecute();
            list=new ArrayList<>();
            list2=new ArrayList<>();

        }
        protected Void doInBackground(Void...params){
            InputStream is=null;
            String result="";
            try{
                HttpClient httpclient=new DefaultHttpClient();
                // HttpPost httppost= new HttpPost("http://192.168.43.34/androidupload/jsontemple.php");
                HttpPost httppost= new HttpPost("http://samarastasewa.pscmrcetonline.com/jsontemple1.php");
                HttpResponse response=httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                // Get our response as a String.
                is = entity.getContent();
            }catch(IOException e){
                e.printStackTrace();
            }

            //convert response to string
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result+=line;
                }
                is.close();
                //result=sb.toString();
            }catch(Exception e){
                e.printStackTrace();
            }
            // parse json data
            try{
                JSONArray jArray =new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject jsonObject=jArray.getJSONObject(i);
                    // add interviewee name to arraylist
                    list.add(jsonObject.getString("tid"));
                    list2.add(jsonObject.getString("vname"));

                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result){
            /*listItems.addAll(list);
            listItems2.addAll(list2);
            listItems3.addAll(list3);
            lat.addAll(lat1);
            lng.addAll(lng1);
            */// adapter.notifyDataSetChanged();
            // listItems1.addAll(list1);
        }
    }

}
