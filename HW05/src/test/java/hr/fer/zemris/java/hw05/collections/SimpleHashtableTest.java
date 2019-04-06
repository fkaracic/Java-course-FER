package hr.fer.zemris.java.hw05.collections;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;

public class SimpleHashtableTest {
	
	@Test
	public void emptyTable() {
		int expectedSize = 0;
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		Assert.assertTrue(table.isEmpty());
		Assert.assertEquals(expectedSize, table.size());
	}
	
	@Test
	public void nonEmptyTable() {
		int expectedSize = 1;
		
		String key = "key";
		String value = "value";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key, value);
		
		Assert.assertFalse(table.isEmpty());
		Assert.assertEquals(expectedSize, table.size());
	}

	@Test (expected = IllegalArgumentException.class)
	public void negativeCapacity() {
		int capacity = -1;
		new SimpleHashtable<String, String>(capacity);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void zeroCapacity() {
		int capacity = 0;
		new SimpleHashtable<String, String>(capacity);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void putNullKey() {
		String key = null;
		String value = "value";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key, value);
	}
	
	@Test
	public void putNullValue() {
		String key = "key";
		String value = null;
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key, value);
		
		Assert.assertTrue(table.containsKey(key));
		Assert.assertTrue(table.containsValue(value));
	}
	
	@Test
	public void putNonExistingKey() {
		String key = "key";
		String value = "value";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key, value);
		
		Assert.assertTrue(table.containsKey(key));
		Assert.assertTrue(table.containsValue(value));
	}
	
	@Test
	public void putExistingKey() {
		String key = "key";
		String value = "value";
		
		String key2 = "key";
		String value2 = "value2";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key, value);
		table.put(key2, value2);
		
		Assert.assertEquals(value2, table.get(key));
	}
	
	@Test
	public void getNullKey() {
		String key = "key";
		String value = "value";
		String searchKey = null;
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key, value);
		
		Assert.assertNull(table.get(searchKey));
	}
	
	@Test
	public void getNonExistingKey() {
		String key = "key1";
		String value = "value1";
		String searchKey = "key2";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key, value);
		
		Assert.assertNull(table.get(searchKey));
	}
	
	@Test
	public void getExistingKey() {
		String key1 = "key1";
		String value1 = "value1";
		String key2 = "key2";
		String value2 = "value2";
		String key3 = "key3";
		String value3 = "value3";
		
		String searchKey = "key2";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key1, value1);
		table.put(key2, value2);
		table.put(key3, value3);
		
		Assert.assertEquals(value2, table.get(searchKey));
	}
	
	@Test
	public void containsExistingKey() {
		String key1 = "key1";
		String value1 = "value1";
		String key2 = "key2";
		String value2 = "value2";
		String key3 = "key3";
		String value3 = "value3";
		
		String searchKey = "key2";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key1, value1);
		table.put(key2, value2);
		table.put(key3, value3);
		
		Assert.assertTrue(table.containsKey(searchKey));
	}
	
	@Test
	public void containsNonExistingKey() {
		String key = "key";
		String value = "value";
		
		String searchKey = "key2";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key, value);
		
		Assert.assertFalse(table.containsKey(searchKey));
	}
	
	@Test
	public void containsNullKey() {
		String key = "key";
		String value = "value";
		
		String searchKey = null;
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key, value);
		
		Assert.assertFalse(table.containsKey(searchKey));
	}
	
	@Test
	public void containsExistingValue() {
		String key1 = "key1";
		String value1 = "value1";
		String key2 = "key2";
		String value2 = "value2";
		String key3 = "key3";
		String value3 = "value3";
		
		String searchValue = "value3";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key1, value1);
		table.put(key2, value2);
		table.put(key3, value3);
		
		Assert.assertTrue(table.containsValue(searchValue));
	}
	
	@Test
	public void containsNonExistingNullValue() {
		String key = "key";
		String value = "value";
		
		String searchKey = null;
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key, value);
		
		Assert.assertFalse(table.containsValue(searchKey));
	}
	
	@Test
	public void containsExistingNullValue() {
		String key1 = "key1";
		String value1 = "value1";
		String key2 = "key2";
		String value2 = "value2";
		String key3 = "key3";
		String value3 = null;
		
		String searchValue = null;
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key1, value1);
		table.put(key2, value2);
		table.put(key3, value3);
		
		Assert.assertTrue(table.containsValue(searchValue));
	}

	@Test
	public void containsNonExistingValue() {
		String key1 = "key1";
		String value1 = "value1";
		String key2 = "key2";
		String value2 = "value2";
		String key3 = "key3";
		String value3 = "value3";
		
		String searchValue = "value4";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key1, value1);
		table.put(key2, value2);
		table.put(key3, value3);
		
		Assert.assertFalse(table.containsValue(searchValue));
	}
	
	@Test
	public void removeNonExistingKey() {
		String key1 = "key1";
		String value1 = "value1";
		String key2 = "key2";
		String value2 = "value2";
		String key3 = "key3";
		String value3 = "value3";
		
		String removeKey = "key4";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key1, value1);
		table.put(key2, value2);
		table.put(key3, value3);
		
		table.remove(removeKey);
		
		int expectedSize = 3;
		
		Assert.assertEquals(expectedSize, table.size());
	}
	
	@Test
	public void removeNullKey() {
		String key1 = "key1";
		String value1 = "value1";
		String key2 = "key2";
		String value2 = "value2";
		String key3 = "key3";
		String value3 = "value3";
		
		String removeKey = null;
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key1, value1);
		table.put(key2, value2);
		table.put(key3, value3);
		
		table.remove(removeKey);
		
		int expectedSize = 3;
		
		Assert.assertEquals(expectedSize, table.size());
	}
	
	@Test
	public void removeExistingKey() {
		String key1 = "key1";
		String value1 = "value1";
		String key2 = "key2";
		String value2 = "value2";
		String key3 = "key3";
		String value3 = "value3";
		
		String removeKey = "key2";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		table.put(key1, value1);
		table.put(key2, value2);
		table.put(key3, value3);
		
		table.remove(removeKey);
		
		int expectedSize = 2;
		
		Assert.assertEquals(expectedSize, table.size());
		Assert.assertFalse(table.containsKey(key2));
		Assert.assertFalse(table.containsValue(value2));
	}
	
	@Test
	public void emptyTableIterator() {		
		SimpleHashtable<String, String> table = new SimpleHashtable<>();
		
		Iterator<SimpleHashtable.TableEntry<String, String>> iterator = table.iterator();
		
		Assert.assertFalse(iterator.hasNext());
	}
	
	@Test (expected = NoSuchElementException.class)
	public void nonEmptyTableIterator() {
		String key1 = "key1";
		String value1 = "value1";
		String key2 = "key2";
		String value2 = "value2";
		String key3 = "key3";
		String value3 = "value3";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>(8);
		
		table.put(key1, value1);
		table.put(key2, value2);
		table.put(key3, value3);
		
		Iterator<SimpleHashtable.TableEntry<String, String>> iterator = table.iterator();
		
		iterator.next();
		iterator.next();
		iterator.next();
		
		iterator.next(); // this one should cause NoSuchElementException
	}
	
	@Test
	public void iteratorRemove() {
		String key1 = "key1";
		String value1 = "value1";
		String key2 = "key2";
		String value2 = "value2";
		String key3 = "key3";
		String value3 = "value3";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>(8);
		
		table.put(key1, value1);
		table.put(key2, value2);
		table.put(key3, value3);
		
		Iterator<SimpleHashtable.TableEntry<String, String>> iterator = table.iterator();
		
		iterator.next();
		iterator.remove();
		
		iterator.next();
		iterator.remove();
		
		iterator.next();
		iterator.remove();
		
		Assert.assertTrue(table.isEmpty());
	}
	
	@Test (expected = IllegalStateException.class)
	public void iteratorRemoveTwice() {
		String key1 = "key1";
		String value1 = "value1";
		String key2 = "key2";
		String value2 = "value2";
		String key3 = "key3";
		String value3 = "value3";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>(8);
		
		table.put(key1, value1);
		table.put(key2, value2);
		table.put(key3, value3);
		
		Iterator<SimpleHashtable.TableEntry<String, String>> iterator = table.iterator();
		
		iterator.next();
		iterator.remove();
		iterator.remove();
	}
	
	@Test (expected = ConcurrentModificationException.class)
	public void modificationWhileIterating() {
		String key1 = "key1";
		String value1 = "value1";
		String key2 = "key2";
		String value2 = "value2";
		String key3 = "key3";
		String value3 = "value3";
		
		SimpleHashtable<String, String> table = new SimpleHashtable<>(8);
		
		table.put(key1, value1);
		table.put(key2, value2);
		table.put(key3, value3);
		
		Iterator<SimpleHashtable.TableEntry<String, String>> iterator = table.iterator();
		
		iterator.next();
		table.remove(key2);
		
		iterator.next();
	}
	
	@Test
	public void testForEachUpdateExistingEntry() {
		String key1 = "key1";
		Integer value1 = 5;
		String key2 = "key2";
		Integer value2 = 5;
		String key3 = "key3";
		Integer value3 = 5;
		
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		
		table.put(key1, value1);
		table.put(key2, value2);
		table.put(key3, value3);
		
		for (SimpleHashtable.TableEntry<String, Integer> entry : table) {
			if (entry.getValue().equals(5)) {
				// must NOT throw
				table.put(entry.getKey(), 4);
			}
		}
	}
	
}
