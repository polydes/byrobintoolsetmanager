package com.byrobingames.manager.app.pages;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.byrobingames.manager.ByRobinGameExtension;
import com.byrobingames.manager.editor.NotifEditor;

import stencyl.sw.SW;
import stencyl.sw.lnf.Theme;
import stencyl.sw.util.UI;

public class LocalNotifPage extends SourcePage{
	
	private static String PAGE_TITLE = "Local Notification Configuration Page";
	private static String EXTEN_NAME = "Local Notification Engine Extension";
	private static String BLOCK_HELP = "https://github.com/byrobingames/localnotifications";
	private static String REPOS_ID = "localnotifications";
	
	private static LocalNotifPage _instance;
	private NotifEditor notifEditor;
	
	public LocalNotifPage()
	{
		super(PAGE_TITLE,BLOCK_HELP, EXTEN_NAME, REPOS_ID);
		
		
		setSettings();
		createMapEditor();
		
		//add(dPanel, BorderLayout.NORTH);
		JScrollPane scroller = UI.createScrollPane(dPanel);
		scroller.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Theme.BORDER_COLOR));
		
		add(scroller,BorderLayout.CENTER);
	}
	public void setSettings(){
		JLabel title = new JLabel("Local Notification Settings");
		title.setFont(SW.get().getFonts().getTitleBoldFont());
		title.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(title);

		JLabel label = new JLabel("<html>You can schedule up your local notifications below<br><br>"
				+ "-NotifiD: Give your notification an id. (Must be Unique number between 0 and 63)<br>"
				+ "-Title: Your notification Title<br>"
				+ "-Message: Your notification Message<br>"
				+ "-Trigger after(in seconds): Time when the system should trigger the notification<br>"
				+ "-Repeat interval Every: The interval at which to reschedule the notification<Br>"
				+ "Value can be set to Minute, Hour, Day, Week or Year<br>");
		label.setFont(SW.get().getFonts().getNormalFont());
		label.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(label);
	}
	
	public void createMapEditor(){
		
		notifEditor = new NotifEditor(ByRobinGameExtension.NOTIFLIST);
		notifEditor.setVisible(true);
		
		dPanel.addGenericRow(notifEditor);
		
	}
	
	public void save()
	{
		if(notifEditor !=null){
			ByRobinGameExtension.NOTIFLIST = notifEditor.getModel();
		}
		
		updateWrapper();
		revalidate();
		repaint();
		
	}
	
	public static LocalNotifPage get()
	{
		if (_instance == null)
			_instance = new LocalNotifPage();

		return _instance;
	}
	
	public static void disposeInstance()
	{
		_instance = null;
	}

}


