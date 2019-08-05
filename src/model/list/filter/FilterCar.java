package model.list.filter;

import java.util.List;
import java.util.stream.Collectors;

import model.dto.DtoCar;
import model.list.ListCar;

/**
 * Archivo: FilterCar.java contiene la definici�n de la clase FilterCar que
 * extiende de ListCar.
 * 
 * @author Jorge Jacobo, Marcos Moreno, Gabriel Garcia, Amanda Franco
 * @version 1.0
 *
 */
public class FilterCar extends ListCar {
	// declaraci�n de atributo
	private String value;

	/**
	 * Contructor con par�metro
	 * 
	 * @param value valor de tipo String
	 */
	public FilterCar(String value) {
		this.value = value;
	}// cierre constructor

	/**
	 * M�todo getList
	 * 
	 * @return retorna un objeto de tipo List
	 */
	@Override
	public List<DtoCar> getList() {
		return super.getList().stream().filter((DtoCar car) -> {
			return car.getPlaca().equals(value);
		}).collect(Collectors.toList());
	}// cierre m�todo getList
}// Cierre clase FilterCar
