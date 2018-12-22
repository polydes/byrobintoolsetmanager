package com.byrobingames.manager.reader;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import misc.Version;


public class ReadGithubApi {
	
	private static String accessToken = "a8aadf95cdb34199b8dddadc45ecc409549afb86"; //public access-token for 5000 request per hour.

	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	
	public static String latestReposVersion(String repos_id) {
		try{
			String reposLocation = "https://api.github.com/repos/byrobingames/"+ repos_id +"/releases/latest?access_token=" + accessToken;
			JSONObject json = readJsonFromUrl(reposLocation);
			
			return json.get("tag_name").toString();
			
		}catch (Exception e){
			e.printStackTrace();
	    	return null;
		}		
	}
	  
	public static boolean checkVersion(String repos_id, Version reposVersion) {
		try{
			
			Version newVersion = new Version(latestReposVersion(repos_id));
		  
			if(newVersion.compareTo(reposVersion) == 1){
				return true;
			}else{
				return false;
			}
	      
		} catch (Exception e) {
	    	e.printStackTrace();
	    	return false;
	    }
	}
	
	public static String getDownloadURL(String repos_id){
		
		try{
			
			String version = latestReposVersion(repos_id);
			String downloadString;
			if(version.equals("null")){
				downloadString = "null";
			}else{
				if(repos_id.equals("byrobintoolsetmanager")){
					downloadString = " https://github.com/byrobingames/"+ repos_id +"/releases/download/"+ version +"/"+ repos_id + "34.zip";
				}else{
					downloadString = " https://github.com/byrobingames/"+ repos_id +"/releases/download/"+ version +"/"+ repos_id + ".zip";
				}
			}
			
			return downloadString;
			
		}catch (Exception e){
			e.printStackTrace();
	    	return null;
		}
		
	}

}
