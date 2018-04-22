package driver;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.sound.midi.Patch;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import abstracts_classes.Animal;

public class InfoJTable extends JPanel{
	private String[] columnNames = {"Animal", "Color", "Size", "Hor.speed", "Ver.speed", "Eat counter"};
	private Object[][] data;
	private ArrayList<Animal> listAnimals;
	private JTable table;
	private int counter = 0;
    private MyRenderer myRenderer;
    private DefaultTableModel defModel;
	private JTable myTable;
    private static Color colors[] = {  Color.BLUE ,Color.RED, Color.GREEN, Color.BLACK, Color.WHITE , Color.CYAN , Color.YELLOW , Color.DARK_GRAY, Color.GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK};
    private static String strcolors[] = {"BLUE" , "RED" , "GREEN" ,"BLACK" ,"WHITE" ,"CYAN" ,"YELLOW" ,"DARK_GRAY" ,"GRAY" ,"MAGENTA" ,"ORANGE" ,"PINK" };
	private int row = -1;
    private int col = 0;
    private static String strSelectRow = "Total : ";
    private static int selectRow = 0;
    private final static int ONE_SECOND = 1000;
    
	public InfoJTable(ArrayList<Animal> listAnimals , AquaPanel paneAqua,JButton btn_Info){
		this.listAnimals = listAnimals;
		
		
		initData(); // initial the data 
		
		myTable.getTableHeader().setReorderingAllowed(false);
        myTable.setPreferredScrollableViewportSize(new Dimension(632, 578)); // create the size of the JTable
        myTable.setFillsViewportHeight(true);                                
	    JScrollPane scrollPane = new JScrollPane(myTable);                   // add scroll panel to the panel on the table
        add(scrollPane);                                                     // add the scroll to the JTable
        
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItemColor = new JMenuItem("Change Color");
        JMenuItem menuItemRemove = new JMenuItem("Remove Current Row");
        JMenuItem menuItemRemoveAll = new JMenuItem("Remove All Rows");
        
        
        Action ChangeColor = new AbstractAction("Change Color") {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	row = selectRow;
		    	//row = myTable.getSelectedRow();
		    	if(row != -1 && col != -1 && myTable.getRowCount() > 1 && row != myTable.getRowCount()){
		    		
		    		String name = (String) myTable.getValueAt(selectRow, 0);
		    		
		    		
			    	int j ; //= name.length();
			    	int index = name.length() - 1;
			    	
			    	for(j = name.length()-1; j >= 0 ; j--){
			    		System.out.println("for j = " + j);
			    		index = j;
			    		if(!(name.indexOf(j) >= '0' && name.indexOf(j) <= '9')){
			    			break;
			    		}
			    	}
			    	
			    	
			    	System.out.println("index = " + index);
			    	System.out.println("name :" + name.substring(index));
			    	int id = selectRow; //Integer.parseInt(name.substring(index));
			    	
			    	Color color = JColorChooser.showDialog(myTable, "Color Chooser", new Color(255,255,255));
			    	System.out.println("change color for id animal: " + id);
			    	
			    	if(id == 0){
				    	paneAqua.ChangeColorById(id + 20, color); // const num for ids
				    	System.out.println("id = " + id + 20);
			    	}
				    else{
			    		paneAqua.ChangeColorById(id + 19, color); // const num for ids
			    		System.out.println("id = " + id + 20);
			    	}
			    	
			    	myTable.clearSelection();
			    	btn_Info.doClick();
			    	btn_Info.doClick();
		    	}
		    }
		};
		menuItemColor.setAction(ChangeColor);
		
