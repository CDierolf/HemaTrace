package com.hemaapps.hematrace.utilities;

import com.hemaapps.hematrace.enums.BloodProductStatus;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javafx.scene.paint.Color;

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
            Date expDate = format.parse(date.toString());

            long diff = expDate.getTime() - currentDate.getTime();
            numDays = diff / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(numDays);
        if (numDays > 7) {
            return BloodProductStatus.OK;
        } else if (numDays <= 7 && numDays > 0) {
            return BloodProductStatus.EXPIRING;
        } else if (numDays <= 0) {
            return BloodProductStatus.EXPIRED;
        } else {
            return BloodProductStatus.NOT_AVAILABLE;
        }
    }

    public static Color getExpiryColor(BloodProductStatus bpStatus) {

        if (null == bpStatus) {
            return null;
        } else switch (bpStatus) {
            case EXPIRED:
                return Color.RED;
            case EXPIRING:
                return Color.YELLOW;
            case OK:
                return Color.GREEN;
            case NOT_AVAILABLE:
                return Color.BLACK;
            default:
                return null;
        }
    }

} //End Subclass ExpiryStatusUtil
