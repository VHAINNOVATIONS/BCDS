package gov.va.vba.vbms.cdm.converters;

import javax.xml.bind.DatatypeConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateConverter {
    public static Date parseDate(String s) {
        try {
            return DatatypeConverter.parseDate(s).getTime();
        }
        catch(IllegalArgumentException e) {
            return null;
        }
    }
    public static String printDate(Date dt) {
        if( dt != null ){
            Calendar cal = new GregorianCalendar();
            cal.setTime(dt);
            return DatatypeConverter.printDate(cal);
        }
        return null;
    }
}
