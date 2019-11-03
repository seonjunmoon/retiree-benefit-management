package cs143.dataaccess;

/**
 * Interface for saving and retrieving AVL trees.
 *
 * @author Kellan Blake
 * @author Harry Vu
 * @author Seonjun Mun
 * @author Seunghyeon Hwang
 */
public interface IDataAccess {

    /**
     * Interface method for saving the AVL tree
     *
     * @param index
     * @param avl
     * @return
     */
    boolean saveAvl(int index, SsnAvl avl);

    /**
     * Interface method for retrieving an AVL tree.
     *
     * @param index
     * @return
     */
    SsnAvl retrieveAvl(int index);
}
