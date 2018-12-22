package com.byrobingames.manager;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.byrobingames.manager.app.Main;
import com.byrobingames.manager.reader.FileReader;
import com.byrobingames.manager.reader.ReadGithubApi;
import com.byrobingames.manager.utils.DownloadEngineEx;
import com.byrobingames.manager.utils.UpdateToolset;
import com.byrobingames.manager.utils.dialog.DialogBox;
import com.byrobingames.manager.writer.FileWriter;
import com.byrobingames.manager.editor.NotifElement;

import stencyl.core.lib.Game;
import stencyl.sw.ext.GameExtension;
import stencyl.sw.ext.OptionsPanel;
import stencyl.sw.util.dg.MessageDialog;

import misc.Version;

public class ByRobinGameExtension extends GameExtension
{
	//tapdaq
	public static String TDClientKey;
	public static String TDIosAppId;
	public static String TDAndroidAppId;
	public static String TDAdTypesInterstitial;
	public static String TDAdTypesVideo;
	public static String TDAdTypesRewardedVideo;
	public static String TDAdTypeMoreApps;
	public static boolean TDTestADS = false;
	public static boolean tpadcolonyen = false, tpadmoben = false, tpapplovinen = false, tpfacebooken = false, tpchartboosten = false, tphyprmxen = false,
			tpinmobien = false, tpironsourceen = false, tpmopuben = false, tpreceptiven = false, tptapjoyen = false, tpunityadsen = false, tpvungleen = false;
	//applovin
	public static String APPLOSDKKEY;
	public static boolean APPLOTESTADS = false;
	//facebook
	public static String FBBANNERPLACE;
	public static String FBRECTBANNERPLACE;
	public static String FBINTERSTITIALPLACE;
	public static boolean FBTESTADS = false;
	//heyzap
	public static String HZPUBLISHERID;
	//Leadbolt
	public static String LBIOSAPIKEY;
	public static String LBANDROIDAPIKEY;
	//startapp
	public static String SAIOSAPPID;
	public static String SAANDROIDAPPID;
	//unityads
	public static String UAIOSGAMEID;
	public static String UAANDROIDGAMEID;
	public static boolean UATESTADS = false;
	public static boolean UADEBUGMODE = false;
	//vungle
	public static String VUIOSAPPID;
	public static String VUANDROIDAPPID;
	//webview
	public static String WVHTMLCODE;
	//notif
	public static ArrayList<NotifElement> NOTIFLIST;

	
	private static final Logger log = Logger.getLogger(ByRobinGameExtension.class);
	
	/*
	 * Happens when StencylWorks launches. 
	 * 
	 * Avoid doing anything time-intensive in here, or it will
	 * slow down launch.
	 */
	@Override
	public void onStartup()
	{
		super.onStartup();
		
		isInMenu = true;
		menuName = "byRobin Extension Manager";
		
		isInGameCenter = true;
		gameCenterName = "byRobin Extension Manager";
	}
	
	/*
	 * Happens when the extension is told to display.
	 * 
	 * May happen multiple times during the course of the app. 
	 * 
	 * A good way to handle this is to make your extension a singleton.
	 */
	@Override
	public void onActivate()
	{
		log.info("byRobinExtension : Activated");
        
        getDesignModeBlocks();
	}
	
	@Override
	public JPanel onGameCenterActivate()
	{
		return Main.get();
	}

	/*
	 * Happens when StencylWorks closes.
	 *  
	 * Usually used to save things out.
	 */
	@Override
	public void onDestroy()
	{
		log.info("byRobinExtension : Destroyed");
	}
	
	/*
	 * Happens when a game that this extension is enabled for is closed.
	 */
	@Override
	public void onGameWithDataClosed()
	{
		log.info("byRobinExtension : Closed Game " + getGame().getName());
		Main.disposePages();
	}

