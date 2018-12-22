package com.byrobingames.manager.app.pages;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import stencyl.sw.SW;
import stencyl.sw.lnf.Theme;

public class SimpleSharePage extends SourcePage{
	
	private static String PAGE_TITLE = "SimpleShare Configuration Page";
	private static String EXTEN_NAME = "SimpleShare Engine Extension";
	private static String BLOCK_HELP = "https://github.com/byrobingames";
	private static String REPOS_ID = "simpleshare";
	
	private static SimpleSharePage _instance;
	
	public SimpleSharePage(){
		super(PAGE_TITLE,BLOCK_HELP, EXTEN_NAME, REPOS_ID);
		
		
		setSettings();
		
		add(dPanel, BorderLayout.NORTH);
	}
	public void setSettings(){
		JLabel title = new JLabel("SimpleShare Settings");
		title.setFont(SW.get().getFonts().getTitleBoldFont());
		title.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(title);

		JLabel label = new JLabel("<html>The website or screenshot will share with Facebook and not the message.<br><br>"
				+ "Because of an update to the Facebook application has replaced the in-built Facebook share activity with one that<br>"
				+ "ignores status text.<br>"
				+ "Facebook’s policies don’t allow you to pre-populate status messages and require all content to be user generated <br>"
				+ "https://developers.facebook.com/docs/apps/review/prefill</html>");
		label.setFont(SW.get().getFonts().getNormalFont());
		label.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(label);
	}
	
	public void save()
	{
		updateWrapper();
		revalidate();
		repaint();
		
	}
	
	public static SimpleSharePage get()
	{
		if (_instance == null)
			_instance = new SimpleSharePage();

		return _instance;
	}
	
	public static void disposeInstance()
	{
		_instance = null;
	}

}

