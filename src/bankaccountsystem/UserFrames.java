package bankaccountsystem;

import static bankaccountsystem.BankAccountSystem.*;
import static bankaccountsystem.ClientFrames.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

public class UserFrames {
    
    public static BASClasses.BankAccount validateLogin(String username, String password, JFrame parentFrame, JFrame prevFrame) {
        
        if (username.equals(adminAccount.getUsername())) {
            if (password.equals(adminAccount.getPassword())) { return adminAccount; }
            else { ErrorFrame("Incorrect Password", parentFrame, prevFrame); return null; }
        }
        
        for (BASClasses.BankAccount account : registeredAccounts) {
            if (account == null || account.getAccountNumber() == 1) { continue; }
            String cAccountUsername = account.getUsername();
            if (cAccountUsername.equals(username)) {
                String cAccountPassword = account.getPassword();
                if (cAccountPassword.equals(password)) { return account; }
                else { ErrorFrame("Incorrect Password", parentFrame, prevFrame); return null; }
            }
        }
        ErrorFrame("Username not found!", parentFrame, prevFrame);
        return null;
    }
    
    public static void SearchFrame(JFrame parentFrame) {
        JFrame SearchFrame = new JFrame();
        JPanel SearchPanel = new JPanel();
        SearchPanel.setName("First");
        int width = 500;
        int height = 350;
        SearchPanel.setBounds(0, 0, width, height);
        SearchPanel.setLayout(null);
        cancelled = "Searching";
        
        JLabel logo = new JLabel();
        logo.setIcon(image);
        logo.setBounds(125, 15, 50, 50);
        SearchPanel.add(logo);
        
        // Design
        JPanel top = new JPanel();
        top.setBackground(Color.orange);
        top.setLayout(null);
        top.setBounds(0, 0, width, 80);
        
        // Title Label
        JLabel titleLabel = new JLabel("Search", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setBounds(width/2 - 125, 15, 250, 50);
        SearchFrame.add(titleLabel);
        
        // Account Number
        JLabel accNumLabel = new JLabel("Account Number:", SwingConstants.CENTER);
        JTextField accNumTF = new JTextField();
        accNumLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        accNumTF.setFont(new Font("Serif", Font.PLAIN, 20));
        accNumLabel.setBounds(30, 125, 175, 40);
        accNumTF.setBounds(230, 125, 200, 40);
        accNumTF.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        // PIN
        JLabel pinLabel = new JLabel("PIN:", SwingConstants.CENTER);
        JTextField pinTF = new JTextField();
        pinLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        pinTF.setFont(new Font("Serif", Font.PLAIN, 20));
        pinLabel.setBounds(30, 125, 175, 40);
        pinTF.setBounds(230, 125, 200, 40);
        pinTF.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        // Next/Confirm Button
        JButton button = new JButton("Next");
        button.setFocusable(false);
        button.setFont(new Font("Serif", Font.BOLD, 16));
        button.setBounds(width/2-40, 250, 80, 40);
        
        accNumTF.addKeyListener(new KeyListener() {
            public void keyPressed (KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    String input = accNumTF.getText();
                    if (input.isEmpty()) {
                        ErrorFrame("Enter Account Number!", SearchFrame, parentFrame);
                        SearchPanel.add(accNumLabel);
                        SearchPanel.add(accNumTF);
                    } else {
                        int accNum = 0;
                        boolean valid = false;
                        for (BASClasses.BankAccount account : registeredAccounts) {
                            try {
                                accNum = Integer.parseInt(accNumTF.getText());
                                if (account.getAccountNumber() == accNum) {
                                    valid = true;
                                    loggedInAccount = account;
                                }
                            } catch (java.lang.NumberFormatException exception) {
                                ErrorFrame("Invalid input!", SearchFrame, parentFrame);
                            }
                        }
                        if (valid) { 
                            SearchPanel.setName("Second");
                            NotificationFrame("Account Found", "Name: " + loggedInAccount.getName());
                            SearchPanel.add(pinLabel);
                            SearchPanel.add(pinTF);
                        } else {
                            ErrorFrame("Account Not Found!", SearchFrame, parentFrame);
                            SearchPanel.add(accNumLabel);
                            SearchPanel.add(accNumTF);
                        }
                    }
                }
            }    
            
            public void keyReleased (KeyEvent e) {
            }   
            
            public void keyTyped (KeyEvent e) {
            }    
        });
        
        button.addActionListener((ActionEvent e) -> {
            SearchPanel.removeAll();
            if (SearchPanel.getName().equals("First")) {
                String input = accNumTF.getText();
                if (input.isEmpty()) {
                    ErrorFrame("Enter Account Number!", SearchFrame, parentFrame);
                    SearchPanel.add(accNumLabel);
                    SearchPanel.add(accNumTF);
                } else {
                    int accNum = 0;
                    boolean valid = false;
                    for (BASClasses.BankAccount account : registeredAccounts) {
                        try {
                            accNum = Integer.parseInt(accNumTF.getText());
                            if (account.getAccountNumber() == accNum) {
                                valid = true;
                                loggedInAccount = account;
                            }
                        } catch (java.lang.NumberFormatException exception) {
                            ErrorFrame("Invalid input!", SearchFrame, parentFrame);
                        }
                    }
                    if (valid) { 
                        SearchPanel.setName("Second");
                        NotificationFrame("Account Found", "AccNum: " + accNum);
                        SearchPanel.add(pinLabel);
                        SearchPanel.add(pinTF);
                    } else {
                        ErrorFrame("Account Not Found!", SearchFrame, parentFrame);
                        SearchPanel.add(accNumLabel);
                        SearchPanel.add(accNumTF);
                    }
                }
            } else if (SearchPanel.getName().equals("Second")) {
                String input = pinTF.getText();
                
                if (input.isEmpty()) {
                    ErrorFrame("Enter PIN!", SearchFrame, parentFrame);
                } else {
                    try {
                        int pin = Integer.parseInt(input);
                        if (loggedInAccount.getPIN() == pin) {
                            MenuFrame(parentFrame);
                            SearchFrame.dispose();
                            NotificationFrame("Account Logged In", "Name: " + loggedInAccount.getName());
                            return;
                        } else { ErrorFrame("Incorrect PIN!", SearchFrame, parentFrame);}
                    } catch (java.lang.NumberFormatException exception) {
                        ErrorFrame("Invalid input!", SearchFrame, parentFrame);
                    }
                }
                SearchPanel.add(pinLabel);
                SearchPanel.add(pinTF);
            }
            SearchPanel.add(button);
            SearchPanel.add(logo);
            SearchPanel.add(titleLabel);
            SearchPanel.add(top);
            SearchPanel.add(logo);
            SearchPanel.repaint();
            SearchPanel.revalidate();
        });
        
        SearchPanel.add(titleLabel);
        SearchPanel.add(button);
        SearchPanel.add(accNumLabel);
        SearchPanel.add(accNumTF);
        SearchPanel.add(top);
        
        SearchFrame.add(SearchPanel);
        
        SearchFrame.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        SearchFrame.setSize(width, height);
        SearchFrame.setLayout(null);
        SearchFrame.setVisible(true);
        SearchFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                SearchFrame.dispose();
                parentFrame.setVisible(true);
            }
        });
    }
    
    public static void UserMenuFrame(JFrame parentFrame) {
        JFrame UserMenuFrame = new JFrame();
        ArrayList<JComponent> myComponents = new ArrayList<JComponent>();
        int width = 400;
        int height = 350;
        
        // Design
        JPanel top = new JPanel();
        top.setBackground(Color.orange);
        top.setLayout(null);
        top.setBounds(0, 0, width, 80);
        
        // Logo
        JLabel logo = new JLabel();
        logo.setIcon(image);
        logo.setBounds(20, 10, 50, 50);
        UserMenuFrame.add(logo);
        
        // Menu Title Label
        JLabel titleLabel = new JLabel("Admin Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setBounds(width/2 - 125, 15, 250, 50);
        UserMenuFrame.add(titleLabel);
        
        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(30, 110, 125, 40);
        searchButton.setFocusable(false);
        
        searchButton.addActionListener((ActionEvent e) -> {
            SearchFrame(UserMenuFrame);
            UserMenuFrame.setVisible(false);
        });
        
        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(240, 110, 125, 40);
        registerButton.setFocusable(false);
        
        registerButton.addActionListener((ActionEvent e) -> {
            RegisterFrame(UserMenuFrame);
            UserMenuFrame.setVisible(false);
        });
        
        // Report Button
        JButton reportButton = new JButton("Report");
        reportButton.setBounds(30, 170, 125, 40);
        reportButton.setFocusable(false);
        
        reportButton.addActionListener((ActionEvent e) -> {
            //ReportFrame(UserMenuFrame, transactionsDone);
            ReportFrame(UserMenuFrame, transactionsDone);
            UserMenuFrame.setVisible(false);
        });
        
        // About Button
        JButton aboutButton = new JButton("About");
        aboutButton.setBounds(240, 170, 125, 40);
        aboutButton.setFocusable(false);
        
        aboutButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "Bank 4ccount System, is comprised of Computer Science Students studying at the Technological Institute of the Philippines.\n"
                    +"Bank 4ccount System is a Java-based Bank Account System that is implemented with the use of NetBeans IDE.\n"
                    +"The goal of Bank 4ccount is to help bank tellers with bank transactions such as Withdrawal, Deposits, and Payment of Bills.\n"
                    +"The proponents hope to give bank tellers a system that is easy to use and understand.\n\n"
                    +"Developers:\n"
                    +"Buendia, Harley Albert C.\n"
                    +"Capuli, Athien Caleb\n"
                    +"Sarmiento, Garry Angelo\n"
                    +"Yamat, Ryan Angelo");
        });
        
        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(width/2 - 62, 250, 125, 40);
        logoutButton.setFocusable(false);
        
        logoutButton.addActionListener((ActionEvent e) -> {
            loggedInAccount = null;
            UserMenuFrame.dispose();
            parentFrame.setVisible(true);
        });
        
        myComponents.add(searchButton);
        myComponents.add(registerButton);
        myComponents.add(reportButton);
        myComponents.add(aboutButton);
        myComponents.add(logoutButton);
        
        for (JComponent component : myComponents) {
            component.setFont(new Font("Serif", Font.PLAIN, 18));
        }
        
        UserMenuFrame.add(searchButton);
        UserMenuFrame.add(registerButton);
        UserMenuFrame.add(reportButton);
        UserMenuFrame.add(aboutButton);
        UserMenuFrame.add(logoutButton);
        UserMenuFrame.add(top);
        
        UserMenuFrame.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        UserMenuFrame.setSize(width, height);
        UserMenuFrame.setLayout(null);
        UserMenuFrame.setVisible(true);
        UserMenuFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                UserMenuFrame.dispose();
                parentFrame.setVisible(true);
            }
        });
    }
    
    public static void ForgetPasswordFrame(JFrame parentFrame) {
        JFrame ForgetPasswordFrame = new JFrame();
        int width = 350;
        int height = 300;
        cancelled = "Forget Password";
        
        JLabel logo = new JLabel();
        logo.setIcon(image);
        logo.setBounds(40, 15, 50, 50);
        ForgetPasswordFrame.add(logo);
        
        // Title
        JLabel titleLabel = new JLabel("Forget password", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setBounds(width/2 - 100, 15, 200, 50);
        ForgetPasswordFrame.add(titleLabel);
        
        // Username
        JLabel accnumLabel = new JLabel("Username:", SwingConstants.CENTER);
        accnumLabel.setBounds(30, 100, 125, 20);
        JTextField accnumTextField = new JTextField();
        accnumTextField.setBounds(150, 100, 125, 20);
        
        ForgetPasswordFrame.add(accnumLabel);
        ForgetPasswordFrame.add(accnumTextField);
        
        
        // Password Text Field
        JLabel passwordLabel = new JLabel("Password:", SwingConstants.CENTER);
        passwordLabel.setBounds(30, 125, 125, 20);
        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setBounds(150, 125, 125, 20);
        
        ForgetPasswordFrame.add(passwordLabel);
        ForgetPasswordFrame.add(passwordTextField);
        
        // Confirm Password Text Field
        JLabel cpasswordLabel = new JLabel("Confirm Password:", SwingConstants.CENTER);
        cpasswordLabel.setBounds(30, 150, 125, 20);
        JPasswordField cpasswordTextField = new JPasswordField();
        cpasswordTextField.setBounds(150, 150, 125, 20);
        
        ForgetPasswordFrame.add(cpasswordLabel);
        ForgetPasswordFrame.add(cpasswordTextField);
        
        // Confirm Button
        JButton closeButton = new JButton("Confirm");
        closeButton.setBounds(width/2 - 40, 200, 80, 20);
        
        closeButton.addActionListener((ActionEvent e) -> {
            
            // Empty Errors
            if (accnumTextField.getText().isBlank()) { ErrorFrame("Enter Account Number", ForgetPasswordFrame, parentFrame); return; }
            if (passwordTextField.getText().isBlank()) { ErrorFrame("Enter Password", ForgetPasswordFrame, parentFrame); return; }
            if (cpasswordTextField.getText().isBlank()) { ErrorFrame("Enter Confirm Password", ForgetPasswordFrame, parentFrame); return; }
            
            String username = accnumTextField.getText();
            
            if (username.equals("admin")) {
                String password = passwordTextField.getText();
                String cpassword = cpasswordTextField.getText();
                if (password.equals(cpassword)) {
                    adminAccount.setUserPass(username, password);
                    System.out.println(adminAccount.getPassword());
                    ForgetPasswordFrame.dispose();
                    parentFrame.setVisible(true);
                    return;
                }
                ErrorFrame("Password not the same", ForgetPasswordFrame, parentFrame);
                return;
            }
            
            for (BASClasses.BankAccount account : registeredAccounts) {
                if (account == null) { continue; }
                if (account.getUsername().equals(username)) {
                    String password = passwordTextField.getPassword().toString();
                    String cpassword = cpasswordTextField.getPassword().toString();
                    if (!password.equals(cpassword)) { ErrorFrame("Password not the same", ForgetPasswordFrame, parentFrame); return; }
                    account.setUserPass(username, password);
                    ForgetPasswordFrame.dispose();
                    parentFrame.setVisible(true);
                    return;
                }
            }
            ErrorFrame("Username not found!", ForgetPasswordFrame, parentFrame);
        });

        
        ForgetPasswordFrame.add(closeButton);
        
        ForgetPasswordFrame.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        ForgetPasswordFrame.setSize(width, height);
        ForgetPasswordFrame.setLayout(null);
        ForgetPasswordFrame.setVisible(true);
        ForgetPasswordFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                ForgetPasswordFrame.dispose();
                parentFrame.setVisible(true);
            }
        });
    }
    
    public static void LoginFrame() {
        JFrame LoginFrame = new JFrame();
        int width = 400;
        int height = 400;
        
        // Design
        JPanel top = new JPanel();
        top.setBackground(Color.orange);
        top.setLayout(null);
        top.setBounds(0, 0, width, 80);
        
        
        // Logo
        JLabel logo = new JLabel();
        logo.setIcon(image);
        logo.setBounds(75, 20, 50, 50);
        LoginFrame.add(logo);
        
        // LOGIN TITLE LABEL
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 40));
        titleLabel.setBounds(width/2 - 50, 15, 100, 50);
        LoginFrame.add(titleLabel);
        
        // Username Text Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(80, 135, 75, 20);
        JTextField usernameTextField = new JTextField();
        usernameTextField.setBounds(160, 135, 125, 20);
        usernameTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        LoginFrame.add(usernameLabel);
        LoginFrame.add(usernameTextField);
        
        // Password Text Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(80, 160, 75, 20);
        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setBounds(160, 160, 125, 20);
        passwordTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        passwordTextField.addKeyListener(new KeyListener() {
            
            public void keyPressed (KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    String username = usernameTextField.getText();
                    String password = new String(passwordTextField.getPassword());
                    BASClasses.BankAccount validation = validateLogin(username, password, LoginFrame, null);
                    if (validation != null) {
                        usernameTextField.setText("");
                        passwordTextField.setText("");
                        UserMenuFrame(LoginFrame);
                        LoginFrame.setVisible(false);
                        NotificationFrame("Logged In Successfully", "Welcome " + username + "!");
                    }
                }
            }    
            
            public void keyReleased (KeyEvent e) {
            }   
            
            public void keyTyped (KeyEvent e) {
            }    
        });
        
        LoginFrame.add(passwordLabel);
        LoginFrame.add(passwordTextField);
        
        // Forgot Password
        JLabel forgotPasswordLabel = new JLabel("Forgot Password");
        forgotPasswordLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        forgotPasswordLabel.setForeground(Color.BLUE);
        forgotPasswordLabel.setBounds(200, 185, 200, 20);
        
        forgotPasswordLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                ForgetPasswordFrame(LoginFrame);
                LoginFrame.setVisible(false);
            }
        });
        
        LoginFrame.add(forgotPasswordLabel);
        
        // Register Label
        JLabel registerLabel = new JLabel("<html>Create an account now, <font color='blue'>register</font><html>");
        registerLabel.setBounds(90, 275, 200, 20);
        
        registerLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                RegisterFrame(LoginFrame);
                LoginFrame.setVisible(false);
            }
        });
        
        LoginFrame.add(registerLabel);
        
        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(width/2-40, 235, 80, 20);
        
        loginButton.addActionListener((ActionEvent e) -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            BASClasses.BankAccount validation = validateLogin(username, password, LoginFrame, null);
            if (validation != null) {
                usernameTextField.setText("");
                passwordTextField.setText("");
                UserMenuFrame(LoginFrame);
                LoginFrame.setVisible(false);
                NotificationFrame("Logged In Successfully", "Welcome " + username + "!");
            }
        });
        
        LoginFrame.add(loginButton);
        
        
        LoginFrame.add(top);

        LoginFrame.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        LoginFrame.setSize(width, height);
        LoginFrame.setLayout(null);
        LoginFrame.setVisible(true);
        LoginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public static void RegisterFrame(JFrame parentFrame) {
        JFrame RegisterFrame = new JFrame();
        JPanel RegisterPanel = new JPanel();
        JPanel Registering = new JPanel();
        ArrayList<JComponent> myComponents = new ArrayList<JComponent>();
        HashMap<String, Integer> monthsAndDays = new HashMap<String, Integer>() {{
            put("January", 31);
            put("February", 28);
            put("March", 31);
            put("April", 30);
            put("May", 31);
            put("June", 30);
            put("July", 31);
            put("August", 31);
            put("September", 30);
            put("October", 31);
            put("November", 30);
            put("December", 31);
        }};
        int width = 500;
        int height = 400;
        RegisterPanel.setBounds(0,0, width, height);
        RegisterPanel.setBackground(Color.decode("#bcbdc0"));
        RegisterPanel.setLayout(null);
        RegisterPanel.setName("Ninja");
        RegisterFrame.add(RegisterPanel);
        
        // Design
        JPanel top = new JPanel();
        top.setBackground(Color.orange);
        top.setLayout(null);
        top.setBounds(0, 0, width, 80);
        
        JLabel logo = new JLabel();
        logo.setIcon(image);
        logo.setBounds(110, 10, 50, 50);
        RegisterPanel.add(logo);
        
        // Register Panel Default thingz
        JLabel RegisterFrameTitle = new JLabel("Register");
        RegisterFrameTitle.setFont(new Font("Serif", Font.BOLD, 40));
        RegisterFrameTitle.setBounds(width/2-75, 15, 150, 50);
        RegisterPanel.add(RegisterFrameTitle);
        
        
        // NINJA PART
        JButton userButton = new JButton("User");
        JButton clientButton = new JButton("Client");
        userButton.setFont(new Font("Serif", Font.BOLD, 20));
        clientButton.setFont(new Font("Serif", Font.BOLD, 20));
        userButton.setBounds(width/2-150, 150, 100, 40);
        clientButton.setBounds(width/2+50, 150, 100, 40);

        
        // FIRST PART
        
        
        // First Name Input Field
        JLabel firstNameLabel = new JLabel("First Name:", SwingConstants.RIGHT);
        firstNameLabel.setBounds(95, 100, 100, 20);

        JTextField firstNameTextField = new JTextField();
        firstNameTextField.setBounds(200, 100, 100, 20);

        myComponents.add(firstNameTextField);
        myComponents.add(firstNameLabel);
        
        // Middle Name Input Field
        JLabel middleNameLabel = new JLabel("Middle Name:", SwingConstants.RIGHT);
        middleNameLabel.setBounds(95, 125, 100, 20);

        JTextField middleNameTextField = new JTextField();
        
        middleNameTextField.setBounds(200, 125, 100, 20);

        myComponents.add(middleNameTextField);
        myComponents.add(middleNameLabel);
        
        // Last Name Input Field
        JLabel lastNameLabel = new JLabel("Last Name:", SwingConstants.RIGHT);
        lastNameLabel.setBounds(95, 150, 100, 20);

        JTextField lastNameTextField = new JTextField();
        lastNameTextField.setBounds(200, 150, 100, 20);
        
        myComponents.add(lastNameTextField);
        myComponents.add(lastNameLabel);

        
        // Birthdate Jspinner
        JLabel birthdateLabel = new JLabel("Birthdate:");
        birthdateLabel.setBounds(width/2-50, 200, 100, 20);
        
        String months[] = {"December", "November", "October", "September", "August", "July", "June", "May", "April", "March", "February", "January"};
        JSpinner monthSpinner = new JSpinner(new SpinnerListModel(months));
        monthSpinner.setValue("January");
        JSpinner daySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
        JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(1980, 1950, 2028, 1));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(yearSpinner, "#");
        yearSpinner.setEditor(editor);
        monthSpinner.setBounds(120, 225, 100, 20);
        daySpinner.setBounds(235, 225, 50, 20);
        yearSpinner.setBounds(295, 225, 75, 20);
        
        monthSpinner.addChangeListener((ChangeEvent e) -> {
            String currentMonth = monthSpinner.getValue().toString();
            int currentYear = Integer.parseInt(yearSpinner.getValue().toString());
            int maxDays = monthsAndDays.get(currentMonth);
            if (currentYear % 4 == 0 && currentMonth.equals("February")) maxDays++;
            SpinnerNumberModel daysModel = new SpinnerNumberModel(1, 1, maxDays, 1); 
            daySpinner.setModel(daysModel);
        });
        
        yearSpinner.addChangeListener((ChangeEvent e) -> {
            String currentMonth = monthSpinner.getValue().toString();
            int currentYear = Integer.parseInt(yearSpinner.getValue().toString());
            int maxDays = monthsAndDays.get(currentMonth);
            if (currentYear % 4 == 0 && currentMonth.equals("February")) maxDays++;
            SpinnerNumberModel daysModel = new SpinnerNumberModel(1, 1, maxDays, 1); 
            daySpinner.setModel(daysModel);
        });

        myComponents.add(monthSpinner);
        myComponents.add(daySpinner);
        myComponents.add(yearSpinner);
        myComponents.add(birthdateLabel);
        
        
        // SECOND PART
        
        
        // Phone Number Input Field
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setBounds(50, 100, 100, 20);

        JTextField phoneNumberTextField = new JTextField();
        phoneNumberTextField.setBounds(150, 100, 200, 20);

        myComponents.add(phoneNumberTextField);
        myComponents.add(phoneNumberLabel);

        // Email Input Field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(100, 125, 50, 20);

        JTextField emailTextField = new JTextField();
        emailTextField.setBounds(150, 125, 200, 20);

        myComponents.add(emailTextField);
        myComponents.add(emailLabel);
        
        // Source of Income Text Field
        JLabel sourceOfIncomeLabel = new JLabel("Source of Income:");
        sourceOfIncomeLabel.setBounds(30, 150, 125, 20);

        JTextField sourceOfIncomeTextField = new JTextField();
        sourceOfIncomeTextField.setBounds(150, 150, 200, 20);

        myComponents.add(sourceOfIncomeTextField);
        myComponents.add(sourceOfIncomeLabel);

        // Address Text Field
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(80, 175, 100, 20);

        JTextField addressTextField = new JTextField();
        addressTextField.setBounds(150, 175, 200, 20);

        myComponents.add(addressTextField);
        myComponents.add(addressLabel);
        
        
        // THIRD PART
        
        
        // Username Text Field
        JLabel usernameLabel = new JLabel("Username:", SwingConstants.CENTER);
        int offset = 50;
        usernameLabel.setBounds(30 + offset, 125, 125, 20);
        JTextField usernameTextField = new JTextField();
        usernameTextField.setBounds(150 + offset, 125, 125, 20);
        
        myComponents.add(usernameLabel);
        myComponents.add(usernameTextField);
        
        // Password Text Field
        JLabel passwordLabel = new JLabel("Password:", SwingConstants.CENTER);
        passwordLabel.setBounds(30 + offset, 150, 125, 20);
        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setBounds(150 + offset, 150, 125, 20);
        
        myComponents.add(passwordLabel);
        myComponents.add(passwordTextField);
        
        // Confirm Password Text Field
        JLabel cpasswordLabel = new JLabel("Confirm Password:", SwingConstants.CENTER);
        cpasswordLabel.setBounds(30 + offset, 175, 125, 20);
        JPasswordField cpasswordTextField = new JPasswordField();
        cpasswordTextField.setBounds(150 + offset, 175, 125, 20);
        
        myComponents.add(cpasswordLabel);
        myComponents.add(cpasswordTextField);
        
        
        // FOURTH PART
        
        ArrayList<Double> debitInterestRates = new ArrayList<Double>();
        debitInterestRates.add(0.75);
        debitInterestRates.add(1.25);
        debitInterestRates.add(2.0);
        
        ArrayList<Double> creditInterestRates = new ArrayList<Double>();
        creditInterestRates.add(2.5);
        creditInterestRates.add(3.5);
        creditInterestRates.add(5.0);
        

        // Card Type Option
        JLabel cardTypeLabel = new JLabel("Card Type:");
        cardTypeLabel.setBounds(125, 125, 100, 20);

        ButtonGroup cardTypeOptionGroup = new ButtonGroup();
        JRadioButton debitOption = new JRadioButton("Debit");
        debitOption.setBounds(125, 150, 100, 20);
        JRadioButton creditOption = new JRadioButton("Credit");
        creditOption.setBounds(125, 175, 100, 20);
        debitOption.setBackground(Color.decode("#bcbdc0"));
        creditOption.setBackground(Color.decode("#bcbdc0"));
        cardTypeOptionGroup.add(debitOption);
        cardTypeOptionGroup.add(creditOption);
        
        
        // Interest Rates Option
        JLabel interestRatesLabel = new JLabel("Interest Rates:");
        interestRatesLabel.setBounds(250, 125, 100, 20);
        
        JComboBox interestRatesOption = new JComboBox();
        interestRatesOption.setBounds(250, 150, 100, 20);
        
        interestRatesLabel.setVisible(false);
        interestRatesOption.setVisible(false);
        
        debitOption.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ie){
                interestRatesLabel.setVisible(true);
                interestRatesOption.setVisible(true);
                interestRatesOption.removeAllItems();
                for (Double i : debitInterestRates) {
                    interestRatesOption.addItem(i);
                }
            }
        });
        
        creditOption.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ie){
                interestRatesLabel.setVisible(true);
                interestRatesOption.setVisible(true);
                interestRatesOption.removeAllItems();
                for (Double i : creditInterestRates) {
                    interestRatesOption.addItem(i);
                }
            }
        });

        
        // Initial Deposit Text Field
        JLabel initialDepositLabel = new JLabel("Initial Deposit: ");
        initialDepositLabel.setBounds(125, 225, 100, 20);
        JTextField initialDepositTextField = new JTextField();
        initialDepositTextField.setBounds(225, 225, 100, 20);
        
        myComponents.add(debitOption);
        myComponents.add(creditOption);
        myComponents.add(cardTypeLabel);
        myComponents.add(interestRatesLabel);
        myComponents.add(interestRatesOption);
        myComponents.add(initialDepositLabel);
        myComponents.add(initialDepositTextField);
        
        // LAST PART (Generation of Account Number and Temporary PIN)
        
        
        // Account Number Label
        JLabel accNumLabel = new JLabel("Account Number: ");
        accNumLabel.setFont(new Font("Serif", Font.BOLD, 20));
        accNumLabel.setBounds(75, 125, 300, 40);
        
        // Temporary PIN Label
        JLabel tempPINLabel = new JLabel("Temporary PIN: ");
        tempPINLabel.setFont(new Font("Serif", Font.BOLD, 20));
        tempPINLabel.setBounds(75, 160, 300, 40);
        
        myComponents.add(accNumLabel);
        myComponents.add(tempPINLabel);
        
        // Confirm Button
        JButton confirmButton = new JButton("Next");
        confirmButton.setBounds(width/2+25, 300, 100, 20);
        
        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(width/2-125, 300, 100, 20);
        
        
        
        userButton.addActionListener((ActionEvent e) -> {
            Registering.setName("User");
            RegisterPanel.removeAll();
            RegisterPanel.add(monthSpinner);
            RegisterPanel.add(daySpinner);
            RegisterPanel.add(yearSpinner);
            RegisterPanel.add(birthdateLabel);
            RegisterPanel.add(firstNameTextField);
            RegisterPanel.add(firstNameLabel);
            RegisterPanel.add(middleNameTextField);
            RegisterPanel.add(middleNameLabel);
            RegisterPanel.add(lastNameTextField);
            RegisterPanel.add(lastNameLabel);

            RegisterPanel.setName("First");
                
            RegisterPanel.add(logo);
            RegisterPanel.add(confirmButton);
            RegisterPanel.add(backButton);
            RegisterPanel.add(RegisterFrameTitle);
            RegisterPanel.add(top);
            RegisterPanel.revalidate();
            RegisterFrame.repaint();
        });
        
        clientButton.addActionListener((ActionEvent e) -> {
            Registering.setName("Client");
            RegisterPanel.removeAll();
            RegisterPanel.add(monthSpinner);
            RegisterPanel.add(daySpinner);
            RegisterPanel.add(yearSpinner);
            RegisterPanel.add(birthdateLabel);
            RegisterPanel.add(firstNameTextField);
            RegisterPanel.add(firstNameLabel);
            RegisterPanel.add(middleNameTextField);
            RegisterPanel.add(middleNameLabel);
            RegisterPanel.add(lastNameTextField);
            RegisterPanel.add(lastNameLabel);

            RegisterPanel.setName("First");
                
            RegisterPanel.add(logo);
            RegisterPanel.add(confirmButton);
            RegisterPanel.add(backButton);
            RegisterPanel.add(RegisterFrameTitle);
            RegisterPanel.add(top);
            RegisterPanel.revalidate();
            RegisterFrame.repaint();
        });
        
        RegisterPanel.add(userButton);
        RegisterPanel.add(clientButton);
        myComponents.add(userButton);
        myComponents.add(clientButton);
                
        confirmButton.addActionListener((ActionEvent e) -> {
            if (RegisterPanel.getName().equals("First")) {
                if (firstNameTextField.getText().isBlank()) { ErrorFrame("Enter First Name!", RegisterFrame, parentFrame); return; }
                if (lastNameTextField.getText().isBlank()) { ErrorFrame("Enter Last Name!", RegisterFrame, parentFrame); return; }
                RegisterPanel.removeAll();
                
                RegisterPanel.add(addressTextField);
                RegisterPanel.add(addressLabel);
                RegisterPanel.add(sourceOfIncomeTextField);
                RegisterPanel.add(sourceOfIncomeLabel);
                RegisterPanel.add(emailTextField);
                RegisterPanel.add(emailLabel);
                RegisterPanel.add(phoneNumberTextField);
                RegisterPanel.add(phoneNumberLabel);
                
                RegisterPanel.setName("Second");
            } else if (RegisterPanel.getName().equals("Second") && Registering.getName().equals("User")) {
                if (addressTextField.getText().isBlank()) { ErrorFrame("Enter Address!", RegisterFrame, parentFrame); return; }
                if (sourceOfIncomeTextField.getText().isBlank()) { ErrorFrame("Enter Occupation!", RegisterFrame, parentFrame); return; }
                if (emailTextField.getText().isBlank()) { ErrorFrame("Enter Email!", RegisterFrame, parentFrame); return; }
                if (phoneNumberTextField.getText().isBlank()) { ErrorFrame("Enter Phone Number!", RegisterFrame, parentFrame); return; }
                RegisterPanel.removeAll();
                
                RegisterPanel.add(usernameLabel);
                RegisterPanel.add(usernameTextField);
                RegisterPanel.add(passwordLabel);
                RegisterPanel.add(passwordTextField);
                RegisterPanel.add(cpasswordLabel);
                RegisterPanel.add(cpasswordTextField);
                
                RegisterPanel.setName("Confirm");
            } else if (RegisterPanel.getName().equals("Second") && Registering.getName().equals("Client")) {
                if (addressTextField.getText().isBlank()) { ErrorFrame("Enter Address!", RegisterFrame, parentFrame); return; }
                if (sourceOfIncomeTextField.getText().isBlank()) { ErrorFrame("Enter Occupation!", RegisterFrame, parentFrame); return; }
                if (emailTextField.getText().isBlank()) { ErrorFrame("Enter Email!", RegisterFrame, parentFrame); return; }
                if (phoneNumberTextField.getText().isBlank()) { ErrorFrame("Enter Phone Number!", RegisterFrame, parentFrame); return; }
                
                String password = new String(passwordTextField.getPassword());
                String cpassword = new String(cpasswordTextField.getPassword());
                
                if (!password.equals(cpassword)) { ErrorFrame("Password not the same", RegisterFrame, parentFrame); return; }
                
                RegisterPanel.removeAll();

                RegisterPanel.add(debitOption);
                RegisterPanel.add(creditOption);
                RegisterPanel.add(cardTypeLabel);
                RegisterPanel.add(interestRatesLabel);
                RegisterPanel.add(interestRatesOption);
                RegisterPanel.add(initialDepositLabel);
                RegisterPanel.add(initialDepositTextField);
                
                RegisterPanel.setName("Confirm");
            } else if (RegisterPanel.getName().equals("Confirm") && !confirmButton.getText().equals("Close")) {
                
                if (Registering.getName().equals("User")) {
                    if (usernameTextField.getText().isBlank() || new String(passwordTextField.getPassword()).isBlank() || new String(cpasswordTextField.getPassword()).isBlank()) {
                        ErrorFrame("Fill in all fields!", RegisterFrame, parentFrame);
                        return;
                    }
                } else {
                    if (initialDepositTextField.getText().isBlank() || (!debitOption.isSelected() && !creditOption.isSelected())) {
                        ErrorFrame("Fill in all fields!", RegisterFrame, parentFrame);
                        return;
                    }
                }
                
                RegisterPanel.removeAll();
                
                String firstName = firstNameTextField.getText();
                String middleName = middleNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String cardType = "";
                String phoneNumber = phoneNumberTextField.getText();
                String email = emailTextField.getText();
                String SOI = sourceOfIncomeTextField.getText();
                String address = addressTextField.getText();
                String birthdate = monthSpinner.getValue().toString() + " " + daySpinner.getValue().toString() + ", " + yearSpinner.getValue().toString();
                boolean auth = (Registering.getName().equals("User")) ? true : false;
                double initialDeposit = 9999999;
                double interestRate = 100;
                
                if (!auth) {
                    initialDeposit = Double.parseDouble(initialDepositTextField.getText());
                    interestRate = (double)interestRatesOption.getSelectedItem();
                    if (debitOption.isSelected() == true) {
                        cardType = "Debit";
                    } else if (creditOption.isSelected() == true) {
                        cardType = "Credit";
                    }
                }
                
                // Errors for if a field is missing
                // use .isEmpty() or .isBlank()
                String name = firstName + " " + middleName + ((middleName.length() > 0)? ". " : "") + lastName;

                // String phoneNumber, String name, String email, String birthday, String address, String occupation, String cardType
                BASClasses.BankAccount newaccount = new BASClasses.BankAccount(phoneNumber, name, email, birthdate, address, SOI, cardType, interestRate, initialDeposit, auth);
                registeredAccounts.add(newaccount);
                
                if (auth) {
                    String username = usernameTextField.getText();
                    String password = new String(passwordTextField.getPassword());
                    String cpassword = new String(cpasswordTextField.getPassword());
                    if (!password.equals(cpassword)) { ErrorFrame("Passwords must match!", RegisterFrame, parentFrame); return; }
                    newaccount.setUserPass(username, password);
                    accNumLabel.setText("Successfuly created: " + username);
                    NotificationFrame("Registration Successful", "Welcome to the team of B4$, " + username + "!");
                    RegisterPanel.setName("Successful");
                    RegisterPanel.add(accNumLabel);
                } else {
                    accNumLabel.setText(accNumLabel.getText() + newaccount.getAccountNumber());
                    tempPINLabel.setText(tempPINLabel.getText() + newaccount.getPIN());
                    RegisterPanel.add(accNumLabel);
                    RegisterPanel.add(tempPINLabel);
                    RegisterPanel.setName("Successful");
                    NotificationFrame("Registration Successful", "Successfully registered a new client: " + name);
                }
                
                
                confirmButton.setText("Close");
                confirmButton.setBounds(width/2-50, 300, 100, 20);
                confirmButton.addActionListener((ActionEvent event) -> {
                    RegisterFrame.dispose();
                    parentFrame.setVisible(true);
                });
            }
            RegisterPanel.add(logo);
            RegisterPanel.add(confirmButton);
            if (!confirmButton.getText().equals("Close")) { RegisterPanel.add(backButton); }
            RegisterPanel.add(RegisterFrameTitle);
            RegisterPanel.add(top);
            RegisterPanel.revalidate();
            RegisterFrame.repaint();
        });
                
        backButton.addActionListener((ActionEvent e) -> {
            RegisterPanel.removeAll();
            if (RegisterPanel.getName().equals("First")) {
                RegisterPanel.add(userButton);
                RegisterPanel.add(clientButton);
                
                RegisterPanel.setName("Ninja");
            } else if (RegisterPanel.getName().equals("Second")) {
                RegisterPanel.add(monthSpinner);
                RegisterPanel.add(daySpinner);
                RegisterPanel.add(yearSpinner);
                RegisterPanel.add(birthdateLabel);
                RegisterPanel.add(firstNameTextField);
                RegisterPanel.add(firstNameLabel);
                RegisterPanel.add(middleNameTextField);
                RegisterPanel.add(middleNameLabel);
                RegisterPanel.add(lastNameTextField);
                RegisterPanel.add(lastNameLabel);
                
                RegisterPanel.setName("First");
            } else if (RegisterPanel.getName().equals("Confirm")) {
                RegisterPanel.add(addressTextField);
                RegisterPanel.add(addressLabel);
                RegisterPanel.add(sourceOfIncomeTextField);
                RegisterPanel.add(sourceOfIncomeLabel);
                RegisterPanel.add(emailTextField);
                RegisterPanel.add(emailLabel);
                RegisterPanel.add(phoneNumberTextField);
                RegisterPanel.add(phoneNumberLabel);
                
                RegisterPanel.setName("Second");
            }
            if (!RegisterPanel.getName().equals("Ninja")) {
                RegisterPanel.add(confirmButton);
                RegisterPanel.add(backButton);
            }
            RegisterPanel.add(logo);
            RegisterPanel.add(RegisterFrameTitle);
            RegisterPanel.add(top);
            RegisterPanel.revalidate();
            RegisterFrame.repaint();
        });
        
        myComponents.add(confirmButton);
        myComponents.add(backButton);
        RegisterPanel.add(top);
        
        for (JComponent x : myComponents) {
            x.setForeground(Color.BLACK);
            x.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            if (x.getClass() == JButton.class) { x.setFocusable(false); }
        }
        
           
        // Register Frame Settings
        RegisterFrame.setTitle("Register/Open an Account");
        RegisterFrame.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);
        RegisterFrame.setSize(width, height);
        RegisterFrame.setLayout(null);
        RegisterFrame.setVisible(true);
        RegisterFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                RegisterFrame.dispose();
                parentFrame.setVisible(true);
                if (!RegisterPanel.getName().equals("Successful")) {
                    NotificationFrame("Registration Cancelled", "");
                }
            }
        });
    }

}
