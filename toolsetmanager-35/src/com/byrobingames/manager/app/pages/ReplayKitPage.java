package com.byrobingames.manager.app.pages;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import stencyl.sw.SW;
import stencyl.sw.lnf.Theme;

public class ReplayKitPage extends SourcePage{
	
	private static String PAGE_TITLE = "ReplayKit Configuration Page";
	private static String EXTEN_NAME = "ReplayKit Engine Extension";
	private static String BLOCK_HELP = "https://github.com/byrobingames/replaykit";
	private static String REPOS_ID = "replaykit";
	
	private static ReplayKitPage _instance;
	
	public ReplayKitPage(){
		
		super(PAGE_TITLE,BLOCK_HELP, EXTEN_NAME, REPOS_ID);
		
		setSettings();
		
		add(dPanel, BorderLayout.NORTH);
	}
	public void setSettings(){
		
		JLabel title = new JLabel("ReplayKit Settings");
		title.setFont(SW.get().getFonts().getTitleBoldFont());
		title.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(title);

		JLabel label = new JLabel("<html>There are no special settings for ReplayKit.<br><br>"
				+ "ReplayKit is only available on devices:<br>"
				+ "- running iOS 9 or above; <br>"
				+ "  iPad Air 2, iPad Mini 2, iPad Mini 3, iPad 5 Air, iPhone 5s, iPhone 6, iPhone 6 Plus, iPhone 7 and iPhone 7 Plus<br>"
				+ "- running Android 5.0 (Lollipop) or above.<br>"
				+ "  For Android make sure you selected API 21 or higher in Mobile settings->version->Android Target version.</html>");
		label.setFont(SW.get().getFonts().getNormalFont());
		label.setForeground(Theme.TEXT_COLOR);;
		dPanel.addGenericRow(label);
	}
	
	public void save()
	{
		updateWrapper();
		revalidate();
		repaint();
		
	}
	
	public static ReplayKitPage get()
	{
		if (_instance == null)
			_instance = new ReplayKitPage();

		return _instance;
	}
	
	public static void disposeInstance()
	{
		_instance = null;
	}

}
