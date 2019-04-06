package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw11.Clock;
import hr.fer.zemris.java.hw11.Util;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseTabAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CreateNewDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CutAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.LanguageAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SortAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.StatisticalInfoAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.UniqueAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LJStatusBarLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LJToolBar;

/**
 * Represents simple text editor. It provides usual method for work with the
 * text (open new file, open file, save, save as, cut, copy, paste...). It has
 * support for three languages: English, Croatian and German. It also provides
 * some statistical information(e.g. about text length, non-blank
 * characters...). Ascending and descending lines sort is available, and also
 * identical lines removal where only first occurence is retained. If mouse is
 * retained for few seconds on one of the tools, a short description is
 * displayed. When program is first started new blank document is opened.
 * 
 * <p>
 * Every tab that contains document has a little icon representing modification
 * indicator. Green disk represents not modified and red represents modified.
 * 
 * 
 * @author Filip Karacic
 *
 */
public class JNotepadPP extends JFrame {
	/**
	 * Localization provider.
	 */
	private ILocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

	/**
	 * Model of zero, one or more documents for this program.
	 */
	private MultipleDocumentModel model = new DefaultMultipleDocumentModel();

	/**
	 * Date and time displayer.
	 */
	private Clock clock = new Clock();

	/**
	 * Action of saving document.
	 */
	public final Action saveDocumentAction = new SaveDocumentAction(model, this, flp);

	/**
	 * Action of saving document as the selected.
	 */
	public final Action saveAsDocumentAction = new SaveAsDocumentAction(model, this, flp);

	/**
	 * Action of closing tab.
	 */
	public final Action closeTabAction = new CloseTabAction(model, this, flp);

	/**
	 * Action of closing program.
	 */
	public final Action exitAction = new ExitAction(model, this, clock, flp);

	/**
	 * Action of creating new blank document.
	 */
	public final Action createNewDocumentAction = new CreateNewDocumentAction(model, this, flp);

	/**
	 * Action of opening selected document.
	 */
	public final Action openDocumentAction = new OpenDocumentAction(model, this, flp);

	/**
	 * Action of displaying statistical info.
	 */
	public final Action statisticalInfoAction = new StatisticalInfoAction(model, this, flp);

	/**
	 * Action of cutting selected text.
	 */
	public final Action cutAction = new CutAction(model, this, flp);

	/**
	 * Action of copying selected text.
	 */
	public final Action copyAction = new CopyAction(model, this, flp);

	/**
	 * Action of pasting text.
	 */
	public final Action pasteAction = new PasteAction(model, this, flp);

	/**
	 * Action of removing unique lines.
	 */
	public final Action uniqueAction = new UniqueAction(model, this, flp);

	/**
	 * Action of lowering case of selected text.
	 */
	public final Action toLowerAction = new CaseAction("to_lowercase", "lower_descr", "control L",
			"to_lowercase_mnemonic", str -> str.toLowerCase(), model, this, flp);
	/**
	 * Action of uppering case of selected text.
	 */
	public final Action toUpperAction = new CaseAction("to_uppercase", "upper_descr", "control U",
			"to_uppercase_mnemonic", str -> str.toUpperCase(), model, this, flp);

	/**
	 * Action of inverting case of selected text.
	 */
	public final Action invertAction = new CaseAction("invert_case", "invert_descr", "control I",
			"invert_case_mnemonic", str -> Util.reverseCase(str), model, this, flp);

	/**
	 * Action of sorting selected lines in descending order.
	 */
	public final Action descendingAction = new SortAction("descending", "alt F3", "desc_descr", "descending_mnemonic", model, this, flp);

	/**
	 * Action of sorting selected lines in ascending order.
	 */
	public final Action ascendingAction = new SortAction("ascending", "alt F2", "asc_descr", "ascending_mnemonic", model, this, flp);

	/**
	 * Action of displaying program in Croatian.
	 */
	public final Action hrLanguageAction = new LanguageAction("hr", "croatian", flp);

	/**
	 * Action of displaying program in English.
	 */
	public final Action enLanguageAction = new LanguageAction("en", "english", flp);

	/**
	 * Action of displaying program in German.
	 */
	public final Action deLanguageAction = new LanguageAction("de", "german", flp);

	/**
	 * Statusbar of this program.
	 */
	private JPanel statusBar;

	/**
	 * Labels of the statusbar.
	 */
	private JLabel[] labels = new JLabel[5];

	/**
	 * Panel for displaying text area and statusbar.
	 */
	private JPanel panel = new JPanel(new BorderLayout());

	/**
	 * Listener for the multiple document model.
	 */
	private MultipleDocumentListener listener = new MultipleDocumentListenerImpl(this, model, labels, flp);

