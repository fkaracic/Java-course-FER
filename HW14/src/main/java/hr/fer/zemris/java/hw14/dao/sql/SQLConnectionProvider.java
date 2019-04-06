package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;

/**
 * Storage of the connections within database in ThreadLocal moment.
 * 
 * Pohrana veza prema bazi podataka u ThreadLocal object. ThreadLocal je zapravo
 * mapa čiji su ključevi identifikator dretve koji radi operaciju nad mapom.
 * 
 * @author Filip Karačić
 *
 */
public class SQLConnectionProvider {

	/**
	 * Thread-local variables provider.
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Set connection for the current thread (or delete record from the map if arguments is <code>null</code>).
	 * 
	 * @param con veza prema bazi
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Get connection which current thread can use.
	 * 
	 * @return connection to the database
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}