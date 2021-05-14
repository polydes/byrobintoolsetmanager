package com.byrobingames.manager.app.pages;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import misc.Version;
import stencyl.core.lib.Game;
import stencyl.sw.SW;
import stencyl.sw.app.App;
import stencyl.sw.editors.game.advanced.EngineExtension;
import stencyl.sw.editors.game.advanced.ExtensionInstanceManager;
import stencyl.sw.ext.ExtensionDependencyManager;
import stencyl.sw.ext.net.ExtensionRepositoryBrowser;
import stencyl.sw.lnf.Theme;
import stencyl.sw.util.Loader;
import stencyl.sw.util.comp.CapsuleButton;
import stencyl.sw.util.dg.DialogPanel;

public class SourcePage extends JPanel{
	
	protected DialogPanel dPanel;
	
	protected JPanel s;
	protected JPanel wrapper;
	
	private EngineExtension e;
	private Version exVersion;
	
	private String PAGE_TITLE;
	private String BLOCK_HELP;
	private String EXTEN_NAME;
	private String REPOS_ID;
	
	private Boolean askForUpdate = true;

	public SourcePage(
			String PAGE_TITLE,
			String BLOCK_HELP,
			String EXTEN_NAME,
			String REPOS_ID
			)
	{
		super(new BorderLayout());
		
		this.PAGE_TITLE = PAGE_TITLE;
		this.BLOCK_HELP = BLOCK_HELP;
		this.EXTEN_NAME = EXTEN_NAME;
		this.REPOS_ID = REPOS_ID;
		
		dPanel = new DialogPanel(Theme.LIGHT_BG_COLOR3);
		
		if (PAGE_TITLE !=""){
			setTitle();
		}
		
		s = new JPanel(new BorderLayout());
		s.setOpaque(false);
		setExtensionWrapper();
		dPanel.addGenericRow(s,25);
		
	}
	
	public void setTitle(){
		JLabel titleLabel = new JLabel(PAGE_TITLE);
		titleLabel.setFont(SW.get().getFonts().getTitleBoldFont());
		titleLabel.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(titleLabel,25);
	}
	
	public void setExtensionWrapper(){
		if(Game.getGame() == null){
			return;
		}
		
		wrapper = new JPanel(new BorderLayout());
		//wrapper.setBounds(LABEL_X, 60, 600, 32);
		Dimension d = new Dimension(600, 40);
		wrapper.setPreferredSize(d);
		wrapper.setMinimumSize(d);
		wrapper.setMaximumSize(d);
		
		wrapper.setBackground(Theme.LIGHT_BG_COLOR3);
		
		if(SW.get().getEngineExtensionManager().getExtensions().containsKey(REPOS_ID)){
			JPanel buttons = new JPanel();
			buttons.setOpaque(false);
			
			e = SW.get().getEngineExtensionManager().getExtensions().get(REPOS_ID);
			exVersion = e.getVersion();
			
			ExtensionDependencyManager deps = SW.get().getExtensionDependencyManager();
			ExtensionInstanceManager extInsts = Game.getGame().getExtensionManager();
			
			ImageIcon icon = e.getIcon();
			
			if(icon == null)
			{
				icon = Loader.getWarningIcon();
			}
			
			JLabel iconLabel = new JLabel(icon);
			wrapper.add(iconLabel,BorderLayout.WEST);
			
			JLabel name = new JLabel("<html>"+e.getName()+"<br>"+e.getDescription()+"</html>");
			name.setFont(SW.get().getFonts().getNormalFont());
			name.setForeground(Theme.TEXT_COLOR);
			wrapper.add(name,BorderLayout.CENTER);
			
			final CapsuleButton enable = new CapsuleButton();
			
			if(extInsts.isEnabled(REPOS_ID))
			{
				enable.useRedTheme();
			}
			
			else
			{
				enable.useGreenTheme();
			}
			
			enable.setText(extInsts.isEnabled(REPOS_ID) ? "Disable" : "Enable");
			enable.addActionListener
			(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent evt)
					{
						if(extInsts.isEnabled(REPOS_ID))
						{
							extInsts.disableExtension(REPOS_ID);
							deps.disable(e);
							enable.setText("Enable");
							enable.useGreenTheme();
						}
						else
						{
							extInsts.enableExtension(REPOS_ID);
							deps.enable(e);
							enable.setText("Disable");
							enable.useRedTheme();
						}
					}
				}
			);
			final JButton website = new CapsuleButton();
			website.setText("Help");
			website.addActionListener
			(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent evt)
					{
						App.showWebpage(BLOCK_HELP);
					}
				}
			);
			
			buttons.add(website);
			buttons.add(enable);
			wrapper.add(buttons,BorderLayout.EAST);
			//add(wrapper);
		}else{
			JLabel iconLabel = new JLabel(Loader.getWarningIcon());
			wrapper.add(iconLabel,BorderLayout.WEST);
			
			JLabel noEx = new JLabel("<html>The " + EXTEN_NAME + " is not installed.<br>Click download to download and install the " + EXTEN_NAME + ".</html>");
			noEx.setFont(SW.get().getFonts().getNormalFont());
			noEx.setForeground(Theme.TEXT_COLOR);
			wrapper.add(noEx,BorderLayout.CENTER);
			
			JButton downloadBtn = new JButton("Download");
			downloadBtn.addActionListener(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent evt)
						{
							new ExtensionRepositoryBrowser(false);
							updateWrapper();
						}
					}
			);
			wrapper.add(downloadBtn,BorderLayout.EAST);
		}
		s.add(wrapper,BorderLayout.WEST);
	}
	
	public void updateWrapper(){
		
		if(s !=null){
			if(Game.getGame() != null){
				s.remove(wrapper);
				setExtensionWrapper();
			}
		}
		
		dPanel.revalidate();
		dPanel.repaint();
		
	}
}
