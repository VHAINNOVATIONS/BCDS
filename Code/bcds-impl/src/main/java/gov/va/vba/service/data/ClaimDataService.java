package gov.va.vba.service.data;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import gov.va.vba.domain.Claim;
import gov.va.vba.persistence.entity.RatingDecision;
import gov.va.vba.persistence.repository.ClaimRepository;
import gov.va.vba.persistence.repository.RatingDecisionRepository;
import gov.va.vba.persistence.util.KneeCalculator;
import gov.va.vba.service.orika.ClaimMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClaimDataService extends AbsDataService<gov.va.vba.persistence.entity.Claim, Claim> {

    private static final Logger LOG = LoggerFactory.getLogger(ClaimDataService.class);

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private RatingDecisionRepository ratingDecisionRepository;

    @Autowired
    private ClaimMapper claimMapper;

    public ClaimDataService() {
        this.setClasses(gov.va.vba.persistence.entity.Claim.class, Claim.class);
    }

    public List<Claim> findAll() {
        List<Claim> output = new ArrayList<>();
        List<gov.va.vba.persistence.entity.Claim> input = ((ClaimRepository) repository).findAll();
        mapper.mapAsCollection(input, output, outputClass);
        return output;
    }

    public List<Claim> findFirstNumberedRow() {
        List<Claim> output = new ArrayList<>();
        List<gov.va.vba.persistence.entity.Claim> input = ((ClaimRepository) repository).findFirstNumberedRow();
        List<Claim> claims = claimMapper.mapCollection(input);
        LOG.info("SIZE :::: " +claims.size());
        return claims;
    }

    public List<Claim> findByVeteranId(Long veteranId) {
        Long[] x = {230L, 270L, 3690L, 3700L, 3710L, 8919L, 3720L, 3730L, 3780L, 3790L, 3800L};
        String[] y = {"5055", "5161", "5162", "5163", "5164", "5165", "5256", "5257", "5258", "5259", "5260", "5261", "5262", "5263", "5264", "5313", "5314", "5315"};
        List<Long> ids = Arrays.asList(x);
        List<gov.va.vba.persistence.entity.Claim> result = claimRepository.findByVeteranVeteranIdAndContentionClsfcnIdIn(veteranId, ids);
        List<Object[]> decisionDetails = ratingDecisionRepository.findDecisionDetails(veteranId);
        Map<gov.va.vba.persistence.entity.Claim, List<RatingDecision>> map = new HashMap<>();
        for (gov.va.vba.persistence.entity.Claim claim : result) {
            List<RatingDecision> decisions = ratingDecisionRepository.findByVeteranVeteranIdAndProfileDateLessThanAndPercentNumberNotNullAndDiagnosisCodeIn(new BigDecimal(claim.getVeteran().getVeteranId()), claim.getClaimDate(), Arrays.asList(y));
            if (CollectionUtils.isNotEmpty(decisions)) {
                List<RatingDecision> filteredDecisions = decisions.stream().filter(d -> d.getPercentNumber() != null).collect(Collectors.toList());
                map.put(claim, filteredDecisions);
            }
        }
        for (Map.Entry<gov.va.vba.persistence.entity.Claim, List<RatingDecision>> entrySet : map.entrySet()) {
            gov.va.vba.persistence.entity.Claim claim = entrySet.getKey();
            List<RatingDecision> decisions = entrySet.getValue();
            LOG.info("Total Decisions " + decisions.size());
            Map<RatingDecision, BigDecimal> m = aggregateRatingDecisions(decisions);
            BigDecimal calculatedCdd = new BigDecimal(1);
            for(Map.Entry<RatingDecision, BigDecimal> entry : m.entrySet()) {
                RatingDecision decision = entry.getKey();
                BigDecimal percentNumber = entry.getValue();
                List<Object[]> objects = claimRepository.aggregateContentions(claim.getClaimId(), veteranId);
                int birthYear = Integer.parseInt(claim.getVeteran().getBirthYear());
                Calendar dob = Calendar.getInstance();
                dob.set(1, 1, birthYear);
                int age = KneeCalculator.claimantAge(claim.getClaimDate(), dob.getTime());
                LOG.info("Claim " + claim.getClaimId() + " Age is " + age);
                //TODO use age
                List<Date> currentClaims = ratingDecisionRepository.findCurrentClaims(veteranId, claim.getClaimDate());
                List<Date> previousClaims = ratingDecisionRepository.findPreviousClaims(veteranId, claim.getClaimDate());
                LOG.info(currentClaims + "Current claims found for claim " + claim.getClaimId());
                LOG.info(previousClaims + "Previous claims found for claim " + claim.getClaimId());
                for (Date profileDate : currentClaims) {
                    List<Integer> currentCDD = ratingDecisionRepository.calculateDecisionCDD(veteranId, profileDate);
                }
                for (Date profileDate : previousClaims) {
                    List<Integer> previousCDD = ratingDecisionRepository.calculateDecisionCDD(veteranId, profileDate);
                }
                //KneeCalculator.calculateCDD(percentNumber);

                calculatedCdd = calculatedCdd.multiply(BigDecimal.valueOf(1)).subtract(percentNumber.divide(new BigDecimal(100)));
            }
            calculatedCdd = calculatedCdd.multiply(BigDecimal.valueOf(100));
            calculatedCdd = (calculatedCdd.compareTo(BigDecimal.valueOf(60)) < 0) ? BigDecimal.valueOf(60) : calculatedCdd;
        }
        List<Claim> output = new ArrayList<>();
        //mapper.mapAsCollection(validClaims, output, outputClass);
        return output;
    }

    private Map<RatingDecision, BigDecimal> aggregateRatingDecisions(List<RatingDecision> decisions) {
        Map<RatingDecision, BigDecimal> m = new HashMap<>();
        for (RatingDecision decision : decisions) {
            boolean isFound = false;
            for (Map.Entry<RatingDecision, BigDecimal> r : m.entrySet()) {
                RatingDecision key = r.getKey();
                if (key.getDiagnosisCode().equals(decision.getDiagnosisCode()) && key.getProfileDate().equals(decision.getProfileDate())) {
                    BigDecimal value = r.getValue();
                    BigDecimal bigDecimal = new BigDecimal(key.getPercentNumber());
                    m.put(key, value.add(bigDecimal));
                    isFound = true;
                }
            }
            if(!isFound) {
                m.put(decision, new BigDecimal(decision.getPercentNumber()));
            }


        }
        return m;
    }

    public List<Claim> findClaimsInRanged(Date from, Date to) {
        List<gov.va.vba.persistence.entity.Claim> result = claimRepository.findByClaimDateBetween(from, to);
        List<Claim> output = new ArrayList<>();
        mapper.mapAsCollection(result, output, outputClass);
        return output;
    }

    public List<Claim> findClaims(boolean establishedDate, Date fromDate, Date toDate, String contentionType, Long regionalOfficeNumber) {
        if (fromDate == null) {
            Calendar instance = Calendar.getInstance();
            instance.set(1900, 12, 31);
            fromDate = instance.getTime();
        }
        if (toDate == null) {
            toDate = new Date();
        }
        List<gov.va.vba.persistence.entity.Claim> result = (establishedDate) ? claimRepository.findClaimSByRangeOnProfileDate(contentionType, regionalOfficeNumber, fromDate, toDate) : claimRepository.findClaimSByRangeOnClaimDate(contentionType, regionalOfficeNumber, fromDate, toDate);
        List<Claim> output = new ArrayList<>();
        mapper.mapAsCollection(result, output, outputClass);
        return output;
    }

    public List<Claim> calculateContentions(Long claimId, Long veteranId) {
        List<Object[]> objects = claimRepository.aggregateContentions(claimId, veteranId);
        List<Date> previousClaims = ratingDecisionRepository.findPreviousClaims(21213L, new Date());
        return null;
    }
}
