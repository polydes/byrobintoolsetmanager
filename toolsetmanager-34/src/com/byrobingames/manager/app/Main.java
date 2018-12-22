package com.byrobingames.manager.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import org.apache.log4j.Logger;

import com.byrobingames.manager.ByRobinGameExtension;
import com.byrobingames.manager.app.StatusBar;
import com.byrobingames.manager.app.pages.AppLovinPage;
import com.byrobingames.manager.app.pages.FacebookPage;
import com.byrobingames.manager.app.pages.GeneralPage;
import com.byrobingames.manager.app.pages.HeyzapPage;
import com.byrobingames.manager.app.pages.LeadboltPage;
import com.byrobingames.manager.app.pages.LocalNotifPage;
import com.byrobingames.manager.app.pages.ReplayKitPage;
import com.byrobingames.manager.app.pages.SimpleSharePage;
import com.byrobingames.manager.app.pages.SourcePage;
import com.byrobingames.manager.app.pages.StartAppPage;
import com.byrobingames.manager.app.pages.TapdaqPage;
import com.byrobingames.manager.app.pages.UnityAdsPage;
import com.byrobingames.manager.app.pages.VunglePage;
import com.byrobingames.manager.app.pages.WebViewPage;
import com.byrobingames.manager.res.Resources;
import com.byrobingames.manager.writer.FileWriter;

import stencyl.core.lib.Game;

import stencyl.sw.lnf.Theme;
import stencyl.sw.util.dg.DialogPanel;

