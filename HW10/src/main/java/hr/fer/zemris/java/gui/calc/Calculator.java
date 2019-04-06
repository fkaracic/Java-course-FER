package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Stack;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.gui.calc.buttons.BinaryInversingOperatorButton;
import hr.fer.zemris.java.gui.calc.buttons.BinaryOperatorButton;
import hr.fer.zemris.java.gui.calc.buttons.EqualsButton;
import hr.fer.zemris.java.gui.calc.buttons.NumberButton;
import hr.fer.zemris.java.gui.calc.buttons.SimpleOperationButton;
import hr.fer.zemris.java.gui.calc.buttons.PopButton;
import hr.fer.zemris.java.gui.calc.buttons.UnaryOperatorButton;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * This program represents simple calculator which provides some simple
 * operations (such as +,-,+,/), but also some a bit complex(such as sin,cos,
 * log, ln, x^n...). Inv check box if check inverse function is calculated for
 * some operations(e.g. if sin then arcsin is calculated). Also provides stack
 * operations (push and pop). Push - pushes current number to the stack, while
 * pop replaces current number with the last number on the stack.
 * 
 * @author Filip Karacic
 *
 */
public class Calculator extends JFrame {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * <code>true</code> if new number is to be provided.
	 */
	public static boolean newNumber;
	/**
	 * Model for this calculator.
	 */
	private CalcModel model;
	/**
	 * Stack for this calculator.
	 */
	private Stack<Double> stack;
	/**
	 * Color for the buttons (blue).
	 */
	public static final Color BUTTON_COLOR = new Color(96, 149, 209);
	/**
	 * Width of the window.
	 */
	private static final int WIDTH = 600;
	/**
	 * Height of the window.
	 */
	private static final int HEIGHT = 450;

