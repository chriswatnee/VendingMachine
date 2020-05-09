/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.PersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author christopherwatnee
 */
public class VendingMachineController {
    
    private VendingMachineView view;
    private VendingMachineServiceLayer service;
    
    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }
    
    public void run() {
        boolean keepGoing = true;
        String menuSelection;
        try {
            loadVendingMachineInventory();
            while(keepGoing) {
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case "Snickers":
                    case "Twix":
                    case "Butterfinger":
                    case "Skittles":
                    case "M&M's":
                    case "Twizzlers":
                    case "Lay's":
                    case "Doritos":
                    case "Cheetos":
                        purchaseItem(menuSelection);
                        break;
                    case "Insert Money":
                        insertMoney();
                        break;
                    case "Exit":
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
            writeVendingMachineInventory();
        } catch (PersistenceException e) {
            errorMessage(e.getMessage());
        }
    }
    
    private void loadVendingMachineInventory() throws PersistenceException {
        service.loadItems();
    }
    
    private String getMenuSelection() {
        List<Item> itemsInStock = service.getAllItems();
        return view.printMenuAndGetSelection(itemsInStock);
    }
    
    private void purchaseItem(String name) {
        try {
            service.purchaseItem(name);
            BigDecimal totalBalance = service.getBalance();
            Change change = service.getChange(totalBalance);
            view.displayChange(change);
        } catch (InsufficientFundsException | NoItemInventoryException e) {
            view.displayErrorMessage(e.getMessage());
        }
        
        view.displayReturnToMainMenu();
    }
    
    private void insertMoney() {
        view.displayInsertMoneyBanner();
        BigDecimal money = view.getMoneyAmount();
        money = service.insertMoney(money);
        BigDecimal totalBalance = service.getBalance();
        view.displayInsertMoneySuccessBanner(money, totalBalance);
        view.displayReturnToMainMenu();
    }
    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }
    
    private void exitMessage() {
        view.displayExitMessageBanner();
    }
    
    private void errorMessage(String message) {
        view.displayErrorMessage(message);
    }
    
    private void writeVendingMachineInventory() throws PersistenceException {
        service.writeItems();
    }
    
}
