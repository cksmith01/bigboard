package gov.utleg.bigboard.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dates {

    public static final String UI_SHORT = "MM/dd/yyyy";
    public static final String UI_FULL = "MM/dd/yyyy HH:mm:ss";
    public static final String ALL_NUMERIC = "yyyyMMddHHmmss";
    public static final String DB_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String DB_SHORT = "yyyy-MM-dd";
    public static final String TIME_STAMP = "yyyyMMddHHmmss";
    public static final String TIME = "HH:mm";

    private static final SimpleDateFormat sdf = new SimpleDateFormat(UI_SHORT);

    public static String format(Date date) {
        if (date != null)
            return sdf.format(date);
        else
            return sdf.format(new Date());
    }

    /**
     * @param date
     * @return MM/dd/yyyy HH:mm:ss
     */
    public static String formatFull(Date date) {
        return Dates.format(date, UI_FULL);
    }
    
    /**
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDB(Date date) {
        return Dates.format(date, DB_FULL);
    }
    
    /**
     * @param date
     * @return yyyy-MM-dd
     */
    public static String formatDBShort(Date date) {
        return Dates.format(date, DB_SHORT);
    }

    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null)
            return sdf.format(date);
        else
            return sdf.format(new Date());
    }

    public static Date parse(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(date);
    }

    public static Date parse(String str) throws ParseException {
    	if (Strings.isBlank(str)) {
    		return null;
    	}
    	String format = null;
    	if (str.indexOf(" ") > -1) {
    		if (str.indexOf("-") > -1) {
    			format = DB_FULL;
    		} else {
    			format = UI_FULL;
    		}
    	} else {
    		if (str.indexOf("-") > -1) {
    			format = DB_SHORT;
    		} else {
    			format = UI_SHORT;
    		}
    	}
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
    }
    
    public static String getDuration(double start) {
        BigDecimal dSec = new BigDecimal((System.currentTimeMillis() - start) / 1000);
        String durration = dSec.round(new MathContext(2)) + " second(s)";
        if (dSec.doubleValue() > 60) {
            dSec = new BigDecimal(dSec.doubleValue()/60);
            durration = dSec.round(new MathContext(1)) + " minute(s)";
        }
        return durration;
    }

    public static int getDaysSince(Date date) {
        double r = (System.currentTimeMillis() - date.getTime()) / (1000 * 60 * 60 * 24);
        return (int) r;
    }
    
    public static String getTimestamp() {
    	String ts = (new SimpleDateFormat(TIME_STAMP)).format(new Date());
    	
    	return ts;
    }
    
    public static Date getDateFromToday(int days) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.DATE, days);
    	return cal.getTime();
    }

    public static void main(String... args) {
        try {
			System.out.println(Dates.parse("2000-02-14 13:02:59"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
