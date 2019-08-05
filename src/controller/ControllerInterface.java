package controller;

/**
 * Archivo: ControllerInterface.java contiene la definici�n de la interface
 * ControllerInterface que establece los m�todos que deben implementar las
 * clases que extiendan de esta.
 * 
 * @author Jorge Jacobo, Marcos Moreno, Gabriel Garcia, Amanda Franco
 * @version 1.0
 *
 */
public interface ControllerInterface {
	// m�todos de la interfaz
	/**
	 * M�todo saveRegistry
	 * 
	 * @return retorna un valor booleano
	 */
	public boolean saveRegistry();

	/**
	 * M�todo filter
	 * 
	 * @return retorna un valor booleano
	 */
	public boolean filter();

	/**
	 * M�todo updateRegistry
	 * 
	 * @return retorna un valor booleano
	 */
	public boolean updateRegistry();

	/**
	 * M�todo deleteRegistry
	 * 
	 * @return retorna un valor booleano
	 */
	public boolean deleteRegistry();

	/**
	 * M�todo getDataOfView
	 * 
	 * @return retorna un valor booleano
	 */
	public boolean getDataOfView();

	/**
	 * M�todo setDataOfView
	 * 
	 * @return retorna un valor booleano
	 */
	public boolean setDataOfView();

	/**
	 * M�todo reloadData
	 * 
	 * @return retorna un valor booleano
	 */
	public boolean reloadData();

	/**
	 * M�todo addListener
	 * 
	 * @return retorna un valor booleano
	 */
	public boolean addListener();
}// cierre interface ControllerInterface