	/**
	 * Width of the window.
	 */
	private static final int WIDTH = 900;
	/**
	 * Height of the window.
	 */
	private static final int HEIGHT = 700;

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Initializes newly created object.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(20, 20);
		setSize(WIDTH, HEIGHT);

		model.addMultipleDocumentListener(listener);

		initGUI();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				exitAction.actionPerformed(null);
				model.removeMultipleDocumentListener(listener);
			}
		});

	}

	/**
	 * Initializes Graphical user interface.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());

		createMenus();
		createToolbars();
		createStatusBar();

		panel.add((DefaultMultipleDocumentModel)model);
		add(panel);

		model.createNewDocument();
	}

	/**
	 * Creates status bar.
	 */
	private void createStatusBar() {
		statusBar = new JPanel(new GridLayout(0, 3));

		createFirstPanel();
		createSecondPanel();
		createThirdPanel();

		statusBar.setBorder(BorderFactory.createLineBorder(Color.gray));

		panel.add(statusBar, BorderLayout.PAGE_END);
	}

	/**
	 * Creates the third panel for the statusbar.
	 */
	private void createThirdPanel() {
		JPanel third = new JPanel(new BorderLayout());

		third.add(clock, BorderLayout.EAST);
		third.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.WEST);
		statusBar.add(third);
	}

	/**
	 * Creates the second panel for the statusbar.
	 */
	private void createSecondPanel() {
		JPanel second = new JPanel(new FlowLayout(FlowLayout.LEADING));
		second.add((labels[1] = new LJStatusBarLabel("ln", flp)));

		second.add((labels[2] = new LJStatusBarLabel("col", flp)));
		second.add((labels[3] = new LJStatusBarLabel("sel", flp)));

		statusBar.add(second);
	}

	/**
	 * Creates the first panel for the statusbar.
	 */
	private void createFirstPanel() {
		JPanel first = new JPanel(new BorderLayout());
		first.add(labels[0] = new LJStatusBarLabel("length", flp), BorderLayout.WEST);

		first.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.EAST);

		statusBar.add(first);
	}

	/**
	 * Creates menubar and is components.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		createFileMenu(menuBar);
		createEditMenu(menuBar);
		createToolsMenu(menuBar);
		createLanguagesMenu(menuBar);

		setJMenuBar(menuBar);
	}

	/**
	 * Creates languages menu.
	 * 
	 * @param menuBar
	 *            menubar of this program
	 */
	private void createLanguagesMenu(JMenuBar menuBar) {
		JMenu languages = new LJMenu("languages", flp);
		menuBar.add(languages);

		languages.add(hrLanguageAction);
		languages.add(enLanguageAction);
		languages.add(deLanguageAction);

	}

	/**
	 * Creates tools menu.
	 * 
	 * @param menuBar
	 *            menubar of this program
	 */
	private void createToolsMenu(JMenuBar menuBar) {
		JMenu toolsMenu = new LJMenu("tools", flp);
		menuBar.add(toolsMenu);

		toolsMenu.add(new JMenuItem(uniqueAction));

		JMenu changeCase = new LJMenu("change_case", flp);
		toolsMenu.add(changeCase);
		changeCase.add(new JMenuItem(toLowerAction));
		changeCase.add(new JMenuItem(toUpperAction));
		changeCase.add(new JMenuItem(invertAction));

		JMenu sort = new LJMenu("sort", flp);

		toolsMenu.add(sort);
		sort.add(new JMenuItem(ascendingAction));
		sort.add(new JMenuItem(descendingAction));
	}

	/**
	 * Creates edit menu.
	 * 
	 * @param menuBar
	 *            menubar of this program
	 */
	private void createEditMenu(JMenuBar menuBar) {
		JMenu editMenu = new LJMenu("edit", flp);
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));
	}

	/**
	 * Creates file menu.
	 * 
	 * @param menuBar
	 *            menubar of this program
	 */
	private void createFileMenu(JMenuBar menuBar) {
		JMenu fileMenu = new LJMenu("file", flp);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(createNewDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));

		fileMenu.addSeparator();

		fileMenu.add(new JMenuItem(statisticalInfoAction));
		fileMenu.add(new JMenuItem(closeTabAction));
		fileMenu.add(new JMenuItem(exitAction));

	}

	/**
	 * Creates toolbar and tools for the toolbar.
	 * 
	 */
	private void createToolbars() {
		JToolBar toolBar = new LJToolBar("tools", flp);

		toolBar.add(new JButton(createNewDocumentAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));

		toolBar.addSeparator();

		toolBar.add(new JButton(statisticalInfoAction));

		toolBar.addSeparator();

		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(pasteAction));

		toolBar.addSeparator();

		toolBar.add(new JButton(closeTabAction));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Method called when program starts.
	 * 
	 * @param args
	 *            command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

}
