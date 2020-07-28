package Entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampToDate {
    public static String getDate(long timestamp){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date netDate = (new Date(timestamp));
        return formatter.format(netDate);
    }
}
