package model.list;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DaoInterface;
import dao.DaoTicket;
import dao.SaveErrosDao;
import model.dto.DtoTicket;

import model.list.interador.DaoInteractor;
import model.list.interador.Interator;
import net.sf.jasperreports.engine.JRException;
import report.FormatReport;

/**
 * Archivo: ListTicket.java contiene la definici�n de la clase ListTicket que
 * implementa de la interfaz Listable.
 * 
 * @author Jorge Jacobo, Marcos Moreno,Gabriel Garcia, Amanda Franco
 * @version 1.0
 *
 */
public class ListTicket implements Listable<DtoTicket> {
	// declaraci�n de atributos
	private static ListTicket instance;

	private DaoInterface<DtoTicket> _dao;
	private List<DtoTicket> _tickets;
//	private Paginator<DtoTicket> _paginator;

	// constructor sin parametros
	private ListTicket() {
		_dao = new DaoTicket();
		_tickets = new ArrayList<DtoTicket>();
		// _paginator = new PaginatorDao<DtoTicket>(_dao);
	}// cierre constructor

	/**
	 * Instancia de la clase ListTicket
	 * 
	 * @return retorna la instancia
	 */
	public static ListTicket getInstance() {

		if (instance == null)
			instance = new ListTicket();

		return instance;
	}// cierre instancia

	/**
	 * M�todo loadList
	 * 
	 * @throws excepcion de clase y base de datos
	 */
	@Override
	public void loadList() throws ClassNotFoundException, SQLException {

		try {
			this._tickets = _dao.getAll();
		} catch (ClassNotFoundException e) {
			SaveErrosDao.saveErrors(e);
			throw new ClassNotFoundException(e.getMessage());
		} catch (SQLException e) {
			SaveErrosDao.saveErrors(e);
			throw new SQLException(e.getMessage());
		}
	}// cierre m�todo loadList

	/**
	 * M�todo add
	 * 
	 * @param dtoTicket objeto de tipo DtoTicket
	 * @return retorna un valor de tipo booleano
	 * @throws excepcion de tipo clase y base de datos
	 */
	@Override
	public boolean add(DtoTicket ticket) throws ClassNotFoundException, SQLException {

		try {
			int id_added = (int) _dao.add(ticket);
			if (id_added != -1) {
				ticket.setId(id_added);
				_tickets.add(ticket);
				return true;
			}
			return false;
		} catch (ClassNotFoundException e) {
			SaveErrosDao.saveErrors(e);
			throw new ClassNotFoundException(e.getMessage());
		} catch (SQLException e) {
			SaveErrosDao.saveErrors(e);
			throw new SQLException(e.getMessage());
		}
	}// cierre m�todo add

	/**
	 * M�todo getOne
	 * 
	 * @param position valor de tipo entero
	 * @return retorna un objeto de tipo DtoTicket
	 */
	@Override
	public DtoTicket getOne(int position) {
		return _tickets.get(position);
	}// cierre m�todo getOne

	/**
	 * M�todo delete
	 * 
	 * @param position valor de tipo entero
	 * @return retorna un valor de tipo booleano
	 * @throws exceopcion de tipo clase y base de datos
	 */
	@Override
	public boolean delete(int position) throws ClassNotFoundException, SQLException {

		try {
			if (_dao.delete(_tickets.get(position).getId())) {
				_tickets.remove(position);
				return true;
			}
			return false;
		} catch (ClassNotFoundException e) {
			SaveErrosDao.saveErrors(e);
			throw new ClassNotFoundException(e.getMessage());
		} catch (SQLException e) {
			SaveErrosDao.saveErrors(e);
			throw new SQLException(e.getMessage());
		}
	}// cierre m�todo delete

	/**
	 * M�todo update
	 * 
	 * @param dtoTicket objeto de tipo DtoTicket
	 * @param position  valor de tipo entero
	 * @return retorna una valor de tipo booleano
	 * @throws excepcion de tipo clase y base de datos
	 */
	@Override
	public boolean update(DtoTicket dtoCar, int position) throws ClassNotFoundException, SQLException {

		try {
			if (_dao.update(dtoCar)) {
				_tickets.set(position, dtoCar);
				return true;
			}
			return false;

		} catch (ClassNotFoundException e) {
			SaveErrosDao.saveErrors(e);
			throw new ClassNotFoundException(e.getMessage());
		} catch (SQLException e) {
			SaveErrosDao.saveErrors(e);
			throw new SQLException(e.getMessage());
		}
	}// cierre m�todo update

	/**
	 * M�todo getList
	 * 
	 * @return retorna un objeto de tipo List
	 */
	@Override
	public List<DtoTicket> getList() {
		return _tickets;
	}// cierre m�todo getList

	/**
	 * M�todo getAll
	 * 
	 * @return retorna un objeto de tipo Interator
	 */
	@Override
	public Interator<DtoTicket> getAll() {
		return new DaoInteractor<DtoTicket>(_tickets);
	}// cierre m�todo getAll

	/**
	 * M�todo sizeDtos
	 * 
	 * @return retorna un valor de tipo entero
	 */
	@Override
	public int sizeDtos() {
		return _tickets.size();
	}// cierre m�todo sizeDtos

	/**
	 * M�todo addedTicketInList
	 * 
	 * @param tickesNews objeto de tipo List
	 * @return retorna valor de tipo booleano
	 */
	private boolean addedTicketInList(List<DtoTicket> tickesNews) {
		if (tickesNews != null) {
			if (tickesNews.size() > 0) {
				_tickets.addAll(tickesNews);
				return true;
			} else
				return false;
		} else
			return false;
	}// cierre m�todo addedTicketInList

	/**
	 * M�todo loadListFilter
	 * 
	 * @param parameter valor de tipo String
	 * @param value     valor de tipo String
	 * @throws excepcion de tipo clase y base de datos
	 */
	@Override
	public void loadListFilter(String parameter, String value) throws ClassNotFoundException, SQLException {

		try {
			System.out.println("parameter: " + parameter + " value " + value);
			_tickets = _dao.getFilter(parameter, value);
		} catch (ClassNotFoundException e) {
			SaveErrosDao.saveErrors(e);
			throw new ClassNotFoundException(e.getMessage());
		} catch (SQLException e) {
			SaveErrosDao.saveErrors(e);
			throw new SQLException(e.getMessage());
		}
	}// cierre m�todo loadListFilter

	/**
	 * M�todo reloadNext
	 * 
	 * @return retorna un valor de tipo booleano
	 * @throws excepcion de tipo clase y base de datos
	 */
	@Override
	public boolean reloadNext() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}// cierre m�todo reloadNext

	/**
	 * M�todo getReport
	 * 
	 * @param format objeto de tipo FormatReport
	 * @return retorna un valor de tipo booleano
	 */
	@Override
	public boolean getReport(FormatReport format)
			throws ClassNotFoundException, SQLException, JRException, IOException {
		_dao.generateReport(format);
		return true;
	}// cierre m�todo getReport

}// cierre clase ListTicket
