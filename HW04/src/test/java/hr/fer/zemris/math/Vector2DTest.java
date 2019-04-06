package hr.fer.zemris.math;

import org.junit.Test;
import org.junit.Assert;

public class Vector2DTest {

	@Test
	public void GetXAndY() {
		double x = 2.0;
		double y = 3.0;
		double DELTA = 1E-8;
		
		Vector2D vector = new Vector2D(x, y);
		
		Assert.assertEquals(x, vector.getX(), DELTA);
		Assert.assertEquals(y, vector.getY(), DELTA);
	}
	
	@Test
	public void translateVector() {
		double x1 = 2.0;
		double y1 = 3.0;
		
		double x2 = 3.0;
		double y2 = 4.0;
		
		Vector2D vector1 = new Vector2D(x1, y1);
		Vector2D vector2 = new Vector2D(x2, y2);
		Vector2D expected = new Vector2D(x1 + x2, y1 + y2);
		
		Assert.assertEquals(expected, vector1.translated(vector2));
		
		vector1.translate(vector2);
		Assert.assertEquals(expected, vector1);
	}
	
	@Test (expected = NullPointerException.class)
	public void translateNull() {
		double x = 2.0;
		double y = 3.0;
		
		Vector2D vector = new Vector2D(x, y);
		
		vector.translate(null);
	}
	
	@Test
	public void rotateVector() {
		double y = Math.sqrt(3) / 2;
		double x = 0.5;
		
		double angle = -60.0;
		
		Vector2D vector = new Vector2D(x, y);
		Vector2D expected = new Vector2D(1.0, 0.0);
		
		Assert.assertEquals(expected, vector.rotated(angle));
		
		vector.rotate(angle);
		Assert.assertEquals(expected, vector);
		
	}
	
	@Test
	public void scaleVector() {
		double x = 3.0;
		double y = 2.0;
		
		double scaler = 2.0;
		
		Vector2D vector = new Vector2D(x, y);
		Vector2D expected = new Vector2D(scaler * x, scaler * y);
		
		Assert.assertEquals(expected, vector.scaled(scaler));
		
		vector.scale(scaler);
		Assert.assertEquals(expected, vector);
		
	}
	
	@Test
	public void copyVector() {
		double x = 3.0;
		double y = 2.0;
		
		Vector2D vector = new Vector2D(x, y);
		Vector2D expected = new Vector2D(x, y);
		
		Assert.assertEquals(expected, vector.copy());
		
		double scaler = 2.0;
		vector.scale(scaler);
		Assert.assertNotEquals(expected, vector);	
	}
}
