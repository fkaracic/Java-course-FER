package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Objects;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
import hr.fer.zemris.math.Vector2D;

/**
 * {@code LSystemBuilderImpl} class represents builder of L-system for fractals
 * drawing.
 * <p>
 * Permitted directives are: origin, angle, unitLength, unitLengthDegreeScaler,
 * command, axiom and production.
 * <p>
 * Permitted actions are: "draw s", "skip s", "scale s", "rotate a", "push",
 * "pop", "color rrggbb" (where s is step, a is angle and rrggbb represents
 * hexadecimal number).
 * 
 * @author Filip Karacic
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	/**
	 * {@code commands} represents storage of the commands for this L-system.
	 */
	private Dictionary commands;
	/**
	 * {@code productions} represents storage of the productions for this L-system.
	 */
	private Dictionary productions;

	/**
	 * Unit length for this L-system.
	 */
	private double unitLength = DEFAULT_UNIT_LENGTH;
	/**
	 * Degree scaler of the unit length for this L-system.
	 */
	private double unitLengthDegreeScaler = DEFAULT_UNIT_LENGTH_DEGREE_SCALER;
	/**
	 * Starting vector for this L-system.
	 */
	private Vector2D origin = DEFAULT_ORIGIN;
	/**
	 * Starting angle for this L-system.
	 */
	private double angle = DEFAULT_ANGLE;
	/**
	 * Starting sequence for developing this L-system.
	 */
	private String axiom = DEFAULT_AXIOM;

	private static final double DEFAULT_ANGLE = 0;
	
	private static final double DEFAULT_UNIT_LENGTH = 0.1;
	
	private static final double DEFAULT_UNIT_LENGTH_DEGREE_SCALER = 1;
	
	private static final Vector2D DEFAULT_ORIGIN = new Vector2D(0, 0);
	
	private static final String DEFAULT_AXIOM = "";
	
	/**
	 * Initializes newly created {@code LSystemBuilderImpl} object representing the
	 * L-system for fractals drawing.
	 */
	public LSystemBuilderImpl() {
		commands = new Dictionary();
		productions = new Dictionary();
	}

	/**
	 * Non-static nested class that represents Lindermayer's systems for fractals
	 * drawing.
	 */
	private class MyLSystem implements LSystem {

		/**
		 * Generates and execute commands for drawing fractals for this L-system
		 * 
		 * @param level
		 *            level (depth) of this L-system
		 * @param painter
		 *            painter that provides drawing lines
		 * @throws NullPointerException
		 *             if {@code painter} is {@code null}
		 **/
		@Override
		public void draw(int level, Painter painter) {
			Objects.requireNonNull(painter);

			Context context = new Context();
			TurtleState defaultState = new TurtleState(origin,
					new Vector2D(Math.cos(angle * Math.PI / 180), Math.sin(angle * Math.PI / 180)), Color.BLACK,
					unitLength * Math.pow(unitLengthDegreeScaler, level));

			context.pushState(defaultState);
			String action = generate(level);

			for (int i = 0, h = action.length(); i < h; i++) {
				Command command = (Command) commands.get(action.charAt(i));
				if (command != null) {
					command.execute(context, painter);
				} else {
					continue;
				}
			}
		}

		/**
		 * Generates commands of the given level for this L-system.
		 * 
		 * @param level
		 *            level (depth) of this L-system
		 * 
		 * @returns {@code String} representation of generated commands
		 * @throws IllegalArgumentException
		 *             if the given level is less than 0
		 */
		@Override
		public String generate(int level) {
			if (level < 0)
				throw new IllegalArgumentException("Level cannot be less than 0.");
			if (level == 0)
				return axiom;
			else {
				String string = generate(level - 1);
				StringBuilder result = new StringBuilder();

				for (int i = 0, h = string.length(); i < h; i++) {
					char symbol = string.charAt(i);

					String value = (String) productions.get(symbol);
					if (value != null) {
						result.append(value);
					} else {
						result.append(symbol);
					}
				}
				return result.toString();
			}
		}

	}

	/**
	 * Creates and returns new L-system for drawing fractals.
	 * 
	 * @returns new {@code LSystem} object representing L-system
	 **/
	@Override
	public LSystem build() {
		return new MyLSystem();
	}

	/**
	 * Returns builder of L-system for drawing fractals from the given array of
	 * {@code String}. Every line should have a form of a directive or should be an
	 * empty line.
	 * 
	 * @returns new {@code LSystemBuilder} object representing L-system builder
	 * 
	 * @throws NullPointerException
	 *             if {@code lines} is null
	 * @throws IllegalArgumentException
	 *             if {@code lines} contains disallowed directive
	 **/
	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		Objects.requireNonNull(lines);

		for (int i = 0; i < lines.length; i++) {
			if (lines[i].isEmpty())
				continue;

			lines[i] = lines[i].trim();

			String[] directive = lines[i].split("\\s+");

			try {
				switch (directive[0].toLowerCase()) {
				case "origin":
					if (directive.length != 3)
						throw new IllegalArgumentException("Invalid directive.");
					origin = new Vector2D(Double.parseDouble(directive[1]), Double.parseDouble(directive[2]));
					break;
				case "angle":
					if (directive.length != 2)
						throw new IllegalArgumentException("Invalid directive.");
					angle = Double.parseDouble(directive[1]);
					break;
				case "unitlength":
					if (directive.length != 2)
						throw new IllegalArgumentException("Invalid directive.");
					unitLength = Double.parseDouble(directive[1]);
					break;
				case "unitlengthdegreescaler":
					unitLengthDegreeScaler(lines[i]);
					break;
				case "command":
					command(directive);
					break;
				case "axiom":
					if (directive.length != 2)
						throw new IllegalArgumentException("Invalid directive.");
					axiom = directive[1];
					break;
				case "production":
					production(directive);
					break;
				default:
					throw new IllegalArgumentException("Invalid directive.");
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid directive. Was: '" + lines[i] + "'.");
			}
		}

		return this;
	}

	/**
	 * Sets {@code unitLengthDegreeScaler} from the given line if the given line has
	 * a valid form of unitLengthDegreeScaler directive.
	 * <p>
	 * Form 1: "unitLengthDegreeScaler a / b"
	 * <p>
	 * Form 2: "unitLengthDegreeScaler a"
	 * 
	 * @param line
	 *            line containing directive for unitLengthDegreeScaler
	 * @throws IllegalArgumentException
	 *             if line is not valid unitLengthDegreeScaler directive
	 * @throws NullPointerException
	 *             if {@code line} is {@code null}
	 */
	private void unitLengthDegreeScaler(String line) {
		Objects.requireNonNull(line);

		if (line.contains("/")) {
			line = line.replace("/", " ");

			String[] directive = line.split("\\s+");

			if (directive.length != 3)
				throw new IllegalArgumentException("Invalid directive.");

			unitLengthDegreeScaler = Double.parseDouble(directive[1]) / Double.parseDouble(directive[2]);
		} else {
			String[] directive = line.split("\\s+");

			if (directive.length != 2)
				throw new IllegalArgumentException("Invalid directive.");

			unitLengthDegreeScaler = Double.parseDouble(directive[1]);
		}
	}

	/**
	 * Adds new production from the given directive if the given directive has a
	 * valid form of production directive.
	 * 
	 * @param directive
	 *            array of {@code String} representing elements of production
	 *            directive
	 * @throws IllegalArgumentException
	 *             if the given directive is not valid production directive
	 * @throws NullPointerException
	 *             if {@code directive} is {@code null}
	 */
	private void production(String[] directive) {
		Objects.requireNonNull(directive);

		if (directive.length != 3)
			throw new IllegalArgumentException("Invalid directive.");
		if (directive[1].length() != 1)
			throw new IllegalArgumentException("Invalid directive.");

		productions.put(directive[1].charAt(0), directive[2]);
	}

	/**
	 * Adds new command from the given directive if the given directive has a valid
	 * form of command directive.
	 * 
	 * @param directive
	 *            array of {@code String} representing elements of production
	 *            directive
	 * @throws IllegalArgumentException
	 *             if the given directive is not valid command directive
	 * @throws NullPointerException
	 *             if {@code directive} is {@code null}
	 */
	private void command(String[] directive) {
		Objects.requireNonNull(directive);

		if (directive[1].length() != 1)
			throw new IllegalArgumentException("Invalid directive.");

		if (directive.length == 4) {
			switch (directive[2].toLowerCase()) {
			case "draw":
				commands.put(directive[1].charAt(0), new DrawCommand(Double.parseDouble(directive[3])));
				break;
			case "skip":
				commands.put(directive[1].charAt(0), new SkipCommand(Double.parseDouble(directive[3])));
				break;
			case "scale":
				commands.put(directive[1].charAt(0), new ScaleCommand(Double.parseDouble(directive[3])));
				break;
			case "rotate":
				commands.put(directive[1].charAt(0), new RotateCommand(Double.parseDouble(directive[3])));
				break;
			case "color":
				commands.put(directive[1].charAt(0), new ColorCommand(Color.decode("0X" + directive[3])));
				break;
			default:
				throw new IllegalArgumentException("Invalid directive.");
			}
		} else if (directive.length == 3) {
			switch (directive[2].toLowerCase()) {
			case "push":
				commands.put(directive[1].charAt(0), new PushCommand());
				break;
			case "pop":
				commands.put(directive[1].charAt(0), new PopCommand());
				break;
			default:
				throw new IllegalArgumentException("Invalid directive.");
			}
		} else {
			throw new IllegalArgumentException("Invalid directive.");
		}
	}

	/**
	 * Returns this builder of L-system for drawing fractals with added given
	 * command.
	 * 
	 * @returns this {@code LSystemBuilder} object with added command
	 * 
	 * @throws NullPointerException
	 *             if {@code action} is null
	 * @throws IllegalArgumentException
	 *             if {@code action} contains invalid command
	 **/
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		Objects.requireNonNull(action);

		StringBuilder command = new StringBuilder();

		command.append("command " + symbol + " " + action);
		command(command.toString().split("\\s+"));

		return this;
	}

	/**
	 * Returns this builder of L-system for drawing fractals with added given
	 * production.
	 * 
	 * @returns this {@code LSystemBuilder} object with added production
	 * 
	 * @throws NullPointerException
	 *             if {@code production} is null
	 * @throws IllegalArgumentException
	 *             if {@code production} is not valid {@code String} representation
	 *             of production
	 **/
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		Objects.requireNonNull(production);

		productions.put(symbol, production);
		return this;
	}

	/**
	 * Returns this builder of L-system for drawing fractals with updated angle.
	 * 
	 * @returns this {@code LSystemBuilder} object with updated angle
	 * 
	 **/
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Returns this builder of L-system for drawing fractals with updated axiom.
	 * 
	 * @returns this {@code LSystemBuilder} object with updated axiom
	 * 
	 * @throws NullPointerException
	 *             if {@code axiom} is null
	 **/
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		Objects.requireNonNull(axiom);

		this.axiom = Objects.requireNonNull(axiom);
		return this;
	}

	/**
	 * Returns this builder of L-system for drawing fractals with updated origin.
	 * 
	 * @returns this {@code LSystemBuilder} object with updated origin
	 * 
	 **/
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Returns this builder of L-system for drawing fractals with updated unit
	 * length.
	 * 
	 * @returns this {@code LSystemBuilder} object with updated unit length
	 * 
	 **/
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Returns this builder of L-system for drawing fractals with updated degree
	 * scaler of the unit length.
	 * 
	 * @returns this {@code LSystemBuilder} object with updated degree scaler of the
	 *          unit length
	 * 
	 **/
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

	/**
	 * Returns the vector of origin (starting position) of this L-system.
	 * 
	 * @return the origin of this L-system
	 */
	public Vector2D getOrigin() {
		return origin;
	}

	/**
	 * Returns the unit length of this L-system.
	 * 
	 * @return the unit length of this L-system
	 */
	public double getUnitLength() {
		return unitLength;
	}

	/**
	 * Returns the degree scaler of the unit length of this L-system.
	 * 
	 * @return the degree scaler of the unit length of this L-system
	 */
	public double getUnitLengthDegreeScaler() {
		return unitLengthDegreeScaler;
	}

	/**
	 * Returns the starting angle of this L-system.
	 * 
	 * @return the starting angle of this L-system
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Returns the axiom of this L-system.
	 * 
	 * @return the axiom of this L-system
	 */
	public String getAxiom() {
		return axiom;
	}

}