public class Main extends JPanel
{

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Main.class);
	public static Main _instance = null;
	
	private final int BUTTON_WIDTH = 70;
	private final int BUTTON_HEIGHT = 57;
	
	private ButtonGroup buttonGroup;
	private JToggleButton generalButton;
	private JToggleButton tapdaqButton;
	public JToggleButton appLovinButton;
	public JToggleButton facebookButton;
	private JToggleButton heyzapButton;
	private JToggleButton leadboltButton;
	private JToggleButton localnotifButton;
	private JToggleButton replaykitButton;
	private JToggleButton simpleshareButton;
	private JToggleButton startappButton;
	private JToggleButton unityadsButton;
	private JToggleButton vungleButton;
	private JToggleButton webviewButton;
	
	private JPanel buttonBar;
	private JPanel buttonBar1;
	private JPanel currentPage;
	//private DialogPanel currentPage;
	
	public final Color SIDEBAR_COLOR = new Color(62, 62, 62);
	public final Color MAIN_COLOR = new Color(43, 43, 43);
    
    public Main()
	{
    	super(new BorderLayout());
    	
    	add(createVerticalButtonBar(), BorderLayout.WEST);
    	add(StatusBar.createStatusBar(), BorderLayout.SOUTH);
    	
    	switchToPage("General");
			
}
	public static Main get()
	{
		if(_instance == null)
			_instance = new Main();
		
		return _instance;
	}
	
	private JPanel createVerticalButtonBar()
	{
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setBackground(SIDEBAR_COLOR);
		buttonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(0x333333)));
		
		buttonBar = new JPanel();
		buttonBar1 = new JPanel();
		
		buttonBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0x333333)));
		buttonBar1.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0x333333)));
		
		buttonBar.setLayout(new BoxLayout(buttonBar, BoxLayout.Y_AXIS));
		buttonBar.setBackground(SIDEBAR_COLOR);
		buttonBar.setMaximumSize(new Dimension(BUTTON_WIDTH,1000));
		buttonBar.setMinimumSize(new Dimension(BUTTON_WIDTH,100));
		
		buttonBar1.setLayout(new BoxLayout(buttonBar1, BoxLayout.Y_AXIS));
		buttonBar1.setBackground(SIDEBAR_COLOR);
		buttonBar1.setMaximumSize(new Dimension(BUTTON_WIDTH,1000));
		buttonBar1.setMinimumSize(new Dimension(BUTTON_WIDTH,100));
		
		//---
		
		buttonGroup = new ButtonGroup();
		
		generalButton = createButton("General", Resources.loadIcon("images/game_settings.png"));
		appLovinButton = createButton("AppLovin", Resources.loadIcon("images/applovin.png"));
		facebookButton = createButton("Facebook", Resources.loadIcon("images/facebook.png"));
		heyzapButton = createButton("Heyzap", Resources.loadIcon("images/heyzap.png"));
		leadboltButton = createButton("Leadbolt", Resources.loadIcon("images/leadbolt.png"));
		localnotifButton = createButton("LocalNotif", Resources.loadIcon("images/localnotif.png"));
		replaykitButton = createButton("ReplayKit", Resources.loadIcon("images/replaykit.png"));
		simpleshareButton = createButton("SimpleShare", Resources.loadIcon("images/simpleshare.png"));
		startappButton  = createButton("StartApp", Resources.loadIcon("images/startapp.png"));
		tapdaqButton = createButton("Tapdaq", Resources.loadIcon("images/tapdaq.png"));
		unityadsButton = createButton("UnityAds", Resources.loadIcon("images/unityads.png"));
		vungleButton = createButton("Vungle", Resources.loadIcon("images/vungle.png"));
		webviewButton = createButton("WebView", Resources.loadIcon("images/webview.png"));
		
		buttonBar.add(generalButton);
		buttonBar1.add(appLovinButton);
		buttonBar.add(facebookButton);
		buttonBar1.add(heyzapButton);
		buttonBar.add(leadboltButton);
		buttonBar1.add(localnotifButton);
		buttonBar.add(replaykitButton);
		buttonBar1.add(simpleshareButton);
		buttonBar.add(startappButton);
		buttonBar1.add(tapdaqButton);
		buttonBar.add(unityadsButton);
		buttonBar1.add(vungleButton);
		buttonBar.add(webviewButton);
		
		buttonPanel.add(buttonBar, BorderLayout.WEST);
		buttonPanel.add(buttonBar1, BorderLayout.EAST);
		//buttonPanel.add(StatusBar.createStatusBar(), BorderLayout.SOUTH);
		
		return buttonPanel;
	}
	
	public JToggleButton createButton(String name, ImageIcon icon)
	{
		JToggleButton button = new JToggleButton()
		{
			@Override
			public void paintComponent(Graphics g)
			{
				if(!isSelected())
				{
					super.paintComponent(g);
					setForeground(Theme.TEXT_COLOR.darker());
					return;
				}
				
				g.setColor(new Color(0x666666));
				setForeground(Theme.TEXT_COLOR);
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		
		button.setIconTextGap(8);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBorder
		(
			BorderFactory.createCompoundBorder
			(
				BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(0x454545)), 
				BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(0x333333))
			)
		);
		
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setVerticalAlignment(SwingConstants.CENTER);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setForeground(Theme.TEXT_COLOR.darker());		
		
		button.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		
		button.setAction
		(
			new AbstractAction(name, icon)
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					switchToPage(e.getActionCommand());
				}
			}
		);
		
		buttonGroup.add(button);
				
		return button;
	}

	
	public void switchToPage(String pageName)
	{
		if(currentPage != null)
			remove(currentPage);invalidate();
			
		if(pageName.equals("General"))
		{
			currentPage = GeneralPage.get();			
			generalButton.setSelected(true);			
		}
		else if(pageName.equals("AppLovin"))
		{
			currentPage = AppLovinPage.get();			
			appLovinButton.setSelected(true);			
		}
		else if(pageName.equals("Facebook"))
		{
			currentPage = FacebookPage.get();			
			facebookButton.setSelected(true);			
		}
		else if(pageName.equals("Heyzap"))
		{
			currentPage = HeyzapPage.get();			
			heyzapButton.setSelected(true);			
		}
		else if(pageName.equals("Leadbolt"))
		{
			currentPage = LeadboltPage.get();			
			leadboltButton.setSelected(true);			
		}
		else if(pageName.equals("LocalNotif"))
		{
			currentPage = LocalNotifPage.get();			
			localnotifButton.setSelected(true);			
		}
		else if(pageName.equals("ReplayKit"))
		{
			currentPage = ReplayKitPage.get();			
			replaykitButton.setSelected(true);			
		}
		else if(pageName.equals("SimpleShare"))
		{
			currentPage = SimpleSharePage.get();			
			simpleshareButton.setSelected(true);			
		}
		else if(pageName.equals("StartApp"))
		{
			currentPage = StartAppPage.get();			
			startappButton.setSelected(true);			
		}
		else if(pageName.equals("Tapdaq"))
		{
			currentPage = TapdaqPage.get();			
			tapdaqButton.setSelected(true);			
		}
		else if(pageName.equals("UnityAds"))
		{
			currentPage = UnityAdsPage.get();			
			unityadsButton.setSelected(true);			
		}
		else if(pageName.equals("Vungle"))
		{
			currentPage = VunglePage.get();			
			vungleButton.setSelected(true);			
		}
		else if(pageName.equals("WebView"))
		{
			currentPage = WebViewPage.get();			
			webviewButton.setSelected(true);			
		}
		
		//currentPage.setLayout(null);
		//currentPage.setLayout(new BorderLayout());
		currentPage.setBackground(Theme.LIGHT_BG_COLOR3);
		add(currentPage, BorderLayout.CENTER);
		
		revalidate();
		repaint();
	}
	
	public static void disposePages()
	{	
		GeneralPage.disposeInstance();
		AppLovinPage.disposeInstance();
		FacebookPage.disposeInstance();
		HeyzapPage.disposeInstance();
		LeadboltPage.disposeInstance();
		LocalNotifPage.disposeInstance();
		ReplayKitPage.disposeInstance();
		SimpleSharePage.disposeInstance();
		StartAppPage.disposeInstance();
		TapdaqPage.disposeInstance();
		UnityAdsPage.disposeInstance();
		VunglePage.disposeInstance();
		WebViewPage.disposeInstance();
		
		_instance = null;
	}

	public void gameSaved()
	{
		
		GeneralPage.get().save();
		AppLovinPage.get().save();
		FacebookPage.get().save();
		HeyzapPage.get().save();
		LeadboltPage.get().save();
		LocalNotifPage.get().save();
		ReplayKitPage.get().save();
		SimpleSharePage.get().save();
		StartAppPage.get().save();
		TapdaqPage.get().save();
		UnityAdsPage.get().save();
		VunglePage.get().save();
		WebViewPage.get().save();
		
		currentPage.revalidate();
		currentPage.repaint();
	}
	
	public void writeBuildFiles() throws IOException
	{
		new FileWriter(Game.getGame()).writeByRobinAssets();
		new FileWriter(Game.getGame()).writeAppLovinIncludeFile();
		new FileWriter(Game.getGame()).writeTapdaqIncludeFile();
	}
	
	
}

