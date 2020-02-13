package hematrace.tests;

import com.hemaapps.hematrace.DAO.BaseProductsDAO;
import com.hemaapps.hematrace.DAO.BaseTransactionDAO;
import org.junit.jupiter.api.Test;
import java.sql.ResultSet;
import java.sql.SQLException;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: hematrace.tests
 * @Date: Feb 4, 2020
 * @Subclass daoTest Description: 
 */
//Imports

//Begin Subclass daoTest
public class daoTest {
    private BaseProductsDAO pDao = new BaseProductsDAO();
    private BaseTransactionDAO tDao = new BaseTransactionDAO();
    @Test
    public void testBaseProductDAOService() {
        pDao.setBaseBloodProductsResultSet(2);
        ResultSet rs = pDao.getResultSet();
        assert(rs != null);
    }
    
    @Test
    public void testBaseTransactionDAOService() throws SQLException {
        tDao.setBaseId(2);
        tDao.setTransactions();
        assert(tDao.getTransactionResultSet() != null);
    }

} //End Subclass daoTest