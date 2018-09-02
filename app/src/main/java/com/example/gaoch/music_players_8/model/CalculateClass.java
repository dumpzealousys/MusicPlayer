package com.example.gaoch.music_players_8.model;

/**
 * Created by gaoch on 2017-05-10.
 */

public class CalculateClass {
    public String setTime(int time) {
        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }
}
