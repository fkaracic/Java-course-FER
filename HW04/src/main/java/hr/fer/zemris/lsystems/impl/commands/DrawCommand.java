package hr.fer.zemris.lsystems.impl.commands;

import java.util.Objects;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Represents the command that draws a line for length of the step in the
 * direction of the current direction of the turtle.
 * 
 * @author Filip Karacic
 *
 */
public class DrawCommand implements Command {
	/**
	 * Length of the step of the drawing.
	 */
	private double step;

	/**
	 * Initializes newly created object representing the command of drawing a line.
	 * 
	 * @param step
	 *            length of the step of the drawing
	 */
	public DrawCommand(double step) {
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
		
		float lineThickness = 1.0f;
		painter.drawLine(state.getPosition().getX(), state.getPosition().getY(), newPosition.getX(), newPosition.getY(),
				state.getColor(), (float) lineThickness);
		
		state.setPosition(newPosition);
	}

}
