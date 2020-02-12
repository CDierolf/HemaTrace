package hematrace.tests;

import com.hemaapps.hematrace.DAO.BaseProductsDAO;
import com.hemaapps.hematrace.Model.BloodProduct;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

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
    private BaseProductsDAO dao = new BaseProductsDAO();
    @Test
    public void testBaseProductDAOService() {
        
        dao.initDao(2);
        assert(dao.getBaseBloodProducts(2) != null);
        
    }

} //End Subclass daoTest