package LibraryManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class LibrarySystem {

    // Scanner object to allow/read user's input
    static Scanner scanner = new Scanner(System.in);

    // Arraylist of loan, item, and user objects
    static ArrayList<Loan> loans = new ArrayList<Loan>();
    static ArrayList<Item> items = new ArrayList<Item>();
    static ArrayList<User> users = new ArrayList<User>();

    // Variables for library name, max renewals for both book and multimedia
    private static final String libraryName = "KD Library";
    private static final int MAX_BOOK_RENEWS = 3;
    private static final int MAX_MULTIMEDIA_RENEWS = 2;

    // Filepath for storing user, item, and loan data
    static String userFile = "/Users/kevindurac/Desktop/USERS.csv";
    static String itemFile = "/Users/kevindurac/Desktop/ITEMS.csv";
    static String loanFile = "/Users/kevindurac/Desktop/LOANS.csv";


    public static void main(String[] args) {

        // Load users, items and loans CSV into arraylist
        users = loadUsersFromCSV(userFile);
        items = loadItemsFromCSV(itemFile);
        loans = loadLoansFromCSV(loanFile);

        try {
            // Declare and initialize variable "action"
            int action;
            do {
                // Display menu options
                System.out.println("Welcome to " + libraryName);
                System.out.println("1. Issue Loan");
                System.out.println("2. Renew Loan");
                System.out.println("3. Return Loan");
                System.out.println("4. View Loans");
                System.out.println("5. Search Loan");
                System.out.println("6. Loan Report");
                System.out.println("7. Exit");

                // Prompt user for an input, read the input and consume a new line
                System.out.print("\nEnter your choice: ");
                action = scanner.nextInt();
                scanner.nextLine();
                System.out.println();

                // call  processChoice method with user's input and repeat until user input = 7
                processChoice(action);
            } while (action != 7);

            // Catch exception that occur during execution
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    private static void processChoice(int value) {
        switch (value) {
            case 1: issueLoan(); break;
            case 2: renewLoan(); break;
            case 3: returnLoan(); break;
            case 4: viewLoan(); break;
            case 5: searchLoan(); break;
            case 6: loanReport(); break;
            case 7: exitProgram();  break;
            default: System.out.println("Unknown option: " + value + "\n");
        }
    }


    static void issueLoan() {

        // Prompt user for input, read the input and retrieve user object matching user's input
        System.out.print("Enter user ID: ");
        String userID = scanner.nextLine();
        User user = getUserID(userID);

        // Prompt user for input, read the input and retrieve item object matching user's input
        System.out.print("Enter item barcode: ");
        String itemBarcode = scanner.nextLine();
        Item item = getItemBarcode(itemBarcode);

        // Check if the user exists
        if (user == null) {
            System.out.println("\nUser with ID " + userID + " does not exist.\n");
            return;
        }

        // Check if the item exists
        if (item == null) {
            System.out.println("\nItem with barcode " + itemBarcode + " does not exist.\n");
            return;
        }

        // Issue a loan add print message indicating a loan has been issued
        Loan newLoan = issueItem(item, user);
        System.out.println("\nLoan issued successfully.\n");


        // Add the issued loan to a list of loans and save it to CSV
        loans.add(newLoan);
        updateLoanCSV(loans, loanFile);
    }
    static Loan issueItem(Item item, User user) {

        // Set the issue date to the current date
        Date issueDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);

        // Base return date based on item type
        if (item.getItemType().equalsIgnoreCase("Book")) {
            calendar.add(Calendar.WEEK_OF_YEAR, 4); // 4 weeks for books
        } else if (item.getItemType().equalsIgnoreCase("Multimedia")) {
            calendar.add(Calendar.WEEK_OF_YEAR, 2); // 2 weeks for multimedia items
        } else {
            // Handle unknown item types
            System.out.println("Unknown item type: " + item.getItemType());
            return null;
        }

        // Set the return date
        Date returnDate = calendar.getTime();

        // Return a new Loan object with the correct dates
        return new Loan(user, item, issueDate, returnDate, 0);
    }

    static void renewLoan() {

        // Prompt user for input, read the input and retrieve the loan object matching user's input
        System.out.print("Enter item barcode: ");
        String itemBarcode = scanner.nextLine();
        Loan loan = getLoan(itemBarcode);

        // Check if there is an active loan for the entered item barcode
        if (loan == null) {
            System.out.println("\nItem with barcode " + itemBarcode + " does not have an active loan.\n");
            return;
        }

        // Retrieve the item object and renew
        Item item = getItemBarcode(itemBarcode);
        renewItem(item, loan);
    }
    static void renewItem(Item item, Loan loan) {

        // Retrieve the number of renewals for the loan
        int itemRenewals = loan.getNumberOfRenewals();

        // Set the current date as the issue date for renewal
        Date currentDate = new Date();

        // Create a Calendar object and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Base renewal period based on item type
        if (item.getItemType().equalsIgnoreCase("Book")) {

            // Check if maximum renewals for books have been reached
            if (itemRenewals >= MAX_BOOK_RENEWS) {
                System.out.println("\nMaximum renewals reached. Cannot renew item.\n");
                return;
            }

            // Add 2 weeks to the current date for book renewals
            calendar.add(Calendar.WEEK_OF_YEAR, 2);
            loan.setReturnDate(calendar.getTime());
            System.out.println("Loan renewed successfully.\n");

        } else if (item.getItemType().equalsIgnoreCase("Multimedia")) {

            // Check if maximum renewals for multimedia items have been reached
            if (itemRenewals >= MAX_MULTIMEDIA_RENEWS) {
                System.out.println("\nMaximum renewals reached. Cannot renew item.\n");
                return;
            }

            // Add 2 weeks to the current date for multimedia renewals
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            loan.setReturnDate(calendar.getTime());
            System.out.println("\nLoan renewed successfully.\n");
        }

        // Increment the number of renewals for the loan and update the loan CSV
        loan.setNumberOfRenewals(itemRenewals + 1);
        updateLoanCSV(loans, loanFile);
    }

    static void returnLoan() {

        // Prompt user for input and read the input
        System.out.print("Enter item barcode: ");
        String itemBarcode = scanner.nextLine();

        // Iterate over the list of loans to find the loan matching user's input
        for (Loan loan : loans) {

            // Check if the loan matches user's input
            if (loan.getItem().getItemBarcode().equals(itemBarcode)) {

                // Remove the loan form the arraylist and update the CSV
                loans.remove(loan);
                updateLoanCSV(loans, loanFile);

                // Print message indicating item matching user's input has been returned
                System.out.println("\nLoan for the item with barcode " + itemBarcode + " returned successfully.\n");
                return;
            }
        }
        // Print message indicating no loan matching user's input was found
        System.out.println("\nItem with barcode " + itemBarcode + " does not have an active loan.\n");
    }

    static void viewLoan() {

        // Checks if there are  loans currently in the system
        if (loans.isEmpty()) {
            System.out.println("\nNo items currently on loan.\n");
        }
        // Iterate over each loan in the list of loans and prints information about each loan
        for (Loan loan : loans) {
            System.out.println(loan);
        }
    }

    static void searchLoan() {

        // Prompt user for input and read the input
        System.out.print("Enter item barcode: ");
        String itemBarcode = scanner.nextLine();

        // Initialize a boolean to track whether a loan is found matching user's input
        boolean found = false;
        for (Loan loan : loans) {

            // Check if the current loan's item barcode matches the entered item barcode
            if (loan.getItem().getItemBarcode().equals(itemBarcode)) {
                System.out.println("User Details: " + loan.getUser().toString());
                System.out.println("Item Details: " + loan.getItem().toString());
                System.out.println("Issue Date: " + loan.getIssueDate());
                System.out.println("Return Date: " + loan.getReturnDate());
                System.out.println("Number of Renewals: " + loan.getNumberOfRenewals());
                System.out.println("-------------------------");
                found = true;
                break;
            }
        }
        // Print message indicating no loan is found given the item barcode
        if (!found) {
            System.out.println("\nNo loan found for item with barcode " + itemBarcode);
        }
    }

    static void loanReport() {
        // Print library name
        System.out.println("Library Name: " + libraryName);

        // Initialize counters
        int totalLoans = loans.size();
        int totalBookLoans = 0;
        int totalMultimediaLoans = 0;
        int totalRenewed = 0;

        // Count total book loans, multimedia loans, and loans renewed more than once
        for (Loan loan : loans) {
            if (loan.getItem().getItemType().equalsIgnoreCase("Book")) {
                totalBookLoans++;
            } else {
                totalMultimediaLoans++;

            }
            totalRenewed += loan.getNumberOfRenewals();

        }

        // Calculate the percentage of loans renewed
        double renewalPercentage = ((double) totalRenewed / totalLoans) * 100;

        // Print total loans, total book loans, total multimedia loans, and percentage of loans renewed
        System.out.println("Total Loans: " + totalLoans);
        System.out.println("Total Book Loans: " + totalBookLoans);
        System.out.println("Total Multimedia Loans: " + totalMultimediaLoans);
        System.out.printf("Percentage of Loans Renewed More Than Once: %.2f%%\n", renewalPercentage);
    }



    // Retrieves user object based on the specified user ID
    private static User getUserID(String userID) {
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }

    // Retrieves item object based on the specified item barcode
    private static Item getItemBarcode(String itemBarcode) {
        for (Item item : items) {
            if (item.getItemBarcode().equals(itemBarcode)) {
                return item;
            }
        }
        return null;
    }

    // Retrieves loan object based on the specified item barcode
    private static Loan getLoan(String itemBarcode) {
        for (Loan loan : loans) {
            if (loan.getItem().getItemBarcode().equals(itemBarcode)) {
                return loan;
            }
        }
        return null;
    }



    // Loads items from CSV and return them as a list of user objects
    private static ArrayList<User> loadUsersFromCSV(String userFile) {

        ArrayList<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length < 4) {
                    System.out.println("\nInvalid user data format in CSV file.\n");
                    continue;
                }
                users.add(new User(userData[0], userData[1], userData[2], userData[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Loads items from CSV and return them as a list of item objects
    private static ArrayList<Item> loadItemsFromCSV(String itemFile) {

        ArrayList<Item> items = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(itemFile))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] itemData = line.split(",");
                if (itemData.length < 5) {
                    System.out.println("\nInvalid item data format in CSV file.\n");
                    continue;
                }
                items.add(new Item(itemData[0], itemData[1], itemData[2], itemData[3], itemData[4], itemData[5]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Loads items from CSV and return them as a list of item objects
    private static ArrayList<Loan> loadLoansFromCSV(String loanFile) {

        ArrayList<Loan> loans = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(loanFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] loanData = line.split(",");
                if (loanData.length != 5) {
                    System.out.println("\nInvalid loan data format: " + line);
                    continue;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                User user = getUserID(loanData[0]);
                Item item = getItemBarcode(loanData[1]);
                Date issueDate = dateFormat.parse(loanData[2].trim());
                Date returnDate = dateFormat.parse(loanData[3].trim());
                int numberOfRenewals = Integer.parseInt(loanData[4]);

                loans.add(new Loan(user, item, issueDate, returnDate, numberOfRenewals));
            }
        } catch (ParseException | NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return loans;
    }

    // Update loan details in CSV
    private static void updateLoanCSV(ArrayList<Loan> loans, String loanFile) {
        try (FileWriter writer = new FileWriter(loanFile)) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

            for (Loan loan : loans) {
                writer.write(loan.getUser().getUserID() + "," +
                        loan.getItem().getItemBarcode() + "," +
                        dateFormat.format(loan.getIssueDate()) + "," +
                        dateFormat.format(loan.getReturnDate()) + "," +
                        loan.getNumberOfRenewals() + "\n");
            }
            System.out.println("\nLoan CSV updated.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Exit the program and save loans to CSV
    private static void exitProgram() {
        System.out.println("\nGoodbye!");
        updateLoanCSV(loans, loanFile);
        System.exit(0);
    }



}



