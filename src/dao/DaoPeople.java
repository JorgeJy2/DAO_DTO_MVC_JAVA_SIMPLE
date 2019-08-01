package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.PoolConnection;
import model.dto.DtoPeople;

public class DaoPeople implements DaoInterface<DtoPeople> {

	private static final String _ADD = "INSERT INTO persona (nombre,apellido_paterno,apellido_materno,telefono,correo) VALUES (?,?,?,?,?) RETURNING id";
	private static final String _GET_ONE = "SELECT id,nombre,apellido_paterno,apellido_materno,telefono,correo FROM persona WHERE id=?";
	private static final String _GET_ALL = "SELECT id,nombre,apellido_paterno,apellido_materno,telefono,correo FROM persona ";
	
	
	private static final String _SELECT_BASE = "SELECT id,nombre,apellido_paterno,apellido_materno,telefono,correo FROM persona ";
	
	
	private static final String _START = " OFFSET ";
	private static final String _LIMIT = " LIMIT ";
	
	private static final String _DELETE = "DELETE FROM persona WHERE id=?";
	private static final String _UPDATE = "UPDATE persona SET nombre=?,apellido_paterno=?,apellido_materno=?,telefono=?,correo=? WHERE id= ?";

	// M�todos implementados de la interface DaoInterface
	@Override
	public Object add(DtoPeople dto) throws SQLException, ClassNotFoundException {
		Connection connectionPostgresql = PoolConnection.getInstancePool().getConnectionToPoll();
		PreparedStatement preparedStatement = connectionPostgresql.prepareStatement(_ADD);
		preparedStatement.setString(1, dto.getName());
		preparedStatement.setString(2, dto.getFirstName());
		preparedStatement.setString(3, dto.getLastName());
		preparedStatement.setString(4, dto.getTelephone());
		preparedStatement.setString(5, dto.getEmail());

		ResultSet result = preparedStatement.executeQuery();

		int idResult = -1;

		while (result.next()) {
			idResult = result.getInt(1);
		}

		result.close();
		preparedStatement.close();
		connectionPostgresql.close();
		return idResult;
	}

	@Override
	public boolean update(DtoPeople dto) throws SQLException, ClassNotFoundException {
		Connection connectionPostgresql = PoolConnection.getInstancePool().getConnectionToPoll();
		PreparedStatement preparedStatement = connectionPostgresql.prepareStatement(_UPDATE);

		preparedStatement.setString(1, dto.getName());
		preparedStatement.setString(2, dto.getFirstName());
		preparedStatement.setString(3, dto.getLastName());
		preparedStatement.setString(4, dto.getTelephone());
		preparedStatement.setString(5, dto.getEmail());
		preparedStatement.setInt(6, dto.getId());

		int tuplasChange = preparedStatement.executeUpdate();
		preparedStatement.close();
		connectionPostgresql.close();

		return (tuplasChange > 0);
	}

	@Override
	public boolean delete(Object key) throws SQLException, ClassNotFoundException {
		Connection connectionPostgresql = PoolConnection.getInstancePool().getConnectionToPoll();
		PreparedStatement preparedStatement = connectionPostgresql.prepareStatement(_DELETE);

		preparedStatement.setInt(1, (int) key);

		int result = preparedStatement.executeUpdate();
		preparedStatement.close();
		connectionPostgresql.close();

		return (result > 0);
	}

	@Override
	public DtoPeople get(Object key) throws SQLException, ClassNotFoundException {
		Connection connectionPostgresql = PoolConnection.getInstancePool().getConnectionToPoll();
		PreparedStatement preparedStatement = connectionPostgresql.prepareStatement(_GET_ONE);

		preparedStatement.setInt(1, (int) key);
		
		ResultSet resultSet = preparedStatement.executeQuery();

		DtoPeople dtoPeople = new DtoPeople();

		while (resultSet.next()) {
			dtoPeople.setName(resultSet.getString(1));
			dtoPeople.setFirstName(resultSet.getString(2));
			dtoPeople.setLastName(resultSet.getString(3));
			dtoPeople.setTelephone(resultSet.getString(4));
			dtoPeople.setEmail(resultSet.getString(5));
		}

		resultSet.close();
		preparedStatement.close();
		connectionPostgresql.close();
		return dtoPeople;
	}

	@Override
	public List<DtoPeople> getAll() throws SQLException, ClassNotFoundException {
		Connection connectionPostgresql = PoolConnection.getInstancePool().getConnectionToPoll();
		PreparedStatement preparedStatement = connectionPostgresql.prepareStatement(_GET_ALL);

		ResultSet tableResultSet = preparedStatement.executeQuery();

		DtoPeople dtoPeople;

		List<DtoPeople> listPeople = new ArrayList<DtoPeople>();

		while (tableResultSet.next()) {
			
			dtoPeople = new DtoPeople();
			
			dtoPeople.setId(tableResultSet.getInt(1));
			dtoPeople.setName(tableResultSet.getString(2));
			dtoPeople.setFirstName(tableResultSet.getString(3));
			dtoPeople.setLastName(tableResultSet.getString(4));
			dtoPeople.setTelephone(tableResultSet.getString(5));
			dtoPeople.setEmail(tableResultSet.getString(6));
			
			listPeople.add(dtoPeople);
		}

		tableResultSet.close();
		preparedStatement.close();
		connectionPostgresql.close();

		return listPeople;
	}

	@Override
	public List<DtoPeople> getPaginator(int init, int end) throws SQLException, ClassNotFoundException {
		Connection connectionPostgresql = PoolConnection.getInstancePool().getConnectionToPoll();
		
		PreparedStatement preparedStatement = connectionPostgresql.prepareStatement(_SELECT_BASE +" ORDER BY id "+ _LIMIT + end + _START + init);
		
		ResultSet tableResultSet = preparedStatement.executeQuery();

		DtoPeople dtoPeople;

		List<DtoPeople> listPeople = new ArrayList<DtoPeople>();

		while (tableResultSet.next()) {
			
			dtoPeople = new DtoPeople();
			
			dtoPeople.setId(tableResultSet.getInt(1));
			dtoPeople.setName(tableResultSet.getString(2));
			dtoPeople.setFirstName(tableResultSet.getString(3));
			dtoPeople.setLastName(tableResultSet.getString(4));
			dtoPeople.setTelephone(tableResultSet.getString(5));
			dtoPeople.setEmail(tableResultSet.getString(6));
			
			listPeople.add(dtoPeople);
		}

		tableResultSet.close();
		preparedStatement.close();
		connectionPostgresql.close();

		return listPeople;
	}
	
	@Override
	public List<DtoPeople> getFilter(String parameter, String value) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}


