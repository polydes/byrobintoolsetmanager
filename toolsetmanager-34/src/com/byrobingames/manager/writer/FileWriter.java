package com.byrobingames.manager.writer;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import com.byrobingames.manager.ByRobinGameExtension;
import com.byrobingames.manager.editor.NotifElement;
import com.byrobingames.manager.reader.FileReader;
import com.byrobingames.manager.res.Resources;
import com.byrobingames.manager.utils.DownloadEngineEx;

import stencyl.core.lib.Game;
import stencyl.sw.util.FileHelper;
import stencyl.sw.util.Locations;

public class FileWriter {
	
	private static final Logger log = Logger.getLogger(FileWriter.class);
	private final Game game;
	private final String projectDir;

	final String sourceDir;
	final String sourceScriptsDir;
	
	public FileWriter(Game game)
	{
		
		this.game = game;
		this.projectDir = Locations.getHXProjectDir(game);
		this.sourceDir = Locations.getPath(projectDir, "Source");
		this.sourceScriptsDir = Locations.getPath(sourceDir, "scripts");
		
	}
	
	public void writeByRobinAssets() throws IOException
	{
		String s = IOUtils.toString(Resources.getUrlStream("hx/ByRobinAssets.hx"));	
		
		//Tapdaq
		s = s.replace("[TDCLIENTKEY]", ByRobinGameExtension.TDClientKey);
		s = s.replace("[TDIOSAPPID]", ByRobinGameExtension.TDIosAppId);
		s = s.replace("[TDANDROIDAPPID]", ByRobinGameExtension.TDAndroidAppId);
		s = s.replace("[TDTESTADS]", String.valueOf(ByRobinGameExtension.TDTestADS));
		s = s.replace("[TDADTYPEINTERSTITIAL]", ByRobinGameExtension.TDAdTypesInterstitial);
		s = s.replace("[TDADTYPEVIDEO]", ByRobinGameExtension.TDAdTypesVideo);
		s = s.replace("[TDADTYPEREWARDEDVIDEO]", ByRobinGameExtension.TDAdTypesRewardedVideo);
		s = s.replace("[TDADTYPEMOREAPPS]", ByRobinGameExtension.TDAdTypeMoreApps);
		//Applovin
		s = s.replace("[APPLOTESTADS]", String.valueOf(ByRobinGameExtension.APPLOTESTADS));
		//Facebookads
		s = s.replace("[FBBANNERPLACE]", ByRobinGameExtension.FBBANNERPLACE);
		s = s.replace("[FBRECTBANNERPLACE]", ByRobinGameExtension.FBRECTBANNERPLACE);
		s = s.replace("[FBINTERSTITIALPLACE]", ByRobinGameExtension.FBINTERSTITIALPLACE);
		s = s.replace("[FBTESTADS]", String.valueOf(ByRobinGameExtension.FBTESTADS));
		//Heyzap
		s = s.replace("[HZPUBLISHERID]", ByRobinGameExtension.HZPUBLISHERID);
		
		//Leadbolt
		s = s.replace("[LBIOSAPIKEY]", ByRobinGameExtension.LBIOSAPIKEY);
		s = s.replace("[LBANDROIDAPIKEY]", ByRobinGameExtension.LBANDROIDAPIKEY);
		
		//StartApp
		s = s.replace("[SAIOSAPPID]", ByRobinGameExtension.SAIOSAPPID);
		s = s.replace("[SAANDROIDAPPID]", ByRobinGameExtension.SAANDROIDAPPID);
		
		//UnityAds
		s = s.replace("[UAIOSGAMEID]", ByRobinGameExtension.UAIOSGAMEID);
		s = s.replace("[UAANDROIDGAMEID]", ByRobinGameExtension.UAANDROIDGAMEID);
		s = s.replace("[UATESTADS]",  String.valueOf(ByRobinGameExtension.UATESTADS));
		s = s.replace("[UADEBUGMODE]",  String.valueOf(ByRobinGameExtension.UADEBUGMODE));
		
		//Vungle
		s = s.replace("[VUIOSAPPID]", ByRobinGameExtension.VUIOSAPPID);
		s = s.replace("[VUANDROIDAPPID]", ByRobinGameExtension.VUANDROIDAPPID);
		
		FileHelper.writeStringToFile(sourceScriptsDir + "ByRobinAssets.hx", s);
		
	}
	
