package bankaccountsystem;

import static bankaccountsystem.BASClasses.*;
import static bankaccountsystem.UserFrames.*;
import static bankaccountsystem.ClientFrames.*;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.*;

public class BankAccountSystem {
    public static ImageIcon image;
    public static int screenWidth;
    public static int screenHeight;
    public static ArrayList<Transaction> transactionsDone = new ArrayList<>();
    public static ArrayList<BankAccount> registeredAccounts = new ArrayList<>();
    public static BankAccount loggedInAccount = null;
    public static BankAccount adminAccount = new BankAccount("admin", "admin", "admin", "admin", "admin", "admin", "admin", 100.0, 15000, true);
    public static String cancelled;
    
    private static Transaction getTransaction(int referenceNumber, ArrayList<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            if (transaction.referenceNumber == referenceNumber) { return transaction; }
        }
        return null;
    }
    
    public static void ErrorFrame(String errorDescription, JFrame parentFrame, JFrame prevFrame) {
        JFrame ErrorFrame = new JFrame();
        int width = 250;
        int height = 250;
        
        // Error Title Label
        JLabel titleLabel = new JLabel("Error", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setBounds(width/2 - 100, 25, 200, 50);
        ErrorFrame.add(titleLabel);
        
        // Try Again
        JLabel tryAgainLabel = new JLabel("Try again?", SwingConstants.CENTER);
        tryAgainLabel.setBounds(width/2-50, 120, 100, 20);
        ErrorFrame.add(tryAgainLabel);
        
        // Unlock Button
        JButton unlockButton = new JButton("Unlock");
        unlockButton.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        unlockButton.setFocusable(false);
        unlockButton.setBounds(30, 150, 50, 20);
        
        unlockButton.addActionListener((ActionEvent e) -> {
            LockUnlockMenu(parentFrame);
            ErrorFrame.dispose();
        });
        
        // Close Button
        JButton closeButton = new JButton("Close");
        closeButton.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        closeButton.setFocusable(false);
        closeButton.setBounds(180, 150, 50, 20);
        
        closeButton.addActionListener((ActionEvent e) -> {
            parentFrame.setVisible(true);
            ErrorFrame.dispose();
        });

        if (parentFrame == prevFrame && loggedInAccount == null) {
            tryAgainLabel.setText("");
            closeButton.setBounds(width/2-25, 150, 50, 20);
            ErrorFrame.add(closeButton);
        } else if (parentFrame == prevFrame && loggedInAccount.getStatus()) {
            tryAgainLabel.setText("Unlock?");
            ErrorFrame.add(unlockButton);
            ErrorFrame.add(closeButton);
        }
        
        // Yes Button
        JButton yesButton = new JButton("Yes");
        yesButton.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        yesButton.setFocusable(false);
        yesButton.setBounds(30, 150, 75, 20);
        
        yesButton.addActionListener((ActionEvent e) -> {
            ErrorFrame.dispose();
        });
        
        
        // No Button
        JButton noButton = new JButton("No");
        noButton.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        closeButton.setFocusable(false);
        noButton.setBounds(180, 150, 50, 20);
        
        noButton.addActionListener((ActionEvent e) -> {
            ErrorFrame.dispose();
            if (prevFrame == null) {
                parentFrame.dispose();
                System.exit(0);
                return;
            }
            NotificationFrame(cancelled + " Cancelled", "");
            parentFrame.dispose();
            prevFrame.setVisible(true);
        });
        
        if (parentFrame != prevFrame) {
            ErrorFrame.add(yesButton);
            ErrorFrame.add(noButton);
        }
        
        // Error decsription
        JLabel errorLabel = new JLabel("<html><p>"+errorDescription+"</p></html>", SwingConstants.CENTER);
        errorLabel.setBounds(width/2 - 100, 50, 200, 100);
        ErrorFrame.add(errorLabel);
        
        ErrorFrame.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        ErrorFrame.setSize(width, height);
        ErrorFrame.setLayout(null);
        ErrorFrame.setVisible(true);
        ErrorFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                ErrorFrame.dispose();
            }
        });
    }
    
    public static void ReportFrame(JFrame parentFrame, ArrayList<Transaction> transactions) {
        //if (transactions.size() == 0) { ErrorFrame("No report to be done!", parentFrame, parentFrame); return; }
        JFrame ReportFrame = new JFrame();
        JPanel TablePanel = new JPanel();
        TablePanel.setLayout(new FlowLayout());
        int width = 750;
        int height = 520;
        TablePanel.setSize(width, 450);

        String[] header = {"Name", "RefNum", "AccNum", "ModUsed", "Amount", "Date"};
        
        JPanel searchPanel = new JPanel();
        searchPanel.setSize(200, 60);
        searchPanel.setLayout(new GridLayout(2, 1));
        JLabel toolTip = new JLabel("Enter Name:");
        JTextField inputField = new JTextField();
        inputField.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        TableRowSorter sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        inputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               search(inputField.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
               search(inputField.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
               search(inputField.getText());
            }
            public void search(String str) {
                if (str.length() == 0) {
                   sorter.setRowFilter(null);
                } else {
                   sorter.setRowFilter(RowFilter.regexFilter(str, 0));
                }
            }
         });
        
        searchPanel.add(toolTip);
        searchPanel.add(inputField);
        
        for (String x : header) {
            model.addColumn(x);
        }

        for (Transaction trans : transactions) {
            model.insertRow(model.getRowCount(), new Object[] { trans.name, trans.referenceNumber, trans.accountNumber, trans.moduleUsed, trans.amount, trans.date });
        }
        
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(width/2, 440, 80, 20);
        closeButton.setFocusable(false);
        
        closeButton.addActionListener((ActionEvent) -> {
            ReportFrame.dispose();
            parentFrame.setVisible(true);
        });
        
        JButton viewButton = new JButton("View");
        viewButton.setBounds(width/2-160, 440, 80, 20);
        viewButton.setFocusable(false);
        
        viewButton.addActionListener((ActionEvent) -> {
            int chosenRefNum = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString());
            ReceiptFrame(ReportFrame, getTransaction(chosenRefNum, transactions));
        });
        
        TablePanel.add(new JScrollPane(table));
        TablePanel.add(searchPanel);
        ReportFrame.add(closeButton);
        ReportFrame.add(viewButton);
        ReportFrame.add(TablePanel);
        
        ReportFrame.setTitle("Report");
        ReportFrame.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        ReportFrame.setSize(width, height);
        ReportFrame.setLayout(null);
        ReportFrame.setVisible(true);
        ReportFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                ReportFrame.dispose();
                parentFrame.setVisible(true);
            }
        });
    }
    
    public static void ReceiptFrame(JFrame parentFrame, Transaction transaction) {
        JFrame ReceiptFrame = new JFrame();
        ArrayList<JComponent> ReceiptComponents = new ArrayList<>();
        int width = 500;
        int height = 500;
        int leftPosition = 40;
        int rightPosition = 280;
        boolean beneficiary = (transaction.beneficiaryAccNum != null);
        if (!transactionsDone.contains(transaction)) { transactionsDone.add(transaction); }
        
        
        // Design
        JPanel top = new JPanel();
        top.setBackground(Color.orange);
        top.setLayout(null);
        top.setBounds(0, 0, width, 60);
        
        // Title
        JLabel logo = new JLabel();
        logo.setIcon(image);
        logo.setBounds(75, 15, 50, 50);
        ReceiptFrame.add(logo);
        
        // Title
        JLabel titleLabel = new JLabel("Transaction Receipt", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 25));
        titleLabel.setBounds(width/2 - 125, 5, 250, 50);
        ReceiptFrame.add(titleLabel);
        
        // Name
        JLabel nameLabel = new JLabel("Account Name:");
        nameLabel.setBounds(leftPosition, 75, 250, 20);
        JLabel nameLabelx = new JLabel(transaction.name);
        nameLabelx.setBounds(rightPosition, 75, 250, 20);
        
        // Account Number
        JLabel accNumLabel = new JLabel("Account Number:");
        accNumLabel.setBounds(leftPosition, 110, 250, 20);
        JLabel accNumLabelx = new JLabel(""+transaction.accountNumber);
        accNumLabelx.setBounds(rightPosition, 110, 250, 20);
        
        // Reference Number
        JLabel refNumLabel = new JLabel("Reference Number:");
        refNumLabel.setBounds(leftPosition, 140, 250, 20);
        JLabel refNumLabelx = new JLabel(""+transaction.referenceNumber);
        refNumLabelx.setBounds(rightPosition, 140, 250, 20);
        
        // Date
        JLabel dateLabel = new JLabel("Transaction Date:");
        dateLabel.setBounds(leftPosition, 170, 250, 20);
        JLabel dateLabelx = new JLabel(transaction.date);
        dateLabelx.setBounds(rightPosition, 170, 250, 20);
        
        // Module Used
        JLabel MULabel = new JLabel("Module Used:");
        MULabel.setBounds(leftPosition, 220, 250, 20);
        JLabel MULabelx = new JLabel(transaction.moduleUsed);
        MULabelx.setBounds(rightPosition, 220, 250, 20);
        
        // Amount
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(leftPosition, 250, 250, 20);
        JLabel amountLabelx = new JLabel("₱"+String.format("%.2f", transaction.amount));
        amountLabelx.setBounds(rightPosition, 250, 250, 20);
        
        // New Balance
        JLabel newbalLabel = new JLabel("New Balance:");
        newbalLabel.setBounds(leftPosition, 280, 250, 20);
        JLabel newbalLabelx = new JLabel("₱"+String.format("%.2f", transaction.remainingBalance));
        newbalLabelx.setBounds(rightPosition, 280, 250, 20);
        
        // Beneficiaries
        // Name
        JLabel beneficiaryNameLabel = new JLabel("Beneficiary Name:");
        beneficiaryNameLabel.setBounds(leftPosition, 330, 250, 20);
        JLabel beneficiaryNameLabelx = new JLabel(""+transaction.beneficiaryName);
        beneficiaryNameLabelx.setBounds(rightPosition, 330, 250, 20);

        // Type
        JLabel beneficiaryTypeLabel = new JLabel("Beneficiary Type:");
        beneficiaryTypeLabel.setBounds(leftPosition, 360, 250, 20);
        JLabel beneficiaryTypeLabelx = new JLabel(""+transaction.beneficiaryType);
        beneficiaryTypeLabelx.setBounds(rightPosition, 360, 250, 20);

        // Account Number
        JLabel beneficiaryAccNumLabel = new JLabel("Beneficiary Account Number:");
        beneficiaryAccNumLabel.setBounds(leftPosition, 390, 250, 20);
        JLabel beneficiaryAccNumLabelx = new JLabel(""+transaction.beneficiaryAccNum);
        beneficiaryAccNumLabelx.setBounds(rightPosition, 390, 250, 20);
        
        // Close Button
        JButton closeButton = new JButton("Close");
        closeButton.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        closeButton.setBounds(width/2-40, 430, 80, 20);
        
        closeButton.addActionListener((ActionEvent e) -> {
                ReceiptFrame.dispose();
                parentFrame.setVisible(true);
        });
        
        ReceiptComponents.add(accNumLabel);
        ReceiptComponents.add(nameLabel);
        ReceiptComponents.add(refNumLabel);
        ReceiptComponents.add(dateLabel);
        ReceiptComponents.add(MULabel);
        ReceiptComponents.add(amountLabel);
        ReceiptComponents.add(newbalLabel);
        if (beneficiary) {
            ReceiptComponents.add(beneficiaryNameLabel);
            ReceiptComponents.add(beneficiaryTypeLabel);
            ReceiptComponents.add(beneficiaryAccNumLabel);
        }
        
        for ( JComponent x : ReceiptComponents ) {
            x.setFont(new Font("Serif", Font.PLAIN, 16));
            ReceiptFrame.add(x);
        }
        
        ReceiptComponents.removeAll(ReceiptComponents);
        ReceiptComponents.add(accNumLabelx);
        ReceiptComponents.add(nameLabelx);
        ReceiptComponents.add(refNumLabelx);
        ReceiptComponents.add(dateLabelx);
        ReceiptComponents.add(MULabelx);
        ReceiptComponents.add(amountLabelx);
        ReceiptComponents.add(newbalLabelx);
        if (beneficiary) {
            ReceiptComponents.add(beneficiaryNameLabelx);
            ReceiptComponents.add(beneficiaryTypeLabelx);
            ReceiptComponents.add(beneficiaryAccNumLabelx);
        }
        
        closeButton.setFocusable(false);
        ReceiptComponents.add(closeButton);
        
        for ( JComponent x : ReceiptComponents ) {
            x.setFont(new Font("Serif", Font.BOLD, 16));
            ReceiptFrame.add(x);
        }
        
        ReceiptFrame.add(top);
        
        ReceiptFrame.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        ReceiptFrame.setSize(width, height);
        ReceiptFrame.setLayout(null);
        ReceiptFrame.setVisible(true);
        ReceiptFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                ReceiptFrame.dispose();
                parentFrame.setVisible(true);
            }
        });
    }
    
    public static void NotificationFrame(String titleLabel, String description) {
        JFrame NotificationFrame = new JFrame();
        JPanel top = new JPanel();
        top.setBackground(Color.lightGray);
        int width = 250;
        int height = 225;
        int delay = 3000;
        top.setBounds(0, 0, width, 40);
        
        JLabel title = new JLabel(titleLabel);
        title.setForeground(Color.black);
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setBounds(width/2-50, 5, 100, 30);
        
        top.add(title);
        
        JLabel descriptionLabel = new JLabel("<html><p style='width: 150px; text-align: center; font-size: 12px; color: white;'>"+description+"</p></html>");
        descriptionLabel.setBounds(25, 50, 200, 90);

        Timer timer = new Timer( delay, (ActionEvent e) -> {
            NotificationFrame.dispose();
        });
        timer.setRepeats(false);
        timer.start();
        
        // Close Button
        JButton closeButton = new JButton("Close");
        closeButton.setFocusable(false);
        closeButton.setBounds(width/2-40, 200, 80, 20);
        
        closeButton.addActionListener((ActionEvent e) -> {
            timer.stop();
            NotificationFrame.dispose();
        });
        
        NotificationFrame.getContentPane().setBackground(Color.black);
        NotificationFrame.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        //NotificationFrame.setLocation(0, screenHeight - 250);
        NotificationFrame.add(closeButton);
        NotificationFrame.add(descriptionLabel);
        NotificationFrame.add(top);
        NotificationFrame.setSize(width, height);
        NotificationFrame.setUndecorated(true);
        NotificationFrame.setShape(new RoundRectangle2D.Double(0, 0, width, height, 25, 25));
        NotificationFrame.setLayout(null);
        NotificationFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = dim.width;
        screenHeight = dim.height;
        
        //image = new ImageIcon("C:\\Users\\buend\\Documents\\NetBeansProjects\\BankAccountSystem\\src\\bankaccountsystem\\Logo.png");
        image = new ImageIcon("C:\\Users\\TIPQC\\Documents\\NetBeansProjects\\BankAccountSystem\\src\\bankaccountsystem\\Logo.png");
        BankAccount dummyAccount = new BankAccount("09692883215", "Harley Albert C. Buendia", "qhacbuendia@tip.edu.ph", "08/23/2005", "secret", "Student", "Credit", 100.0, 150000, false);
        dummyAccount.changePINA(1);
        dummyAccount.changeAccNumA(1);
        registeredAccounts.add(dummyAccount);
        
        transactionsDone.add(new Transaction("08/21/2013", "Withdraw", "Migs", "a", 329175612, 192365621, 5231.32, 19523814.54));
        
                
        // number, name, email, bdate, address, occupation, cardType
        adminAccount.setUserPass("admin", "admin");
        LoginFrame();
        NotificationFrame("Welcome to B4$!", "Please log in to proceed.");
    }
}