        Action RemoveCurrentRow = new AbstractAction("Remove Current Row") {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	row = selectRow;
		    	//row = myTable.getSelectedRow();
		    	//System.out.println("rc: " + row + col);
		    	if(row != -1 && col != -1 && myTable.getRowCount() > 1){
		    		String name = (String) myTable.getModel().getValueAt(row, col);
			    	
			    	int j ; //= name.length()-1;
			    	int index = name.length()-1;
			    	for(j = name.length()-1; j >= 0 ; j--){
			    		index = j;
			    		if(!(name.indexOf(j) >= '0' && name.indexOf(j) <= '9')){
			    			break;
			    		}
			    	}
			    	
			    	int id = Integer.parseInt(name.substring(index));
			    	
			    	if(id == 0)
			    		paneAqua.removeAnimalById(id + 20); // const num for ids
			    	else
			    		paneAqua.removeAnimalById(id + 19); // const num for ids
			    	
			    	myTable.clearSelection();
			    	btn_Info.doClick();
			    	btn_Info.doClick();
		    	}
		    }
		};
		menuItemRemove.setAction(RemoveCurrentRow);
        
		Action RemoveAllRows = new AbstractAction("Remove All Rows") {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	if(myTable.getRowCount() > 1){
		    		paneAqua.setDestroyThread();
		    		myTable.clearSelection();
		    		btn_Info.doClick();
		    		btn_Info.doClick();
		    	}
		    }
		};
		menuItemRemoveAll.setAction(RemoveAllRows);
		
        myTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            	row = myTable.rowAtPoint(e.getPoint());
                if(row > -1){
                	myTable.setRowSelectionInterval(row, row);
                	
                	row = myTable.getSelectedRow();
                	              	
                	selectRow = row;
                	
                	btn_Info.doClick();
			    	btn_Info.doClick();
			    	
			    	if(selectRow >= 0){
			    		strSelectRow = "" + (String) myTable.getValueAt(selectRow, 0);;
			    		myTable.setRowSelectionInterval(selectRow, selectRow);
			    	}
			    	else{
			    		strSelectRow = "Total : ";
			    	}
			    	
			    	System.out.println("Selected Row: " + selectRow + ", name = " + (String) myTable.getValueAt(selectRow, 0));
                   
                }
                else {
                	myTable.clearSelection();
                }
                
                	

                
            }
        });
        
        popupMenu.add(menuItemColor);
        popupMenu.add(menuItemRemove);
        popupMenu.add(menuItemRemoveAll);
        myTable.setComponentPopupMenu(popupMenu);
        myTable.setDefaultEditor(Object.class, null);
       
        myTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int row = myTable.getSelectedRow();
            }
        });
	}
	
	public void initData(){
		// the data of the rows in the JTable
		data  = new Object[listAnimals.size() + 1][];
		for (int i = 0; i < listAnimals.size() + 1; i++) 
		{
			data[i]    = new Object[columnNames.length];
			if(i == listAnimals.size()){
				data[listAnimals.size()][0] = new Object();
				data[listAnimals.size()][0] =  strSelectRow; //"Total : ";
				
				data[i][1] = new Object();
				data[i][1] = "";
				
				data[i][2] = new Object();
				data[i][2] =  "";
				
				data[i][3] = new Object();
				data[i][3] =  "";
				
				data[i][4] = new Object();
				data[i][4] =  "";
				
				data[i][5] = new Object();
				data[i][5] =  String.valueOf(counter);
			}
			else{
				data[i][0] = new Object();
				data[i][0] =  "" + listAnimals.get(i).getAnimalName();
				
				String str_color = "";
				for(int c = 0; c < colors.length; c++){
					if(new Color(listAnimals.get(i).getColorRGB().getRed(), 
							listAnimals.get(i).getColorRGB().getGreen(), 
							listAnimals.get(i).getColorRGB().getBlue()).equals(colors[c])){
						str_color = strcolors[c];
						break;
					}
				}
				if(str_color.equals("")){
					if(listAnimals.get(i).getColorRGB().getGreen() < 120 && listAnimals.get(i).getColorRGB().getRed() < 120 && listAnimals.get(i).getColorRGB().getBlue()< 120){
						str_color = "kind: Darkly";
					}
					else if(listAnimals.get(i).getColorRGB().getGreen() > 180 && listAnimals.get(i).getColorRGB().getRed() > 180 && listAnimals.get(i).getColorRGB().getBlue() > 180){
						str_color = "kind: Lighty";
					}
					else
						str_color = "mouse above me";
				}
				
				data[i][1] = new Object();
				data[i][1] = str_color;
				
				
				data[i][2] = new Object();
				data[i][2] =  String.valueOf(listAnimals.get(i).getSize());
				
				data[i][3] = new Object();
				data[i][3] =  String.valueOf(listAnimals.get(i).getHorSpeed());
				
				data[i][4] = new Object();
				data[i][4] =  String.valueOf(listAnimals.get(i).getVerSpeed());
				
				data[i][5] = new Object();
				data[i][5] =  String.valueOf(listAnimals.get(i).getEatCount());
				counter += listAnimals.get(i).getEatCount();
			}
		}
		myRenderer = new MyRenderer();   // See below
        defModel = new DefaultTableModel(data, columnNames);
        myTable = new JTable(defModel);
        myTable.setDefaultRenderer(Object.class, myRenderer);
       
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 2; i < 6; i++){
        	myTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
	}
	
	public void setAnimals(ArrayList<Animal> listAnimals){
		this.listAnimals = listAnimals;
		initData();
	}
	
	public class MyRenderer extends DefaultTableCellRenderer  
	{ 
		public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int column) 
		{ 
		    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
		   
		    table.setRowHeight(row, 30);
		    
	    	if (!table.isRowSelected(row))
	    	{
	    		if(row == listAnimals.size()) {
	    			c.setBackground(new Color(190,190,190));
	    		}
	    		else if(row < listAnimals.size() && column == 1) {
	    			Color clr = listAnimals.get(row).getColorRGB();
	    			if(clr != null){
	    				 c.setBackground(clr); 
	    			}
	    			((JComponent) c).setToolTipText("RGB("+ String.valueOf(listAnimals.get(row).getColorRGB().getRed())  + ","+
	    													String.valueOf(listAnimals.get(row).getColorRGB().getGreen())+ ","+
	    													String.valueOf(listAnimals.get(row).getColorRGB().getBlue()) +")");
	    		}
	    		else if(column == 1){
					c.setBackground(new Color(255,255,255));
					((JComponent) c).setToolTipText("no color");
				}
	    		else{
	    			c.setBackground(new Color(255,255,255));
	    		}
	    	}
		    return c; 
		} 
	} 
}
