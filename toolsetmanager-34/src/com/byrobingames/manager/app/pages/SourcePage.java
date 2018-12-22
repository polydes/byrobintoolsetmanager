package com.byrobingames.manager.app.pages;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.byrobingames.manager.ByRobinGameExtension;
import com.byrobingames.manager.reader.ReadGithubApi;
import com.byrobingames.manager.utils.DownloadEngineEx;
import com.byrobingames.manager.utils.UpdateToolset;
import com.byrobingames.manager.utils.dialog.DialogBox;

import misc.Version;
import stencyl.core.lib.Game;
import stencyl.sw.SW;
import stencyl.sw.app.App;
import stencyl.sw.editors.game.advanced.EngineExtension;
import stencyl.sw.editors.game.advanced.ExtensionInstance;
import stencyl.sw.lnf.Theme;
import stencyl.sw.util.Loader;
import stencyl.sw.util.comp.CapsuleButton;
import stencyl.sw.util.dg.DialogPanel;

public class SourcePage extends JPanel{
	
	protected DialogPanel dPanel;
	
	protected JPanel s;
	protected JPanel wrapper;
	
	private EngineExtension e;
	private Version exVersion;
	
	private String PAGE_TITLE;
	private String BLOCK_HELP;
	private String EXTEN_NAME;
	private String REPOS_ID;
	
	private Boolean askForUpdate = true;

	public SourcePage(
			String PAGE_TITLE,
			String BLOCK_HELP,
			String EXTEN_NAME,
			String REPOS_ID
			)
	{
		super(new BorderLayout());
		
		this.PAGE_TITLE = PAGE_TITLE;
		this.BLOCK_HELP = BLOCK_HELP;
		this.EXTEN_NAME = EXTEN_NAME;
		this.REPOS_ID = REPOS_ID;
		
		dPanel = new DialogPanel(Theme.LIGHT_BG_COLOR3);
		
		if (PAGE_TITLE !=""){
			setTitle();
		}
		
		s = new JPanel(new BorderLayout());
		s.setOpaque(false);
		setExtensionWrapper();
		dPanel.addGenericRow(s,25);

		
	}
	
	public void setTitle(){
		JLabel titleLabel = new JLabel(PAGE_TITLE);
		titleLabel.setFont(SW.get().getFonts().getTitleBoldFont());
		titleLabel.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(titleLabel,25);
	}
	
