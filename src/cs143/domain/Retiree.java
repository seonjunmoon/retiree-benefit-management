package cs143.domain;

import java.io.Serializable;

/**
 * Class for creating retiree objects
 *
 * @author Kellan Blake
 * @author Harry Vu
 * @author Seonjun Mun
 * @author Seunghyeon Hwang
 */
public class Retiree implements Comparable<Retiree>, Serializable {

    private long ssn;
    private String fullName;
    private double monthlyBenefit;

    /**
     * Constructor
     *
     * @param ssn the SSN of the retiree
     * @param fullName The retiree's name
     * @param monthlyBenefit Their monthly social security benefits
     */
    public Retiree(long ssn, String fullName, double monthlyBenefit) {
        this.ssn = ssn;
        this.fullName = fullName;
        this.monthlyBenefit = monthlyBenefit;
    }

    /**
     * Gets the SSN of the retiree
     *
     * @return the retiree's SSN
     */
    public long getSsn() {
        return ssn;
    }

    /**
     * Gets the full name of the retiree
     *
     * @return a string containing the retiree's full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Gets the monthly benefits for the retiree
     *
     * @return a double representing the amount of money the retiree gets per
     * month
     */
    public double getMonthlyBenefit() {
        return monthlyBenefit;
    }

    @Override
    public String toString() {
        return ssn + " : " + fullName + "\n"
                + "monthlyBenefit = $" + monthlyBenefit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.ssn ^ (this.ssn >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Retiree other = (Retiree) obj;
        return this.ssn == other.ssn;
    }

    @Override
    public int compareTo(Retiree o) {
        if (this.ssn < o.ssn) {
            return -1;
        } else if (this.ssn > o.ssn) {
            return 1;
        } else {
            return 0;
        }
    }

}
