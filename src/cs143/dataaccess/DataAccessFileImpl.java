package cs143.dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class that handles saving and retrieving AVL trees to and from files.
 *
 * @author Kellan Blake
 * @author Harry Vu
 * @author Seonjun Mun
 * @author Seunghyeon Hwang
 */
public class DataAccessFileImpl implements IDataAccess {

    /**
     * Saves the given SsnAvl to the file with the given index
     *
     * @param index the number for the file name.
     * @param avl The SsnAvl to be saved to the file
     * @return true if successfully saved, false if not.
     */
    @Override
    public boolean saveAvl(int index, SsnAvl avl) {
        String filename = "avl" + index + ".ser";
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream output = new ObjectOutputStream(fileOut);
            output.writeObject(avl);
            fileOut.close();
            output.close();
        } catch (IOException ioe) {
            return false;
        }
        return true;
    }

    /**
     * Retrieves the SsnAvl from the file with the given index and returns it.
     *
     * @param index the number for the file name.
     * @return the SsnAvl from the file with the given index.
     */
    @Override
    public SsnAvl retrieveAvl(int index) {
        String filename = "avl" + index + ".ser";
        SsnAvl avl = new SsnAvl();
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream input = new ObjectInputStream(fileIn);
            avl = (SsnAvl) input.readObject();
            fileIn.close();
            input.close();
        } catch (IOException ioe) {
            return null;
        } catch (ClassNotFoundException ex) {
            return null;
        }
        return avl;
    }

}
