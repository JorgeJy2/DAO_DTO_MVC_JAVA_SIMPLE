package model.list;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DaoCar;
import dao.DaoInterface;
import model.dto.DtoCar;
import model.list.interador.DaoInteractor;
import model.list.interador.Interator;
import model.list.paginator.Paginator;
import model.list.paginator.PaginatorDao;


public class ListCar implements Listable<DtoCar> {
	
	private static ListCar _instance;
	
	private DaoInterface<DtoCar> _daoAuto;
	
	private List<DtoCar> _listAuto;
	private Paginator<DtoCar> paginator;
	
	protected ListCar() {
		_listAuto = new ArrayList<DtoCar>();
		_daoAuto = new DaoCar();
		paginator  = new PaginatorDao<DtoCar>(_daoAuto);
	}
	
	public static ListCar getInstance() {
		if (_instance == null) {
			_instance = new ListCar();
		}
		return _instance;
	}
	
	@Override
	public List<DtoCar> getList(){
		return _listAuto;
	}
	
	@Override
	public void loadList () throws ClassNotFoundException, SQLException{	
		//_listAuto = paginator.next();
		if(reloadNext()) {
			System.out.println("Se cargó");
		}else {
			System.out.println("No se recargó.");
		}
	}

	private boolean addedCarsInList(List<DtoCar> carsNews) {
		if (carsNews != null) {
			if(carsNews.size() > 0) {
				_listAuto.addAll(carsNews);
				return true;
			}else 
				return false;
		}else 
			return false;
	}
	
	@Override
	public void loadListFilter(String parameter,String value) throws ClassNotFoundException, SQLException{
		_listAuto = _daoAuto.getFilter(parameter, value);
	}
	
	
	@Override
	public void add (DtoCar dtoCar) throws ClassNotFoundException, SQLException{
		int id_added = (int) _daoAuto.add(dtoCar);
		if( id_added != -1 ) {
			dtoCar.setId(id_added);
			_listAuto.add(dtoCar);
		}	
	}
	
	@Override
	public DtoCar getOne(int position) {
		return _listAuto.get(position);
	}
	
	@Override
	public void delete(int position) throws ClassNotFoundException,SQLException{
		if(_daoAuto.delete(_listAuto.get(position).getId()))
			_listAuto.remove(position);
	}
	
	@Override
	public void update (DtoCar dtoCar, int position) throws ClassNotFoundException,SQLException{
		if(_daoAuto.update(dtoCar))
			_listAuto.set(position,dtoCar);
	}
	
	@Override
	public Interator<DtoCar> getAll() {	
		return new DaoInteractor<DtoCar>(_listAuto);
	}
	
	@Override
	public int sizeDtos() {
		return _listAuto.size();
	}

	@Override
	public boolean reloadNext() throws ClassNotFoundException, SQLException {
		return addedCarsInList(paginator.next());
	}
}
