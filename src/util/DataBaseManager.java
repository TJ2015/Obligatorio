package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class DataBaseManager {
	
	static public void crearBaseDeDatos(String nombre, String url) throws Exception {
		Connection connection;
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://" + url + "/?user=root");
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + nombre);
	}
	
	static public boolean setupTenant(String PU, String idTenant, String url) {
		return setupTenant(PU, idTenant, url, 3306);
	}
	
	static public boolean setupTenant(String PU, String idTenant, String url, int port) {
		/*
		try {
			crearBaseDeDatos(idTenant, url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		Map properties = new HashMap<>();
		properties.put("javax.persistence.jdbc.url", "jdbc:mysql://" + url + ":" + port + "/" + idTenant);
		Persistence.generateSchema("MultiTeanantTest", properties);
		
		*/
		Map<String, String> properties = new HashMap<>();
		
		properties.put("connection.url", "jdbc:mysql://localhost/caca");
		EntityManager em = Persistence.createEntityManagerFactory("Obligatorio", properties).createEntityManager();		
		
		return true;
	}

}
