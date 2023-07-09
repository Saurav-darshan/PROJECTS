
import java.io.*;
import java.util.*;

class BankAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public String toString() {
        return "Account Number: " + accountNumber + ", Account Holder: " + accountHolder + ", Balance: ₹  " + balance;
    }
}

class Bank {
    private List<BankAccount> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public BankAccount findAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public void saveData(String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(accounts);
            System.out.println("Data saved successfully.");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            accounts = (List<BankAccount>) ois.readObject();
            System.out.println("Data loaded successfully.");
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void displayAllAccounts() {
        for (BankAccount account : accounts) {
            System.out.println(account);
        }
    }
}

public class BankingApp {
    public static void main(String[] args) {
        Bank bank = new Bank();

        // Load bank data from the file (if available)
        bank.loadData("bankdata.txt");

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("\n-------**** Banking System Menu *****------------");
            System.out.println("1. Create Account");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.nextLine();
                    System.out.print("Enter account holder name: ");
                    String accountHolder = scanner.nextLine();
                    System.out.print("Enter initial balance: ");
                    double initialBalance = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline character

                    BankAccount newAccount = new BankAccount(accountNumber, accountHolder, initialBalance);
                    bank.addAccount(newAccount);
                    System.out.println(
                            "-----------------Account created successfully.------------------------------------");
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextLine();
                    BankAccount account = bank.findAccount(accountNumber);
                    if (account != null) {
                        System.out.println("Account Holder: " + account.getAccountHolder());
                        System.out.println("Account Balance: RS- " + account.getBalance());
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextLine();
                    account = bank.findAccount(accountNumber);
                    if (account != null) {
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume the newline character
                        account.deposit(depositAmount);
                        System.out.println("Deposit successful. New balance: RS- " + account.getBalance());
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextLine();
                    account = bank.findAccount(accountNumber);
                    if (account != null) {
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume the newline character
                        account.withdraw(withdrawalAmount);
                        System.out.println("Withdrawal successful. New balance: RS- " + account.getBalance());
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 5:
                    // Save bank data to a file before exiting
                    bank.saveData("bankdata.txt");
                    System.out.println("Exiting the program. Bank data saved.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
