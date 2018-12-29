package com.byrobingames.manager.app.pages;

import java.awt.BorderLayout;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;


import com.byrobingames.manager.ByRobinGameExtension;

import stencyl.core.lib.Game;
import stencyl.sw.SW;
import stencyl.sw.app.App;
import stencyl.sw.lnf.Theme;
import stencyl.sw.util.comp.text.AutoVerifyField;
import stencyl.sw.util.comp.text.FieldVerifier;


public class UnityAdsPage extends SourcePage implements FieldVerifier{
	
	private static UnityAdsPage _instance;
	
	private static String PAGE_TITLE = "UnityAds Configuration Page";
	private static String EXTEN_NAME = "UnityAds Engine Extension";
	private static String BLOCK_HELP = "https://github.com/byrobingames/unityads";
	private static String REPOS_ID = "unityads";

	private AutoVerifyField UAIosGameID;
	private AutoVerifyField UAAndroidGameID;
	private JCheckBox UATestADS;
	private JCheckBox UADebugMode;
	
	private UnityAdsPage()
	{
		super(PAGE_TITLE,BLOCK_HELP, EXTEN_NAME, REPOS_ID);
		
		setSettings();
		
		add(dPanel, BorderLayout.NORTH);
	}
	
	public void setSettings(){
		JLabel title = new JLabel("UnityAds Account Settings");
		title.setFont(SW.get().getFonts().getTitleBoldFont());
		title.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(title);
		
		JLabel note = new JLabel("<html>Note: Fill in your Game ID from <a href=\"https://operate.dashboard.unity3d.com\">https://operate.dashboard.unity3d.com</a></html>");
        note.setFont(SW.get().getFonts().getNormalFont());
		note.setForeground(Theme.TEXT_COLOR);
		note.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	App.showWebpage("https://unity3d.com/services/ads");
            }
        });
		dPanel.addGenericRow(note);
		
		dPanel.startBlock();
		
		//iosGameID
		UAIosGameID = new AutoVerifyField(16,this,"");
		UAIosGameID.setText(ByRobinGameExtension.UAIOSGAMEID);
		UAIosGameID.disableErrorIcon();
		dPanel.addTextFieldRestrictedWidth("iOS Game ID", UAIosGameID, 300);
        
        //androidGameID      		
      	UAAndroidGameID = new AutoVerifyField(16,this,"");
      	UAAndroidGameID.setText(ByRobinGameExtension.UAANDROIDGAMEID);
      	UAAndroidGameID.disableErrorIcon();
      	dPanel.addTextFieldRestrictedWidth("Android Game ID", UAAndroidGameID, 300);
        		
		UATestADS = new JCheckBox();
		UATestADS.setSelected(ByRobinGameExtension.UATESTADS);
		dPanel.addCheckbox("Enable Testads",UATestADS);	
		
		UADebugMode = new JCheckBox();
		UADebugMode.setSelected(ByRobinGameExtension.UADEBUGMODE);
		dPanel.addCheckbox("Enable Debug Mode",UADebugMode);
		
		JLabel note2 = new JLabel("<html>Enable Debug mode if you want debug logs.</html>");
		note2.setForeground(Theme.TEXT_COLOR.darker());
		dPanel.addGenericRow("", note2);
		
		dPanel.finishBlock();
        
	}
	
	public void save()
	{
		ByRobinGameExtension.UAIOSGAMEID = UAIosGameID.getText();
		ByRobinGameExtension.UAANDROIDGAMEID = UAAndroidGameID.getText();
		ByRobinGameExtension.UATESTADS = UATestADS.isSelected();
		ByRobinGameExtension.UADEBUGMODE = UADebugMode.isSelected();
		
		updateWrapper();
		revalidate();
		repaint();
	}
	

	public static UnityAdsPage get()
	{
		if (_instance == null)
			_instance = new UnityAdsPage();

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




