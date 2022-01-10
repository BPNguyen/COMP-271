package BinarySearchTrees;

/**
 * Brian Nguyen
 * COMP 271 - 003, F20
 * 06 - Binary Search Tree
 */

/** Import modules */
import java.util.ArrayList;
import java.util.List;

/**
 * A simple Binary Search Tree (BST) class.
 */
public class BST {

    // A tree is just a root, really! Everything grows from here.
    private TreeNode root; // what's a TreeNode? See below.

    // And here's what a TreeNode looks like.
    class TreeNode {
        String value; // The data we store in this node
        TreeNode left; // left child
        TreeNode right; // right child
        TreeNode parent; // parent
        // basic constructor
        public TreeNode(String s) {
            this.value = s; // assigns content to String
            left = right = parent = null; // makes pointers to children and parent null
        } // constructor TreeNode
    } // class TreeNode


    /**
     * Inserts unique value into tree; if value already
     * exists, method returns false.
     *
     * @param s value to insert
     */
    public boolean insert(String s) {
        boolean success = false;
        if (!valueExists(s)) { // Value is not stored in tree already; we can add it
            success = true; // Method will return this value to indicate successful insertion
            TreeNode newNode = new TreeNode(s); // Node with new value to be inserted
            if (root == null) { // If tree is empty,
                root = newNode; // new node becomes its root.
            } else { // Start our search from the root to find where to place new node.
                TreeNode currentNode = root; // We start our search from the root.
                boolean keepTrying = true; // Control variable from the principal loop, below.
                while (keepTrying) {  // Principal loop; exits only when keepTrying becomes false.
                    if (s.compareTo(currentNode.value) > 0) { // New value is greater than current node; go RIGHT
                        if (currentNode.right == null) { // If right child is null
                            currentNode.right = newNode; // place new value here
                            newNode.parent = currentNode; // Assign parent node
                            keepTrying = false; // Flag to exit the principal loop.
                        } else { // Right child is not null
                            currentNode = currentNode.right; // Make right child the current node and try again.
                        }
                    } else { // New value is less than current node; go LEFT
                        if (currentNode.left == null) { // If left child is null
                            currentNode.left = newNode; // place new value here.
                            newNode.parent = currentNode; // Assign parent node
                            keepTrying = false; // Flag to exit the principal loop.
                        } else { // Left child is not null.
                            currentNode = currentNode.left; // Make left child the current node and try again.
                        }
                    }
                }
            }
        }
        return success;
    } // method insert

    /**
     * Find if String searchForMe exists in the tree, in an iterative scan
     *
     * @param searchForMe Value to search for
     * @return true if searchForMe found; false otherwise
     */
    public boolean valueExists(String searchForMe) {
        boolean success = false; // Assume String is not in the tree.
        if (root != null) { // Start searching from the top.
            TreeNode currentNode = root; // initialize iterative node
            boolean keepTrying = true; // Loop control flag
            while (keepTrying) {
                if (currentNode.value.compareTo(searchForMe) == 0) { // found!
                    success = true; // flag success
                    // System.out.println(currentNode.parent.value);
                    keepTrying = false; // get out of the while loop
                } else if (searchForMe.compareTo(currentNode.value) > 0) { // Go right
                    if (currentNode.right == null) { // end of tree; no luck
                        keepTrying = false; // exit while loop
                    } else { // keep pushing right
                        currentNode = currentNode.right; // new value for next iteration
                    }
                } else { // Go left
                    if (currentNode.left == null) { // end of tree; no luck
                        keepTrying = false; // exit while loop
                    } else { // keep pushing left
                        currentNode = currentNode.left; // new value for next iteration
                    }
                }
            }
        }
        return success;
    } // method valueExists

