package hr.fer.zemris.java.custom.collections;

import org.junit.Test;
import org.junit.Assert;

public class LinkedListIndexedCollectionTest {

	@Test
	public void addToEmptyAndGet() {
		LinkedListIndexedCollection empty = new LinkedListIndexedCollection();
		Integer value = 10;

		empty.add(value);

		Assert.assertEquals(value, empty.get(0));
	}

	@Test
	public void addToNonEmptyAndGet() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Integer value1 = 10;
		Integer value2 = 20;

		collection.add(value1);
		collection.add(value2);

		Assert.assertEquals(value1, collection.get(0));
		Assert.assertEquals(value2, collection.get(1));
	}

	@Test(expected = NullPointerException.class)
	public void addNull() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Integer value = null;

		collection.add(value);
	}

	@Test
	public void remove() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Integer value1 = 10;
		Integer value2 = 20;

		collection.add(value1);
		collection.add(value2);

		collection.remove(value2);

		int expectedSize = 1;

		Assert.assertEquals(expectedSize, collection.size());
	}

	@Test
	public void removeFromEmpty() {
		LinkedListIndexedCollection empty = new LinkedListIndexedCollection();
		Integer value = 10;

		Assert.assertFalse(empty.remove(value));

	}

	@Test
	public void containsEmpty() {
		LinkedListIndexedCollection empty = new LinkedListIndexedCollection();
		Integer value = 10;

		Assert.assertFalse(empty.contains(value));

	}

	@Test
	public void containsNull() {
		LinkedListIndexedCollection empty = new LinkedListIndexedCollection();
		Integer value = null;

		Assert.assertFalse(empty.contains(value));

	}

	@Test
	public void containsAdded() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Integer value1 = 10;
		Integer value2 = 20;

		collection.add(value1);
		collection.add(value2);

		Assert.assertTrue(collection.contains(value1));
		Assert.assertTrue(collection.contains(value2));
	}

	@Test
	public void clear() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Integer value1 = 10;
		Integer value2 = 20;

		collection.add(value1);
		collection.add(value2);

		collection.clear();
		int expectedSize = 0;

		Assert.assertNotNull(collection);
		Assert.assertEquals(expectedSize, collection.size());
	}

	@Test
	public void clearEmpty() {
		LinkedListIndexedCollection empty = new LinkedListIndexedCollection();

		empty.clear();
		int expectedSize = 0;

		Assert.assertNotNull(empty);
		Assert.assertEquals(expectedSize, empty.size());
	}

	@Test
	public void begginingInsert() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Integer value1 = 10;
		Integer value2 = 20;
		Integer value3 = 30;
		int index = 0;

		collection.add(value1);
		collection.add(value2);
		collection.insert(value3, index);

		int expectedSize = 3;

		Assert.assertEquals(expectedSize, collection.size());
		Assert.assertEquals(value3, collection.get(index));
	}

	@Test
	public void endInsert() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Integer value1 = 10;
		Integer value2 = 20;
		Integer value3 = 30;
		int index = 2;

		collection.add(value1);
		collection.add(value2);
		collection.insert(value3, index);

		int expectedSize = 3;

		Assert.assertEquals(expectedSize, collection.size());
		Assert.assertEquals(value3, collection.get(index));
	}

	@Test
	public void inBetweenInsert() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Integer value1 = 10;
		Integer value2 = 20;
		Integer value3 = 30;
		int index = 1;

		collection.add(value1);
		collection.add(value2);
		collection.insert(value3, index);

		int expectedSize = 3;

		Assert.assertEquals(expectedSize, collection.size());
		Assert.assertEquals(value3, collection.get(index));
	}

	@Test
	public void existingValueIndex() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Integer value1 = 10;
		Integer value2 = 20;
		Integer value3 = 30;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		int index1 = 0;
		int index2 = 1;
		int index3 = 2;

		Assert.assertEquals(index1, collection.indexOf(value1));
		Assert.assertEquals(index2, collection.indexOf(value2));
		Assert.assertEquals(index3, collection.indexOf(value3));
	}

	@Test
	public void nonExistingValueIndex() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Integer value1 = 10;
		Integer value2 = 20;
		Integer value3 = 30;

		collection.add(value1);
		collection.add(value2);

		int invalidIndex = -1;

		Assert.assertEquals(invalidIndex, collection.indexOf(value3));
	}

	@Test
	public void removeStart() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Integer value1 = 10;
		Integer value2 = 20;
		Integer value3 = 30;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		int index1 = 0;
		collection.remove(index1);

		int expectedSize = 2;

		Assert.assertEquals(expectedSize, collection.size());
		Assert.assertFalse(collection.contains(value1));
	}

	@Test
	public void removeInBetween() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Integer value1 = 10;
		Integer value2 = 20;
		Integer value3 = 30;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		int index1 = 1;
		collection.remove(index1);

		int expectedSize = 2;

		Assert.assertEquals(expectedSize, collection.size());
		Assert.assertFalse(collection.contains(value2));
	}

	@Test
	public void removeEnd() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Integer value1 = 10;
		Integer value2 = 20;
		Integer value3 = 30;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		int index1 = 2;
		collection.remove(index1);

		int expectedSize = 2;

		Assert.assertEquals(expectedSize, collection.size());
		Assert.assertFalse(collection.contains(value3));
	}

	@Test
	public void collectionToArray() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		Integer value1 = 10;
		Integer value2 = 20;
		Integer value3 = 30;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		Object[] array = collection.toArray();

		Assert.assertEquals(collection.size(), array.length);

		for (int i = 0; i < array.length; i++) {
			Assert.assertEquals(collection.get(i), array[i]);
		}
	}

	@Test
	public void emptyToArray() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

		Object[] array = collection.toArray();

		Assert.assertNotNull(array);
		Assert.assertEquals(collection.size(), array.length);
	}
}
