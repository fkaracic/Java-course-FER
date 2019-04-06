package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * {@code SimpleHashtable} class implements a hash table, which maps keys to
 * values. Any non-{@code null} object can be used as a key and any object can
 * be used as a value.
 * <p>
 *
 * To successfully store and retrieve objects from a hash table, the objects
 * used as keys must implement the {@code hashCode} method and the
 * {@code equals} method.
 * <p>
 *
 * An instance of {@code SimpleHashtable} has two parameters that affect its
 * performance: <i>initial capacity</i> and <i>load factor</i>. The
 * <i>capacity</i> is the number of <i>buckets</i> in the hash table, and the
 * <i>initial capacity</i> is simply the capacity at the time the hash table is
 * created. <i>Initial capacity</i> must be in the form: two to the n-th power
 * where n is a non-negative integer. Hash table is <i>open</i>: in the case of
 * a "hash collision", a single bucket stores multiple entries, which must be
 * searched sequentially. The <i>load factor</i> is a measure of how full the
 * hash table is allowed to get before its capacity is automatically doubled.
 * <p>
 * 
 * The default load factor which offers a good tradeoff between time and space
 * costs is 0.75 and the default capacity is 16.
 * <p>
 * 
 * The iterator returned by the {@code iterator} method of this hash table has
 * its specific behavior: if the hash table is structurally modified at any time
 * after the iterator is created, in any way except through the iterator's own
 * {@code remove} method, the iterator will throw a
 * {@link ConcurrentModificationException}.
 * 
 * @param <K>
 *            the type of keys maintained by this map
 * @param <V>
 *            the type of mapped values
 *
 * @author Filip Karacic
 * 
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * This hash table data.
	 */
	private TableEntry<K, V>[] table;
	/**
	 * Number of entries in this hash table.
	 */
	private int size;
	/**
	 * Default capacity of this hash table.
	 */
	private static final int DEFAULT_CAPACITY = 16;
	/**
	 * Default load factor. The table is rehashed when {@code size / capacity}
	 * equals or is greater than load factor.
	 */
	private static final double LOAD_FACTOR = 0.75;

	/**
	 * The number of times this hash table has been structurally modified.
	 * Structural modifications are those that change the number of entries in this
	 * hash table or otherwise modify its internal structure (e.g., rehash).
	 */
	private int modificationCount;

	/**
	 * Constructs a new, empty hash table with default capacity (16) and default
	 * load factor (0.75).
	 *
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructs a new, empty hash table with the specified initial capacity and
	 * default load factor (0.75).
	 *
	 * @param capacity
	 *            the initial capacity of the hash table.
	 * @exception IllegalArgumentException
	 *                if the initial capacity is less than one.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException("Table size must be at least one. Was: " + capacity);

		size = 0;
		capacity = calculateTableSize(capacity);

		table = (TableEntry<K, V>[]) new TableEntry[capacity];
	}

	/**
	 * Returns the first power of the number two equal or greather than the given
	 * capacity.
	 * 
	 * @param capacity
	 *            capacity to be checked
	 * @return the first power of the number two equal or greather than the given
	 *         capacity
	 */
	private int calculateTableSize(int capacity) {
		int twoPotention = 1;

		while (twoPotention < capacity) {
			twoPotention <<= 1;
		}

		return twoPotention;
	}

	/**
	 * Represents one bucket of this hash table.
	 *
	 * @param <K>
	 *            the type of keys maintained by this map
	 * @param <V>
	 *            the type of mapped values
	 */
	public static class TableEntry<K, V> {
		/**
		 * Hash key of this bucket.
		 */
		private K key;
		/**
		 * Value of this ordered pair.
		 */
		private V value;
		/**
		 * Pointer to the next ordered pair in this bucket.
		 */
		private TableEntry<K, V> next;

		/**
		 * Initializes newly created object representing a bucket of this hash table.
		 * 
		 * @param key
		 *            hash key of this slot
		 * @param value
		 *            value of this ordered pair
		 * @param next
		 *            pointer to the next ordered pair in this bucket
		 * @throws NullPointerException
		 *             if the given key is {@code null}
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = Objects.requireNonNull(key);
			this.value = value;
			this.next = next;
		}

		/**
		 * Returns value of this ordered pair.
		 * 
		 * @return value of this ordered pair
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets value of this ordered pair to the given value.
		 * 
		 * @param value
		 *            new value for this ordered pair
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Returns the key of this bucket.
		 * 
		 * @return key of this bucket.
		 */
		public K getKey() {
			return key;
		}
	}

	/**
	 * Maps the specified {@code key} to the specified {@code value} in this hash
	 * table. The key cannot be {@code null} but the value can.
	 * <p>
	 * Hash table is <i>open</i>: in the case of a "hash collision", a single bucket
	 * stores multiple entries, which must be searched sequentially.
	 *
	 * @param key
	 *            the hash table key
	 * @param value
	 *            the value
	 * 
	 * @exception IllegalArgumentException
	 *                if the key is {@code null}
	 */
	public void put(K key, V value) {
		if (key == null)
			throw new IllegalArgumentException("Key cannot be null.");

		int slot = Math.abs(key.hashCode()) % table.length;

		if (table[slot] == null) {
			table[slot] = new TableEntry<K, V>(key, value, null);
			modificationCount++;
		} else {
			TableEntry<K, V> entry = table[slot];

			if (containsKey(key)) {
				while (!entry.getKey().equals(key)) {
					entry = entry.next;
				}
				entry.setValue(value);
				return;
			} else {
					while (entry.next != null) {
						entry = entry.next;
					}
					entry.next = new TableEntry<K, V>(key, value, null);
				
				modificationCount++;
			}
		}
		
		size++;

		if (Double.compare(size, LOAD_FACTOR * table.length) >= 0) {
			doubleCapacity();
		}

	}

	/**
	 * Rehashes the old table to the new one with double capacity.
	 */
	private void doubleCapacity() {

		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[2 * table.length];

		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> oldEntry = table[i];

			while (oldEntry != null) {
				int slot = Math.abs(oldEntry.key.hashCode()) % newTable.length;

				if (newTable[slot] == null) {
					newTable[slot] = new TableEntry<K, V>(oldEntry.key, oldEntry.value, null);
				} else {
					TableEntry<K, V> newEntry = newTable[slot];

					while (newEntry.next != null) {
						newEntry = newEntry.next;
					}

					newEntry.next = new TableEntry<K, V>(oldEntry.key, oldEntry.value, null);
				}

				oldEntry = oldEntry.next;
			}
		}

		table = newTable;
		modificationCount++;
	}

	/**
	 * Returns the value to which the specified key is mapped, or {@code null} if
	 * this map contains no mapping for the key.
	 *
	 * <p>
	 * More formally, if this map contains a mapping from a key {@code k} to a value
	 * {@code v} such that {@code (key.equals(k))}, then this method returns
	 * {@code v}; otherwise it returns {@code null}. (There can be at most one such
	 * mapping.)
	 *
	 * @param key
	 *            the key whose associated value is to be returned
	 * 
	 * @return the value to which the specified key is mapped, or {@code null} if
	 *         this map contains no mapping for the key
	 */
	public V get(K key) {
		if(key == null) return null;
		
		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> entry = table[slot];

		while (entry != null) {
			if (entry.key.equals(key))
				return entry.value;
			entry = entry.next;
		}

		return null;
	}

	/**
	 * Returns number of entries in this hash table.
	 * 
	 * @return number of entries in this hash table
	 */
	public int size() {
		return size;
	}

	/**
	 * Tests if the specified object is a key in this hash table.
	 *
	 * @param key
	 *            possible key
	 * @return {@code true} if the specified object is a key in this hash table, as
	 *         determined by the {@code equals} method, {@code false} otherwise.
	 */
	public boolean containsKey(Object key) {
		if (key == null)
			return false;

		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> entry = table[slot];

		while (entry != null) {
			if (entry.key.equals(key))
				return true;

			entry = entry.next;
		}

		return false;
	}

	/**
	 * Tests if some key maps into the specified value in this hash table. This
	 * operation is more expensive than the {@link #containsKey containsKey} method.
	 *
	 * @param value
	 *            a value to search for
	 * @return {@code true} if some key maps to the {@code value} argument in this
	 *         hash table as determined by the {@code equals} method, {@code false}
	 *         otherwise.
	 */
	public boolean containsValue(Object value) {

		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> entry = table[i];

			while (entry != null) {
				if (entry.value == null) {
					if (value == null)
						return true;
				} else {
					if (entry.value.equals(value))
						return true;
				}
				entry = entry.next;
			}
		}

		return false;
	}

	/**
	 * Removes the key (and its corresponding value) from this hash table. This
	 * method does nothing if the key is not in the hash table.
	 *
	 * @param key
	 *            the key that needs to be removed
	 */
	public void remove(K key) {
		if (!containsKey(key))
			return;

		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> entry = table[slot];

		if (entry.key.equals(key)) {
			table[slot] = entry.next;
		} else {
			while (!entry.next.key.equals(key)) {
				entry = entry.next;
			}

			entry.next = entry.next.next;
		}

		size--;
		modificationCount++;
	}

	/**
	 * Returns {@code true} if table contains no entries.
	 * 
	 * @return {@code true} if table contains no entries
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Clears this hash table so that it contains no keys.
	 */
	public void clear() {
		if (size == 0)
			return;

		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}

		size = 0;
		modificationCount++;
	}

	@Override
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("[");

		Iterator<SimpleHashtable.TableEntry<K, V>> iterator = iterator();

		while (iterator.hasNext()) {
			TableEntry<K, V> entry = iterator.next();
			build.append(entry.getKey() + "=" + entry.getValue());

			if (iterator.hasNext()) {
				build.append(",");
			}
		}

		build.append("]");

		return build.toString();
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Iterator for this hash table.
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/**
		 * Current index of this hash table.
		 */
		private int tableIndex;
		/**
		 * Current entry of this hash table.
		 */
		private TableEntry<K, V> current;
		/**
		 * Next entry of this hash table.
		 */
		private TableEntry<K, V> next;
		/**
		 * The copy of the number of times this hash table has been structurally
		 * modified.
		 */
		private int modificationCount;

		/**
		 * Initializes iterator for this hash map.
		 */
		public IteratorImpl() {
			tableIndex = 0;
			current = null;

			while (tableIndex < table.length && table[tableIndex] == null)
				tableIndex++;

			if (tableIndex < table.length)
				next = table[tableIndex];

			modificationCount = SimpleHashtable.this.modificationCount;
		}

		@Override
		public boolean hasNext() {
			if (modificationCount != SimpleHashtable.this.modificationCount)
				throw new ConcurrentModificationException();

			return next != null;
		}

		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (modificationCount != SimpleHashtable.this.modificationCount)
				throw new ConcurrentModificationException();

			if (next == null)
				throw new NoSuchElementException("No more elements.");

			current = next;

			if (next.next == null) {
				tableIndex++;
				while (tableIndex < table.length && table[tableIndex] == null)
					tableIndex++;
				if (tableIndex >= table.length) {

					next = null;
				} else {
					next = table[tableIndex];
				}
			} else {
				next = next.next;
			}

			return current;
		}

		@Override
		public void remove() {
			if (modificationCount != SimpleHashtable.this.modificationCount)
				throw new ConcurrentModificationException();

			if (current == null)
				throw new IllegalStateException("There is no element to be removed");

			SimpleHashtable.this.remove(current.key);
			modificationCount++;
			current = null;
		}
	}
}
