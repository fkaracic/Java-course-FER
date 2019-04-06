package hr.fer.zemris.lsystems.impl.commands;

import java.util.Objects;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Represents the command that rotates the current direction of the turtle
 * for the given angle.
 * 
 * @author Filip Karacic
 *
 */
public class RotateCommand implements Command{
	/**
	 * Angle of the rotation.
	 */
	private double angle;
	
	/**
	 * Initializes newly created command of rotation for the given angle.
	 * 
	 * @param angle angle of the rotation
	 */
	public RotateCommand(double angle) {
		
			this.angle = angle;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.lsystems.impl.Command#execute(hr.fer.zemris.lsystems.impl.Context, hr.fer.zemris.lsystems.Painter)
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		Objects.requireNonNull(ctx);
		Objects.requireNonNull(painter);
		
		TurtleState state = ctx.getCurrentState();
		
		Vector2D currentPosition = state.getDirection(); 
		Vector2D newDirection = currentPosition.rotated(angle);
		
		state.setDirection(newDirection);
	}

}
