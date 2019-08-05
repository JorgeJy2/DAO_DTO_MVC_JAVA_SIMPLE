package observer;

import java.util.HashSet;
import java.util.Set;

/**
 * Archivo: Observable.java contiene la definici�n de la clase Observable.
 * 
 * @author Jorge Jacobo, Marcos Moreno, Gabriel Garcia, Amanda Franco
 * @version 1.0
 *
 */
public class Observable {
	// declaraci�n de atributo
	private final Set<IObserver> IObservers = new HashSet<IObserver>();

	/**
	 * M�todo addIObservers
	 * 
	 * @param observador objeto de tipo IObserver
	 */
	public void addIObservers(IObserver observador) {
		this.IObservers.add(observador);

	}// cierre m�todo addIObservers

	/**
	 * M�todo removeIObservers
	 * 
	 * @param observador objeto de tipo IObserver
	 */
	public void removeIObservers(IObserver observador) {
		this.IObservers.remove(observador);

	}// cierre m�todo removeIObservers

	/**
	 * M�todo notifyIObservers
	 */
	public void notifyIObservers() {
		for (IObserver IObserver : IObservers) {
			IObserver.update();
		}
	}// cierre m�todo notifyIObservers
}// cierre clase Observable
