package com.example.gaoch.music_players_8.controller;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;

import android.media.MediaPlayer;
import android.os.Handler;
import android.content.BroadcastReceiver;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.gaoch.music_players_8.model.IOclass;

public class MusicService extends Service {
    public MusicService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    MusicBroadCast musicBroadCast;  //广播
    MediaPlayer mediaPlayer;  //多媒体对象
    Handler handler;   //歌曲显示时间线程
    @Override
    public void onCreate(){
        super.onCreate();
        musicBroadCast=new MusicBroadCast();
        mediaPlayer=new MediaPlayer();
        TelephonyManager tmgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tmgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        handler=new Handler();
        registerBoradcastReceiver();
    }
    //监听电话状态
    private boolean mResumeAfterCall = false;
    private PhoneStateListener phoneStateListener=new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, String incomingNumber){
            if (state==TelephonyManager.CALL_STATE_RINGING) {
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                int ringvolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
                if (ringvolume > 0) {
                    //电话铃响时关闭音乐
                    mResumeAfterCall = (mediaPlayer.isPlaying()||mResumeAfterCall);
                    mediaPlayer.pause();
                }
            } else if (state==TelephonyManager.CALL_STATE_OFFHOOK) {
                // 通话时暂停音乐
                mResumeAfterCall = (mediaPlayer.isPlaying()||mResumeAfterCall);
                mediaPlayer.pause();
            } else if (state==TelephonyManager.CALL_STATE_IDLE) {
                // 重新播放音乐
                if (mResumeAfterCall) {
                    //通话挂断时恢复播放
                    mediaPlayer.start();
                    mResumeAfterCall = false;
                }
            }
        }
    };
    public class MusicBroadCast extends BroadcastReceiver {
        //接受广播
        @Override
        public void onReceive(Context context, Intent intent) {
            //上次播放记录
            if (intent.getAction().equals("LAST_PLAY")&&(!mediaPlayer.isPlaying())) {
                String path = intent.getStringExtra("lastplay");
                try {
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    All_Time();
                    mediaPlayer.start();
                    handler.postDelayed(mrunable, 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //播放广播
            else if (intent.getAction().equals("ACTION_INDEX")) {
                String path = intent.getStringExtra("index");
                int pos=intent.getIntExtra("pos",0);
                IOclass iOclass=new IOclass();
                iOclass.save_pos(pos,MusicService.this);
                try {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    All_Time();
                    mediaPlayer.start();
                    handler.postDelayed(mrunable, 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent1=new Intent();
                intent1.setAction("LAST_POS");
                intent1.putExtra("lastpos",pos);
                sendBroadcast(intent1);
            }
            //暂停/播放广播
            else if (intent.getAction().equals("ACTION_ISPLAY")) {
                boolean isplay = intent.getBooleanExtra("isplay",false);
                try {
                    if(isplay==false){
                        mediaPlayer.pause();
                    }else{
                        mediaPlayer.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //进度条事件
            else if(intent.getAction().equals("ACTION_SEEKBAR_DRANG")){
                int progress = intent.getIntExtra("progress", 0);
                mediaPlayer.seekTo(progress);
            }else if(intent.hasExtra("state")){
                if (intent.getIntExtra("state", 0)==0&&mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    Toast.makeText(context, "耳机拔出", Toast.LENGTH_LONG).show();
                }
                else if (intent.getIntExtra("state", 0)==1){
                    Toast.makeText(context, "耳机插入", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    public void All_Time(){
        Intent intent1 = new Intent();
        intent1.setAction("ACTION_ALLTIME");
        int time=mediaPlayer.getDuration();
        intent1.putExtra("all_time", time);
        sendBroadcast(intent1);
    }
    //注册广播
    public void registerBoradcastReceiver() {
        // 属性交互
        IntentFilter intentfilter = new IntentFilter();
        // 获得歌曲路径
        intentfilter.addAction("ACTION_INDEX");
        // 播放/暂停
        intentfilter.addAction("ACTION_ISPLAY");
        //上次播放记录
        intentfilter.addAction("LAST_PLAY");
        // 重复播放
        intentfilter.addAction("ACTION_MENTION");
        //进度条拖动
        intentfilter.addAction("ACTION_SEEKBAR_DRANG");
        //耳机插孔插入和拔出
        intentfilter.addAction("android.intent.action.HEADSET_PLUG");
        intentfilter.addAction("SERVICECMD");
        intentfilter.addAction("PAUSE_ACTION");
        registerReceiver(musicBroadCast, intentfilter);
    }
    //歌曲线程
    Runnable mrunable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            final Intent intent = new Intent();
            intent.setAction("ACTION_CURRENTTIME");
            intent.putExtra("current_time", mediaPlayer.getCurrentPosition());
            sendBroadcast(intent);
            //当前时间的运行线程
            handler.postDelayed(mrunable, 1000);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Intent intent1=new Intent();
                    intent1.setAction("COMPLETE_PLAY");
                    sendBroadcast(intent1);
                }
            });
        }
    };
    @Override
    public void onDestroy(){
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        TelephonyManager tmgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tmgr.listen(phoneStateListener, 0);
        unregisterReceiver(musicBroadCast);
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
