package com.example.gaoch.music_players_8.model;

/**
 * Created by gaoch on 2017-04-17.
 */

public class MusicInfo {
    String text_song;
    String path;
    int time;
    int ID;
    //歌曲信息
    public String getText_song() {
        return text_song;
    }
    public void setText_song(String text_song) {
        this.text_song = text_song;
    }
    //歌曲路径
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    //歌曲时长
    public int getTime(){
        return time;
    }
    public void setTime(int time){
        this.time=time;
    }
    //歌曲ID
    public int getID(){
        return ID;
    }
    public void setID(int ID){
        this.ID=ID;
    }
}
