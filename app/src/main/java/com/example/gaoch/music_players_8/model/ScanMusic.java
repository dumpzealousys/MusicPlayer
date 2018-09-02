package com.example.gaoch.music_players_8.model;

/**
 * Created by gaoch on 2017-04-17.
 */
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

public class ScanMusic {
    public ArrayList<MusicInfo> query(ArrayList<MusicInfo>list,Context mContext) {
        ArrayList<MusicInfo>arrayList;    //创建ArryList
        ArrayList<File> filelist=new ArrayList<File>();
        arrayList = new ArrayList<MusicInfo>();      //实例化ArryList对象
        String path = Environment.getExternalStorageDirectory().getPath();
        File file = new File(path);
        saoMiao(file,filelist);
        MusicInfo musicInfo;
        for (File ff : filelist) {
            musicInfo=new MusicInfo();
            String musicpath=ff.getPath();//歌曲路径
            System.out.println(musicpath);
            musicInfo.setPath(musicpath);
            DealingString dealingString=new DealingString();
            String music_msg=dealingString.deal_adapter(musicpath);
            musicInfo.setText_song(music_msg);
            arrayList.add(musicInfo);  //添加
        }

        /*Cursor cursor=null;
        try{
            cursor=mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);  //创建一个扫描游标
            if(cursor!=null){
                MusicInfo musicInfo;
                while(cursor.moveToNext()){
                    musicInfo=new MusicInfo();
                    String musicpath=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));//歌曲路径
                    musicInfo.setPath(musicpath);
                    DealingString dealingString=new DealingString();
                    String music_msg=dealingString.deal_adapter(musicpath);
                    musicInfo.setText_song(music_msg);
                    int time=cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));   //歌曲时间
                    musicInfo.setTime(time);
                    arrayList.add(musicInfo);  //添加
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }*/
        //排序
        MusicSort musicSort=new MusicSort();
        musicSort.MusicInfoSort(arrayList);
        IOclass iOclass=new IOclass();
        iOclass.save_all_music(arrayList,mContext);
        return arrayList;
    }
    private static void saoMiao(File file,ArrayList<File> list) {
        // 列出该文件夹下的所有文件及文件夹
        File[] fs = file.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                if (pathname.isDirectory())// 如果是文件夹的话就直接收集这个文件夹
                {
                    return true;
                } else {
                    return pathname.getName().endsWith(".mp3")||pathname.getName().endsWith(".flac");
                }
            }
        });
        // 将集合进行遍历
        if (fs != null) {
            for (File f : fs) {
                if (f.isFile())// 若是文件就直接收藏起来
                {
                    list.add(f);
                } else {// 若是文件夹的话利用递归的方法进行再次扫描文件夹
                    saoMiao(f,list);
                }

            }
        }
    }
}
