package hr.fer.zemris.java.namebuilder;

/**
 * Interface {@code NameBuilder} represents name builder and has method which
 * executes operation over the given info provider.
 * 
 * @author Filip Karacic
 *
 */
public interface NameBuilder {

	/**
	 * Performs action over the given name builder.
	 * 
	 * @param info
	 *            info about the given name builder
	 */
	void execute(NameBuilderInfo info);

}