	public void writeAppLovinIncludeFile() throws IOException{
		String extensionDir = "";
		if(DownloadEngineEx.checkEngineEx("applovin-master") && !DownloadEngineEx.checkEngineEx("applovin")){
			extensionDir = Locations.getGameExtensionsLocation()+"applovin-master";
		}else if(DownloadEngineEx.checkEngineEx("applovin") && !DownloadEngineEx.checkEngineEx("applovin-master")){
			extensionDir = Locations.getGameExtensionsLocation()+"applovin";
		}else{
			return;
		}
		
		String s = IOUtils.toString(Resources.getUrlStream("project/applovin/include.xml"));
		
		s = s.replace("[APPLOVINSDKKEY]", (!ByRobinGameExtension.APPLOSDKKEY.isEmpty()) ? "<setenv name=\"APPLOVIN_SDKKEY\" value=\"" + ByRobinGameExtension.APPLOSDKKEY + "\" />" : "");
		
		FileHelper.writeStringToFile(Locations.getPath(extensionDir) + "include.xml", s);
	}
	
	public void writeTapdaqIncludeFile() throws IOException{
		String extensionDir = "";
		if(DownloadEngineEx.checkEngineEx("tapdaq-master") && !DownloadEngineEx.checkEngineEx("tapdaq")){
			extensionDir = Locations.getGameExtensionsLocation()+"tapdaq-master";
		}else if(DownloadEngineEx.checkEngineEx("tapdaq") && !DownloadEngineEx.checkEngineEx("tapdaq-master")){
			extensionDir = Locations.getGameExtensionsLocation()+"tapdaq";
		}else{
			return;
		}
		
		String s = IOUtils.toString(Resources.getUrlStream("project/tapdaq/include.xml"));
		
		s = s.replace("[SETENVTPADCOLONY]", (ByRobinGameExtension.tpadcolonyen) ? "<setenv name=\"TPADCOLONY_ENABLED\" value=\"" + String.valueOf(ByRobinGameExtension.tpadcolonyen) + "\" />" : "");
		s = s.replace("[SETENVTPAPPLOVIN]", (ByRobinGameExtension.tpapplovinen) ? "<setenv name=\"TPAPPLOVIN_ENABLED\" value=\"" + String.valueOf(ByRobinGameExtension.tpapplovinen) + "\" />" : "");
		s = s.replace("[SETENVTPFACEBOOK]", (ByRobinGameExtension.tpfacebooken) ? "<setenv name=\"TPFACEBOOK_ENABLED\" value=\"" + String.valueOf(ByRobinGameExtension.tpfacebooken) + "\" />" : "");
		s = s.replace("[SETENVTPCHARTBOOST]", (ByRobinGameExtension.tpchartboosten) ? "<setenv name=\"TPCHARTBOOST_ENABLED\" value=\"" + String.valueOf(ByRobinGameExtension.tpchartboosten) + "\" />" : "");
		s = s.replace("[SETENVTPHYPRMX]", (ByRobinGameExtension.tphyprmxen) ? "<setenv name=\"TPHYPRMX_ENABLED\" value=\"" + String.valueOf(ByRobinGameExtension.tphyprmxen) + "\" />" : "");
		s = s.replace("[SETENVTPINMOBI]", (ByRobinGameExtension.tpinmobien) ? "<setenv name=\"TPINMOBI_ENABLED\" value=\"" + String.valueOf(ByRobinGameExtension.tpinmobien) + "\" />" : "");
		s = s.replace("[SETENVTPIRONSOURCE]", (ByRobinGameExtension.tpironsourceen) ? "<setenv name=\"TPIRONSOURCE_ENABLED\" value=\"" + String.valueOf(ByRobinGameExtension.tpironsourceen) + "\" />" : "");
		s = s.replace("[SETENVTPMOPUB]", (ByRobinGameExtension.tpmopuben) ? "<setenv name=\"TPMOPUB_ENABLED\" value=\"" + String.valueOf(ByRobinGameExtension.tpmopuben) + "\" />" : "");
		s = s.replace("[SETENVTPRECEPTIV]", (ByRobinGameExtension.tpreceptiven) ? "<setenv name=\"TPRECEPTIV_ENABLED\" value=\"" + String.valueOf(ByRobinGameExtension.tpreceptiven) + "\" />" : "");
		s = s.replace("[SETENVTPTAPJOY]", (ByRobinGameExtension.tptapjoyen) ? "<setenv name=\"TPTAPJOY_ENABLED\" value=\"" + String.valueOf(ByRobinGameExtension.tptapjoyen) + "\" />" : "");
		s = s.replace("[SETENVTPUNITYADS]", (ByRobinGameExtension.tpunityadsen) ? "<setenv name=\"TPUNITYADS_ENABLED\" value=\"" + String.valueOf(ByRobinGameExtension.tpunityadsen) + "\" />" : "");
		s = s.replace("[SETENVTPVUNGLE]", (ByRobinGameExtension.tpvungleen) ? "<setenv name=\"TPVUNGLE_ENABLED\" value=\"" + String.valueOf(ByRobinGameExtension.tpvungleen) + "\" />" : "");
		
		s = s.replace("[SETNAMETPADCOLONY]", (ByRobinGameExtension.tpadcolonyen) ? "<set name=\"tpadcolony\"/>" : "");
		s = s.replace("[SETNAMETPAPPLOVIN]", (ByRobinGameExtension.tpapplovinen) ? "<set name=\"tpapplovin\"/>" : "");
		s = s.replace("[SETNAMETPFACEBOOK]", (ByRobinGameExtension.tpfacebooken) ? "<set name=\"tpaudiencenetwork\"/>" : "");
		s = s.replace("[SETNAMETPCHARTBOOST]", (ByRobinGameExtension.tpchartboosten) ? "<set name=\"tpchartboost\"/>" : "");
		s = s.replace("[SETNAMETPHYPRMX]", (ByRobinGameExtension.tphyprmxen) ? "<set name=\"tphyprmx\"/>" : "");
		s = s.replace("[SETNAMETPINMOBI]", (ByRobinGameExtension.tpinmobien) ? "<set name=\"tpinmobi\"/>" : "");
		s = s.replace("[SETNAMETPIRONSOURCE]", (ByRobinGameExtension.tpironsourceen) ? "<set name=\"tpironsource\"/>" : "");
		s = s.replace("[SETNAMETPMOPUB]", (ByRobinGameExtension.tpmopuben) ? "<set name=\"tpmopub\"/>" : "");
		s = s.replace("[SETNAMETPRECEPTIV]", (ByRobinGameExtension.tpreceptiven) ? "<set name=\"tpreceptive\"/>" : "");
		s = s.replace("[SETNAMETPTAPJOY]", (ByRobinGameExtension.tptapjoyen) ? "<set name=\"tptapjoy\"/>" : "");
		s = s.replace("[SETNAMETPUNITYADS]", (ByRobinGameExtension.tpunityadsen) ? "<set name=\"tpunityads\"/>" : "");
		s = s.replace("[SETNAMETPVUNGLE]", (ByRobinGameExtension.tpvungleen) ? "<set name=\"tpvungle\"/>" : "");
		
		FileHelper.writeStringToFile(Locations.getPath(extensionDir) + "include.xml", s);
	}
	
