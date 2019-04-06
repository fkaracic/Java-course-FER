package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Represents execution of a command over the state of the turtle.
 * <p>
 * This is a functional interface whose functional method is
 * {@link #execute(Context, Painter)}.
 * 
 * @author Filip Karacic
 *
 */
@FunctionalInterface
public interface Command {

	/**
	 * Performs the specified command over the state of the turtle.
	 * 
	 * @param ctx
	 *            context that remembers states of the turtle
	 * @param painter
	 *          painter that provides drawing lines
	 * @throws NullPointerException if {@code ctx} or {@code painter} is {@code null}
	 */
	void execute(Context ctx, Painter painter);
}
