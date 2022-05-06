package com.byrobingames.manager.app;

import static stencyl.sw.util.Locations.getPath;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.byrobingames.manager.app.pages.AppLovinPage;
import com.byrobingames.manager.app.pages.FacebookPage;
import com.byrobingames.manager.app.pages.GeneralPage;
import com.byrobingames.manager.app.pages.HeyzapPage;
import com.byrobingames.manager.app.pages.LeadboltPage;
import com.byrobingames.manager.app.pages.LocalNotifPage;
import com.byrobingames.manager.app.pages.ReplayKitPage;
import com.byrobingames.manager.app.pages.SimpleSharePage;
import com.byrobingames.manager.app.pages.StartAppPage;
import com.byrobingames.manager.app.pages.TapdaqPage;
import com.byrobingames.manager.app.pages.VunglePage;
import com.byrobingames.manager.app.pages.WebViewPage;
import com.byrobingames.manager.res.Resources;
import com.byrobingames.manager.writer.FileWriter;

import stencyl.core.lib.Game;
import stencyl.sw.SW;
import stencyl.sw.app.tasks.buildgame.GameBuilder;
import stencyl.sw.editors.game.advanced.ExtensionInstance;
import stencyl.sw.lnf.Theme;
import stencyl.sw.loc.LanguagePack;
import stencyl.sw.prefs.runconfigs.BuildConfig;
import stencyl.sw.util.FileHelper;
import stencyl.sw.util.Locations;
import stencyl.sw.util.ProgressDialog;
import stencyl.sw.util.dg.MessageDialog;
import stencyl.sw.util.net.NetHelper;
import stencyl.sw.util.net.ProgressTrackerAdapter;
import stencyl.util.ProcessHelper;
import stencyl.util.ProgressTracker;

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
		VunglePage.get().save();
		WebViewPage.get().save();
		
		currentPage.revalidate();
		currentPage.repaint();
	}
	
	//XXX: This should be easily available through extension API
	private static GameBuilder getRunningBuilder()
	{
		Optional<SwingWorker<Integer, Void>> builderWorker =
			SW.get().getTaskManager().getRunningTask
			(
				task -> task.getClass().getEnclosingClass() == GameBuilder.class
			);
		
		if(builderWorker.isEmpty())
		{
			log.info("No running GameBuilder.");
			return null;
		}
		
		Object o;
		try
		{
			o = FieldUtils.readDeclaredField(builderWorker.get(), "this$0", true);
		}
		catch (IllegalAccessException e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
		
		if(o instanceof GameBuilder)
		{
			return (GameBuilder) o;
		}
		
		log.error("Couldn't access GameBuilder object.");
		return null;
	}
	
	//XXX: This should be easily available through extension API
	private static BuildConfig getRunningBuildConfig()
	{
		GameBuilder builder = getRunningBuilder();
		
		if(builder == null) return null;
		
		BuildConfig buildConfig;
		try
		{
			buildConfig = (BuildConfig) FieldUtils.readField(builder, "buildConfig", true);
		}
		catch (IllegalAccessException e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
		
		return buildConfig;
	}
	
	public void writeBuildFiles() throws IOException
	{
		BuildConfig buildConfig = getRunningBuildConfig();

		for(ExtensionInstance inst : Game.getGame().getExtensionManager().getLoadedEnabledExtensions().values())
		{
			File extensionRoot = new File(Locations.getGameExtensionLocation(inst.getExtensionID()));
			File dependencies = new File(extensionRoot, "brg-dependencies.xml");
			if(dependencies.exists())
			{
				Element deps = FileHelper.readXMLFromFile(dependencies).getDocumentElement();
				resolveDependencies(deps.getChildNodes(), inst.getExtensionID(), buildConfig);
			}
		}
		
		new FileWriter(Game.getGame()).writeByRobinAssets();
		new FileWriter(Game.getGame()).writeAppLovinIncludeFile();
		new FileWriter(Game.getGame()).writeTapdaqIncludeFile();
	}
	
	private void resolveDependencies(NodeList nl, String extensionID, BuildConfig buildConfig)
	{
		for(int i = 0; i < nl.getLength(); ++i)
		{
			if(nl.item(i) instanceof Element)
			{
				resolveDependencies((Element) nl.item(i), extensionID, buildConfig);
			}
		}
	}
	
	private void resolveDependencies(Element e, String extensionID, BuildConfig buildConfig)
	{
		if(e.hasAttribute("if"))
		{
			String condition = e.getAttribute("if");
			if(!buildConfig.platform.getBuildString().equals(condition))
				return;
		}
		
		switch(e.getTagName())
		{
			case "section":
				resolveDependencies(e.getChildNodes(), extensionID, buildConfig);
				break;
			case "ndll":
				resolveNDLL(e, extensionID, buildConfig);
				break;
			default:
				log.error("Unkown dependency type: " + e.getTagName());
		}
	}

	/*
	 * Example:
	 * 
	 * <ndll buildfile="project/build" output="project/ndll/iPhone" />
	 */
	private void resolveNDLL(Element e, String extensionID, BuildConfig buildConfig)
	{
		File extensionFolder = new File(Locations.getGameExtensionLocation(extensionID));
		File buildFile = new File(extensionFolder, e.getAttribute("buildfile"));
		File output = new File(extensionFolder, e.getAttribute("output"));
		
		if(!output.exists())
		{
			FileHelper.makeFileExecutable(buildFile, true);
			ProcessHelper.command(buildFile.getAbsolutePath())
				.environment(Game.getGame().getHaxeEnvironment().getEnvironment())
				.workingDir(buildFile.getParentFile().toPath())
				.runAndWait(ProcessHelper.PRINT_ERRORS);
		}
	}
}