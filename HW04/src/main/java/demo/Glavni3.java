package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Program demonstrates the L-system fractals drawing.
 * 
 * @author Filip Karacic
 *
 */
public class Glavni3 {

	/**
	 * Method that is called when program starts.
	 * 
	 * @param args command line arguments represented as {@code String} array
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
