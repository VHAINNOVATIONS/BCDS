package gov.va.vba.service.data;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import gov.va.vba.domain.Claim;
import gov.va.vba.persistence.entity.RatingDecision;
import gov.va.vba.persistence.repository.ClaimRepository;
import gov.va.vba.persistence.repository.RatingDecisionRepository;
import gov.va.vba.persistence.util.KneeCalculator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimDataService extends AbsDataService<gov.va.vba.persistence.entity.Claim, Claim> {

    private static final Logger LOG = LoggerFactory.getLogger(ClaimDataService.class);

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private RatingDecisionRepository ratingDecisionRepository;

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
        mapper.mapAsCollection(input, output, outputClass);
        return output;
    }

    public List<Claim> findByVeteranId(Long veteranId) {
        Long[] x = {230L, 270L, 3690L, 3700L, 3710L, 8919L, 3720L, 3730L, 3780L, 3790L, 3800L};
        String[] y = {"5055", "5161", "5162", "5163", "5164", "5165", "5256", "5257", "5258", "5259", "5260", "5261", "5262", "5263", "5264", "5313", "5314", "5315"};
        List<Long> ids = Arrays.asList(x);
        List<gov.va.vba.persistence.entity.Claim> result = claimRepository.findByVeteranVeteranIdAndContentionClsfcnIdIn(veteranId, ids);
        List<Object[]> decisionDetails = ratingDecisionRepository.findDecisionDetails(veteranId);
        result.stream().filter(c -> {
            List<RatingDecision> decisions = ratingDecisionRepository.findByVeteranVeteranIdAndProfileDateLessThanAndPercentNumberNotNullAndDiagnosisCodeIn(new BigDecimal(c.getVeteran().getVeteranId()), c.getClaimDate(), Arrays.asList(y));
            return CollectionUtils.isNotEmpty(decisions);
            /*if (CollectionUtils.isNotEmpty(decisions)) {
                LOG.info("Total Decisions " + decisions.size() );
                for (RatingDecision decision : decisions) {
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
                    for(Date profileDate : currentClaims) {
                        List<Integer> currentCDD = ratingDecisionRepository.calculateDecisionCDD(veteranId, profileDate);
                    }
                    for(Date profileDate : previousClaims) {
                        List<Integer> previousCDD = ratingDecisionRepository.calculateDecisionCDD(veteranId, profileDate);
                    }
                    KneeCalculator.calculateCDD(claim.get);
                }
            }*/
        }).collect(Collectors.toList());
        List<Claim> output = new ArrayList<>();


        //mapper.mapAsCollection(validClaims, output, outputClass);
        return output;
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
