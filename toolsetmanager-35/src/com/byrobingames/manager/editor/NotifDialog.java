package com.byrobingames.manager.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import stencyl.sw.lnf.Theme;
import stencyl.sw.loc.LanguagePack;
import stencyl.sw.util.Util;
import stencyl.sw.util.comp.ButtonBarFactory;
import stencyl.sw.util.comp.GroupButton;
import stencyl.sw.util.comp.text.AutoVerifyField;
import stencyl.sw.util.comp.text.FieldVerifier;
import stencyl.sw.util.dg.DialogPanel;
import stencyl.sw.util.dg.StencylDialog;

public class NotifDialog extends StencylDialog implements FieldVerifier{
	
	private static final Logger log = Logger.getLogger(NotifDialog.class);
	private static final LanguagePack lang = LanguagePack.get();
    
    private static final int WIDTH = 430;
    private static final int HEIGHT = 250;
    
    private AutoVerifyField notifIdField;
    private AutoVerifyField titleField;
    private AutoVerifyField messageField;
    private AutoVerifyField timeintervalField;
    private JComboBox<String> doeveryField;
	
	private JButton okButton;
	
	private String title;
	private String message;
	private Integer timeinterval;
	private String doevery;
	private Integer notifid;
	
	public Boolean cancel = false;
	private Boolean notifVerify = false, titleVerify = false, messageVerify = false, timeVerify = false;
	
	
	public NotifDialog(JFrame owner, NotifElement model)
	{
		super(owner, model == null ? "Add schedule to map" : "Edit schedule", WIDTH, HEIGHT, false, false);
		
		if(model != null)
		{
			this.notifid = model.getNotifid();
			this.title = model.getTitle();
			this.message = model.getMessage();
			this.timeinterval = model.getTimeinterval();
			this.doevery = model.getDoevery();
		}
		
		add(createContentPanel(), BorderLayout.CENTER);

		if(!Util.isMacOSX())
		{
			setBackground(Theme.APP_COLOR);
		}

		setVisible(true);
	}
	
	public Integer getNotifid() {
		return notifid;
	}
	public String getTitle() {
		return title;
	}
	public String getMessage() {
		return message;
	}
	public Integer getTimeinterval() {
		return timeinterval;
	}
	public String getDoevery() {
		return doevery;
	}

	public JComponent createContentPanel()
	{
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());
		panel.setBackground(Theme.BG_COLOR);

		panel.add(createEditPanel(), BorderLayout.CENTER);

		return panel;
	}

	public JPanel createEditPanel()
	{
		DialogPanel panel = new DialogPanel(Theme.LIGHT_BG_COLOR3);

		panel.finishBlock();
		
		
		notifIdField = new AutoVerifyField(this, "");
		
		if(notifid != null)
		{
			notifIdField.setText(notifid.toString());
		}
		
		notifIdField.setBackground(Theme.EDITOR_BG_COLOR);
		panel.addTextField("Notification ID", notifIdField);
		
		titleField = new AutoVerifyField(this, "");
		
		if(title != null)
		{
			titleField.setText(title);
		}
		
		titleField.setBackground(Theme.EDITOR_BG_COLOR);
		panel.addTextField("Title", titleField);
		
		messageField = new AutoVerifyField(this, "");
		
		if(message != null)
		{
			messageField.setText(message);
		}
		
		messageField.setBackground(Theme.EDITOR_BG_COLOR);
		panel.addTextField("Message", messageField);
		

		timeintervalField = new AutoVerifyField(this, "");
		
		if(timeinterval != null)
		{
			timeintervalField.setText(timeinterval.toString());
		}
		
		timeintervalField.setBackground(Theme.EDITOR_BG_COLOR);
		panel.addTextField("Time Interval", timeintervalField);
		

		ArrayList<String> dolist = new ArrayList<String>();
		dolist.add("no repeat");
		dolist.add("Minute");
		dolist.add("Hour");
		dolist.add("Day");
		dolist.add("Week");
		dolist.add("Month");
		dolist.add("Year");
		
		
		doeveryField = new JComboBox<String>(dolist.toArray(new String[dolist.size()]));
		
		for(String s : dolist)
		{
			if(s.equals(doevery))
			{
				doeveryField.setSelectedItem(s);
				break;
			}
		}
		
		doeveryField.addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					doevery = ((String) doeveryField.getSelectedItem());
					repaint();
				}
			}
		);

		panel.addDropdown("Do Every", doeveryField);
		
		panel.finishBlock();

		return panel;
	}
	
	@Override
	public JPanel createButtonPanel() 
	{
		okButton = new GroupButton(0);
		JButton cancelButton = new GroupButton(0);

		okButton.setAction
		(
			new AbstractAction(lang.get("globals.ok"))
			{
				public void actionPerformed(ActionEvent e) 
				{
					commitChanges();
				}
			}
		);
		okButton.setEnabled(false);

		cancelButton.setAction
		(
			new AbstractAction(lang.get("globals.cancel")) 
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					cancel();
				}
			}
		);

		return ButtonBarFactory.createButtonBar
		(
			this,
			new JButton[] {okButton, cancelButton},
			0,
			false
		);
	}
	
	
	public void cancel() {
		
		cancel = true;
		setVisible(false);
		
	}

	public void commitChanges() {
		notifid = Integer.valueOf(notifIdField.getText());
		title = titleField.getText();
		message = messageField.getText();
		timeinterval = Integer.valueOf(timeintervalField.getText());
		doevery = ((String) doeveryField.getSelectedItem());
		
		setVisible(false);
		
	}

	@Override
	public boolean verifyText(JTextField arg0, String arg1) {
		if (arg1 != null)
		{
			arg1 = arg1.trim();
		}
		
		if(notifIdField != null && arg0.equals(notifIdField.getTextField())){
			try
			{					
				this.notifid = Integer.valueOf(arg1);
				//okButton.setEnabled(true);
				notifVerify = true;
			}
			
			catch(NumberFormatException e)
			{
				okButton.setEnabled(false);
				notifVerify = false;
				return false;
			}
			/*if(arg1.isEmpty()){
				okButton.setEnabled(false);
				notifVerify = false;
				return false;
			}else{
				notifVerify = true;
			}*/
		}
		if(titleField != null && arg0.equals(titleField.getTextField())){
			if(arg1.isEmpty()){
				okButton.setEnabled(false);
				titleVerify = false;
				return false;
			}else{
				titleVerify = true;
			}
		}
		if(messageField != null && arg0.equals(messageField.getTextField())){
			if(arg1.isEmpty()){
				okButton.setEnabled(false);
				messageVerify = false;
				return false;
			}else{
				messageVerify = true;
			}
		}
		if(timeintervalField != null && arg0.equals(timeintervalField.getTextField())){
			try
			{					
				this.timeinterval = Integer.valueOf(arg1);
				//okButton.setEnabled(true);
				timeVerify = true;
			}
			
			catch(NumberFormatException e)
			{
				okButton.setEnabled(false);
				timeVerify = false;
				return false;
			}
		}
		
		boolean result = (notifVerify && titleVerify && messageVerify && timeVerify);
		
		log.info(result);
		okButton.setEnabled(result);
		return true;
	}

}
