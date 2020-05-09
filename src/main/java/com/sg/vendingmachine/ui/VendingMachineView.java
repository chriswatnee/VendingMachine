/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author christopherwatnee
 */
public class VendingMachineView {
    
    private UserIO io;
    
    public VendingMachineView(UserIO io) {
        this.io = io;
    }
    
    public String printMenuAndGetSelection(List<Item> itemsInStock) {
        io.print("\n*** Vending Machine Menu ***");
        itemsInStock.stream()
                .forEach(i -> io.print(i.getName() + ": $" + i.getCost()));
        io.print("Insert Money");
        io.print("Exit");
        return io.readString("Please select from the above choices.");
    }
    
    public void displayChange(Change change) {
        io.print("\n*** Change Returned ***");
        int quarters = change.getQuarters();
        int dimes = change.getDimes();
        int nickels = change.getNickels();
        int pennies = change.getPennies();
        
        if (quarters > 0) {
            io.print("Quarters: " + quarters);
        }
        
        if (dimes > 0) {
            io.print("Dimes: " + dimes); 
        }
        
        if (nickels > 0) {
            io.print("Nickels: " + nickels);
        }

        if (pennies > 0) {
            io.print("Pennies: " + pennies);
        }
        
        if (quarters == 0 && dimes == 0 && nickels == 0 && pennies == 0) {
            io.print("No change.");
        }
    }
    
    public void displayReturnToMainMenu() {
        io.readString("Press enter to return to main menu.");
    }
    
    public void displayInsertMoneyBanner() {
        io.print("\n*** Insert Money ***");
    }
    
    public BigDecimal getMoneyAmount() {
        return io.readBigDecimal("Please enter the amount of money you would like to insert ($0.01-$100.00).", new BigDecimal("0.01"), new BigDecimal("100.00"));
    }
    
    public void displayInsertMoneySuccessBanner(BigDecimal money, BigDecimal totalBalance) {
        io.print("You have just inserted $" + money + " into the vending machine and have inserted $" + totalBalance + " in total.");
    }
    
    public void displayUnknownCommandBanner() {
        io.print("\nUnknown Command");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("\n*** Error ***");
        io.print(errorMsg);
    }
    
    public void displayExitMessageBanner() {
        io.print("\nThank you!");
    }
    
}
