package com.example.gaoch.music_players_8.model;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import android.util.Base64;
//import java.util.Base64;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;


public class wanginfo {
	
	
	public static String getHash(String SongName,String SingerName) throws IOException {
	       //System.out.println("Hello World");
		Integer m=0;
		//	kugou.fe();
		//String SongName="晴天";
		//String SingerName="周杰伦";
		String albumName="叶惠美";
		int hash1=0;
		String html="";
		
		html = wangsearch.getPage(SongName);
		
		
		//System.out.println(html);
		
		JsonReader reader = Json.createReader(new StringReader(html.toString()));
		
		JsonObject personObject = reader.readObject();
 
		  reader.close();
		  
		  JsonObject dataObject = personObject.getJsonObject("result");
		  JsonArray songArray = dataObject.getJsonArray("songs");
		  
		  for(JsonValue x :songArray){
		  	JsonReader y = Json.createReader(new StringReader(x.toString()));
				JsonObject z = y.readObject();
				String singer="";
				y.close(); 
				
				JsonArray art = z.getJsonArray("artists");
				for(JsonValue g :art){
				  	JsonReader f = Json.createReader(new StringReader(g.toString()));
						JsonObject h = f.readObject();
						singer=h.getString("name");
				}
				
		
		
		
		//String singer=z.getString("singername");
		String songname=z.getString("name");
		if(singer.indexOf(SingerName)!=-1&&songname.indexOf(SongName)!=-1)
		{
     // System.out.println(z.getString("singername")+"  "+z.getString("filename")+"  "+
      		//z.getString("album_name")+"  "+ z.getString("songname"));
      
      
      	hash1=z.getInt("id");
      	m=hash1;
      	
      	
      	//System.out.println(hash1);
      	return m.toString();
      }
  }
	return m.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static class Hex {
		 
	    /**
	     * 用于建立十六进制字符的输出的小写字符数组
	     */
	    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
	            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	 
	    /**
	     * 用于建立十六进制字符的输出的大写字符数组
	     */
	    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
	            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	 
	    /**
	     * 将字节数组转换为十六进制字符数组
	     *
	     * @param data byte[]
	     * @return 十六进制char[]
	     */
	    public static char[] encodeHex(byte[] data) {
	        return encodeHex(data, true);
	    }
	 
	    /**
	     * 将字节数组转换为十六进制字符数组
	     *
	     * @param data        byte[]
	     * @param toLowerCase <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
	     * @return 十六进制char[]
	     */
	    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
	        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	    }
	 
	    /**
	     * 将字节数组转换为十六进制字符数组
	     *
	     * @param data     byte[]
	     * @param toDigits 用于控制输出的char[]
	     * @return 十六进制char[]
	     */
	    protected static char[] encodeHex(byte[] data, char[] toDigits) {
	        int l = data.length;
	        char[] out = new char[l << 1];
	        // two characters form the hex value.
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
	            out[j++] = toDigits[0x0F & data[i]];
	        }
	        return out;
	    }
	 
	    /**
	     * 将字节数组转换为十六进制字符串
	     *
	     * @param data byte[]
	     * @return 十六进制String
	     */
	    public static String encodeHexStr(byte[] data) {
	        return encodeHexStr(data, true);
	    }
	 
