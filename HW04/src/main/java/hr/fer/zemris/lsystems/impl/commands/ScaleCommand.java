package hr.fer.zemris.lsystems.impl.commands;

import java.util.Objects;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Represents the command that scales the vector of the current direction of the turtle
 * for the given factor.
 * 
 * @author Filip Karacic
 *
 */
public class ScaleCommand implements Command{
	/**
	 * Factor of the scaling.
	 */
	private double factor;
	
	/**
	 * Initializes newly created command of scaling for the given factor.
	 * 
	 * @param factor factor of the scaling
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.lsystems.impl.Command#execute(hr.fer.zemris.lsystems.impl.Context, hr.fer.zemris.lsystems.Painter)
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		Objects.requireNonNull(ctx);
		Objects.requireNonNull(painter);
		
		TurtleState state = ctx.getCurrentState();
		
		state.setStepLength(state.getStepLength() * factor);
	}

}
