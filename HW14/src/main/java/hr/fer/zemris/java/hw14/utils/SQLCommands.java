package hr.fer.zemris.java.hw14.utils;

/**
 * Represents sql command until rain stops
 * 
 * @author Filip Karacic
 *
 */
public class SQLCommands {

	/**
	 * Command for the creation of the Pools table;
	 */
	public static final String createPollsTable = "CREATE TABLE Polls" + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
			+ "title VARCHAR(150) NOT NULL," + "message CLOB(2048) NOT NULL" + ")";
	
	
	/**
	 * Command for the creation of the PoolOptions table;
	 */
	public static final String createPollOptionsTable = "CREATE TABLE PollOptions" + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
			+ "optionTitle VARCHAR(100) NOT NULL," + "optionLink VARCHAR(150) NOT NULL," + "pollID BIGINT,"
			+ "votesCount BIGINT," + "FOREIGN KEY (pollID) REFERENCES Polls(id)" + ")";
}
