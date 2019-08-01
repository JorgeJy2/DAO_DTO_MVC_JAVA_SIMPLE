package controller;

import java.awt.JobAttributes;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.concurrent.Executor;

import javax.swing.JOptionPane;
import javax.swing.JScrollBar;

import dao.DaoCar;
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
	private DaoCar daoCar;
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
		daoCar = new DaoCar();
		addListener();
		addScrollTable();
		reloadData();
	}
	
	@Override
	public boolean saveRegistry() {
		if (carGui.getBtnAdd().getText().equalsIgnoreCase("Modificar")) {
			if (getDataOfView()) {
				updateRegistry();
				reloadData();
				return true;
			}else {
				return true;
			}
			
		}else {
			dtoCar = new DtoCar(); 
			try {
				if (getDataOfView()) {
					daoCar.add(dtoCar);
					reloadData();
					return true;
				}else {
					return false;
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
		listCar = ListCar.getInstance();
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
        	carGuiView.getTable().addKeyListener(this);
        	carGuiView.getTable().addMouseListener(mauseClickedOnTable);
        	return true;
		} catch (ClassNotFoundException | SQLException e) {
			Messages.showError(e.getLocalizedMessage());
			return false;
		}  
	}

	@Override
	public boolean updateRegistry() {
		try {
			daoCar.update(dtoCar);
			setDataOfView();
			newRegistry = false;
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			Messages.showError(e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public boolean deleteRegistry() {
		try {
			dtoCar = listCar.getList().get(indexSelectOnView);
			daoCar.delete(dtoCar.getId());
			if (carGuiView.getTable().getSelectedRow() < 0) {
				newRegistry = true;
			}else {
				newRegistry = false;
				dtoCar = listCar.getList().get(carGuiView.getTable().getSelectedRow());
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
			if (validateFieldText(carGui.getTxtColor().getText())) {
				dtoCar.setColor(carGui.getTxtColor().getText());
			}else {
				Messages.showError("  Campo Color Invalido");
				return false;
			}
			if (validateFieldText(carGui.getTxtModelo().getText())) {
				dtoCar.setModelo(carGui.getTxtModelo().getText());
			}else {
				Messages.showError("  Campo Modelo Invalido");
				return false;
			}
			if (validateFieldText(carGui.getTxtPlaca().getText())) {
				dtoCar.setPlaca(carGui.getTxtPlaca().getText());
			}else {
				Messages.showError("  Campo Placa Invalido");
				return false;
			}
			return true;
		} catch (Exception e) {
			Messages.showError("  "+e.getMessage());
			return false;
		}
	}
	
	private boolean validateFieldText(String text) {
		if (text.length()<1) {
			return false;	
		}if (text.equals("")) {
			return false;	
		}
		return true;
	}

	@Override
    public void keyReleased( KeyEvent d ) { 
       if( carGuiView.getTable().getSelectedRows().length > 0 ) { 
         indexSelectOnView = carGuiView.getTable().getSelectedRow();
         dtoCar = listCar.getList().get(indexSelectOnView);
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
				dtoCar = listCar.getList().get(indexSelectOnView);
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
        	carGuiView.getTable().addKeyListener(this);
        	carGuiView.getTable().addMouseListener(mauseClickedOnTable);
        	
        	return true;
		} catch (ClassNotFoundException | SQLException e) {
			Messages.showError("  "+e.getMessage());
			return false;
		}  
	}
	
	
	@Override
	public boolean addListener() { 
		try {
			carGuiView.getBtnFilter().addActionListener(this);
			carGui.getBtnAdd().addActionListener(this);
			carGui.getBtnDelete().addActionListener(this);
			carGui.getBtnCancel().addActionListener(this);
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
				JOptionPane.showMessageDialog(null, "agregar");
		//		carGuiView.getTable().is(0, 0);
				dtoCar = listCar.getList().get(indexSelectOnView);
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
			
//			try {
//				daoCar.generateReport();
//			} catch (ClassNotFoundException e1) {
//				Messages.showError(" "+e1.getMessage());
//				//e1.printStackTrace();
//			} catch (SQLException e1) {
//				Messages.showError(" "+e1.getMessage());
//				//e1.printStackTrace();
//			}
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
	                	System.out.println("Final");
	                	controllerCar.loadNextCars();
	                }
	            }
		}
		
	}
	
 
}
