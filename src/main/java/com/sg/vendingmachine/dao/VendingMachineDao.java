/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.util.List;

/**
 *
 * @author christopherwatnee
 */
public interface VendingMachineDao {
    
    Item getItem(String name);
    
    Item addItem(String name, Item item);
    
    Item removeItem(String name);
    
    Item purchaseItem(String name);
    
    List<Item> getAllItems();
    
    void loadItems() throws PersistenceException;
    
    void writeItems() throws PersistenceException;
    
}
