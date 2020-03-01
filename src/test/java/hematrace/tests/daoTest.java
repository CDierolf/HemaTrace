package hematrace.tests;

import com.hemaapps.hematrace.DAO.BaseProductsDAO;
import com.hemaapps.hematrace.DAO.BaseTransactionDAO;
import com.hemaapps.hematrace.DAO.UserDAO;
import org.junit.jupiter.api.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

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
    private BaseProductsDAO pDao;
    private BaseTransactionDAO tDao = new BaseTransactionDAO();
    private UserDAO uDao = new UserDAO();
    @Test
    public void testBaseProductDAOService() throws SQLException{
        pDao = BaseProductsDAO.getInstance();
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
    
    @Test //valid user = true by crew id
    public void testUserDAOServiceTrue() throws SQLException, ParseException {
        
        assert(uDao.validateCrewUser("1023") == true);
       
    }
    
    @Test // valid user = false by crew id
    public void testUserDAOServiceFalse() throws SQLException, ParseException {
        assert(uDao.validateCrewUser("1022") == false);
    }

} //End Subclass daoTest