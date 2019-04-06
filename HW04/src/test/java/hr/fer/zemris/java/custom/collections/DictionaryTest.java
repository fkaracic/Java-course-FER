package hr.fer.zemris.java.custom.collections;

import org.junit.Test;
import org.junit.Assert;

public class DictionaryTest {

	@Test
	public void putExistingKey() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("Key1", "Value1");
		dictionary.put("Key2", "Value2");
		dictionary.put("Key3", "Value3");
		
		dictionary.put("Key1", "Value4");
		
		Assert.assertEquals("Value4", dictionary.get("Key1"));
	}
	
	@Test (expected = NullPointerException.class)
	public void putNullKey() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put(null, "Value1");
	}
	
	@Test
	public void putNullValue() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("Key1", null);
		
		Assert.assertEquals(null, dictionary.get("Key1"));
	}
	
	@Test
	public void getNonExistingKey() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("Key1", "Value1");
		dictionary.put("Key2", "Value2");
		dictionary.put("Key3", "Value3");
		
		Assert.assertNull(dictionary.get("Key4"));
	}
	
	@Test
	public void getExistingKey() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("Key1", "Value1");
		dictionary.put("Key2", "Value2");
		dictionary.put("Key3", "Value3");
		
		Assert.assertEquals("Value1", dictionary.get("Key1"));
	}
	
}
