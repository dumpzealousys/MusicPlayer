package com.example.gaoch.music_players_8.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.example.gaoch.music_players_8.R;
import com.example.gaoch.music_players_8.model.CalculateClass;
import com.example.gaoch.music_players_8.model.IOclass;
import com.example.gaoch.music_players_8.model.MusicInfo;
import com.example.gaoch.music_players_8.model.MyAdapter;
import com.example.gaoch.music_players_8.model.ScanMusic;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    Intent s_intent;
    private DrawerLayout mDrawerLayout;    //滑动菜单
    private SwipeRefreshLayout swipeRefresh;   //下拉刷新
    MyBrodcastRecriver mybrodcast;   //广播
    ListView listView;   //列表
    MyAdapter musicAdatpter;   //适配器
    ScanMusic scanMusic;   //扫描
    ArrayList<MusicInfo> arrlist;     // 动态数组
    ArrayList<String> context_song = new ArrayList<String>();  //歌曲列表
    ArrayList<Integer> context_time = new ArrayList<Integer>();   //歌曲时长列表
    ArrayList<String> context_path = new ArrayList<String>();   //歌曲路径列表
    ImageView play_music,previous_music,next_music;   //播放、暂停、上下曲
    TextView maxtime,currenttime;   //总时长，当前播放时长
    SeekBar seekBar;   //进度条
    int pos;//播放记录
    String strpath;
    String recent_one_record;
    boolean isplay;  //是否播放
    ImageView record_play;  //播放记录
    Toolbar toolbar;   //标题栏
    MusicNotification musicNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        musicView();
        musicNotification=new MusicNotification(MainActivity.this);
        musicNotification.Initnotify();
        //启动服务
        s_intent=new Intent(MainActivity.this,MusicService.class);
        startService(s_intent);
        //注册广播
        mybrodcast=new MyBrodcastRecriver();
        zhuce();
        //加载上次播放位置
        IOclass iOclass=new IOclass();
       // iOclass1.remove_all_favorite(MainActivity.this);
        pos=iOclass.load_pos(MainActivity.this);
        //读取上次播放记录
        recent_one_record=iOclass.load(MainActivity.this);
        strpath=recent_one_record;
        isplay=false;
        //控件绑定
        play_music=(ImageView)findViewById(R.id.play_music);   //播放、暂停
        previous_music=(ImageView)findViewById(R.id.previous_music);   //上一曲
        next_music=(ImageView)findViewById(R.id.next_music);    //下一曲
        record_play=(ImageView)findViewById(R.id.recent_play);   //播放记录
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);   //下拉刷新
        swipeRefresh.setColorSchemeResources(R.color.lightblue);   //初始化
        play_music.setBackgroundResource(R.mipmap.ic_play_music);  //初始化
        maxtime=(TextView) findViewById(R.id.stop_time);   //播放总时长
        currenttime=(TextView)findViewById(R.id.start_time);   //当前播放时长
        seekBar=(SeekBar)findViewById(R.id.bar_time);   //进度条
        listView = (ListView) findViewById(R.id.music_list);    // 播放列表
        toolbar = (Toolbar) findViewById(R.id.toolbar);  //标题栏
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);  //滑动菜单
        final NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
        //获取权限，获取媒体库
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            scanMusic = new ScanMusic();
            arrlist = scanMusic.query(arrlist, this);
            for (int i = 0; i < arrlist.size(); i++) {
                context_song.add(arrlist.get(i).getText_song());
                context_path.add(arrlist.get(i).getPath());
                context_time.add(arrlist.get(i).getTime());
            }
        }
        //获取电话权限
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_PHONE_STATE},2);
        }
        //加载列表
        musicAdatpter=new MyAdapter(MainActivity.this,R.layout.item,context_path);
        listView.setAdapter(musicAdatpter);
        //控件监听
        //进度条
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser == true) {
                    Intent intent1 = new Intent();
                    intent1.setAction("ACTION_SEEKBAR_DRANG");
                    intent1.putExtra("progress", progress);
                    sendBroadcast(intent1);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        //滑动菜单功能实现
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_excit:   //退出
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        //下拉刷新
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_reaction();
            }
        });
        //上一曲
        previous_music.setOnClickListener(this);
        //下一曲
        next_music.setOnClickListener(this);
        //播放、暂停
        play_music.setOnClickListener(this);
        //listView点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicInfo musicInfo=arrlist.get(position);
                pos=position;
                play_music.setBackgroundResource(R.mipmap.ic_pause_play);
                Intent intent = new Intent();
                intent.setAction("ACTION_INDEX");
                intent.putExtra("index", musicInfo.getPath());
                intent.putExtra("pos",pos);
                sendBroadcast(intent);
                strpath=musicInfo.getPath();
                //保存最近一条播放记录
                String save_recent_play=musicInfo.getPath();
                IOclass iOclass=new IOclass();
                iOclass.save(save_recent_play,MainActivity.this);
            }
        });

        //播放记录
        record_play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,RecordActivity.class);
                startActivity(intent);
            }
        });

        //回到顶部
        ImageView fab=(ImageView) findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listView.setSelectionAfterHeaderView();
            }
        });
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_music:
                isplay=!isplay;
                if(isplay==true){
                    play_music.setBackgroundResource(R.mipmap.ic_pause_play);
                    if(recent_one_record.equals(strpath)) {
                        strpath="";
                        Intent intent = new Intent();
                        intent.setAction("LAST_PLAY");
                        intent.putExtra("lastplay", recent_one_record);
                        sendBroadcast(intent);
                    }
                }else{
                    play_music.setBackgroundResource(R.mipmap.ic_play_music);
                }
                Intent intent2=new Intent();
                intent2.setAction("ACTION_ISPLAY");
                intent2.putExtra("isplay",isplay);
                sendBroadcast(intent2);
                break;
            case R.id.previous_music:   //上一曲
                play_music.setBackgroundResource(R.mipmap.ic_pause_play);
                isplay=true;
                setPrevious_music();
                break;
            case R.id.next_music:   //下一曲
                play_music.setBackgroundResource(R.mipmap.ic_pause_play);
                isplay=true;
                setNext_music();
                break;
            default:
                break;
        }
    }
    
    
    private void musicView()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
    }
    else
        {
            AudioWidget audioWidget = new AudioWidget.Builder(this).build();
            audioWidget.show(100,100);

            audioWidget.controller().onControlsClickListener(new AudioWidget.OnControlsClickListener() {
                @Override
                public boolean onPlaylistClicked() {

                        return false;
                }

                @Override
                public void onPlaylistLongClicked() {

                }

                @Override
                public void onPreviousClicked() {
                    play_music.setBackgroundResource(R.mipmap.ic_pause_play);
                    isplay=true;
                    setPrevious_music();

                }

                @Override
                public void onPreviousLongClicked() {

                }

                @Override
                public boolean onPlayPauseClicked() {


                    isplay=!isplay;
                    if(isplay==true) {
                        play_music.setBackgroundResource(R.mipmap.ic_pause_play);
                        if (recent_one_record.equals(strpath)) {
                            strpath = "";
                            Intent intent = new Intent();
                            intent.setAction("LAST_PLAY");
                            intent.putExtra("lastplay", recent_one_record);
                            sendBroadcast(intent);
                        }

                    }else{
                        play_music.setBackgroundResource(R.mipmap.ic_play_music);
                    }
                    Intent intent2=new Intent();
                    intent2.setAction("ACTION_ISPLAY");
                    intent2.putExtra("isplay",isplay);
                    sendBroadcast(intent2);

                    return false;
                }

                @Override
                public void onPlayPauseLongClicked() {

                }

                @Override
                public void onNextClicked() {
                    play_music.setBackgroundResource(R.mipmap.ic_pause_play);
                    isplay=true;
                    setNext_music();

                }

                @Override
                public void onNextLongClicked() {

                }

                @Override
                public void onAlbumClicked() {

                }

                @Override
                public void onAlbumLongClicked() {

                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                // now you can show audio widget

                AudioWidget audioWidget = new AudioWidget.Builder(this).build();
                audioWidget.show(100,100);
                audioWidget.controller().onControlsClickListener(new AudioWidget.OnControlsClickListener() {
                    @Override
                    public boolean onPlaylistClicked() {

                        return false;
                    }

                    @Override
                    public void onPlaylistLongClicked() {

                    }

                    @Override
                    public void onPreviousClicked() {
                        play_music.setBackgroundResource(R.mipmap.ic_pause_play);
                        isplay=true;
                        setPrevious_music();

                    }

                    @Override
                    public void onPreviousLongClicked() {

                    }

                    @Override
                    public boolean onPlayPauseClicked() {

                        isplay=!isplay;
                        if(isplay==true) {
                            play_music.setBackgroundResource(R.mipmap.ic_pause_play);
                            if (recent_one_record.equals(strpath)) {
                                strpath = "";
                                Intent intent = new Intent();
                                intent.setAction("LAST_PLAY");
                                intent.putExtra("lastplay", recent_one_record);
                                sendBroadcast(intent);
                            }

                        }else{
                            play_music.setBackgroundResource(R.mipmap.ic_play_music);
                        }
                        Intent intent2=new Intent();
                        intent2.setAction("ACTION_ISPLAY");
                        intent2.putExtra("isplay",isplay);
                        sendBroadcast(intent2);

                        return false;
                    }

                    @Override
                    public void onPlayPauseLongClicked() {

                    }

                    @Override
                    public void onNextClicked() {

                        play_music.setBackgroundResource(R.mipmap.ic_pause_play);
                        isplay=true;
                        setNext_music();

                    }

                    @Override
                    public void onNextLongClicked() {

                    }

                    @Override
                    public void onAlbumClicked() {

                    }

                    @Override
                    public void onAlbumLongClicked() {

                    }
                });
            }
        }
    }

    //下一曲
    public void setNext_music(){
        Intent intent1=new Intent();
        intent1.setAction("ACTION_INDEX");
        pos=(pos+1)%arrlist.size();
        intent1.putExtra("index",arrlist.get(pos).getPath());
        intent1.putExtra("pos",pos);
        sendBroadcast(intent1);
        //保存最近一条播放记录
        String save_recent_play1=arrlist.get(pos).getPath();
        IOclass iOclass1=new IOclass();
        iOclass1.save(save_recent_play1,MainActivity.this);
    }

    //上一曲
    public void setPrevious_music(){
        Intent intent=new Intent();
        intent.setAction("ACTION_INDEX");
        if((pos-1)>=0){
            pos=(pos-1)%arrlist.size();
            intent.putExtra("index",arrlist.get(pos).getPath());
        }
        else{
            pos=arrlist.size()-1;
            intent.putExtra("index",arrlist.get(pos).getPath());
        }
        intent.putExtra("pos",pos);
        sendBroadcast(intent);
        //保存最近一条播放记录
        String save_recent_play=arrlist.get(pos).getPath();
        IOclass iOclass=new IOclass();
        iOclass.save(save_recent_play,MainActivity.this);
    }

    //重写返回按键,连续按两次退出
    long firsttime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondtime=System.currentTimeMillis();
            if(secondtime-firsttime>1000){
                Toast.makeText(MainActivity.this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                firsttime = secondtime; //更新firstTime
                return true;
            }else {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //获取权限
    @Override
    public void onRequestPermissionsResult(int requestCode,String [] permission,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    scanMusic = new ScanMusic();
                    arrlist = scanMusic.query(arrlist, this);
                    for (int i = 0; i < arrlist.size(); i++) {
                        context_song.add(arrlist.get(i).getText_song());
                        context_path.add(arrlist.get(i).getPath());
                        context_time.add(arrlist.get(i).getTime());
                    }
                }else {
                    Toast.makeText(this,"存储权限被拒绝",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if(grantResults.length<=0||grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"读取本机识别码权限被拒绝",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    //下拉刷新
    private void refresh_reaction(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    //加载toolbar
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    //标题栏点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:    //侧滑菜单
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.search:   //查找歌曲
                Intent intent1=new Intent(MainActivity.this,SearchActivity.class);
                intent1.putExtra("contex_path",context_path);
                intent1.putExtra("contex_song",context_song);
                startActivity(intent1);
                break;
            case R.id.Favorite:    //收藏歌曲
                Intent intent=new Intent(MainActivity.this,FavoriteMusic.class);
                startActivity(intent);
                break;
            case R.id.Position:   //歌曲定位
                int index = pos;
                View v = listView.getChildAt(1);
                int top = (v == null) ? 0 : v.getTop();
                listView.setSelectionFromTop(index, top);
                Toast.makeText(this,context_song.get(pos),Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"删除",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    //广播
    public class MyBrodcastRecriver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if(intent.getAction().equals("ACTION_ALLTIME")){
                int time=intent.getIntExtra("all_time",0);
                //设置播放时间总长
                CalculateClass calculateClass1=new CalculateClass();
                maxtime.setText(calculateClass1.setTime(time));
                seekBar.setMax(time);
                play_music.setBackgroundResource(R.mipmap.ic_pause_play);
                isplay=true;
            } else if (intent.getAction().equals("ACTION_CURRENTTIME")) {
                int Currentime = intent.getIntExtra("current_time",0);
                //当前播放时长
                CalculateClass calculateClass=new CalculateClass();
                currenttime.setText(calculateClass.setTime(Currentime));
                seekBar.setProgress(Currentime);
            }
            //完成播放
            else if(intent.getAction().equals("COMPLETE_PLAY")){
                setNext_music();
            }
            //设置当前位置
            else if(intent.getAction().equals("LAST_POS")){
                pos=intent.getIntExtra("lastpos",0);
            }
            //监听耳机插孔状态
            else if(intent.hasExtra("state")) {
                if (intent.getIntExtra("state", 0) == 0) {
                    isplay=false;
                    play_music.setBackgroundResource(R.mipmap.ic_play_music);
                }
            }
        }
    }

    //注册广播
    public void zhuce() {
        //描述Intent的属性
        IntentFilter mintentfilter = new IntentFilter();
        mintentfilter.addAction("ACTION_ALLTIME");   //总时间
        mintentfilter.addAction("ACTION_CURRENTTIME");//当前时间
        mintentfilter.addAction("COMPLETE_PLAY");   //当前歌曲播放完成
        mintentfilter.addAction("LAST_POS");   //当前位置
        //耳机插孔插入和拔出
        mintentfilter.addAction("android.intent.action.HEADSET_PLUG");
        registerReceiver(mybrodcast, mintentfilter);
    }
    @Override

    //退出
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mybrodcast);
        stopService(s_intent);
        musicNotification.closenotify();
    }
}
