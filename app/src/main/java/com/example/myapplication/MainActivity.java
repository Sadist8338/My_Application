package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText City;
    private TextView result_info;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        City = findViewById(R.id.City);
        Button citybtn = findViewById(R.id.Citybtn);
        result_info= findViewById(R.id.result_info);

        citybtn.setOnClickListener(view -> {
    if(City.getText().toString().trim().equals(""))
        Toast.makeText(MainActivity.this, R.string.no_Text, Toast.LENGTH_LONG).show();
    else {
        String city = City.getText().toString();
        String key = "449c87a47cebbda834f82562c7d59b2a";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" +city+ "&appid="+key+"&units=metric&lang=ua";

        AsyncTask<String, String, String> execute = new GetURLData().execute(url);
    }
        });
    }
    private class GetURLData extends AsyncTask<String,String,String>{

            protected void onPreExecute(){
                super.onPreExecute();
                result_info.setText("Шукаю...");
            }
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
               connection =(HttpURLConnection) url.openConnection();
               connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line="";

                while((line=reader.readLine())!=null)
                    buffer.append(line).append("\n");

                return buffer.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
               if(connection != null)
                   connection.disconnect();
               try{
               if(reader != null)
                   reader.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
        }
        @Override
        protected void onPostExecute(String result){
                super.onPostExecute(result);

                result_info.setText(result);
        }
        }
    }

