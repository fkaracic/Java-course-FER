package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Test;
import static org.junit.Assert.*;

public class ObjectMultistackTest {
	
	@Test (expected = NullPointerException.class)
	public void pushNullName() {
		ObjectMultistack multi = new ObjectMultistack();
		
		multi.push(null, new ValueWrapper("value"));
	}
	
	@Test (expected = NullPointerException.class)
	public void pushNullWrapper() {
		ObjectMultistack multi = new ObjectMultistack();
		
		multi.push("Name", null);
	}
	
	@Test
	public void pushNullValueWrapped() {
		ObjectMultistack multi = new ObjectMultistack();
		
		multi.push("Name", new ValueWrapper(null));
		
		assertFalse(multi.isEmpty("Name"));
	}
	
	@Test
	public void pushValueWrapped() {
		ObjectMultistack multi = new ObjectMultistack();
		
		multi.push("Name", new ValueWrapper("Value"));
		
		assertFalse(multi.isEmpty("Name"));
	}
	
	@Test (expected = EmptyObjectMultiStackException.class)
	public void popEmpty() {
		ObjectMultistack multi = new ObjectMultistack();
		
		assertTrue(multi.isEmpty("Name"));
		multi.pop("Name");
	}
	
	@Test (expected = EmptyObjectMultiStackException.class)
	public void popNotExisting() {
		ObjectMultistack multi = new ObjectMultistack();
		
		multi.push("Name", new ValueWrapper("Value"));
		
		assertTrue(multi.isEmpty("Name2"));
		multi.pop("Name2");
	}

	@Test
	public void popNullValueWrapped() {
		ObjectMultistack multi = new ObjectMultistack();
		
		multi.push("Name", new ValueWrapper(null));
		
		assertEquals(null, multi.pop("Name").getValue());
		assertTrue(multi.isEmpty("Name"));
	}
	
	@Test
	public void popValueWrapped() {
		ObjectMultistack multi = new ObjectMultistack();
		
		multi.push("Name", new ValueWrapper("Value"));
		
		assertEquals("Value", multi.pop("Name").getValue());
		assertTrue(multi.isEmpty("Name"));
	}
	
	@Test (expected = EmptyObjectMultiStackException.class)
	public void peekEmpty() {
		ObjectMultistack multi = new ObjectMultistack();
		
		assertTrue(multi.isEmpty("Name"));
		multi.peek("Name");
	}
	
	@Test (expected = EmptyObjectMultiStackException.class)
	public void peekNotExisting() {
		ObjectMultistack multi = new ObjectMultistack();
		
		multi.push("Name", new ValueWrapper("Value"));
		
		assertTrue(multi.isEmpty("Name2"));
		multi.peek("Name2");
	}

	@Test
	public void peekNullValueWrapped() {
		ObjectMultistack multi = new ObjectMultistack();
		
		multi.push("Name", new ValueWrapper(null));
		
		assertEquals(null, multi.peek("Name").getValue());
		assertFalse(multi.isEmpty("Name"));
	}
	
	@Test
	public void peekValueWrapped() {
		ObjectMultistack multi = new ObjectMultistack();
		
		multi.push("Name", new ValueWrapper("Value"));
		
		assertEquals("Value", multi.peek("Name").getValue());
		assertFalse(multi.isEmpty("Name"));
	}
}
