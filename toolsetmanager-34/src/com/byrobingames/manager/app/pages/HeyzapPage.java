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


public class HeyzapPage extends SourcePage implements FieldVerifier{
	
	private static HeyzapPage _instance;
	
	private static String PAGE_TITLE = "Heyzap Configuration Page";
	private static String EXTEN_NAME = "Heyzap Engine Extension";
	private static String BLOCK_HELP = "https://github.com/byrobingames/heyzap/wiki/3.-Using-the-Heyzap-Extension#32-how-to-use-the-blocks";
	private static String REPOS_ID = "heyzap";
	
	private AutoVerifyField HZPublisherID;
	
	private HeyzapPage()
	{
		super(PAGE_TITLE,BLOCK_HELP, EXTEN_NAME, REPOS_ID);
		
		setSettings();
		
		add(dPanel, BorderLayout.NORTH);
	}
	
	public void setSettings(){
		JLabel title = new JLabel("Heyzap Account Settings");
		title.setFont(SW.get().getFonts().getTitleBoldFont());
		title.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(title);
		
		JLabel note = new JLabel("<html>Note: Fill in your Publisher ID from <a href=\"https://developers.heyzap.com/dashboard\">https://developers.heyzap.com/dashboard</a></html>");
        note.setFont(SW.get().getFonts().getNormalFont());
		note.setForeground(Theme.TEXT_COLOR);
		note.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	App.showWebpage("https://developers.heyzap.com/dashboard");
            }
        });
		dPanel.addGenericRow(note);
		
		//ClientKey
		dPanel.startBlock();
        HZPublisherID = new AutoVerifyField(16,this,"");
        HZPublisherID.setText(ByRobinGameExtension.HZPUBLISHERID);
        HZPublisherID.disableErrorIcon();
		dPanel.addTextFieldRestrictedWidth("Publisher ID", HZPublisherID, 300);
		dPanel.finishBlock();
	}
	
	public void save()
	{
		ByRobinGameExtension.HZPUBLISHERID = HZPublisherID.getText();
		
		updateWrapper();
		revalidate();
		repaint();
	}
	

	public static HeyzapPage get()
	{
		if (_instance == null)
			_instance = new HeyzapPage();

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

