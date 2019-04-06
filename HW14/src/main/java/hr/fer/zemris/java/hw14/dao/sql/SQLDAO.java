package hr.fer.zemris.java.hw14.dao.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;
import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.utils.SQLCommands;
import hr.fer.zemris.java.hw14.utils.Utils;

/**
 * Implementation of the work with the database tables.
 * 
 * @author Filip Karacic
 *
 */
public class SQLDAO implements DAO {

	@Override
	public Poll getPoll(long ID) throws SQLException {
		Connection connection = SQLConnectionProvider.getConnection();

		PreparedStatement pst = null;
		Poll poll = null;
		try {
			pst = connection.prepareStatement("SELECT title, message from Polls WHERE id=" + ID);
			ResultSet rset = pst.executeQuery();
			try {
				while (rset.next()) {
					String title = rset.getString(1);
					String message = rset.getString(2);

					poll = new Poll(ID, title, message);
				}
			} finally {
				rset.close();
			}
		} finally {
			pst.close();
		}

		return poll;
	}

	@Override
	public List<Poll> getPolls() throws SQLException {
		Connection connection = SQLConnectionProvider.getConnection();

		PreparedStatement pst = null;
		List<Poll> polls = new ArrayList<>();

		try {
			pst = connection.prepareStatement("SELECT id, title, message from Polls order by id");
			ResultSet rset = pst.executeQuery();
			try {
				while (rset.next()) {
					long id = rset.getLong(1); 
					String title = rset.getString(2);
					String message = rset.getString(3);

					polls.add(new Poll(id, title, message));
				}
			} finally {
				rset.close();
			}
		} finally {
			pst.close();
		}

		return polls;
	}

	@Override
	public void addVote(long optionID) throws SQLException {
		Connection connection = SQLConnectionProvider.getConnection();

		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement("UPDATE PollOptions SET votesCount= votesCount + 1 WHERE id=" + optionID);
			pst.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

	}

	@Override
	public List<PollOption> getPollOptions(long pollID) throws SQLException {
		Connection connection = SQLConnectionProvider.getConnection();

		PreparedStatement pst = null;
		List<PollOption> pollOptions = new ArrayList<>();

		try {
			pst = connection
					.prepareStatement("SELECT id, optionTitle, optionLink, votesCount FROM PollOptions WHERE pollID="
							+ pollID + " order by id");
			ResultSet rset = pst.executeQuery();
			try {
				while (rset.next()) {
					long id = rset.getLong(1);
					String title = rset.getString(2);
					String link = rset.getString(3);
					long votes = rset.getLong(4);

					pollOptions.add(new PollOption(title, link, id, pollID, votes));
				}
			} finally {
				rset.close();
			}
		} finally {
			pst.close();
		}

		return pollOptions;
	}

	@Override
	public void checkTables(DataSource cpds) throws SQLException {
		Connection connection = cpds.getConnection();
		DatabaseMetaData dbm = connection.getMetaData();
		ResultSet pollsTable = dbm.getTables(null, null, "POLLS", null);
		ResultSet pollOptionsTable = dbm.getTables(null, null, "POLLOPTIONS", null);

		if (!pollsTable.next()) {
			String createTableSQL = SQLCommands.createPollsTable;

			createTable(connection, createTableSQL);
		}

		if (!pollOptionsTable.next()) {
			String createTableSQL = SQLCommands.createPollOptionsTable;
			createTable(connection, createTableSQL);
		}

		if (isPollsEmpty(connection)) {
			insertIntoTables(connection);
		}
	}

	/**
	 * Returns <code>true</code> if table is empty, false otherwise.
	 * 
	 * @param connection connection to the database
	 * 
	 * @return <code>true</code> if table is empty, false otherwise
	 * 
	 * @throws SQLException if error checking occurs.
	 */
	private boolean isPollsEmpty(Connection connection) throws SQLException {
		try (PreparedStatement pollsStatement = connection.prepareStatement("SELECT COUNT(*) AS NUMBER FROM POLLS",
				Statement.RETURN_GENERATED_KEYS)) {
			ResultSet result = pollsStatement.executeQuery();
			result.next();
			int numberOfInputs = result.getInt("NUMBER");
			return numberOfInputs == 0;
		}
	}

	/**
	 * Inserts into the table
	 * 
	 * @param connection connection to the database.
	 */
	private void insertIntoTables(Connection connection) {

		String insertPoll = "INSERT INTO polls (title, message) values (?,?)";
		String insertPollOption = "INSERT INTO pollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)";

		List<String> pollsLines = null;
		List<String> pollOptionsLines = null;

		try {
			pollsLines = Utils.getPolls();
		} catch (IOException e) {
			return;
		}

		try (PreparedStatement pollsStatement = connection.prepareStatement(insertPoll,
				Statement.RETURN_GENERATED_KEYS)) {

			int i = 0;
			for (String pollsLine : pollsLines) {
				String[] polls = pollsLine.split("\t");
				pollsStatement.setString(1, polls[0]);
				pollsStatement.setString(2, polls[1]);

				pollsStatement.executeUpdate();

				ResultSet rset = pollsStatement.getGeneratedKeys();
				
				try {
					if (rset != null && rset.next()) {
						long ID = rset.getLong(1);
						pollOptionsLines = Utils.getOptions(i++);
						for (String optionsLine : pollOptionsLines) {
							try (PreparedStatement pollOptionsStatement = connection.prepareStatement(insertPollOption,
									Statement.RETURN_GENERATED_KEYS)) {

								String[] options = optionsLine.split("\t");
								pollOptionsStatement.setString(1, options[0]);
								pollOptionsStatement.setString(2, options[1]);
								pollOptionsStatement.setLong(3, ID);
								pollOptionsStatement.setLong(4, 0L);

								pollOptionsStatement.executeUpdate();
							}
						}

					}
				} catch (IOException e) {
				} finally {
					rset.close();
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Creates table according to the given SQL.
	 * 
	 * @param connection connection to the database
	 * @param SQL sql statement
	 * 
	 * @throws SQLException if error obtaining table info occurrs
	 */
	private void createTable(Connection connection, String SQL) throws SQLException {
		
		try (PreparedStatement statement = connection.prepareStatement(SQL)) {
			statement.executeUpdate();
		}
	}
}