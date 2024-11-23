package bankaccountsystem;

import static bankaccountsystem.BankAccountSystem.*;
import static bankaccountsystem.BASClasses.*;
import static bankaccountsystem.UserFrames.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.*;

public class ClientFrames {
    
    public static ArrayList<JComponent> loadMenuButtons(JFrame parentFrame) {
        ArrayList<JComponent> myComponents = new ArrayList<JComponent>();
        
        // Withdraw Function
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(new Font("Serif", Font.BOLD, 18));
        withdrawButton.setFocusable(false);
        withdrawButton.setBounds(50, 100, 125, 40);
        
        withdrawButton.addActionListener((ActionEvent e) -> {
            if (loggedInAccount.getStatus()) { ErrorFrame("Account Locked!", parentFrame, parentFrame); return; }
            
            parentFrame.setVisible(false);
            WithdrawMenu(parentFrame);
        });
        
        // Deposit Function
        JButton depositButton = new JButton("Deposit");
        depositButton.setFont(new Font("Serif", Font.BOLD, 18));
        depositButton.setFocusable(false);
        depositButton.setBounds(310, 100, 125, 40);
        
        depositButton.addActionListener((ActionEvent e) -> {
            if (loggedInAccount.getStatus()) { ErrorFrame("Account Locked!", parentFrame, parentFrame); return; }
            
            parentFrame.setVisible(false);
            DepositMenu(parentFrame);
        });
        
        // Balance Inquiry Function
        JButton balanceInqButton = new JButton("Bal Inq.");
        balanceInqButton.setFont(new Font("Serif", Font.BOLD, 18));
        balanceInqButton.setFocusable(false);
        balanceInqButton.setBounds(50, 150, 125, 40);
        
        balanceInqButton.addActionListener((ActionEvent e) -> {
            parentFrame.setVisible(false);
            BalanceInquiryMenu(parentFrame);
        });
        
        
        // Pay Bills Function
        JButton payBillsButton = new JButton("Pay Bills");
        payBillsButton.setFont(new Font("Serif", Font.BOLD, 18));
        payBillsButton.setFocusable(false);
        payBillsButton.setBounds(310, 150, 125, 40);
        
        payBillsButton.addActionListener((ActionEvent e) -> {
            if (loggedInAccount.getStatus()) { ErrorFrame("Account Locked!", parentFrame, parentFrame); return; }
            
            parentFrame.setVisible(false);
            PayBillsMenu(parentFrame);
        });
        
        
        // Send Money Function
        JButton sendMoneyButton = new JButton("Send Money");
        sendMoneyButton.setFont(new Font("Serif", Font.BOLD, 18));
        sendMoneyButton.setFocusable(false);
        sendMoneyButton.setBounds(50, 200, 125, 40);
        
        sendMoneyButton.addActionListener((ActionEvent e) -> {
            if (loggedInAccount.getStatus()) { ErrorFrame("Account Locked!", parentFrame, parentFrame); return; }
            
            parentFrame.setVisible(false);
            SendMoneyMenu(parentFrame);
        });
        
        // Change PIN Function
        JButton ChangePINButton = new JButton("Change PIN");
        ChangePINButton.setFont(new Font("Serif", Font.BOLD, 18));
        ChangePINButton.setFocusable(false);
        ChangePINButton.setBounds(200, 250, 100, 40);
        
        ChangePINButton.addActionListener((ActionEvent e) -> {
            if (loggedInAccount.getStatus()) { ErrorFrame("Account Locked!", parentFrame, parentFrame); return; }
            
            parentFrame.setVisible(false);
            ChangePinMenu(parentFrame);
        });
        
        
        // Lock/Unlock Function
        JButton LUButton = new JButton("Lock/Unlock");
        LUButton.setFont(new Font("Serif", Font.BOLD, 18));
        LUButton.setFocusable(false);
        LUButton.setBounds(310, 200, 125, 40);
        
        LUButton.addActionListener((ActionEvent e) -> {
            parentFrame.setVisible(false);
            LockUnlockMenu(parentFrame);
        });
        
        
        myComponents.add(withdrawButton);
        myComponents.add(depositButton);
        myComponents.add(balanceInqButton);
        myComponents.add(payBillsButton);
        myComponents.add(sendMoneyButton);
        myComponents.add(LUButton);
        myComponents.add(ChangePINButton);
        
        return myComponents;
    }
    
