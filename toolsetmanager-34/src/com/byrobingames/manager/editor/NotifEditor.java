package com.byrobingames.manager.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.log4j.Logger;

import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.grid.AbstractRow;
import com.jidesoft.grid.CellEditorManager;
import com.jidesoft.grid.CellRendererManager;
import com.jidesoft.grid.TreeTableModel;
import com.jidesoft.swing.PaintPanel;

import misc.comp.ITunesTableHeaderRenderer;
import stencyl.sw.SW;
import stencyl.sw.actions.Actions;
import stencyl.sw.actions.SAction;
import stencyl.sw.lnf.Theme;
import stencyl.sw.loc.LanguagePack;
import stencyl.sw.util.UI;
import stencyl.sw.util.comp.GroupButton;

public class NotifEditor extends JPanel implements ActionListener, MouseListener
{
	/*-------------------------------------*\
	 * Globals
	\*-------------------------------------*/ 
    
	private static final Logger log = Logger.getLogger(NotifEditor.class);
	
    private static final LanguagePack lang = LanguagePack.get();
    
    /*-------------------------------------*\
	 * Model
	\*-------------------------------------*/ 
    
	private final SAction newAction;
    private final SAction deleteAction;
    private final SAction upAction;
    private final SAction downAction;
    private final SAction importAction;
    
    private JTable table;
    private ArrayList<MapRow> rowList;
    
    private ArrayList<NotifElement> model;
    private MapModel mapModel;
    
    public NotifEditor(ArrayList<NotifElement> model)
    {
    	setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(350, 170));
		
		this.model = model;
		
		if (this.model == null)
		{
			this.model = new ArrayList<NotifElement>();
		}
		
		newAction = Actions.getAction("map.add").getCopy();
		deleteAction = Actions.getAction("map.remove").getCopy();
		upAction = Actions.getAction("list.up").getCopy();
		downAction = Actions.getAction("list.down").getCopy();
		importAction = Actions.getAction("list.import").getCopy();
		
		newAction.setListener(this);
		deleteAction.setListener(this);
		upAction.setListener(this);
		downAction.setListener(this);
		importAction.setListener(this);
		
		initTable();
		
