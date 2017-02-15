package gov.va.vba.service.impl;

import gov.va.vba.persistence.entity.ModelRatingResults;
import gov.va.vba.persistence.models.data.ClaimDetails;
import gov.va.vba.persistence.models.data.DecisionDetails;
import gov.va.vba.persistence.repository.RatingDao;
import gov.va.vba.service.AppUtill;
import gov.va.vba.service.KneeService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ProSphere User on 1/11/2017.
 */
@Service
@Transactional
public class KneeServiceImpl implements KneeService {

    private static final Logger LOG = LoggerFactory.getLogger(KneeServiceImpl.class);

    @Autowired
    private RatingDao ratingDao;

    @Override
    public ModelRatingResults processClaims(int veteranId, List<ClaimDetails> kneeClaims, String currentLogin) {
        if (CollectionUtils.isNotEmpty(kneeClaims)) {
            ClaimDetails kneeClaim = kneeClaims.get(0);
            int calculatedValue = 0;
            BigDecimal priorCdd = BigDecimal.ZERO;
            List<DecisionDetails> decisions = ratingDao.getDecisionsPercentByClaimDate(veteranId, kneeClaim.getClaimDate());
            if(CollectionUtils.isNotEmpty(decisions)){
	            calculatedValue = calculateKneeRating(decisions);
	                DecisionDetails decisionDetails = decisions.get(0);
	                Map<String, DecisionDetails> map = new HashMap<>();
	                map.put(decisionDetails.getDecisionCode(), decisionDetails);
	                priorCdd = applyFormula(map);
            }
            int age = ratingDao.getClaimaintAge(veteranId, kneeClaim.getClaimId());
            age = AppUtill.roundToCeilMultipleOfTen(age);
            LOG.info("ROUNDED AGE :: {}", age);

            Date beginDate = ratingDao.getBeginDate(veteranId, kneeClaim.getClaimDate());
            int cddAge = 0;
            if(beginDate != null) {
                cddAge = AppUtill.diffInYears(beginDate, kneeClaim.getClaimDate());
            }


            ModelRatingResults results = new ModelRatingResults();
            results.setProcessDate(new Date());


            results.setCDDAge((long) cddAge);
            //results.setClaimAge((long) age);
            results.setClaimId(kneeClaim.getClaimId());
            results.setClaimDate(kneeClaim.getClaimDate());
            results.setProfileDate(kneeClaim.getProfileDate());
            results.setVeteranId((long) veteranId);
            results.setRegionalOfficeNumber(kneeClaim.getClaimRONumber());
            results.setClaimantAge((long) age);
            results.setModelType("Knee");
            //results.setQuantCDD(calculatedCdd.longValue());
            results.setCurrentCDD((long) calculatedValue);
            results.setPriorCDD(priorCdd.longValue());
            LOG.info("SAVING KNEE RATING RESULTS " + results);
            return results;
        }
        return null;
    }

    private int calculateKneeRating(List<DecisionDetails> decisions) {
        Map<String, DecisionDetails> map = new HashMap<>();
        LOG.info("-------------------------------------");
        for (DecisionDetails dd : decisions) {
            if (!map.containsKey(dd.getDecisionCode())) {
                map.put(dd.getDecisionCode(), dd);
                LOG.info("DECISION DETAILS :: CODE IS {} AND PERCENT_NUMBER IS {} ", dd.getDecisionCode(), dd.getPercentNumber());
            }
        }
        LOG.info("-------------------------------------");

        BigDecimal calculatedValue = applyFormula(map);
        LOG.info("CALCULATED VALUE ::::::  {}", calculatedValue.intValue());
        return AppUtill.roundToCeilMultipleOfTen(calculatedValue.intValue());
    }

    private BigDecimal applyFormula(Map<String, DecisionDetails> map) {
        BigDecimal calValue = BigDecimal.ONE;
        for (Map.Entry<String, DecisionDetails> x : map.entrySet()) {
            DecisionDetails decisionDetails = x.getValue();
            String percentNumber = decisionDetails.getPercentNumber();
            calValue = calValue.multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(Integer.parseInt(percentNumber)).divide(BigDecimal.valueOf(100))));
        }
        calValue = (BigDecimal.ONE.subtract(calValue)).multiply(BigDecimal.valueOf(100));
        return (calValue.compareTo(BigDecimal.valueOf(60)) == 1) ? BigDecimal.valueOf(60) : calValue.setScale(-1, RoundingMode.HALF_UP);
    }

}
