package hr.fer.zemris.java.hw06.demo2;

import org.junit.Test;
import static org.junit.Assert.*;

public class PrimesCollectionTest {
	
	@Test (expected = IllegalArgumentException.class)
	public void negativeSize() {
		new PrimesCollection(-1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void zeroSize() {
		new PrimesCollection(0);
	}
	
	@Test
	public void onlyOne() {
		PrimesCollection collection = new PrimesCollection(1);
		
		for(Integer prime : collection) {
			assertEquals(2, prime.intValue());
		}
	}
	
	@Test
	public void collectionOfTen() {
		PrimesCollection collection = new PrimesCollection(10);
		
		Integer[] primes = new Integer[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29}; 
		int i = 0;
		
		for(Integer prime : collection) {
			assertEquals(primes[i], prime);
			i++;
		}
	}

}