    /**
     * Iterative in-Order traversal of the tree
     */
    public void inOrder() {
        if (root == null) { // empty tree
            System.out.println("Tree is empty");
        } else {
            System.out.println("\nIn-Order traversal of your tree:\n");
            int wordCount = 1; // tracks how many words are printed before new line
            int wordPerLine = 5; // I want this many words per line
            List<TreeNode> nodesToProcess = new ArrayList<TreeNode>(); // Simple "stack"
            // Start from the top
            TreeNode currentNode = root;
            // The following loop traverses while there are items in the "stack"
            while ( currentNode != null || nodesToProcess.size() > 0 ) {
                while (currentNode != null) {
                    nodesToProcess.add(0,currentNode);
                    currentNode = currentNode.left; // Go as left as you can
                }
                currentNode = nodesToProcess.get(0); // When no more left, print what's on top of the stack
                System.out.printf("%-15s ",currentNode.value);
                if ( wordCount%wordPerLine==0 ) {
                    System.out.printf("\n");
                }
                wordCount++;
                nodesToProcess.remove(0); // remove the current node from the stack
                currentNode = currentNode.right; // go right
            }
        }
    } // method inOrder

    /**
     * Method to find the smallest node of a tree (or subtree). The smallest node is the
     * left-most node of the tree (or subtree).
     * @param node the root of the tree or subtree we wish to scan
     * @return the node with the smallest value
     */
    public TreeNode minNode(TreeNode node) {
        TreeNode current = node;
        while ( current.left != null) { // Keep going left until no more
            current = current.left;
        }
        return current; // this is the smallest node
    } // method minNode

    /**
     * Method successor finds, iteratively, the node with the next highest value from the
     * node provided. If the node whose successor we seek has a right subtree, the successor
     * is the smallest node of that subtree. Otherwise, we start from the root, towards
     * the node whose successor we seek. Every time we go left at a node, we mark that
     * node as the successor.
     * @param ofThisNode Node whose successor we are seeking.
     * @return The node's successor; null if it has no successor
     */
    public TreeNode successor(TreeNode ofThisNode) {
        TreeNode succ = null;
        if ( ofThisNode.right != null) { // Node whose successor we seek, has a right subtree.
            succ = minNode(ofThisNode.right); // Successor is smallest node of right subtree.
        } else { //
            TreeNode current = root; // Start from root and go towards node whose successor we seek.
            boolean keepTraversing = true; // Switch to exit the while loop when done
            while (keepTraversing) {
                if ( ofThisNode.value.compareTo(current.value ) < 0 ) { // Node whose successor we seek should be to the left.
                    if ( current.left != null ) { // Can we go left?
                        succ = current; // Mark this node as successor
                        current = current.left; // Go left
                    } else { // We can no longer go left -- end of tree?
                        keepTraversing = false; // Signal to exit the while loop.
                    }
                } else { // Node whose successor we seek should be to the right.
                    if ( current.right != null ) { // Can we go right?
                        current = current.right; // Go right
                    } else { // We can no longer go right -- end of tree?
                        keepTraversing = false; // Signal to exit while loop.
                    }
                } // Done deciding left/right as we search for the node whose successor we seek.
            } // Done traversing the tree
        } // Done looking for the successor; we have it (or we end up with null, ie, end of tree).
        return succ;
    } // method successor

    /**
     * 'successorP' method
     * 
     * Finds the node with the next highest value from the node provided, given its parent node. 
     * If the node has a right subtree, the successor is the smallest node of that subtree.
     * If the left child of the node's parent is the node in question, then the parent is the successor.
     * Otherwise, the node is on the right subtree of its parent, so continue traversing tree 
     * upwards until a left child is found.
     * 
     * @param ofThisNode Node whose successor we are seeking.
     * @return The node's successor; null if it has no successor
     */
    public TreeNode successorP(TreeNode ofThisNode) {

        // TreeNode successor placeholder
        TreeNode succ = null;

        // If node in question has a right subtree...
        if (ofThisNode.right != null) {

            // Assign smallest node of right subtree as successor
            succ = minNode(ofThisNode.right);
        } else {

            // Parent of node in question
            TreeNode parent = ofThisNode.parent;

            // Continuation key
            boolean keepTraversing = true;

            while (keepTraversing && parent != null) {

                // If parent's left node is equal to node in question...
                if (parent.left.value.compareTo(ofThisNode.value) == 0) {

                    // Assign parent node as successor
                    succ = parent;

                    // Disable continuation key
                    keepTraversing = false;
                } else {

                    // Traverse tree upwards through parent
                    ofThisNode = parent;
                }
            }
        }

        // Return successor
        return succ;
    }

