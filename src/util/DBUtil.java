package util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class DBUtil {

	public static void crearBase(String nombre) {
		ConnectionProvider conProv = new ConnectionProviderImpl("");
		try {
			conProv.getConnection().createStatement().execute( "CREATE DATABASE IF NOT EXISTS " + nombre );
			try {
				List<String> queries = getQueriesFromSQLFile("META-INF/tablas.sql");
				
				for( String q : queries ) {
					System.out.println("Ejecutando Query: " + q);
					conProv = new ConnectionProviderImpl(nombre);
					conProv.getConnection().createStatement().execute(q);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch ( SQLException e ) {
			throw new HibernateException(
					"MultiTenantConnectionProvider::Could not alter JDBC connection to specified schema [" + nombre + "]",e);
		}
	}
	
	public static List<String> getQueriesFromSQLFile(String url) throws FileNotFoundException {

		InputStream in = DBUtil.class.getClassLoader().getResourceAsStream(url);
		Scanner input;
		List<String> lineas = new ArrayList<>();
		List<String> queries = new ArrayList<>();

		input = new Scanner(in, "utf-8");
		
		while(input.hasNext()) {
		    //or to process line by line
		    lineas.add(input.nextLine());
		}
		
		String aux = "";
		String auxCom = "";
		boolean comentario = false;
		
		for(String l : lineas) {
			if(!l.startsWith("--") && (!comentario)) {
				if( !l.startsWith("/*") || (l.startsWith("/*") && l.contains("40101 SET NAMES") ) ) {
					aux += l;
					if(l.endsWith(";")) {
						queries.add(aux);
						aux = "";
					}
				} else {
					if( !l.endsWith("*/") && !l.endsWith("*/;") ) {
						comentario = true;
					}
				}
			} else if(l.endsWith("*/") || l.endsWith("*/;")) {
				comentario = false;
			}
		}
		input.close();
		
		return queries;
	}
	
}
