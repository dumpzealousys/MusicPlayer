package com.example.gaoch.music_players_8.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gaoch.music_players_8.R;
import com.example.gaoch.music_players_8.model.DealingString;
import com.example.gaoch.music_players_8.model.IOclass;

import java.util.ArrayList;

public class FavoriteMusic extends AppCompatActivity {
    Intent s_intent;
    private ArrayList<String>arrayList;
    ListView listView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_music);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);   //标题栏
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        //开启服务
        s_intent=new Intent(FavoriteMusic.this,MusicService.class);
        startService(s_intent);
        //提取字符串
        final IOclass iOclass=new IOclass();
        arrayList=new ArrayList<String>();
        iOclass.load_from_favorite(arrayList,FavoriteMusic.this);
        final ArrayList<String> songlist=new ArrayList<String>();
        final ArrayList<String> pathlist=new ArrayList<String>();
        final ArrayList<Integer> poslist=new ArrayList<Integer>();
        //字符串处理
        DealingString dealingString=new DealingString();
        dealingString.deal_favorite(arrayList,pathlist,songlist,poslist);
        listView=(ListView) findViewById(R.id.favorite_list);
        adapter=new ArrayAdapter<String>(FavoriteMusic.this,R.layout.list_item_text,songlist);
        listView.setAdapter(adapter);
        //清空收藏列表
        ImageView remove_all=(ImageView)findViewById(R.id.remove_all);
        remove_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView.getCount()>0){
                    //提示框
                    AlertDialog.Builder dialog=new AlertDialog.Builder(FavoriteMusic.this);
                    dialog.setTitle("清空收藏列表");
                    dialog.setMessage("确定是否清空收藏列表");
                    dialog.setCancelable(true);
                    dialog.setIcon(R.mipmap.delete_button);
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(FavoriteMusic.this, "已清空列表", Toast.LENGTH_SHORT).show();
                            IOclass iOclass1 = new IOclass();
                            iOclass1.remove_all_favorite(FavoriteMusic.this);
                            arrayList = new ArrayList<String>();
                            iOclass1.load_from_favorite(arrayList, FavoriteMusic.this);
                            adapter = new ArrayAdapter<String>(FavoriteMusic.this, R.layout.list_item_text, arrayList);
                            listView.setAdapter(adapter);
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }else {
                    Toast.makeText(FavoriteMusic.this,"列表为空",Toast.LENGTH_SHORT).show();
                }

            }
        });
        //返回
        ImageView return_main=(ImageView) findViewById(R.id.return_main);
        return_main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        //listview点击事件响应
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str=pathlist.get(position);
                Intent intent = new Intent();
                intent.setAction("ACTION_INDEX");
                intent.putExtra("index", str);
                intent.putExtra("pos",poslist.get(position));
                sendBroadcast(intent);
                //保存最近一条播放记录
                IOclass iOclass1=new IOclass();
                iOclass1.save(str,FavoriteMusic.this);
            }
        });
        //长按删除
        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                return true;
            }
            });*/
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
