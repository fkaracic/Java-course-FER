package hr.fer.zemris.java.hw14.dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Interface {@code DAO} is used for reaching or updating values from the tables
 * in the database.
 * 
 * @author Filip Karacic
 *
 */
public interface DAO {

	/**
	 * Returns poll with the specified ID.
	 * 
	 * @param ID
	 *            ID of the poll.
	 * 
	 * @return poll with the specified ID
	 * 
	 * @throws SQLException
	 *             if error obtaining poll occurs
	 */
	Poll getPoll(long ID) throws SQLException;

	/**
	 * Check if tables Polls and PollOptions exist and if they are not empty. If
	 * they do not exist or if they are empty, they will be created.
	 * 
	 * @param cpds source of the data
	 * 
	 * @throws SQLException
	 *             if error creating poll occurs
	 */
	void checkTables(DataSource cpds) throws SQLException;

	/**
	 * Returns options for the specified pollID.
	 * 
	 * @param pollID ID of the poll
	 * 
	 * @return options for the specified pollID
	 * 
	 * @throws SQLException if error obtaining poll options occurs.
	 */
	List<PollOption> getPollOptions(long pollID) throws SQLException;

	/**
	 * Return all polls in the Polls table
	 * 
	 * @return all polls in the Polls table
	 * 
	 * @throws SQLException if error getting polls occurs
	 */
	public List<Poll> getPolls() throws SQLException;

	/**
	 * Adds one vote to the option with the given ID.
	 * 
	 * @param optionID ID of the selected option
	 * 
	 * @throws SQLException if error voting occurs
	 */
	public void addVote(long optionID) throws SQLException;
}