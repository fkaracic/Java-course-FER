package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.utils.Utils;

/**
 * Represents servlet context listener. Performs action whenever application is
 * started or closed.
 * 
 * @author Filip Karacic
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		try {
			String connectionURL = Utils.fromProperty(sce);
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			try {
				cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
			} catch (PropertyVetoException e1) {
				throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
			}

			cpds.setJdbcUrl(connectionURL);

			sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

			DAOProvider.getDao().checkTables(cpds);
		} catch (Exception e) {
			contextDestroyed(sce);
			System.out
					.println("Error initializing application. Verify dbsettings.properties file exists and is valid.");
			System.exit(1);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}