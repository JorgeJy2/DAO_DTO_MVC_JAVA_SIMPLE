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

import javax.swing.JScrollBar;
 
import gui.content.car.CarContainerMainGui;
import gui.content.car.CarGui;
import gui.content.car.CarGuiView;
import gui.dialogs.Messages;
import model.dto.DtoCar;
import model.list.ListCar;
import model.list.Listable;
import model.list.interador.Interator; 

public class ControllerCar extends ControllerWindow {
	 
	private CarContainerMainGui viewCar; 
	private CarGuiView carGuiView;
	private CarGui carGui; 
	
	private DtoCar dtoCar;
	private int indexSelectOnView;
	private MauseClickedOnTable mauseClickedOnTable;
	private boolean newRegistry;
	
	private Listable<DtoCar> listCar;
	
	private ScrollableTable scrollableTable;
	
	public ControllerCar(CarContainerMainGui viewCar) {
		this.viewCar = viewCar;
		listCar = ListCar.getInstance(); 
		mauseClickedOnTable = new MauseClickedOnTable(this);
		scrollableTable = new ScrollableTable(this); 
		carGuiView = this.viewCar.getCarGuiView();
		carGui = this.viewCar.getCarGui();
		
		addListener();
		addScrollTable();
		
		carGuiView.getTable().addKeyListener(this);
    	carGuiView.getTable().addMouseListener(mauseClickedOnTable);
		
		if ( listCar.sizeDtos() <= 0) {
			try {
				listCar.loadList();
			} catch (ClassNotFoundException | SQLException e) {
				Messages.showMessage(e.getLocalizedMessage());
			}
		}
		reloadData();
	
	}
	
