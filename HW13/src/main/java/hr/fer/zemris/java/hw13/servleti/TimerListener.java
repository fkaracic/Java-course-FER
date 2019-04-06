package hr.fer.zemris.java.hw13.servleti;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Represents servlet context listener that at the start of the application
 * remembers the current time.
 * 
 * @author Filip Karacic
 *
 */
public class TimerListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		long startingTime = System.currentTimeMillis();
		sce.getServletContext().setAttribute("start", startingTime);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
