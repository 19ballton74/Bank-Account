/*
 * Title: ATMInterface.java
 * Author: Brock A. Allton
 * Date: 12 September 2016
 * Purpose: Set up ATM GUI for use with Account and Insufficient Funds Exception
 *          classes
 */
package atminterface;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;

class Account {
    private double balance;
    
    public Account(double balance){
        this.balance = balance;
    }
    
    public double getBalance (){
        return balance;
    }
    
    public void deposit(double amount){
        balance += amount;
    }
    
    public void withdraw(double amount) throws InsufficientFunds{
        if (amount <= balance){
            balance -= amount;
        }//End if
        else {
            double overDraw = amount - balance;
            throw new InsufficientFunds(overDraw);
        }//End else
    }//End witdraw
}//End class

class InsufficientFunds extends Exception {
    private final double amount;
    
    public InsufficientFunds (double amount){
        this.amount = amount;
    }
    
    public double getAmount(){
        return amount;
    }
}

public class ATMInterface extends JFrame{
    
    private final JButton withdrawButton, depositButton, transferButton, 
            balanceButton;
    private final JRadioButton savingsButton, checkingButton;
    private final JTextField amountText;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 250;
    static Account checkingAccount;
    static Account savingsAccount;
    
    public ATMInterface(){
        setSize(WIDTH,HEIGHT);
        setTitle ("ATM Machine");
        setResizable(false);
        
        
        
        //Set so that window shows up in middle of screen when opened
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = getSize().width;
        int h = getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;
        setLocation(x,y);
        
        //Close window when exiting
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Set up panel for buttons to be placed on
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
     
        //set up buttons, put on panel, assign event handlers
        withdrawButton = new JButton ("Withdraw");
        constraints.insets = new Insets(5,50,2,5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(withdrawButton, constraints);
        withdrawButton.addActionListener(new ButtonListener());
        
        depositButton = new JButton ("Deposit");
        constraints.insets = new Insets(5,5,2,50);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        add(depositButton, constraints);
        depositButton.addActionListener(new ButtonListener());
        
        transferButton = new JButton ("Transfer To");
        constraints.insets = new Insets (1,50,2,5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(transferButton, constraints);
        transferButton.addActionListener(new ButtonListener());
                   
        balanceButton = new JButton ("Balance");
        constraints.insets = new Insets (1,5,2,50);
        constraints.insets = new Insets (1,5,2,50);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        add(balanceButton, constraints);
        balanceButton.addActionListener(new ButtonListener());
        
        checkingButton = new JRadioButton ("Checking");
        constraints.insets = new Insets (1,50,5,5);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(checkingButton, constraints);
        checkingButton.addActionListener(new ButtonListener());
        
        savingsButton = new JRadioButton ("Savings");
        constraints.insets = new Insets(1,5,5,50);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.gridx = 1;
        add(savingsButton, constraints);
        savingsButton.addActionListener(new ButtonListener());
        
        amountText = new JTextField(10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets (1,35,30,35);
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.gridy = 4;
        add(amountText, constraints);
        amountText.addActionListener(new ButtonListener());
        
        //group radio buttons to allow only one selection at a time
        ButtonGroup group = new ButtonGroup();
        group.add(checkingButton);
        group.add(savingsButton);  
        
        setVisible(true);
    }//end public ATMInterface
    
    //Handling events for the buttons
    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed (ActionEvent e){
            DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
            Object source = e.getSource();
           
            //Balance Inquiry
            if(source == balanceButton){
                double balance;
                if(checkingButton.isSelected()){
                    balance = checkingAccount.getBalance();
                    JOptionPane.showMessageDialog(null, "Checking Balance: $" +
                            df.format(balance));
                }//End checking balance
                else if(savingsButton.isSelected()){
                    balance = savingsAccount.getBalance();
                    JOptionPane.showMessageDialog(null, "Savings Balance: $" +
                            df.format(balance));
                }//emd savings balance         
            }//End Balance handling
            
            //Deposits for checking and savings
            if (source == depositButton){
                if(checkingButton.isSelected()){
                    try{
                        double amount = Double.parseDouble(amountText.getText());
                        checkingAccount.deposit(amount);
                        JOptionPane.showMessageDialog(null, "Checking"
                                + " Deposit Complete");
                    }
                    catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, 
                                "Please Enter a Numeric Value");
                    }
                }//End checking Deposit
                else if(savingsButton.isSelected()){
                    try{
                        double amount = Double.parseDouble(amountText.getText());
                        savingsAccount.deposit(amount);
                        JOptionPane.showMessageDialog(null, "Savings"
                                + " Deposit Complete");
                    }
                    catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog (null, "Please Enter a "
                                + " Numeric Value");
                    }
                }//End Savings Deposit
            }//End Deposit handling
            
            //Withdraw for checking and savings
            if(source == withdrawButton){
                if(checkingButton.isSelected()){
                    try{
                        double amount = Double.parseDouble(amountText.getText());
                        if(amount % 20 == 0){
                            checkingAccount.withdraw(amount);
                            JOptionPane.showMessageDialog(null, "Checking "
                                    + " Withdraw of $" + df.format(amount) 
                                    + " Complete");               
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Increments Must"
                                    + " Be $20");
                        }
                    }
                    catch (InsufficientFunds ex){
                        JOptionPane.showMessageDialog(null, "Account short by:"
                                + " $" + ex.getAmount());        
                    }
                }//end checking withdraw    
                else if(savingsButton.isSelected()){                    
                    try{
                        double amount = Double.parseDouble(amountText.getText());
                        if(amount % 20 == 0){
                            savingsAccount.withdraw(amount);
                            JOptionPane.showMessageDialog(null, "Savings "
                                    + " Withdraw of $" + df.format(amount) 
                                    + " Complete");    
                        }
                    }
                    catch(InsufficientFunds ex){
                        JOptionPane.showMessageDialog(null, "Account Short By:"
                                + " $" + ex.getAmount());
                    }
                }//end savings Withdraw
            }//End withdraw handling
            
            //Transfers for checking and savings
            if(source == transferButton){
                if(checkingButton.isSelected()){ 
                    try{
                       double amount = Double.parseDouble(amountText.getText());
                       savingsAccount.withdraw(amount);
                       checkingAccount.deposit(amount);
                       JOptionPane.showMessageDialog(null, "Transfer of $" 
                               + df.format(amount) + 
                               " From Savings to Checking Complete");        
                    }
                    catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Please Enter "
                                + "A Numeric Value");
                    }
                    catch(InsufficientFunds ex){
                        JOptionPane.showMessageDialog(null, "Savings Account is"
                                + "Short By: $" + ex.getAmount());
                    }
                }//End transfer to checking
                if(savingsButton.isSelected()){
                    try{
                        double amount = Double.parseDouble(amountText.getText());
                        checkingAccount.withdraw(amount);
                        savingsAccount.deposit(amount);
                        JOptionPane.showMessageDialog(null, "Transfer of $" 
                                + df.format(amount)+ " From Checking to Savings"
                                + " Complete");
                    }
                    catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Please Enter "
                                + "A Numeric Value");
                    }
                    catch(InsufficientFunds ex){
                        JOptionPane.showMessageDialog(null, "Checking Account is"
                                + " Short By: $" + ex.getAmount());
                    }
                }//End transfer to savings
            }//End Transfer handling
                    
        }//end public actionPerformed
    }//End ButtonListener class

    
    public static void main(String[] args) {   
      
       new ATMInterface();
       checkingAccount = new Account(0);
       savingsAccount = new Account(0);
    }//End main

}//End class