	@Override
	public boolean saveRegistry() {
		if (carGui.getBtnAdd().getText().equalsIgnoreCase("Modificar")) {
			if (getDataOfView()) 
				updateRegistry();
			
			return true;
			
		}else {
			
			
			dtoCar = new DtoCar();
			try {
				if (getDataOfView()) {
					listCar.add(dtoCar);
					
					reloadData();
					carGuiView.getTable().setRowSelectionInterval(0,0);
					carGuiView.getScrollPaneTable().getViewport().setViewPosition(new Point(0,0));
					
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
        	listCar.loadListFilter(carGuiView.getCbxFilter(), carGuiView.getTxtFilter().getText());
        	String[][] data= new String[listCar.sizeDtos()][5]; 
            Interator<DtoCar> inte =  listCar.getAll(); 
    		while(inte.hasNext()) {
    			int pointerCar = inte.now();
    			DtoCar car =inte.next();
    			data[pointerCar][0] = car.getModelo();
            	data[pointerCar][1] = car.getPlaca();
            	data[pointerCar][2] = car.getColor();
    		} 
        	carGuiView.setModelTable(data);
       
        	return true;
		} catch (ClassNotFoundException | SQLException e) {
			Messages.showError(e.getLocalizedMessage());
			return false;
		}  
	}

	@Override
	public boolean updateRegistry() {
		try {
			
			if (listCar.update(dtoCar, indexSelectOnView)) {
				carGuiView.getTable().setValueAt(dtoCar.getModelo(), indexSelectOnView, 0);
				carGuiView.getTable().setValueAt(dtoCar.getPlaca(), indexSelectOnView, 1);
				carGuiView.getTable().setValueAt(dtoCar.getColor(), indexSelectOnView, 2);
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
			//dtoCar = listCar.getOne(indexSelectOnView);
			listCar.delete(indexSelectOnView);
			if (carGuiView.getTable().getSelectedRow() < 0) {
				newRegistry = true;
			}else {
				newRegistry = false;
				dtoCar = listCar.getOne(carGuiView.getTable().getSelectedRow());
			}
			listCar.loadList();
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
			if (validateFieldText(carGui.getTxtColor().getText())) {
				dtoCar.setColor(carGui.getTxtColor().getText());
			}else {
				Messages.showError("  Campo Color inv�lido");
				return false;
			}
			if (validateFieldText(carGui.getTxtModelo().getText())) {
				dtoCar.setModelo(carGui.getTxtModelo().getText());
			}else {
				Messages.showError("  Campo Modelo inv�lido");
				return false;
			}
			if (validateFieldText(carGui.getTxtPlaca().getText())) {
				dtoCar.setPlaca(carGui.getTxtPlaca().getText());
			}else {
				Messages.showError("  Campo Placa inv�lido");
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
    public void keyReleased( KeyEvent d ) { 
       if( carGuiView.getTable().getSelectedRows().length > 0 ) { 
         indexSelectOnView = carGuiView.getTable().getSelectedRow();
         dtoCar = listCar.getOne(indexSelectOnView);
         dtoCar.setModelo(carGuiView.getTable().getValueAt(indexSelectOnView, 0).toString());
         dtoCar.setPlaca(carGuiView.getTable().getValueAt(indexSelectOnView, 1).toString());
         dtoCar.setColor(carGuiView.getTable().getValueAt(indexSelectOnView, 2).toString());
       }
       updateRegistry();
    }

	@Override
	public boolean setDataOfView() {
		try {
			if (newRegistry) {
				dtoCar = new DtoCar();
				carGui.getBtnAdd().setText("Agregar");
				carGui.getTxtColor().setText("");
				carGui.getTxtModelo().setText("");
				carGui.getTxtPlaca().setText("");
				carGui.getBtnDelete().setEnabled(false);
			}else {
				dtoCar = listCar.getOne(indexSelectOnView);
				carGui.getTxtColor().setText(dtoCar.getColor());
				carGui.getTxtModelo().setText(dtoCar.getModelo());
				carGui.getTxtPlaca().setText(dtoCar.getPlaca());
				carGui.getBtnAdd().setText("Modificar");
				carGui.getBtnDelete().setEnabled(true);
			}
			return true;
		} catch (Exception e) {
			Messages.showError("  "+e.getMessage());
			return false;
		}
	}
	
	
	
	@Override
	public boolean reloadData() {
		/*
        try {
        	listCar.loadList();
        	String[][] data= new String[listCar.sizeDtos()][5]; 
            Interator<DtoCar> inte =  listCar.getAll(); 
    		while(inte.hasNext()) {
    			
    			int pointerCar = inte.now();
    			DtoCar car =inte.next();
    			data[pointerCar][0] = car.getModelo();
            	data[pointerCar][1] = car.getPlaca();
            	data[pointerCar][2] = car.getColor();
    		} 
        	carGuiView.setModelTable(data);

        	
        	return true;
		} catch (ClassNotFoundException | SQLException e) {
			Messages.showError("  "+e.getMessage());
			return false;
		}  */
		
		String[][] data= new String[listCar.sizeDtos()][5]; 
        Interator<DtoCar> inte =  listCar.getAll(); 
		while(inte.hasNext()) {
			int pointerCar = inte.now();
			DtoCar car =inte.next();
			data[pointerCar][0] = car.getModelo();
        	data[pointerCar][1] = car.getPlaca();
        	data[pointerCar][2] = car.getColor();
		} 
    	carGuiView.setModelTable(data);
    	return true;
	}
	
	/*
	public boolean reloadDataList() {
        	String[][] data= new String[listCar.sizeDtos()][5]; 
            Interator<DtoCar> inte =  listCar.getAll(); 
    		while(inte.hasNext()) {
    			int pointerCar = inte.now();
    			DtoCar car =inte.next();
    			data[pointerCar][0] = car.getModelo();
            	data[pointerCar][1] = car.getPlaca();
            	data[pointerCar][2] = car.getColor();
    		} 
        	carGuiView.setModelTable(data);
        //	carGuiView.getTable().addKeyListener(this);
        //	carGuiView.getTable().addMouseListener(mauseClickedOnTable);
        	return true;
	}
	*/
	
	@Override
	public boolean addListener() { 
		try {
			carGuiView.getBtnFilter().addActionListener(this);
			carGui.getBtnAdd().addActionListener(this);
			carGui.getBtnDelete().addActionListener(this);
			carGui.getBtnCancel().addActionListener(this);
			carGui.getBtnInforme().addActionListener(this);
		
		    return true;
		} catch (Exception e) {
			Messages.showError("  "+e.getMessage());
			return false;
		}
	}
	
	public void addScrollTable() {
		carGuiView.getScrollPaneTable().getVerticalScrollBar().addAdjustmentListener(scrollableTable);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { 
		if (e.getSource() == carGuiView.getBtnFilter()) {
			 filter();
		} else if(e.getSource() == carGui.getBtnCancel()) {
			if (carGui.getBtnAdd().getText().equalsIgnoreCase("Modificar")) {
				newRegistry = true; 
				carGuiView.getTable().clearSelection();
				setDataOfView();
			}else {
				carGuiView.getTable().setRowSelectionInterval(0, 0); 
				newRegistry = false;
				setDataOfView();
			}
		}else if(e.getSource() == carGui.getBtnAdd()) {
			if (saveRegistry()) {
				Messages.showMessage(" Guardado");
			}
		}else if(e.getSource() == carGui.getBtnDelete()) {
			if (deleteRegistry()) {
				Messages.showMessage(" Eliminado");
			} 	
		}else if(e.getSource() == carGui.getBtnInforme()) {
			try {
				((ListCar) listCar).getReport("reporte.jasper");
			} catch (ClassNotFoundException e1) {
				Messages.showError(" "+e1.getMessage());
			} catch (SQLException e1) {
				Messages.showError(" "+e1.getMessage());
			}
		}
	}
	
	private class MauseClickedOnTable extends MouseAdapter{
		private ControllerCar controllerCar;
		
		public MauseClickedOnTable(ControllerCar controllerCar) {
			this.controllerCar = controllerCar;
		}
		public void mouseClicked(MouseEvent evnt)
		{
			  if (evnt.getClickCount() == 1)
			  {
				  this.controllerCar.indexSelectOnView = this.controllerCar.carGuiView.getTable().getSelectedRow();
				  this.controllerCar.newRegistry = false;
				  dtoCar = listCar.getOne(indexSelectOnView);
				  this.controllerCar.setDataOfView();
			  }
		}
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
				if(listCar.reloadNext()) 
					reloadData();
			} catch (ClassNotFoundException | SQLException e) {
				Messages.showError(" "+e.getLocalizedMessage());
			}
		});
	}
	//Inner Class Event to view
	private class ScrollableTable implements AdjustmentListener {
		
		private ControllerCar controllerCar; 
		public ScrollableTable(ControllerCar controllerCar) {
			this.controllerCar = controllerCar;
		}
		
		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			  if(!e.getValueIsAdjusting()){    
	                JScrollBar source = (JScrollBar) e.getAdjustable();
	                int extent = source.getModel().getExtent();
	                int maximum = source.getModel().getMaximum();
	                if(e.getValue() + extent == maximum){
	                	controllerCar.loadNextCars();
	                }
	            }
		}
		
	}
	
	
	
 
}
