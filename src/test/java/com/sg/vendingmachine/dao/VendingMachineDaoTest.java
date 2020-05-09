/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author christopherwatnee
 */
public class VendingMachineDaoTest {
    
    private VendingMachineDao dao;
    
    public VendingMachineDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = ctx.getBean("vendingMachineDao", VendingMachineDao.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Item> itemList = dao.getAllItems();
        for (Item item : itemList) {
            dao.removeItem(item.getName());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addItem method, of class VendingMachineDao.
     */
    @Test
    public void testAddGetItem() {
        Item item = new Item("Snickers");
        item.setCost(new BigDecimal("0.75"));
        item.setNumberInInventory(8);
        
        dao.addItem(item.getName(), item);
        
        Item fromDao = dao.getItem(item.getName());
        
        assertEquals(item, fromDao);
    }

    /**
     * Test of removeItem method, of class VendingMachineDao.
     */
    @Test
    public void testRemoveItem() {
        Item item1 = new Item("Snickers");
        item1.setCost(new BigDecimal("0.75"));
        item1.setNumberInInventory(8);
        
        dao.addItem(item1.getName(), item1);
        
        Item item2 = new Item("Twix");
        item2.setCost(new BigDecimal("0.75"));
        item2.setNumberInInventory(1);
        
        dao.addItem(item2.getName(), item2);
        
        dao.removeItem(item1.getName());
        assertEquals(1, dao.getAllItems().size());
        assertNull(dao.getItem(item1.getName()));
        
        dao.removeItem(item2.getName());
        assertEquals(0, dao.getAllItems().size());
        assertNull(dao.getItem(item2.getName()));
    }

    /**
     * Test of purchaseItem method, of class VendingMachineDao.
     */
    @Test
    public void testPurchaseItem() {
        Item item = new Item("Snickers");
        item.setCost(new BigDecimal("0.75"));
        item.setNumberInInventory(8);
        
        dao.addItem(item.getName(), item);
        
        dao.purchaseItem(item.getName());
        
        assertEquals(7, item.getNumberInInventory());
    }

    /**
     * Test of getAllItems method, of class VendingMachineDao.
     */
    @Test
    public void testGetAllItems() {
        Item newItem1 = new Item("Snickers");
        newItem1.setCost(new BigDecimal("0.75"));
        newItem1.setNumberInInventory(8);
        
        dao.addItem(newItem1.getName(), newItem1);
        
        Item newItem2 = new Item("Twix");
        newItem2.setCost(new BigDecimal("0.75"));
        newItem2.setNumberInInventory(1);
        
        dao.addItem(newItem2.getName(), newItem2);
        
        assertEquals(2, dao.getAllItems().size());
    }
    
}
