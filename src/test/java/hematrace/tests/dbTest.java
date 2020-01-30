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
    
    public dbTest() {
    }
    
    @Test
    public void testDbConnection() {
        Database db = new Database();
        db.init();
        assert(db.getConnection() != null);
    }
    
}
