package com.example.gaoch.music_players_8.controller;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaoch.music_players_8.R;

public class WelcomActivity extends AppCompatActivity {
    Handler handler;
    Runnable runnable;
    private boolean InActivity=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcom);
        TextView text_sip=(TextView)findViewById(R.id.text_skip);   //跳过欢迎页面
        text_sip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InActivity=true;
                Intent intent = new Intent(WelcomActivity.this, MainActivity.class);
                startActivity(intent);
                WelcomActivity.this.finish();
            }
        });
        handler= new Handler();
        //当计时结束,跳转至主界面
        handler.postDelayed(runnable=new Runnable() {
            @Override
            public void run() {
                if(InActivity==false){
                    Intent intent = new Intent(WelcomActivity.this, MainActivity.class);
                    startActivity(intent);
                    WelcomActivity.this.finish();
                }
            }
        }, 3000);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
