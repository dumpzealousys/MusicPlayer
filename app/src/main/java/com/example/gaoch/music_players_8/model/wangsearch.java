package com.example.gaoch.music_players_8.model;
import java.net.*;
import java.math.BigInteger;
//import java.util.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

//import yinyue.wangyiyun.EncryptTools;
//import yinyue.wangyiyun.Hex;

import java.io.*;
import java.util.regex.*;
public class wangsearch {

	
	public static String  getPage(String songname) throws IOException  {
		
     
        




        ////////////////////
        Document document = Jsoup.connect("https://music.163.com/api/search/get/").cookie("appver", "1.5.2")
                .data("s",songname ).data("limit", "20")
                .data("type","1").data("offset","0")
                //.header("Referer", "http://music.163.com/")
                .ignoreContentType(true).post();
        //////////////////////////////////


        String result =document.toString().replaceAll("&quot;", "\"");
        
        return result.replaceAll("<.*>","").trim();
    }
	
	

}
