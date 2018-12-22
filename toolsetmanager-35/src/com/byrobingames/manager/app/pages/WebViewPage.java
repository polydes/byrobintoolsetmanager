package com.byrobingames.manager.app.pages;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.byrobingames.manager.ByRobinGameExtension;

import stencyl.sw.SW;
import stencyl.sw.lnf.Theme;
import stencyl.sw.util.UI;

public class WebViewPage extends SourcePage{
	
	private static String PAGE_TITLE = "WebView Configuration Page";
	private static String EXTEN_NAME = "WebView Engine Extension";
	private static String BLOCK_HELP = "https://github.com/byrobingames/webview2.0#documentation-and-block-examples";
	private static String REPOS_ID = "webview2.0";
	
	private static WebViewPage _instance;
	
	private JTextArea htmlArea;
	
	public WebViewPage(){
		super(PAGE_TITLE,BLOCK_HELP, EXTEN_NAME, REPOS_ID);
		
		setSettings();
		
		add(dPanel, BorderLayout.NORTH);
	}
	public void setSettings(){
		JLabel title = new JLabel("WebView Settings");
		title.setFont(SW.get().getFonts().getTitleBoldFont());
		title.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(title);

		JLabel label = new JLabel("<html>There are no special settings for WebView.<br><br>"
				+ "You can add HTML code below to use with the openHTML block<br>"
				+ "<strong>(Leave the textfield in openHTML block empty if you use the below textarea)<br> </strong>");
		label.setFont(SW.get().getFonts().getNormalFont());
		label.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(label);
		
		htmlArea = new JTextArea();
		htmlArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));	
		htmlArea.setText(ByRobinGameExtension.WVHTMLCODE);
		htmlArea.setLineWrap(true);
		htmlArea.setWrapStyleWord(true);
		JScrollPane scroll = UI.createScrollPane(htmlArea);
		scroll.setMinimumSize(new Dimension(100, 80));
		scroll.setPreferredSize(new Dimension(100, 300));
		dPanel.addGenericRow("HTML Code",scroll);
		
		dPanel.finishBlock();
	}
	
	public void save()
	{
		ByRobinGameExtension.WVHTMLCODE = htmlArea.getText();
		
		updateWrapper();
		revalidate();
		repaint();
		
	}
	
	public static WebViewPage get()
	{
		if (_instance == null)
			_instance = new WebViewPage();

		return _instance;
	}
	
	public static void disposeInstance()
	{
		_instance = null;
	}

}


