package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * {@code UniqueNumbers} provides adding or searching a value and calculating
 * amount of values. Binary tree is used as an auxiliary data structure.
 * 
 * @author Filip Karacic
 */
public class UniqueNumbers {

	/**
	 * Provides entering integers and adding them to the binary tree until 'kraj' is
	 * entered. It also prints every value of the binary tree sorted from the lowest
	 * to the greatest and then from the greatest to the lowest.
	 * 
	 * @param args
	 *            command line arguments represented as array of {@code string}
	 */
	public static void main(String[] args) {
		TreeNode head = null;

		try (Scanner sc = new Scanner(System.in)) {
			while (true) {
				System.out.printf("Unesite broj > ");
				String text = sc.nextLine();

				if (text.equals("kraj")) {
					break;
				}

				try {
					int value = Integer.parseInt(text);
					if (!containsValue(head, value)) {
						head = addNode(head, value);
						System.out.println("Dodano.");
					} else {
						System.out.println("Broj već postoji. Preskačem.");
					}
				} catch (NumberFormatException e) {
					System.out.println("\'" + text + "\'" + " nije cijeli broj.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.printf("Ispis od najmanjeg: ");
		printFromLowest(head);

		System.out.printf("\nIspis od najvećeg: ");
		printFromGreatest(head);
	}

	/**
	 * Auxiliary data structure that can be used as a node of the tree. The node
	 * contains integer value.
	 */
	public static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}

	/**
	 * Adds a value into the binary tree if the exact same value does not exist in
	 * the tree.
	 * 
	 * @param head
	 *            root of the tree
	 * @param value
	 *            value to be added to the tree
	 * @return root of the tree
	 */
	public static TreeNode addNode(TreeNode head, int value) {
		if (head == null) {
			head = new TreeNode();
			head.value = value;
			head.left = null;
			head.right = null;
		} else {
			if (head.value == value) {
				return head;
			} else if (head.value > value) {
				head.left = addNode(head.left, value);
			} else {
				head.right = addNode(head.right, value);
			}
		}

		return head;
	}

	/**
	 * Calculates size of the binary tree.
	 * 
	 * @param head
	 *            root of the tree
	 * @return size of the binary tree
	 */
	public static int treeSize(TreeNode head) {
		if (head == null) {
			return 0;
		} else {
			return 1 + treeSize(head.left) + treeSize(head.right);
		}
	}

	/**
	 * Searches for the given value in the tree.
	 * 
	 * @param head
	 *            root of the tree
	 * @param value
	 *            value that is searched for
	 * @return {@code true} if value is present in the tree, {@code false} otherwise
	 */
	public static boolean containsValue(TreeNode head, int value) {
		if (head == null) {
			return false;
		} else {
			if (head.value == value) {
				return true;
			} else {
				if (head.value > value) {
					return containsValue(head.left, value);
				} else {
					return containsValue(head.right, value);
				}
			}
		}
	}

	/**
	 * Prints all values of the tree sorted from the lowest to the greatest.
	 * 
	 * @param head
	 *            root of the tree
	 */
	public static void printFromLowest(TreeNode head) {
		if (head == null) {
			return;
		} else {
			printFromLowest(head.left);
			System.out.printf(head.value + " ");
			printFromLowest(head.right);
		}
	}

	/**
	 * Prints all values of the tree sorted from the greatest to the lowest.
	 * 
	 * @param head
	 *            root of the tree
	 */
	public static void printFromGreatest(TreeNode head) {
		if (head == null) {
			return;
		} else {
			printFromGreatest(head.right);
			System.out.printf(head.value + " ");
			printFromGreatest(head.left);
		}
	}
}
