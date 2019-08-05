package model.list.paginator;

import java.sql.SQLException;
import java.util.List;

import dao.DaoInterface;

/**
 * Archivo: PaginatorDao.java contiene la definici�n de la clase PaginatorDao
 * que implementa de la interfaz Paginator.
 * 
 * @author Jorge Jacobo, Marcos Moreno, Gabriel Garcia, Amanda Franco
 * @version 1.0
 *
 */
public class PaginatorDao<Dto> implements Paginator<Dto> {
	// declaraci�n de atributos
	private DaoInterface<Dto> dao;
	private int index = 0;

	/**
	 * Constructor con par�metro
	 * 
	 * @param dao objeto de tipo DaoInterface
	 */
	public PaginatorDao(DaoInterface<Dto> dao) {
		this.dao = dao;
	}// cierre constructor

	// Implementaci�n de m�todos
	/**
	 * M�todo first
	 * 
	 * @return retorna un objeto de tipo List
	 * @throws excepcion de tipo clase y base de datos
	 */
	@Override
	public List<Dto> first() throws ClassNotFoundException, SQLException {
		return dao.getPaginator(0, NUM_PAGINATOR);
	}// Cierre m�todo first

	/**
	 * M�todo next
	 * 
	 * @return retorna un objeto de tipo List
	 * @throws excepcion de clase y base de datos
	 */
	@Override
	public List<Dto> next() throws ClassNotFoundException, SQLException {
		List<Dto> next = dao.getPaginator(index, ((index + 1) + NUM_PAGINATOR));

		if (next.size() > 0)
			index += next.size();

		return next;
	}// cierre m�todo next

}// cierre clase PaginatorDao
