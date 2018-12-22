package com.byrobingames.manager.app.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EtchedBorder;

import org.apache.log4j.Logger;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;

import com.byrobingames.manager.ByRobinGameExtension;
import com.byrobingames.manager.res.Resources;

import stencyl.sw.SW;
import stencyl.sw.app.App;
import stencyl.sw.lnf.Theme;
import stencyl.sw.util.UI;
import stencyl.sw.util.comp.CapsuleButton;
import stencyl.sw.util.comp.text.AutoVerifyField;
import stencyl.sw.util.comp.text.FieldVerifier;
import stencyl.sw.util.dg.DialogPanel;

public class TapdaqPage extends SourcePage implements FieldVerifier {
		
		private static TapdaqPage _instance;
		private static final Logger log = Logger.getLogger(ByRobinGameExtension.class);
		
		private static String PAGE_TITLE = "Tapdaq Configuration Page";
		private static String EXTEN_NAME = "Tapdaq Engine Extension";
		private static String BLOCK_HELP = "https://github.com/byrobingames/tapdaq/wiki/Implement-Tapdaq-Advertising-in-your-Stencyl-game#blocks";
		private static String REPOS_ID = "tapdaq";
		
		private JCheckBox TDTestADS;
		private AutoVerifyField TDClientKey;
		private AutoVerifyField TDIosAppId;
		private AutoVerifyField TDAndroidAppId;
		private AutoVerifyField TDAdTypeInterstitial;
		private AutoVerifyField TDAdTypeVideo;
		private AutoVerifyField TDAdTypeRewardedVideo;
		private AutoVerifyField TDAdTypeMoreApps;
		
		private boolean enabled = false;
		
