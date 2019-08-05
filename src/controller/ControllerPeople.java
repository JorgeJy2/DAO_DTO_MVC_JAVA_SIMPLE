package controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.concurrent.Executor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
 
import gui.content.people.PeopleContainerMainGui; 
import gui.dialogs.Messages; 
import model.dto.DtoPeople; 
import model.list.ListPeople; 
import model.list.interador.Interator;
import report.FormatReport;
import report.ReportPeople;
import report.decoratorComponent.ReportFilterPeople;

public class ControllerPeople extends ControllerWindow{

	private PeopleContainerMainGui view;
	
	private ListPeople listPeople;
	
	private ScrollableTable scrollableTable;
	private DtoPeople dtoPeople;

	private int indexSelectOnView; 
	
	private MauseClickedOnTable mauseClickedOnTable;
	private boolean newRegistry;  
	 
	
	public ControllerPeople(PeopleContainerMainGui view) {
		this.view = view;
		listPeople = ListPeople.getInstance();
		scrollableTable = new ScrollableTable(this); 
		view.getPeopleGuiView().getTable().addMouseListener(new MauseClickedOnTable(this));
		addScrollTable();
		addListener();
		if(listPeople.sizeDtos() > 0 ) {
			System.out.println("Contiene datos...");
		}else {
			System.out.println("No contiene datos cargar..");
			try {
				listPeople.loadList();
			}catch (ClassNotFoundException | SQLException e) {
				Messages.showError("  "+e.getMessage());
				
			}   
		} 
		reloadData();
	}  
	public boolean reloadDataList() { 
        	String[][] data= new String[listPeople.sizeDtos()][5]; 
            Interator<DtoPeople> inte =  listPeople.getAll(); 
    		while(inte.hasNext()) {
    			int pointerPeople = inte.now();
    			DtoPeople people =inte.next();
    			data[pointerPeople][0] = people.getName();
            	data[pointerPeople][1] = people.getFirstName();
            	data[pointerPeople][2] = people.getLastName();
            	data[pointerPeople][3] = people.getEmail();
            	data[pointerPeople][4] = people.getTelephone();
    		} 
    		view.getPeopleGuiView().setModelTable(data);
    		view.getPeopleGuiView().getTable().addKeyListener(this);
    		view.getPeopleGuiView().getTable().addMouseListener(mauseClickedOnTable); 
        	return true;
	
	}
	
	@Override
	public boolean saveRegistry() {
		if (view.getPeopleGui().getBtnAdd().getText().equalsIgnoreCase("Modificar")) {
			if (getDataOfView()) {
				updateRegistry(); 
				//reloadData();
				return true;
			}else {
				return true;
			} 
		}else { 
			dtoPeople = new DtoPeople();
			try {
				if (getDataOfView()) {
					listPeople.add(dtoPeople); 
					reloadDataList();
					view.getPeopleGuiView().getTable().setRowSelectionInterval(0,0);
					view.getPeopleGuiView().getScrollPaneTable().getViewport().setViewPosition(new Point(0,0));
					return true;
				}
			} catch (ClassNotFoundException e) { 
				e.printStackTrace();
			} catch (SQLException e) { 
				e.printStackTrace();
			}	
			
		}
		return false;
	}

	@Override
	public boolean filter() {
        try {
        	listPeople.loadListFilter(view.getPeopleGuiView().getCbxFilter(), view.getPeopleGuiView().getTxtFilter().getText());
        	String[][] data= new String[listPeople.sizeDtos()][5]; 
            Interator<DtoPeople> inte =  listPeople.getAll(); 
    		while(inte.hasNext()) {
    			int pointerPeople = inte.now();
    			DtoPeople people =inte.next();
    			data[pointerPeople][0] = people.getName();
            	data[pointerPeople][1] = people.getFirstName();
            	data[pointerPeople][2] = people.getLastName();
            	data[pointerPeople][3] = people.getEmail();
            	data[pointerPeople][4] = people.getTelephone();
    		} 
    		view.getPeopleGuiView().setModelTable(data);
    		view.getPeopleGuiView().getTable().addKeyListener(this);
    		view.getPeopleGuiView().getTable().addMouseListener(mauseClickedOnTable);
        	return true;
		} catch (ClassNotFoundException | SQLException e) {
			Messages.showError(e.getLocalizedMessage());
			return false;
		}  
	}

