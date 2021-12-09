

public class BST<E extends Comparable<E>> {
	private TreeNode root;
	private int size;
	
	private class TreeNode{
		E value;
		TreeNode left;
		TreeNode right;
		TreeNode(E val){
			value = val;
			left = right = null;
		}
	}
	BST(){  // O(1)
	     root = null;
	     size = 0; 
	  }
	public int size() { // O(1)
	  return size; 
	}
	public boolean isEmpty() { // O(1)
	    return (size == 0); 
	}
	public void clear() { // O(1)
	    root = null; size=0;
	}
	// Search method
	public int contains(E item) { // O(log n) - O(n)
		int iterations = 0;
		TreeNode node = root;
		while (node != null) {
			iterations++;
			if(item.compareTo(node.value) < 0) // go left
				node = node.left;
			else if (item.compareTo(node.value)> 0)// go right
				node = node.right;
			else// equal
				return iterations;
		}
		return iterations;
	}
	
	// Method add()
	public int add(E item) {// O(log n) - O(n)
		int iterations = 0;
		if (root == null) // first node to be inserted
			root = new TreeNode(item);
		else {
			TreeNode parent, node;
			parent = null; node = root;
			while (node != null) {// Looking for a leaf node
				iterations++;
				parent = node;
				if(item.compareTo(node.value) < 0) {
					node = node.left; }
				else if (item.compareTo(node.value) > 0) {
					node = node.right; }
				else
					return iterations; // duplicates are not allowed
			}
			if (item.compareTo(parent.value)< 0)
				parent.left = new TreeNode(item);
			else
				parent.right = new TreeNode(item);
		}
		size++;
		return iterations; 
	}
	
	// Method remove()
	public int remove(E item) { // O(log n) - O(n)
		TreeNode parent, node;
		parent = null;
		node = root;
		int iterations = 0;
		// Find item first
		while (node != null) {
			iterations++;
			if (item.compareTo(node.value) < 0) { // go left
				parent = node;
				node = node.left;
			} else if (item.compareTo(node.value) > 0) { // go right
				parent = node;
				node = node.right;
			} else // equal
				break; // item found
		}
		if (node == null) // item not in the tree
			return iterations;

		// Case 1: node has no children
		if (node.left == null && node.right == null) {
			if (parent == null) { // delete root
				root = null;
			} else {
				changeChild(parent, node, null);
			}
		}
		//case 2: one right child
		else if (node.left == null) {
			if (parent == null) { // delete root
				root = node.right;
			} else {
				changeChild(parent, node, node.right);
			}
		}
		//case 2: one left child
		else if (node.right == null) {
			if (parent == null) { // delete root
				root = node.left;
			} else {
				changeChild(parent, node, node.left);
			}
		}
		//Case 3: node has two children
		else {
			TreeNode rightMostParent = node;
			TreeNode rightMost = node.left;
			// go right on the left subtree
			while (rightMost.right != null) {
				iterations++;
				rightMostParent = rightMost;
				rightMost = rightMost.right;
			}
			// copy the value of rigthMost to node
			node.value = rightMost.value;
			// delete rigthMost
			changeChild(rightMostParent, rightMost, rightMost.left);
		}
		size--;
		return iterations;
	}

	private void changeChild(TreeNode parent, 
							 TreeNode node, 
							 TreeNode newChild) { // O(1)
		if (parent.left == node)
			parent.left = newChild;
		else
			parent.right = newChild;
	}
	
	// Recursive method inorder()
	public void inorder() { // O(n)
		inorder(root);
	}
	private void inorder(TreeNode node) {// O(n)
		if (node != null) {
			inorder(node.left);
			System.out.print(node.value + " ");
			inorder(node.right);
		}
	}
	// Recursive method preorder()
	public void preorder() {// O(n)
		preorder(root);
	}
	private void preorder(TreeNode node) {// O(n)
		if (node != null) {
			System.out.print(node.value + " ");
			preorder(node.left);
			preorder(node.right);
		}
	}
	// Recursive method postorder()
	public void postorder() {// O(n)
		postorder(root);
	}
	private void postorder(TreeNode node)  {// O(n)
		if (node != null) {
			postorder(node.left);
			postorder(node.right);
			System.out.print(node.value + " ");	
		}
	}
}
