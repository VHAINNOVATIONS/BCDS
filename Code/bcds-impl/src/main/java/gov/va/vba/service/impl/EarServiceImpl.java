package gov.va.vba.service.impl;

import gov.va.vba.persistence.entity.ModelRatingResults;
import gov.va.vba.persistence.models.data.ClaimDetails;
import gov.va.vba.persistence.models.data.DecisionDetails;
import gov.va.vba.persistence.repository.EarDao;
import gov.va.vba.persistence.repository.RatingDao;
import gov.va.vba.service.AppUtill;
import gov.va.vba.service.EarService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ProSphere User on 1/4/2017.
 */
@Service
@Transactional
public class EarServiceImpl implements EarService {

    private static final Logger LOG = LoggerFactory.getLogger(EarServiceImpl.class);

    @Autowired
    private EarDao earDao;

    @Autowired
    private RatingDao ratingDao;

    @Override
    public ModelRatingResults processClaims(int veteranId, List<ClaimDetails> claims, String currentLogin) {

        if (CollectionUtils.isNotEmpty(claims)) {
            Comparator<ClaimDetails> comparator = Comparator.comparing(ClaimDetails::getProfileDate);
            ClaimDetails kneeClaim = claims.stream().max(comparator).get();
            //List<ClaimDetails> previousClaims = earDao.getPreviousClaims(veteranId, kneeClaim.getProfileDate());
            List<DecisionDetails> decisions = earDao.getDecisionsPercentByClaimDate(veteranId, kneeClaim.getClaimDate());
            int calculatedValue = calculateEarRating(decisions);
            BigDecimal priorCdd = getPriorCdd(decisions);
            int age = getClaimentAge(veteranId, kneeClaim);
            Date beginDate = earDao.getBeginDate(veteranId, kneeClaim.getClaimDate());
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
            results.setModelType("Ear");
            //results.setQuantCDD(calculatedCdd.longValue());
            results.setCurrentCDD((long) calculatedValue);
            results.setPriorCDD(priorCdd.longValue());
            LOG.info("SAVING EAR RATING RESULTS " + results);
            return results;
        }
        return null;
    }

    @Override
    public int getClaimentAge(int veteranId, ClaimDetails kneeClaim) {
        int claimantAge = ratingDao.getClaimaintAge(veteranId, kneeClaim.getClaimId());
        return AppUtill.roundToCeilMultipleOfTen(claimantAge);
    }

    @Override
    public BigDecimal getPriorCdd(List<DecisionDetails> decisions) {
        BigDecimal priorCdd = BigDecimal.ZERO;
        if (CollectionUtils.isNotEmpty(decisions)) {
            DecisionDetails decisionDetails = decisions.get(0);
            Map<String, DecisionDetails> map = new HashMap<>();
            map.put(decisionDetails.getDecisionCode(), decisionDetails);
            priorCdd = applyFormula(map);
        }
        return priorCdd;
    }

    private int calculateEarRating(List<DecisionDetails> decisions) {
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
        BigDecimal hundred = BigDecimal.valueOf(100);
        for (Map.Entry<String, DecisionDetails> x : map.entrySet()) {
            DecisionDetails decisionDetails = x.getValue();
            String percentNumber = decisionDetails.getPercentNumber();
            calValue = calValue.multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(Integer.parseInt(percentNumber)).divide(hundred)));
        }
        return (BigDecimal.ONE.subtract(calValue)).multiply(hundred);
    }


}
