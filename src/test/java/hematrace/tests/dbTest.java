/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hematrace.tests;

import com.hemaapps.hematrace.Database.Database;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author pis7ftw
 */
public class dbTest {
    
    
    Database db = new Database();
    
    public dbTest() {
    }
    
    @Test
    public void testDbConnection() {
        db.init();
        assert(db.getConnection() != null);
    }
    
    @Test
    public void testDbConnectionStringSet() {
        assert(db.isConnectionStringSet());
    }
    
}
