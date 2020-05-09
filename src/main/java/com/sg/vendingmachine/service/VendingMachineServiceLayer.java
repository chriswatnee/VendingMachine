/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.PersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author christopherwatnee
 */
public interface VendingMachineServiceLayer {
    
    BigDecimal insertMoney(BigDecimal money);
    
    Item getItem(String name);
    
    Item purchaseItem(String name) throws
            InsufficientFundsException,
            NoItemInventoryException;
    
    List<Item> getAllItems();
    
    Change getChange(BigDecimal changeInPennies);
    
    void loadItems() throws PersistenceException;
    
    void writeItems() throws PersistenceException;
    
    BigDecimal getBalance();
    
    void setBalance(BigDecimal balance);
}
