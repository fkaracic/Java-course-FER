package hr.fer.zemris.java.hw07.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilTest {

	@Test (expected = IllegalArgumentException.class)
	public void nullText() {
		String nullText = null;
		
		Util.hexToByte(nullText);
	}
	
	@Test
	public void emptyText() {
		String empty = "";
		
		byte[] bytes = Util.hexToByte(empty);
		
		assertNotNull(bytes);
		assertEquals(0, bytes.length);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void oddHexadecimalCharacters() {
		String odd = "ffb";
		
		Util.hexToByte(odd);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidHexadecimalCharacters() {
		String invalid = "ffbge";
		
		Util.hexToByte(invalid);
	}
	
	@Test
	public void validText() {
		String empty = "ff0a";
		
		byte[] bytes = Util.hexToByte(empty);
		
		assertNotNull(bytes);
		assertEquals(2, bytes.length);
		
		byte first = -1;
		byte second = 10;
		
		assertEquals(first, bytes[0]);
		assertEquals(second, bytes[1]);
	}
	
	@Test
	public void upperAndLowerCases() {
		String empty = "fF0a7CfE";
		
		byte[] bytes = Util.hexToByte(empty);
		
		assertNotNull(bytes);
		assertEquals(4, bytes.length);
		
		byte first = -1;
		byte second = 10;
		byte third = 124;
		byte fourth = -2;
		
		assertEquals(first, bytes[0]);
		assertEquals(second, bytes[1]);
		assertEquals(third, bytes[2]);
		assertEquals(fourth, bytes[3]);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void nullArray() {
		byte[] nullArray= null;
		
		Util.byteToHex(nullArray);
	}
	
	@Test
	public void emptyArray() {
		byte[] empty = new byte[0];
		
		 String hex = Util.byteToHex(empty);
		
		assertNotNull(hex);
		assertTrue(hex.isEmpty());
	}
	
	@Test
	public void validArray() {
		byte[] valid = new byte[] {-1, 10, 124, -2};
		
		String hex = Util.byteToHex(valid);
		
		assertNotNull(hex);
		
		byte[] result = Util.hexToByte(hex);
		
		for(int i = 0; i < 4; i++) {
			assertEquals(valid[i], result[i]);
		}
	}
	
}
