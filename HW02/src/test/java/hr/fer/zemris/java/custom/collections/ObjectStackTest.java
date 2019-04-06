package hr.fer.zemris.java.custom.collections;

import org.junit.Test;
import org.junit.Assert;

public class ObjectStackTest {

	@Test
	public void stackPushAndPop() {
		ObjectStack stack = new ObjectStack();
		Integer value = 20;

		stack.push(value);

		Assert.assertEquals(value, stack.pop());
	}

	@Test(expected = EmptyStackException.class)
	public void popEmptyStack() {
		ObjectStack stack = new ObjectStack();

		stack.pop();
	}

	@Test(expected = EmptyStackException.class)
	public void peekEmptyStack() {
		ObjectStack stack = new ObjectStack();

		stack.peek();
	}

	@Test
	public void emptyStackSize() {
		ObjectStack stack = new ObjectStack();
		int expected = 0;

		Assert.assertEquals(expected, stack.size());
		Assert.assertTrue(stack.isEmpty());
	}

	@Test
	public void nonEmptyStackSize() {
		ObjectStack stack = new ObjectStack();
		Integer value1 = 10;
		Integer value2 = 20;

		stack.push(value1);
		stack.push(value2);

		int expectedSize = 2;

		Assert.assertEquals(expectedSize, stack.size());
		Assert.assertFalse(stack.isEmpty());
	}

	@Test
	public void emptyStackClear() {
		ObjectStack stack = new ObjectStack();

		stack.clear();

		Assert.assertNotNull(stack);
	}

	@Test
	public void nonEmptyStackClear() {
		ObjectStack stack = new ObjectStack();
		Integer value1 = 10;
		Integer value2 = 20;
		int expectedSize = 0;

		stack.push(value1);
		stack.push(value2);

		stack.clear();

		Assert.assertNotNull(stack);
		Assert.assertEquals(expectedSize, stack.size());
	}

}