	public void writeWebViewHTMLFile() throws IOException{
		
		if(!DownloadEngineEx.checkEngineEx("webview2.0")){
			return;
		}
		
		String s = ByRobinGameExtension.WVHTMLCODE;
		String datafolderlocation = Locations.getExtensionGameDataLocation(game, "com.byrobingames.manager");
		FileHelper.writeStringToFile(Locations.getPath(datafolderlocation) + "webview.html", s);
		
		if(!DownloadEngineEx.checkEngineEx("webview2.0") || !DownloadEngineEx.checkIfEnabled("webview2.0") ){
			return;
		}
		
		String assetsDataDir = Locations.getPath(projectDir, "Assets/data/com.byrobingames.manager");
		FileHelper.writeStringToFile(Locations.getPath(assetsDataDir) + "webview.html", s);
	}
	
	public void writeNotifJSONFile() throws IOException
	{
		if(!DownloadEngineEx.checkEngineEx("localnotifications")){
			return;
		}
		
		String datafolderlocation = Locations.getExtensionGameDataLocation(game, "com.byrobingames.manager");
		
		JSONArray notifjson = new JSONArray();
		
		for (NotifElement notifelement : ByRobinGameExtension.NOTIFLIST){
			
			JSONArray jsonarray = new JSONArray();
			jsonarray.put(notifelement.getNotifid());
			jsonarray.put(notifelement.getTitle());
			jsonarray.put(notifelement.getMessage());
			jsonarray.put(notifelement.getTimeinterval());
			jsonarray.put(notifelement.getDoevery());
			
			notifjson.put(jsonarray);
		}
		
		FileReader.jsonNotifString = notifjson.toString();
		FileHelper.writeStringToFile(Locations.getPath(datafolderlocation) + "notif.json", FileReader.jsonNotifString);
		
		if(!DownloadEngineEx.checkEngineEx("localnotifications") || !DownloadEngineEx.checkIfEnabled("localnotifications") ){
			return;
		}
		
		String assetsDataDir = Locations.getPath(projectDir, "Assets/data/com.byrobingames.manager");
		
		FileHelper.writeStringToFile(Locations.getPath(assetsDataDir) + "notif.json", FileReader.jsonNotifString);
	}
}
