package hr.fer.zemris.java.hw14.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;

/**
 * Represents helpful class for reading files and getting polls File.
 * 
 * @author Filip Karacic
 *
 */
public class Utils {

	/**
	 * Path of the polls file.
	 */
	private static final Path pollsFile = Paths.get("./src/main/resources/polls.txt");
	/**
	 * Path of the option files
	 */
	private static final Path[] optionFiles = new Path[] { Paths.get("./src/main/resources/bands.txt"),
			Paths.get("./src/main/resources/movies.txt") };

	/**
	 * Returns list of the polls.
	 * 
	 * @return list of the polls.
	 * 
	 * @throws IOException
	 *             if error reading occurs
	 */
	public static List<String> getPolls() throws IOException {
		return Files.readAllLines(pollsFile);
	}

	/**
	 * Returns list of the options on the specified path within the given
	 * optionIndex.
	 * 
	 * @param optionIndex
	 *            index of the path to be used.
	 * 
	 * @return list of the options on the specified path within the given
	 *         optionIndex
	 * 
	 * @throws IOException
	 *             if error reading file occurs
	 */
	public static List<String> getOptions(int optionIndex) throws IOException {
		if (optionIndex < 0 || optionIndex >= optionFiles.length)
			throw new IllegalArgumentException("Invalid option path index.");

		return Files.readAllLines(optionFiles[optionIndex]);
	}

	/**
	 * Creates connection url from property file. Property file contains host, port,
	 * dbName, user and password.
	 * 
	 * @param sce event object for notifications about changes to the servlet context of a web application
	 * 
	 * @return connection url created from property file
	 */
	public static String fromProperty(ServletContextEvent sce) {
		Properties property = new Properties();
		String path = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");

		try {
			property.load(Files.newInputStream(Paths.get(path)));
		} catch (IOException e) {
			throw new IllegalStateException("Cannot read property file", e);
		}

		String host = property.getProperty("host");
		String port = property.getProperty("port");
		String dbName = property.getProperty("name");

		String user = property.getProperty("user");
		String password = property.getProperty("password");

		return "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + user + ";password=" + password;
	}

}