	    /**
	     * 将字节数组转换为十六进制字符串
	     *
	     * @param data        byte[]
	     * @param toLowerCase <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
	     * @return 十六进制String
	     */
	    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
	        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	    }
	 
	    /**
	     * 将字节数组转换为十六进制字符串
	     *
	     * @param data     byte[]
	     * @param toDigits 用于控制输出的char[]
	     * @return 十六进制String
	     */
	    protected static String encodeHexStr(byte[] data, char[] toDigits) {
	        return new String(encodeHex(data, toDigits));
	    }
	 
	    /**
	     * 将十六进制字符数组转换为字节数组
	     *
	     * @param data 十六进制char[]
	     * @return byte[]
	     * @throws RuntimeException 如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
	     */
	    public static byte[] decodeHex(char[] data) {
	 
	        int len = data.length;
	 
	        if ((len & 0x01) != 0) {
	            throw new RuntimeException("Odd number of characters.");
	        }
	 
	        byte[] out = new byte[len >> 1];
	 
	        // two characters form the hex value.
	        for (int i = 0, j = 0; j < len; i++) {
	            int f = toDigit(data[j], j) << 4;
	            j++;
	            f = f | toDigit(data[j], j);
	            j++;
	            out[i] = (byte) (f & 0xFF);
	        }
	 
	        return out;
	    }
	 
	    /**
	     * 将十六进制字符转换成一个整数
	     *
	     * @param ch    十六进制char
	     * @param index 十六进制字符在字符数组中的位置
	     * @return 一个整数
	     * @throws RuntimeException 当ch不是一个合法的十六进制字符时，抛出运行时异常
	     */
	    protected static int toDigit(char ch, int index) {
	        int digit = Character.digit(ch, 16);
	        if (digit == -1) {
	            throw new RuntimeException("Illegal hexadecimal character " + ch
	                    + " at index " + index);
	        }
	        return digit;
	    }
	}
	
	
	
	public static class EncryptTools {
	    //AES加密
	    public static String encrypt(String text, String secKey) throws Exception {
	        byte[] raw = secKey.getBytes();
	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	        // "算法/模式/补码方式"
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
	        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
	        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	        byte[] encrypted = cipher.doFinal(text.getBytes());
	       // return Base64.getEncoder().encodeToString(encrypted);

			return Base64.encodeToString(encrypted ,Base64.CRLF);
	    
	}
	  //字符填充
	  public static String zfill(String result, int n) {
	        if (result.length() >= n) {
	            result = result.substring(result.length() - n, result.length());
	        } else {
	            StringBuilder stringBuilder = new StringBuilder();
	            for (int i = n; i > result.length(); i--) {
	                stringBuilder.append("0");
	            }
	            stringBuilder.append(result);
	            result = stringBuilder.toString();
	        }
	        return result;
	    }
	}
	
	public static String commentAPI(String hash) throws Exception {
		
        //私钥，随机16位字符串（自己可改）
        String secKey = "cd950039b25s54b7";
        String text = "{\"username\": \"\", \"rememberLogin\": \"true\", \"password\": \"\",\"offset\": \"0\"}";
        String modulus = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";
        String nonce = "0CoJUm6Qyw8W8jud";
        String pubKey = "010001";
        //2次AES加密，得到params
        String params = EncryptTools.encrypt(EncryptTools.encrypt(text, nonce), secKey);
        StringBuffer stringBuffer = new StringBuffer(secKey);
        //逆置私钥
        secKey = stringBuffer.reverse().toString();
        String hex = Hex.encodeHexStr(secKey.getBytes());
        BigInteger bigInteger1 = new BigInteger(hex, 16);
        BigInteger bigInteger2 = new BigInteger(pubKey, 16);
        BigInteger bigInteger3 = new BigInteger(modulus, 16);
        //RSA加密计算
        BigInteger bigInteger4 = bigInteger1.pow(bigInteger2.intValue()).remainder(bigInteger3);
        String encSecKey= Hex.encodeHexStr(bigInteger4.toByteArray());
        //字符填充
        encSecKey= EncryptTools.zfill(encSecKey, 256);
        //评论获取
        String songurl="https://music.163.com/weapi/v1/resource/comments/R_SO_4_"+hash+"?csrf_token=";
        Document document = Jsoup.connect(songurl)//.cookie("appver", "1.5.0.75771")
                .header("Referer", "http://music.163.com/")
                .data("params", params).data("encSecKey", encSecKey)
                //.data("deviceId","EB6607806C9797542BB7CD66F4E2807E036E175A0A002700001A")
                .ignoreContentType(true).post();
        String result =document.toString().replaceAll("&quot;", "\"");
        
        return result.replaceAll("<.*>","").trim();
        
    }
	
	
	
public static String gethotCom(String html)  {
		
		
	    String jjj="";
		
		JsonReader reader = Json.createReader(new StringReader(html.toString()));
		
		JsonObject personObject = reader.readObject();

		  reader.close();
		  
		  
		  JsonArray songArray = personObject.getJsonArray("hotComments");
	jjj=jjj+"精彩评论"+'\n'+'\n';
		  for(JsonValue x :songArray){
		  	JsonReader y = Json.createReader(new StringReader(x.toString()));
				JsonObject z = y.readObject();
				String singer="";
				y.close(); 
				JsonObject user=z.getJsonObject("user");
				int weight=z.getInt("likedCount");
				String nick = user.getString("nickname");
				String con = z.getString( "content");
              JsonValue time1=z.get("time");
              String kk=time1.toString();
              kk=kk.substring(0,10);
              long iy=Long.parseLong(kk);
              String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date( iy*1000));

                /*
				System.out.println(
						"like_num" +" "+weight+" "+ "msg"+" "+con
						+" "+"name"+" "+nick);
              */
             // jjj=jjj+"" +" "+weight+" "+'\n'+ "msg"+" "+con
             //         +" "+'\n'+"name"+" "+nick+'\n'+"time"+"  "+date+'\n';


              jjj=jjj+nick+'\n'+con+'\n'+date+'\n'+weight+"赞"+'\n'+'\n';


				
		  }
		  JsonArray rty = personObject.getJsonArray("comments");
	jjj=jjj+'\n'+"最新评论"+'\n'+'\n';
		  for(JsonValue x :rty){
			  	JsonReader y = Json.createReader(new StringReader(x.toString()));
					JsonObject z = y.readObject();
					String singer="";
					y.close(); 
					JsonObject user=z.getJsonObject("user");
					int weight=z.getInt("likedCount");
					String nick = user.getString("nickname");
					String con = z.getString( "content");


              JsonValue time1=z.get("time");
              String kk=time1.toString();
              kk=kk.substring(0,10);
              long iy=Long.parseLong(kk);
              String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date( iy*1000));

					/*
					System.out.println(
							"like_num" +" "+weight+" "+ "msg"+" "+con
							+" "+"name"+" "+nick);
                    */

              jjj=jjj+nick+'\n'+con+'\n'+date+'\n'+weight+"赞"+'\n'+'\n';




					
			  }
	jjj=jjj+'\n'+"评论来自酷我音乐，酷狗音乐和网易云音乐"+"\n"+
			"评论功能by"+'\n'
			+"bfuwsiyqiang@163.com\n";
    return jjj;
}
	
	
	

	

}