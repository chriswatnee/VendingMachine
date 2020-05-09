/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author christopherwatnee
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {
    
    private Item onlyItem;
    private List<Item> itemList = new ArrayList<>();
    
    public VendingMachineDaoStubImpl() {
        onlyItem = new Item("Snickers");
        onlyItem.setCost(new BigDecimal("0.75"));
        onlyItem.setNumberInInventory(8);
        
        itemList.add(onlyItem);
    }

    @Override
    public Item getItem(String name) {
        if (name.equals(onlyItem.getName())) {
            return onlyItem;
        } else {
            return null;
        }
    }
    
    @Override
    public Item addItem(String name, Item item) {
        if (name.equals(onlyItem.getName())) {
            return onlyItem;
        } else {
            return null;
        }
    }
    
    @Override
    public Item removeItem(String name) {
        if (name.equals(onlyItem.getName())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public Item purchaseItem(String name) {
        if (name.equals(onlyItem.getName())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public List<Item> getAllItems() {
        return itemList;
    }

    @Override
    public void loadItems() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void writeItems() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
