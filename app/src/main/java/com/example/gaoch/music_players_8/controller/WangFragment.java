package com.example.gaoch.music_players_8.controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gaoch.music_players_8.controller.Comments;
import com.example.gaoch.music_players_8.R;


public class WangFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
       // MainActivity activity= (MainActivity) getActivity();
       // String asd=activity.
       // TextView textView= (TextView) getView().findViewById(R.id.textwang);
       // textView.setText("456");

       View view = inflater.inflate(R.layout.wyy,container,false);
        //TextView textView= (TextView) view.findViewById(R.id.textwang);
        //textView.setText("456");
        Comments activity= (Comments) getActivity();







        return view;

    }


}
