/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.PersistenceException;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author christopherwatnee
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {
    
    private VendingMachineDao dao;
    private BigDecimal balance = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
    
    public VendingMachineServiceLayerImpl(VendingMachineDao dao) {
        this.dao = dao;
    }

    @Override
    public BigDecimal insertMoney(BigDecimal money) {
        money = money.setScale(2, RoundingMode.HALF_UP);
        balance = balance.add(money);
        return money;
    }
    
    @Override
    public Item getItem(String name) {
        return dao.getItem(name);
    }

    @Override
    public Item purchaseItem(String name) throws
            InsufficientFundsException,
            NoItemInventoryException {
        Item item = dao.getItem(name);
        BigDecimal itemCost = item.getCost();
        
        // Validate user has enough money
        validateFunds(itemCost);
        // Validate item is in stock
        validateInventory(item);
        
        // Passed all business rules checks so proceed
        
        // Update balance
        balance = balance.subtract(itemCost);
        // Purchase Item
        dao.purchaseItem(item.getName());
        
        return item;
    }

    @Override
    public List<Item> getAllItems() {
        return dao.getAllItems();
    }

    @Override
    public Change getChange(BigDecimal money) {
        balance = balance.subtract(money);
        BigDecimal balanceInPennies = money.multiply(new BigDecimal("100"));
        return new Change(balanceInPennies.intValue());
    }
    
    @Override
    public void loadItems() throws PersistenceException {
        dao.loadItems();
    }
    
    @Override
    public void writeItems() throws PersistenceException {
        dao.writeItems();
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    private void validateFunds(BigDecimal itemCost) throws InsufficientFundsException {
        if (balance.compareTo(itemCost) == -1) {
            throw new InsufficientFundsException(
                    "Insufficient funds: You currently have $"
                    + balance
                    + " in the machine.");
        }
    }
    
    private void validateInventory(Item item) throws NoItemInventoryException {
        if (item.getNumberInInventory() == 0) {
            throw new NoItemInventoryException(item.getName() + " is out of stock");
        }
    }
}
