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


public class VunglePage extends SourcePage implements FieldVerifier{
	
	private static VunglePage _instance;
	
	private static String PAGE_TITLE = "Vungle Configuration Page";
	private static String EXTEN_NAME = "Vungle Engine Extension";
	private static String BLOCK_HELP = "https://github.com/byrobingames/vungle";
	private static String REPOS_ID = "vungle";
	
	private AutoVerifyField VUIosAppID;
	private AutoVerifyField VUAndroidAppID;
	
	private VunglePage()
	{
		super(PAGE_TITLE,BLOCK_HELP, EXTEN_NAME, REPOS_ID);
	
		setSettings();
		
		add(dPanel, BorderLayout.NORTH);
	}
	
	public void setSettings(){
		JLabel title = new JLabel("Vungle Account Settings");
		title.setFont(SW.get().getFonts().getTitleBoldFont());
		title.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(title);
		
		JLabel note = new JLabel("<html>Note: Fill in your App ID from <a href=\"https://www.vungle.com\">https://www.vungle.com</a></html>");
        note.setFont(SW.get().getFonts().getNormalFont());
		note.setForeground(Theme.TEXT_COLOR);
		note.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	App.showWebpage("https://www.vungle.com/");
            }
        });
		dPanel.addGenericRow(note);
		
		dPanel.startBlock();
		
		//iosAppID
		VUIosAppID = new AutoVerifyField(16,this,"");
		VUIosAppID.setText(ByRobinGameExtension.VUIOSAPPID);
		VUIosAppID.disableErrorIcon();
		dPanel.addTextFieldRestrictedWidth("iOS App ID", VUIosAppID, 300);
        
        //androidAppid
      	VUAndroidAppID = new AutoVerifyField(16,this,"");
      	VUAndroidAppID.setText(ByRobinGameExtension.VUANDROIDAPPID);
      	VUAndroidAppID.disableErrorIcon();
      	dPanel.addTextFieldRestrictedWidth("Android App ID", VUAndroidAppID, 300);
        
      	dPanel.finishBlock();
	}
	
	public void save()
	{
		ByRobinGameExtension.VUIOSAPPID = VUIosAppID.getText();
		ByRobinGameExtension.VUANDROIDAPPID = VUAndroidAppID.getText();
		
		updateWrapper();
		revalidate();
		repaint();
	}
	

	public static VunglePage get()
	{
		if (_instance == null)
			_instance = new VunglePage();

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




