package factory;

import dao.DaoInterface;
import dao.DaoPeople;

/**
 * Archivo: FactoryPeople.java contiene la definici�n de la clase FactoryPeople
 * que implementa ObjectDao.
 * 
 * @author Jorge Jacobo, Marcos Moreno, Gabriel Garcia, Amanda Franco
 * @version 1.0
 *
 */
public class FactoryPeople implements ObjectDao {
	/**
	 * M�todo crearDao
	 * 
	 * @return retorna un objeto DaoPeople
	 */
	@Override
	public DaoInterface<?> crearDao() {
		DaoPeople daoPeople = new DaoPeople();
		return daoPeople;
	}// cierre m�todo crearDao

}// cierre clase FactoryPeople
