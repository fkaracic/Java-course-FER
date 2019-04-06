package hr.fer.zemris.java.namebuilder;

/**
 * Interface {@code NameBuilderInfo} is used to obtain informations about the
 * name builder.
 * 
 * @author Filip Karacic
 *
 */
public interface NameBuilderInfo {

	/**
	 * Returns {@code StringBuilder} object for this name builder info provider
	 * 
	 * @return {@code StringBuilder} object for this name builder info provider
	 */
	StringBuilder getStringBuilder();

	/**
	 * Returns group at the specified index. Group zero denotes the entire pattern.
	 * 
	 * @param index
	 *            group index
	 * @return group at the specified index represented by {@code String}
	 * 
	 * @throws IllegalStateException
	 *             - If no match has yet been attempted, or if the previous match
	 *             operation failed
	 * @throws IndexOutOfBoundsException
	 *             - If there is no capturing group in the pattern with the given
	 *             index
	 */
	String getGroup(int index);

}
