package hr.fer.zemris.lsystems.impl.commands;

import java.util.Objects;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Represents the command that moves the turtle for the length of the step in
 * the direction of the current direction of the turtle without drawing the
 * line.
 * 
 * @author Filip Karacic
 *
 */
public class SkipCommand implements Command {
	/**
	 * Length of the step of the movement.
	 */
	private double step;

	/**
	 * Initializes newly created command of movement for the given step.
	 * 
	 * @param step
	 *            length of the step of the movement.
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.lsystems.impl.Command#execute(hr.fer.zemris.lsystems.impl.Context, hr.fer.zemris.lsystems.Painter)
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		Objects.requireNonNull(ctx);
		Objects.requireNonNull(painter);

		TurtleState state = ctx.getCurrentState();

		Vector2D newPosition = state.getPosition()
				.translated(state.getDirection().scaled(step * state.getStepLength()));

		state.setPosition(newPosition);
	}

}
