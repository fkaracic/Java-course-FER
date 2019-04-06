package hr.fer.zemris.java.gui.prim;

import static java.lang.Math.sqrt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

public class PrimListModelTest {
	PrimListModel model;
	
	@Before
	public void setUp() {
		model = new PrimListModel();
	}
	
	@Test
	public void testSize() {
		model.next();
		model.next();
		model.next();
		model.next();
		
		int size = 5;
		
		Assert.assertEquals(size, model.getSize());
	}
	
	@Test
	public void testGetter() {
		model.next();
		model.next();
		model.next();
		model.next();
		
		Assert.assertEquals(Integer.valueOf(1), model.getElementAt(0));
		Assert.assertEquals(Integer.valueOf(2), model.getElementAt(1));
		Assert.assertEquals(Integer.valueOf(3), model.getElementAt(2));
		Assert.assertEquals(Integer.valueOf(5), model.getElementAt(3));
		Assert.assertEquals(Integer.valueOf(7), model.getElementAt(4));
	}
	
	@Test
	public void testNextPrime() {
		int[] primes = new int[] {2,3,5,7,11,13,17,19,23,29,31};
	
		int current = 1;
		for(int i = 0; i < 11; i++) {
			Assert.assertEquals(primes[0], calculateNext(current));
		}
	}

	private static int calculateNext(int current) {
		int prim = current;
		boolean isPrim;

		while (true) {
			isPrim = true;
			prim++;

			if (prim > 2 && prim % 2 == 0)
				continue;

			for (long i = 2; i <= sqrt(prim); i++) {
				if (prim % i == 0) {
					isPrim = false;
					break;
				}
			}

			if (isPrim)
				return prim;
		}
	}
}
