package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Represents worker that draws image with dimensions 200x200 that has white
 * background and blue circle in it.
 * 
 * @author Filip Karacic
 *
 */
public class CircleWorker implements IWebWorker {
	
	/**
	 * Width of the image.
	 */
	private final static int WIDTH = 200;
	/**
	 * Height of the image.
	 */
	private final static int HEIGHT = 200;

	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("image/jpg");

		BufferedImage bim = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		Rectangle r = new Rectangle(0, 0, WIDTH, HEIGHT);

		g2d.setColor(Color.white);
		g2d.fillRect(r.x, r.y, r.width, r.height);

		g2d.setColor(Color.blue);
		g2d.fillOval(r.x, r.y, r.width, r.height);
		g2d.create(0, 0, 200, 200);

		g2d.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}