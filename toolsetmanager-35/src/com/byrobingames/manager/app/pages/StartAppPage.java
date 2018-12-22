package com.byrobingames.manager.app.pages;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;


import com.byrobingames.manager.ByRobinGameExtension;

import stencyl.sw.SW;
import stencyl.sw.app.App;
import stencyl.sw.lnf.Theme;
import stencyl.sw.util.comp.text.AutoVerifyField;
import stencyl.sw.util.comp.text.FieldVerifier;


public class StartAppPage extends SourcePage implements FieldVerifier{
	
	private static StartAppPage _instance;
	
	private static String PAGE_TITLE = "StartApp Configuration Page";
	private static String EXTEN_NAME = "StartApp Engine Extension";
	private static String BLOCK_HELP = "https://github.com/byrobingames/startapp";
	private static String REPOS_ID = "startapp";
	
	private AutoVerifyField SAIosAppID;
	private AutoVerifyField SAAndroidAppID;
	
	private StartAppPage()
	{
		super(PAGE_TITLE,BLOCK_HELP, EXTEN_NAME, REPOS_ID);
		
		setSettings();
		
		add(dPanel, BorderLayout.NORTH);
	}
	
	public void setSettings(){
		JLabel title = new JLabel("StartApp Account Settings");
		title.setFont(SW.get().getFonts().getTitleBoldFont());
		title.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(title);
		
		JLabel note = new JLabel("<html>Note: Fill in your App ID from <a href=\"http://www.startapp.com\">http://www.startapp.com</a></html>");
	    note.setFont(SW.get().getFonts().getNormalFont());
	    note.setForeground(Theme.TEXT_COLOR);
		note.addMouseListener(new MouseAdapter() {
	           @Override
	           public void mouseClicked(MouseEvent e) {
	           	App.showWebpage("http://www.startapp.com");
	           }
	       });
		dPanel.addGenericRow(note);
		
		dPanel.startBlock();
		//iosAppID
		SAIosAppID = new AutoVerifyField(16,this,"");
		SAIosAppID.setText(ByRobinGameExtension.SAIOSAPPID);
		SAIosAppID.disableErrorIcon();
		dPanel.addTextFieldRestrictedWidth("iOS App ID",SAIosAppID,300);
        
        //androidAppid
      	SAAndroidAppID = new AutoVerifyField(16,this,"");
      	SAAndroidAppID.setText(ByRobinGameExtension.SAANDROIDAPPID);
      	SAAndroidAppID.disableErrorIcon();
      	dPanel.addTextFieldRestrictedWidth("Android App ID",SAAndroidAppID,300);
      	
      	dPanel.finishBlock();
        
	}
	
	public void save()
	{
		ByRobinGameExtension.SAIOSAPPID = SAIosAppID.getText();
		ByRobinGameExtension.SAANDROIDAPPID = SAAndroidAppID.getText();

		updateWrapper();
		revalidate();
		repaint();
	}
	

	public static StartAppPage get()
	{
		if (_instance == null)
			_instance = new StartAppPage();

		return _instance;
	}
	
	public static void disposeInstance()
	{
		_instance = null;
	}

	@Override
	public boolean verifyText(JTextField arg0, String arg1) {
		// TODO Auto-generated method stub
		return true;
	}

}



