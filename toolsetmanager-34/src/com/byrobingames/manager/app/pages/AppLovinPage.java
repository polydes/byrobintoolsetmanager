package com.byrobingames.manager.app.pages;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;


import com.byrobingames.manager.ByRobinGameExtension;
import com.byrobingames.manager.app.Main;

import stencyl.core.lib.Game;
import stencyl.sw.SW;
import stencyl.sw.app.App;
import stencyl.sw.lnf.Theme;
import stencyl.sw.util.comp.text.AutoVerifyField;
import stencyl.sw.util.comp.text.FieldVerifier;

public class AppLovinPage extends SourcePage implements FieldVerifier{
	
	private static AppLovinPage _instance;
	
	private static String PAGE_TITLE = "AppLovin Configuration Page";
	private static String EXTEN_NAME = "AppLovin Engine Extension";
	private static String BLOCK_HELP = "https://github.com/byrobingames/applovin";
	private static String REPOS_ID = "applovin";
	
	private AutoVerifyField AppLovinKEY;
	private JCheckBox AppLovinTestADS;
	
	private AppLovinPage()
	{
		super(PAGE_TITLE,BLOCK_HELP, EXTEN_NAME, REPOS_ID);
		
		
		JLabel titleSettings = new JLabel("Applovin Account Settings");
		titleSettings.setFont(SW.get().getFonts().getTitleBoldFont());
		titleSettings.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(titleSettings);
		
		JLabel note = new JLabel("<html>Note: Fill in your SDK Key from <a href=\"https://www.applovin.com/account#keys\">https://www.applovin.com/account#keys</a></html>");
        note.setFont(SW.get().getFonts().getNormalFont());
		note.setForeground(Theme.TEXT_COLOR);
		note.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	App.showWebpage("https://www.applovin.com/account#keys");
            }
        });
		dPanel.addGenericRow(note);
		
		
		dPanel.startBlock();
		AppLovinKEY = new AutoVerifyField(16,this,"");
		AppLovinKEY.setText(ByRobinGameExtension.APPLOSDKKEY);
		AppLovinKEY.disableErrorIcon();
		dPanel.addTextFieldRestrictedWidth("SDK Key:", AppLovinKEY, 300);
		
		AppLovinTestADS = new JCheckBox();
		AppLovinTestADS.setSelected(ByRobinGameExtension.APPLOTESTADS);
		dPanel.addCheckbox("Enable Testads",AppLovinTestADS);
		
		dPanel.finishBlock();
		
		add(dPanel, BorderLayout.NORTH);
		
	}
	
	public void save()
	{
		ByRobinGameExtension.APPLOSDKKEY = AppLovinKEY.getText();
		ByRobinGameExtension.APPLOTESTADS = AppLovinTestADS.isSelected();
		
		updateWrapper();
		revalidate();
		repaint();
	}
	

	public static AppLovinPage get()
	{
		if (_instance == null)
			_instance = new AppLovinPage();

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
