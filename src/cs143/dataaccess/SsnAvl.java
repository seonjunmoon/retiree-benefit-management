package cs143.dataaccess;

import cs143.domain.Retiree;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * AVL tree class for handling retiree.
 *
 * @author Kellan Blake
 * @author Harry Vu
 * @author Seonjun Mun
 * @author Seunghyeon Hwang
 */
public final class SsnAvl extends BST implements Serializable {

    /**
     * Default Constructor
     */
    public SsnAvl() {
    }

    /**
     * Constructor that takes an array of Retiree objects
     *
     * @param objects an array of retiree objects.
     */
    public SsnAvl(Retiree[] objects) {
        for (Retiree r : objects) {
            add(r);
        }
    }

    /**
     * Adds the given retiree to the AVL
     *
     * @param r the retiree to be added
     * @return true if the retiree is added, false if not.
     */
    public boolean add(Retiree r) {
        boolean successful = super.insert(r);
        if (!successful) {
            return false;
        } else {
            balancePath(r);
        }
        return true;
    }

    /**
     * Removes a retiree from the AVL
     *
     * @param r the retiree to be deleted
     * @return true if removed, false if not
     */
    public boolean remove(Retiree r) {
        if (root == null) {
            return false;
        }
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
                break;
            }
        }
        if (current == null) {
            return false;
        }
        if (current.left == null) {
            if (parent == null) {
                root = current.right;
            } else {
                if (r.compareTo(parent.element) < 0) {
                    parent.left = current.right;
                } else {
                    parent.right = current.right;
                }
                balancePath(parent.element);
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
            balancePath(parentOfRightMost.element);
        }
        size--;
        return true;
    }

    /**
     * Creates a new AVLTreeNode with the given retiree
     *
     * @param r the given retiree
     * @return the new AVLTreeNode made with the retiree
     */
    @Override
    protected AVLTreeNode createNewNode(Retiree r) {
        return new AVLTreeNode(r);
    }

    /**
     * Updates the height of the passed AVLTreeNode
     *
     * @param node the passed AVLTreeNode
     */
    private void updateHeight(AVLTreeNode node) {
        if (node.left == null && node.right == null) {
            node.height = 0;
        } else if (node.left == null) {
            node.height = 1 + ((AVLTreeNode) (node.right)).height;
        } else if (node.right == null) {
            node.height = 1 + ((AVLTreeNode) (node.left)).height;
        } else {
            node.height = 1
                    + Math.max(((AVLTreeNode) (node.right)).height,
                            ((AVLTreeNode) (node.left)).height);
        }
    }

    /**
     * Balances the AVL tree using the path method in BST
     *
     * @param r the retiree to be used when balancing.
     */
    private void balancePath(Retiree r) {
        ArrayList<TreeNode> path = path(r);
        for (int i = path.size() - 1; i >= 0; i--) {
            AVLTreeNode a = (AVLTreeNode) (path.get(i));
            updateHeight(a);
            AVLTreeNode parentOfA = (a == root) ? null
                    : (AVLTreeNode) (path.get(i - 1));
            switch (balanceFactor(a)) {
                case -2:
                    if (balanceFactor((AVLTreeNode) a.left) <= 0) {
                        balanceLL(a, parentOfA);
                    } else {
                        balanceLR(a, parentOfA);
                    }
                    break;
                case +2:
                    if (balanceFactor((AVLTreeNode) a.right) >= 0) {
                        balanceRR(a, parentOfA);
                    } else {
                        balanceRL(a, parentOfA);
                    }
            }
        }
    }

    /**
     * Gets the balance factor of the AVLTreeNode
     *
     * @param node the passed AVLTreeNode
     * @return the balance factor of the AVLTreeNode
     */
    private int balanceFactor(AVLTreeNode node) {
        if (node.right == null) {
            return -node.height;
        } else if (node.left == null) {
            return +node.height;
        } else {
            return ((AVLTreeNode) node.right).height
                    - ((AVLTreeNode) node.left).height;
        }
    }

    /**
     * Balances the tree if there is a left left imbalance.
     *
     * @param a The TreeNode to be balanced.
     * @param parentOfA The parent of the tree node that needs balancing.
     */
    private void balanceLL(TreeNode a, TreeNode parentOfA) {
        TreeNode b = a.left;
        if (a == root) {
            root = b;
        } else if (parentOfA.left == a) {
            parentOfA.left = b;
        } else {
            parentOfA.right = b;
        }

        a.left = b.right;
        b.right = a;
        updateHeight((AVLTreeNode) a);
        updateHeight((AVLTreeNode) b);
    }

    /**
     * Balances the tree if there is a left right imbalance.
     *
     * @param a The TreeNode to be balanced.
     * @param parentOfA The parent of the tree node that needs balancing.
     */
    private void balanceLR(TreeNode a, TreeNode parentOfA) {
        TreeNode b = a.left;
        TreeNode c = b.right;
        if (a == root) {
            root = c;
        } else if (parentOfA.left == a) {
            parentOfA.left = c;
        } else {
            parentOfA.right = c;
        }
        a.left = c.right;
        b.right = c.left;
        c.left = b;
        c.right = a;
        updateHeight((AVLTreeNode) a);
        updateHeight((AVLTreeNode) b);
        updateHeight((AVLTreeNode) c);
    }

    /**
     * Balances the tree if there is a right right imbalance.
     *
     * @param a The TreeNode to be balanced.
     * @param parentOfA The parent of the tree node that needs balancing.
     */
    private void balanceRR(TreeNode a, TreeNode parentOfA) {
        TreeNode b = a.right;
        if (a == root) {
            root = b;
        } else if (parentOfA.left == a) {
            parentOfA.left = b;
        } else {
            parentOfA.right = a;
        }
        a.right = b.left;
        b.left = a;
        updateHeight((AVLTreeNode) a);
        updateHeight((AVLTreeNode) b);
    }

    /**
     * Balances the tree if there is a right left imbalance.
     *
     * @param a The TreeNode to be balanced.
     * @param parentOfA The parent of the tree node that needs balancing.
     */
    private void balanceRL(TreeNode a, TreeNode parentOfA) {
        TreeNode b = a.right;
        TreeNode c = b.left;
        if (a == root) {
            root = c;
        } else if (parentOfA.left == a) {
            parentOfA.left = c;
        } else {
            parentOfA.right = c;
        }
        a.right = c.left;
        b.left = c.right;
        c.left = a;
        c.right = b;
        updateHeight((AVLTreeNode) a);
        updateHeight((AVLTreeNode) b);
        updateHeight((AVLTreeNode) c);
    }

    /**
     * Class for AVL tree nodes
     */
    protected static class AVLTreeNode extends BST.TreeNode implements Serializable {

        protected int height = 0;

        /**
         * Constructor that takes a retiree object
         *
         * @param r the retiree to make the tree node with
         */
        public AVLTreeNode(Retiree r) {
            super(r);
        }
    }

}