	/**
	 * Constructor for initialization of the calculator.
	 */
	public Calculator() {
		model = new CalcModelImpl();
		stack = new Stack<>();
		
		setMinimumSize(new Dimension(WIDTH, HEIGHT));

		initializeGUI();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * Initializes GUI for this calculator (i.e. label,buttons..).
	 */
	private void initializeGUI() {
		JPanel panel = new JPanel(new CalcLayout(3));
		
		initializeLabel(panel);
		initializeButtons(panel);
		initializeCheckBox(panel);

		add(panel);
	}

	/**
	 * Initializes label that contains output of the calculator.
	 * 
	 * @param panel
	 *            panel to which initialized label is added.
	 */
	private void initializeLabel(JPanel panel) {
		JLabel label = new JLabel("0", SwingConstants.TRAILING);
		label.setFont(label.getFont().deriveFont(30.f));

		label.setOpaque(true);
		label.setBackground(Color.ORANGE);

		label.setBorder(BorderFactory.createLineBorder(Color.BLUE));

		model.addCalcValueListener(l -> {
			label.setFont(label.getFont().deriveFont((float) (label.getSize().height * 0.45)));

			label.setText(l.toString());
		});

		panel.add(label, "1,1");
	}

	/**
	 * Initializes buttons that provides user to calculate operation.
	 * 
	 * @param panel
	 *            panel to which initialized buttons are added.
	 */
	private void initializeButtons(JPanel panel) {

		initializeNumberButtons(panel);

		initializeSimpleButtons(panel);

		intializeBinaryOperations(panel);
		initializeUnaryOperators(panel);

		JButton power = new BinaryInversingOperatorButton("x^n", (v, d) -> Math.pow(v, 1.0 / d),
				(v, d) -> Math.pow(v, d), model);
		panel.add(power, "5,1");

		JButton equals = new EqualsButton(model);
		panel.add(equals, "1,6");

		JButton pop = new PopButton(model, "pop", stack);
		panel.add(pop, "4,7");
	}

	/**
	 * Initializes buttons containing numbers.
	 * 
	 * @param panel
	 *            panel to which initialized buttons are added.
	 */
	private void initializeNumberButtons(JPanel panel) {
		panel.add(new NumberButton(0, model), "5,3");

		int k = 1;
		for (int i = 4; i >= 2; i--) {
			for (int j = 3; j <= 5; j++) {
				panel.add(new NumberButton(k++, model), i + "," + j);
			}
		}

	}

	/**
	 * Initializes buttons of operations clr, res, sign swap and push.
	 * 
	 * @param panel
	 *            panel to which initialized buttons are added.
	 */
	private void initializeSimpleButtons(JPanel panel) {
		JButton clr = new SimpleOperationButton("clr", model, model -> model.clear());
		JButton res = new SimpleOperationButton("res", model, model -> model.clearAll());
		JButton signChanger = new SimpleOperationButton("+/-", model, model -> model.swapSign());
		JButton push = new SimpleOperationButton("push", model, model -> stack.push(model.getValue()));

		Consumer<CalcModel> dotConsumer = model -> {
			if (Calculator.newNumber) {
				model.clear();
				Calculator.newNumber = false;
			}

			model.insertDecimalPoint();
		};

		JButton decimalPoint = new SimpleOperationButton(".", model, dotConsumer);

		panel.add(clr, "1,7");
		panel.add(res, "2,7");
		panel.add(decimalPoint, "5,5");
		panel.add(signChanger, "5,4");
		panel.add(push, "3,7");
	}

	/**
	 * Initializes buttons of simple binary operations (+,-,*,/).
	 * 
	 * @param panel
	 *            panel to which initialized buttons are added.
	 */
	private void intializeBinaryOperations(JPanel panel) {
		panel.add(new BinaryOperatorButton("+", (a, b) -> a + b, model), "5,6");
		panel.add(new BinaryOperatorButton("-", (a, b) -> a - b, model), "4,6");
		panel.add(new BinaryOperatorButton("*", (a, b) -> a * b, model), "3,6");
		panel.add(new BinaryOperatorButton("/", (a, b) -> a / b, model), "2,6");

	}

	/**
	 * Initializes buttons of operations sin, cos, tan, ctg, 1/x, ln, log and their
	 * inversion functions.
	 * 
	 * @param panel
	 *            panel to which initialized buttons are added.
	 */
	private void initializeUnaryOperators(JPanel panel) {
		JButton sin = new UnaryOperatorButton("sin", v -> Math.sin(v), v -> Math.asin(v), model);
		JButton cos = new UnaryOperatorButton("cos", v -> Math.cos(v), v -> Math.acos(v), model);
		JButton tan = new UnaryOperatorButton("tan", v -> Math.tan(v), v -> Math.atan(v), model);
		JButton ctg = new UnaryOperatorButton("ctg", v -> 1.0 / Math.tan(v), v -> Math.atan(1.0 / v), model);

		JButton reversed = new UnaryOperatorButton("1/x", v -> 1.0 / v, v -> 1.0 / v, model);

		JButton ln = new UnaryOperatorButton("ln", v -> Math.log(v), v -> Math.exp(v), model);
		JButton log = new UnaryOperatorButton("log", v -> Math.log10(v), v -> Math.pow(10.0, v), model);

		panel.add(sin, "2,2");
		panel.add(log, "3,1");
		panel.add(cos, "3,2");
		panel.add(tan, "4,2");
		panel.add(ctg, "5,2");
		panel.add(reversed, "2,1");
		panel.add(ln, "4,1");
	}

	/**
	 * Initializes check box for inversion functions, or ordinary functions if not
	 * checked.
	 * 
	 * @param panel
	 *            panel to which initialized check box is added.
	 */
	private void initializeCheckBox(JPanel panel) {
		JCheckBox check = new JCheckBox("Inv");

		check.setBackground(BUTTON_COLOR);

		check.addActionListener(l -> {
			UnaryOperatorButton.inv = !UnaryOperatorButton.inv;
			BinaryInversingOperatorButton.inv = !BinaryInversingOperatorButton.inv;
		});

		panel.add(check, "5,7");
	}

	/**
	 * Method called when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator();
		});
	}

}
