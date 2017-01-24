package gov.va.vba.service.data;

import gov.va.vba.bcdss.models.ClaimRating;
import gov.va.vba.bcdss.models.Rating;
import gov.va.vba.bcdss.models.RatingDecisions;
import gov.va.vba.bcdss.models.VeteranClaim;
import gov.va.vba.bcdss.models.VeteranClaimRating;
import gov.va.vba.domain.Claim;
import gov.va.vba.persistence.common.ModelType;
import gov.va.vba.persistence.entity.DDMModelPatternIndex;
import gov.va.vba.persistence.entity.ModelRatingResults;
import gov.va.vba.persistence.entity.ModelRatingResultsCntnt;
import gov.va.vba.persistence.entity.ModelRatingResultsCntntId;
import gov.va.vba.persistence.entity.ModelRatingResultsDiag;
import gov.va.vba.persistence.entity.ModelRatingResultsDiagId;
import gov.va.vba.persistence.entity.ModelRatingResultsStatus;
import gov.va.vba.persistence.entity.ModelRatingResultsStatusId;
import gov.va.vba.persistence.models.data.ClaimDetails;
import gov.va.vba.persistence.models.data.ContentionDetails;
import gov.va.vba.persistence.models.data.DiagnosisCount;
import gov.va.vba.persistence.repository.ClaimRepository;
import gov.va.vba.persistence.repository.EarDao;
import gov.va.vba.persistence.repository.ModelRatingResultsCntnRepository;
import gov.va.vba.persistence.repository.ModelRatingResultsDiagRepository;
import gov.va.vba.persistence.repository.ModelRatingResultsRepository;
import gov.va.vba.persistence.repository.ModelRatingResultsStatusRepository;
import gov.va.vba.persistence.repository.RatingDao;
import gov.va.vba.persistence.repository.RatingDecisionRepository;
import gov.va.vba.service.EarService;
import gov.va.vba.service.KneeService;
import gov.va.vba.service.orika.ClaimMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClaimDataService extends AbsDataService<gov.va.vba.persistence.entity.Claim, Claim> {

    private static final Logger LOG = LoggerFactory.getLogger(ClaimDataService.class);
    private static final List<Long> KNEE_CONTENTION_IDS = Arrays.asList(230L, 270L, 3690L, 3700L, 3710L, 8919L, 3720L, 3730L, 3780L, 3790L, 3800L);
    private static final List<Long> EAR_CONTENTION_IDS = Arrays.asList(2200L, 2210L, 3140L, 3150L, 4130L, 4210L, 4700L, 4920L, 5000L, 5010L, 5710L, 6850L);
    private static final List<String> KNEE_DIAGNOSIS_CODES = Arrays.asList("5055", "5161", "5162", "5163", "5164", "5165", "5256", "5257", "5258", "5259", "5260", "5261", "5262", "5263", "5264", "5313", "5314", "5315");
    private static final List<String> EAR_DIAGNOSIS_CODES = Arrays.asList("6100", "6200", "6210", "6202", "6204", "6205", "6207", "6209", "6201", "6211", "6260");

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
    private ModelRatingResultsDiagRepository modelRatingResultsDiagRepository;

    @Autowired
    private ModelRatingResultsStatusRepository modelRatingResultsStatusRepository;

    @Autowired
    private EarService earService;

    @Autowired
    private KneeService kneeService;

    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private EarDao earDao;

    @Autowired
    private DDMDataService ddmDataService;

    @Autowired
    private DDMModelCntntService ddmModelCntntService;

    @Autowired
    private DDMModelDiagService ddmModelDiagService;

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
        List<ClaimDetails> claims = ratingDao.getClaims();
        LOG.info("SIZE of input :::: " + claims.size());
        for (int i = 0; i < claims.size(); i++) {
            System.out.println(claims.get(i));
        }
        return mapKneeClaimsToClaims(claims);
    }

    public List<VeteranClaimRating> findByVeteranId(List<VeteranClaim> veteranClaims) {
        List<VeteranClaimRating> veteranClaimRatings = new ArrayList<>();
        String currentLogin = null;
        for (VeteranClaim vc : veteranClaims) {

            VeteranClaimRating veteranClaimRating = new VeteranClaimRating();
            veteranClaimRating.setVeteran(vc.getVeteran());
            int veteranId = vc.getVeteran().getVeteranId();
            if (null != vc.getUserId()) {
                currentLogin = vc.getUserId();
            }
            List<gov.va.vba.bcdss.models.Claim> claims = vc.getClaim();
            List<ClaimRating> claimRatings = new ArrayList<>();
            for (gov.va.vba.bcdss.models.Claim c : claims) {
                int claimId = c.getClaimId();
                LOG.info("CONTENTION CODE IS ::: " + c.getContentionId());
                if (c.getContentionId() == 0) {
                    continue;
                }
                ContentionDetails contentionDetails = ratingDao.getContention(NumberUtils.toInt(c.getContentionClassificationId()));
                String modelType = contentionDetails.getModelType();
                ModelRatingResults results = null;
                List<ClaimDetails> claimDetails = new ArrayList<>();
                if (modelType.equalsIgnoreCase(ModelType.EAR.name())) {
                    claimDetails = earDao.getClaims(veteranId, claimId);
                    results = earService.processClaims(veteranId, claimDetails, currentLogin);
                } else if (modelType.equalsIgnoreCase(ModelType.KNEE.name())) {
                    claimDetails = ratingDao.getPreviousClaims(veteranId, claimId);
                    results = kneeService.processClaims(veteranId, claimDetails, currentLogin);
                }

                if (results != null) {
                    //Added code for Process Id Sequence generation
                    List<Long> maxprocessId = ratingDao.getProcessIDSeq();
                    LOG.debug("******************************");
                    long processIdSeq = maxprocessId.get(0);
                    LOG.info("MAX ProcessId -------- " + processIdSeq);

                    //Set<Long> claimsList = new HashSet<>();
                    //Map<Long, Integer> contentionCounts = new HashMap<>();
                    Map<Long, Long> contentionCounts = claimDetails.stream().collect(Collectors.groupingBy(ClaimDetails::getContentionClassificationId, Collectors.counting()));
                    Set<Long> claimsList = claimDetails.stream().map(ClaimDetails::getClaimId).collect(Collectors.toSet());

                    //setting ProcessId as Max Sequence generated+1
                    results.setProcessId(processIdSeq + 1);
                    results.setClaimCount((long) claimsList.size());
                    results.setContentionCount((long) contentionCounts.keySet().size());

                    ModelRatingResults savedResults = modelRatingResultsRepository.save(results);
                    BigDecimal processId = BigDecimal.valueOf(savedResults.getProcessId());

                    LOG.info("=================================================================");
                    LOG.info("RESULTS :::::::::::::::: " + savedResults);
                    LOG.info("=================================================================");
                    List<DiagnosisCount> diagnosisCount;
                    List<String> diagnosisCodes;
                    List<Long> contentions;
                    if (modelType.equalsIgnoreCase("EAR")) {
                        diagnosisCodes = EAR_DIAGNOSIS_CODES;
                        contentions = EAR_CONTENTION_IDS;
                        diagnosisCount = ratingDao.getEarDiagnosisCount((long) veteranId, savedResults.getClaimDate());
                    } else {
                        diagnosisCodes = KNEE_DIAGNOSIS_CODES;
                        contentions = KNEE_CONTENTION_IDS;
                        diagnosisCount = ratingDao.getDiagnosisCount((long) veteranId, savedResults.getClaimDate());
                    }
                    saveModelResultsCtnts(contentionCounts, savedResults, contentions);
                    saveModelResultsDiag(savedResults, diagnosisCount, diagnosisCodes);
                    saveResultStatus(results, currentLogin);

                    //TODO: last param
                    List<Long> patterns = ratingDao.getPattern(results.getModelType(), savedResults.getPriorCDD(), contentionCounts, diagnosisCount);

                    if (CollectionUtils.isNotEmpty(patterns)) {
                        if (patterns.size() == 1) {
                            results.setPatternId(patterns.get(0));
                        } else {
                            HashMap<Long, Long> countMap = new HashMap<>();
                            for (Long i : contentions) {
                                countMap.put(i, 0L);
                            }
                            for (Map.Entry<Long, Long> x : contentionCounts.entrySet()) {
                                if (countMap.containsKey(x.getKey())) {
                                    countMap.put(x.getKey(), x.getValue());
                                }
                            }
                            List<DiagnosisCount> diagCount = new ArrayList<>();
                            for (String code : diagnosisCodes) {
                                DiagnosisCount dc = new DiagnosisCount();
                                dc.setDecisionCode(code);
                                dc.setCount(0);
                            }
                            for (DiagnosisCount x : diagnosisCount) {
                                String diagCode = x.getDecisionCode();
                                int count = x.getCount();
                                for (DiagnosisCount dc : diagCount) {
                                    if (dc.getDecisionCode().equals(diagCode)) {
                                        dc.setCount(count);
                                        break;
                                    }
                                }
                            }
                            patterns = ratingDao.getPattern(results.getModelType(), savedResults.getPriorCDD(), countMap, diagCount);
                            if (CollectionUtils.isNotEmpty(patterns)) {
                                results.setPatternId(patterns.get(0));
                            }
                        }
                    }
                    /*List<DDMModelPattern> patterns = ddmDataService.getPatternId(results.getModelType(), results.getClaimantAge(), results.getClaimCount(), (long) contentionCounts.keySet().size(), 0L);
                    if (CollectionUtils.isNotEmpty(patterns)) {
                        List<Long> patternsList = patterns.stream().map(DDMModelPattern::getPatternId).collect(Collectors.toList());
                        List<Long> cntntPattrens = ddmModelCntntService.getKneePatternId(contentionCounts, patternsList, modelType.toUpperCase());
                        if (CollectionUtils.isNotEmpty(cntntPattrens)) {
                            //List<Long> diagPatternsList = patterns.stream().map(DDMModelPattern::getPatternId).collect(Collectors.toList());

                            List<Long> diagPattren = ddmModelDiagService.getKneePatternId(diagnosisCount, cntntPattrens, modelType.toUpperCase());
                            LOG.info("PATTREN SIZE :::::: " + diagPattren);
                            if (CollectionUtils.isNotEmpty(diagPattren)) {
                                Long pattrenId = diagPattren.get(0);
                                results.setPatternId(pattrenId);
                                LOG.info("PATTREN ID :: " + pattrenId);
                                results = modelRatingResultsRepository.save(results);
                            }
                        }
                    }*/
                    //ddmModelCntntService.getPatternId(results.getModelType(), )

                    /*EarDecision ed = new EarDecision();
                    KneeDecision kd = new KneeDecision();*/
                    RatingDecisions ratingDecisions = new RatingDecisions();
                    ratingDecisions.setProcessId(processId.intValue());
                   /* ratingDecisions.setEarRatings(ed);
                    ratingDecisions.setKneeRatings(kd);*/
                    gov.va.vba.bcdss.models.Rating rating = new Rating();
                    rating.setCddAge(savedResults.getClaimantAge().intValue());
                    rating.setClaimAge(savedResults.getClaimantAge().intValue());
                    rating.setClaimantAge(savedResults.getClaimantAge().intValue());
                    rating.setClaimCount(savedResults.getClaimCount().intValue());
                    //rating.setContationCount(contentionsCount);
                    rating.setCurrentCdd(savedResults.getCurrentCDD().intValue());
                    rating.setRaterEvaluation(savedResults.getCurrentCDD().intValue());
                    rating.setPriorCdd(results.getPriorCDD().intValue());
                    rating.setProcessId(processId.intValue());
                    //rating.setQuantCdd(calculatedCdd.intValue());
                    rating.setRatingDecisions(ratingDecisions);
                    rating.setModelType(savedResults.getModelType());
                    if (results.getPatternId() != null) {
                        List<DDMModelPatternIndex> accuracy = ratingDao.getPatternAccuracy(results.getPatternId());
                        if (CollectionUtils.isNotEmpty(accuracy)) {
                            DDMModelPatternIndex ddmModelPatternIndex = accuracy.get(0);
                            LOG.info("PATTREN INDEX :: PATTERN {} AND ACCURACY {}", ddmModelPatternIndex.getPatternId(), ddmModelPatternIndex.getAccuracy());
                            if (ddmModelPatternIndex.getAccuracy() != null) {
                                rating.setAccuracy(ddmModelPatternIndex.getAccuracy().intValue());
                            }
                            if (ddmModelPatternIndex.getPatternIndexNumber() != null) {
                                rating.setRateOfUse(ddmModelPatternIndex.getPatternIndexNumber().intValue());
                            }
                            if (ddmModelPatternIndex.getCDD() != null) {
                                rating.setQuantCdd(ddmModelPatternIndex.getCDD().intValue());
                            }
                        }
                        rating.setPatternId(results.getPatternId().intValue());
                    }

                    rating.setProcessDate(savedResults.getClaimDate());
                    c.setClaimDate(savedResults.getClaimDate());
                        /*c.setProfileDate(claim.getProfileDate());*/
                    //c.setContentionClassificationId(String.valueOf(kneeClaim.getContentionClsfcnId()));
                    //c.setContentionId(Integer.parseInt(kneeClaim.getContentionId()));
                    //c.setProductTypeCode(kneeClaim.getEndPrdctTypeCode());
                    // c.setContentionBeginDate(kneeClaim.getContentionBeginDate());
                    ClaimRating cr = new ClaimRating();
                    cr.setClaim(c);
                    cr.setRating(rating);
                    claimRatings.add(cr);
                }
            }
            veteranClaimRating.getClaimRating().addAll(claimRatings);
            veteranClaimRatings.add(veteranClaimRating);
        }
        return veteranClaimRatings;
    }

    /**
     * Description: This method is used to save contentions count
     *
     * @param counts      - claimId
     * @param results     - results
     * @param contentions
     */
    private List<ModelRatingResultsCntnt> saveModelResultsCtnts(Map<Long, Long> counts, ModelRatingResults results, List<Long> contentions) {
        //TODO:
        //List<Object[]> contentionsCount = claimRepository.aggregateContentions(claimId, veteranId);
        HashMap<Long, Long> countMap = new HashMap<>();
        for (Long i : contentions) {
            countMap.put(i, 0L);
        }
        for (Map.Entry<Long, Long> x : counts.entrySet()) {
            if (countMap.containsKey(x.getKey())) {
                countMap.put(x.getKey(), x.getValue());
            }
        }
        List<ModelRatingResultsCntnt> cntntList = new ArrayList<>();
        for (Map.Entry<Long, Long> x : countMap.entrySet()) {
            ModelRatingResultsCntntId cntntId = new ModelRatingResultsCntntId(x.getKey(), results.getProcessId());
            ModelRatingResultsCntnt cntnt = new ModelRatingResultsCntnt();
            cntnt.setId(cntntId);
            cntnt.setCount(BigDecimal.valueOf(x.getValue()));
            cntnt.setModelRatingResults(results);
            cntntList.add(cntnt);
        }
        modelRatingResultsCntnRepository.save(cntntList);
        LOG.info("SUCCESSFULLY SAVED CONTENTION COUNT INTO MODEL_RATING_RESULTS_CNTNT TABLE");
        return cntntList;
    }

    /**
     * Description:
     *
     * @param results
     * @param diagnosisCount
     * @param diagnosisCodes
     */
    private void saveModelResultsDiag(ModelRatingResults results, List<DiagnosisCount> diagnosisCount, List<String> diagnosisCodes) {
        HashMap<String, Long> diagCount = new HashMap<>();
        for (String i : diagnosisCodes) {
            diagCount.put(i, 0L);
        }
        for (DiagnosisCount x : diagnosisCount) {
            String diagCode = x.getDecisionCode();
            int count = x.getCount();
            if (diagCount.containsKey(diagCode)) {
                diagCount.put(diagCode, (long) count);
            }
        }
        List<ModelRatingResultsDiag> diagList = new ArrayList<>();
        for (Map.Entry<String, Long> x : diagCount.entrySet()) {
            ModelRatingResultsDiagId diagId = new ModelRatingResultsDiagId(Long.valueOf(x.getKey()), results.getProcessId());
            ModelRatingResultsDiag diag = new ModelRatingResultsDiag();
            diag.setId(diagId);
            diag.setCount(x.getValue());
            diag.setModelRatingResults(results);
            diagList.add(diag);
        }
        modelRatingResultsDiagRepository.save(diagList);
        LOG.info("SUCCESSFULLY SAVED DIGANOSIS COUNT INTO MODEL_RATING_RESULTS_DIAG TABLE");

    }

    private void saveResultStatus(ModelRatingResults results, String user) {
        ModelRatingResultsStatusId id = new ModelRatingResultsStatusId();
        id.setProcessId(results.getProcessId());
        id.setProcessStatus("PENDING");
        ModelRatingResultsStatus status = new ModelRatingResultsStatus();
        status.setId(id);
        status.setModelRatingResults(results);
        status.setCrtdDtm(new Date());
        status.setCrtdBy(user);
        modelRatingResultsStatusRepository.save(status);
        LOG.info("SAVED MODEL RATING RESULTS STATUS SUCCESSFULLY");
    }

    public List<Claim> findClaims(String contentionType, Long regionalOfficeNumber, Date fromDate, Date toDate) {
        LOG.debug("REST request to get advance filtered Claims");
        List<ClaimDetails> input = new ArrayList<>();
        input = ratingDao.getClaimsByAllFilters(contentionType, regionalOfficeNumber, fromDate, toDate);

        LOG.info("SIZE :::: " + input.size());
        return mapFilteredClaims(input);

    }

    public List<Claim> getProcessClaimsResults(boolean establishedDate, Date fromDate, Date toDate, String contentionType, Long regionalOfficeNumber) {
        if (fromDate == null) {
            Calendar instance = Calendar.getInstance();
            instance.set(1900, 12, 31);
            fromDate = instance.getTime();
        }
        if (toDate == null) {
            toDate = new Date();
        }
        List<gov.va.vba.persistence.entity.Claim> result = claimRepository.findClaimSByRangeOnClaimDate(contentionType, regionalOfficeNumber, fromDate, toDate);
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

    private Date calculateClaimDate(Date claimDate) {
        if (claimDate != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(claimDate);
            return instance.getTime();
        }
        return null;
    }

    private List<Claim> mapKneeClaimsToClaims(List<ClaimDetails> kneeClaims) {
        List<Claim> claims = new ArrayList<>();
        for (ClaimDetails kneeClaim : kneeClaims) {
            Claim c = new Claim();
            gov.va.vba.domain.util.Veteran v = new gov.va.vba.domain.util.Veteran();
            v.setVeteranId(kneeClaim.getVeteranId());
            c.setClaimDate(kneeClaim.getClaimDate());
            c.setClaimId(kneeClaim.getClaimId());
            c.setRegionalOfficeOfClaim(kneeClaim.getClaimROName());
            c.setContentionId(kneeClaim.getContentionId());
            //Added CNTN_CLSFCN_ID to map to request header
            c.setContentionClassificationId(kneeClaim.getContentionClassificationId());
            c.setContentionClaimTextKeyForModel(kneeClaim.getContentionClaimantText());
            c.setCestDate(calculateCestDate(kneeClaim.getClaimDate()));
            c.setVeteran(v);
            c.setModelType(kneeClaim.getModelType());
            claims.add(c);
        }
        return claims;
    }

    private List<Claim> mapFilteredClaims(List<ClaimDetails> filteredClaims) {
        List<Claim> claims = new ArrayList<>();
        for (ClaimDetails filteredClaim : filteredClaims) {
            Claim c = new Claim();
            gov.va.vba.domain.util.Veteran v = new gov.va.vba.domain.util.Veteran();
            v.setVeteranId(filteredClaim.getVeteranId());
            c.setVeteran(v);
            c.setClaimDate(filteredClaim.getClaimDate());
            c.setClaimId(filteredClaim.getClaimId());
            c.setContentionId(filteredClaim.getContentionId());
            c.setRegionalOfficeOfClaim(filteredClaim.getClaimROName());
            c.setContentionClaimTextKeyForModel(filteredClaim.getContentionClaimantText());
            c.setCestDate(calculateCestDate(filteredClaim.getClaimDate()));
            c.setContentionClassificationId(filteredClaim.getContentionClassificationId());
            c.setModelType(filteredClaim.getModelType());
            claims.add(c);
        }
        return claims;
    }
}
