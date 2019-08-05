package gui.content.ticket;

import java.awt.GridLayout;

import javax.swing.JPanel;

import controller.ControllerTicket;
import gui.resource.ResourcesGui;

/**
 * Archivo: TicketContainerMainGui.java contiene la definici�n de la clase
 * TicketContainerMainGui que extiende de JPanel.
 * 
 * @author Jorge Jacobo, Marcos Moreno, Gabriel Garcia, Amanda Franco
 * @version 1.0
 *
 */
public class TicketContainerMainGui extends JPanel {
	// declaraci�n de atributos
	private static final long serialVersionUID = 1L;

	private TicketGui ticketGui;
	private TicketGuiView ticketGuiView;

	// constructor sin par�metros
	public TicketContainerMainGui() {
		createGui();

		new ControllerTicket(this);
	}// cierre constructor

	// m�todo que crea la vista
	private void createGui() {
		ticketGui = new TicketGui();
		ticketGuiView = new TicketGuiView();

		this.setBackground(ResourcesGui.COLOR.getSecondColor());
		this.setLayout(new GridLayout(1, 2));
		this.add(ticketGuiView);
		this.add(ticketGui);
	}// cierre m�todo createGui

	// getters
	public TicketGui getTicketGui() {
		return ticketGui;
	}

	public TicketGuiView getTicketGuiView() {
		return ticketGuiView;
	}

}// cierre clase TicketContainerMainGui
