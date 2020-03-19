/*
 * Title: InsufficientFunds.java
 * Author: Brock A. Allton
 * Date: 12 September 2016
 * Purpose: Insufficient Funds exception handler for use with ATM Interface 
 */
import java.io.*;

public class InsufficientFunds extends Exception {
    private double amount;
    
    public InsufficientFunds (double amount){
        this.amount = amount;
    }
    
    public double getAmount(){
        return amount;
    }
}
