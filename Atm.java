/*ATM INTERFACE
We have all come across ATMs in our cities and it is built on Java. This complex project consists of five different classes and is a console-based application. When the system starts the user is prompted with user id and user pin. On entering the details successfully, then ATM functionalities are unlocked. The project allows to perform following operations:
1. Transactions History
2. Withdraw
3. Deposit
4. Transfer
5. Quit*/

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Transaction {
    private String description;
    private double amount;

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private int userId;
    private int userPin;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(int userId, int userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean authenticate(int userId, int userPin) {
        return this.userId == userId && this.userPin == userPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
            System.out.println("Deposited $" + amount + " into your account.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdraw", amount));
            System.out.println("Withdrawn $" + amount + " from your account.");
        } else {
            System.out.println("Insufficient funds or invalid amount for withdrawal.");
        }
    }

    public void transfer(Account targetAccount, double amount) {
        if (targetAccount != null && amount > 0 && balance >= amount) {
            balance -= amount;
            targetAccount.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + targetAccount.userId, amount));
            System.out.println("Transferred $" + amount + " to account " + targetAccount.userId + ".");
        } else {
            System.out.println("Invalid transfer or insufficient funds.");
        }
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}

public class Atm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create user accounts (You can extend this for multiple accounts)
        Account userAccount = new Account(12345, 1234);

        System.out.println("Welcome to the ATM Interface");
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter User PIN: ");
        int userPin = scanner.nextInt();

        if (userAccount.authenticate(userId, userPin)) {
            System.out.println("Authentication successful!");
            performTransactions(userAccount, scanner);
        } else {
            System.out.println("Invalid User ID or PIN. Exiting.");
        }

        scanner.close();
    }

    public static void performTransactions(Account account, Scanner scanner) {
        boolean quit = false;

        while (!quit) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewTransactionHistory(account);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: $");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter deposit amount: $");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter target account User ID: ");
                    int targetUserId = scanner.nextInt();
                    Account targetAccount = null; // You would need to look up the target account
                    // Implement account lookup logic here
                    if (targetAccount != null) {
                        System.out.print("Enter transfer amount: $");
                        double transferAmount = scanner.nextDouble();
                        account.transfer(targetAccount, transferAmount);
                    } else {
                        System.out.println("Target account not found.");
                    }
                    break;
                case 5:
                    quit = true;
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    public static void viewTransactionHistory(Account account) {
        List<Transaction> transactions = account.getTransactionHistory();
        System.out.println("\nTransaction History:");

        for (Transaction transaction : transactions) {
            System.out.println(transaction.getDescription() + ": $" + transaction.getAmount());
        }
    }
}
