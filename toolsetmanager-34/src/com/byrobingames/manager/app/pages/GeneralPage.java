package com.byrobingames.manager.app.pages;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.byrobingames.manager.res.Resources;

import stencyl.sw.SW;
import stencyl.sw.app.App;
import stencyl.sw.lnf.Theme;
import stencyl.sw.util.comp.text.FieldVerifier;

public class GeneralPage extends SourcePage implements FieldVerifier {

	
	private final Logger log = Logger.getLogger(GeneralPage.class);
	private static GeneralPage _instance;
	
	private static String PAGE_TITLE = "GeneralPage";
	private static String EXTEN_NAME = "byRobin Manager Engine Extension";
	private static String BLOCK_HELP = "https://github.com/byrobingames/byRobinextensionmanager";
	private static String REPOS_ID = "byRobinextensionmanager";
	
	private GeneralPage()
	{
		super(PAGE_TITLE,BLOCK_HELP, EXTEN_NAME, REPOS_ID);
		
		createPaypalButton();
		
		JLabel titleSettings = new JLabel("<html>Thanks for using my extensions<br>"
				+ "This Toolset Extension is made to manage my Engine Extension that you can find in the left menu.<br><br>"
				+ "The Toolset will also check available updates.<br>"
				+ "If there is an update available you can update it inside the extension page.<br>"
				+ "The Extensions will download and install automatically when you click the download/update button<br>"
				+ "<br><br>"
				+ "I have make this tool opensource on https://byrobingames.github.io.<br>"
				+ "Feel free to fork and make a pull request.<br><br>"
				+ "If you have any suggestion of if you have any issues, you can create an issue on Github:<br>"
				+ "<a href=\"https://github.com/byrobingames/byRobinextensionmanager/issues\">https://github.com/byrobingames/byRobinextensionmanager/issues</a></html>");
		titleSettings.setFont(SW.get().getFonts().getNormalFont());
		titleSettings.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(titleSettings);
		
		followMe();
		
		dPanel.finishBlock();
		add(dPanel, BorderLayout.NORTH);
	
	}	
		
public void followMe(){
		JPanel s = new JPanel(new BorderLayout());
		s.setOpaque(false);
		
		JPanel follow = new JPanel(new BorderLayout());
		
		
		ImageIcon iconFacebook = Resources.loadIcon("images/social_f.png");
		JLabel facebook = new JLabel(iconFacebook);
		facebook.setHorizontalAlignment(SwingConstants.LEFT);
		facebook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	App.showWebpage("https://www.facebook.com/byRobinDevelopment");
            }
        });
		follow.add(facebook,BorderLayout.WEST);
		
		ImageIcon iconTwitter = Resources.loadIcon("images/social_t.png");
		JLabel twitter = new JLabel(iconTwitter);
		facebook.setHorizontalAlignment(SwingConstants.LEFT);
		twitter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	App.showWebpage("https://twitter.com/byrobingames");
            }
		});
		
		follow.add(twitter,BorderLayout.CENTER);
		s.add(follow,BorderLayout.WEST);
		dPanel.addGenericRow(s);
	}
	public void createPaypalButton(){
		
		ImageIcon icon = Resources.loadIcon("images/paypal.png");
		JLabel paypal = new JLabel(icon);
		paypal.setHorizontalAlignment(SwingConstants.LEFT);
		//paypal.setBounds(25, 50, icon.getIconWidth(),icon.getIconHeight());
		//paypalButton.setHorizontalAlignment(SwingConstants.CENTER);
		//paypalButton.setVerticalAlignment(SwingConstants.CENTER);
		/*paypalButton.setAction
		(
				new AbstractAction()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						App.showWebpage("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=HKLGFCAGKBMFL");
					}
				}
			);*/
		add(paypal);
	}
	
	
	public void save()
	{
		updateWrapper();
		revalidate();
		repaint();
	}
	

	public static GeneralPage get()
	{
		if (_instance == null)
			_instance = new GeneralPage();

		return _instance;
	}
	
	public static void disposeInstance()
	{
		_instance = null;
	}


	@Override
	public boolean verifyText(JTextField arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