		JScrollPane scrollPane = UI.createScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getViewport().setBackground(Theme.EDITOR_BG_COLOR);	
		scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Theme.BORDER_COLOR));
		
		//PaintPanel tableHeader = UI.createButtonPanel(BorderFactory.createMatteBorder(1, 0, 0, 1, Theme.BORDER_COLOR));
		//tableHeader.setPreferredSize(new Dimension(1, 20));
		//tableHeader.add(Box.createHorizontalStrut(5));
		
		/*JLabel l = new JLabel("NotifID");
		tableHeader.add(l);
		
		tableHeader.add(Box.createHorizontalStrut(60));
		
		l = new JLabel("Title");
		tableHeader.add(l);
		
		tableHeader.add(Box.createHorizontalStrut(60));
		
		l = new JLabel("Message");
		tableHeader.add(l);
		
		tableHeader.add(Box.createHorizontalStrut(60));
		
		l = new JLabel("Timeinterval");
		tableHeader.add(l);
		
		tableHeader.add(Box.createHorizontalStrut(60));
		
		l = new JLabel("Repeat every");
		tableHeader.add(l);*/ 
		
		JPanel wrapper = new JPanel(new BorderLayout());
		wrapper.setOpaque(false);
		wrapper.add(scrollPane, BorderLayout.CENTER);
		//wrapper.add(tableHeader, BorderLayout.NORTH);
		
		JPanel bar = createBar();
		add(bar, BorderLayout.WEST);
		add(wrapper, BorderLayout.CENTER);
		
		refreshActions();
		
    }
    
    private void initTable()
    {
    	ObjectConverterManager.initDefaultConverter();
        CellRendererManager.initDefaultRenderer();
        CellEditorManager.initDefaultEditor();
        
    	//table = new SortableTable();
        table = new JTable(){
        	
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
                return false;               
        	}
        };
    	table.setBackground(Theme.LIGHT_BG_COLOR);
		table.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		table.setForeground(Theme.TEXT_COLOR);
		table.setFont(SW.get().getFonts().getNormalFont());
		table.getTableHeader().setDefaultRenderer(new ITunesTableHeaderRenderer(table));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFillsViewportHeight(true);
		
		/*table.setEditingColumn(0);
		table.setEditingColumn(1);
		table.setEditingColumn(2);
		table.setEditingColumn(3);
		table.setEditingColumn(4);*/
		
		table.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Theme.BORDER_COLOR));
		table.getTableHeader().setPreferredSize(new Dimension(1, 20));
		//table.getTableHeader().setMinimumSize(new Dimension(0, 0));
		//table.getTableHeader().setMaximumSize(new Dimension(0, 0));
		table.getTableHeader().setVisible(true);
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(Theme.BORDER_COLOR);
		table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Theme.BORDER_COLOR));

		mapModel = new MapModel(null);
		table.setModel(mapModel);
		
		table.getColumnModel().getColumn(0).setHeaderValue("NotifID(Integer 0-63)");
		table.getColumnModel().getColumn(1).setHeaderValue("Title");
		table.getColumnModel().getColumn(2).setHeaderValue("Message");
		table.getColumnModel().getColumn(3).setHeaderValue("Trigger after(in seconds)");
		table.getColumnModel().getColumn(4).setHeaderValue("Repeat Every");
		table.getTableHeader().repaint();
		


		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(JLabel.LEFT);
		table.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);


		
		/*table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(0).setMaxWidth(100);	
		table.getColumnModel().getColumn(0).setMinWidth(100);
		
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(100);	
		table.getColumnModel().getColumn(1).setMinWidth(100);
		
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setMaxWidth(100);	
		table.getColumnModel().getColumn(2).setMinWidth(100);
		
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setMaxWidth(100);	
		table.getColumnModel().getColumn(3).setMinWidth(100);
		
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setMaxWidth(100);	
		table.getColumnModel().getColumn(4).setMinWidth(100);*/
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(false);
		table.setIntercellSpacing(new Dimension(0, 0));
		
		table.addMouseListener(this);		
		
		resetTableModel();		
    }
    
    private void resetTableModel() 
	{
    	rowList = new ArrayList<MapRow>();
    	
    	for (NotifElement e: model)
    	{
    		//log.info("byrobinextension : notifeditor" + e.key);
    		//log.info("byrobinextension : notifeditor" + e.value);
    		//log.info("byrobinextension : notifeditor" + e.value2);
    		rowList.add(new MapRow(e));
    	}
    	
    	mapModel.setOriginalRows(rowList);
	}
    
    private JPanel createBar()
	{
		PaintPanel buttonPanel = UI.createVerticalButtonPanel1();
        buttonPanel.add(Box.createHorizontalGlue());
        
        Dimension d = new Dimension(23, 23);
        
        for(SAction action : new SAction[] {newAction, importAction, deleteAction, upAction, downAction})
        {
        	final GroupButton act = new GroupButton(0);
    		act.setAction(action);
    		act.setText("");
    		act.setTargetHeight(23);
    		act.setMargin(new Insets(0, 0, 0, 0));
    		act.setMinimumSize(d);
    		act.setPreferredSize(d);
    		act.setMaximumSize(d);

        	buttonPanel.add(act);
    		buttonPanel.add(Box.createVerticalStrut(5));
        }
        
        buttonPanel.add(Box.createHorizontalGlue());
        
		return buttonPanel;
	}
	
    public ArrayList<NotifElement> getModel()
    {
    	return model;
    }
    
    private void refreshActions()
	{		
		deleteAction.setEnabled(table.getSelectedRow() != -1);
		upAction.setEnabled(table.getSelectedRow() > 0);
		//downAction.setEnabled(!model.isEmpty() && table.getSelectedRow() < model.size() - 1);
		downAction.setEnabled(table.getSelectedRow() >= 0 && table.getSelectedRow() < model.size() && table.getSelectedRow() < model.size() - 1);
	}
    
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		refreshActions();
		
		int selectedRow = table.getSelectedRow();
		Boolean isRowselected = table.isRowSelected(selectedRow);
        
        if(e.getClickCount() == 2 && selectedRow != -1 && isRowselected)
        {
        	for (NotifElement ne : model){
        		if(ne.getNotifid() == table.getValueAt(selectedRow, 0)){
        			
        			NotifDialog nd = new NotifDialog(SW.get(), ne);
        		
        			if(!nd.getNotifid().toString().isEmpty() && !nd.getTitle().isEmpty() && !nd.getMessage().isEmpty() && !nd.getTimeinterval().toString().isEmpty() && !nd.getDoevery().isEmpty()){
					
        				NotifElement element = new NotifElement();
        				element.setNotifid(nd.getNotifid());
        				element.setTitle(nd.getTitle());
        				element.setMessage(nd.getMessage());
        				element.setTimeinterval(nd.getTimeinterval());
        				element.setDoevery(nd.getDoevery());
						
						
						mapModel.setValueAt(nd.getNotifid(), selectedRow, 0);
						mapModel.setValueAt(nd.getTitle(), selectedRow, 1);
						mapModel.setValueAt(nd.getMessage(), selectedRow, 2);
						mapModel.setValueAt(nd.getTimeinterval(), selectedRow, 3);
						mapModel.setValueAt(nd.getDoevery(), selectedRow, 4);
						
						model.set(model.indexOf(ne), element);
						
						 break;
					}
        		}
        	}
        }
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}	
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int selectedRow = table.getSelectedRow();
		
		if(e.getActionCommand().equals(lang.get("map.add")))
		{
			NotifDialog nd = new NotifDialog(SW.get(), null);
			if(!nd.cancel){
				if(!nd.getNotifid().toString().isEmpty() && !nd.getTitle().isEmpty() && !nd.getMessage().isEmpty() && !nd.getTimeinterval().toString().isEmpty() && !nd.getDoevery().isEmpty()){
					//NotifElement element = new NotifElement("notifid","title","message",0,"do every");
					NotifElement element = new NotifElement();
					element.setNotifid(nd.getNotifid());
					element.setTitle(nd.getTitle());
					element.setMessage(nd.getMessage());
					element.setTimeinterval(nd.getTimeinterval());
					element.setDoevery(nd.getDoevery());
					
					mapModel.addRow(new MapRow(element));
					model.add(element);
				}
			}							
		}
		
		else if(e.getActionCommand().equals(lang.get("map.remove")))
		{
			//TODO: Undo/Redo Support?			
			if (selectedRow != -1)
			{
				mapModel.removeRow(selectedRow);
				model.remove(selectedRow);
			}
		}
		
		else if(e.getActionCommand().equals(lang.get("list.up")))
		{
			NotifElement above = model.get(selectedRow-1);
			NotifElement curr = model.get(selectedRow);
			
			model.set(selectedRow, above);
			model.set(selectedRow - 1, curr);
			
			mapModel.moveUpRow(mapModel.getRowAt(selectedRow));
			
			if (selectedRow < rowList.size())
			{
				table.getSelectionModel().setSelectionInterval(0, selectedRow-1);
			}			
		}
		
		else if(e.getActionCommand().equals(lang.get("list.down")))
		{
			NotifElement below = model.get(selectedRow+1);
			NotifElement curr = model.get(selectedRow);
			
			model.set(selectedRow, below);
			model.set(selectedRow + 1, curr);
			
			mapModel.moveDownRow(mapModel.getRowAt(selectedRow));
			
			if (selectedRow < rowList.size())
			{
				table.getSelectionModel().setSelectionInterval(0, selectedRow+1);
			}		
		}
		else if(e.getActionCommand().equals(lang.get("list.import")))
		{
			File file = UI.showOpenFileChooser(lang.get("list.import.title"));
			
			if(file != null) 
			{	
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file))))
			    {
					String nextLine = "";

					while((nextLine = reader.readLine()) != null) 
					{
						String[] pair = nextLine.split("=", 5);
						if (pair.length == 5)
						{
							//NotifElement element = new NotifElement(pair[0], pair[1], pair[2], Integer.parseInt(pair[3]), pair[4]);
							NotifElement element = new NotifElement();
							element.setNotifid(Integer.parseInt(pair[0]));
							element.setTitle(pair[1]);
							element.setMessage(pair[2]);
							element.setTimeinterval(Integer.parseInt(pair[3]));
							element.setDoevery(pair[4]);
							
							mapModel.addRow(new MapRow(element));
							model.add(element);
						}
						else
						{
							log.warn("Could not parse line \"" + nextLine + "\". Expected format: key=value.");
						}
					}
			    } 
			    
			    catch(IOException ex) 
			    {
			    	log.error(ex.getMessage(), ex);
			    }
			}
		}
		
		refreshActions();
	}	
	
	private static final class MapRow extends AbstractRow implements Comparable<MapRow> 
	{
		private final NotifElement elem;
		
		public MapRow(NotifElement e)
		{
			this.elem = e;
		}

		@Override
		public Object getValueAt(int column) 
		{
			if (column == 0)
			{
				return elem.getNotifid();
			}
			else if(column == 1){
				return elem.getTitle();
			}
			else if(column == 2){
				return elem.getMessage();
			}
			else if(column == 3){
				return elem.getTimeinterval();
			}
			else if(column == 4){
				return elem.getDoevery();
			}
			return null;

			
		}

		@Override
		public void setValueAt(Object value, int x)
	    {
			if (x == 0)
			{
				elem.setNotifid((Integer) value);
			}
			
			else if (x == 1)
			{
				elem.setTitle(value.toString());
			}
			
			else if (x == 2)
			{
				elem.setMessage(value.toString());
			}
			else if (x == 3)
			{
				elem.setTimeinterval((Integer) value);
			}
			else if (x == 4)
			{
				elem.setDoevery(value.toString());
			}
			
	    }
		
		@Override
		public int compareTo(MapRow row) 
		{
			return 0;
		}
	}
	
	private static final class MapModel extends TreeTableModel<MapRow>
	{

		public MapModel(ArrayList<MapRow> list)
		{
			super(list);
		}
		
		@Override
		public int getColumnCount() 
		{
			return 5;
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex) 
	    {
	        switch (columnIndex) 
	        {
	            case 0:
	                return String.class;

	            case 1:
	                return String.class;
	                
	            case 2:
	                return String.class;
	                
	            case 3:
	                return Integer.class;
	                
	            case 4:
	                return String.class;
	            
	        }

	        return super.getColumnClass(columnIndex);
	    }
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) 
	    {
	        return true;
	    }	
	}
	
}

