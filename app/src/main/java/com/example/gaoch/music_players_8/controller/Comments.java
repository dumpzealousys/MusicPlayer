package com.example.gaoch.music_players_8.controller;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.AndroidException;
import android.util.Log;
import android.support.v4.app.*;
import com.example.gaoch.music_players_8.R;



//////////////////////////


import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;







//////////////////////


public class Comments extends AppCompatActivity implements View.OnClickListener {
    public String data = "";
    public String result = "this is result";
    public String op = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        //Fragment wangFragment=(WangFragment) getFragmentManager().findFragmentById(R.id.left_fragment);
        Intent intent = getIntent();
        data = intent.getStringExtra("info");  //从主活动得到歌手歌名
        //Log.d("123456789", data);

        String pre="";
        Button button1=(Button) findViewById(R.id.btn1);
        button1.setOnClickListener(this);
        Button button2=(Button) findViewById(R.id.btn2);
        button2.setOnClickListener(this);
        Button button3=(Button) findViewById(R.id.btn3);
        button3.setOnClickListener(this);
        button1.setBackgroundColor(Color.parseColor("#ffe599"));
        button2.setBackgroundColor(Color.parseColor("#FAEBD7"));
        button3.setBackgroundColor(Color.parseColor("#FAEBD7"));




        //String song = data.substring((data.indexOf("-") + 1)); //get songname
        //String singer = data.substring(0, data.indexOf("-")); //get singername
        // Intent intent1=new Intent().setClassName()

        // TextView textView = (TextView) findViewById(R.id.textwang);
        sendRequestWithHttpURLConnection(5);//
        sendRequestWithHttpURLConnection(5);//
        sendRequestWithHttpURLConnection(0);/////先用网易云
        sendRequestWithHttpURLConnection(0);/////先用网易云

        sendRequestWithHttpURLConnection(4);//酷狗
        sendRequestWithHttpURLConnection(4);//酷狗
        sendRequestWithHttpURLConnection(4);//酷狗


