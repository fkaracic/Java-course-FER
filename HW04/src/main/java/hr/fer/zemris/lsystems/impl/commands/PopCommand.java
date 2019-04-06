package hr.fer.zemris.lsystems.impl.commands;

import java.util.Objects;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Represents the command that pops the current state of the turtle.
 * 
 * @author Filip Karacic
 *
 */
public class PopCommand implements Command{

	/* (non-Javadoc)
	 * @see hr.fer.zemris.lsystems.impl.Command#execute(hr.fer.zemris.lsystems.impl.Context, hr.fer.zemris.lsystems.Painter)
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		Objects.requireNonNull(ctx);
		Objects.requireNonNull(painter);
		
		ctx.popState();
	}

}
