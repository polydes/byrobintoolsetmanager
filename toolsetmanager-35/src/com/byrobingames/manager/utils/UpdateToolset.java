package com.byrobingames.manager.utils;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import com.byrobingames.manager.ByRobinGameExtension;
import com.byrobingames.manager.reader.ReadGithubApi;
import com.byrobingames.manager.utils.dialog.DialogBox;

import stencyl.core.lib.Game;
import stencyl.sw.SW;
import stencyl.sw.app.TaskManager;
import stencyl.sw.app.tasks.StencylWorker;
import stencyl.sw.editors.game.advanced.ExtensionInstance;
import stencyl.sw.util.FileHelper;
import stencyl.sw.util.Locations;
import stencyl.sw.util.MultiFileDownloader;
import stencyl.sw.util.MultiFileDownloader.FileToDownload;


public class UpdateToolset {
	
	public static void renameExFolder(String repos_id){
		
		Runnable mainTask = new Runnable() {
		    public void run() {
		    	DialogBox.showErrorDialog("", "Wrong Foldername", 
		    			"<html>It seems that the foldername of extension is nog correct.<br>"
	    						+ "For correct working the extension foldername must be renamed<br> "
	    						+ "from foldername: <strong>" + repos_id + "-master</strong> to foldername: <strong>" + repos_id + "</strong>.<br><br>"
	    						+ "When you press ok i will rename the folder for you.</html>",
		    			true
		    			);
		    }
		};
		Runnable onFinish = new Runnable() {
		    public void run() {
		    	if(DialogBox.okIsPressed){
		    		DialogBox.okIsPressed = false;
		    		
		    		final String sourceName = repos_id+"-master";
		    		final String engineExRoot = Locations.getGameExtensionsLocation();
		    		final File sourceLocation = new File(engineExRoot + sourceName);
		    		final File targetLocation = new File(engineExRoot + repos_id);
		    		
		    		boolean isEnabled;
		    		if(DownloadEngineEx.checkIfEnabled(sourceName)){
		    			isEnabled = true;
		    			final ExtensionInstance instMaster = Game.getGame().getExtensions().get(sourceName);
		    			SW.get().getEngineExtensionManager().disableExtension(sourceName);
						SW.get().getExtensionDependencyManager().disable(instMaster.getExtension());
		    		}else{
		    			isEnabled = false;
		    		}
		    		
		    		
		    		FileHelper.copyDirectory(sourceLocation, targetLocation);
		    		FileHelper.delete(sourceLocation);
		    		SW.get().getEngineExtensionManager().reload();
		    		
		    		SW.get().getApp().saveGame(null);
		    		
		    		try{
		    			if(isEnabled){
			    			final ExtensionInstance inst = Game.getGame().getExtensions().get(repos_id);
							SW.get().getEngineExtensionManager().enableExtension(repos_id);
							SW.get().getExtensionDependencyManager().enable(inst.getExtension());
			    		}
		    		}catch (Exception e){
		    			e.printStackTrace();
		    		}
		    		
		    	}
		    }
		};
		ByRobinGameExtension.doLongTask(mainTask, onFinish);
		
	}

}
