package com.example.gaoch.music_players_8.model;

import java.util.ArrayList;

/**
 * Created by gaoch on 2017-05-13.
 */

public class DealingString {
    //处理收藏列表字符串，只显示歌曲信息
    public void deal_favorite(ArrayList<String> arrayList,ArrayList<String> pathlist,ArrayList<String> songlist,ArrayList<Integer> poslist){
        for(int i=arrayList.size()-1;i>=0;i--) {
            String str = arrayList.get(i).substring(0, arrayList.get(i).indexOf("#"));
            int a = Integer.parseInt(str.trim());
            poslist.add(a);   //位置信息
            pathlist.add(arrayList.get(i).substring(arrayList.get(i).indexOf("#") + 1, arrayList.get(i).length()));  //路径信息
            int ii = 0, jj = 0;
            for (int j = arrayList.get(i).length() - 1; j >= 0; j--) {
                if (arrayList.get(i).charAt(j) == '.' & jj == 0) {
                    jj = j;
                }
                if (arrayList.get(i).charAt(j) == '/') {
                    ii = j;
                    break;
                }
            }
            songlist.add(arrayList.get(i).substring(ii + 1, jj));   //歌曲信息
        }
    }

    //处理适配器的字符串，使得在列表中只显示歌曲信息
    public String deal_adapter(String str){
        //字符串处理
        int ii=0,jj=0;
        for(int i=str.length()-1;i>=0;i--){
            if(str.charAt(i)=='.'&jj==0){
                jj=i;
            }
            if(str.charAt(i)=='/'){
                ii=i;
                break;
            }
        }
        String music_msg=str.substring(ii+1,jj);   //歌曲信息
        return music_msg;
    }

    //处理播放记录字符串，使其显示歌曲信息和时间
    public void deal_record(ArrayList<RecordMusic> recordlist,
                            ArrayList<String> arrayList,ArrayList<String> pathlist,ArrayList<String> songlist,ArrayList<String> timelist){
        int ss=0;
        //把最新的记录放到listview最前面
        for(int i=arrayList.size()-1;i>=0;i--){
            timelist.add(arrayList.get(i).substring(0,arrayList.get(i).indexOf("#")));
            pathlist.add(arrayList.get(i).substring(arrayList.get(i).indexOf("#")+1,arrayList.get(i).length()));
            int ii=0,jj=0;
            for(int j=arrayList.get(i).length()-1;j>=0;j--){
                if(arrayList.get(i).charAt(j)=='.'&jj==0){
                    jj=j;
                }
                if(arrayList.get(i).charAt(j)=='/'){
                    ii=j;
                    break;
                }
            }
            songlist.add(arrayList.get(i).substring(ii+1,jj));   //歌曲信息
            RecordMusic recordMusic=new RecordMusic(songlist.get(ss),timelist.get(ss));
            ss++;
            recordlist.add(recordMusic);
        }
    }
}
