package com.example.gaoch.music_players_8.model;

import android.util.Log;

import java.net.*;


import java.io.*;
import java.util.regex.*;
public class Kuwosearch {
	

	
		
		 public static String getPage(String arg)
		   {
			String result;
			String urlString = "";
		      try
		      {
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
		         
		      }catch(IOException e)
		      {
		         e.printStackTrace();
		      }
		      finally{
		    	  result =urlString;
		      }
			return result;
			
			
			
		   }
		 
		public static String getSongInfo(String song){
			Log.d("789",song);
			String op=song.replaceAll(" ","%20");
			Log.d("987",op);
			String url1="http://search.kuwo.cn/r.s?all="+op+
					"&ft=music&newsearch=1&alflac=1&itemset=web_2013&client=kt&cluster=0&pn=0&rn=100&rformat=json&encoding=utf8&show_copyright_off=1&vipver=MUSIC_8.7.1.0_BCS20";
			
			
			
			//String regex = "\\{.*\\}";
			//Pattern p = Pattern.compile(regex);
			
			String result= getPage(url1);
			//Matcher matcher = p.matcher(result);
			//matcher.find();
			String asd= result.replace('\'', '"');
			Log.d("asd",asd);
			
			return asd;
			
			
			
		}
		
		public static String gethotcom(String hash){
			
			String url = "http://comment.kuwo.cn/com.s?type=get_comment&uid=0&prod=newWeb&digest=15&sid="+hash+"&page=1&rows=20&f=web&gid=e336f832-a3de-4b10-988c-8bb542ed57c2&jpcallback=getCommentListFn&_=1494843616767";
			String html = getPage(url);
			
			String regex = "=(\\{.*\\});";
			Pattern p = Pattern.compile(regex);
			Matcher matcher = p.matcher(html);
			matcher.find();
			return  matcher.group(1);
			
			
		}
		
public static String getcom(String hash){
			
			String url = "http://comment.kuwo.cn/com.s?type=get_rec_comment&uid=0&prod=newWeb&digest=15&sid="+hash+"&page=1&rows=10&f=web&gid=e336f832-a3de-4b10-988c-8bb542ed57c2&jpcallback=getRecCommentListFn&_=14948436160702";
			String html = getPage(url);
			
			String regex = "=(\\{.*\\});";
			Pattern p = Pattern.compile(regex);
			Matcher matcher = p.matcher(html);
			matcher.find();
			return  matcher.group(1);
			
			
		}
		
		
		public static  String tryurl="http://m.kugou.com/app/i/getSongInfo.php?hash=b0fe09d66f664d62626ce226765cd98e&cmd=playInfo";
		
	


}
