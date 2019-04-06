package hr.fer.zemris.java.hw07.shell;

/**
 * Status of the shell. CONTINUE OR TERMINATE. Shell has working status
 * (CONTINUE) until exit command terminates program (change status to
 * TERMINATE).
 * 
 * @author Filip Karacic
 */
public enum ShellStatus {

	/**
	 * Continues work.
	 */
	CONTINUE,

	/**
	 * Terminates work.
	 */
	TERMINATE;
}
