package com.apps.quantitymeasurement.repository;

import java.util.List;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.DatabaseException;
import com.apps.quantitymeasurement.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementDatabaseRepository.class.getName());

	private static final String INSERT_SQL = "INSERT INTO quantity_measurement_entity ("
			+ "this_value, this_unit, this_measurement_type," + "that_value, that_unit, that_measurement_type,"
			+ "operation, result_value, result_unit, result_measurement_type,"
			+ "result_string, is_error, error_message" + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SELECT_ALL_SQL = "SELECT * FROM quantity_measurement_entity ORDER BY created_at DESC";

	private static final String DELETE_ALL_SQL = "DELETE FROM quantity_measurement_entity";

	private static QuantityMeasurementDatabaseRepository instance;

	private final ConnectionPool connectionPool;

	private QuantityMeasurementDatabaseRepository() {
		this.connectionPool = ConnectionPool.getInstance();
		logger.info("QuantityMeasurementDatabaseRepository initialized");
	}

	public static synchronized QuantityMeasurementDatabaseRepository getInstance() {

		if (instance == null) {
			instance = new QuantityMeasurementDatabaseRepository();
		}

		return instance;
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {

		try (Connection conn = connectionPool.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

			ps.setDouble(1, entity.thisValue);
			ps.setString(2, entity.thisUnit);
			ps.setString(3, entity.thisMeasurementType);

			ps.setObject(4, entity.thatValue, Types.DOUBLE);
			ps.setString(5, entity.thatUnit);
			ps.setString(6, entity.thatMeasurementType);

			ps.setString(7, entity.operation);

			ps.setObject(8, entity.resultValue, Types.DOUBLE);
			ps.setString(9, entity.resultUnit);
			ps.setString(10, entity.resultMeasurementType);

			ps.setString(11, entity.resultString);
			ps.setBoolean(12, entity.isError);
			ps.setString(13, entity.errorMessage);

			ps.executeUpdate();

		} catch (SQLException e) {

			throw new DatabaseException("INSERT quantity_measurement_entity failed", e);

		}

	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {

		List<QuantityMeasurementEntity> list = new ArrayList<>();

		try (Connection conn = connectionPool.getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				QuantityMeasurementEntity entity = new QuantityMeasurementEntity(null, null, null);

				entity.thisValue = rs.getDouble("this_value");
				entity.thisUnit = rs.getString("this_unit");
				entity.thisMeasurementType = rs.getString("this_measurement_type");

				entity.thatValue = rs.getDouble("that_value");
				entity.thatUnit = rs.getString("that_unit");
				entity.thatMeasurementType = rs.getString("that_measurement_type");

				entity.operation = rs.getString("operation");

				entity.resultValue = rs.getDouble("result_value");
				entity.resultUnit = rs.getString("result_unit");
				entity.resultMeasurementType = rs.getString("result_measurement_type");

				entity.resultString = rs.getString("result_string");
				entity.isError = rs.getBoolean("is_error");
				entity.errorMessage = rs.getString("error_message");

				list.add(entity);

			}

		} catch (SQLException e) {

			throw new DatabaseException("SELECT quantity_measurement_entity failed", e);

		}

		logger.info("Loaded " + list.size() + " records from DB");

		return list;
	}

	public void deleteAll() {

		try (Connection conn = connectionPool.getConnection();
				PreparedStatement ps = conn.prepareStatement(DELETE_ALL_SQL)) {

			ps.executeUpdate();

		} catch (SQLException e) {

			throw new DatabaseException("DELETE quantity_measurement_entity failed", e);

		}

	}
}