package cs143.dataaccess;

import cs143.domain.Retiree;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A binary search tree for SsnAvl to extend.
 *
 * @author Kellan Blake
 * @author Harry Vu
 * @author Seonjun Mun
 * @author Seunghyeon Hwang
 */
public class BST implements Serializable {

    protected TreeNode root;
    protected int size = 0;

    /**
     * Default constructor
     */
    public BST() {
    }

    /**
     * Constructor taking an array of retiree objects.
     *
     * @param objects an array of retiree objects
     */
    public BST(Retiree[] objects) {
        for (Retiree object : objects) {
            insert(object);
        }
    }

    /**
     * Gets the retiree with the given SSN
     *
     * @param ssn the SSN the user enters for the retiree they want to find.
     * @return The retiree with the given SSN or null if they don't exist.
     */
    public Retiree get(long ssn) {
        TreeNode current = root;
        while (current != null) {
            if (ssn < current.element.getSsn()) {
                current = current.left;
            } else if (ssn > current.element.getSsn()) {
                current = current.right;
            } else {
                return current.element;
            }
        }
        return null;
    }

    /**
     * Inserts the given retiree into the BST
     *
     * @param r the retiree to be added
     * @return true if the retiree is successfully entered, false if not.
     */
    public final boolean insert(Retiree r) {
        if (root == null) {
            root = createNewNode(r);
        } else {
            TreeNode parent = null;
            TreeNode current = root;
            while (current != null) {
                if (r.compareTo(current.element) < 0) {
                    parent = current;
                    current = current.left;
                } else if (r.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                } else {
                    return false;
                }
            }
            if (r.compareTo(parent.element) < 0) {
                parent.left = createNewNode(r);
            } else {
                parent.right = createNewNode(r);
            }
        }
        size++;
        return true;
    }

    /**
     * Deletes the given retiree from the BST
     *
     * @param e the retiree to be deleted.
     * @return true if the retiree is deleted, false if not.
     */
    public boolean delete(Retiree e) {
        TreeNode parent = null;
        TreeNode current = root;
        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            } else if (e.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            } else {
                break;
            }
        }
        if (current == null) {
            return false;
        }
        if (current.left == null) {
            if (parent == null) {
                root = current.right;
            } else if (e.compareTo(parent.element) < 0) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        } else {
            TreeNode parentOfRightMost = current;
            TreeNode rightMost = current.left;
            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right;
            }
            current.element = rightMost.element;
            if (parentOfRightMost.right == rightMost) {
                parentOfRightMost.right = rightMost.left;
            } else {
                parentOfRightMost.left = rightMost.left;
            }
        }

        size--;
        return true;
    }

    /**
     * Creates a new tree node
     *
     * @param r the given retiree
     * @return a tree node containing the retiree passed.
     */
    protected TreeNode createNewNode(Retiree r) {
        return new TreeNode(r);
    }

    /**
     * Gets the size of the BST
     *
     * @return the size of the BST.
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the root of the BST
     *
     * @return the root of the BST
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * Clears the BST
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Checks if the BST is empty
     *
     * @return true if the BST is empty, false if not.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Creates and returns an array list of retirees
     *
     * @param r The retiree to check the path with.
     * @return an array list of retirees
     */
    public ArrayList<TreeNode> path(Retiree r) {
        ArrayList<TreeNode> list = new ArrayList<>();
        TreeNode current = root;

        while (current != null) {
            list.add(current);
            if (r.compareTo(current.element) < 0) {
                current = current.left;
            } else if (r.compareTo(current.element) > 0) {
                current = current.right;
            } else {
                break;
            }
        }
        return list;
    }

    /**
     * Inner class to make tree nodes
     */
    public static class TreeNode implements Serializable {

        public Retiree element;
        public TreeNode left;
        public TreeNode right;

        /**
         * Constructor
         *
         * @param r Retiree to make the tree node with
         */
        public TreeNode(Retiree r) {
            element = r;
        }
    }

}
