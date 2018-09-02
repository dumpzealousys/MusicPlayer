package com.example.gaoch.music_players_8.model;

import android.content.Context;
import android.widget.Toast;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by gaoch on 2017-05-10.
 */

public class MusicSort {
    //按某一列进行排序
    public void MusicInfoSort(ArrayList<MusicInfo> songlist){
        Collections.sort(songlist, new Comparator<MusicInfo>() {
            @Override
            public int compare(MusicInfo o1, MusicInfo o2) {
                ArrayList<String> arrayList=new ArrayList<String>();
                arrayList.add(o1.getText_song());
                arrayList.add(o2.getText_song());
                return ListSort(arrayList);
            }
        });
    }
    //中文排序
    public int ListSort(ArrayList<String> songlist){
        ArrayList<String> list=new ArrayList<String>();
        list.add(songlist.get(0));
        list.add(songlist.get(1));
        //添加首字母和标识符&
        for(int i=0;i<songlist.size();i++){
            String str=songlist.get(i);
            if(str.length()==0)
                return 0;
            String alphabet=str.substring(0,1);
            if (alphabet.matches("[\\u4e00-\\u9fa5]+")) {
                str=getAlphabet(str) + "&" + str;
                songlist.set(i,str);
            }
        }
        //设置排序语言环境
        Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(songlist, com);
        //遍历数组，去除标识符&及首字母
        for (int i=0;i<songlist.size();i++) {
            String str=songlist.get(i);
            if(str.contains("&")&&str.indexOf("&")==1){
                songlist.set(i,str.split("&")[1]);
            }
        }
        if(songlist.get(0).equals(list.get(0))&&songlist.get(1).equals(list.get(1)))
            return -1;
        else return 1;
    }
    //拼音首字母
    public String getAlphabet(String str) {
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部小写
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String pinyin = null;
        try {
            pinyin = (String) PinyinHelper.toHanyuPinyinStringArray(str.charAt(0), defaultFormat)[0];
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return pinyin.substring(0, 1);
    }
}