    public static void MenuFrame(JFrame parentFrame) {
        JFrame MenuFrame = new JFrame();
        ArrayList<JComponent> myComponents;
        int width = 500;
        int height = 500;
        
        // Design
        JPanel top = new JPanel();
        top.setBackground(Color.orange);
        top.setLayout(null);
        top.setBounds(0, 0, width, 80);
        
        // Logo
        JLabel logo = new JLabel();
        logo.setIcon(image);
        logo.setBounds(75, 15, 50, 50);
        MenuFrame.add(logo);
        
        // Menu Title Label
        JLabel titleLabel = new JLabel("Main Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setBounds(width/2 - 125, 15, 250, 50);
        MenuFrame.add(titleLabel);
        
        myComponents = loadMenuButtons(MenuFrame);
        
        for (JComponent x : myComponents) {
            // x.setForeground(Color.BLACK);
            x.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            MenuFrame.add(x);
        }
        
        MenuFrame.add(top);
        
        MenuFrame.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        MenuFrame.setSize(width, height);
        MenuFrame.setLayout(null);
        MenuFrame.setVisible(true);
        MenuFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                parentFrame.setVisible(true);
                MenuFrame.dispose();
            }
        });
    }
    
    public static void WithdrawMenu(JFrame parentFrame) {
        JFrame WithdrawMenu = new JFrame();
        int width = 250;
        int height = 200;
        cancelled = "Transaction";
        
        // Design
        JPanel top = new JPanel();
        top.setBackground(Color.orange);
        top.setLayout(null);
        top.setBounds(0, 0, width, 50);
        
        // Title
        JLabel titleLabel = new JLabel("Withdraw", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 25));
        titleLabel.setBounds(width/2 - 100, 0, 200, 50);
        WithdrawMenu.add(titleLabel);
        
        // Withdraw amount
        JLabel withdrawAmountLabel = new JLabel("Enter amount:");
        withdrawAmountLabel.setBounds(30, 75, 100, 20);
        JTextField withdrawAmountTF = new JTextField();
        withdrawAmountTF.setBounds(120, 75, 100, 20);
        
        // Confirm Button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("Serif", Font.PLAIN, 18));
        confirmButton.setBounds(width/2-60, 120, 120, 30);
        
        confirmButton.addActionListener((ActionEvent e) -> {
            if (withdrawAmountTF.getText().isBlank()) { ErrorFrame("Enter Amount!", WithdrawMenu, parentFrame); return; }
            
            try {
                double amount = Double.parseDouble(withdrawAmountTF.getText());
                Transaction transaction = loggedInAccount.withdraw(amount);
                if (transaction != null) {
                    WithdrawMenu.dispose();
                    ReceiptFrame(parentFrame, transaction);
                    NotificationFrame("Transaction Successful", "Withdraw successful!" + "Transaction Reference Number: " + transaction.referenceNumber);
                } else {
                    ErrorFrame("Insufficient Amount!", WithdrawMenu, parentFrame);
                }
            } catch (java.lang.NumberFormatException exception) {
                ErrorFrame("Invalid input!", WithdrawMenu, parentFrame);
            }
        });
        
        WithdrawMenu.add(withdrawAmountLabel);
        WithdrawMenu.add(withdrawAmountTF);
        WithdrawMenu.add(confirmButton);
        WithdrawMenu.add(top);
        
        WithdrawMenu.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        WithdrawMenu.setSize(width, height);
        WithdrawMenu.setLayout(null);
        WithdrawMenu.setVisible(true);
        WithdrawMenu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                NotificationFrame(cancelled + " Cancelled", "");
                WithdrawMenu.dispose();
                parentFrame.setVisible(true);
            }
        });
    }
    
    public static void DepositMenu(JFrame parentFrame) {
        JFrame DepositMenu = new JFrame();
        int width = 250;
        int height = 200;
        cancelled = "Transaction";
        
        // Design
        JPanel top = new JPanel();
        top.setBackground(Color.orange);
        top.setLayout(null);
        top.setBounds(0, 0, width, 50);
        
        // Title
        JLabel titleLabel = new JLabel("Deposit", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 25));
        titleLabel.setBounds(width/2 - 100, 0, 200, 50);
        DepositMenu.add(titleLabel);
        
        // Deposit amount
        JLabel depositAmountLabel = new JLabel("Enter amount:");
        depositAmountLabel.setBounds(30, 75, 100, 20);
        JTextField depositAmountTF = new JTextField();
        depositAmountTF.setBounds(120, 75, 100, 20);
        
        // Confirm Button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("Serif", Font.PLAIN, 18));
        confirmButton.setBounds(width/2-60, 120, 120, 30);
        
        confirmButton.addActionListener((ActionEvent e) -> {
            if (depositAmountTF.getText().isEmpty()) {
                ErrorFrame("Enter amount!", DepositMenu, parentFrame);
                return;
            }
            try {
                double amount = Double.parseDouble(depositAmountTF.getText());
                BASClasses.Transaction transaction = loggedInAccount.deposit(amount);
                if (transaction != null) {
                    DepositMenu.dispose();
                    ReceiptFrame(parentFrame, transaction);
                    NotificationFrame("Transaction Successful", "Deposit successful!" + "Transaction Reference Number: " + transaction.referenceNumber);
                } else {
                    ErrorFrame("Insufficient Amount!", DepositMenu, parentFrame);
                }
            } catch (java.lang.NumberFormatException exception) {
                ErrorFrame("Invalid input!", DepositMenu, parentFrame);
            }
        });
        
        DepositMenu.add(depositAmountLabel);
        DepositMenu.add(depositAmountTF);
        DepositMenu.add(confirmButton);
        DepositMenu.add(top);
        
        DepositMenu.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        DepositMenu.setSize(width, height);
        DepositMenu.setLayout(null);
        DepositMenu.setVisible(true);
        DepositMenu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                NotificationFrame(cancelled + " Cancelled", "");
                DepositMenu.dispose();
                parentFrame.setVisible(true);
            }
        });
    }
    
    public static void BalanceInquiryMenu(JFrame parentFrame) {
        JFrame BalanceInquiryMenu = new JFrame();
        int width = 300;
        int height = 350;
        
        // Design
        JPanel top = new JPanel();
        top.setBackground(Color.orange);
        top.setLayout(null);
        top.setBounds(0, 0, width, 50);
        
        // Title
        JLabel titleLabel = new JLabel("Balance Inquiry", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 25));
        titleLabel.setBounds(width/2 - 100, 0, 200, 50);
        BalanceInquiryMenu.add(titleLabel);
        
        // Withdraw amount
        JLabel depositAmountLabel = new JLabel("Balance: ₱" + String.format("%.2f", loggedInAccount.getBalance()));
        depositAmountLabel.setBounds(30, 75, 200, 20);
        
        // Predicted Balance
        JLabel predictedBalanceLabel = new JLabel();
        predictedBalanceLabel.setBounds(30, 100, 200, 20);
        
        // Predicted Balance Option
        JLabel optionsLabel = new JLabel("Month for Predicted Balance");
        optionsLabel.setBounds(30, 135, 200, 20);
        JComboBox options = new JComboBox();
        options.setBounds(30, 160, 100, 20);
        
        int OptionsList[] = {1, 2, 3, 6, 12};
        
        for (int i : OptionsList) {
            options.addItem(i);
        }
        
        options.addActionListener((ActionEvent e) -> {
            double predictedBalance = loggedInAccount.getBalance() + loggedInAccount.getBalance() * loggedInAccount.getInterestRate() * (Double.parseDouble(options.getSelectedItem().toString()) / 12);
            predictedBalanceLabel.setText("Predicted Balance: ₱" + String.format("%.2f", predictedBalance));
            BalanceInquiryMenu.repaint();
        });
        
        // View Transaction History Button
        JButton transHistoryButton = new JButton("Transaction History");
        transHistoryButton.setBounds(width/2-75, 200, 150, 30);
        
        transHistoryButton.addActionListener((ActionEvent e) -> {
            ReportFrame(BalanceInquiryMenu, loggedInAccount.getTransactionHistory());
        });
        
        // Close Button
        JButton confirmButton = new JButton("Close");
        confirmButton.setFont(new Font("Serif", Font.PLAIN, 18));
        confirmButton.setBounds(width/2-60, 275, 120, 30);
        
        confirmButton.addActionListener((ActionEvent e) -> {
            BASClasses.Transaction transaction = loggedInAccount.balanceInquiry();
            BalanceInquiryMenu.dispose();
            ReceiptFrame(parentFrame, transaction);
        });
        
        BalanceInquiryMenu.add(depositAmountLabel);
        BalanceInquiryMenu.add(optionsLabel);
        BalanceInquiryMenu.add(predictedBalanceLabel);
        BalanceInquiryMenu.add(transHistoryButton);
        BalanceInquiryMenu.add(options);
        BalanceInquiryMenu.add(confirmButton);
        BalanceInquiryMenu.add(top);
        
        BalanceInquiryMenu.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        BalanceInquiryMenu.setSize(width, height);
        BalanceInquiryMenu.setLayout(null);
        BalanceInquiryMenu.setVisible(true);
        BalanceInquiryMenu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                BalanceInquiryMenu.dispose();
                parentFrame.setVisible(true);
            }
        });
    }
    
    public static void PayBillsMenu(JFrame parentFrame) {
        JFrame PayBillsMenu = new JFrame();
        int width = 350;
        int height = 300;
        cancelled = "Transaction";
        
        // Design
        JPanel top = new JPanel();
        top.setBackground(Color.orange);
        top.setLayout(null);
        top.setBounds(0, 0, width, 50);
        
        // Title
        JLabel titleLabel = new JLabel("Pay Bills", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 25));
        titleLabel.setBounds(width/2 - 100, 0, 200, 50);
        PayBillsMenu.add(titleLabel);
        
        // Bill Account Name
        JLabel billNameLabel = new JLabel("Enter Biller Name:", SwingConstants.RIGHT);
        billNameLabel.setBounds(15, 75, 150, 20);
        JTextField billNameTF = new JTextField();
        billNameTF.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        billNameTF.setBounds(170, 75, 125, 20);
        
        // Bill Type
        JLabel billTypeLabel = new JLabel("Enter Biller Type:", SwingConstants.RIGHT);
        billTypeLabel.setBounds(15, 100, 150, 20);
        JTextField billTypeTF = new JTextField();
        billTypeTF.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        billTypeTF.setBounds(170, 100, 125, 20);
        
        // Bill Account Number
        JLabel billAccNumLabel = new JLabel("Biller Account Number:", SwingConstants.RIGHT);
        billAccNumLabel.setBounds(15, 125, 150, 20);
        JTextField billAccNumTF = new JTextField();
        billAccNumTF.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        billAccNumTF.setBounds(170, 125, 125, 20);
        
        // Amount to Pay
        JLabel amountLabel = new JLabel("Enter amount:", SwingConstants.RIGHT);
        amountLabel.setBounds(15, 150, 150, 20);
        JTextField amountTF = new JTextField();
        amountTF.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        amountTF.setBounds(170, 150, 125, 20);
        
        // Confirm Button
        JButton confirmButton = new JButton("Pay Bill");
        confirmButton.setBounds(width/2-50, 225, 100, 20);
        
        confirmButton.addActionListener((ActionEvent e) -> {
            if (billTypeTF.getText().isEmpty()) { ErrorFrame("Enter Biller Type!", PayBillsMenu, parentFrame);  return; }
            if (billNameTF.getText().isEmpty()) { ErrorFrame("Enter Biller Name!", PayBillsMenu, parentFrame);  return; }
            if (billAccNumTF.getText().isEmpty()) { ErrorFrame("Enter Biller Account Number!", PayBillsMenu, parentFrame);  return; }
            if (amountTF.getText().isEmpty()) { ErrorFrame("Enter Amount!", PayBillsMenu, parentFrame);  return; }
            
            try {
                double amount = Double.parseDouble(amountTF.getText());
                String BillAccNum = billAccNumTF.getText();
                String BillType = billTypeTF.getText();
                String BillName = billNameTF.getText();
                BASClasses.Transaction transaction = loggedInAccount.payBills(BillType, BillName, BillAccNum, amount);
                NotificationFrame("Transaction Successful", "Pay Bills successful!" + "Transaction Reference Number: " + transaction.referenceNumber);
                PayBillsMenu.dispose();
                ReceiptFrame(parentFrame, transaction);
            } catch (java.lang.NumberFormatException exception) {
                ErrorFrame("Invalid input!", PayBillsMenu, parentFrame);
            }
        });
        
        PayBillsMenu.add(amountLabel);
        PayBillsMenu.add(amountTF);
        PayBillsMenu.add(confirmButton);
                
        PayBillsMenu.add(billTypeLabel);
        PayBillsMenu.add(billTypeTF);
        PayBillsMenu.add(billNameLabel);
        PayBillsMenu.add(billNameTF);
        PayBillsMenu.add(billAccNumLabel);
        PayBillsMenu.add(billAccNumTF);
        PayBillsMenu.add(top);
        
        PayBillsMenu.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        PayBillsMenu.setSize(width, height);
        PayBillsMenu.setLayout(null);
        PayBillsMenu.setVisible(true);
        PayBillsMenu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                NotificationFrame(cancelled + " Cancelled", "");
                PayBillsMenu.dispose();
                parentFrame.setVisible(true);
            }
        });
    }
    
    public static void SendMoneyMenu(JFrame parentFrame) {
        JFrame SendMoneyMenu = new JFrame();
        JPanel SendMoneyPanel = new JPanel();
        int width = 350;
        int height = 300;
        SendMoneyPanel.setBounds(0, 0, width, height);
        SendMoneyPanel.setSize(width, height);
        SendMoneyPanel.setLayout(null);
        cancelled = "Transaction";
        
        // Design
        JPanel top = new JPanel();
        top.setBackground(Color.orange);
        top.setLayout(null);
        top.setBounds(0, 0, width, 50);
        
        // Title
        JLabel titleLabel = new JLabel("Send Money", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 25));
        titleLabel.setBounds(width/2 - 100, 0, 200, 50);
        SendMoneyMenu.add(titleLabel);

        // Buttons
        JButton gcashButton = new JButton("GCASH");
        JButton bankTransferButton = new JButton("Bank Transfer");
        gcashButton.setBounds(width/2-50, 75, 100, 40);
        bankTransferButton.setBounds(width/2-75, 125, 150, 40);
        gcashButton.setFocusable(false);
        bankTransferButton.setFocusable(false);
        
        // Bill Account Name
        JLabel billNameLabel = new JLabel("Enter Account Name:", SwingConstants.RIGHT);
        billNameLabel.setBounds(15, 75, 150, 20);
        JTextField billNameTF = new JTextField();
        billNameTF.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        billNameTF.setBounds(170, 75, 125, 20);
        
        // Bill Account Number
        JLabel billAccNumLabel = new JLabel("Enter Account Number:", SwingConstants.RIGHT);
        billAccNumLabel.setBounds(15, 100, 150, 20);
        JTextField billAccNumTF = new JTextField();
        billAccNumTF.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        billAccNumTF.setBounds(170, 100, 125, 20);
        
        // Transfer amount
        JLabel transferAmountLabel = new JLabel("Enter amount:", SwingConstants.RIGHT);
        transferAmountLabel.setBounds(15, 125, 150, 20);
        JTextField transferAmountTF = new JTextField();
        transferAmountTF.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        transferAmountTF.setBounds(170, 125, 125, 20);
        
        // Confirm Button
        JButton confirmButton = new JButton("Transfer");
        confirmButton.setBounds(width/2-50, 200, 100, 20);
        
        gcashButton.addActionListener((ActionEvent e) -> {
            SendMoneyPanel.setName("GCASH");
            SendMoneyPanel.removeAll();
            SendMoneyPanel.add(titleLabel);
            SendMoneyPanel.add(billNameLabel);
            SendMoneyPanel.add(billNameTF);
            SendMoneyPanel.add(billAccNumLabel);
            SendMoneyPanel.add(billAccNumTF);
            SendMoneyPanel.add(transferAmountLabel);
            SendMoneyPanel.add(transferAmountTF);
            SendMoneyPanel.add(confirmButton);
            SendMoneyPanel.add(top);
            SendMoneyPanel.repaint();
            SendMoneyPanel.revalidate();
        });
        
        bankTransferButton.addActionListener((ActionEvent e) -> {
            SendMoneyPanel.setName("Bank Transfer");
            SendMoneyPanel.removeAll();
            SendMoneyPanel.add(titleLabel);
            SendMoneyPanel.add(billNameLabel);
            SendMoneyPanel.add(billNameTF);
            SendMoneyPanel.add(billAccNumLabel);
            SendMoneyPanel.add(top);
            SendMoneyPanel.add(billAccNumTF);
            SendMoneyPanel.add(transferAmountLabel);
            SendMoneyPanel.add(transferAmountTF);
            SendMoneyPanel.add(confirmButton);
            SendMoneyPanel.repaint();
            SendMoneyPanel.revalidate();
        });
        
        confirmButton.addActionListener((ActionEvent e) -> {
            if (billNameTF.getText().isEmpty()) { ErrorFrame("Enter Biller Name!", SendMoneyMenu, parentFrame);  return; }
            if (billAccNumTF.getText().isEmpty()) { ErrorFrame("Enter Biller Account Number!", SendMoneyMenu, parentFrame);  return; }
            if (transferAmountTF.getText().isEmpty()) { ErrorFrame("Enter Amount!", SendMoneyMenu, parentFrame);  return; }
            try {
                String BillAccNum = billAccNumTF.getText();
                double amount = Double.parseDouble(transferAmountTF.getText());
                String BillType = SendMoneyPanel.getName();
                String BillName = billNameTF.getText();
                BASClasses.Transaction transaction = loggedInAccount.sendMoney(BillType, BillName, BillAccNum, amount);
                if (transaction != null) {
                    SendMoneyMenu.dispose();
                    NotificationFrame("Transaction Successful", "Send Money successful!" + "Transaction Reference Number: " + transaction.referenceNumber);
                    ReceiptFrame(parentFrame, transaction);
                } else {
                    ErrorFrame("Insufficient Amount!", SendMoneyMenu, parentFrame);
                }
            } catch (java.lang.NumberFormatException exception) {
                ErrorFrame("Invalid input!", SendMoneyMenu, parentFrame);
            }
        });
        
        SendMoneyPanel.add(gcashButton);
        SendMoneyPanel.add(bankTransferButton);
        SendMoneyPanel.add(top);
        
        SendMoneyMenu.add(SendMoneyPanel);
        
        SendMoneyMenu.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        SendMoneyMenu.setSize(width, height);
        SendMoneyMenu.setLayout(null);
        SendMoneyMenu.setVisible(true);
        SendMoneyMenu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                NotificationFrame(cancelled + " Cancelled", "");
                SendMoneyMenu.dispose();
                parentFrame.setVisible(true);
            }
        });
    }
    
    public static void LockUnlockMenu(JFrame parentFrame) {
        JFrame LockUnlockMenu = new JFrame();
        int width = 300;
        int height = 250;
        System.out.println(loggedInAccount.getStatus());
        String toBeStatus = (!loggedInAccount.getStatus()) ? "Lock" : "Unlock";
        cancelled = toBeStatus + "ing";
        
        // Design
        JPanel top = new JPanel();
        top.setBackground(Color.orange);
        top.setLayout(null);
        top.setBounds(0, 0, width, 50);
        
        // Title
        JLabel titleLabel = new JLabel(toBeStatus + " Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 25));
        titleLabel.setBounds(width/2 - 100, 0, 200, 50);
        LockUnlockMenu.add(titleLabel);
        
        // Lock
        JLabel recoveryPINLabel = new JLabel("", SwingConstants.CENTER);
        recoveryPINLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        recoveryPINLabel.setBounds(width/2-100, 75, 200, 40);
        recoveryPINLabel.setVisible(false);
        
        // Unlock
        JLabel inputRPINLabel = new JLabel("Enter Recovery PIN:", SwingConstants.RIGHT);
        inputRPINLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        inputRPINLabel.setBounds(20, 75, 150, 30);
        JTextField inputRPINTF = new JTextField();
        inputRPINTF.setBounds(175, 80, 75, 20);
        inputRPINTF.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        JLabel unlockedLabel = new JLabel("Unlocked", SwingConstants.CENTER);
        unlockedLabel.setFont(new Font("Serif", Font.BOLD, 16));
        unlockedLabel.setBounds(width/2-65, 100, 130, 30);
        unlockedLabel.setVisible(false);
        
        if (toBeStatus.equals("Unlock")) {
            LockUnlockMenu.add(inputRPINLabel);
            LockUnlockMenu.add(inputRPINTF);
            LockUnlockMenu.add(unlockedLabel);
        }
        
        // Confirm Button
        JButton confirmButton = new JButton(toBeStatus);
        confirmButton.setFont(new Font("Serif", Font.BOLD, 20));
        confirmButton.setBounds(width/2-50, 150, 100, 40);
        confirmButton.setFocusable(false);
        
        confirmButton.addActionListener((ActionEvent e) -> {
            if (confirmButton.getText().equals("Close")) {
                LockUnlockMenu.dispose();
                parentFrame.setVisible(true);
                NotificationFrame("Transaction Successful", cancelled + " of account is successful!");
                return;
            }
            if (toBeStatus.equals("Lock")) {
                int PIN = 100000 + new Random().nextInt(999999);
                recoveryPINLabel.setText("Recovery PIN: " + PIN);
                recoveryPINLabel.setVisible(true);
                loggedInAccount.lockAccount(PIN);
                confirmButton.setText("Close");
            } else {
                if (inputRPINTF.getText().isBlank()) { ErrorFrame("Enter Recovery PIN!", LockUnlockMenu, parentFrame); return; }
                try {
                    int PIN = Integer.parseInt(inputRPINTF.getText());
                    if (loggedInAccount.getRecoveryPIN() == PIN) {
                        unlockedLabel.setVisible(true);
                        loggedInAccount.lockAccount(PIN);
                        confirmButton.setText("Close");
                    } else { ErrorFrame("Incorrect recovery PIN!", LockUnlockMenu, parentFrame); }
                } catch (java.lang.NumberFormatException exception) {
                    ErrorFrame("Invalid input!", LockUnlockMenu, parentFrame);
                }
            }
        });
        
        LockUnlockMenu.add(recoveryPINLabel);
        LockUnlockMenu.add(confirmButton);
        LockUnlockMenu.add(top);
        
        LockUnlockMenu.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        LockUnlockMenu.setSize(width, height);
        LockUnlockMenu.setLayout(null);
        LockUnlockMenu.setVisible(true);
        LockUnlockMenu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                NotificationFrame(cancelled + " Cancelled", "");
                LockUnlockMenu.dispose();
                parentFrame.setVisible(true);
            }
        });
    }
    
    public static void ChangePinMenu(JFrame parentFrame) {
        JFrame ChangePinMenu = new JFrame();
        int width = 350;
        int height = 300;
        cancelled = "Change PIN";
        
        // Design
        JPanel top = new JPanel();
        top.setBackground(Color.orange);
        top.setLayout(null);
        top.setBounds(0, 0, width, 50);
        
        // Title
        JLabel titleLabel = new JLabel("Change PIN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 25));
        titleLabel.setBounds(width/2 - 100, 0, 200, 50);
        ChangePinMenu.add(titleLabel);
        
        // Old PIN
        JLabel oldPINLabel = new JLabel("Old PIN:", SwingConstants.RIGHT);
        oldPINLabel.setBounds(60, 75, 80, 20);
        JTextField oldPINTF = new JTextField();
        oldPINTF.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        oldPINTF.setBounds(150, 75, 100, 20);
        
        // New PIN
        JLabel newPINLabel = new JLabel("New PIN:", SwingConstants.RIGHT);
        newPINLabel.setBounds(60, 100, 80, 20);
        JTextField newPINTF = new JTextField();
        newPINTF.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        newPINTF.setBounds(150, 100, 100, 20);
        
        // Confirm New PIN
        JLabel cnewPINLabel = new JLabel("Confirm PIN:", SwingConstants.RIGHT);
        cnewPINLabel.setBounds(60, 125, 80, 20);
        JTextField cnewPINTF = new JTextField();
        cnewPINTF.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        cnewPINTF.setBounds(150, 125, 100, 20);
        
        // Confirm Button
        JButton confirmButton = new JButton("Done");
        confirmButton.setFont(new Font("Serif", Font.PLAIN, 18));
        confirmButton.setBounds(width/2-60, 170, 120, 30);
        
        confirmButton.addActionListener((ActionEvent e) -> {
            if (confirmButton.getText().equals("Done")) {
                if (oldPINTF.getText().isBlank()) { ErrorFrame("Enter Old PIN!", ChangePinMenu, parentFrame); return; }
                if (newPINTF.getText().isBlank()) { ErrorFrame("Enter New PIN!", ChangePinMenu, parentFrame); return; }
                if (cnewPINTF.getText().isBlank()) { ErrorFrame("New PIN is not the same!", ChangePinMenu, parentFrame); return; }
                
                try {
                    int oldPIN = Integer.parseInt(oldPINTF.getText());
                    int PIN = Integer.parseInt(newPINTF.getText());
                    int cPIN = Integer.parseInt(cnewPINTF.getText());
                    if (oldPIN != loggedInAccount.getPIN()) { ErrorFrame("Incorrect Old PIN!", ChangePinMenu, parentFrame); return; }
                    if (PIN != cPIN) { ErrorFrame("New PIN should match!", ChangePinMenu, parentFrame); return; }
                    BASClasses.Transaction transaction = loggedInAccount.changePIN(PIN);
                    ChangePinMenu.dispose();
                    ReceiptFrame(parentFrame, transaction);
                    NotificationFrame("Transaction Successful", "Change PIN successful!" + "Transaction Reference Number: " + transaction.referenceNumber);
                } catch (java.lang.NumberFormatException exception) {
                    ErrorFrame("Invalid Input!", ChangePinMenu, parentFrame);
                }
            }
        });
        
        ChangePinMenu.add(oldPINLabel);
        ChangePinMenu.add(oldPINTF);
        ChangePinMenu.add(newPINLabel);
        ChangePinMenu.add(newPINTF);
        ChangePinMenu.add(cnewPINLabel);
        ChangePinMenu.add(cnewPINTF);
        ChangePinMenu.add(confirmButton);
        ChangePinMenu.add(top);
        
        ChangePinMenu.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        ChangePinMenu.setSize(width, height);
        ChangePinMenu.setLayout(null);
        ChangePinMenu.setVisible(true);
        ChangePinMenu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                NotificationFrame(cancelled + " Cancelled", "");
                ChangePinMenu.dispose();
                parentFrame.setVisible(true);
            }
        });
        
    }
}
