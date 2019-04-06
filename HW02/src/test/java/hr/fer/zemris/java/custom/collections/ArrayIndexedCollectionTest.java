package hr.fer.zemris.java.custom.collections;

import org.junit.Test;
import org.junit.Assert;

public class ArrayIndexedCollectionTest {

	@Test
	public void addToEmptyCollection() {
		ArrayIndexedCollection empty = new ArrayIndexedCollection();
		Integer value = 7;
		int startingSize = empty.size();

		empty.add(value);

		Assert.assertEquals(startingSize + 1, empty.size());
		Assert.assertTrue(empty.contains(value));
	}

	@Test
	public void addToFullCollection() {
		int capacity = 2;
		int expectedSize = 3;
		ArrayIndexedCollection full = new ArrayIndexedCollection(capacity);

		Integer value1 = 20;
		Integer value2 = 30;
		Integer value3 = 40;

		full.add(value1);
		full.add(value2);
		Assert.assertEquals(capacity, full.size());

		full.add(value3);
		Assert.assertTrue(full.contains(value3));
		Assert.assertEquals(expectedSize, full.size());
	}

	@Test(expected = NullPointerException.class)
	public void addNull() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		Object value = null;

		collection.add(value);
	}

	@Test
	public void addDuplicate() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		Integer value1 = 20;
		Integer value2 = 30;

		collection.add(value1);
		collection.add(value2);

		Integer value3 = 20;
		int expectedSize = 3;

		collection.add(value3);
		Assert.assertEquals(expectedSize, collection.size());
	}

	@Test
	public void getValidIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		Integer value1 = 20;
		Integer value2 = 30;

		collection.add(value1);
		collection.add(value2);

		Assert.assertEquals(value1, collection.get(0));
		Assert.assertEquals(value2, collection.get(1));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getGreaterIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		Integer value1 = 20;
		Integer value2 = 30;

		collection.add(value1);
		collection.add(value2);

		int invalidIndex = collection.size();
		collection.get(invalidIndex);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getLowerIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		Integer value1 = 20;
		Integer value2 = 30;

		collection.add(value1);
		collection.add(value2);

		int invalidIndex = -1;
		collection.get(invalidIndex);
	}

	@Test
	public void getZeroIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		Integer value1 = 20;
		Integer value2 = 30;

		collection.add(value1);
		collection.add(value2);

		int index = 0;
		Assert.assertEquals(value1, collection.get(index));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getFromEmptyCollection() {
		ArrayIndexedCollection empty = new ArrayIndexedCollection();

		empty.get(0);
	}

	@Test
	public void clearCollection() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		Integer value1 = 20;
		Integer value2 = 30;
		Integer value3 = 40;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		int expectedSize = 0;

		collection.clear();

		Assert.assertNotNull(collection);
		Assert.assertEquals(expectedSize, collection.size());
	}

	@Test
	public void clearEmptyCollection() {
		ArrayIndexedCollection empty = new ArrayIndexedCollection(3);
		int expectedSize = 0;

		empty.clear();

		Assert.assertNotNull(empty);
		Assert.assertEquals(expectedSize, empty.size());
	}

	@Test
	public void insertAtBeggining() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		Integer value1 = 20;
		Integer value2 = 30;
		Integer value3 = 40;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		int position = 0;
		Integer value = 50;
		int expectedSize = 4;

		collection.insert(value, position);

		Assert.assertTrue(collection.contains(value));
		Assert.assertEquals(value, collection.get(position));
		Assert.assertEquals(expectedSize, collection.size());
	}

	@Test
	public void insertAtEnd() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		Integer value1 = 20;
		Integer value2 = 30;
		Integer value3 = 40;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		int position = 3;
		Integer value = 50;
		int expectedSize = 4;

		collection.insert(value, position);

		Assert.assertTrue(collection.contains(value));
		Assert.assertEquals(value, collection.get(position));
		Assert.assertEquals(expectedSize, collection.size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void insertBiggerIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		Integer value1 = 20;
		Integer value2 = 30;
		Integer value3 = 40;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		int position = collection.size() + 1;
		Integer value = 50;

		collection.insert(value, position);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void insertLowerIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		Integer value1 = 20;
		Integer value2 = 30;
		Integer value3 = 40;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		int position = -1;
		Integer value = 50;

		collection.insert(value, position);
	}

	@Test(expected = NullPointerException.class)
	public void insertNull() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		Integer value1 = 20;
		Integer value2 = 30;
		Integer value3 = 40;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		int position = 1;
		Integer value = null;

		collection.insert(value, position);
	}

	@Test
	public void indexOfExistingValue() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		Integer value1 = 20;
		Integer value2 = 30;
		Integer value3 = 40;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		Integer value = 30;
		int expectedIndex = 1;

		Assert.assertEquals(expectedIndex, collection.indexOf(value));
	}

	@Test
	public void indexOfNonExistingValue() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		Integer value1 = 20;
		Integer value2 = 30;
		Integer value3 = 40;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		Integer value = 50;
		int expectedIndex = -1;

		Assert.assertEquals(expectedIndex, collection.indexOf(value));
	}

	@Test
	public void indexOfNull() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		Integer value1 = 20;
		Integer value2 = 30;
		Integer value3 = 40;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		Integer value = null;
		int expectedIndex = -1;

		Assert.assertEquals(expectedIndex, collection.indexOf(value));
	}

	@Test
	public void removeZeroIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		Integer value1 = 20;
		Integer value2 = 30;
		Integer value3 = 40;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		int expectedSize = 2;
		int removeIndex = 0;
		Integer removedValue = 20;

		collection.remove(removeIndex);

		Assert.assertEquals(expectedSize, collection.size());
		Assert.assertFalse(collection.contains(removedValue));
	}

	@Test
	public void removeLastIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		Integer value1 = 20;
		Integer value2 = 30;
		Integer value3 = 40;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		int expectedSize = 2;
		int removeIndex = collection.size() - 1;
		Integer removedValue = 40;

		collection.remove(removeIndex);

		Assert.assertEquals(expectedSize, collection.size());
		Assert.assertFalse(collection.contains(removedValue));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void removeBiggerIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		Integer value1 = 20;
		Integer value2 = 30;
		Integer value3 = 40;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		int removeIndex = collection.size();

		collection.remove(removeIndex);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void removeLowerIndex() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
		Integer value1 = 20;
		Integer value2 = 30;
		Integer value3 = 40;

		collection.add(value1);
		collection.add(value2);
		collection.add(value3);

		int removeIndex = -1;

		collection.remove(removeIndex);
	}

}
