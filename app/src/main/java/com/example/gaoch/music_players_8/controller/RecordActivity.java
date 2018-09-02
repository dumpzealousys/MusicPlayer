package com.example.gaoch.music_players_8.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaoch.music_players_8.R;
import com.example.gaoch.music_players_8.model.DealingString;
import com.example.gaoch.music_players_8.model.IOclass;
import com.example.gaoch.music_players_8.model.RecordMusic;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    ListView listView;  //列表
    private ArrayList<RecordMusic> recordlist;
    ArrayList<String> arrayList;
    ArrayList<String> pathlist;
    ArrayList<String> timelist;
    ArrayList<String> songlist;
    IOclass iOclass;  //输入输出流类
    ImageView remove_all_record;
    ImageView return_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        iOclass=new IOclass();
        arrayList=new ArrayList<String>();
        pathlist=new ArrayList<String>();
        timelist=new ArrayList<String>();
        songlist=new ArrayList<String>();
        recordlist=new ArrayList<RecordMusic>();
        showlist();
        final RecordAdapter adapter=new RecordAdapter(RecordActivity.this,R.layout.record_item,recordlist);
        listView=(ListView) findViewById(R.id.record_list);
        listView.setAdapter(adapter);
        //清空播放记录
        remove_all_record=(ImageView)findViewById(R.id.remove_all);
        remove_all_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹框提示
                AlertDialog.Builder dialog=new AlertDialog.Builder(RecordActivity.this);
                dialog.setTitle("清空播放记录");
                dialog.setMessage("确定是否清空播放记录");
                dialog.setCancelable(true);
                dialog.setIcon(R.mipmap.delete_button);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<String> templist=new ArrayList<String>();
                        iOclass.load_record_music(templist,RecordActivity.this);
                        iOclass.remove_all_record(RecordActivity.this);
                        recordlist.removeAll(recordlist);
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                        int i=templist.size()-1;
                        String str=templist.get(i).substring(templist.get(i).indexOf("#")+1,templist.get(i).length());
                        iOclass.save(str,RecordActivity.this);
                        Toast.makeText(RecordActivity.this,"已清空所有播放记录",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        return_main=(ImageView)findViewById(R.id.return_main);
        return_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //初始化列表
    private void showlist(){
        iOclass.load_record_music(arrayList,RecordActivity.this);
        DealingString dealingString=new DealingString();
        dealingString.deal_record(recordlist,arrayList,pathlist,songlist,timelist);
    }
    //适配器
    class RecordAdapter extends ArrayAdapter<RecordMusic>{
        private int resourceId;
        public RecordAdapter(Context context, int textViewResourceId, List<RecordMusic> obj){
        super(context,textViewResourceId,obj);
            resourceId=textViewResourceId;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RecordMusic recordMusic=getItem(position);
            View view;
            ViewHolder viewHolder;
            if(convertView==null){
                view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
                viewHolder=new ViewHolder();
                viewHolder.record_music=(TextView)view.findViewById(R.id.record_music);
                viewHolder.record_time=(TextView)view.findViewById(R.id.record_time);
                view.setTag(viewHolder);
            }else{
                view=convertView;
                viewHolder=(ViewHolder)view.getTag();
            }
            viewHolder.record_music.setText(recordMusic.getPath());
            viewHolder.record_time.setText(recordMusic.getTime());
            return view;
        }
    }
    class ViewHolder{
        TextView record_music;
        TextView record_time;
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
