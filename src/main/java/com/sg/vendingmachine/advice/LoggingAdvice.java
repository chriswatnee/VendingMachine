/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.advice;

import com.sg.vendingmachine.dao.PersistenceException;
import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author christopherwatnee
 */
public class LoggingAdvice {
    VendingMachineAuditDao auditDao;
    
    public LoggingAdvice(VendingMachineAuditDao auditDao) {
        this.auditDao = auditDao;
    }
    
    public void createAuditEntry(JoinPoint jp, Exception ex) {
        Object[] args = jp.getArgs();
        String auditEntry = "Type of error: " + ex;
        auditEntry += " | Item requested: " + args[0];
        try {
            auditDao.writeAuditEntry(auditEntry);
        } catch (PersistenceException e) {
            System.err.println("ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }
    
}