	/*
	 * Happens when a game that this extension is enabled for is opened.
	 */
	@Override
	public void onGameWithDataOpened()
	{
		log.info("byRobinExtension : Opened Game " + getGame().getName());
		
		int openCount = readIntGameProp("opened", 0);
		putGameProp("opened", ++openCount);
		
		log.info(String.format("This game has been opened %d times with byRobinExtension enabled.", openCount));
		
		readDataFromPref();
			
		try {
			new FileReader(getGame()).readWebViewHTMLFile();
			new FileReader(getGame()).readNotifJSONFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		updateToolset();
	}

	/*
	 * Happens when a game that this extension is enabled for is saved.
	 */
	@Override
	public void onGameWithDataSaved()
	{
		if(Main._instance != null ){
			log.info("byRobinExtension : Saved Game " + getGame().getName());
			Main.get().gameSaved();
		
			writeDataToPref();
		
			try {
				new FileWriter(getGame()).writeWebViewHTMLFile();
				new FileWriter(getGame()).writeNotifJSONFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void onGameBuild(Game game)
	{
		if(Main._instance != null ){
			if(!DownloadEngineEx.checkByRobinEngineEx()){
				
				//downloading byRobin Engine Extension Manager
				log.info("byRobinExtension : downloding byrobin engine extension manager ");
				return;
			}

			try {
				Main.get().writeBuildFiles();
			} catch (IOException e) {
				e.printStackTrace();
				log.info("byRobinExtension : writeHxFile " + e);
			}
		}
	}

	/*
	 * Happens when this extension is enabled for a game.
	 */
	@Override
	public void onInstalledForGame()
	{
		log.info("byRobinExtension : Installed for Game " + getGame().getName());
	}

	/*
	 * Happens when this extension is disabled for a game.
	 */
	@Override
	public void onUninstalledForGame()
	{
		log.info("byRobinExtension : Uninstalled for Game " + getGame().getName());
	}

	/*
	 * Happens when a game is being opened, and the internalVersion of
	 * this extension has been increased since the game was last opened.
	 */
	@Override
	public void updateFromVersion(int version)
	{
		
	}

	
	@Override
	protected boolean hasOptions()
	{
		return false;
	}
	
	/*
	 * Happens when the user requests the Options dialog for your extension.
	 * 
	 * You need to provide the form. We wrap it in a dialog.
	 */
	@Override
	public OptionsPanel onOptions()
	{
		return null;
	}
	
	/*
	 * Happens when the extension is first installed.
	 */
	@Override
	public void onInstall()
	{

	}
	
	/*
	 * Happens when the extension is uninstalled.
	 * 
	 * Clean up files.
	 */
	@Override
	public void onUninstall()
	{
		
	}
	///////////////////
	
	public File getDataLocation(){
		return getDataFolder();
		
	}
	
	public void writeDataToPref(){
		//Tapdaq
		putGameProp("TDClientKey", TDClientKey);
		putGameProp("TDIosAppId", TDIosAppId);
		putGameProp("TDAndroidAppId", TDAndroidAppId);
		putGameProp("TDTestADS", String.valueOf(TDTestADS));
		putGameProp("TDAdTypesInterstitial", TDAdTypesInterstitial);
		putGameProp("TDAdTypesVideo", TDAdTypesVideo);
		putGameProp("TDAdTypesRewardedVideo", TDAdTypesRewardedVideo);
		putGameProp("TDAdTypeMoreApps",TDAdTypeMoreApps);
		putGameProp("TDAdcolonyEn",String.valueOf(tpadcolonyen));
		putGameProp("TDAdMobEn",String.valueOf(tpadmoben));
		putGameProp("TDAppLovinEn",String.valueOf(tpapplovinen));
		putGameProp("TDFacebookEn",String.valueOf(tpfacebooken));
		putGameProp("TDChartboostEn",String.valueOf(tpchartboosten));
		putGameProp("TDHyprmxEn",String.valueOf(tphyprmxen));
		putGameProp("TDInMobiEn",String.valueOf(tpinmobien));
		putGameProp("TDIronsourceEn",String.valueOf(tpironsourceen));
		putGameProp("TDMopupEn",String.valueOf(tpmopuben));
		putGameProp("TDReceptivEn",String.valueOf(tpreceptiven));
		putGameProp("TDTapjoyEn",String.valueOf(tptapjoyen));
		putGameProp("TDUnityadsEn",String.valueOf(tpunityadsen));
		putGameProp("TDVungleEn",String.valueOf(tpvungleen));
		
		//AppLovin
		putGameProp("APPLOSDKKEY",APPLOSDKKEY);
		putGameProp("APPLOTESTADS", String.valueOf(APPLOTESTADS));
		
		//Facebookads
		putGameProp("FBBANNERPLACE",FBBANNERPLACE);
		putGameProp("FBRECTBANNERPLACE",FBRECTBANNERPLACE);
		putGameProp("FBINTERSTITIALPLACE",FBINTERSTITIALPLACE);
		putGameProp("FBTESTADS", String.valueOf(FBTESTADS));
		
		//Heyzap
		putGameProp("HZPUBLISHERID", HZPUBLISHERID);
		
		//Leadbolt
		putGameProp("LBIOSAPIKEY", LBIOSAPIKEY);
		putGameProp("LBANDROIDAPIKEY", LBANDROIDAPIKEY);
		
		//StartApp
		putGameProp("SAIOSAPPID", SAIOSAPPID);
		putGameProp("SAANDROIDAPPID", SAANDROIDAPPID);
		
		//UnityAds
		putGameProp("UAIOSGAMEID", UAIOSGAMEID);
		putGameProp("UAANDROIDGAMEID", UAANDROIDGAMEID);
		putGameProp("UATESTADS", String.valueOf(UATESTADS));
		putGameProp("UADEBUGMODE", String.valueOf(UADEBUGMODE));
		
		//Vungle
		putGameProp("VUIOSAPPID", VUIOSAPPID);
		putGameProp("VUANDROIDAPPID", VUANDROIDAPPID);
		
		
	}
	
	public void readDataFromPref(){
		//Tapdaq
		TDClientKey = readStringGameProp("TDClientKey","");
		TDIosAppId = readStringGameProp("TDIosAppId","");
		TDAndroidAppId = readStringGameProp("TDAndroidAppId","");
		TDTestADS = Boolean.valueOf(readStringGameProp("TDTestADS",""));
		TDAdTypesInterstitial = readStringGameProp("TDAdTypesInterstitial","");
		TDAdTypesVideo = readStringGameProp("TDAdTypesVideo","");
		TDAdTypesRewardedVideo = readStringGameProp("TDAdTypesRewardedVideo","");
		TDAdTypeMoreApps = readStringGameProp("TDAdTypeMoreApps","");
		
		tpadcolonyen  = Boolean.valueOf(readStringGameProp("TDAdcolonyEn",""));
		tpadmoben  = Boolean.valueOf(readStringGameProp("TDAdMobEn",""));
		tpapplovinen  = Boolean.valueOf(readStringGameProp("TDAppLovinEn",""));
		tpfacebooken  = Boolean.valueOf(readStringGameProp("TDFacebookEn",""));
		tpchartboosten  = Boolean.valueOf(readStringGameProp("TDChartboostEn",""));
		tphyprmxen  = Boolean.valueOf(readStringGameProp("TDHyprmxEn",""));
		tpinmobien  = Boolean.valueOf(readStringGameProp("TDInMobiEn",""));
		tpironsourceen  = Boolean.valueOf(readStringGameProp("TDIronsourceEn",""));
		tpmopuben  = Boolean.valueOf(readStringGameProp("TDMopupEn",""));
		tpreceptiven  = Boolean.valueOf(readStringGameProp("TDReceptivEn",""));
		tptapjoyen  = Boolean.valueOf(readStringGameProp("TDTapjoyEn",""));
		tpunityadsen  = Boolean.valueOf(readStringGameProp("TDUnityadsEn",""));
		tpvungleen  = Boolean.valueOf(readStringGameProp("TDVungleEn",""));
		
		//AppLovin
		APPLOSDKKEY = readStringGameProp("APPLOSDKKEY","");
		APPLOTESTADS  = Boolean.valueOf(readStringGameProp("APPLOTESTADS",""));
		
		//Facebookads
		FBBANNERPLACE = readStringGameProp("FBBANNERPLACE","");
		FBRECTBANNERPLACE = readStringGameProp("FBRECTBANNERPLACE","");
		FBINTERSTITIALPLACE = readStringGameProp("FBINTERSTITIALPLACE","");
		FBTESTADS  = Boolean.valueOf(readStringGameProp("FBTESTADS",""));
		
		//Heyzap
		HZPUBLISHERID = readStringGameProp("HZPUBLISHERID","");
		
		//Leadbolt
		LBIOSAPIKEY = readStringGameProp("LBIOSAPIKEY","");
		LBANDROIDAPIKEY = readStringGameProp("LBANDROIDAPIKEY","");
		
		//startApp
		SAIOSAPPID = readStringGameProp("SAIOSAPPID","");
		SAANDROIDAPPID = readStringGameProp("SAANDROIDAPPID","");
		
		//unityads
		UAIOSGAMEID = readStringGameProp("UAIOSGAMEID","");
		UAANDROIDGAMEID  = readStringGameProp("UAANDROIDGAMEID","");
		UATESTADS = Boolean.valueOf(readStringGameProp("UATESTADS",""));
		UADEBUGMODE = Boolean.valueOf(readStringGameProp("UADEBUGMODE",""));
		
		//Vungle
		VUIOSAPPID = readStringGameProp("VUIOSAPPID","");
		VUANDROIDAPPID = readStringGameProp("VUANDROIDAPPID","");
		
	}
	
	public void updateToolset(){
		log.info("byRobinExtension : Updating from version " + getManifest().internalVersion + " for Game " + getGame().getName());
		final String name ="byRobin Extension Manager (Stencyl 3.4)";
		
		String versionString = Integer.toString(getManifest().internalVersion);
		
		Version toolsetVersion = new Version(versionString);
		
		if(ReadGithubApi.checkVersion("byrobintoolsetmanager",toolsetVersion)){	
			Runnable mainTask = new Runnable() {
			    public void run() {
			    	DialogBox.showErrorDialog("", "Update available", 
			    			"<html>There is an update available for " + name + ". <br>"
		    						+ "Do you want to update the Toolset now?.</html>",
			    			true
			    			);
			    }
			};
			Runnable onFinish = new Runnable() {
			    public void run() {
			    	if(DialogBox.okIsPressed){
			    		DialogBox.okIsPressed = false;
			    		
			    		UpdateToolset.downloadToolset(name);
			    	}
			    }
			};
			ByRobinGameExtension.doLongTask(mainTask, onFinish);
		}
	}
}