package hr.fer.zemris.math;

import org.junit.Test;
import org.junit.Assert;

public class Vector3Test {

	@Test
	public void GetXAndY() {
		double x = 2.0;
		double y = 3.0;
		double z = 1.2;
		double DELTA = 1E-8;
		
		Vector3 vector = new Vector3(x, y, z);
		
		Assert.assertEquals(x, vector.getX(), DELTA);
		Assert.assertEquals(y, vector.getY(), DELTA);
		Assert.assertEquals(z, vector.getZ(), DELTA);
	}
	
	@Test
	public void scaleVector() {
		double x = 3.0;
		double y = 2.0;
		double z = 1.2;
		
		double scaler = 2.0;
		
		Vector3 vector = new Vector3(x, y, z);
		Vector3 expected = new Vector3(scaler * x, scaler * y, scaler * z);
		
		Assert.assertEquals(expected, vector.scale(scaler));
	}
	
	@Test
	public void norm() {
		double x = 3.0;
		double y = 2.0;
		double z = 1.2;
		
		Vector3 vector = new Vector3(x, y, z);
		double expected = Math.sqrt(3*3 + 2*2 + 1.2*1.2);
		
		Assert.assertEquals(expected, vector.norm(), 1E-8);
	}
	
	@Test
	public void addition() {
		double x1 = 3.0;
		double y1 = 2.0;
		double z1 = 1.2;
		
		double x2 = 2.0;
		double y2 = 1.0;
		double z2 = 0.2;
		
		Vector3 vector = new Vector3(x1, y1, z1);
		Vector3 vector2 = new Vector3(x2, y2, z2);
		
		Vector3 expected = new Vector3(x2 + x1, y2 + y1, z2 + z1);
		
		Assert.assertEquals(expected, vector2.add(vector));
	}
	
	@Test
	public void subtraction() {
		double x1 = 3.0;
		double y1 = 2.0;
		double z1 = 1.2;
		
		double x2 = 2.0;
		double y2 = 1.0;
		double z2 = 0.2;
		
		Vector3 vector = new Vector3(x1, y1, z1);
		Vector3 vector2 = new Vector3(x2, y2, z2);
		
		Vector3 expected = new Vector3(x2 - x1, y2 - y1, z2 - z1);
		
		Assert.assertEquals(expected, vector2.sub(vector));
	}
	
	@Test
	public void dot() {
		double x1 = 3.0;
		double y1 = 2.0;
		double z1 = 1.2;
		
		double x2 = 2.0;
		double y2 = 1.0;
		double z2 = 0.2;
		
		Vector3 vector = new Vector3(x1, y1, z1);
		Vector3 vector2 = new Vector3(x2, y2, z2);
		
		double expected = 6+2+0.2*1.2;
		
		Assert.assertEquals(expected, vector.dot(vector2), 1e-8);
	}
	
	@Test
	public void cross() {
		double x1 = 1.0;
		double y1 = 0.0;
		double z1 = 0.0;
		
		double x2 = 0.0;
		double y2 = 1.0;
		double z2 = 0.0;
		
		Vector3 vector = new Vector3(x1, y1, z1);
		Vector3 vector2 = new Vector3(x2, y2, z2);
		
		Vector3 expected = new Vector3(0.0, 0.0, 1.0);
		
		Assert.assertEquals(expected, vector.cross(vector2));
	}
	
	@Test
	public void scale() {
		double x1 = 3.0;
		double y1 = 2.0;
		double z1 = 1.2;
		
		double scale = 2.5;
		
		Vector3 vector = new Vector3(x1, y1, z1);
		Vector3 expected = new Vector3(x1 * scale, y1 * scale, z1 * scale);
		
		Assert.assertEquals(expected, vector.scale(scale));
	}
	
	@Test
	public void cosine() {
		double x1 = 3.0;
		double y1 = 2.0;
		double z1 = 1.2;
		
		double x2 = 2.0;
		double y2 = 1.0;
		double z2 = 0.2;
		
		Vector3 vector = new Vector3(x1, y1, z1);
		Vector3 vector2 = new Vector3(x2, y2, z2);
		
		double expected = vector.dot(vector2)/(vector.norm() * vector2.norm());
		
		Assert.assertEquals(expected, vector.cosAngle(vector2), 1e-8);
	}
	
	@Test
	public void toArray() {
		double x1 = 3.0;
		double y1 = 2.0;
		double z1 = 1.2;
		
		Vector3 vector = new Vector3(x1, y1, z1);
		double[] array = vector.toArray();
		
		Assert.assertEquals(x1, array[0], 1e-8);
		Assert.assertEquals(y1, array[1], 1e-8);
		Assert.assertEquals(z1, array[2], 1e-8);
	}
	
	
}
