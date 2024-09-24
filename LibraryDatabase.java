package LibraryManager;

import java.util.Date;

// Define the Item class
class Item {

    // Define private instance variables
    private String itemBarcode;
    private String itemAuthor;
    private String itemTitle;
    private String itemType;
    private String itemYear;
    private String itemISBN;

    // Constructor to initialize item details
    public Item(String itemBarcode, String itemAuthor, String itemTitle, String itemType, String itemYear, String itemISBN) {
        this.itemBarcode = itemBarcode;
        this.itemAuthor = itemAuthor;
        this.itemTitle = itemTitle;
        this.itemType = itemType;
        this.itemYear = itemYear;
        this.itemISBN = itemISBN;
    }

    // Getter ans setter methods for item details
    public String getItemBarcode() {
        return itemBarcode;
    }
    public String getItemAuthor() {
        return itemAuthor;
    }
    public String getItemTitle() {
        return itemTitle;
    }
    public String getItemType() {
        return itemType;
    }
    public String getItemYear() {
        return itemYear;
    }
    public String getItemISBN() {
        return itemISBN;
    }
    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }
    public void setItemAuthor(String itemAuthor) {
        this.itemAuthor = itemAuthor;
    }
    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    public void setItemYear(String itemYear) {
        this.itemYear = itemYear;
    }
    public void setItemISBN(String itemISBN) {
        this.itemISBN = itemISBN;
    }

    // toString method to display item details
    public String toString() {
        String itemDetails = "Item Details:\n";
        itemDetails += "Barcode: " + getItemBarcode() + "\n";
        itemDetails += "Author/Artist: " + getItemAuthor() + "\n";
        itemDetails += "Title: " + getItemTitle() + "\n";
        itemDetails += "Type: " + getItemType() + "\n";
        itemDetails += "Year: " + getItemYear() + "\n";
        itemDetails += "ISBN: " + getItemISBN() + "\n";
        return itemDetails;
    }

}

// Define the Book class which extends Item
class Book extends Item {

    // Constructor to initialize book details
    public Book(String itemBarcode, String itemAuthor, String itemTitle, String itemType, String itemYear, String itemISBN) {
        super(itemBarcode, itemAuthor, itemTitle, itemType, itemYear, itemISBN);
    }

    // Override toString method to display book details
    @Override
    public String toString() {
        String itemDetails = "Item Details:\n";
        itemDetails += "Barcode: " + getItemBarcode() + "\n";
        itemDetails += "Author: " + getItemAuthor() + "\n";
        itemDetails += "Title: " + getItemTitle() + "\n";
        itemDetails += "Type: " + getItemType() + "\n";
        itemDetails += "Year: " + getItemYear() + "\n";
        itemDetails += "ISBN: " + getItemISBN() + "\n";
        return itemDetails;
    }

}

// Define the Multimedia class which extends Item
class Multimedia extends Item {

    // Constructor to initialize multimedia details
    public Multimedia(String itemBarcode, String itemAuthor, String itemTitle, String itemType, String itemYear, String itemISBN) {
        super(itemBarcode, itemAuthor, itemTitle, itemType, itemYear, itemISBN);
    }

    // Override toString method to display multimedia details
    @Override
    public String toString() {
        String itemDetails = "Item Details:\n";
        itemDetails += "Barcode: " + getItemBarcode() + "\n";
        itemDetails += "Artist: " + getItemAuthor() + "\n";
        itemDetails += "Title: " + getItemTitle() + "\n";
        itemDetails += "Type: " + getItemType() + "\n";
        itemDetails += "Year: " + getItemYear() + "\n";
        itemDetails += "ISBN: " + getItemISBN() + "\n";
        return itemDetails;
    }
}

// Define the User class
class User {

    // Define private instance variables
    private String userID;
    private String userFName;
    private String userLName;
    private String userEmail;

    // Constructor to initialize user details
    public User(String userID, String userFName, String userLName, String userEmail) {
        this.userID = userID;
        this.userFName = userFName;
        this.userLName = userLName;
        this.userEmail = userEmail;
    }

    // Getter and setter methods for user details
    public String getUserID() {
        return userID;
    }
    public String getUserFName() {
        return userFName;
    }
    public String getUserLName() {
        return userLName;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setUserFName(String userFName) {
        this.userFName = userFName;
    }
    public void setUserLName(String userLName) {
        this.userLName = userLName;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    // toString method to display user details
    public String toString() {
        String userDetails = "User Details:\n";
        userDetails += "User ID: " + getUserID() + "\n";
        userDetails += "First Name: " + getUserFName() + "\n";
        userDetails += "Last Name: " + getUserLName() + "\n";
        userDetails += "Email: " + getUserEmail() + "\n";
        return userDetails;
    }
}

// Define the Loan class
class Loan {

    // Define private instance variables
    private User user;
    private Item item;
    private Date issueDate;
    private Date returnDate;
    private int numberOfRenewals;

    // Constructor to initialize loan details
    public Loan(User user, Item item, Date issueDate, Date returnDate, int numberOfRenewals) {
        this.user = user;
        this.item = item;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.numberOfRenewals = numberOfRenewals;
    }

    // Getter and setter methods for loan details
    public User getUser() {
        return user;
    }
    public Item getItem() {
        return item;
    }
    public Date getIssueDate() {
        return issueDate;
    }
    public Date getReturnDate() {
        return returnDate;
    }
    public int getNumberOfRenewals() {
        return numberOfRenewals;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    public void setNumberOfRenewals(int numberOfRenewals) {
        this.numberOfRenewals = numberOfRenewals;
    }

    // toString method to display loan details
    public String toString() {
        String loanDetails = "Loan Details:\n";
        loanDetails += "User ID: " + user.getUserID() + "\n";
        loanDetails += "Barcode: " + item.getItemBarcode() + "\n";
        loanDetails += "Issue Date: " + getIssueDate() + "\n";
        loanDetails += "Return Date: " + getReturnDate() + "\n";
        loanDetails += "Number of Renewals: " + getNumberOfRenewals() + "\n";
        return loanDetails;
    }
}