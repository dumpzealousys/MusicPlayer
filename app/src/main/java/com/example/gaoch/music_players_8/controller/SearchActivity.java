package com.example.gaoch.music_players_8.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaoch.music_players_8.R;
import com.example.gaoch.music_players_8.model.IOclass;
import com.example.gaoch.music_players_8.model.MyAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity{
    Intent s_intent;
    ImageView return_main;   //返回按钮
    TextView cancle_textview;  //取消
    EditText search_edit_text;   //搜索框
    ArrayList<String> contex_path=new ArrayList<String>();
    ArrayList<String> context_song=new ArrayList<String>();
    ArrayList<String> mData = new ArrayList<String>();
    ListView search_list;   //列表
    Handler handler;   //多线程
    MyAdapter myAdapter;   //适配器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //开启服务
        s_intent=new Intent(SearchActivity.this,MusicService.class);
        startService(s_intent);
        //标题栏
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        //获取搜索数据源
        Intent intent=getIntent();
        contex_path=intent.getStringArrayListExtra("contex_path");
        context_song=intent.getStringArrayListExtra("contex_song");
        handler=new Handler();
        //清空哈希索引
        mData.clear();
        //控件绑定
        return_main=(ImageView)findViewById(R.id.return_main);
        cancle_textview=(TextView) findViewById(R.id.cancle_textview);
        search_edit_text=(EditText)findViewById(R.id.search_edit_text);
        search_list=(ListView) findViewById(R.id.search_list);
        myAdapter=new MyAdapter(SearchActivity.this,R.layout.item,mData);
        search_list.setAdapter(myAdapter);
        //控件监听
        //返回主活动
        return_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //取消按键
        cancle_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_edit_text.setText("");
            }
        });
        //文本改变监听
        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    handler.post(eChanged);
                }
                else{
                    mData.clear();
                    myAdapter.notifyDataSetChanged();
                }
            }
        });

        //列表监听事件
        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str=mData.get(position);
                Intent intent = new Intent();
                int a=0;
                for(int i=0;i<contex_path.size();i++){
                    if(contex_path.get(i).contains(str)){
                        a=i;
                        break;
                    }
                }
                intent.setAction("ACTION_INDEX");
                intent.putExtra("index", str);
                intent.putExtra("pos",a);
                sendBroadcast(intent);
                //保存最近一条播放记录
                IOclass iOclass1=new IOclass();
                iOclass1.save(str,SearchActivity.this);
            }
        });
    }

    private void getmDataSub(ArrayList<String> mDataSubs, String data)
    {
        int length = context_song.size();
        for(int i = 0; i < length; ++i){
            if(context_song.get(i).contains(data)){
                mDataSubs.add(contex_path.get(i));
            }
        }
    }

    //多线程
    Runnable eChanged = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            String data = search_edit_text.getText().toString();
            mData.clear();
            getmDataSub(mData, data);
            myAdapter.notifyDataSetChanged();
        }
    };
    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
