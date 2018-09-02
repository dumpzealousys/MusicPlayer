package com.example.gaoch.music_players_8.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaoch.music_players_8.controller.Comments;
import com.example.gaoch.music_players_8.R;

import java.util.ArrayList;

/**
 * Created by gaoch on 2017-05-13.
 */

public class MyAdapter extends ArrayAdapter<String> {
    private int resourceId;
    private ArrayList<String> arrayList;
    Context context;
    int pos;
    public MyAdapter(Context context, int textViewResourceId, ArrayList<String> obj){
        super(context,textViewResourceId,obj);
        arrayList=new ArrayList<String>();
        this.context=context;
        resourceId=textViewResourceId;
        arrayList=obj;
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent){
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.add_to_list=(ImageView) view.findViewById(R.id.add_to_list);
            viewHolder.comment_view=(ImageView) view.findViewById(R.id.view_commands);
            viewHolder.song_text=(TextView) view.findViewById(R.id.text_music);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }
        String str=arrayList.get(position);
        DealingString dealingString=new DealingString();
        viewHolder.song_text.setText(dealingString.deal_adapter(str));
        final String op= viewHolder.song_text.getText().toString();
        //跳转到查看评论活动界面
        viewHolder.comment_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String para= op;
                Intent intent=new Intent(context, Comments.class);
                intent.putExtra("info",para);
                context.startActivity(intent);
            }
        });
        //添加歌曲进入收藏列表
        viewHolder.add_to_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String favorite_music=arrayList.get(position);
                IOclass iOclass=new IOclass();
                ArrayList<String> songlist=new ArrayList<String>();
                songlist=iOclass.load_all_music(context);
                int pos=0;
                for(int i=0;i<songlist.size();i++){
                    if(favorite_music.contains(songlist.get(i))){
                        pos=i;
                        break;
                    }
                }
                //判断是否存在列表中
                String str=Integer.toString(pos);
                if(iOclass.is_in_list(str+"#"+favorite_music,context)){
                    iOclass.save_to_favorite(str+"#"+favorite_music,context);
                    Toast.makeText(context,"添加到收藏列表",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(context,"已存在收藏列表",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
class ViewHolder{
    ImageView add_to_list;
    ImageView comment_view;
    TextView song_text;
}
