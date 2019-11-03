package cs143.dataaccess;

import cs143.domain.Retiree;

/**
 * The map class that interacts with the RetireeManager when a retiree needs to
 * be added, removed, or grabbed from a file.
 *
 * @author Kellan Blake
 * @author Harry Vu
 * @author Seonjun Mun
 * @author Seunghyeon Hwang
 */
public class SsnMap {

    private static int CAPACITY = 1024;
    private IDataAccess data = new DataAccessFileImpl();

    /**
     * Gets the retiree with the given SSN
     *
     * @param ssn the SSN of the retiree the program needs to get
     * @return the retiree with the given SSN
     */
    public Retiree get(long ssn) {
        SsnAvl temp = data.retrieveAvl(this.hash(ssn));
        if (temp != null) {
            return temp.get(ssn);
        }
        return null;
    }

    /**
     * Inserts the retiree passed.
     *
     * @param ssn the SSN of the retiree to be added
     * @param r the retiree to be added
     * @return true if added, false if not
     */
    public boolean insert(long ssn, Retiree r) {
        SsnAvl temp = data.retrieveAvl(this.hash(ssn));
        boolean confirm;
        if (temp != null) {
            confirm = temp.add(r);
        } else {
            temp = new SsnAvl();
            confirm = temp.add(r);
        }
        data.saveAvl(this.hash(ssn), temp);
        return confirm;
    }

    /**
     * Removes the retiree with the given SSN
     *
     * @param ssn the SSN of the retiree to be removed
     * @return true if removed, false if not
     */
    public boolean remove(long ssn) {
        SsnAvl temp = data.retrieveAvl(this.hash(ssn));
        boolean confirm;
        if (temp != null) {
            confirm = temp.delete(temp.get(ssn));
        } else {
            return false;
        }
        data.saveAvl(this.hash(ssn), temp);
        return confirm;
    }

    /**
     * Makes and returns the hash for the given SSN
     *
     * @param ssn the SSN of the retiree to be hashed
     * @return the supplemental hash of the hash code and the capacity
     */
    public int hash(long ssn) {
        int hashCode = (int) (ssn ^ (ssn >>> 32));
        return supplementalHash(hashCode) & (CAPACITY - 1);
    }

    private static int supplementalHash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

}
