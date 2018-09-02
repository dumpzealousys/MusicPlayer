package com.example.gaoch.music_players_8.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URLDecoder;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class yinyue {
	
	
	public static String fromwang(String SongName,String SingerName) throws Exception{
		//String songname = "晴天";
		//String singername="周杰伦";
		//String html1= wangsearch.getPage(songname);
		//System.out.println(html1);
		String hash= wanginfo.getHash(SongName, SingerName);
		String com=wanginfo.commentAPI(hash);
		//System.out.println(com);
		
		return wanginfo.gethotCom(com);
		
		
	}
	
	
	
	public static String fromkuwo(String SongName,String SingerName) {
		//System.out.println("Hello World");
		//String SongName="晴天";
		//String SingerName="周杰伦";
		//String html= Kuwosearch.getSongInfo(SongName);
		//System.out.println(html);
		String hash1=Kuwoinfo.getHash(SongName, SingerName);
		//System.out.println(hash1);
		String html2= Kuwosearch.getcom(hash1);//这个才是热评
		//System.out.println(html2);
		String html3=Kuwosearch.gethotcom(hash1);
		
		//Kuwoinfo.gethotCom(html2);//这个是热评
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		return  "精彩评论"+'\n'+'\n'+Kuwoinfo.gethotCom(html2)+'\n'+"最新评论"+'\n'+'\n'+Kuwoinfo.gethotCom(html3);

		
		
		
		
		
		
	}
	
	public static void main(String []args) throws Exception{
		//String SongName="leaving on a jet plane";
		//String SingerName="john";
		String SongName="晴天";
		String SingerName="周杰伦";
		
		/*
		
		BufferedReader br = new BufferedReader(new
                InputStreamReader(System.in));
		
		
		try {
			//System.out.println("请输入歌曲名，然后按回车");
			SongName=br.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {//System.out.println("请输入歌手名，然后按回车");
			SingerName=br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		System.out.println("酷狗的");
		System.out.println("暂时没有热评");
		System.out.println();
		fromkugou(SongName,SingerName);
		System.out.println();
		System.out.println();
		System.out.println();
		
		
		System.out.println("酷我的");
		System.out.println();
		fromkuwo(SongName,SingerName);
		System.out.println();
		System.out.println();
		System.out.println();
		
		
		System.out.println("网易云的");
		System.out.println();
		fromwang(SongName,SingerName);
		System.out.println();
		System.out.println();
		System.out.println();
		
		
	}
	
	
	public static String fromkugou(String SongName,String SingerName) throws IOException {
		
		
		BufferedReader br = new BufferedReader(new
                InputStreamReader(System.in));
		
		
		String hash1=Kugouinfo.getHash(SongName, SingerName,"");
		//String html1 = Kugousearch.getPla(hash1);
		//String playurl =dealinfo.getplay(html1);
		
		//System.out.println(playurl);
		String html2= Kugousearch.gethotcom(hash1);
		//System.out.println(html2);
		
		//Kugouinfo.gethotCom(html2);
		return Kugouinfo.getCom(html2);
		//System.out.println(html2);
		
		
		
	}

	/*
	public static void main1(String []args) {
	       //System.out.println("Hello World");
		//	kugou.fe();
		String SongName="晴天";
		String SingerName="周杰伦";
		String albumName="叶惠美";
		String hash1="";
		String html=Kugousearch.getSongInfo(SongName);
		//System.out.println(html);
		
		JsonReader reader = Json.createReader(new StringReader(html.toString()));
		
		JsonObject personObject = reader.readObject();
       
        reader.close();
        
        JsonObject dataObject = personObject.getJsonObject("data");
        JsonArray songArray = dataObject.getJsonArray("info");
        
        for(JsonValue x :songArray){
        	JsonReader y = Json.createReader(new StringReader(x.toString()));
    		JsonObject z = y.readObject();
            
    		y.close(); 
    		
    		String singer=z.getString("singername");
    		String songname=z.getString("songname");
    		if(singer.indexOf(SingerName)!=-1&&songname.indexOf(SongName)!=-1)
    		{
           // System.out.println(z.getString("singername")+"  "+z.getString("filename")+"  "+
            		//z.getString("album_name")+"  "+ z.getString("songname"));
            
            
            	hash1=z.getString("hash");
            	
            	
            	System.out.println(hash1);
            	break;
            	
            }
           	
        }
        
        
     }
     */
}
