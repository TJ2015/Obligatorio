package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class ConnectionProviderImpl implements ConnectionProvider {
	
	private final BasicDataSource basicDataSource = new BasicDataSource();
	
	private static Map<String, String> ENV;
	private static String MYSQL_HOST;
	private static String MYSQL_PORT;
	
	public ConnectionProviderImpl(String database){
		
		if( ENV == null )
			ENV = System.getenv();
		
		if( MYSQL_HOST == null )
			MYSQL_HOST = ENV.get("OPENSHIFT_MYSQL_DB_HOST");
		
		if( MYSQL_PORT == null )
			MYSQL_PORT = ENV.get("OPENSHIFT_MYSQL_DB_PORT");
				
		//this should be read from properties file		
		//basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		//basicDataSource.setUrl("jdbc:mysql://localhost:3306/"+database);
		basicDataSource.setUrl("jdbc:mysql://" + MYSQL_HOST + ":" + MYSQL_PORT + "/" + database); // es para la nube
		basicDataSource.setUsername("sapo_admin");
		basicDataSource.setPassword("sapo_admin");
		basicDataSource.setInitialSize(1);
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
