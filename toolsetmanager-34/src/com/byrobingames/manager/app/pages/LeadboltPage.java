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


public class LeadboltPage extends SourcePage implements FieldVerifier{
	
	private static LeadboltPage _instance;
	
	private static String PAGE_TITLE = "Leadbolt Configuration Page";
	private static String EXTEN_NAME = "Leadbolt Engine Extension";
	private static String BLOCK_HELP = "https://github.com/byrobingames/leadbolt";
	private static String REPOS_ID = "leadbolt";
	
	private AutoVerifyField LBIosApiKey;
	private AutoVerifyField LBAndroidApiKey;
	
	private LeadboltPage()
	{
		super(PAGE_TITLE,BLOCK_HELP, EXTEN_NAME, REPOS_ID);
		
		setSettings();
		
		add(dPanel, BorderLayout.NORTH);
	}
	
	public void setSettings(){
		JLabel title = new JLabel("Leadbolt Account Settings");
		title.setFont(SW.get().getFonts().getTitleBoldFont());
		title.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(title);
		
		JLabel note = new JLabel("<html>Note: Fill in your API Key from <a href=\"https://portal.leadbolt.com/p/app\">https://portal.leadbolt.com/p/app</a></html>");
	    note.setFont(SW.get().getFonts().getNormalFont());
		note.setForeground(Theme.TEXT_COLOR);
		note.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	App.showWebpage("https://portal.leadbolt.com/p/app");
	            }
	        });
		dPanel.addGenericRow(note);
		
		//iosApiKey
		dPanel.startBlock();
		
		LBIosApiKey = new AutoVerifyField(16,this,"");
		LBIosApiKey.setText(ByRobinGameExtension.LBIOSAPIKEY);
		LBIosApiKey.disableErrorIcon();
		dPanel.addTextFieldRestrictedWidth("iOS API Key", LBIosApiKey, 300);
        
        //androidApiKey
		LBAndroidApiKey = new AutoVerifyField(16,this,"");
		LBAndroidApiKey.setText(ByRobinGameExtension.LBANDROIDAPIKEY);
		LBAndroidApiKey.disableErrorIcon();
		dPanel.addTextFieldRestrictedWidth("Android API Key", LBAndroidApiKey, 300);
		
		dPanel.finishBlock();
        
	}
	
	public void save()
	{
		ByRobinGameExtension.LBIOSAPIKEY = LBIosApiKey.getText();
		ByRobinGameExtension.LBANDROIDAPIKEY = LBAndroidApiKey.getText();

		updateWrapper();
		revalidate();
		repaint();
	}
	

	public static LeadboltPage get()
	{
		if (_instance == null)
			_instance = new LeadboltPage();

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