	public void setExtensionWrapper(){
		if(Game.getGame() == null){
			return;
		}
		
		wrapper = new JPanel(new BorderLayout());
		//wrapper.setBounds(LABEL_X, 60, 600, 32);
		Dimension d = new Dimension(600, 40);
		wrapper.setPreferredSize(d);
		wrapper.setMinimumSize(d);
		wrapper.setMaximumSize(d);
		
		wrapper.setBackground(Theme.LIGHT_BG_COLOR3);
		
		////Check if foldername contains master, if so rename the folder...
		String masterFolder = REPOS_ID +"-master";
		if(DownloadEngineEx.checkEngineEx(masterFolder)){
			UpdateToolset.renameExFolder(REPOS_ID);
		}
		/////
		
		if(DownloadEngineEx.checkEngineEx(REPOS_ID)){
			JPanel buttons = new JPanel();
			buttons.setOpaque(false);
			
			e = new EngineExtension(REPOS_ID);
			e.load();
			exVersion = e.getVersion();
			
			if(Game.getGame().getExtensions().get(e.getID()) == null)
			{
				Game.getGame().getExtensions().put(e.getID(), new ExtensionInstance(e.getID(), false));
			}
			final ExtensionInstance inst = Game.getGame().getExtensions().get(e.getID());
			
			ImageIcon icon = e.getIcon();
			
			if(icon == null)
			{
				icon = Loader.getWarningIcon();
			}
			
			JLabel iconLabel = new JLabel(icon);
			wrapper.add(iconLabel,BorderLayout.WEST);
			
			JLabel name = new JLabel("<html>"+e.getName()+"<br>"+e.getDescription()+"</html>");
			name.setFont(SW.get().getFonts().getNormalFont());
			name.setForeground(Theme.TEXT_COLOR);
			wrapper.add(name,BorderLayout.CENTER);
			
			final CapsuleButton enable = new CapsuleButton();
			
			if(inst.isEnabled())
			{
				enable.useRedTheme();
			}
			
			else
			{
				enable.useGreenTheme();
			}
			
			enable.setText(inst.isEnabled() ? "Disable" : "Enable");
			enable.addActionListener
			(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent evt)
					{
						inst.toggle();
						
						enable.setText(inst.isEnabled() ? "Disable" : "Enable");
						
						if(inst.isEnabled())
						{
							enable.useRedTheme();
							SW.get().getEngineExtensionManager().enableExtension(e.getID());
							SW.get().getExtensionDependencyManager().enable(inst.getExtension());
						}
						
						else
						{
							enable.useGreenTheme();
							SW.get().getEngineExtensionManager().disableExtension(e.getID());
							SW.get().getExtensionDependencyManager().disable(inst.getExtension());
						}
					}
				}
			);
			final JButton website = new CapsuleButton();
			website.setText("Help");
			website.addActionListener
			(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent evt)
					{
						App.showWebpage(BLOCK_HELP);
					}
				}
			);
			
			CapsuleButton update= null;
			if(ReadGithubApi.checkVersion(e.getID(),e.getVersion())){
				update = new CapsuleButton();
				update.setText("Update");
				update.useRedTheme();
				update.addActionListener
				(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent evt)
						{
							
							DownloadEngineEx.downloadEngineEx(EXTEN_NAME,REPOS_ID);
							
						}
					}
				);
				if(askForUpdate){
					Runnable mainTask = new Runnable() {
					    public void run() {
					    		DialogBox.showGenericDialog(
					    				"Update available", 
					    				"<html>There is an update available for " + EXTEN_NAME + ". <br>Use the \"Update\" button to download and install the new Version.</html>");
					    }
					};
					Runnable onFinish = new Runnable() {
					    public void run() {
					    	askForUpdate = false;
					    }
					};
					ByRobinGameExtension.doLongTask(mainTask, onFinish);
				}
			}
			
			if(update != null){
				Dimension d2 = new Dimension(650, 32);
				wrapper.setPreferredSize(d2);
				wrapper.setMinimumSize(d2);
				wrapper.setMaximumSize(d2);
				buttons.add(update);
			}
			buttons.add(website);
			buttons.add(enable);
			wrapper.add(buttons,BorderLayout.EAST);
			//add(wrapper);
		}else{
			JLabel iconLabel = new JLabel(Loader.getWarningIcon());
			wrapper.add(iconLabel,BorderLayout.WEST);
			
			JLabel noEx = new JLabel("<html>The " + EXTEN_NAME + " is not installed.<br>Click download to download and install the " + EXTEN_NAME + ".</html>");
			noEx.setFont(SW.get().getFonts().getNormalFont());
			noEx.setForeground(Theme.TEXT_COLOR);
			wrapper.add(noEx,BorderLayout.CENTER);
			
			JButton downloadBtn = new JButton("Download");
			downloadBtn.addActionListener(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent evt)
						{
							
							DownloadEngineEx.downloadEngineEx(EXTEN_NAME,REPOS_ID);
						}
					}
			);
			wrapper.add(downloadBtn,BorderLayout.EAST);
		}
		s.add(wrapper,BorderLayout.WEST);
	}
	
	public void updateWrapper(){
		
		if(s !=null){
			if(Game.getGame() != null){
				s.remove(wrapper);
				setExtensionWrapper();
			}
		}
		
		dPanel.revalidate();
		dPanel.repaint();
		
	}
}