	@Override
	public boolean updateRegistry() {
	try {
			
			if (listPeople.update(dtoPeople, indexSelectOnView)) {
 
				view.getPeopleGuiView().getTable().setValueAt(dtoPeople.getName(), indexSelectOnView, 0);
				view.getPeopleGuiView().getTable().setValueAt(dtoPeople.getFirstName(), indexSelectOnView, 1);
				view.getPeopleGuiView().getTable().setValueAt(dtoPeople.getLastName(), indexSelectOnView, 2);
				view.getPeopleGuiView().getTable().setValueAt(dtoPeople.getEmail(), indexSelectOnView, 3);
				view.getPeopleGuiView().getTable().setValueAt(dtoPeople.getTelephone(), indexSelectOnView, 4);
				//setDataOfView();
				newRegistry = false;
				return true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			Messages.showError(e.getLocalizedMessage());
		}
		
		return false;
	}

	@Override
	public boolean deleteRegistry() {
		try {
			dtoPeople = listPeople.getList().get(indexSelectOnView); 
			listPeople.delete(indexSelectOnView);
			if (view.getPeopleGuiView().getTable().getSelectedRow() < 0) {
				newRegistry = true;
			}else {
				newRegistry = false;
				dtoPeople = listPeople.getList().get(view.getPeopleGuiView().getTable().getSelectedRow());
			}
			reloadData();
			setDataOfView();
			return true;
		} catch (ClassNotFoundException e) { 
			Messages.showError("  "+e.getMessage());
			return false;
		} catch (SQLException e) { 
			Messages.showError("  "+e.getMessage());
			return false;
		}
	}

	@Override
	public boolean getDataOfView() {
		try {
			if (validateFieldText(view.getPeopleGui().getTxtName().getText())) {
				dtoPeople.setName(view.getPeopleGui().getTxtName().getText());
			}else {
				Messages.showError("  Campo Nombre inv�lido");
				return false;
			}
			if (validateFieldText(view.getPeopleGui().getTxtFirsName().getText())) {
				dtoPeople.setFirstName(view.getPeopleGui().getTxtFirsName().getText());
			}else {
				Messages.showError("  Campo Apellido Paterno inv�lido");
				return false;
			}
			if (validateFieldText(view.getPeopleGui().getTxtLastName().getText())) {
				dtoPeople.setLastName(view.getPeopleGui().getTxtLastName().getText());
			}else {
				Messages.showError("  Campo Apellido Materno inv�lido");
				return false;
			}
			
			if (validateFieldText(view.getPeopleGui().getTxtEmail().getText())) {
				dtoPeople.setEmail(view.getPeopleGui().getTxtEmail().getText());
			}else {
				Messages.showError("  Campo  Correo inv�lido��lido");
				return false;
			}
			if (validateFieldText(view.getPeopleGui().getTxtTelephone().getText())) {
				dtoPeople.setTelephone(view.getPeopleGui().getTxtTelephone().getText());
			}else {
				Messages.showError("  Campo T�lefono inv�lido");
				return false;
			} 
			return true;
		} catch (Exception e) {
			Messages.showError("  "+e.getMessage());
			return false;
		}
	}
	
	private boolean validateFieldText(String text) {
		if (text.length()<1 || text.equals("") )
			return false;	
		
		return true;
	}

	@Override
	public boolean setDataOfView() {
		try {
			if (newRegistry) {
				dtoPeople = new DtoPeople();
				view.getPeopleGui().getBtnAdd().setText("Agregar");
				view.getPeopleGui().getTxtFirsName().setText("");
				view.getPeopleGui().getTxtEmail().setText("");
				view.getPeopleGui().getTxtLastName().setText("");
				view.getPeopleGui().getTxtName().setText("");
				view.getPeopleGui().getTxtTelephone().setText("");	
				view.getPeopleGui().getBtnDelete().setEnabled(false);
			}else {
				dtoPeople = listPeople.getList().get(indexSelectOnView);
				view.getPeopleGui().getTxtFirsName().setText(dtoPeople.getFirstName() + " ID"+ dtoPeople.getId());
				view.getPeopleGui().getTxtEmail().setText(dtoPeople.getEmail());
				view.getPeopleGui().getTxtLastName().setText(dtoPeople.getLastName());
				view.getPeopleGui().getTxtName().setText(dtoPeople.getName());
				view.getPeopleGui().getTxtTelephone().setText(dtoPeople.getTelephone());	
				view.getPeopleGui().getBtnAdd().setText("Modificar");
				view.getPeopleGui().getBtnDelete().setEnabled(true);
			}
			return true;
		} catch (Exception e) {
			Messages.showError("  "+e.getMessage());
			return false;
		}
	}

	@Override
	public boolean reloadData() {
		
        	String[][] data= new String[listPeople.sizeDtos()][5]; 
            Interator<DtoPeople> interator =  listPeople.getAll(); 
    		while(interator.hasNext()) {
    			int countPeoples = interator.now();
    			DtoPeople people= interator.next();
            	data[countPeoples][0] = people.getName();
            	data[countPeoples][1] = people.getLastName();
            	data[countPeoples][2] = people.getFirstName();
            	data[countPeoples][3] = people.getEmail();
            	data[countPeoples][4] = people.getTelephone();
    		} 
        	view.getPeopleGuiView().setModelTable(data);
        	view.getPeopleGuiView().getTable().addKeyListener(this);
        	view.getPeopleGuiView().getTable().addMouseListener(mauseClickedOnTable);
        	
        	return true;
		
	}

	@Override
    public void keyReleased( KeyEvent d ) { 
       if( view.getPeopleGuiView().getTable().getSelectedRows().length > 0 ) { 
         indexSelectOnView = view.getPeopleGuiView().getTable().getSelectedRow();
         dtoPeople = listPeople.getList().get(indexSelectOnView);
         dtoPeople.setName(view.getPeopleGuiView().getTable().getValueAt(indexSelectOnView, 0).toString());
         dtoPeople.setLastName(view.getPeopleGuiView().getTable().getValueAt(indexSelectOnView, 1).toString());
         dtoPeople.setFirstName(view.getPeopleGuiView().getTable().getValueAt(indexSelectOnView, 2).toString());
         dtoPeople.setEmail(view.getPeopleGuiView().getTable().getValueAt(indexSelectOnView, 3).toString());
         dtoPeople.setTelephone(view.getPeopleGuiView().getTable().getValueAt(indexSelectOnView, 4).toString());
       }
       updateRegistry();
    }
	
	@Override
	public void actionPerformed(ActionEvent e) { 
		if (e.getSource() == view.getPeopleGuiView().getBtnFilter()) {
			 filter();
		} else if(e.getSource() == view.getPeopleGui().getBtnCancel()) {
			if (view.getPeopleGui().getBtnAdd().getText().equalsIgnoreCase("Modificar")) {
				newRegistry = true; 
				view.getPeopleGuiView().getTable().clearSelection();
				setDataOfView();
			}else {
				view.getPeopleGuiView().getTable().setRowSelectionInterval(0, 0); 
				newRegistry = false;
				setDataOfView();
			}
		}else if(e.getSource() == view.getPeopleGui().getBtnAdd()) {
			if (saveRegistry()) {
				Messages.showMessage(" Guardado");
			}
		}else if(e.getSource() == view.getPeopleGui().getBtnDelete()) {
			if (deleteRegistry()) {
				Messages.showMessage(" Eliminado");
			} 	
		}else if(e.getSource() == view.getPeopleGui().getBtnInforme()) {
			searchReport();
		}
	}

	private void searchReport() {
		String[] reportOption = { "Reporte Simple", "Reporte(Mediante Busqueda Avanzada)"};
		JFrame frame = new JFrame();
		
	    String index = (String) JOptionPane.showInputDialog(frame,"Qu� reporte deseas ver?","Formato de Reporte",
		        JOptionPane.QUESTION_MESSAGE, null, reportOption, reportOption[0]);
	    
	    FormatReport format = null;
	    boolean execute = false;
	    if (index.equalsIgnoreCase("Reporte Simple")) {
	    	format = new ReportPeople();
	    	execute = true;
		}else {
			format = new ReportFilterPeople(this, new ReportPeople());
			execute = true;
		} 
	    if (execute) {
	    	try {
				((ListPeople) listPeople).getReport(format);
			} catch (ClassNotFoundException e1) { 
				e1.printStackTrace();
			} catch (SQLException e1) { 
				e1.printStackTrace();
			} 
		} 
	}



	@Override
	public boolean addListener() {
		try {
			view.getPeopleGuiView().getBtnFilter().addActionListener(this);
			view.getPeopleGui().getBtnAdd().addActionListener(this);
			view.getPeopleGui().getBtnDelete().addActionListener(this);
			view.getPeopleGui().getBtnCancel().addActionListener(this);
			view.getPeopleGui().getBtnInforme().addActionListener(this); 
		    return true;
		} catch (Exception e) {
			Messages.showError("  "+e.getMessage());
			return false;
		}
	}
	
	public void addScrollTable() {
		view.getPeopleGuiView().getScrollPaneTable().getVerticalScrollBar().addAdjustmentListener(scrollableTable);	
	}
	
	public void loadNextCars () {
		Executor executor = new Executor() {
			@Override
			public void execute(Runnable arg0) {
				arg0.run();
			}
		};

		executor.execute(() -> {
			try {
				if(listPeople.reloadNext()) 
					reloadData();
			} catch (ClassNotFoundException | SQLException e) {
				Messages.showError(" "+e.getLocalizedMessage());
			}
		});
	}
 
//Inner Class Event to view
	
	private class MauseClickedOnTable extends MouseAdapter{
			private ControllerPeople controllerPeople; 
			public MauseClickedOnTable(ControllerPeople controllerPeople) {
				this.controllerPeople = controllerPeople;
			}
			public void mouseClicked(MouseEvent evnt)
			{
				  if (evnt.getClickCount() == 1)
				  {
					  this.controllerPeople.indexSelectOnView =  this.controllerPeople.view.getPeopleGuiView().getTable().getSelectedRow();
					  this.controllerPeople.newRegistry = false;
					  this.controllerPeople.dtoPeople = this.controllerPeople.listPeople.getList().get(indexSelectOnView);
					  this.controllerPeople.setDataOfView();
				  }
			}
		}
		
	private class ScrollableTable implements AdjustmentListener {
		
		private ControllerPeople controller;
		
		public ScrollableTable(ControllerPeople controller) {
			this.controller = controller;
		}
		
		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			  if(!e.getValueIsAdjusting()){    
	                JScrollBar source = (JScrollBar) e.getAdjustable();
	                int extent = source.getModel().getExtent();
	                int maximum = source.getModel().getMaximum();
	                if(e.getValue() + extent == maximum){
	                	System.out.println("Final");
	                	controller.loadNextCars();
	                }
	            }
		} 
	}

	public String getParametro() { 
		return JOptionPane.showInputDialog(null, "Ingresa Digitos de una placa para b�squeda");
	}

}
