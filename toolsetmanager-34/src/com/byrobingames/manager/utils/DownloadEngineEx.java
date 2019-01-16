package com.byrobingames.manager.utils;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

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

public final class DownloadEngineEx {
	
	private static final Logger log = Logger.getLogger(DownloadEngineEx.class);
	
	private static boolean isEnabled = false;
	
	public static boolean checkByRobinEngineEx()
	{
		File byRobinEngineEx = new File(Locations.getGameExtensionsLocation()+"byRobinextensionmanager");
		
		if (!byRobinEngineEx.exists())
		{
			DialogBox.showGenericDialog(
					"The byRobin Egine Extension Manager is required", 
					"Click OK to start installation. An internet connection is required.");
			
			DownloadEngineEx.downloadEngineEx("byRobin Engine Extension Manager","byRobinextensionmanager");

			return false;
		}
		else
		{
			if(!checkIfEnabled("byRobinextensionmanager")){
				DialogBox.showGenericDialog(
						"Enable engine extension", 
						"Enable \"byRobin Engine Extension Manager\" in the GeneralPage");
				
				return false;
			}
			return true;
		}
		
	}
	
	public static boolean checkEngineEx(String extensionID){
		
		File engineEx = new File(Locations.getGameExtensionsLocation() + extensionID);
		
		if(engineEx.exists()){
			return true;
		}
		
		return false;
	}
	
	public static void downloadEngineEx(String ext_name, String repos_id)
	{
		if(checkIfEnabled(repos_id)){
			isEnabled = true;
		}else{
			isEnabled = false;
		}
		
		final String engineExRoot = Locations.getGameExtensionsLocation();
		final File EngineExRootFile = new File(engineExRoot);
		EngineExRootFile.mkdirs();
		FileHelper.makeFileWritable(EngineExRootFile);

		final String downloadURL = ReadGithubApi.getDownloadURL(repos_id);
		final String fileName = FilenameUtils.getName(downloadURL);
		final String downloadLocation = engineExRoot + fileName;
		
		final File cleanFolder = new File(engineExRoot + repos_id);
		
		ArrayList<FileToDownload> files = new ArrayList<FileToDownload>();
		files.add(new FileToDownload(downloadURL, ext_name, downloadLocation));
		
		Runnable r = new Runnable()
		{
			@Override
			public void run()
			{
				final TaskManager t = SW.get().getApp().getTaskManager();
				
				t.run(new StencylWorker()
				{
					@Override
					public Integer doInBackground()
					{
						if(downloadURL.equals("null")){
							//failed to download 
							return 0;
						}
						t.showProgress("Installing "+ ext_name + "...");
						
						try {
							if(cleanFolder.exists() && cleanFolder.isDirectory()){
								//FileUtils.deleteDirectory(cleanFolder);
								FileUtils.cleanDirectory(cleanFolder);
							}
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						FileHelper.unzip(new File(downloadLocation), EngineExRootFile);
						
						return 0;
					}

					@Override
					public void done(boolean status)
					{
						super.done(status);
						
						if(downloadURL.equals("null")){
							//failed to download stop code and delete null.zip and return
							FileHelper.delete(new File(Locations.getPath(Locations.getGameExtensionsLocation(), fileName)));
							return;
						}

						t.hideProgress();
						
						DialogBox.showGenericDialog(
								"Success", 
								"The " + ext_name + " has been installed.");
						
						
						
						FileHelper.delete(new File(Locations.getPath(Locations.getGameExtensionsLocation(), fileName)));
						SW.get().getEngineExtensionManager().reload();
						
						if(isEnabled){
							final ExtensionInstance inst = Game.getGame().getExtensions().get(repos_id);
							SW.get().getEngineExtensionManager().enableExtension(repos_id);
							SW.get().getExtensionDependencyManager().enable(inst.getExtension());
						}
						
						try{
							if(repos_id.equals("byRobinextensionmanager")){
								final ExtensionInstance inst = Game.getGame().getExtensions().get(repos_id);
								SW.get().getEngineExtensionManager().enableExtension(repos_id);
								SW.get().getExtensionDependencyManager().enable(inst.getExtension());
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						SW.get().getApp().saveGame(null);
						
					}
				});
			}
		};

		MultiFileDownloader.downloadFiles("Downloading " + ext_name + "...", files, r);
	}
	
	public static boolean checkIfEnabled(String repos_id){
		
		try{
			final ExtensionInstance inst = Game.getGame().getExtensions().get(repos_id);
			
			if(inst.isEnabled()){
				return true;
			}else{
				return false;
			}
		}catch (Exception e){
			e.printStackTrace();
	    	return false;
		}
			
	}


}
