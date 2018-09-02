package com.example.gaoch.music_players_8.model;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.*;


public class Kugouinfo {
	public static String getHash(String theSongName,String SingerName,String album) throws IOException {
	       //System.out.println("Hello World");
		//	kugou.fe();
		//String SongName="晴天";
		//String SingerName="周杰伦";
		String regex   = "(.+)[(（].*[)）]";
	    Pattern p  = Pattern.compile(regex);
	    String str = theSongName;
	    Matcher  m   = p.matcher(str);
	    String result="";
	    if (m.find())
	    {result= m.group(1);}
	    else{
	    	result=str;
	    	}
	    String SongName=result;
		
		
		
		
		
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
 		
 		if(singer.toLowerCase().indexOf(SingerName.toLowerCase())!=-1&&
 				songname.toLowerCase().indexOf(SongName.toLowerCase())!=-1)
 		{
        // System.out.println(z.getString("singername")+"  "+z.getString("filename")+"  "+
         		//z.getString("album_name")+"  "+ z.getString("songname"));
         
         
         	hash1=z.getString("hash");
         	
         	
         	//System.out.println(hash1);
         	return hash1;
         }
     }
	return hash1;
	}
	
	
	
	public static String getplay(String html){
		JsonReader y = Json.createReader(new StringReader(html));
	
 		JsonObject z = y.readObject();
 		y.close(); 
 		JsonObject a = z.getJsonObject("data");
 		String url = a.getString("play_url");
 		
 		
 		
 		
 		
         
 		 
		return url;
		
		
	}
	/*
	public static void gethotCom(String html) {
		
		
		JsonReader y = Json.createReader(new StringReader(html));
		
 		JsonObject z = y.readObject();
 		y.close(); 
 		JsonArray a = z.getJsonArray("weightList");
 		for(JsonValue b :a){
 			JsonReader c = Json.createReader(new StringReader(b.toString()));
 	 		JsonObject d = c.readObject();
 	         
 	 		c.close(); 
 	 		System.out.println(
 	 				"weight" +" "+d.getString("weight")+" "+"content"+" "+d.getString("content")
 	 				+" "+"user_name"+" "+d.getString("user_name")
 	 				
 	 				
 	 				
 	 				);
 	 		
 	 		
 			
 		}
	}
	*/
	//返回的 weighList 暂时为空
 		public static String  getCom(String html) {
 			
 			String jjj="";
 			JsonReader y = Json.createReader(new StringReader(html));
 			
 	 		JsonObject z = y.readObject();
 	 		y.close(); 
 	 		JsonArray a = z.getJsonArray("list");
			jjj=jjj+"最近评论"+'\n'+'\n';
 	 		for(JsonValue b :a){
 	 			JsonReader c = Json.createReader(new StringReader(b.toString()));
 	 	 		JsonObject d = c.readObject();
 	 	         
 	 	 		c.close();

				//jjj=jjj+"content"+" "+d.getString("content")
				//		+" "+"user_name"+" "+d.getString("user_name")+'\n';




				jjj=jjj+d.getString("user_name")+'\n'+d.getString("content")+'\n'+
						d.getString("updatetime")+'\n'+d.getJsonObject("like").getInt("count")
						+"赞"+'\n'+'\n';





 	 	 		System.out.println(
 	 	 				//"weight" +" "+d.getString("weight")+" "+
 	 	 		"content"+" "+d.getString("content")
 	 	 				+" "+"user_name"+" "+d.getString("user_name")
 	 	 				
 	 	 				
 	 	 				
 	 	 				);
 	 	 		
 	 	 		
 	 			
 	 		}
 				
 		
		return jjj;
		
	}
	
	
	
	
	
	

}
