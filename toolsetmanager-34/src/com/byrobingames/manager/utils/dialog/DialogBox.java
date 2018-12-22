package com.byrobingames.manager.utils.dialog;
	
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import stencyl.sw.SW;
import stencyl.sw.app.TaskManager;
import stencyl.sw.lnf.Theme;
import stencyl.sw.loc.LanguagePack;
import stencyl.sw.util.HTML;
import stencyl.sw.util.Loader;
import stencyl.sw.util.Util;
import stencyl.sw.util.comp.ButtonBarFactory;
import stencyl.sw.util.comp.GroupButton;
import stencyl.sw.util.dg.StencylDialog;

public final class DialogBox extends StencylDialog
	{    
	    
	    private static final TaskManager taskManager = SW.get().getApp().getTaskManager();
	    
	    private static final int MAX_TEXT_WIDTH = 400;
	    
	    public static boolean okIsPressed = false; 
	   
	    
		private final String msg;
		private final String smallMsg;
		private final boolean showCancelButton;
		
		
		public static void showGenericDialog(String msg, String smallMsg)
		{
			showGenericDialog("", msg, smallMsg);
		}

		public static void showGenericDialog(String title, String msg, String smallMsg)
		{
			
			
			new DialogBox(title, msg, smallMsg);
		}
			
		public static void showWarningDialog(String msg, String smallMsg)
		{
			taskManager.hideProgress();
			
			new DialogBox("Warning", msg, smallMsg);
		}
		
		public static void showErrorDialog(String msg, String smallMsg)
		{
			showErrorDialog("Error", msg, smallMsg, false);
		}

		public static void showErrorDialog(String msg, String smallMsg, boolean showCancelButton)
		{
			showErrorDialog("Error", msg, smallMsg, showCancelButton);
		}
		
		public static void showErrorDialog(String title, String msg, String smallMsg, boolean showCancelButton)
		{
			taskManager.hideProgress();
			
			new DialogBox(title, msg, smallMsg, showCancelButton);
		}

		
		private DialogBox(String title, String msg, String smallMsg)
		{
			this(title, msg, smallMsg, false);
		}
		
		private DialogBox(String title, String msg, String smallMsg, boolean showCancelButton)
		{
			super(SW.get(), title, false, false);
			
			this.msg = msg;
			this.smallMsg = smallMsg;
			this.showCancelButton = showCancelButton;
			
			if(!Util.isMacOSX())
			{
				setBackground(Theme.EDITOR_BG_COLOR);
			}
			
			add(buttonPanel = createButtonPanel(), BorderLayout.SOUTH);
			add(createContentPanel(), BorderLayout.CENTER);
			
			hideCloseButton();
			pack();
			setVisible(true);
		}
		
		
		@Override
		public JComponent createContentPanel()
		{	
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBackground(Theme.LIGHT_BG_COLOR);

			JLabel label = new JLabel();
			
			if(!msg.isEmpty())
			{
				label.setText(new HTML(new HTML(msg).biggerFont().bold() + HTML.BREAK + HTML.BREAK + smallMsg).maxWidth(MAX_TEXT_WIDTH).wrap());
			}
			
			else
			{
				label.setText(new HTML(smallMsg).maxWidth(MAX_TEXT_WIDTH).wrap());
			}
			
			label.setForeground(Theme.TEXT_COLOR);
			label.setIcon(Loader.loadIcon("res/global/icon48.png"));
			label.setIconTextGap(25);
			label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			
			panel.add(label, BorderLayout.CENTER);
			
			return panel;
		}
		
		@Override
		public JPanel createButtonPanel() 
		{
			JButton okButton = new GroupButton(0);

			okButton.setAction
			(
			    new AbstractAction("Ok") 
				{
				    @Override
					public void actionPerformed(ActionEvent e) 
				    {
				    	okIsPressed = true;
				        cancel();
				    }
				}
			);
			
			if (showCancelButton)
			{
				GroupButton cacelButton = new GroupButton(0);
				
				cacelButton.setAction
				(
				    new AbstractAction("Cancel") 
					{
					    @Override
						public void actionPerformed(ActionEvent e) 
					    {
					    	cancel();
					    }
					}
				);
				
				return ButtonBarFactory.createButtonBar(this, new JButton[] { cacelButton, okButton }, 1);
			}
			    
			return ButtonBarFactory.createButtonBar(this, new JButton[] { okButton }, 1);
	    }
		
		@Override
		public void cancel()
		{
	    	setVisible(false);
		}

}
