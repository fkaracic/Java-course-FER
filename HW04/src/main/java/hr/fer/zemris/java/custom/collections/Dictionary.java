package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Class {@code Dictionary} is used for storage of values by the given key.
 * Having two or more same keys is not permitted and also key cannot be
 * {@code null}. Value,on the other hand, can be {@code null}.
 * 
 * @author Filip Karacic
 *
 */
public class Dictionary {
	/**
	 * Collection used for storage of dictionary's elements.
	 */
	private ArrayIndexedCollection dictionary;

	/**
	 * Initializes newly created {@code Dictionary} object representing storage of
	 * values by the given key.
	 */
	public Dictionary() {
		dictionary = new ArrayIndexedCollection();
	}

	/**
	 * Auxiliary data structure.
	 *
	 */
	private static class Data {
		/**
		 * Key of this element.
		 */
		Object key;
		/**
		 * Value of this element.
		 */
		Object value;

		/**
		 * Initializes newly created object to represent an element containing key and
		 * value.
		 * 
		 * @param key key for element
		 * @param value value for this element
		 * 
		 * throws NullPointerException if key is {@code null}
		 */
		public Data(Object key, Object value) {
			this.key = Objects.requireNonNull(key,"Key cannot be null.");
			this.value = value;
		}

		/**
		 * Returns key of this element.
		 * 
		 * @return key of this element
		 */
		public Object getKey() {
			return key;
		}

		/**
		 * Returns value of this element.
		 * 
		 * @return value of this element
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * Sets value of this element to {@code value}.
		 * 
		 * @param value value to be set for this element
		 */
		public void setValue(Object value) {
			this.value = value;
		}

	}

	/**
	 * Returns {@code true} if this dictionary contains no elements.
	 * 
	 * @return {@code true} if this dictionary contains no elements
	 */
	public boolean isEmpty() {
		return dictionary.size() == 0;
	}

	/**
	 * Returns number of elements currently in this dictionary.
	 * 
	 * @return number of elements currently in this dictionary
	 */
	public int size() {
		return dictionary.size();
	}

	/**
	 * Removes all of the elements from this dictionary. The dictionary will be
	 * empty after this method returns.
	 */
	public void clear() {
		dictionary.clear();
	}

	/**
	 * Adds the given value in the dictionary by the given key if this dictionary
	 * does not contain any element with the given key. If this dictionary contains
	 * element with the given key, value of that element is re-written with the
	 * given value.
	 * 
	 * @param key
	 *            key to insert value at
	 * @param value
	 *            value to be added to the dictionary
	 * 
	 * @throws NullPointerException
	 *             if {@code key} is {@code null}
	 */
	public void put(Object key, Object value) {
		Objects.requireNonNull(key);

		Data data = existingKey(key);
		if (data == null) {
			data = new Data(key, value);
			dictionary.add(data);
		} else {
			data.setValue(value);
		}

	}

	/**
	 * Returns value stored by the given key, or {@code null} if this dictionary
	 * does not contain this key. It also returns {@code null} if the key is
	 * {@code null}.
	 * 
	 * @param key
	 *            key whose value is searched for
	 * @return value stored by the given key, or {@code null} if this dictionary
	 *         does not contain this key
	 */
	public Object get(Object key) {
		if (key == null)
			return null;

		Data data = existingKey(key);

		if (data == null)
			return null;

		return data.getValue();
	}

	/**
	 * Searches for the given key in this dictionary. Returns element with the given
	 * key or {@code null} if this dictionary does not contain element with this
	 * key.
	 * 
	 * @param key
	 *            key to be searched for
	 * @return element with the given key or {@code null} if this dictionary does
	 *         not contain element with this key.
	 */
	private Data existingKey(Object key) {
		if (key == null)
			return null;

		for (int i = 0, h = dictionary.size(); i < h; i++) {
			Data data = (Data) dictionary.get(i);
			if (data.getKey().equals(key)) {
				return data;
			}
		}

		return null;
	}
}