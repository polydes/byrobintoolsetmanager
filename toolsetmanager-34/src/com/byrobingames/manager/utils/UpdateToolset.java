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
	
	private static final String repos_id ="byrobintoolsetmanager";
	
	public static void downloadToolset(String name)
	{
		SW.get().getApp().saveGame(null);
		
		final String exRoot = Locations.getExtensionsLocation();
		final File exRootFile = new File(exRoot);
		exRootFile.mkdirs();
		FileHelper.makeFileWritable(exRootFile);

		final String downloadURL = ReadGithubApi.getDownloadURL(repos_id);
		final String fileName  = FilenameUtils.getName(downloadURL);
		final String extensionLocation = exRoot + fileName;

		ArrayList<FileToDownload> files = new ArrayList<FileToDownload>();
		files.add(new FileToDownload(downloadURL, name, extensionLocation));
		
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
							//failed to download stop code and delete null.zip and return
							return 0;
						}
						
						t.showProgress("Installing "+ name + "...");
						
						FileHelper.unzip(new File(extensionLocation), exRootFile);
						
						return 0;
					}

					@Override
					public void done(boolean status)
					{
						super.done(status);
						
						if(downloadURL.equals("null")){
							//failed to download stop code and delete null.zip and return
							FileHelper.delete(new File(Locations.getPath(Locations.getExtensionsLocation(), fileName)));
							return;
						}
						
						t.hideProgress();
						
						FileHelper.delete(new File(Locations.getPath(Locations.getExtensionsLocation(), fileName)));
						
						DialogBox.showGenericDialog(
								"Success", 
								"<html>The " + name + " has been installed.<br>"
										+ "Please restart Stencyl</html>");
						
					}
				});
			}
		};

		MultiFileDownloader.downloadFiles("Downloading " + name + "...", files, r);
	}

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
