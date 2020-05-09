/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author christopherwatnee
 */
public class VendingMachineServiceLayerTest {
    
    private VendingMachineServiceLayer service;
    
    public VendingMachineServiceLayerTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        service.setBalance(new BigDecimal("0.00"));
        Item item = service.getItem("Snickers");
        item.setNumberInInventory(8);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insertMoney method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testInsertMoneyOnce() {
        BigDecimal expectedResult = new BigDecimal("1.25");
        assertEquals(expectedResult, service.insertMoney(new BigDecimal("1.25")));
    }
    
    @Test
    public void testInsertMoneyTwice() {
        service.insertMoney(new BigDecimal("2.01"));
        service.insertMoney(new BigDecimal("3.75"));
        BigDecimal expectedResult = new BigDecimal("5.76");
        assertEquals(expectedResult, service.getBalance());
    }
    
    @Test
    public void testInsertMoneyScale() {
        BigDecimal expectedResult = new BigDecimal("99.00");
        assertEquals(expectedResult, service.insertMoney(new BigDecimal("99")));
    }
    
    @Test
    public void testInsertMoneyRounding() {
        BigDecimal expectedResult = new BigDecimal("10.36");
        assertEquals(expectedResult, service.insertMoney(new BigDecimal("10.356")));
    }

    /**
     * Test of getItem method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetItem() throws Exception {
        Item item = service.getItem("Snickers");
        assertNotNull(item);
    }

    /**
     * Test of purchaseItem method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testPurchaseItem() throws Exception {
        service.setBalance(new BigDecimal("0.75"));
        Item item = service.purchaseItem("Snickers");
        assertNotNull(item);
    }
    
    @Test
    public void testPurchaseItemNoBalance() throws Exception {
        try {
            service.purchaseItem("Snickers");
            fail("Expected InsufficientFundsException was not thrown.");
        } catch (InsufficientFundsException e) {
            return;
        }
    }
    
    @Test
    public void testPurchaseItemInsufficientFunds() throws Exception {
        service.setBalance(new BigDecimal("0.74"));
        
        try {
            service.purchaseItem("Snickers");
            fail("Expected InsufficientFundsException was not thrown.");
        } catch (InsufficientFundsException e) {
            return;
        }
    }
    
    @Test
    public void testPurchaseItemNoItemInventory() throws Exception {
        service.setBalance(new BigDecimal("0.75"));
        Item item = service.getItem("Snickers");
        item.setNumberInInventory(0);
        
        try {
            service.purchaseItem("Snickers");
            fail("Expected NoItemInventoryException was not thrown.");
        } catch (NoItemInventoryException e) {
            return;
        }
    }

    /**
     * Test of getAllItemsInStock method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetAllItems() {
        assertEquals(1, service.getAllItems().size());
    }

    /**
     * Test of getChange method, of class VendingMachineServiceLayer.
     */
    @Test
    public void getChange100() {
        Change change = service.getChange(new BigDecimal("1.00"));
        assertEquals(4, change.getQuarters());
        assertEquals(0, change.getDimes());
        assertEquals(0, change.getNickels());
        assertEquals(0, change.getPennies());
    }
    
    @Test
    public void getChange099() {
        Change change = service.getChange(new BigDecimal("0.99"));
        assertEquals(3, change.getQuarters());
        assertEquals(2, change.getDimes());
        assertEquals(0, change.getNickels());
        assertEquals(4, change.getPennies());
    }
    
    @Test
    public void getChange091() {
        Change change = service.getChange(new BigDecimal("0.91"));
        assertEquals(3, change.getQuarters());
        assertEquals(1, change.getDimes());
        assertEquals(1, change.getNickels());
        assertEquals(1, change.getPennies());
    }
    
    @Test
    public void getChange055() {
        Change change = service.getChange(new BigDecimal("0.55"));
        assertEquals(2, change.getQuarters());
        assertEquals(0, change.getDimes());
        assertEquals(1, change.getNickels());
        assertEquals(0, change.getPennies());
    }
    
    @Test
    public void getChange025() {
        Change change = service.getChange(new BigDecimal("0.25"));
        assertEquals(1, change.getQuarters());
        assertEquals(0, change.getDimes());
        assertEquals(0, change.getNickels());
        assertEquals(0, change.getPennies());
    }
    
    @Test
    public void getChange009() {
        Change change = service.getChange(new BigDecimal("0.09"));
        assertEquals(0, change.getQuarters());
        assertEquals(0, change.getDimes());
        assertEquals(1, change.getNickels());
        assertEquals(4, change.getPennies());
    }
    
    @Test
    public void getChange005() {
        Change change = service.getChange(new BigDecimal("0.05"));
        assertEquals(0, change.getQuarters());
        assertEquals(0, change.getDimes());
        assertEquals(1, change.getNickels());
        assertEquals(0, change.getPennies());
    }
    
    @Test
    public void getChange001() {
        Change change = service.getChange(new BigDecimal("0.01"));
        assertEquals(0, change.getQuarters());
        assertEquals(0, change.getDimes());
        assertEquals(0, change.getNickels());
        assertEquals(1, change.getPennies());
    }
    
}
