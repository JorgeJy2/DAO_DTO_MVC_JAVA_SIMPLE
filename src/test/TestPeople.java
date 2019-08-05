package test;

import java.sql.SQLException;

import model.dto.DtoCar;
import model.dto.DtoPeople;
import model.list.ListPeople;
import model.list.interador.Interator;

/**
 * Archivo: TestPeople.java contiene la definici�n de la clase TestPeople.
 * 
 * @author Jorge Jacobo, Marcos Moreno, Gabriel Garcia, Amanda Franco
 * @version 1.0
 *
 */
public class TestPeople {
	// declaraci�n de atributo
	private ListPeople listPeople;

	// constructor sin par�metro
	public TestPeople() {
		listPeople = ListPeople.getInstance();
	}

	// m�todo selectTest
	public void selectTest() {
		try {
			listPeople.loadList();
			Interator<DtoPeople> interator = listPeople.getAll();
			while (interator.hasNext()) {
				DtoPeople car = interator.next();
				System.out.println(car);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Ocurrió un error en test de listPeople, al momento de consultar.");
			System.err.println(e.getLocalizedMessage());
		}
	}// Cierre m�todo selectTest

	// m�todo addTest
	public void addTest() {
		try {
			DtoPeople people = new DtoPeople();
			people.setName("Jacobo");
			people.setEmail("josdsj@gmail.com");
			listPeople.add(people);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Ocurrió un error en test de listPeople, al momento de agregar.");
			System.err.println(e.getLocalizedMessage());
		}
	}// cierre m�todo addTest

	// m�todo updateTest
	public void updateTest() {
		try {
			Interator<DtoPeople> interator = listPeople.getAll();
			DtoPeople people = interator.first();
			if (people != null) {
				people.setName("Otro nombre");
				listPeople.update(people, 0);
			} else
				System.out.println("Lista de coches vacia.");

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Ocurrió un error en test de listPeople, al momento de actualizar.");
			System.err.println(e.getLocalizedMessage());
		}
	}// cierre m�todo updateTest

	// m�todo delteTest
	public void delteTest() {
		try {
			Interator<DtoPeople> interator = listPeople.getAll();
			DtoPeople car = interator.first();
			if (car != null) {
				listPeople.delete(0);
			} else
				System.out.println("Lista de coches vacia.");

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Ocurrió un error en test de listPeople, al momento de eliminar.");
			System.err.println(e.getLocalizedMessage());
		}
	}// cierre m�todo delteTest

	/**
	 * M�todo getFirst
	 * 
	 * @return retorna un objeto de tipo DtoPeople
	 */
	public DtoPeople getFirst() {

		try {
			listPeople.loadList();
			Interator<DtoPeople> interator = listPeople.getAll();
			return interator.first();

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Ocurrió un error en test de listCar, al momento de eliminar.");
			System.err.println(e.getLocalizedMessage());
			return null;
		}
	}// cierre m�todo getFirst
}// cierre clase TestPeople
