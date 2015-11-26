package util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class ConnectionProviderImpl implements ConnectionProvider {
	
	private final BasicDataSource basicDataSource = new BasicDataSource();
	
	public ConnectionProviderImpl(String database){
		//this should be read from properties file		
		//basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		//basicDataSource.setUrl("jdbc:mysql://localhost:3306/"+database);
		basicDataSource.setUrl("jdbc:mysql://56564d530c1e66dcfb00009e-obligatorio.rhcloud.com:64886/"+database);// es para la nube
		basicDataSource.setUsername("sapo_admin");
		basicDataSource.setPassword("sapo_admin");
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
