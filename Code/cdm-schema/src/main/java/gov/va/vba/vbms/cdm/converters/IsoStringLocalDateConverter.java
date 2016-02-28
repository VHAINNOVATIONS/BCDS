package gov.va.vba.vbms.cdm.converters;

import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;

public class IsoStringLocalDateConverter {

    public static LocalDate parseLocalDate(String dateString) {
        try {
            return new LocalDate(dateString);
        }
        catch(IllegalArgumentException e) {
            return null;
        }
    }

    public static String printLocalDate(LocalDate localDate) {
        if( localDate != null ){
            return ISODateTimeFormat.date().print(localDate);
        }
        return null;
    }
}
