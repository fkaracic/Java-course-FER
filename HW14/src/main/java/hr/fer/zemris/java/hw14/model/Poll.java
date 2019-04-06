package hr.fer.zemris.java.hw14.model;

import java.util.Objects;

/**
 * Represents a poll that has unique id, title and message.
 * 
 * @author Filip Karacic
 *
 */
public class Poll {

	/**
	 * Unique id for this poll.
	 */
	private long id;
	/**
	 * Title of the poll.
	 */
	private String title;
	/**
	 * Message of the poll.
	 */
	private String message;

	/**
	 * Constructor for the newly created Poll object.
	 * 
	 * @param id unique id
	 * @param title title of the poll
	 * @param message message of the poll
	 */
	public Poll(long id, String title, String message) {
		this.id = id;
		this.title = Objects.requireNonNull(title);
		this.message = Objects.requireNonNull(message);
	}

	/**
	 * Returns this poll's id.
	 * 
	 * @return this poll's id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Returns this poll's title.
	 * 
	 * @return this poll's title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns this poll's message.
	 * 
	 * @return this poll's message
	 */
	public String getMessage() {
		return message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Poll))
			return false;

		Poll other = (Poll) obj;
		
		return id != other.id;
	}

}
