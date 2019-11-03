package cs143.ui;

import cs143.business.RetireeManager;
import cs143.domain.Retiree;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class that handles the UI.
 *
 * @author Kellan Blake
 * @author Harry Vu
 * @author Seonjun Mun
 * @author Seunghyeon Hwang
 * @version 6/10/2018
 */
public class SsnMapApp {

    private static Scanner scanIn = new Scanner(System.in);
    private static RetireeManager rm = new RetireeManager();

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        long input = 0;
        do {
            input = displayMenu();
            if (input == 0) {
                delete();
            } else if (input == 1) {
                add();
            } else if (input > 1) {
                get(input);
            }
        } while (input >= 0);
    }

    /**
     * Displays menu for user to choose options.
     *
     * @return the user inputted number.
     */
    public static long displayMenu() {
        long input = 0;
        do {
            try {
                System.out.println("Select an option: \n-1 Exit \n 0 Delete a Retiree"
                        + "\n 1 Add a Retiree \n SSN Get Monthly Benefits");
                input = scanIn.nextLong();
            } catch (InputMismatchException ime) {
                input = -5;
                System.out.println("Please enter a number.");
                scanIn.nextLine();
            }
        } while (input < -1);
        return input;
    }

    /**
     * Deletes the retiree with the user entered SSN, does nothing or returns to
     * the menu when an invalid SSN is entered.
     */
    public static void delete() {
        System.out.println("Enter SSN to delete: ");
        long ssn = 0;
        try {
            ssn = scanIn.nextLong();
            while (ssn < 100000000 || 999999999 < ssn) {
                System.out.println("Enter a valid SSN: ");
                ssn = scanIn.nextLong();
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input, returing to menu.");
            scanIn.nextLine();
            return;
        }
        Retiree out = rm.get(ssn);
        if (out == null) {
            System.out.println("The retiree cannot be found");
            return;
        }
        System.out.println("Are you sure you want to delete " + out + "? (Y/N)");
        scanIn.nextLine();
        String choose = scanIn.nextLine();
        if (choose.equalsIgnoreCase("Y")) {
            rm.delete(ssn);
            System.out.println("Retiree has been successfully deleted.");
        } else if (choose.equalsIgnoreCase("N")) {
            System.out.println("Cancelled");
        } else {
            System.out.println("Enter either Y or N. Returning to menu.");
        }
    }

    /**
     * Adds the retiree created by the user, returns to the menu or does nothing
     * if there is an invalid input. Trying to add a duplicate does not work and
     * returns to the menu.
     */
    public static void add() {
        System.out.println("Enter an SSN to add: ");
        long ssn = 0;
        try {
            ssn = scanIn.nextLong();
            while (ssn < 100000000 || 999999999 < ssn) {
                scanIn.nextLine();
                System.out.println("Enter a valid SSN: ");
                ssn = scanIn.nextLong();
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid Input, returning to menu.");
            scanIn.next();
            return;
        }
        Retiree duplicate = rm.get(ssn);
        if (duplicate != null) {
            System.out.println("Retiree with this SSN already exists, "
                    + "returning to menu.");
            return;
        }

        String name;
        boolean flag = false;
        System.out.println("Enter the full name of the retiree: ");
        do {
            name = scanIn.nextLine();
            for (int i = 0; i < name.length(); i++) {
                if (Character.isLetter(name.charAt(i)) || Character.isWhitespace(name.charAt(i))) {
                    flag = true;
                } else {
                    flag = false;
                    System.out.println("Please enter a name: ");
                    break;
                }
            }
        } while (flag == false);

        System.out.println("Enter a monthly benefit to add: ");
        double benefit = 0;
        try {
            benefit = scanIn.nextDouble();
            while (benefit < 0) {
                System.out.println("Enter a valid number: ");
                benefit = scanIn.nextLong();
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid Input, returning to menu.");
            scanIn.nextLine();
            return;
        }
        System.out.println("Are you sure you want to add "
                + ssn + ": " + name + "($" + benefit + ")" + "? (Y/N)");
        scanIn.nextLine();
        String choose = scanIn.nextLine();
        if (choose.equalsIgnoreCase("Y")) {
            Retiree in = new Retiree(ssn, name, benefit);
            rm.add(in);
            System.out.println("Retiree successfully added.");
        } else if (choose.equalsIgnoreCase("N")) {
            System.out.println("Cancelled.");
        } else {
            System.out.println("Enter either Y or N. Returning to menu.");
        }
    }

    /**
     * Gets the retiree with the given SSN and displays their info for the user.
     *
     * @param ssn the SSN of the retiree the user wants to get info for.
     */
    public static void get(long ssn) {
        try {
            while (ssn < 100000000 || 999999999 < ssn) {
                System.out.println("Enter a valid SSN:");
                scanIn.nextLine();
                ssn = scanIn.nextLong();
            }
        } catch (InputMismatchException ime) {
            System.out.println("Invalid input, please enter a 9-digit number, "
                    + "returning to menu.");
            scanIn.nextLine();
            return;
        }
        Retiree selected = rm.get(ssn);
        if (selected != null) {
            System.out.println("Name: " + selected.getFullName() + "\nSSN: "
                    + selected.getSsn() + "\nMonthly Benefits: $" + selected.getMonthlyBenefit());
        } else {
            System.out.print("The retiree cannot be found. \n");
        }
    }

}
