package model.list.interador;

/**
 * Archivo: Interator.java contiene la definici�n de la interface Interator.
 * 
 * @author Jorge Jacobo, Marcos Moreno, Gabriel Garcia, Amanda Franco
 * @version 1.0
 *
 */
public interface Interator<Dto> {
	// m�todos de la interface
	public Dto first();

	public Dto next();

	public boolean hasNext();

	public boolean hasBefore();

	public Dto before();

	public int now();
}// cierre interface Interator
