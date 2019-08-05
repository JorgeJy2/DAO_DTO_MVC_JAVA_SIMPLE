package factory;

import dao.DaoInterface;

/**
 * Archivo: ObjectDao.java contiene la definici�n de la interface ObjectDao.
 * 
 * @author Jorge Jacobo, Marcos Moreno, Gabriel Garcia, Amanda Franco
 * @version 1.0
 *
 */
public interface ObjectDao {
	// m�todo crearDao
	public DaoInterface<?> crearDao();
}// cierre interface ObjectDao
