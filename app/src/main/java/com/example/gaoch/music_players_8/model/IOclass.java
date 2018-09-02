package com.example.gaoch.music_players_8.model;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by gaoch on 2017-05-09.
 */

public class IOclass {
    //保存最近一次的播放歌曲路径
    public void save(String save_recent_play,Context context){
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
            out=context.openFileOutput("playrecord", Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(save_recent_play);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        //获取当前时间
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd-EEEE  HH:mm:ss ", Locale.getDefault());
        String date = sDateFormat.format(new java.util.Date());
        //同时保存到所有记录中
        save_record_music(date+"#"+save_recent_play,context);
    }
    //读取最近一次的播放歌曲路径
    public String load(Context context){
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try{
            in=context.openFileInput("playrecord");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(reader!=null){
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return content.toString();
    }
    //保存最近一次播放的position
    public void save_pos(int pos,Context context){
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
            out=context.openFileOutput("recordpos", Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            String str=Integer.toString(pos);
            writer.write(str);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    //读取最近一次播放的position
    public int load_pos(Context context){
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        int i=0;
        try{
            in=context.openFileInput("recordpos");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null){
               content.append(line);
            }
            line=content.toString();
            i=Integer.parseInt(line.trim());
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(reader!=null){
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return i;
    }
    //添加到收藏列表
    public void save_to_favorite(String save_favorite,Context context){
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
            out=context.openFileOutput("favorite_record", Context.MODE_APPEND);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(save_favorite+"\r\n");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    //读取收藏列表
    public void load_from_favorite(ArrayList<String> arrayList, Context context){
        FileInputStream in=null;
        BufferedReader reader=null;
        try{
            int i=0;
            in=context.openFileInput("favorite_record");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null){
                arrayList.add(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(reader!=null){
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    //清空收藏列表
    public void remove_all_favorite(Context context){
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
            out=context.openFileOutput("favorite_record", Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write("");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    //判断是否在收藏列表中
    public boolean is_in_list(String name,Context context){
        FileInputStream in=null;
        BufferedReader reader=null;
        try{
            int i=0;
            in=context.openFileInput("favorite_record");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null){
                if(name.equals(line)) {
                    return false;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(reader!=null){
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return true;
    }
    //保存所有播放记录
    public void save_record_music(String record,Context context){
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
            out=context.openFileOutput("allmusic_record", Context.MODE_APPEND);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(record+"\r\n");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    //读取所有播放记录
    public void load_record_music(ArrayList<String> arrayList,Context context){
        FileInputStream in=null;
        BufferedReader reader=null;
        try{
            int i=0;
            in=context.openFileInput("allmusic_record");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null){
                arrayList.add(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(reader!=null){
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    //清空播放记录
    public void remove_all_record(Context context){
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
            out=context.openFileOutput("allmusic_record", Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write("");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    //保存所有歌曲信息
    public void save_all_music(ArrayList<MusicInfo> arrayList,Context context){
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
            out=context.openFileOutput("allmusic_path", Context.MODE_APPEND);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            for(int i=0;i<arrayList.size();i++){
                writer.write(arrayList.get(i).getText_song()+"\r\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public ArrayList<String> load_all_music(Context context){
        ArrayList<String> songlist=new ArrayList<String>();
        FileInputStream in=null;
        BufferedReader reader=null;
        try{
            int i=0;
            in=context.openFileInput("allmusic_path");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null){
                songlist.add(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(reader!=null){
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return songlist;
    }
}
