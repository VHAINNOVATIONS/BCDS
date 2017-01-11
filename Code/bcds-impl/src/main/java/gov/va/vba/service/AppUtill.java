package gov.va.vba.service;

import org.apache.commons.collections.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by ProSphere User on 12/14/2016.
 */
public class AppUtill {

    /**
     * Description: This method gives oldest date from the list of dates
     *
     * @param dates - list of dates
     * @return - max date
     */
    public static Date getMaxDate(List<Date> dates) {
        if (CollectionUtils.isEmpty(dates)) {
            return null;
        }
        return dates.stream().max(Date::compareTo).get();
    }

    public static int roundToCeilMultipleOfTen(int value) {
        int i = value / 10;
        if ((value % 10) > 0) {
            return (i + 1) * 10;
        }
        return value;
    }

}
