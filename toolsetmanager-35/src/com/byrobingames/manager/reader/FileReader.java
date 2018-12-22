package com.byrobingames.manager.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import com.byrobingames.manager.ByRobinGameExtension;
import com.byrobingames.manager.editor.NotifElement;
import com.byrobingames.manager.writer.FileWriter;

import stencyl.core.lib.Game;
import stencyl.sw.util.FileHelper;
import stencyl.sw.util.Locations;

public class FileReader {
	
	private static final Logger log = Logger.getLogger(FileWriter.class);
	private final Game game;
	private final String projectDir;

	final String sourceDir;
	final String sourceScriptsDir;
	
	public static String jsonNotifString ="";
	
	public FileReader(Game game)
	{
		
		this.game = game;
		this.projectDir = Locations.getHXProjectDir(game);
		this.sourceDir = Locations.getPath(projectDir, "Source");
		this.sourceScriptsDir = Locations.getPath(sourceDir, "scripts");
		
	}
	
	public void readWebViewHTMLFile() throws FileNotFoundException, IOException{
		String datafolderlocation = Locations.getExtensionGameDataLocation(game, "com.byrobingames.manager");
		ByRobinGameExtension.WVHTMLCODE = FileHelper.readFileToString(new File(datafolderlocation + "webview.html"));
	}
	
	public void readNotifJSONFile() throws JSONException, FileNotFoundException, IOException
	{
		String datafolderlocation = Locations.getExtensionGameDataLocation(game, "com.byrobingames.manager");
				
		jsonNotifString = FileHelper.readFileToString(new File(datafolderlocation + "notif.json"));
		JSONArray a = new JSONArray(jsonNotifString);
		
		ArrayList<NotifElement> arraynotif = new ArrayList<NotifElement>();
		
		if(a.length() != 0){
			log.info(a.get(0));
			for (int i=0; i < a.length(); i++) {
				JSONArray ja = new JSONArray(a.get(i).toString());
				
				log.info(ja.get(0));
				log.info(ja.get(1));
				log.info(ja.get(2));
				log.info(ja.get(3));
				log.info(ja.get(4));
				
				NotifElement ne = new NotifElement();
				ne.setNotifid(Integer.valueOf(ja.get(0).toString()));
				ne.setTitle(ja.get(1).toString());
				ne.setMessage(ja.get(2).toString());
				ne.setTimeinterval(Integer.valueOf(ja.get(3).toString()));
				ne.setDoevery(ja.get(4).toString());
				
				arraynotif.add(ne);
	        }
			ByRobinGameExtension.NOTIFLIST = arraynotif;
		}
		
	}

}