		private TapdaqPage()
		{
			super(PAGE_TITLE,BLOCK_HELP, EXTEN_NAME, REPOS_ID);
			
			setSettings();
			setAdTypes();
			setImportanLabelt();
			setRightPanel();
			
			JScrollPane scroller = UI.createScrollPane(dPanel);
			scroller.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Theme.BORDER_COLOR));
			
			add(scroller,BorderLayout.CENTER);
		}
		
		public void setSettings(){
			
			JLabel title = new JLabel("<html>Tapdaq Account Settings</html>");
			title.setFont(SW.get().getFonts().getTitleBoldFont());
			title.setForeground(Theme.TEXT_COLOR);
			dPanel.addGenericRow(title);
			
			JLabel note = new JLabel("<html>Note: Fill in your Client Key and App ID from <a href=\"https://tapdaq.com/login\">https://tapdaq.com/login</a></html>");
		    note.setFont(SW.get().getFonts().getNormalFont());
		    note.setForeground(Theme.TEXT_COLOR);
			note.addMouseListener(new MouseAdapter() {
		           @Override
		           public void mouseClicked(MouseEvent e) {
		           	App.showWebpage("https://tapdaq.com/login");
		           }
		       });
			dPanel.addGenericRow(note);
			
			//dPanel.startBlock();
			//ClientKey
			TDClientKey = new AutoVerifyField(16,this,"");
			TDClientKey.setText(ByRobinGameExtension.TDClientKey);
			TDClientKey.disableErrorIcon();
			dPanel.addTextFieldRestrictedWidth("Client key",TDClientKey,300);
	        
	        //IOS APPID
			TDIosAppId = new AutoVerifyField(16,this,"");
			TDIosAppId.setText(ByRobinGameExtension.TDIosAppId);
			TDIosAppId.disableErrorIcon();
			dPanel.addTextFieldRestrictedWidth("iOS AppID",TDIosAppId,300);
	        
	        //Android APPID
			TDAndroidAppId = new AutoVerifyField(16,this,"");
			TDAndroidAppId.setText(ByRobinGameExtension.TDAndroidAppId);
			TDAndroidAppId.disableErrorIcon();
			dPanel.addTextFieldRestrictedWidth("Android AppID",TDAndroidAppId,300);
			
			TDTestADS = new JCheckBox();
			TDTestADS.setSelected(ByRobinGameExtension.TDTestADS);
			dPanel.addCheckbox("Enable Testads",TDTestADS);
			
			dPanel.finishBlock();
			dPanel.startBlock();
			
		}
		
		public void setAdTypes(){
			
			JLabel title = new JLabel("Tapdaq Register Tags");
			title.setFont(SW.get().getFonts().getTitleBoldFont());
			title.setForeground(Theme.TEXT_COLOR);
			dPanel.addGenericRow(title);
			
			TDAdTypeInterstitial = new AutoVerifyField(16,this,"");
			TDAdTypeInterstitial.setText(ByRobinGameExtension.TDAdTypesInterstitial);
			TDAdTypeInterstitial.disableErrorIcon();
			dPanel.addTextFieldRestrictedWidth("TDTypesInterstitial",TDAdTypeInterstitial,300);
			
			TDAdTypeVideo = new AutoVerifyField(16,this,"");
			TDAdTypeVideo.setText(ByRobinGameExtension.TDAdTypesVideo);
			TDAdTypeVideo.disableErrorIcon();
			dPanel.addTextFieldRestrictedWidth("TDAdTypeVideo",TDAdTypeVideo,300);
			
			TDAdTypeRewardedVideo = new AutoVerifyField(16,this,"");
			TDAdTypeRewardedVideo.setText(ByRobinGameExtension.TDAdTypesRewardedVideo);
			TDAdTypeRewardedVideo.disableErrorIcon();
			dPanel.addTextFieldRestrictedWidth("TDAdTypeRewardedVideo",TDAdTypeRewardedVideo,300);
			
			TDAdTypeMoreApps = new AutoVerifyField(16,this,"");
			TDAdTypeMoreApps.setText(ByRobinGameExtension.TDAdTypeMoreApps);
			TDAdTypeMoreApps.disableErrorIcon();
			dPanel.addTextFieldRestrictedWidth("TDAdTypeMoreApps",TDAdTypeMoreApps,300);
	        
	        JLabel note = new JLabel("<html>Note: you can use any custom string e.g. \"my_custom_tag\" (without \").<br>You can also add multiple placement tags by comma-separating them e.g. \"main_menu,my_custom_tag\" (without \").</html>");
			note.setForeground(Theme.TEXT_COLOR.darker());
			dPanel.addGenericRow("", note);
			
			dPanel.finishBlock();

	        
		}
		
		public void setImportanLabelt(){
			
			JLabel important = new JLabel("Important for publish to iOS");
			important.setFont(SW.get().getFonts().getTitleBoldFont());
			important.setForeground(Color.RED);
			dPanel.addGenericRow(important,1);
			
			JLabel importantNote = new JLabel("<html>If you ready to publish to iOS, you have to create an .ipa with Xcode<br>"
					+ "Follow the tutorial how to do this: https://github.com/byrobingames/tapdaq/wiki/iOS:-Publish-ipa-with-Xcode</html>");
			importantNote.setFont(SW.get().getFonts().getTitleBoldFont());
			importantNote.setForeground(Theme.TEXT_COLOR);
			importantNote.addMouseListener(new MouseAdapter() {
		           @Override
		           public void mouseClicked(MouseEvent e) {
		           	App.showWebpage("https://github.com/byrobingames/tapdaq/wiki/iOS:-Publish-ipa-with-Xcode");
		           }
		       });
			dPanel.addGenericRow(importantNote);
			
		}
		
		public void setRightPanel(){
			
			DialogPanel right = new DialogPanel(Theme.LIGHT_BG_COLOR3);
			
			//adcolony
			JPanel adcolonypanel = new JPanel(new BorderLayout());
			adcolonypanel.setOpaque(false);
			adcolonypanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			adcolonypanel.setPreferredSize(new Dimension(200,30));
			JLabel nwadcolony = new JLabel("<html>Adcolony: </html>");
			nwadcolony.setFont(SW.get().getFonts().getTitleBoldFont());
			nwadcolony.setForeground(Theme.TEXT_COLOR);
			nwadcolony.setAlignmentX(25);
			CapsuleButton btadcolony = new CapsuleButton();
			btadcolony = createToggleButton("adcolony");
			adcolonypanel.add(nwadcolony,BorderLayout.WEST);
			adcolonypanel.add(btadcolony,BorderLayout.EAST);
			
			//admob
			/*JPanel admobpanel = new JPanel(new BorderLayout());
			admobpanel.setOpaque(false);
			admobpanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			admobpanel.setPreferredSize(new Dimension(200,30));
			JLabel nwadmob = new JLabel("<html>Admob: </html>");
			nwadmob.setFont(SW.get().getFonts().getTitleBoldFont());
			nwadmob.setForeground(Theme.TEXT_COLOR);
			nwadmob.setAlignmentX(25);
			CapsuleButton btadmob = new CapsuleButton();
			btadmob = createToggleButton("admob");
			admobpanel.add(nwadmob,BorderLayout.WEST);
			admobpanel.add(btadmob,BorderLayout.EAST);*/
			
			//applovin
			JPanel applovinpanel = new JPanel(new BorderLayout());
			applovinpanel.setOpaque(false);
			applovinpanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			applovinpanel.setPreferredSize(new Dimension(200,30));
			JLabel nwapplovin = new JLabel("<html>AppLovin: </html>");
			nwapplovin.setFont(SW.get().getFonts().getTitleBoldFont());
			nwapplovin.setForeground(Theme.TEXT_COLOR);
			nwapplovin.setAlignmentX(25);
			CapsuleButton btapplovin = new CapsuleButton();
			btapplovin = createToggleButton("applovin");
			applovinpanel.add(nwapplovin,BorderLayout.WEST);
			applovinpanel.add(btapplovin,BorderLayout.EAST);
			
			//facebook
			JPanel facebookpanel = new JPanel(new BorderLayout());
			facebookpanel.setOpaque(false);
			facebookpanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			facebookpanel.setPreferredSize(new Dimension(200,30));
			JLabel nwfacebook = new JLabel("<html>Facebook: </html>");
			nwfacebook.setFont(SW.get().getFonts().getTitleBoldFont());
			nwfacebook.setForeground(Theme.TEXT_COLOR);
			nwfacebook.setAlignmentX(25);
			CapsuleButton btfacebook = new CapsuleButton();
			btfacebook = createToggleButton("facebook");
			facebookpanel.add(nwfacebook,BorderLayout.WEST);
			facebookpanel.add(btfacebook,BorderLayout.EAST);
			
			//chartboost
			JPanel chartboostpanel = new JPanel(new BorderLayout());
			chartboostpanel.setOpaque(false);
			chartboostpanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			chartboostpanel.setPreferredSize(new Dimension(200,30));
			JLabel nwchartboost = new JLabel("<html>Chartboost: </html>");
			nwchartboost.setFont(SW.get().getFonts().getTitleBoldFont());
			nwchartboost.setForeground(Theme.TEXT_COLOR);
			nwchartboost.setAlignmentX(25);
			CapsuleButton btchartboost = new CapsuleButton();
			btchartboost = createToggleButton("chartboost");
			chartboostpanel.add(nwchartboost,BorderLayout.WEST);
			chartboostpanel.add(btchartboost,BorderLayout.EAST);
			
			//Hyprmx
			JPanel hyprmxpanel = new JPanel(new BorderLayout());
			hyprmxpanel.setOpaque(false);
			hyprmxpanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			hyprmxpanel.setPreferredSize(new Dimension(200,30));
			JLabel nwhyprmx = new JLabel("<html>Hyprmx: </html>");
			nwhyprmx.setFont(SW.get().getFonts().getTitleBoldFont());
			nwhyprmx.setForeground(Theme.TEXT_COLOR);
			nwhyprmx.setAlignmentX(25);
			CapsuleButton bthyprmx = new CapsuleButton();
			bthyprmx = createToggleButton("hyprmx");
			hyprmxpanel.add(nwhyprmx,BorderLayout.WEST);
			hyprmxpanel.add(bthyprmx,BorderLayout.EAST);
			
			//Inmobi
			JPanel inmobipanel = new JPanel(new BorderLayout());
			inmobipanel.setOpaque(false);
			inmobipanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			inmobipanel.setPreferredSize(new Dimension(200,30));
			JLabel nwinmobi = new JLabel("<html>InMobi: </html>");
			nwinmobi.setFont(SW.get().getFonts().getTitleBoldFont());
			nwinmobi.setForeground(Theme.TEXT_COLOR);
			nwinmobi.setAlignmentX(25);
			CapsuleButton btinmobi = new CapsuleButton();
			btinmobi = createToggleButton("inmobi");
			inmobipanel.add(nwinmobi,BorderLayout.WEST);
			inmobipanel.add(btinmobi,BorderLayout.EAST);
			
			//Ironsource
			JPanel ironsourcepanel = new JPanel(new BorderLayout());
			ironsourcepanel.setOpaque(false);
			ironsourcepanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			ironsourcepanel.setPreferredSize(new Dimension(200,30));
			JLabel nwironsource = new JLabel("<html>Ironsource: </html>");
			nwironsource.setFont(SW.get().getFonts().getTitleBoldFont());
			nwironsource.setForeground(Theme.TEXT_COLOR);
			nwironsource.setAlignmentX(25);
			CapsuleButton btironsource = new CapsuleButton();
			btironsource = createToggleButton("ironsource");
			ironsourcepanel.add(nwironsource,BorderLayout.WEST);
			ironsourcepanel.add(btironsource,BorderLayout.EAST);
			
			//Mopub
			JPanel mopubpanel = new JPanel(new BorderLayout());
			mopubpanel.setOpaque(false);
			mopubpanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			mopubpanel.setPreferredSize(new Dimension(200,30));
			JLabel nwmopub = new JLabel("<html>Mopub: </html>");
			nwmopub.setFont(SW.get().getFonts().getTitleBoldFont());
			nwmopub.setForeground(Theme.TEXT_COLOR);
			nwmopub.setAlignmentX(25);
			CapsuleButton btmopub = new CapsuleButton();
			btmopub = createToggleButton("mopub");
			mopubpanel.add(nwmopub,BorderLayout.WEST);
			mopubpanel.add(btmopub,BorderLayout.EAST);
			
			//receptiv
			JPanel receptivpanel = new JPanel(new BorderLayout());
			receptivpanel.setOpaque(false);
			receptivpanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			receptivpanel.setPreferredSize(new Dimension(200,30));
			JLabel nwreceptiv = new JLabel("<html>Receptiv: </html>");
			nwreceptiv.setFont(SW.get().getFonts().getTitleBoldFont());
			nwreceptiv.setForeground(Theme.TEXT_COLOR);
			nwreceptiv.setAlignmentX(25);
			CapsuleButton btreceptiv = new CapsuleButton();
			btreceptiv = createToggleButton("receptiv");
			receptivpanel.add(nwreceptiv,BorderLayout.WEST);
			receptivpanel.add(btreceptiv,BorderLayout.EAST);
			
			//Tapjoy
			JPanel tapjoyvpanel = new JPanel(new BorderLayout());
			tapjoyvpanel.setOpaque(false);
			tapjoyvpanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			tapjoyvpanel.setPreferredSize(new Dimension(200,30));
			JLabel nwtapjoy = new JLabel("<html>Tapjoy: </html>");
			nwtapjoy.setFont(SW.get().getFonts().getTitleBoldFont());
			nwtapjoy.setForeground(Theme.TEXT_COLOR);
			nwtapjoy.setAlignmentX(25);
			CapsuleButton bttapjoy = new CapsuleButton();
			bttapjoy = createToggleButton("tapjoy");
			tapjoyvpanel.add(nwtapjoy,BorderLayout.WEST);
			tapjoyvpanel.add(bttapjoy,BorderLayout.EAST);
			
			//Unityads
			JPanel unityadspanel = new JPanel(new BorderLayout());
			unityadspanel.setOpaque(false);
			unityadspanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			unityadspanel.setPreferredSize(new Dimension(200,30));
			JLabel nwtunityads = new JLabel("<html>UnityAds: </html>");
			nwtunityads.setFont(SW.get().getFonts().getTitleBoldFont());
			nwtunityads.setForeground(Theme.TEXT_COLOR);
			nwtunityads.setAlignmentX(25);
			CapsuleButton btunityads = new CapsuleButton();
			btunityads = createToggleButton("unityads");
			unityadspanel.add(nwtunityads,BorderLayout.WEST);
			unityadspanel.add(btunityads,BorderLayout.EAST);
			
			//Vungle
			JPanel vunglepanel = new JPanel(new BorderLayout());
			vunglepanel.setOpaque(false);
			vunglepanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			vunglepanel.setPreferredSize(new Dimension(200,30));
			JLabel nwtvungle = new JLabel("<html>Vungle: </html>");
			nwtvungle.setFont(SW.get().getFonts().getTitleBoldFont());
			nwtvungle.setForeground(Theme.TEXT_COLOR);
			nwtvungle.setAlignmentX(25);
			CapsuleButton btvungle = new CapsuleButton();
			btvungle = createToggleButton("vungle");
			vunglepanel.add(nwtvungle,BorderLayout.WEST);
			vunglepanel.add(btvungle,BorderLayout.EAST);
			
			
			
			right.addGenericRow(adcolonypanel);
			//right.addGenericRow(admobpanel);
			right.addGenericRow(applovinpanel);
			right.addGenericRow(chartboostpanel);
			right.addGenericRow(facebookpanel);
			right.addGenericRow(hyprmxpanel);
			right.addGenericRow(inmobipanel);
			right.addGenericRow(ironsourcepanel);
			right.addGenericRow(mopubpanel);
			right.addGenericRow(receptivpanel);
			right.addGenericRow(tapjoyvpanel);
			right.addGenericRow(unityadspanel);
			right.addGenericRow(vunglepanel);
			
			add(right,BorderLayout.EAST);
		}
		
		public void save()
		{
			ByRobinGameExtension.TDClientKey = TDClientKey.getText();
			ByRobinGameExtension.TDIosAppId = TDIosAppId.getText();
			ByRobinGameExtension.TDAndroidAppId = TDAndroidAppId.getText();
			ByRobinGameExtension.TDTestADS = TDTestADS.isSelected();
			ByRobinGameExtension.TDAdTypesInterstitial = TDAdTypeInterstitial.getText();
			ByRobinGameExtension.TDAdTypesVideo = TDAdTypeVideo.getText();
			ByRobinGameExtension.TDAdTypesRewardedVideo = TDAdTypeRewardedVideo.getText();
			ByRobinGameExtension.TDAdTypeMoreApps = TDAdTypeMoreApps.getText();
			
			updateWrapper();
			revalidate();
			repaint();
			
		}
		
		private CapsuleButton createToggleButton(String network){
			
			final CapsuleButton enable = new CapsuleButton();
			enable.setActionCommand(network);
			
			if(network.equals("adcolony")){
				enabled = ByRobinGameExtension.tpadcolonyen;
			}
			else if(network.equals("admob")){
				enabled = ByRobinGameExtension.tpadmoben;
			}
			else if(network.equals("applovin")){
				enabled = ByRobinGameExtension.tpapplovinen;
			}
			else if(network.equals("facebook")){
				enabled = ByRobinGameExtension.tpfacebooken;
			}
			else if(network.equals("chartboost")){
				enabled = ByRobinGameExtension.tpchartboosten;
			}
			else if(network.equals("hyprmx")){
				enabled = ByRobinGameExtension.tphyprmxen;
			}
			else if(network.equals("inmobi")){
				enabled = ByRobinGameExtension.tpinmobien;
			}
			else if(network.equals("ironsource")){
				enabled = ByRobinGameExtension.tpironsourceen;
			}
			else if(network.equals("mopub")){
				enabled = ByRobinGameExtension.tpmopuben;
			}
			else if(network.equals("receptiv")){
				enabled = ByRobinGameExtension.tpreceptiven;
			}
			else if(network.equals("tapjoy")){
				enabled = ByRobinGameExtension.tptapjoyen;
			}
			else if(network.equals("unityads")){
				enabled = ByRobinGameExtension.tpunityadsen;
			}
			else if(network.equals("vungle")){
				enabled = ByRobinGameExtension.tpvungleen;
			}
			
			
			if(enabled)
			{
				enable.useRedTheme();
			}
			
			else
			{
				enable.useGreenTheme();
			}
			
			enable.setText(enabled ? "Disable" : "Enable");
			enable.addActionListener
			(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent evt)
					{
						log.info("byRobinExtension : enable/disable" + evt.getActionCommand());
						boolean enabled2 = false;
						if(evt.getActionCommand().equals("adcolony")){
							if(!ByRobinGameExtension.tpadcolonyen){
								enabled2 = true;
							}
						}
						else if(evt.getActionCommand().equals("admob")){
							if(!ByRobinGameExtension.tpadmoben){
								enabled2 = true;
							}
						}
						else if(evt.getActionCommand().equals("applovin")){
							if(!ByRobinGameExtension.tpapplovinen){
								enabled2 = true;
							}
						}
						else if(evt.getActionCommand().equals("facebook")){
							if(!ByRobinGameExtension.tpfacebooken){
								enabled2 = true;
							}
						}
						else if(evt.getActionCommand().equals("chartboost")){
							if(!ByRobinGameExtension.tpchartboosten){
								enabled2 = true;
							}
						}
						else if(evt.getActionCommand().equals("hyprmx")){
							if(!ByRobinGameExtension.tphyprmxen){
								enabled2 = true;
							}
						}
						else if(evt.getActionCommand().equals("inmobi")){
							if(!ByRobinGameExtension.tpinmobien){
								enabled2 = true;
							}
						}
						else if(evt.getActionCommand().equals("ironsource")){
							if(!ByRobinGameExtension.tpironsourceen){
								enabled2 = true;
							}
						}
						else if(evt.getActionCommand().equals("mopub")){
							if(!ByRobinGameExtension.tpmopuben){
								enabled2 = true;
							}
						}
						else if(evt.getActionCommand().equals("receptiv")){
							if(!ByRobinGameExtension.tpreceptiven){
								enabled2 = true;
							}
						}
						else if(evt.getActionCommand().equals("tapjoy")){
							if(!ByRobinGameExtension.tptapjoyen){
								enabled2 = true;
							}
						}
						else if(evt.getActionCommand().equals("unityads")){
							if(!ByRobinGameExtension.tpunityadsen){
								enabled2 = true;
							}
						}
						else if(evt.getActionCommand().equals("vungle")){
							if(!ByRobinGameExtension.tpvungleen){
								enabled2 = true;
							}
						}
						
						enable.setText(enabled2 ? "Disable" : "Enable");
						
						
						if(enabled2)
						{
							enable.useRedTheme();
							if(evt.getActionCommand().equals("adcolony")){
								ByRobinGameExtension.tpadcolonyen = true;
							}
							else if(evt.getActionCommand().equals("admob")){
								ByRobinGameExtension.tpadmoben = true;
							}
							else if(evt.getActionCommand().equals("applovin")){
								ByRobinGameExtension.tpapplovinen = true;
							}
							else if(evt.getActionCommand().equals("facebook")){
								ByRobinGameExtension.tpfacebooken = true;
							}
							else if(evt.getActionCommand().equals("chartboost")){
								ByRobinGameExtension.tpchartboosten = true;
							}
							else if(evt.getActionCommand().equals("hyprmx")){
								ByRobinGameExtension.tphyprmxen = true;
							}
							else if(evt.getActionCommand().equals("inmobi")){
								ByRobinGameExtension.tpinmobien = true;
							}
							else if(evt.getActionCommand().equals("ironsource")){
								ByRobinGameExtension.tpironsourceen = true;
							}
							else if(evt.getActionCommand().equals("mopub")){
								ByRobinGameExtension.tpmopuben = true;
							}
							else if(evt.getActionCommand().equals("receptiv")){
								ByRobinGameExtension.tpreceptiven = true;
							}
							else if(evt.getActionCommand().equals("tapjoy")){
								ByRobinGameExtension.tptapjoyen = true;
							}
							else if(evt.getActionCommand().equals("unityads")){
								ByRobinGameExtension.tpunityadsen = true;
							}
							else if(evt.getActionCommand().equals("vungle")){
								ByRobinGameExtension.tpvungleen = true;
							}
						}
						
						else
						{
							enable.useGreenTheme();
							
							if(evt.getActionCommand().equals("adcolony")){
								ByRobinGameExtension.tpadcolonyen = false;
							}
							else if(evt.getActionCommand().equals("admob")){
								ByRobinGameExtension.tpadmoben = false;
							}
							else if(evt.getActionCommand().equals("applovin")){
								ByRobinGameExtension.tpapplovinen = false;
							}
							else if(evt.getActionCommand().equals("facebook")){
								ByRobinGameExtension.tpfacebooken = false;
							}
							else if(evt.getActionCommand().equals("chartboost")){
								ByRobinGameExtension.tpchartboosten = false;
							}
							else if(evt.getActionCommand().equals("hyprmx")){
								ByRobinGameExtension.tphyprmxen = false;
							}
							else if(evt.getActionCommand().equals("inmobi")){
								ByRobinGameExtension.tpinmobien = false;
							}
							else if(evt.getActionCommand().equals("ironsource")){
								ByRobinGameExtension.tpironsourceen = false;
							}
							else if(evt.getActionCommand().equals("mopub")){
								ByRobinGameExtension.tpmopuben = false;
							}
							else if(evt.getActionCommand().equals("receptiv")){
								ByRobinGameExtension.tpreceptiven = false;
							}
							else if(evt.getActionCommand().equals("tapjoy")){
								ByRobinGameExtension.tptapjoyen = false;
							}
							else if(evt.getActionCommand().equals("unityads")){
								ByRobinGameExtension.tpunityadsen = false;
							}
							else if(evt.getActionCommand().equals("vungle")){
								ByRobinGameExtension.tpvungleen = false;
							}
						}
					}
				}
			);
			
			return enable;
		}
		
		public static TapdaqPage get()
		{
			if (_instance == null)
				_instance = new TapdaqPage();

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
