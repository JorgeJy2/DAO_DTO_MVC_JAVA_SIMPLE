package factory;

import dao.DaoInterface;

/**
 * Archivo: FactoryDao.java contiene la definici�n de la clase FactoryDao.
 * 
 * @author Jorge Jacobo, Marcos Moreno, Gabriel Garcia, Amanda Franco
 *
 */
public class FactoryDao {
	/**
	 * M�todo est�tico crearFabricaDao
	 * 
	 * @param factory objeto de tipo ObjectDao
	 */
	public static void crearFabricaDao(ObjectDao factory) {
		/** Aplicamos Polimorfismo */
		DaoInterface<?> objetoDAO = factory.crearDao();
		// objetoDAO.add();
		// objetoVehiculo.codigoDeVehiculo();
	}// cierre m�todo crearFabricaDao
}// cierre clase FactoryDao
