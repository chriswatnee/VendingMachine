/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author christopherwatnee
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {
    
    public static final String VENDING_MACHINE_FILE = "items.txt";
    public static final String DELIMITER = "::";
    private Map<String, Item> items = new HashMap<>();

    @Override
    public Item getItem(String name) {
        return items.get(name);
    }

    @Override
    public Item addItem(String name, Item item) {
        return items.put(name, item);
    }

    @Override
    public Item removeItem(String name) {
        return items.remove(name);
    }

    @Override
    public Item purchaseItem(String name) {
        Item item = items.get(name);
        item.setNumberInInventory(item.getNumberInInventory() - 1);
        return item;
    }

    @Override
    public List<Item> getAllItems() {
        return new ArrayList<Item>(items.values());
    }
    
    @Override
    public void loadItems() throws PersistenceException {
        Scanner scanner;
        
        try {
            // Create scanner to read the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(VENDING_MACHINE_FILE)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Could not load vending machine items into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentTokens holds each of the parts of the currentLine after it has been split
        String[] currentTokens;
        
        // Loop through file line by line
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            // Create a new item and put it into the map of items
            Item currentItem = new Item(currentTokens[0]);
            currentItem.setCost(new BigDecimal(currentTokens[1]));
            currentItem.setNumberInInventory(Integer.parseInt(currentTokens[2]));
            
            // Put currentItem into the map using the name as the key
            items.put(currentItem.getName(), currentItem);
        }
        // Close scanner
        scanner.close();
    }
    
    @Override
    public void writeItems() throws PersistenceException {
        PrintWriter out;
        
        try {
             // Create PrintWriter for writing to the vending machine file
            out = new PrintWriter(new FileWriter(VENDING_MACHINE_FILE));
        } catch (IOException e) {
            throw new PersistenceException("Could not save vending machine item data.");
        }
        
        List<Item> itemList = this.getAllItems();
        // Loop through all items
        itemList.stream()
                .forEach(i -> {
                    // Write item to the file
                    out.println(i.getName() + DELIMITER
                            + i.getCost() + DELIMITER
                            + i.getNumberInInventory());
                    // Force line to be writen to file
                    out.flush();
                });
        // Close stream
        out.close();
    }
}
