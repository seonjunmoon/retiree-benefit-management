package cs143.business;

import cs143.dataaccess.SsnMap;
import cs143.domain.Retiree;

/**
 * Business class that handles adds, deletes, and gets Retiree objects from the
 * data access classes.
 *
 * @author Kellan Blake
 * @author Harry Vu
 * @author Seonjun Mun
 * @author Seunghyeon Hwang
 */
public class RetireeManager {

    private SsnMap map;

    /**
     * Constructor
     */
    public RetireeManager() {
        map = new SsnMap();
    }

    /**
     * Adds the given retiree to the SsnMap
     *
     * @param retiree the retiree to be added
     * @return true if added, false if not
     */
    public boolean add(Retiree retiree) {
        return map.insert(retiree.getSsn(), retiree);
    }

    /**
     * Deletes the retiree with the given SSN
     *
     * @param ssn the SSN of the retiree to be deleted
     * @return true if deleted, false if not.
     */
    public boolean delete(long ssn) {
        return map.remove(ssn);
    }

    /**
     * Gets the retiree with the given SSN
     *
     * @param ssn the SSN of the retiree the program has to get.
     * @return The retiree with the given SSN.
     */
    public Retiree get(long ssn) {
        return map.get(ssn);
    }

}
