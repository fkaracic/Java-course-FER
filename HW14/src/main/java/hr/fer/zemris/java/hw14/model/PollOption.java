package hr.fer.zemris.java.hw14.model;

import java.util.Objects;

/**
 * Represents poll option for the poll specified with the unique pollID. Option
 * has it's title, link specific for that option, id, id of the poll and number
 * of votes.
 * 
 * @author Filip Karacic
 *
 */
public class PollOption {

	/**
	 * Title of this option.
	 */
	private String title;
	/**
	 * Link specified for this option.
	 */
	private String link;
	/**
	 * ID of this option.
	 */
	private long ID;
	/**
	 * ID of the poll.
	 */
	private long pollID;
	/**
	 * Number of votes.
	 */
	private long votes;

	/**
	 * Initializes newly created {@code PollOption} object representing one of the
	 * options of the poll.
	 * 
	 * @param title title of the option
	 * @param link link for this option
	 * @param ID ID of the option
	 * @param pollID ID of the poll
	 * @param votes number of votes for this option
	 * 
	 * @throws NullPointerException if the given title or link is <code>null</code>
	 */
	public PollOption(String title, String link, long ID, long pollID, long votes) {
		this.title = Objects.requireNonNull(title);
		this.link = Objects.requireNonNull(link);
		this.ID = ID;
		this.pollID = pollID;
		this.votes = votes;
	}

	/**
	 * Returns title of this option.
	 * 
	 * @return title of this option
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns link of this option.
	 * 
	 * @return link of this option
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Returns ID of this option.
	 * 
	 * @return ID of this option
	 */
	public long getID() {
		return ID;
	}

	/**
	 * Returns number of votes for this option.
	 * 
	 * @return title of votes for this option
	 */
	public long getVotes() {
		return votes;
	}

	/**
	 * Returns ID of the poll.
	 * 
	 * @return ID of the poll.
	 */
	public long getPollID() {
		return pollID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ID ^ (ID >>> 32));
		result = prime * result + (int) (pollID ^ (pollID >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PollOption))
			return false;

		PollOption other = (PollOption) obj;

		return ID == other.ID && pollID == other.pollID;
	}

}
