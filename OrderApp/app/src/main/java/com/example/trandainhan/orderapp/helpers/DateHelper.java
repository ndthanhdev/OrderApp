package com.example.trandainhan.orderapp.helpers;

import java.util.Date;

/**
 * Created by duyth on 5/22/2017.
 */

public class DateHelper {

    public static CharSequence dateToString(Date date){
        return android.text.format.DateFormat.format("dd-MM-yyyy",date);
    }
}
