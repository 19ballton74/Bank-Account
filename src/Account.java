/*
 * Title: Account.java
 * Author: Brock A. Allton
 * Date: 12 September 2016
 * Purpose: Establish account for use by the ATM Interface code
 */


public class Account {
    private double checkBalance = 0.0d;
    private double saveBalance = 0.0d;
    
    public Account(double checkBalance, double saveBalance){
        this.checkBalance = checkBalance;
        this.saveBalance = saveBalance;
    }
    
    public double getCheckBal (){
        return checkBalance;
    }
    
    public void checkDeposit(double amount){
        checkBalance += amount;
    }
    
    public void checkWithdraw(double amount) throws InsufficientFunds{
        if (amount <= checkBalance){
            checkBalance -= amount;
        }//End if
        else {
            double overDraw = amount - checkBalance;
            throw new InsufficientFunds(overDraw);
        }//End else
    }//End checkWitdraw
    
    public double getSaveBal(){
        return saveBalance;
    }
    
    public void saveDeposit(double amount){
        saveBalance += amount;
    }
    
    public void saveWithdraw(double amount) throws InsufficientFunds {
        if(amount <= saveBalance){
            saveBalance += amount;
        }//End if
        else{
            double overDraw = amount - saveBalance;
            throw new InsufficientFunds(overDraw);
        }//End else
    }//End saveWitdraw
    
}//End class
