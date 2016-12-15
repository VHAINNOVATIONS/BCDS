package gov.va.vba.persistence.util;

import gov.va.vba.persistence.entity.Rating;
import gov.va.vba.persistence.entity.RatingDecision;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by ProSphere User on 11/14/2016.
 */
public class KneeCalculator {

    public static int claimantAge (Date claimDate, Date dateOfBirth) {
        long diff = TimeUnit.MICROSECONDS.toDays(claimDate.getTime() - dateOfBirth.getTime());
        int claimantAge = Math.round((diff) / 365);
        if (claimantAge <= 20) {
            return 20;
        } else if (claimantAge <= 30) {
            return 30;
        } else if (claimantAge <= 40) {
            return 40;
        } else if (claimantAge <= 50) {
            return 50;
        } else if (claimantAge <= 60) {
            return 60;
        } else if (claimantAge <= 70) {
            return 70;
        } else if (claimantAge <= 80) {
            return 80;
        } else if (claimantAge <= 90) {
            return 90;
        } else {
            return 100;
        }
    }

    public static int descisionAge (Date claimDate, Date beginDate) {
        if(claimDate != null && beginDate != null) {
            long diff = TimeUnit.MILLISECONDS.toDays(claimDate.getTime() - beginDate.getTime());
            return Math.round((diff) / 365);
        }
        return 0;
    }

    /*public static int calculateCDD(BigDecimal percentNumber) {
        BigDecimal cdd = new BigDecimal(1).subtract(percentNumber.divide(new BigDecimal(100)));

        BigDecimal kneeCdd = cdd;

        aggregateDecisionKneeCdd = new BigDecimal(100).m * ( new BigDecimal(1).subtract(cdd));

        if hasKneeCDD:
        aggregateDecision.KNEE_CDD = 100 * (1 - kneeCdd)
        if aggregateDecision.KNEE_CDD > 60:
        aggregateDecision.KNEE_CDD = 60

    }*/

    public void KneeCDD(RatingDecision kneeRatings) {
        int totalCDD = 0;
        int totalKneeCDD = 0;
        int aggregateDecisionCDD = 0;
        int aggregateDecisionKneeCDD = 0;
        boolean hasKneeCDD = false;
        List<String> KneeDiagnosisCodeList = Arrays.asList("5055","5161","5162","5163","5164","5164","5256","5257","5258","5259","5260","5261","5262","5263","5264","5313","5314","5315");

        String nbrPrcnt = kneeRatings.getPercentNumber();
        Integer prcnt = StringUtils.isNotBlank(nbrPrcnt) && StringUtils.isNumeric(nbrPrcnt) ? Integer.getInteger(nbrPrcnt) : 0;
        totalCDD *= (1 - (prcnt /100));

        String diagnosisCode = kneeRatings.getDiagnosisCode();
        if (KneeDiagnosisCodeList.contains(diagnosisCode)) {
            hasKneeCDD = true;
            totalKneeCDD *= (1 - (Integer.parseInt(diagnosisCode )/ 100));
        } else {
            aggregateDecisionCDD = (100 * (1 - totalCDD));
        }

        if (hasKneeCDD) {
            aggregateDecisionKneeCDD = (100 * (1 - totalKneeCDD));
            if (aggregateDecisionKneeCDD > 60) {
                aggregateDecisionKneeCDD = 60;
            } else {
                aggregateDecisionKneeCDD = 0;
            }
        }
    }

}
