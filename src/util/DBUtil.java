package util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public final class DBUtil {

	private static SessionFactory factory = null;
	
	public static Session crearSession(String tenant) 
	{
		Session session = null;
		try {
			/*************************************************************************/
			/*************************** BRYAN ***************************************/
			if (factory == null || factory.isClosed()) {
				factory = new Configuration().configure().buildSessionFactory();
			}
			session = factory.withOptions().tenantIdentifier("sapo_" + tenant).openSession();
			
			/*************************** BRYAN ***************************************/
			/*************************************************************************/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}
	
	public static void cerrarFabrica()
	{
		try {
			if (factory != null && !factory.isClosed()) {
				factory.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void crearBase(String nombre) {
		try {
			ConnectionProvider conProv = new ConnectionProviderImpl("");
			conProv.getConnection().createStatement().execute("CREATE DATABASE IF NOT EXISTS " + nombre);
			conProv.getConnection().close();
			try {
				List<String> queries = getQueriesFromSQLFile("META-INF/tablas.sql");
				int count = 0;
				for (String q : queries) {
					System.out.println("Ejecutando Query: " + q);
					if (count % 4 == 0)
						conProv = new ConnectionProviderImpl(nombre);

					conProv.getConnection().createStatement().execute(q);
					conProv.getConnection().close();
					count++;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			throw new HibernateException("MultiTenantConnectionProvider::Could not alter JDBC connection to specified schema [" + nombre+ "]",e);
		}
	}

	public static void modificarBase(String nombre) {
		try {

			System.out.println("Modificando clave `av_usuarioscompartidos`...");

			ConnectionProvider conProv = new ConnectionProviderImpl(nombre);
			conProv.getConnection().createStatement().execute(
					"ALTER TABLE `av_usuarioscompartidos` ADD KEY `FK_usucomp` (`usuariosCompartidos_idUsuario`) USING BTREE;");
			conProv.getConnection().close();

			conProv = new ConnectionProviderImpl(nombre);
			conProv.getConnection().createStatement()
					.execute("ALTER TABLE `av_usuarioscompartidos` DROP KEY `UK_6fs67215r98r91xul672y3a8k`;");
			conProv.getConnection().close();

			conProv = new ConnectionProviderImpl(nombre);
			conProv.getConnection().createStatement().execute(
					"ALTER TABLE `usuario_avcompartidos` ADD KEY `FK_usucomp` (`AVcompartidos_idAV`) USING BTREE;");
			conProv.getConnection().close();

			conProv = new ConnectionProviderImpl(nombre);
			conProv.getConnection().createStatement()
					.execute("ALTER TABLE `usuario_avcompartidos` DROP KEY `UK_31aoc7x2q3fbs7vhqawyw82sm`;");
			conProv.getConnection().close();

			System.out.println("clave modificada con éxito");

		} catch (SQLException e) {
			throw new HibernateException("MultiTenantConnectionProvider::Could not alter JDBC connection to specified schema [" + nombre+ "]", e);
		}
	}

	public static List<String> getQueriesFromSQLFile(String url) throws FileNotFoundException {
		List<String> queries = null;
		try {
			InputStream in = DBUtil.class.getClassLoader().getResourceAsStream(url);
			Scanner input;
			List<String> lineas = new ArrayList<>();
			queries = new ArrayList<>();

			input = new Scanner(in, "utf-8");

			while (input.hasNext()) {
				// or to process line by line
				lineas.add(input.nextLine());
			}

			String aux = "";
			boolean comentario = false;

			for (String l : lineas) {
				if (!l.startsWith("--") && (!comentario)) {
					if (!l.startsWith("/*") || (l.startsWith("/*") && l.contains("40101 SET NAMES"))) {
						aux += l;
						if (l.endsWith(";")) {
							queries.add(aux);
							aux = "";
						}
					} else {
						if (!l.endsWith("*/") && !l.endsWith("*/;")) {
							comentario = true;
						}
					}
				} else if (l.endsWith("*/") || l.endsWith("*/;")) {
					comentario = false;
				}
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return queries;
	}

	public static void eliminarTenant(String tenant) {
		try {
			ConnectionProvider conProv = new ConnectionProviderImpl("");
			conProv.getConnection().createStatement().execute("DROP DATABASE sapo_" + tenant);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