        sendRequestWithHttpURLConnection(1);/////先用酷我


    }
    @Override

    public void onClick(View v){
        Button button1=(Button) findViewById(R.id.btn1);
        Button button2=(Button) findViewById(R.id.btn2);//#0000CD
        Button button3=(Button) findViewById(R.id.btn3);
        switch (v.getId()){
            case R.id.btn1:
                sendRequestWithHttpURLConnection(1);

                button1.setBackgroundColor(Color.parseColor("#ffe599"));
                button2.setBackgroundColor(Color.parseColor("#FAEBD7"));
                button3.setBackgroundColor(Color.parseColor("#FAEBD7"));


                break;
            case R.id.btn2:
                sendRequestWithHttpURLConnection(2);

                button2.setBackgroundColor(Color.parseColor("#ffe599"));
                button1.setBackgroundColor(Color.parseColor("#FAEBD7"));
                button3.setBackgroundColor(Color.parseColor("#FAEBD7"));
                break;
            case R.id.btn3:
                sendRequestWithHttpURLConnection(3);

                button3.setBackgroundColor(Color.parseColor("#ffe599"));
                button2.setBackgroundColor(Color.parseColor("#FAEBD7"));
                button1.setBackgroundColor(Color.parseColor("#FAEBD7"));
                break;
            default:
                break;
        }

    }

    private void sendRequestWithHttpURLConnection(int i) {
        // 开启线程来发起网络请求
        final int zz=i;
        new Thread(new Runnable() {
            @Override
            public void run() {
                //data=data.trim();
                Log.d("123456789", data);
                final StringBuilder response = new StringBuilder();
                Log.d("Comments",data); //测试成功传递信息

                String song="";
                String singer="";
                if(data.indexOf("-")==data.lastIndexOf("-")) {


                    song = data.substring((data.indexOf("-") + 1)).trim(); //get songname
                    singer = data.substring(0, data.indexOf("-")).trim(); //get singername
                }
                else{
                    song = data.substring(0,data.lastIndexOf("-")).trim().substring((data.indexOf("-") + 1)).trim();//substring((data.indexOf("-") + 1)).trim(); //get songname
                    singer =data.substring(0,data.lastIndexOf("-")).trim().substring(0, data.indexOf("-")).trim();
                }


                //singer=singer.replaceAll(" ", "");
               // song=song.replaceAll(" ", "");
                Log.d("song",song); //测试成功传递信息
                Log.d("singer",singer); //测试成功传递信息

                showResponse("0",zz);//检查是否已经加载


                try{

                    if(zz==0) response.append(com.example.gaoch.music_players_8.model.yinyue.fromwang(song,singer));
                    if(zz==1) response.append(com.example.gaoch.music_players_8.model.yinyue.fromkuwo(song,singer));

                    if(zz==5) response.append(com.example.gaoch.music_players_8.model.yinyue.fromkuwo(song,singer));
                    if(zz==4) response.append(com.example.gaoch.music_players_8.model.yinyue.fromkugou(song,singer));
                    //if(zz==3) response.append(com.example.gaoch.music_players_8.model.yinyue.fromwang(song,singer));


                    //response.append(com.example.gaoch.music_players_8.model.yinyue.fromwang(song,singer));
                    // 这是网易云的
                    // response.append(com.example.gaoch.music_players_8.model.yinyue.fromkuwo(song,singer));
                    //酷我的，还没格式化没有提取日期
                    //response.append(com.example.gaoch.music_players_8.model.yinyue.fromkugou(song,singer));
                    //Log.d("Comments",response.toString()); //测试成功传递


                    showResponse(response.toString(),zz);



                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }).start();
    }


    public void showResponse(final String response,final int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上

                TextView textView = (TextView) findViewById(R.id.textwang);

                if (i==3) {
                    TextView textView2=(TextView)findViewById(R.id.xxx);
                    String opp=textView2.getText().toString();
                    if(opp.length()>10)  {
                        textView.setText(opp);
                        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
                        // textView.setBackgroundColor(0xcc0000);
                        return;
                    }

                }
                if (i==2) {
                    TextView textView2=(TextView)findViewById(R.id.yyy);
                    String opp=textView2.getText().toString();
                    if(opp.length()>10)  {
                        textView.setText(opp);
                        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
                        // textView.setBackgroundColor(0xcc0000);
                        return;
                    }

                }
                if (i==1) {
                    TextView textView2=(TextView)findViewById(R.id.zzz);
                    String opp=textView2.getText().toString();
                    if(opp.length()>10)  {
                        textView.setText(opp);
                        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
                        //textView.setBackgroundColor(0xcc0000);
                        return;
                    }

                }


                //Log.d("Comments",response.toString()); //测试成功传递
                if (i==0){
                    TextView textView2=(TextView)findViewById(R.id.xxx);
                    textView2.setText(response.toString());

                }
                if (i==5){
                    TextView textView2=(TextView)findViewById(R.id.zzz);
                    textView2.setText(response.toString());

                }

                if (i==4){
                    TextView textView2=(TextView)findViewById(R.id.yyy);
                    textView2.setText(response.toString());

                }

                if(i!=0&&i!=4&&i!=5)  textView.setText(response.toString());


                //下面两条与顺序逻辑无关
                Log.d("Comments",response.toString()); //测试成功传递
                textView.setMovementMethod(ScrollingMovementMethod.getInstance());
            }
        });
    }


    protected void ii() {
        Log.d("Comments",data); //测试成功传递信息
        String song=data.substring((data.indexOf("-")+1)); //get songname
        String singer=data.substring(0,data.indexOf("-")); //get singername
        Log.d("Comments",song); //测试成功传递信息
        Log.d("Comments",singer); //测试成功传递信息
        try{
            result =com.example.gaoch.music_players_8.model.yinyue.fromwang(song,singer);
            Log.d("Comments",result); //测试成功传递信息
        }catch (Exception e){
            e.printStackTrace();
        }


    }












}