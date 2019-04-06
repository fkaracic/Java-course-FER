package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

public class UniqueNumbersTest {
	private TreeNode head;

	@Before
	public void setUp() {
		head = new TreeNode();
		head.value = 50;

		head.left = new TreeNode();
		head.left.value = 42;

		head.right = new TreeNode();
		head.right.value = 55;
	}

	@Test
	public void emptyTreeSize() {
		int expected = 0;
		TreeNode root = null;

		Assert.assertEquals(expected, UniqueNumbers.treeSize(root));
	}

	@Test
	public void nonEmptyTreeSize() {
		int expected = 3;

		Assert.assertEquals(expected, UniqueNumbers.treeSize(head));
	}

	@Test
	public void addingToEmptyTree() {
		int value = 42;
		int expected = 42;
		TreeNode root = null;

		root = UniqueNumbers.addNode(root, value);

		Assert.assertNotNull(root);
		Assert.assertEquals(expected, root.value);
	}

	@Test
	public void addingNewToNonEmptyTree() {
		int greatestValue = 60;

		head = UniqueNumbers.addNode(head, greatestValue);

		Assert.assertNotNull(head.right.right);
		Assert.assertEquals(greatestValue, head.right.right.value);
	}

	@Test
	public void addingExisting() {
		int existing = 42;
		int expectedTreeSize = UniqueNumbers.treeSize(head);

		head = UniqueNumbers.addNode(head, existing);

		Assert.assertEquals(expectedTreeSize, UniqueNumbers.treeSize(head));
	}

	@Test
	public void searchEmptyTree() {
		int searchingValue = 50;
		TreeNode root = null;

		Assert.assertFalse(UniqueNumbers.containsValue(root, searchingValue));
	}

	@Test
	public void searchExistingNonEmptyTree() {
		int existingValue = 42;

		Assert.assertTrue(UniqueNumbers.containsValue(head, existingValue));
	}

	@Test
	public void searchNonExistingNonEmptyTree() {
		int nonExistingValue = 49;

		Assert.assertFalse(UniqueNumbers.containsValue(head, nonExistingValue));
	}
}
