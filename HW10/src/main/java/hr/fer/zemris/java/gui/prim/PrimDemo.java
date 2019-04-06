package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * Program for primes calculation. Output is displayed in the two same sized
 * split windows. At the beginning number 1 is present and then with every click
 * on next button next prime is displayed.
 * 
 * @author Filip Karacic
 *
 */
public class PrimDemo extends JFrame {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Width of the window.
	 */
	private static final int WIDTH = 600;
	/**
	 * Height of the window.
	 */
	private static final int HEIGHT = 450;

	/**
	 * Model for this prime numbers;
	 */
	private PrimListModel model;

	/**
	 * Constructor that initializes values for this frame.
	 */
	public PrimDemo() {
		setSize(WIDTH, HEIGHT);
		setTitle("PRIME NUMBERS");

		model = new PrimListModel();

		initializeGUI();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setVisible(true);
	}

	/**
	 * Initializes GUI, i.e. panel, buttons and lists.
	 */
	private void initializeGUI() {
		JPanel panel = new JPanel(new BorderLayout());

		initializeLists(panel);

		initializeButton(panel);

		add(panel);

	}

	/**
	 * Initializes List that displays the output.
	 * 
	 * @param panel panel on which components are added
	 */
	private void initializeLists(JPanel panel) {
		JPanel listsPanel = new JPanel(new GridLayout(1, 0));
		listsPanel.add(new JScrollPane(new JList<>(model)));
		listsPanel.add(new JScrollPane(new JList<>(model)));

		panel.add(listsPanel);
	}

	/**
	 * Initializes button next.
	 * 
	 * @param panel panel on which components are added
	 */
	private void initializeButton(JPanel panel) {
		JButton next = new JButton("next");

		next.addActionListener(l -> {
			model.next();
		});

		panel.add(next, BorderLayout.PAGE_END);
	}

	/**
	 * Method called when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo();
		});
	}

}
