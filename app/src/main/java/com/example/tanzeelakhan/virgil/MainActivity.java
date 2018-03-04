package com.example.tanzeelakhan.virgil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button btnCamera = (Button) findViewById(R.id.btnCamera);
        imageView = (ImageView) findViewById(R.id.imageView);

        btnCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
                Log.d("hello2", "helloagain3");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
//        sendPost(bitmap);
        doInBackground();
    }

    protected String doInBackground() {

        //works with test case
        //songText = "All of me";
        //artistText = "John Legend";

//        String song = songText.toString();
//        song = song.replaceAll("\\s","+");
//        String artist = artistText.toString();
//        artist = artist.replaceAll("\\s","+");
        // Do some validation here

        try {

            URL url = new URL("http://ec2-18-216-113-91.us-east-2.compute.amazonaws.com/audio");
//            Log.d("url", url.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                Log.d("json object", stringBuilder.toString());

                JSONObject jObject = new JSONObject(stringBuilder.toString());
                try {
                    JSONArray jArray = jObject.getJSONArray("search");

                    for (int i = 0; i < jArray.length(); i++) {
                        try {
                            JSONObject oneObject = jArray.getJSONObject(i);
                            // Pulling items from the array
//                            bpm = oneObject.getString("tempo");
//                            if (bpm.equals("null")) {
//                                bpm = "0";
//                            }
//                            Log.d("BPM", bpm);

                        } catch (JSONException e) {
                            // Oops
                        }
                    }
                }
                catch (Exception e){
                    Log.d("hello","lol");
//                    bpm = "0";
//                    return bpm;
                    return "0";
                }
//                return bpm;
                return "0";
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.d("plso","helloo");
            Log.e("ERROR", e.getMessage(), e);
//            bpm = "0";
//            return bpm;
            return "0";
        }
    }
    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("haha","hahaha");
                    URL url = new URL("http://ec2-18-216-113-91.us-east-2.compute.amazonaws.com/api/v1/audio");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

//                    JSONObject jsonParam = new JSONObject();
////                    jsonParam.put("timestamp", 1488873360);
////                    jsonParam.put("uname", message.getUser());
////                    jsonParam.put("message", message.getMessage());
//                    jsonParam.put("latitude", 0D);
//                    jsonParam.put("longitude", 0D);

//                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
//                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

//    protected String doInBackground(String songText, String artistText) {
//
//        //works with test case
//        //songText = "All of me";
//        //artistText = "John Legend";
//
//        String song = songText.toString();
//        song = song.replaceAll("\\s","+");
//        String artist = artistText.toString();
//        artist = artist.replaceAll("\\s","+");
//        // Do some validation here
//
//        try {
//
////            URL url = new URL(API_URL + "api_key=" + API_KEY + "&type=both&lookup=song:" + song + "%20artist:" + artist);
//            URL url = new URL("http://ec2-18-216-113-91.us-east-2.compute.amazonaws.com/api/v1/image");
////            Log.d("url", url.toString());
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            try {
//
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(line).append("\n");
//                }
//                bufferedReader.close();
//                Log.d("json object", stringBuilder.toString());
//
//                JSONObject jObject = new JSONObject(stringBuilder.toString());
//                try {
//                    JSONArray jArray = jObject.getJSONArray("search");
//
//                    for (int i = 0; i < jArray.length(); i++) {
//                        try {
//                            JSONObject oneObject = jArray.getJSONObject(i);
//                            // Pulling items from the array
//                            bpm = oneObject.getString("tempo");
//                            if (bpm.equals("null")) {
//                                bpm = "0";
//                            }
//                            Log.d("BPM", bpm);
//
//                        } catch (JSONException e) {
//                            // Oops
//                        }
//                    }
//                }
//                catch (Exception e){
//                    Log.d("hello","lol");
//                    bpm = "0";
//                    return bpm;
//                }
//                return bpm;
//            }
//            finally{
//                urlConnection.disconnect();
//            }
//        }
//        catch(Exception e) {
//            Log.d("plso","helloo");
//            Log.e("ERROR", e.getMessage(), e);
//            bpm = "0";
//            return bpm;
//        }
//    }
}
