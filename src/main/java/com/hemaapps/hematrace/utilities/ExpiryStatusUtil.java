package com.hemaapps.hematrace.utilities;

import com.hemaapps.hematrace.enums.BloodProductStatus;
import static java.lang.Math.abs;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.LocalDate;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: com.hemaapps.hematrace.utilities
 * @Date: Mar 20, 2020
 * @Subclass ExpiryStatusUtil Description: 
 */
//Imports

//Begin Subclass ExpiryStatusUtil
public class ExpiryStatusUtil {
    
   public static BloodProductStatus getExpiryStatus(Date date) {
       
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       float numDays = 0.0f;
       
       try {
           Date currentDate = format.parse(LocalDate.now().toString());
           System.out.println(currentDate);
           Date expDate = format.parse(date.toString());
           System.out.println(expDate);
           
           long diff = expDate.getTime() - currentDate.getTime();
           numDays = diff / (1000*60*60*24);
       } catch (Exception e) {
           System.out.println(e);
       }
       
       System.out.println(numDays);
       if (numDays > 7)  {
           return BloodProductStatus.OK;
       } else if (numDays <= 7 && numDays > 0) {
           return BloodProductStatus.EXPIRING;
       } else if (numDays <= 0) {
           return BloodProductStatus.EXPIRED;
       } else {
           return BloodProductStatus.NOT_AVAILABLE;
       }
   }
    
   
    

} //End Subclass ExpiryStatusUtil