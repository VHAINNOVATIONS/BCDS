package gov.va.vba.service.data;

import gov.va.vba.bcdss.models.ClaimRating;
import gov.va.vba.bcdss.models.EarDecision;
import gov.va.vba.bcdss.models.KneeDecision;
import gov.va.vba.bcdss.models.Rating;
import gov.va.vba.bcdss.models.RatingDecisions;
import gov.va.vba.bcdss.models.VeteranClaim;
import gov.va.vba.bcdss.models.VeteranClaimRating;
import gov.va.vba.domain.Claim;
import gov.va.vba.persistence.entity.DDMContention;
import gov.va.vba.persistence.entity.ModelRatingResults;
import gov.va.vba.persistence.entity.ModelRatingResultsCntnt;
import gov.va.vba.persistence.entity.ModelRatingResultsCntntId;
import gov.va.vba.persistence.entity.ModelRatingResultsDiag;
import gov.va.vba.persistence.entity.ModelRatingResultsDiagId;
import gov.va.vba.persistence.entity.ModelRatingResultsStatus;
import gov.va.vba.persistence.entity.ModelRatingResultsStatusId;
import gov.va.vba.persistence.entity.RatingDecision;
import gov.va.vba.persistence.repository.ClaimRepository;
import gov.va.vba.persistence.repository.DDMContentionRepository;
import gov.va.vba.persistence.repository.ModelRatingResultsRepository;
import gov.va.vba.persistence.repository.ModelRatingResultsCntnRepository;
import gov.va.vba.persistence.repository.RatingDecisionRepository;
import gov.va.vba.persistence.util.KneeCalculator;
import gov.va.vba.service.orika.ClaimMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ClaimDataService extends AbsDataService<gov.va.vba.persistence.entity.Claim, Claim> {

    private static final Logger LOG = LoggerFactory.getLogger(ClaimDataService.class);
    private static final List<Long> KNEE_CONTENTION_IDS = Arrays.asList(230L, 270L, 3690L, 3700L, 3710L, 8919L, 3720L, 3730L, 3780L, 3790L, 3800L);

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private RatingDecisionRepository ratingDecisionRepository;

    @Autowired
    private ClaimMapper claimMapper;

    @Autowired
    private ModelRatingResultsRepository modelRatingResultsRepository;

    @Autowired
    private ModelRatingResultsCntnRepository modelRatingResultsCntnRepository;

    @Autowired
    private DDMContentionRepository ddmContentionRepository;

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
        List<gov.va.vba.persistence.entity.Claim> input = ((ClaimRepository) repository).findFirstNumberedRow();
        List<Claim> claims = claimMapper.mapCollection(input);
        for (Claim c : claims) {
            c.setCestDate(calculateCestDate(c.getClaimDate()));
        }
        LOG.info("SIZE :::: " + claims.size());
        return claims;
    }

    public List<VeteranClaimRating> findByVeteranId(List<VeteranClaim> veteranClaims) {
        String[] y = {"5055", "5161", "5162", "5163", "5164", "5165", "5256", "5257", "5258", "5259", "5260", "5261", "5262", "5263", "5264", "5313", "5314", "5315"};
        List<VeteranClaimRating> veteranClaimRatings = new ArrayList<>();
        for (VeteranClaim vc : veteranClaims) {
            VeteranClaimRating veteranClaimRating = new VeteranClaimRating();
            veteranClaimRating.setVeteran(vc.getVeteran());
            int veteranId = vc.getVeteran().getVeteranId();
            List<gov.va.vba.bcdss.models.Claim> claims = vc.getClaim();
            List<Object[]> decisionDetails = ratingDecisionRepository.findDecisionDetails((long) veteranId);
            Map<gov.va.vba.persistence.entity.Claim, List<RatingDecision>> map = new HashMap<>();
            int claimCount = 0;
            List<ClaimRating> claimRatings = new ArrayList<>();

            for (gov.va.vba.bcdss.models.Claim c : claims) {
                int claimId = c.getClaimId();
                gov.va.vba.persistence.entity.Claim claim = claimRepository.findOneByClaimId((long) claimId);
                if (claim != null) {
                    List<RatingDecision> claimDecisions = ratingDecisionRepository.findByVeteranVeteranIdAndProfileDateLessThanAndPercentNumberNotNullAndDiagnosisCodeIn((long) vc.getVeteran().getVeteranId(), claim.getClaimDate(), Arrays.asList(y));
                    if (CollectionUtils.isNotEmpty(claimDecisions)) {
                        claimCount += 1;
                        Map<String, Float> calculatedPercentNumber = new HashMap<>();
                        for(RatingDecision rd : claimDecisions) {
                            String diagnosisCode = rd.getDiagnosisCode();
                            if(calculatedPercentNumber.containsKey(diagnosisCode)) {
                                Float value = calculatedPercentNumber.get(diagnosisCode);
                                float updatedValue = (value + Float.valueOf(rd.getPercentNumber())) / 2;
                                calculatedPercentNumber.put(diagnosisCode, updatedValue);
                            } else {
                                calculatedPercentNumber.put(diagnosisCode, Float.valueOf(rd.getPercentNumber()));
                            }
                        }
                        Float calculatedCdd = calculateKneeRating(calculatedPercentNumber);
                        List<RatingDecision> decisions = claimDecisions.stream().filter(d -> d.getPercentNumber() != null).collect(Collectors.toList());
                        LOG.info("Total Decisions " + decisions.size());
                        /*Map<RatingDecision, BigDecimal> m = aggregateRatingDecisions(decisions);
                        BigDecimal calculatedCdd = new BigDecimal(1);
                        //int contentionsCount = 0;

                        for (Map.Entry<RatingDecision, BigDecimal> entry : m.entrySet()) {
                            RatingDecision decision = entry.getKey();
                            BigDecimal percentNumber = entry.getValue();
                            //KneeCalculator.calculateCDD(percentNumber);
                            //contentionsCount += objects.size();
                            calculatedCdd = calculatedCdd.multiply(BigDecimal.valueOf(1)).subtract(percentNumber.divide(BigDecimal.valueOf(100)));
                        }
                        calculatedCdd = calculatedCdd.multiply(BigDecimal.valueOf(100));
                        calculatedCdd = (calculatedCdd.compareTo(BigDecimal.valueOf(60)) < 0) ? BigDecimal.valueOf(60) : calculatedCdd;
*/
                        List<Date> currentClaims = ratingDecisionRepository.findCurrentClaims((long) veteranId, claim.getClaimDate());
                        List<Date> previousClaims = ratingDecisionRepository.findPreviousClaims((long) veteranId, claim.getClaimDate());
                        LOG.info(currentClaims + "Current claims found for claim " + claim.getClaimId());
                        LOG.info(previousClaims + "Previous claims found for claim " + claim.getClaimId());

                        List<Integer> currentCDD = new ArrayList<>();
                        for (Date profileDate : currentClaims) {
                            List<Integer> cdd = ratingDecisionRepository.calculateDecisionCDD((long) veteranId, profileDate);
                            currentCDD.addAll(cdd);
                        }
                        List<Integer> previousCDD = new ArrayList<>();
                        for (Date profileDate : previousClaims) {
                            List<Integer> cdd = ratingDecisionRepository.calculateDecisionCDD((long) veteranId, profileDate);
                            previousCDD.addAll(cdd);
                        }

                        int birthYear = Integer.parseInt(claim.getVeteran().getBirthYear());
                        Calendar dob = Calendar.getInstance();
                        dob.set(1, 1, birthYear);
                        int age = KneeCalculator.claimantAge(claim.getClaimDate(), dob.getTime());
                        LOG.info("Claim " + claim.getClaimId() + " Age is " + age);

                        int currentCddSum = currentCDD.stream().mapToInt(Integer::intValue).sum();
                        int previousCddSum = previousCDD.stream().mapToInt(Integer::intValue).sum();

                        ModelRatingResults results = new ModelRatingResults();
                        results.setProcessDate(new Date());
                        results.setProcessId(generateRandomId());
                        results.setClaimAge((long) age);
                        results.setClaimId(claim.getClaimId());
                        results.setClaimDate(claim.getClaimDate());
                        results.setProfileDate(claim.getProfileDate());
                        results.setVeteranId((long) veteranId);
                        results.setRegionalOfficeNumber(claim.getClaimRegionalOfficeNumber());
                        results.setClaimantAge((long) age);
                        results.setClaimCount((long) claimCount);
                        //results.setQuantCDD(calculatedCdd.longValue());
                        results.setCurrentCDD(calculatedCdd.longValue());
                        results.setPriorCDD((long) previousCddSum);
                        ModelRatingResults savedResults = modelRatingResultsRepository.save(results);
                        BigDecimal processId = BigDecimal.valueOf(savedResults.getProcessId());

                        saveModelResultsCtnts(veteranId, claim.getClaimId(), savedResults);

                        ModelRatingResultsDiagId diagId = new ModelRatingResultsDiagId(null, processId);
                        ModelRatingResultsDiag diag = new ModelRatingResultsDiag();
                        diag.setId(diagId);

                        ModelRatingResultsStatusId statusId = new ModelRatingResultsStatusId(processId, "Test" + claim.getClaimId());
                        ModelRatingResultsStatus status = new ModelRatingResultsStatus();
                        status.setId(statusId);
                        status.setCrtdDtm(new Date());

                        EarDecision ed = new EarDecision();
                        KneeDecision kd = new KneeDecision();
                        RatingDecisions ratingDecisions = new RatingDecisions();
                        ratingDecisions.setProcessId(processId.intValue());
                        ratingDecisions.setEarRatings(ed);
                        ratingDecisions.setKneeRatings(kd);
                        gov.va.vba.bcdss.models.Rating rating = new Rating();
                        rating.setCddAge(age);
                        rating.setClaimAge(age);
                        rating.setClaimantAge(age);
                        rating.setClaimCount(claimCount);
                        //rating.setContationCount(contentionsCount);
                        rating.setCurrentCdd(calculatedCdd.intValue());
                        rating.setPriorCdd(previousCddSum);
                        rating.setProcessId(processId.intValue());
                        //rating.setQuantCdd(calculatedCdd.intValue());
                        rating.setRatingDecisions(ratingDecisions);
                        rating.setModelType("KNEE");
                        rating.setProcessDate(savedResults.getClaimDate());
                        c.setClaimDate(claim.getClaimDate());
                        c.setProfileDate(claim.getProfileDate());
                        c.setContentionClassificationId(String.valueOf(claim.getContentionClsfcnId()));
                        c.setContentionId(Integer.parseInt(claim.getContentionId()));
                        c.setProductTypeCode(claim.getEndPrdctTypeCode());
                        c.setContentionBeginDate(claim.getContentionBeginDate());
                        ClaimRating cr = new ClaimRating();
                        cr.setClaim(c);
                        cr.setRating(rating);
                        claimRatings.add(cr);
                    }
                }
            }
            veteranClaimRating.getClaimRating().addAll(claimRatings);
            veteranClaimRatings.add(veteranClaimRating);
        }
        return veteranClaimRatings;
    }

    /**
     * Description: This method is used to save contentions count
     *  @param veteranId - veteranId
     * @param claimId   - claimId
     * @param results - results
     */
    private void saveModelResultsCtnts(long veteranId, long claimId, ModelRatingResults results) {
        List<Object[]> contentionsCount = claimRepository.aggregateContentions(claimId, veteranId);
        HashMap<Long, Long> countMap = new HashMap<>();
        for (Long i : KNEE_CONTENTION_IDS) {
            countMap.put(i, 0L);
        }
        for (Object[] x : contentionsCount) {
            String groupName = String.valueOf(x[0]);
            long count = (long) x[1];
            Long cntnId = Long.valueOf(groupName.split("_")[1]);
            if (countMap.containsKey(cntnId)) {
                countMap.put(cntnId, count);
            }
        }
        List<DDMContention> allCntnts = ddmContentionRepository.findAll();
        List<ModelRatingResultsCntnt> cntntList = new ArrayList<>();
        for (Map.Entry<Long, Long> x : countMap.entrySet()) {
            ModelRatingResultsCntntId cntntId = new ModelRatingResultsCntntId(x.getKey(), results.getProcessId());
            ModelRatingResultsCntnt cntnt = new ModelRatingResultsCntnt();
            cntnt.setId(cntntId);
            cntnt.setCount(BigDecimal.valueOf(x.getValue()));
            DDMContention ddmContention = getDDMContention(allCntnts, x.getKey());
            if(ddmContention != null) {
                cntnt.setContention(ddmContention);
                cntnt.setModelRatingResults(results);
                cntntList.add(cntnt);
            }
        }
        modelRatingResultsCntnRepository.save(cntntList);
        LOG.info("SUCCESSFULLY SAVED CONTENTION COUNT INTO MODEL_RATING_RESULTS_CNTNT TABLE");

    }

    private DDMContention getDDMContention(List<DDMContention> allCntnts, Long contentionCode) {
        for (DDMContention ddmContention : allCntnts) {
            if (ddmContention.getContentionCode().equals(contentionCode)) {
                return ddmContention;
            }
        }
        return null;
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
            if (!isFound) {
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

    private Date calculateCestDate(Date claimDate) {
        if (claimDate != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(claimDate);
            instance.add(Calendar.DAY_OF_MONTH, 10);
            return instance.getTime();
        }
        return null;
    }

    // -- remove this use @GeneratedValue
    @Deprecated
    private long generateRandomId() {
        long x = 12345L;
        long y = 23456789L;
        Random r = new Random();
        return x + ((long) (r.nextDouble() * (y - x)));
    }

    private float calculateKneeRating(Map<String, Float> calculatedPercentNumber) {
        float calVal = 1;
        for(Float x : calculatedPercentNumber.values()) {
            calVal *= 1 - (x / 100);
        }
        calVal = (1-calVal) * 100;
        return (calVal < 60) ? 60 : calVal;
    }

}
