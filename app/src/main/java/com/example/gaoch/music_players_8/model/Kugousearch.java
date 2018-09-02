package com.example.gaoch.music_players_8.model;
import java.net.*;
import java.io.*;
import java.util.regex.*;

public class Kugousearch {
	
	 public static String getPage(String arg) throws IOException 
	   {
		String result;
		String urlString = "";
	      
	      
	         URL url = new URL(arg);//("http://m.kugou.com/app/i/getSongInfo.php?hash=b0fe09d66f664d62626ce226765cd98e&cmd=playInfo");
	         URLConnection urlConnection = url.openConnection();
	         HttpURLConnection connection = null;
	         
	         connection = (HttpURLConnection) urlConnection;
	        
	         BufferedReader in = new BufferedReader(
	         new InputStreamReader(connection.getInputStream()));
	         
	         String current;
	         while((current = in.readLine()) != null)
	         {
	            urlString += current;
	            //urlString += '\n';
	            
	         }
	         result =urlString;
	         
	        // System.out.println(urlString);
	         
	      
	         return result;
	}
	 
	public static String getSongInfo(String song1) throws IOException{
		String song=URLEncoder.encode(song1, "utf-8");
		String url1="http://mobilecdn.kugou.com/api/v3/search/song?showtype=14&highlight=em&pagesize=20&iscorrect=1&tag_aggr=1&tagtype=%E5%85%A8%E9%83%A8&plat=0&sver=5&keyword="+song+"&correct=1&version=8493&page=1&tag=1&with_res_tag=1&qq-pf-to=pcqq.c2c";
		//String url1=URLEncoder.encode(url2, "utf-8");
		String regex = "\\{.*\\}";
		
		Pattern p = Pattern.compile(regex);
		
		String result= getPage(url1);
		Matcher matcher = p.matcher(result);
		matcher.find();
		
		return matcher.group();
		
		
		
	}
	public static String getPlay(String hash) throws IOException{
		String url1="http://www.kugou.com/yy/index.php?r=play/getdata&hash="+hash;
		String result= getPage(url1);
		return result;
		
		
	}
	
	public static String gethotcom(String hash) throws IOException{
		
		String url = "http://m.comment.service.kugou.com/index.php?r=comments/getCommentWithLike&code=fc4be23b4e972707f36b8a828a93ba8a&extdata="+hash+"&p=2&pagesize=20&ver=1.01&kugouid=0&clienttoken=&appid=1005&clientver=8493&mid=141725785412179493459649512441110495738&clienttime=1491284519&key=7f5b3387b104559c6abd1380f028e77e&qq-pf-to=pcqq.c2c";
		String html = getPage(url);
		return  html ;
		
		
	}
	
	
	
	
}
