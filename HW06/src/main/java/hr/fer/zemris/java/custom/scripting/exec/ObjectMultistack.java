package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * {@code ObjectMultistack} represents a special kind of Map. It allows user to
 * store multiple values for the same key and it provides a stack-like
 * abstraction. Keys will be instances of the class String. Values that will be
 * associated with those keys will be instances of class ValueWrapper.
 * <p>
 * 
 * Values are permitted to be <code>null</code>, but keys are not.
 * 
 * @author Filip Karacic
 *
 */
public class ObjectMultistack {
	/**
	 * Structure used for mapping keys to the values.
	 */
	Map<String, MultistackEntry> multiStack;

	/**
	 * Initializes newly created "multistack".
	 */
	public ObjectMultistack() {
		multiStack = new HashMap<>();
	}

	/**
	 * Pushes the given value to this "multistack" within the given name.
	 * 
	 * @param name
	 *            name within value is stored
	 * @param valueWrapper
	 *            value to be pushed
	 * 
	 * @throws NullPointerException
	 *             if the given name or the given wrapper is <code>null</code>
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		Objects.requireNonNull(name, "Name within value is stored cannot be null");
		Objects.requireNonNull(valueWrapper, "Wrapper cannot be null");

		MultistackEntry entry = multiStack.get(name);

		MultistackEntry newEntry = new MultistackEntry(valueWrapper);
		newEntry.next = entry;

		multiStack.put(name, newEntry);
	}

	/**
	 * Returns the last value stored within the given name and removes it.
	 * 
	 * @param name
	 *            name within value is stored
	 * @return the last value stored within the given name
	 * @throws NullPointerException
	 *             if the given name is <code>null</code>
	 * @throws EmptyObjectMultiStackException
	 *             if there is no element stored within the given name
	 */
	public ValueWrapper pop(String name) {
		Objects.requireNonNull(name, "Name within value is stored cannot be null");

		MultistackEntry entry = multiStack.get(name);

		if (entry == null)
			throw new EmptyObjectMultiStackException("There is no value stored within the given name.");

		multiStack.put(name, entry.next);

		return entry.value;
	}

	/**
	 * Looks at the last value stored within the given name, without removing it.
	 * 
	 * @param name
	 *            name within value is stored
	 * @return the last value stored within the given name
	 * @throws NullPointerException
	 *             if the given name is <code>null</code>
	 * @throws EmptyObjectMultiStackException
	 *             if there is no element stored within the given name
	 */
	public ValueWrapper peek(String name) {
		Objects.requireNonNull(name, "Name within value is stored cannot be null");

		MultistackEntry entry = multiStack.get(name);

		if (entry == null)
			throw new EmptyObjectMultiStackException("There is no value stored within the given name.");

		return entry.value;
	}

	/**
	 * Returns <code>true</code> if no element is stored within the given name.
	 * 
	 * @param name
	 *            name within value is stored
	 * @return <code>true</code> if no element is stored within the given name
	 */
	public boolean isEmpty(String name) {
		return multiStack.get(name) == null;
	}

	/**
	 * Represents entry for this "multistack".
	 */
	private static class MultistackEntry {
		/**
		 * Value of this entry.
		 */
		private ValueWrapper value;
		/**
		 * Next entry.
		 */
		private MultistackEntry next;

		/**
		 * 
		 * Initializes newly created {@code MultiStackEntry} representing an entry of
		 * the "multistack".
		 * 
		 * @param value
		 *            value for this entry.
		 * @throws NullPointerException
		 *             if the given wrapper is <code>null</code>
		 */
		public MultistackEntry(ValueWrapper value) {
			Objects.requireNonNull(value, "Wrapper cannot be null");
			this.value = value;
		}

	}
}
