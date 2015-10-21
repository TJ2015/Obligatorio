package util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class ConnectionProviderImpl implements ConnectionProvider {
	
	private final BasicDataSource basicDataSource = new BasicDataSource();
	
	public ConnectionProviderImpl(String database){
		//this should be read from properties file		
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUrl("jdbc:mysql://localhost:3306/"+database);
		basicDataSource.setUsername("root");
		basicDataSource.setPassword("");
		basicDataSource.setInitialSize(2);
		basicDataSource.setMaxTotal(10);
	}
	
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
		arg0.close();
	}
	@Override
	public Connection getConnection() throws SQLException {
		return basicDataSource.getConnection();
	}
	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

}
