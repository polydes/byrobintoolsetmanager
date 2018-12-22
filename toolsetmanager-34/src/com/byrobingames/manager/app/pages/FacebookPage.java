package com.byrobingames.manager.app.pages;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.byrobingames.manager.ByRobinGameExtension;
import com.byrobingames.manager.app.Main;

import stencyl.sw.SW;
import stencyl.sw.app.App;
import stencyl.sw.lnf.Theme;
import stencyl.sw.util.comp.text.AutoVerifyField;
import stencyl.sw.util.comp.text.FieldVerifier;


public class FacebookPage extends SourcePage implements FieldVerifier{

	private static FacebookPage _instance;
	
	private static String PAGE_TITLE = "Facebook Configuration Page";
	private static String EXTEN_NAME = "Facebook Ads Engine Extension";
	private static String BLOCK_HELP = "https://github.com/byrobingames/facebookads";
	private static String REPOS_ID = "facebookads";
	
	private JCheckBox fbTestADS;
	private AutoVerifyField fbBannerPlace;
	private AutoVerifyField fbRectBannerPlace;
	private AutoVerifyField fbInterstitialPlace;
	
	private FacebookPage()
	{
		super(PAGE_TITLE,BLOCK_HELP, EXTEN_NAME, REPOS_ID);
		
		
		setSettings();
		
		add(dPanel, BorderLayout.NORTH);
	}
	
	public void setSettings(){
		JLabel title = new JLabel("Facebook Ads Placements");
		title.setFont(SW.get().getFonts().getTitleBoldFont());
		title.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(title);
		
		 JLabel note = new JLabel("<html>Note: Create Facebook ads on <a href=\"https://developers.facebook.com/docs/audience-network\">https://developers.facebook.com/docs/audience-network</a></html>");
	        note.setFont(SW.get().getFonts().getNormalFont());
			note.setForeground(Theme.TEXT_COLOR);
			note.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	App.showWebpage("https://developers.facebook.com/docs/audience-network");
	            }
	        });
			dPanel.addGenericRow(note);
		
		//Banner
		dPanel.startBlock();
		
		fbBannerPlace = new AutoVerifyField(16,this,"");
		fbBannerPlace.setText(ByRobinGameExtension.FBBANNERPLACE);
		fbBannerPlace.disableErrorIcon();
		dPanel.addTextFieldRestrictedWidth("Banner placementID", fbBannerPlace, 300);
        
        //RectBanner
      		
      	fbRectBannerPlace = new AutoVerifyField(16,this,"");
      	fbRectBannerPlace.setText(ByRobinGameExtension.FBRECTBANNERPLACE);
      	fbRectBannerPlace.disableErrorIcon();
		dPanel.addTextFieldRestrictedWidth("Rect. Banner placementID", fbRectBannerPlace, 300);
        
        //Interstitial
      		
      	fbInterstitialPlace = new AutoVerifyField(16,this,"");
      	fbInterstitialPlace.setText(ByRobinGameExtension.FBINTERSTITIALPLACE);
      	fbInterstitialPlace.disableErrorIcon();
		dPanel.addTextFieldRestrictedWidth("Interstitial placementID", fbInterstitialPlace, 300);
		
		fbTestADS = new JCheckBox();
		fbTestADS.setSelected(ByRobinGameExtension.FBTESTADS);
		dPanel.addCheckbox("Enable Testads",fbTestADS);
    	
    	dPanel.finishBlock();
        
	}
	
	public void save()
	{
		ByRobinGameExtension.FBBANNERPLACE = fbBannerPlace.getText();
		ByRobinGameExtension.FBRECTBANNERPLACE = fbRectBannerPlace.getText();
		ByRobinGameExtension.FBINTERSTITIALPLACE = fbInterstitialPlace.getText();
		ByRobinGameExtension.FBTESTADS = fbTestADS.isSelected();
		
		updateWrapper();
		revalidate();
		repaint();
	}
	

	public static FacebookPage get()
	{
		if (_instance == null)
			_instance = new FacebookPage();

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

