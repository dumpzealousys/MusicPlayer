package com.example.gaoch.music_players_8.model;

/**
 * Created by gaoch on 2017-05-11.
 */

public class RecordMusic {
    private String path;
    private String time;
    public RecordMusic(){
    }
    public RecordMusic(String path,String time){
        this.path=path;
        this.time=time;
    }
    public String getPath(){
        return path;
    }
    public void setPath(String path){
        this.path=path;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time=time;
    }
}
