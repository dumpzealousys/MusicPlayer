package com.example.gaoch.music_players_8.model;

import android.util.Log;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class Kuwoinfo {
	
	public static String getHash(String SongName,String SingerName) {

	       //System.out.println("Hello World");
		//	kugou.fe();
		//String SongName="晴天";
		//String SingerName="周杰伦";
		String albumName="叶惠美";
		String hash1="";
		String html=Kuwosearch.getSongInfo(SongName);
		//System.out.println(html);
		
		JsonReader reader = Json.createReader(new StringReader(html.toString()));
		
		JsonObject personObject = reader.readObject();
 
		reader.close();
  
		//JsonObject dataObject = personObject.getJsonObject("data");
		JsonArray songArray = personObject.getJsonArray("abslist");
  
		for(JsonValue x :songArray){
			JsonReader y = Json.createReader(new StringReader(x.toString()));
			JsonObject z = y.readObject();
      
		y.close(); 
		
		String singer=z.getString("ARTIST");
		String songname=z.getString("SONGNAME");

			Log.d("SongName",SongName);
			Log.d("SingerName",SingerName);
			Log.d("songname",songname);
			Log.d("singer",singer);
			SongName=SongName.toLowerCase().replaceAll(" ","&nbsp;");
			SingerName=SingerName.toLowerCase().replaceAll(" ","&nbsp;");
			songname=songname.toLowerCase().replaceAll(" ","&nbsp;");
			singer=singer.toLowerCase().replaceAll(" ","&nbsp;");

		if(singer.indexOf(SingerName)!=-1&&songname.indexOf(SongName)!=-1)
		{
     // System.out.println(z.getString("singername")+"  "+z.getString("filename")+"  "+
      		//z.getString("album_name")+"  "+ z.getString("songname"));
      
      
      	hash1=z.getString("MP3RID");
      	
      	
      	//System.out.println(hash1);
      	
      	return hash1.substring(4);
      }
  }
	return hash1.substring(4);
	}
	
	
	
	public static String getplay(String html){
		JsonReader y = Json.createReader(new StringReader(html));
	
		JsonObject z = y.readObject();
		y.close(); 
		JsonObject a = z.getJsonObject("data");
		String url = a.getString("play_url");
		
		
		
		
		
      
		 
		return url;
		
		
	}
	
	public static String gethotCom(String html)  {

		String jjj="";
		JsonReader y = Json.createReader(new StringReader(html));
		
		JsonObject z = y.readObject();
		y.close(); 
		JsonArray a = z.getJsonArray("rows");
		for(JsonValue b :a){
			JsonReader c = Json.createReader(new StringReader(b.toString()));
	 		JsonObject d = c.readObject();

	         
	 		c.close(); 
	 		try {
				//jjj=jjj+"like_num" +" "+d.getString("like_num")+" "+ "msg"+" "+d.getString( "msg")
				//		+" "+"u_name"+" "+URLDecoder.decode(d.getString("u_name"),"UTF-8")+'\n';

				jjj=jjj+URLDecoder.decode(d.getString("u_name"),"UTF-8")+'\n'+d.getString( "msg")+'\n'+
						d.getString("time")+'\n'+d.getString("like_num")+"赞"+'\n'+'\n';

				System.out.println(
						"like_num" +" "+d.getString("like_num")+" "+ "msg"+" "+d.getString( "msg")
						+" "+"u_name"+" "+URLDecoder.decode(d.getString("u_name"),"UTF-8")
						
						
						
						);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		
	 		
			
		}
		return jjj;
	}
	
		public static String getCom(String html) {
			String jjj="";
			
			JsonReader y = Json.createReader(new StringReader(html));
			
	 		JsonObject z = y.readObject();
	 		y.close(); 
	 		JsonArray a = z.getJsonArray("list");
	 		for(JsonValue b :a){
	 			JsonReader c = Json.createReader(new StringReader(b.toString()));
	 	 		JsonObject d = c.readObject();
	 	         
	 	 		c.close();
				jjj=jjj+"content"+" "+d.getString("content")
						+" "+"user_name"+" "+d.getString("user_name")+'\n';

	 	 		System.out.println(
	 	 				//"weight" +" "+d.getString("weight")+" "+
	 	 		"content"+" "+d.getString("content")
	 	 				+" "+"user_name"+" "+d.getString("user_name")
	 	 				
	 	 				
	 	 				
	 	 				);
	 	 		
	 	 		
	 			
	 		}



			return jjj;
	}
	
	

}
