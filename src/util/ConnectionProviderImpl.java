package util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class ConnectionProviderImpl implements ConnectionProvider {

	private static final long serialVersionUID = 1L;

	private final BasicDataSource basicDataSource = new BasicDataSource();

	public ConnectionProviderImpl(String database) {
		try {
			// this should be read from properties file
			basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
			basicDataSource.setUrl("jdbc:mysql://localhost:3306/" + database);
			basicDataSource.setUsername("sapo_admin");
			basicDataSource.setPassword("sapo_admin");
			basicDataSource.setInitialSize(2);
			basicDataSource.setMaxTotal(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isUnwrappableAs(Class arg0) {
		return false;
	}

	@Override
	public <t> t unwrap(Class<t> arg0) {
		return null;
	}

	@Override
	public void closeConnection(Connection arg0) throws SQLException {
		try {
			arg0.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Connection getConnection() throws SQLException {
		Connection conexion = null;
		try {
			conexion = basicDataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conexion;
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

}
