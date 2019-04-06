package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;
import java.util.Objects;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Represents the command that changes color in the current state of the turtle.
 * 
 * @author Filip Karacic
 *
 */
public class ColorCommand implements Command {
	/**
	 * Color of the drawing line.
	 */
	private Color color;

	/**
	 * Initializes newly created object representing the command of changing color.
	 * 
	 * @param color color to be set
	 * 
	 * @throws NullPointerException if {@code color} is {@code null}
	 */
	public ColorCommand(Color color) {
		Objects.requireNonNull(color);

		this.color = color;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.lsystems.impl.Command#execute(hr.fer.zemris.lsystems.impl.Context, hr.fer.zemris.lsystems.Painter)
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		Objects.requireNonNull(ctx);
		Objects.requireNonNull(painter);

		TurtleState state = ctx.getCurrentState();
		state.setColor(color);
	}

}
