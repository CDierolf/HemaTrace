package com.hemaapps.hematrace.utilities;

import com.hemaapps.hematrace.DAO.BaseProductsDAO;
import com.hemaapps.hematrace.Model.BloodProduct;
import com.hemaapps.hematrace.Model.PRBCImpl;
import com.hemaapps.hematrace.Model.PlasmaImpl;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.utilities
 * @Date: Feb 25, 2020
 * @Subclass BloodProductValidation Description: 
 */
//Imports

//Begin Subclass BloodProductValidation
public class BloodProductValidation {
    
    private static final Logger log = LoggerFactory.getLogger(BloodProductValidation.class);
    
    private int prbcDNLength;
    private int plasmaDNLength;
    private boolean isValidDonorNumber;
    private static BaseProductsDAO baseProductsDao;
    
    public BloodProductValidation() throws SQLException {
        populateFieldsFromProperties();
        baseProductsDao = BaseProductsDAO.getInstance();
    }

    public int getPrbcDNLength() {
        return prbcDNLength;
    }

    public void setPrbcDNLength(int prbcDNLength) {
        this.prbcDNLength = prbcDNLength;
    }
    public int getPlasmaDNLength() {
        return plasmaDNLength;
    }
    public void setPlasmaDNLength(int plasmaDNLength) {
        this.plasmaDNLength = plasmaDNLength;
    }
    public void setIsValidDonorNumber(boolean isValidDonorNumber) {
        this.isValidDonorNumber = isValidDonorNumber;
    }
    public boolean getIsValidDonorNumber() {
        return this.isValidDonorNumber;
    } 
    
    public boolean isValidDonorNumber(String donorNumber, int dnLength) {
        boolean valid = false;
        if (dnLength == getPlasmaDNLength()) {
            for (PlasmaImpl pi : baseProductsDao.getBasePlasmaProducts()) {
                if (pi.getDonorNumber().equals(donorNumber)) {
                    valid = true;
                    log.info(String.format("Validating scanned plasma donor number: %s"
                        + ". Valid donor number for plasma product: %s"
                        + ". Scanned DN length: %s.", donorNumber, valid, donorNumber.length()));
                    break;
                }
            }
        }
        if (dnLength == getPrbcDNLength()) {
            System.out.println("ENTERING PRBC. Size: " + baseProductsDao.getBasePRBCProducts().size());
            for (PRBCImpl pi : baseProductsDao.getBasePRBCProducts()) {
                if (pi.getDonorNumber().equals(donorNumber)) {
                    valid = true;
                    log.info(String.format("Validating scanned PRBC donor number: %s"
                        + ". Valid donor number for PRBC product: %s"
                        + ". Scanned DN length: %s.", donorNumber, valid, donorNumber.length()));
                    break;
                } 
            }
        }
        return valid;
    }
    
    private void populateFieldsFromProperties() {
        try (InputStream input = new FileInputStream("src/main/resources/properties/bloodproduct.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            setPrbcDNLength(Integer.parseInt(prop.getProperty("prbcDonorNumberLength")));
            setPlasmaDNLength(Integer.parseInt(prop.getProperty("plasmaDonorNumberLength")));
            log.info("BloodProduct properties successfully loaded.");
        } catch (IOException ex) {
            log.error(String.format("BloodProduct properties failed to load: %s.", ex.getMessage()));
        }
    }
    
    
} //End Subclass BloodProductValidation