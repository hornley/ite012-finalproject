package bankaccountsystem;

import static bankaccountsystem.BankAccountSystem.*;
import java.awt.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.swing.border.Border;
import java.util.ArrayList;

public class BASClasses {
    
    // Transaction Class
    public static class Transaction {
        public String date;
        public String moduleUsed;
        public String name;
        public String cardType;
        public int referenceNumber;
        public int accountNumber;
        public double amount;
        public double remainingBalance;
        
        public String beneficiaryName;
        public String beneficiaryType;
        public String beneficiaryAccNum;

        public Transaction(String date, String mu, String name, String ct, int rn, int an, double amount, double rembal) {
            this.date = date;
            this.name = name;
            this.cardType = ct;
            this.moduleUsed = mu;
            this.referenceNumber = rn;
            this.accountNumber = an;
            this.amount = amount;
            this.remainingBalance = rembal;
        }
        
        public void setBeneficiaries(String BT, String BN, String BAN) {
            this.beneficiaryType = BT;
            this.beneficiaryAccNum = BAN;
            this.beneficiaryName = BN;
        }

        public void print() {
            System.out.println("Successfully created new transaction with reference number: " + this.referenceNumber);
            System.out.println("Date: " + this.date);
            System.out.println("Module Used: " + this.moduleUsed);
        }
    }
    
    // Bank Account Card Class
    public static class BankAccountCard {
        private int cardNumber;
        private int cvv;
        private String expiryDate;
        private String name;
    }
    
    // Bank Account Class
    public static class BankAccount {
        private double balance;
        private int accountNumber;
        private int PIN;
        private double interestRate;
        private String username;
        private String password;
        private String phoneNumber;
        private String name;
        private String email;
        private String birthday;
        private String address;
        private String cardType;
        private String occupation;
        private String gender;
        private ArrayList<Transaction> transactionHistory = new ArrayList<Transaction>();
        private boolean locked = false;
        private int recoveryPIN;
        private boolean authorized;

        // Card Info
        private BankAccountCard card;

        // Constructor
        public BankAccount(String phoneNumber, String name, String email, String birthday, String address, String occupation, String cardType, double interestRate, double initialDeposit, boolean  auth) {
            this.phoneNumber = phoneNumber;
            this.name = name;
            this.email = email;
            this.birthday = birthday;
            this.address = address;
            this.occupation = occupation;
            this.cardType = cardType;
            this.accountNumber = 1000000000 + new Random().nextInt(900000000);
            this.PIN = 100000 + new Random().nextInt(999999);
            this.interestRate = interestRate;
            this.balance = initialDeposit;
            this.authorized = (auth) ? true : false;
            if (!auth) {
                System.out.println("Account Number: " + this.accountNumber);
                System.out.println("PIN: " + this.PIN);
            }
        }
        
        public void test() { this.accountNumber = 1; this.PIN = 1; }
        
        public boolean getStatus() { return this.locked; }
        
        public double getInterestRate() { return this.interestRate; }
        
        public int getAccountNumber() { return this.accountNumber; }
        
        public int getPIN() { return this.PIN; }
        
        public void setUserPass(String username, String password) {
            this.username = username;
            this.password = password;
        }
        
        public String getName() { return this.name; }
        
        public String getUsername() { return this.username; }      
        
        public String getPassword() { return this.password; }

        public double getBalance() { return this.balance; }

        public BankAccountCard getCardInfo() { return this.card; }

        public void lockAccount(int PIN) {
            if (!this.locked) {
                this.recoveryPIN = PIN;
            }
            this.locked = !this.locked;
        }
        
        public int getRecoveryPIN() { return this.recoveryPIN; }
        
        public void changePINA(int PIN) { this.PIN = PIN; }
        
        public Transaction changePIN(int PIN) {
            this.PIN = PIN;
            return newTransaction("Change PIN", this.balance);
        }
        
        private Transaction newTransaction(String module, double amount) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now();
            Random rand = new Random();

            String time = dtf.format(now);
            int referenceNumber = 1000000000 + rand.nextInt(900000000);
            
            Transaction transaction = new Transaction(time, module, this.name, this.cardType, referenceNumber, this.accountNumber, amount, this.balance);
            this.transactionHistory.add(transaction);
            return transaction;
        }
        
        public ArrayList<Transaction> getTransactionHistory() { return this.transactionHistory; }

        public Transaction withdraw(double amount) {
            if (this.balance < amount) { return null; }
            
            this.balance -= amount;

            return newTransaction("Withdraw", amount);
        }
        
        public Transaction deposit(double amount) {
            this.balance += amount;
            
            return newTransaction("Deposit", amount);
        }
        
        public Transaction balanceInquiry() {
            return newTransaction("Balance Inquiry", this.balance);
        }
        
        public Transaction payBills(String BillType, String BillAccName, String BillAccNum, double amount) {
            if (this.balance < amount) { return null; }
            
            this.balance -= amount;
            
            Transaction transaction = newTransaction("Pay Bills", amount);
            transaction.setBeneficiaries(BillType, BillAccName, BillAccNum);
            
            return transaction;
        }
        
        public Transaction sendMoney(String BillType, String BillAccName, String BillAccNum, double amount) {
            if (this.balance < amount) { return null; }
            
            this.balance -= amount;
            
            Transaction transaction = newTransaction("Send Money", amount);
            transaction.setBeneficiaries(BillType, BillAccName, BillAccNum);
            
            return transaction;
        }
        
        public void changeAccNumA(int num) { this.accountNumber = num; }
    }
    

    // Insufficient Balance Error Class
    public static class InsufficientBalance {
        
    }
}