    /**
     * 'deleteNode' method
     * 
     * Deletes a specified node from its BST, as long as the node exists and is not null.
     * If the node has no children, it is simply dereferenced from its parent.
     * If the node has only one child, its child is adopted by its parent in its place.
     * If the node has both children, the node is swapped with its successor, 
     * the newly-swapped successor node is recursively deleted.
     * 
     * @param deleteMe Node to be deleted.
     * @return True if node is successfully deleted, false if not
     */
    public boolean deleteNode(TreeNode deleteMe) {

        // Successful deletion key
        boolean success = false;

        while (!success) {

            // Ensure node is not null
            if (deleteMe != null) {
            
                // If node does not have children...
                if (deleteMe.left == null && deleteMe.right == null) {
                    
                    // If node is the root...
                    if (deleteMe == root) {

                        // Set root to null
                        root = null;
                    } else {

                        // Parent of node in question
                        TreeNode parent = deleteMe.parent;

                        // Check if node is the left child of the parent, otherwise it is the right child
                        if (parent.left.value.compareTo(deleteMe.value) == 0) {

                            // Dereference node from parent
                            parent.left = null;

                        } else {

                            // Dereference node from parent
                            parent.right = null;
                        }
                    }

                    // Enable success key
                    success = true;
                }

                // If node has only a left child...
                if (deleteMe.right == null && deleteMe.left != null) {

                    // If node is the root...
                    if (deleteMe == root) {

                        // Assign its left child as the root
                        root = root.left;
                    }

                    // Parent of node in question
                    TreeNode parent = deleteMe.parent;

                    // If node is the left child of parent, otherwise it is the right child
                    if (parent.left.value.compareTo(deleteMe.value) == 0) {

                        // Assign its parent's left child as the node's left child
                        parent.left = deleteMe.left;
                    } else {

                        // Assign its parent's right child as the node's left child
                        parent.right = deleteMe.left;
                    }

                    // Enable success key
                    success = true;
                }

                // If node has a only right child...
                if (deleteMe.left == null && deleteMe.right != null) {

                    // If node is the root...
                    if (deleteMe == root) {

                        // Assign its right child as the root
                        root = root.right;
                    }

                    // Parent of node in question
                    TreeNode parent = deleteMe.parent;

                    // If node is the left child of parent, otherwise it is the right child
                    if (parent.left.value.compareTo(deleteMe.value) == 0) {

                        // Assign its parent's left child as the node's right child
                        parent.left = deleteMe.right;
                    } else {

                        // Assign its parent's right child as the node's right child
                        parent.right = deleteMe.right;
                    }

                    // Enable success key
                    success = true;
                }

                // If node has both children...
                if (deleteMe.left != null & deleteMe.right != null) {

                    // TreeNode swap placeholder
                    TreeNode swap = new TreeNode(deleteMe.value);

                    // Successor of node in question
                    TreeNode succ = successorP(deleteMe);

                    // Swap node's value with successor's value
                    deleteMe.value = successorP(deleteMe).value;

                    // Swap successor's value with original node's value
                    succ.value = swap.value;

                    // Recursively delete successor node
                    success = deleteNode(succ);
                }
            }
        }

        // Return successful deletion key
        return success;
    }

    /** Main method */
    public static void main (String[]args){

        // Instance of BST 'maple'
        BST maple = new BST();

        // Insert nodes into 'maple'
        maple.insert("b");
        maple.insert("a");
        maple.insert("c");
        maple.insert("d");
        maple.insert("b2");
        maple.insert("d2");
        maple.insert("c2");

        // Display contents of 'maple' in-order
        System.out.println("    ~ Initial contents of BST");
        maple.inOrder();
        System.out.println("\n\n" + "-".repeat(50));

        // Node to be deleted
        TreeNode node = maple.root.right;

        // Node information
        System.out.println("\n    ~ Node-to-be-Deleted's Information\n");
        System.out.println("Node: " + node.value);
        System.out.println("Parent: " + node.parent.value);
        System.out.println("Successor: " + maple.successorP(node).value);
        System.out.println("\n" + "-".repeat(50));

        // Deletion status
        System.out.println("\n    ~ Post-deletion contents of BST\n");
        System.out.println("Successful deletion? " + maple.deleteNode(node));

        // Display contents of 'maple' post-deletion of 'node'
        maple.inOrder();

    } // method main

} // class BST
