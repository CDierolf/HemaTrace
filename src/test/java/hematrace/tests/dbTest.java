/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hematrace.tests;

import com.hemaapps.hematrace.Database.DatabaseService;
import org.junit.jupiter.api.Test;

/**
 *
 * @author pis7ftw
 */
public class dbTest {
    private DatabaseService databaseService = new DatabaseService();
   
    
    @Test
    public void testDbConnection() {
        databaseService.init();
        assert(databaseService.getConnection() != null);
    }
    
    @Test
    public void testDbConnectionStringSet() {
        assert(databaseService.isConnectionStringSet());
    }
    
}
