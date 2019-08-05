package gui.content.car;

import java.awt.GridLayout;
import javax.swing.JPanel;

import controller.ControllerCar;
import gui.resource.ResourcesGui;

/**
 * Archivo: CarContainerMainGui.java contiene la definici�n de la clase
 * CarContainerMainGui que extiende de JPanel.
 * 
 * @author Jorge Jacobo, Marcos Moreno, Gabriel Garcia, Amanda Franco
 * @version 1.0
 *
 */
public class CarContainerMainGui extends JPanel {
//declaraci� de atributos
	private static final long serialVersionUID = 1L;

	private CarGuiView carGuiView;
	private CarGui carGui;

	/**
	 * Constructor sin par�metros
	 */
	public CarContainerMainGui() {
		createGui();
		new ControllerCar(this);
	}// cierre constructor

	// m�todo que crea la vista
	private void createGui() {
		this.carGuiView = new CarGuiView();
		this.carGui = new CarGui();
		this.setBackground(ResourcesGui.COLOR.getSecondColor());
		this.setLayout(new GridLayout(1, 2));
		this.add(this.carGuiView);
		this.add(this.carGui);
	}// cierre m�todo createGui

	/**
	 * M�todo getCarGuiView
	 * 
	 * @return retorna un objeto CarGuiView
	 */
	public CarGuiView getCarGuiView() {
		return carGuiView;
	}// cierre m�todo getCarGuiView

	/**
	 * M�todo getCarGui
	 * 
	 * @return retorna un objeto CarGui
	 */
	public CarGui getCarGui() {
		return carGui;
	}// cierre m�todo getCarGui

}// cierre clase CarContainerMainGui
