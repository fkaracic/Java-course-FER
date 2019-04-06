package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.dao.sql.SQLDAO;

/**
 * Represents class that know what to return as a subsystem access provider.
 * 
 * @author Filip Karacic
 *
 */
public class DAOProvider {

	/**
	 * Used for reaching and updating table inputs.
	 */
	private static DAO dao = new SQLDAO();

	/**
	 * Returns object that encapsulates acces of the layer for the persistance of the data.
	 * 
	 * @return object that encapsulates acces of the layer for the persistance of the data
	 * 
	 */
	public static DAO getDao() {
		return dao;
	}

}